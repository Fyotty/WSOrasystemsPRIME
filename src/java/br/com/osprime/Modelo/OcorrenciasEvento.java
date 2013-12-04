/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.Modelo;

import br.com.orasystems.Modelo.Empresas;
import br.com.orasystems.Modelo.Repositores;
import br.com.orasystems.Utilitarios.DateTimeAdapter;
import java.sql.Timestamp;
import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author fernando
 */
@XmlRootElement(name = "OCORRENCIA_EVENTO")
@XmlType(propOrder = {"id", "empresas", "repositores", "eventos", "data_inicio", "latitude",
    "longitude", "status_ocorrencia", "data_termino", "data_criacao",
    "observacao", "nome_responsavel", "ocorrenciasEventoFotos"})
public class OcorrenciasEvento {

    private long id;
    private Eventos eventos;
    private Timestamp data_inicio;
    private Timestamp data_transmissao;
    private String latitude;
    private String longitude;
    private int status_ocorrencia;
    private Timestamp data_termino;
    private Timestamp data_criacao;
    private String observacao;
    private String nome_responsavel;
    private OcorrenciasEventoFotos ocorrenciasEventoFotos;
    private Empresas empresas;
    private Repositores repositores;

    public OcorrenciasEvento() {
        this.eventos = new Eventos();
        this.ocorrenciasEventoFotos = new OcorrenciasEventoFotos();
        this.empresas = new Empresas();
        this.repositores = new Repositores();
    }

    //@XmlTransient
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @XmlElement(name = "evento")
    public Eventos getEventos() {
        return eventos;
    }

    public void setEventos(Eventos eventos) {
        this.eventos = eventos;
    }

    @XmlElement(name = "dataInicio", required = false)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    public Timestamp getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(Timestamp data_inicio) {
        this.data_inicio = data_inicio;
    }

    @XmlTransient
    public Timestamp getData_transmissao() {
        return data_transmissao;
    }

    public void setData_transmissao(Timestamp data_transmissao) {
        this.data_transmissao = data_transmissao;
    }

    @XmlElement(name = "latitude")
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @XmlElement(name = "longitude")
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @XmlElement(name = "status")
    public int getStatus_ocorrencia() {
        return status_ocorrencia;
    }

    public void setStatus_ocorrencia(int status_ocorrencia) {
        this.status_ocorrencia = status_ocorrencia;
    }

    @XmlElement(name = "datatermino", required = false)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    public Timestamp getData_termino() {
        return data_termino;
    }

    public void setData_termino(Timestamp data_termino) {
        this.data_termino = data_termino;
    }

    @XmlElement(name = "dataCriacao", required = false)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    public Timestamp getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(Timestamp data_criacao) {
        this.data_criacao = data_criacao;
    }

    @XmlElement(name = "observacao")
    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @XmlElement(name = "nomeResponsavel")
    public String getNome_responsavel() {
        return nome_responsavel;
    }

    public void setNome_responsavel(String nome_responsavel) {
        this.nome_responsavel = nome_responsavel;
    }

    @XmlElement(name = "FOTOS", required = true)
    public OcorrenciasEventoFotos getOcorrenciasEventoFotos() {
        return ocorrenciasEventoFotos;
    }

    public void setOcorrenciasEventoFotos(OcorrenciasEventoFotos ocorrenciasEventoFotos) {
        this.ocorrenciasEventoFotos = ocorrenciasEventoFotos;
    }

    @XmlElement(name = "empresa")
    public Empresas getEmpresas() {
        return empresas;
    }

    public void setEmpresas(Empresas empresas) {
        this.empresas = empresas;
    }

    @XmlElement(name = "repositor")
    public Repositores getRepositores() {
        return repositores;
    }

    public void setRepositores(Repositores repositores) {
        this.repositores = repositores;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 31 * hash + Objects.hashCode(this.eventos);
        hash = 31 * hash + Objects.hashCode(this.data_inicio);
        hash = 31 * hash + Objects.hashCode(this.data_transmissao);
        hash = 31 * hash + Objects.hashCode(this.latitude);
        hash = 31 * hash + Objects.hashCode(this.longitude);
        hash = 31 * hash + this.status_ocorrencia;
        hash = 31 * hash + Objects.hashCode(this.data_termino);
        hash = 31 * hash + Objects.hashCode(this.data_criacao);
        hash = 31 * hash + Objects.hashCode(this.observacao);
        hash = 31 * hash + Objects.hashCode(this.nome_responsavel);
        hash = 31 * hash + Objects.hashCode(this.ocorrenciasEventoFotos);
        hash = 31 * hash + Objects.hashCode(this.empresas);
        hash = 31 * hash + Objects.hashCode(this.repositores);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OcorrenciasEvento other = (OcorrenciasEvento) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.eventos, other.eventos)) {
            return false;
        }
        if (!Objects.equals(this.data_inicio, other.data_inicio)) {
            return false;
        }
        if (!Objects.equals(this.data_transmissao, other.data_transmissao)) {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        if (this.status_ocorrencia != other.status_ocorrencia) {
            return false;
        }
        if (!Objects.equals(this.data_termino, other.data_termino)) {
            return false;
        }
        if (!Objects.equals(this.data_criacao, other.data_criacao)) {
            return false;
        }
        if (!Objects.equals(this.observacao, other.observacao)) {
            return false;
        }
        if (!Objects.equals(this.nome_responsavel, other.nome_responsavel)) {
            return false;
        }
        if (!Objects.equals(this.ocorrenciasEventoFotos, other.ocorrenciasEventoFotos)) {
            return false;
        }
        if (!Objects.equals(this.empresas, other.empresas)) {
            return false;
        }
        if (!Objects.equals(this.repositores, other.repositores)) {
            return false;
        }
        return true;
    }
}
