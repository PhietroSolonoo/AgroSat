package br.com.fiap.agrosat.controller;

import br.com.fiap.agrosat.dto.ProdutorLista;
import br.com.fiap.agrosat.dto.ProdutorRequest;
import br.com.fiap.agrosat.dto.ProdutorResponse;
import br.com.fiap.agrosat.model.Produtor;
import br.com.fiap.agrosat.service.ProdutorService;
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
@RequestMapping("/produtores")
@Tag(name = "Produtores", description = "Gerenciamento de produtores rurais")
public class ProdutorController {

    private final ProdutorService produtorService;

    public ProdutorController(ProdutorService produtorService) {
        this.produtorService = produtorService;
    }

    @Operation(summary = "Cadastra um novo produtor rural")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produtor cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou CPF já cadastrado",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping
    public ResponseEntity<Produtor> cadastrarProdutor(@Valid @RequestBody ProdutorRequest request) {
        Produtor produtorSalvo = produtorService.criar(request);
        return new ResponseEntity<>(produtorSalvo, HttpStatus.CREATED);
    }

    @Operation(summary = "Busca um produtor pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtor encontrado"),
            @ApiResponse(responseCode = "404", description = "Produtor não encontrado",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutorResponse> buscarProdutor(@PathVariable Long id) {
        ProdutorResponse produtor = produtorService.buscarPorId(id);
        return new ResponseEntity<>(produtor, HttpStatus.OK);
    }

    @Operation(summary = "Lista produtores com paginação e ordenação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum produtor encontrado",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping
    public ResponseEntity<Page<ProdutorLista>> listarProdutores(
            @RequestParam(defaultValue = "0") Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 5, Sort.by("cpf").ascending());
        Page<ProdutorLista> produtores = produtorService.listar(pageable);
        if (produtores.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(produtores, HttpStatus.OK);
    }

    @Operation(summary = "Atualiza os dados de um produtor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtor atualizado"),
            @ApiResponse(responseCode = "404", description = "Produtor não encontrado",
                    content = @Content(schema = @Schema()))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Produtor> atualizarProdutor(@PathVariable Long id,
                                                       @Valid @RequestBody ProdutorRequest request) {
        Produtor produtorAtualizado = produtorService.atualizar(id, request);
        return new ResponseEntity<>(produtorAtualizado, HttpStatus.OK);
    }

    @Operation(summary = "Remove um produtor pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produtor removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produtor não encontrado",
                    content = @Content(schema = @Schema()))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerProdutor(@PathVariable Long id) {
        produtorService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
