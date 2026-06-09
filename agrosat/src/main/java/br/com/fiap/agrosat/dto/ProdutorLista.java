package br.com.fiap.agrosat.dto;

import org.springframework.hateoas.Link;

public record ProdutorLista(Long id, String nome, String cpf, String cidade, Link link) {
}
