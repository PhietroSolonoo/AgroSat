package br.com.fiap.agrosat.dto;

import jakarta.validation.constraints.*;

public record PropriedadeRequest(
        @NotBlank(message = "O nome da propriedade é obrigatório")
        @Size(min = 3, max = 150)
        String nome,

        @NotNull(message = "A área em hectares é obrigatória")
        @Positive(message = "A área deve ser positiva")
        Double areaHa,

        @NotBlank(message = "A cultura é obrigatória")
        String cultura,

        @NotNull(message = "A latitude é obrigatória")
        Double latitude,

        @NotNull(message = "A longitude é obrigatória")
        Double longitude,

        String status,

        @NotNull(message = "O id do produtor é obrigatório")
        Long idProdutor,

        Long idCooperativa
) {
}
