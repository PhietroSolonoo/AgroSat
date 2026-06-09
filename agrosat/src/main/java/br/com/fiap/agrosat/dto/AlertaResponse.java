package br.com.fiap.agrosat.dto;

import org.springframework.hateoas.Link;
import java.time.LocalDate;

public record AlertaResponse(Long id, String tipo, String nivel, String descricao,
                              LocalDate dataAlerta, Integer resolvido,
                              String nomePropriedade, Link link) {
}
