package br.com.fiap.agrosat.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TB_USUARIO")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tp_perfil", discriminatorType = DiscriminatorType.STRING)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_usuario")
    @SequenceGenerator(name = "sq_usuario", sequenceName = "SQ_USUARIO", allocationSize = 1)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "nm_usuario", nullable = false, length = 100)
    private String nome;

    @Column(name = "ds_email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "ds_senha", nullable = false, length = 255)
    private String senha;

    @Column(name = "nr_telefone", length = 20)
    private String telefone;

    // tp_perfil NÃO aparece aqui como campo Java
    // o JPA usa ela só como discriminator — o banco já tem a coluna criada pelo SQL
    // insertable=false, updatable=false evita conflito
    @Column(name = "tp_perfil", insertable = false, updatable = false)
    private String perfil;

    @Column(name = "dt_cadastro")
    private LocalDate dataCadastro;

    public Usuario() {}

    public Usuario(String nome, String email, String senha, String telefone) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.dataCadastro = LocalDate.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getPerfil() { return perfil; }
    public LocalDate getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDate dataCadastro) { this.dataCadastro = dataCadastro; }
}