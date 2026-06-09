package br.com.fiap.agrosat.dto;

import org.springframework.hateoas.Link;

public record PropriedadeResponse(Long id, String nome, Double areaHa, String cultura,
                                   Double latitude, Double longitude, String status,
                                   String nomeProdutor, String cooperativa, Link link) {
}
