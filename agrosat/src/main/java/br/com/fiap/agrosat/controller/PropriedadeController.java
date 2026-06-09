package br.com.fiap.agrosat.controller;

import br.com.fiap.agrosat.dto.PropriedadeLista;
import br.com.fiap.agrosat.dto.PropriedadeRequest;
import br.com.fiap.agrosat.dto.PropriedadeResponse;
import br.com.fiap.agrosat.model.Propriedade;
import br.com.fiap.agrosat.service.PropriedadeService;
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
@RequestMapping("/propriedades")
@Tag(name = "Propriedades", description = "Gerenciamento de propriedades rurais")
public class PropriedadeController {

    private final PropriedadeService propriedadeService;

    public PropriedadeController(PropriedadeService propriedadeService) {
        this.propriedadeService = propriedadeService;
    }

    @Operation(summary = "Cadastra uma nova propriedade rural")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Propriedade cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping
    public ResponseEntity<Propriedade> cadastrarPropriedade(@Valid @RequestBody PropriedadeRequest request) {
        Propriedade propriedadeSalva = propriedadeService.criar(request);
        return new ResponseEntity<>(propriedadeSalva, HttpStatus.CREATED);
    }

    @Operation(summary = "Busca uma propriedade pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propriedade encontrada"),
            @ApiResponse(responseCode = "404", description = "Propriedade não encontrada",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{id}")
    public ResponseEntity<PropriedadeResponse> buscarPropriedade(@PathVariable Long id) {
        PropriedadeResponse propriedade = propriedadeService.buscarPorId(id);
        return new ResponseEntity<>(propriedade, HttpStatus.OK);
    }

    @Operation(summary = "Lista propriedades com paginação e ordenação por nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhuma propriedade encontrada",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping
    public ResponseEntity<Page<PropriedadeLista>> listarPropriedades(
            @RequestParam(defaultValue = "0") Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 5, Sort.by("nome").ascending());
        Page<PropriedadeLista> propriedades = propriedadeService.listar(pageable);
        if (propriedades.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(propriedades, HttpStatus.OK);
    }

    @Operation(summary = "Atualiza os dados de uma propriedade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propriedade atualizada"),
            @ApiResponse(responseCode = "404", description = "Propriedade não encontrada",
                    content = @Content(schema = @Schema()))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Propriedade> atualizarPropriedade(@PathVariable Long id,
                                                             @Valid @RequestBody PropriedadeRequest request) {
        Propriedade propriedadeAtualizada = propriedadeService.atualizar(id, request);
        return new ResponseEntity<>(propriedadeAtualizada, HttpStatus.OK);
    }

    @Operation(summary = "Remove uma propriedade pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Propriedade removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Propriedade não encontrada",
                    content = @Content(schema = @Schema()))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerPropriedade(@PathVariable Long id) {
        propriedadeService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
