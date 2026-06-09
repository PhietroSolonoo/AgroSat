package br.com.fiap.agrosat.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TB_RECOMENDACAO")
public class Recomendacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_recomendacao")
    @SequenceGenerator(name = "sq_recomendacao", sequenceName = "SQ_RECOMENDACAO", allocationSize = 1)
    @Column(name = "id_recomendacao")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_propriedade", nullable = false)
    private Propriedade propriedade;

    @ManyToOne
    @JoinColumn(name = "id_alerta")
    private Alerta alerta;

    @Column(name = "ds_recomendacao", nullable = false, length = 500)
    private String descricao;

    @Column(name = "ds_acao", nullable = false, length = 300)
    private String acao;

    @Column(name = "dt_gerada")
    private LocalDate dataGerada;

    @Column(name = "dt_lida")
    private LocalDate dataLida;

    @Column(name = "fl_lida")
    private Integer lida;

    public Recomendacao() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Propriedade getPropriedade() { return propriedade; }
    public void setPropriedade(Propriedade propriedade) { this.propriedade = propriedade; }
    public Alerta getAlerta() { return alerta; }
    public void setAlerta(Alerta alerta) { this.alerta = alerta; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getAcao() { return acao; }
    public void setAcao(String acao) { this.acao = acao; }
    public LocalDate getDataGerada() { return dataGerada; }
    public void setDataGerada(LocalDate dataGerada) { this.dataGerada = dataGerada; }
    public LocalDate getDataLida() { return dataLida; }
    public void setDataLida(LocalDate dataLida) { this.dataLida = dataLida; }
    public Integer getLida() { return lida; }
    public void setLida(Integer lida) { this.lida = lida; }
}
