package br.com.fiap.agrosat.mapper;

import br.com.fiap.agrosat.controller.AlertaController;
import br.com.fiap.agrosat.dto.AlertaLista;
import br.com.fiap.agrosat.dto.AlertaResponse;
import br.com.fiap.agrosat.model.Alerta;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AlertaMapper {

    public AlertaResponse alertaToResponse(Alerta alerta) {
        Link link = linkTo(methodOn(AlertaController.class)
                .listarAlertasDaPropriedade(alerta.getPropriedade().getId(), 0))
                .withRel("Alertas da propriedade");
        return new AlertaResponse(alerta.getId(), alerta.getTipo(), alerta.getNivel(),
                alerta.getDescricao(), alerta.getDataAlerta(), alerta.getResolvido(),
                alerta.getPropriedade().getNome(), link);
    }

    public AlertaLista alertaToLista(Alerta alerta) {
        Link link = linkTo(methodOn(AlertaController.class).buscarAlerta(alerta.getId())).withRel("Detalhes do alerta");
        return new AlertaLista(alerta.getId(), alerta.getTipo(), alerta.getNivel(),
                alerta.getDataAlerta(), alerta.getResolvido(), link);
    }
}
