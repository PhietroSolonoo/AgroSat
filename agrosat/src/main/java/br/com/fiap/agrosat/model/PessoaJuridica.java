package br.com.fiap.agrosat.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("COOPERATIVA")
public class PessoaJuridica extends Usuario {

    public PessoaJuridica() {}

    public PessoaJuridica(String nome, String email, String senha, String telefone) {
        super(nome, email, senha, telefone);
    }
}