package br.com.fiap.agrosat.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "TB_PRODUTOR")
public class Produtor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_produtor")
    @SequenceGenerator(name = "sq_produtor", sequenceName = "SQ_PRODUTOR", allocationSize = 1)
    @Column(name = "id_produtor")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "nr_cpf", nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(name = "dt_nascimento")
    private LocalDate dataNascimento;

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

    @OneToMany(mappedBy = "produtor")
    private List<Propriedade> propriedades;

    public Produtor() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
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
    public List<Propriedade> getPropriedades() { return propriedades; }
    public void setPropriedades(List<Propriedade> propriedades) { this.propriedades = propriedades; }
}
