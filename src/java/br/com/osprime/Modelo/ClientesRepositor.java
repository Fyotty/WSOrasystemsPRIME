/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.Modelo;

import br.com.orasystems.Modelo.Empresas;
import br.com.orasystems.Modelo.Repositores;
import java.util.Objects;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author fernando
 */
@XmlType(propOrder = {"repositores", "operacao"})
public class ClientesRepositor {
    
    private int id;
    private Empresas empresas;
    private ClientesReposicao clientesReposicao;
    private Repositores repositores;
    private String operacao;

    public ClientesRepositor() {
        this.empresas = new Empresas();
        this.clientesReposicao = new ClientesReposicao();
        this.repositores = new Repositores();
    }

    @XmlTransient
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

    @XmlTransient
    public ClientesReposicao getClientesReposicao() {
        return clientesReposicao;
    }

    public void setClientesReposicao(ClientesReposicao clientesReposicao) {
        this.clientesReposicao = clientesReposicao;
    }

    public Repositores getRepositores() {
        return repositores;
    }

    public void setRepositores(Repositores repositores) {
        this.repositores = repositores;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
        hash = 97 * hash + Objects.hashCode(this.empresas);
        hash = 97 * hash + Objects.hashCode(this.clientesReposicao);
        hash = 97 * hash + Objects.hashCode(this.repositores);
        hash = 97 * hash + Objects.hashCode(this.operacao);
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
        final ClientesRepositor other = (ClientesRepositor) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.empresas, other.empresas)) {
            return false;
        }
        if (!Objects.equals(this.clientesReposicao, other.clientesReposicao)) {
            return false;
        }
        if (!Objects.equals(this.repositores, other.repositores)) {
            return false;
        }
        if (!Objects.equals(this.operacao, other.operacao)) {
            return false;
        }
        return true;
    }

}
