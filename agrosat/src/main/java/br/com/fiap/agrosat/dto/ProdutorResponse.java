package br.com.fiap.agrosat.dto;

import org.springframework.hateoas.Link;

public record ProdutorResponse(Long id, String nome, String email, String telefone, String cpf, String cidade, String estado, Link link) {
}
