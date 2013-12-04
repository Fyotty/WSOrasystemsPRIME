/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.Modelo;

import br.com.orasystems.Modelo.Empresas;
import java.util.Objects;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author fernando
 */
//@XmlRootElement(name = "ROTAREPOSICAO")
@XmlType(propOrder = {"id", "codigo", "segunda", "terca", "quarta", "quinta",
                      "sexta", "sabado", "domingo", "sequencia", "descricao"})
public class RotaReposicao {

    private int id;
    private Empresas empresas;
    private int codigo;
    private String segunda;
    private String terca;
    private String quarta;
    private String quinta;
    private String sexta;
    private String sabado;
    private String domingo;
    private int sequencia;
    private String descricao;

    public RotaReposicao() {
        this.empresas = new Empresas();
    }

    //@XmlTransient
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlTransient
    public Empresas getEmpresas() {
        return empresas;
    }

    public void setEmpresas(Empresas empresas) {
        this.empresas = empresas;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getSegunda() {
        return segunda;
    }

    public void setSegunda(String segunda) {
        this.segunda = segunda;
    }

    public String getTerca() {
        return terca;
    }

    public void setTerca(String terca) {
        this.terca = terca;
    }

    public String getQuarta() {
        return quarta;
    }

    public void setQuarta(String quarta) {
        this.quarta = quarta;
    }

    public String getQuinta() {
        return quinta;
    }

    public void setQuinta(String quinta) {
        this.quinta = quinta;
    }

    public String getSexta() {
        return sexta;
    }

    public void setSexta(String sexta) {
        this.sexta = sexta;
    }

    public String getSabado() {
        return sabado;
    }

    public void setSabado(String sabado) {
        this.sabado = sabado;
    }

    public String getDomingo() {
        return domingo;
    }

    public void setDomingo(String domingo) {
        this.domingo = domingo;
    }

    public int getSequencia() {
        return sequencia;
    }

    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.empresas);
        hash = 29 * hash + this.codigo;
        hash = 29 * hash + Objects.hashCode(this.segunda);
        hash = 29 * hash + Objects.hashCode(this.terca);
        hash = 29 * hash + Objects.hashCode(this.quarta);
        hash = 29 * hash + Objects.hashCode(this.quinta);
        hash = 29 * hash + Objects.hashCode(this.sexta);
        hash = 29 * hash + Objects.hashCode(this.sabado);
        hash = 29 * hash + Objects.hashCode(this.domingo);
        hash = 29 * hash + this.sequencia;
        hash = 29 * hash + Objects.hashCode(this.descricao);
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
        final RotaReposicao other = (RotaReposicao) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.empresas, other.empresas)) {
            return false;
        }
        if (this.codigo != other.codigo) {
            return false;
        }
        if (!Objects.equals(this.segunda, other.segunda)) {
            return false;
        }
        if (!Objects.equals(this.terca, other.terca)) {
            return false;
        }
        if (!Objects.equals(this.quarta, other.quarta)) {
            return false;
        }
        if (!Objects.equals(this.quinta, other.quinta)) {
            return false;
        }
        if (!Objects.equals(this.sexta, other.sexta)) {
            return false;
        }
        if (!Objects.equals(this.sabado, other.sabado)) {
            return false;
        }
        if (!Objects.equals(this.domingo, other.domingo)) {
            return false;
        }
        if (this.sequencia != other.sequencia) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        return true;
    }
}
