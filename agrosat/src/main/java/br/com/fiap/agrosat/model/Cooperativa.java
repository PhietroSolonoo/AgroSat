package br.com.fiap.agrosat.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TB_COOPERATIVA")
public class Cooperativa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_cooperativa")
    @SequenceGenerator(name = "sq_cooperativa", sequenceName = "SQ_COOPERATIVA", allocationSize = 1)
    @Column(name = "id_cooperativa")
    private Long id;

    @Column(name = "nm_cooperativa", nullable = false, length = 150)
    private String nome;

    @Column(name = "nr_cnpj", nullable = false, unique = true, length = 18)
    private String cnpj;

    @Column(name = "ds_regiao", length = 100)
    private String regiao;

    @Column(name = "ds_estado", length = 2)
    private String estado;

    @Column(name = "nr_telefone", length = 20)
    private String telefone;

    @Column(name = "ds_email", length = 150)
    private String email;

    @Column(name = "dt_cadastro")
    private LocalDate dataCadastro;

    public Cooperativa() {
    }

    public Cooperativa(String nome, String cnpj, String regiao, String estado, String telefone, String email) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.regiao = regiao;
        this.estado = estado;
        this.telefone = telefone;
        this.email = email;
        this.dataCadastro = LocalDate.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public String getRegiao() { return regiao; }
    public void setRegiao(String regiao) { this.regiao = regiao; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDate getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDate dataCadastro) { this.dataCadastro = dataCadastro; }
}
