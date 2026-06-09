package br.com.fiap.agrosat.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TB_ALERTA")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_alerta")
    @SequenceGenerator(name = "sq_alerta", sequenceName = "SQ_ALERTA", allocationSize = 1)
    @Column(name = "id_alerta")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_propriedade", nullable = false)
    private Propriedade propriedade;

    @ManyToOne
    @JoinColumn(name = "id_leitura")
    private LeituraSatelital leitura;

    @Column(name = "tp_alerta", nullable = false, length = 20)
    private String tipo;

    @Column(name = "tp_nivel", nullable = false, length = 10)
    private String nivel;

    @Column(name = "ds_alerta", nullable = false, length = 500)
    private String descricao;

    @Column(name = "dt_alerta")
    private LocalDate dataAlerta;

    @Column(name = "fl_resolvido")
    private Integer resolvido;

    @Column(name = "dt_resolucao")
    private LocalDate dataResolucao;

    public Alerta() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Propriedade getPropriedade() { return propriedade; }
    public void setPropriedade(Propriedade propriedade) { this.propriedade = propriedade; }
    public LeituraSatelital getLeitura() { return leitura; }
    public void setLeitura(LeituraSatelital leitura) { this.leitura = leitura; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDate getDataAlerta() { return dataAlerta; }
    public void setDataAlerta(LocalDate dataAlerta) { this.dataAlerta = dataAlerta; }
    public Integer getResolvido() { return resolvido; }
    public void setResolvido(Integer resolvido) { this.resolvido = resolvido; }
    public LocalDate getDataResolucao() { return dataResolucao; }
    public void setDataResolucao(LocalDate dataResolucao) { this.dataResolucao = dataResolucao; }
}
