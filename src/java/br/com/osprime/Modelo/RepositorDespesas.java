/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.Modelo;

import br.com.orasystems.Modelo.Empresas;
import br.com.orasystems.Modelo.Repositores;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

/**
 *
 * @author fernando
 */
public class RepositorDespesas {

    private long id;
    private Empresas empresas;
    private Repositores repositores;
    private Date data;
    private String descricao;
    private Double valor;
    private Timestamp data_hora_envio;
    private String observacao;

    public RepositorDespesas() {
        this.empresas = new Empresas();
        this.repositores = new Repositores();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Empresas getEmpresas() {
        return empresas;
    }

    public void setEmpresas(Empresas empresas) {
        this.empresas = empresas;
    }

    public Repositores getRepositores() {
        return repositores;
    }

    public void setRepositores(Repositores repositores) {
        this.repositores = repositores;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Timestamp getData_hora_envio() {
        return data_hora_envio;
    }

    public void setData_hora_envio(Timestamp data_hora_envio) {
        this.data_hora_envio = data_hora_envio;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 37 * hash + Objects.hashCode(this.empresas);
        hash = 37 * hash + Objects.hashCode(this.repositores);
        hash = 37 * hash + Objects.hashCode(this.data);
        hash = 37 * hash + Objects.hashCode(this.descricao);
        hash = 37 * hash + Objects.hashCode(this.valor);
        hash = 37 * hash + Objects.hashCode(this.data_hora_envio);
        hash = 37 * hash + Objects.hashCode(this.observacao);
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
        final RepositorDespesas other = (RepositorDespesas) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.empresas, other.empresas)) {
            return false;
        }
        if (!Objects.equals(this.repositores, other.repositores)) {
            return false;
        }
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.valor, other.valor)) {
            return false;
        }
        if (!Objects.equals(this.data_hora_envio, other.data_hora_envio)) {
            return false;
        }
        if (!Objects.equals(this.observacao, other.observacao)) {
            return false;
        }
        return true;
    }
}
