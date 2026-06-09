package br.com.fiap.agrosat.controller;

import br.com.fiap.agrosat.dto.AlertaLista;
import br.com.fiap.agrosat.dto.AlertaRequest;
import br.com.fiap.agrosat.dto.AlertaResponse;
import br.com.fiap.agrosat.model.Alerta;
import br.com.fiap.agrosat.service.AlertaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alertas")
@Tag(name = "Alertas", description = "Gerenciamento de alertas da lavoura")
public class AlertaController {

    private final AlertaService alertaService;

    public AlertaController(AlertaService alertaService) {
        this.alertaService = alertaService;
    }

    @Operation(summary = "Registra um novo alerta para uma propriedade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alerta registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping
    public ResponseEntity<Alerta> registrarAlerta(@Valid @RequestBody AlertaRequest request) {
        Alerta alertaSalvo = alertaService.criar(request);
        return new ResponseEntity<>(alertaSalvo, HttpStatus.CREATED);
    }

    @Operation(summary = "Busca um alerta pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alerta encontrado"),
            @ApiResponse(responseCode = "404", description = "Alerta não encontrado",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{id}")
    public ResponseEntity<AlertaResponse> buscarAlerta(@PathVariable Long id) {
        AlertaResponse alerta = alertaService.buscarPorId(id);
        return new ResponseEntity<>(alerta, HttpStatus.OK);
    }

    @Operation(summary = "Lista alertas de uma propriedade com paginação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alertas retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum alerta encontrado",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/propriedade/{idPropriedade}")
    public ResponseEntity<Page<AlertaLista>> listarAlertasDaPropriedade(
            @PathVariable Long idPropriedade,
            @RequestParam(defaultValue = "0") Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 5, Sort.by("dataAlerta").descending());
        Page<AlertaLista> alertas = alertaService.listarPorPropriedade(idPropriedade, pageable);
        if (alertas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(alertas, HttpStatus.OK);
    }

    @Operation(summary = "Marca um alerta como resolvido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alerta resolvido"),
            @ApiResponse(responseCode = "404", description = "Alerta não encontrado",
                    content = @Content(schema = @Schema()))
    })
    @PatchMapping("/{id}/resolver")
    public ResponseEntity<Alerta> resolverAlerta(@PathVariable Long id) {
        Alerta alerta = alertaService.resolverAlerta(id);
        return new ResponseEntity<>(alerta, HttpStatus.OK);
    }

    @Operation(summary = "Remove um alerta pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Alerta removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Alerta não encontrado",
                    content = @Content(schema = @Schema()))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerAlerta(@PathVariable Long id) {
        alertaService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
