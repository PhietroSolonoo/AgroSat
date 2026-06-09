package br.com.fiap.agrosat.dto;

import org.springframework.hateoas.Link;
import java.time.LocalDate;

public record AlertaLista(Long id, String tipo, String nivel, LocalDate dataAlerta, Integer resolvido, Link link) {
}
