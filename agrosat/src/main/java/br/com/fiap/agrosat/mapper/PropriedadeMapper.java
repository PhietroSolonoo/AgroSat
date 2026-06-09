package br.com.fiap.agrosat.mapper;

import br.com.fiap.agrosat.controller.PropriedadeController;
import br.com.fiap.agrosat.dto.PropriedadeLista;
import br.com.fiap.agrosat.dto.PropriedadeResponse;
import br.com.fiap.agrosat.model.Propriedade;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PropriedadeMapper {

    public PropriedadeResponse propriedadeToResponse(Propriedade propriedade) {
        Link link = linkTo(methodOn(PropriedadeController.class).listarPropriedades(0)).withRel("Lista de propriedades");
        String cooperativa = propriedade.getCooperativa() != null ? propriedade.getCooperativa().getNome() : "Sem cooperativa";
        return new PropriedadeResponse(propriedade.getId(), propriedade.getNome(),
                propriedade.getAreaHa(), propriedade.getCultura(),
                propriedade.getLatitude(), propriedade.getLongitude(),
                propriedade.getStatus(), propriedade.getProdutor().getUsuario().getNome(),
                cooperativa, link);
    }

    public PropriedadeLista propriedadeToLista(Propriedade propriedade) {
        Link link = linkTo(methodOn(PropriedadeController.class).buscarPropriedade(propriedade.getId())).withRel("Detalhes da propriedade");
        return new PropriedadeLista(propriedade.getId(), propriedade.getNome(),
                propriedade.getCultura(), propriedade.getStatus(), link);
    }
}
