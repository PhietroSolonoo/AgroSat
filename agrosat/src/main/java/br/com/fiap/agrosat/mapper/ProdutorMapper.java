package br.com.fiap.agrosat.mapper;

import br.com.fiap.agrosat.controller.ProdutorController;
import br.com.fiap.agrosat.dto.ProdutorLista;
import br.com.fiap.agrosat.dto.ProdutorResponse;
import br.com.fiap.agrosat.model.Endereco;
import br.com.fiap.agrosat.model.Produtor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProdutorMapper {

    public ProdutorResponse produtorToResponse(Produtor produtor) {
        Endereco end = produtor.getEndereco();
        String cidade = end != null ? end.getCidade() : null;
        String estado = end != null ? end.getEstado() : null;

        Link link = linkTo(methodOn(ProdutorController.class)
                .listarProdutores(0)).withRel("Lista de produtores");

        return new ProdutorResponse(
                produtor.getId(),
                produtor.getUsuario().getNome(),
                produtor.getUsuario().getEmail(),
                produtor.getUsuario().getTelefone(),
                produtor.getCpf(),
                cidade,
                estado,
                link
        );
    }

    public ProdutorLista produtorToLista(Produtor produtor) {
        Endereco end = produtor.getEndereco();
        String cidade = end != null ? end.getCidade() : null;

        Link link = linkTo(methodOn(ProdutorController.class)
                .buscarProdutor(produtor.getId())).withRel("Detalhes do produtor");

        return new ProdutorLista(
                produtor.getId(),
                produtor.getUsuario().getNome(),
                produtor.getCpf(),
                cidade,
                link
        );
    }
}