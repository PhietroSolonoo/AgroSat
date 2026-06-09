package br.com.fiap.agrosat.service;

import br.com.fiap.agrosat.dto.DadosClimaticosResponse;
import br.com.fiap.agrosat.model.LeituraSatelital;
import br.com.fiap.agrosat.model.Propriedade;
import br.com.fiap.agrosat.repository.LeituraSatelitalRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class SateliteService {

    private final WebClient webClient;
    private final LeituraSatelitalRepository leituraRepository;

    @Value("${nasa.power.api.url}")
    private String nasaPowerUrl;

    @Value("${openmeteo.api.url}")
    private String openMeteoUrl;

    public SateliteService(WebClient.Builder webClientBuilder, LeituraSatelitalRepository leituraRepository) {
        this.webClient = webClientBuilder.build();
        this.leituraRepository = leituraRepository;
    }

    @Cacheable("dadosClima")
    public DadosClimaticosResponse buscarDadosClimaticos(double latitude, double longitude) {
        String hoje = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String semanaPassada = LocalDate.now().minusDays(7).format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        try {
            Map response = webClient.get()
                    .uri(nasaPowerUrl + "?parameters=T2M,PRECTOTCORR,RH2M,WS10M&community=AG" +
                            "&longitude=" + longitude + "&latitude=" + latitude +
                            "&start=" + semanaPassada + "&end=" + hoje + "&format=JSON")
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            return extrairDadosNasa(response, latitude, longitude);

        } catch (Exception e) {
            return buscarDadosOpenMeteo(latitude, longitude);
        }
    }

    private DadosClimaticosResponse buscarDadosOpenMeteo(double latitude, double longitude) {
        try {
            Map response = webClient.get()
                    .uri(openMeteoUrl + "?latitude=" + latitude + "&longitude=" + longitude +
                            "&current=temperature_2m,relative_humidity_2m,precipitation,wind_speed_10m" +
                            "&forecast_days=1")
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            return extrairDadosOpenMeteo(response, latitude, longitude);
        } catch (Exception ex) {
            return new DadosClimaticosResponse(latitude, longitude, 25.0, 60.0, 0.0, 10.0, calcularNdviEstimado(60.0, 0.0));
        }
    }

    private DadosClimaticosResponse extrairDadosNasa(Map response, double lat, double lon) {
        try {
            Map properties = (Map) response.get("properties");
            Map parameter = (Map) properties.get("parameter");
            Map t2m = (Map) parameter.get("T2M");
            Map prec = (Map) parameter.get("PRECTOTCORR");
            Map rh2m = (Map) parameter.get("RH2M");
            Map ws = (Map) parameter.get("WS10M");

            String ultimaData = t2m.keySet().stream().reduce((a, b) -> b).orElse("").toString();
            double temp = ((Number) t2m.get(ultimaData)).doubleValue();
            double precipitacao = ((Number) prec.get(ultimaData)).doubleValue();
            double umidade = ((Number) rh2m.get(ultimaData)).doubleValue();
            double vento = ((Number) ws.get(ultimaData)).doubleValue();
            double ndvi = calcularNdviEstimado(umidade, precipitacao);

            return new DadosClimaticosResponse(lat, lon, temp, umidade, precipitacao, vento, ndvi);
        } catch (Exception e) {
            return new DadosClimaticosResponse(lat, lon, 25.0, 60.0, 0.0, 10.0, calcularNdviEstimado(60.0, 0.0));
        }
    }

    private DadosClimaticosResponse extrairDadosOpenMeteo(Map response, double lat, double lon) {
        try {
            Map current = (Map) response.get("current");
            double temp = ((Number) current.get("temperature_2m")).doubleValue();
            double umidade = ((Number) current.get("relative_humidity_2m")).doubleValue();
            double precipitacao = ((Number) current.get("precipitation")).doubleValue();
            double vento = ((Number) current.get("wind_speed_10m")).doubleValue();
            double ndvi = calcularNdviEstimado(umidade, precipitacao);

            return new DadosClimaticosResponse(lat, lon, temp, umidade, precipitacao, vento, ndvi);
        } catch (Exception e) {
            return new DadosClimaticosResponse(lat, lon, 25.0, 60.0, 0.0, 10.0, calcularNdviEstimado(60.0, 0.0));
        }
    }

    public LeituraSatelital salvarLeitura(Propriedade propriedade, DadosClimaticosResponse dados) {
        LeituraSatelital leitura = new LeituraSatelital();
        leitura.setPropriedade(propriedade);
        leitura.setDataLeitura(LocalDate.now());
        leitura.setDataCriacao(LocalDate.now());
        leitura.setNdvi(dados.ndviEstimado());
        leitura.setPrecipitacao(dados.precipitacaoMm());
        leitura.setUmidadeSolo(dados.umidadeRelativa());
        leitura.setTempMax(dados.temperaturaC());
        leitura.setTempMin(dados.temperaturaC() - 5);
        leitura.setFonte("NASA_POWER");
        return leituraRepository.save(leitura);
    }

    private double calcularNdviEstimado(double umidade, double precipitacao) {
        double base = (umidade / 100.0) * 0.6;
        double bonus = Math.min(precipitacao / 50.0, 0.4);
        double ndvi = base + bonus;
        return Math.min(Math.max(ndvi, -1.0), 1.0);
    }

    public String calcularSaudeLavoura(double ndvi) {
        if (ndvi >= 0.6) return "EXCELENTE";
        if (ndvi >= 0.4) return "BOA";
        if (ndvi >= 0.2) return "MODERADA";
        if (ndvi >= 0.0) return "RUIM";
        return "CRITICA";
    }
}
