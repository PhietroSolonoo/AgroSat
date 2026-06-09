package br.com.fiap.agrosat.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TB_LEITURA_SATELITAL")
public class LeituraSatelital {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_leitura_satelital")
    @SequenceGenerator(name = "sq_leitura_satelital", sequenceName = "SQ_LEITURA_SATELITAL", allocationSize = 1)
    @Column(name = "id_leitura")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_propriedade", nullable = false)
    private Propriedade propriedade;

    @Column(name = "dt_leitura", nullable = false)
    private LocalDate dataLeitura;

    @Column(name = "nr_ndvi")
    private Double ndvi;

    @Column(name = "nr_umidade_solo")
    private Double umidadeSolo;

    @Column(name = "nr_precipitacao")
    private Double precipitacao;

    @Column(name = "nr_temp_max")
    private Double tempMax;

    @Column(name = "nr_temp_min")
    private Double tempMin;

    @Column(name = "tp_fonte", length = 20)
    private String fonte;

    @Column(name = "dt_criacao")
    private LocalDate dataCriacao;

    public LeituraSatelital() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Propriedade getPropriedade() { return propriedade; }
    public void setPropriedade(Propriedade propriedade) { this.propriedade = propriedade; }
    public LocalDate getDataLeitura() { return dataLeitura; }
    public void setDataLeitura(LocalDate dataLeitura) { this.dataLeitura = dataLeitura; }
    public Double getNdvi() { return ndvi; }
    public void setNdvi(Double ndvi) { this.ndvi = ndvi; }
    public Double getUmidadeSolo() { return umidadeSolo; }
    public void setUmidadeSolo(Double umidadeSolo) { this.umidadeSolo = umidadeSolo; }
    public Double getPrecipitacao() { return precipitacao; }
    public void setPrecipitacao(Double precipitacao) { this.precipitacao = precipitacao; }
    public Double getTempMax() { return tempMax; }
    public void setTempMax(Double tempMax) { this.tempMax = tempMax; }
    public Double getTempMin() { return tempMin; }
    public void setTempMin(Double tempMin) { this.tempMin = tempMin; }
    public String getFonte() { return fonte; }
    public void setFonte(String fonte) { this.fonte = fonte; }
    public LocalDate getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDate dataCriacao) { this.dataCriacao = dataCriacao; }
}
