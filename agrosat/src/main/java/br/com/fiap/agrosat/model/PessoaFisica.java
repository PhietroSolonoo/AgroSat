package br.com.fiap.agrosat.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("PRODUTOR")
public class PessoaFisica extends Usuario {

    public PessoaFisica() {}

    public PessoaFisica(String nome, String email, String senha, String telefone) {
        super(nome, email, senha, telefone);
    }
}