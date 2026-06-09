package br.com.fiap.agrosat.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Endereco {

    @Column(name = "ds_logradouro", length = 200)
    private String logradouro;

    @Column(name = "nr_numero", length = 10)
    private String numero;

    @Column(name = "ds_bairro", length = 100)
    private String bairro;

    @Column(name = "ds_cidade", length = 100)
    private String cidade;

    @Column(name = "ds_estado", length = 2)
    private String estado;

    @Column(name = "nr_cep", length = 9)
    private String cep;

    public Endereco() {}

    public Endereco(String logradouro, String numero, String bairro,
                    String cidade, String estado, String cep) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
}