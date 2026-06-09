package br.com.fiap.agrosat.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record ProdutorRequest(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
        String nome,

        @NotBlank(message = "O email é obrigatório")
        @Email(message = "Informe um email válido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        String senha,

        String telefone,

        @NotBlank(message = "O CPF é obrigatório")
        String cpf,

        LocalDate dataNascimento,
        String logradouro,
        String numero,
        String bairro,
        String cidade,
        String estado,
        String cep
) {
}
