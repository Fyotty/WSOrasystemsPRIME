/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.Modelo;

import br.com.orasystems.Modelo.Empresas;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author fernando
 */
@XmlRootElement(name = "ULTIMACOMPRA")
@XmlType(propOrder = {"codigo_produto", "descricao_produto", "unidade", "quantidade"})
public class UltimaCompraReposicao {

    private transient long id;
    private transient Empresas empresas;
    private transient ClientesReposicao clientesReposicao;
    private long codigo_produto;
    private String descricao_produto;
    private String unidade;
    private double quantidade;

    public UltimaCompraReposicao() {
        this.empresas = new Empresas();
        this.clientesReposicao = new ClientesReposicao();
    }

    @XmlTransient
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @XmlTransient
    public Empresas getEmpresas() {
        return empresas;
    }

    public void setEmpresas(Empresas empresas) {
        this.empresas = empresas;
    }

    @XmlTransient
    public ClientesReposicao getClientesReposicao() {
        return clientesReposicao;
    }

    public void setClientesReposicao(ClientesReposicao clientesReposicao) {
        this.clientesReposicao = clientesReposicao;
    }

    public long getCodigo_produto() {
        return codigo_produto;
    }

    public void setCodigo_produto(long codigo_produto) {
        this.codigo_produto = codigo_produto;
    }

    public String getDescricao_produto() {
        return descricao_produto;
    }

    public void setDescricao_produto(String descricao_produto) {
        this.descricao_produto = descricao_produto;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 29 * hash + Objects.hashCode(this.empresas);
        hash = 29 * hash + Objects.hashCode(this.clientesReposicao);
        hash = 29 * hash + (int) (this.codigo_produto ^ (this.codigo_produto >>> 32));
        hash = 29 * hash + Objects.hashCode(this.descricao_produto);
        hash = 29 * hash + Objects.hashCode(this.unidade);
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.quantidade) ^ (Double.doubleToLongBits(this.quantidade) >>> 32));
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
        final UltimaCompraReposicao other = (UltimaCompraReposicao) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.empresas, other.empresas)) {
            return false;
        }
        if (!Objects.equals(this.clientesReposicao, other.clientesReposicao)) {
            return false;
        }
        if (this.codigo_produto != other.codigo_produto) {
            return false;
        }
        if (!Objects.equals(this.descricao_produto, other.descricao_produto)) {
            return false;
        }
        if (!Objects.equals(this.unidade, other.unidade)) {
            return false;
        }
        if (Double.doubleToLongBits(this.quantidade) != Double.doubleToLongBits(other.quantidade)) {
            return false;
        }
        return true;
    }
}
