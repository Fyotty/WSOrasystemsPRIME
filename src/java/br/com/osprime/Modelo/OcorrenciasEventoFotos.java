/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.Modelo;

import br.com.orasystems.Utilitarios.DateTimeAdapter;
import java.sql.Timestamp;
import java.util.Arrays;
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
@XmlRootElement(name = "FOTOS")
@XmlType(propOrder = {"foto_tipo", "data_captura", "observacao", "imagem"})
public class OcorrenciasEventoFotos {

    private long id;
    private OcorrenciasEvento ocorrenciasEvento;
    private String caminho_foto;
    private int foto_tipo;
    private Timestamp data_captura;
    private String observacao;
    private byte[] imagem;

    public OcorrenciasEventoFotos() {
    }

    @XmlTransient
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @XmlTransient
    public OcorrenciasEvento getOcorrenciasEvento() {
        return ocorrenciasEvento;
    }

    public void setOcorrenciasEvento(OcorrenciasEvento ocorrenciasEvento) {
        this.ocorrenciasEvento = ocorrenciasEvento;
    }

    @XmlTransient
    public String getCaminho_foto() {
        return caminho_foto;
    }

    public void setCaminho_foto(String caminho_foto) {
        this.caminho_foto = caminho_foto;
    }

    @XmlElement(name = "tipoFoto", required = false)
    public int getFoto_tipo() {
        return foto_tipo;
    }

    public void setFoto_tipo(int foto_tipo) {
        this.foto_tipo = foto_tipo;
    }

    @XmlElement(name = "dataCaptura", required = false)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    public Timestamp getData_captura() {
        return data_captura;
    }

    public void setData_captura(Timestamp data_captura) {
        this.data_captura = data_captura;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 67 * hash + Objects.hashCode(this.ocorrenciasEvento);
        hash = 67 * hash + Objects.hashCode(this.caminho_foto);
        hash = 67 * hash + this.foto_tipo;
        hash = 67 * hash + Objects.hashCode(this.data_captura);
        hash = 67 * hash + Objects.hashCode(this.observacao);
        hash = 67 * hash + Arrays.hashCode(this.imagem);
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
        final OcorrenciasEventoFotos other = (OcorrenciasEventoFotos) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.ocorrenciasEvento, other.ocorrenciasEvento)) {
            return false;
        }
        if (!Objects.equals(this.caminho_foto, other.caminho_foto)) {
            return false;
        }
        if (this.foto_tipo != other.foto_tipo) {
            return false;
        }
        if (!Objects.equals(this.data_captura, other.data_captura)) {
            return false;
        }
        if (!Objects.equals(this.observacao, other.observacao)) {
            return false;
        }
        if (!Arrays.equals(this.imagem, other.imagem)) {
            return false;
        }
        return true;
    }
}
