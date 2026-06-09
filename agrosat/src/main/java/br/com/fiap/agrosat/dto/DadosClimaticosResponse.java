package br.com.fiap.agrosat.dto;

public record DadosClimaticosResponse(
        double latitude,
        double longitude,
        double temperaturaC,
        double umidadeRelativa,
        double precipitacaoMm,
        double ventoKmh,
        double ndviEstimado
) {
}
