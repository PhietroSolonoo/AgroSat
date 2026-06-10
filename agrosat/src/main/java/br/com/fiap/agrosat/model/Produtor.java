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


    @Embedded
    private Endereco endereco;

    @OneToMany(mappedBy = "produtor")
    private List<Propriedade> propriedades;

    public Produtor() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }
    public List<Propriedade> getPropriedades() { return propriedades; }
    public void setPropriedades(List<Propriedade> propriedades) { this.propriedades = propriedades; }
}