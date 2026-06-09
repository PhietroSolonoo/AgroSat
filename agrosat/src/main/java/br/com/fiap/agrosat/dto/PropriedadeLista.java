package br.com.fiap.agrosat.dto;

import org.springframework.hateoas.Link;

public record PropriedadeLista(Long id, String nome, String cultura, String status, Link link) {
}
