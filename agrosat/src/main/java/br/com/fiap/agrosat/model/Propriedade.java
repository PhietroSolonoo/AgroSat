package br.com.fiap.agrosat.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "TB_PROPRIEDADE")
public class Propriedade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_propriedade")
    @SequenceGenerator(name = "sq_propriedade", sequenceName = "SQ_PROPRIEDADE", allocationSize = 1)
    @Column(name = "id_propriedade")
    private Long id;

    @Column(name = "nm_propriedade", nullable = false, length = 150)
    private String nome;

    @Column(name = "nr_area_ha", nullable = false)
    private Double areaHa;

    @Column(name = "tp_cultura", nullable = false, length = 20)
    private String cultura;

    @Column(name = "nr_latitude", nullable = false)
    private Double latitude;

    @Column(name = "nr_longitude", nullable = false)
    private Double longitude;

    @Column(name = "tp_status", length = 10)
    private String status;

    @Column(name = "dt_cadastro")
    private LocalDate dataCadastro;

    @ManyToOne
    @JoinColumn(name = "id_produtor", nullable = false)
    private Produtor produtor;

    @ManyToOne
    @JoinColumn(name = "id_cooperativa")
    private Cooperativa cooperativa;

    @OneToMany(mappedBy = "propriedade")
    private List<Alerta> alertas;

    @OneToMany(mappedBy = "propriedade")
    private List<LeituraSatelital> leituras;

    public Propriedade() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Double getAreaHa() { return areaHa; }
    public void setAreaHa(Double areaHa) { this.areaHa = areaHa; }
    public String getCultura() { return cultura; }
    public void setCultura(String cultura) { this.cultura = cultura; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDate dataCadastro) { this.dataCadastro = dataCadastro; }
    public Produtor getProdutor() { return produtor; }
    public void setProdutor(Produtor produtor) { this.produtor = produtor; }
    public Cooperativa getCooperativa() { return cooperativa; }
    public void setCooperativa(Cooperativa cooperativa) { this.cooperativa = cooperativa; }
    public List<Alerta> getAlertas() { return alertas; }
    public void setAlertas(List<Alerta> alertas) { this.alertas = alertas; }
    public List<LeituraSatelital> getLeituras() { return leituras; }
    public void setLeituras(List<LeituraSatelital> leituras) { this.leituras = leituras; }
}
