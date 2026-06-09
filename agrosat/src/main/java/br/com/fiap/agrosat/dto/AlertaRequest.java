package br.com.fiap.agrosat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AlertaRequest(
        @NotNull(message = "O id da propriedade é obrigatório")
        Long idPropriedade,

        Long idLeitura,

        @NotBlank(message = "O tipo do alerta é obrigatório")
        String tipo,

        @NotBlank(message = "O nível é obrigatório")
        String nivel,

        @NotBlank(message = "A descrição é obrigatória")
        String descricao
) {
}
