package br.com.fiap.agrosat.controller;

import br.com.fiap.agrosat.dto.DadosClimaticosResponse;
import br.com.fiap.agrosat.exception.ResourceNotFoundException;
import br.com.fiap.agrosat.model.LeituraSatelital;
import br.com.fiap.agrosat.model.Propriedade;
import br.com.fiap.agrosat.repository.PropriedadeRepository;
import br.com.fiap.agrosat.service.SateliteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/satelite")
@Tag(name = "Satélite", description = "Dados climáticos e saúde da lavoura via satélite")
public class SateliteController {

    private final SateliteService sateliteService;
    private final PropriedadeRepository propriedadeRepository;

    public SateliteController(SateliteService sateliteService, PropriedadeRepository propriedadeRepository) {
        this.sateliteService = sateliteService;
        this.propriedadeRepository = propriedadeRepository;
    }

    @Operation(summary = "Busca dados climáticos de uma propriedade via NASA POWER / Open-Meteo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados climáticos retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Propriedade não encontrada",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/propriedade/{id}/clima")
    public ResponseEntity<DadosClimaticosResponse> buscarClimaDaPropriedade(@PathVariable Long id) {
        Propriedade propriedade = propriedadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Propriedade não encontrada para o id: " + id));
        DadosClimaticosResponse dados = sateliteService.buscarDadosClimaticos(
                propriedade.getLatitude(), propriedade.getLongitude());
        return new ResponseEntity<>(dados, HttpStatus.OK);
    }

    @Operation(summary = "Calcula a saúde da lavoura e salva a leitura no banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saúde calculada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Propriedade não encontrada",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping("/propriedade/{id}/saude")
    public ResponseEntity<Map<String, Object>> calcularESalvarSaude(@PathVariable Long id) {
        Propriedade propriedade = propriedadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Propriedade não encontrada para o id: " + id));

        DadosClimaticosResponse dados = sateliteService.buscarDadosClimaticos(
                propriedade.getLatitude(), propriedade.getLongitude());

        LeituraSatelital leitura = sateliteService.salvarLeitura(propriedade, dados);
        String saude = sateliteService.calcularSaudeLavoura(dados.ndviEstimado());

        Map<String, Object> resultado = Map.of(
                "idLeitura", leitura.getId(),
                "propriedade", propriedade.getNome(),
                "ndvi", dados.ndviEstimado(),
                "saudeLavoura", saude,
                "temperatura", dados.temperaturaC(),
                "umidade", dados.umidadeRelativa(),
                "precipitacao", dados.precipitacaoMm()
        );
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    @Operation(summary = "Busca dados climáticos por coordenadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados climáticos retornados com sucesso")
    })
    @GetMapping("/clima")
    public ResponseEntity<DadosClimaticosResponse> buscarClimaPorCoordenadas(
            @RequestParam double latitude,
            @RequestParam double longitude) {
        DadosClimaticosResponse dados = sateliteService.buscarDadosClimaticos(latitude, longitude);
        return new ResponseEntity<>(dados, HttpStatus.OK);
    }
}
