/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.Modelo;

import br.com.orasystems.Modelo.Empresas;
import br.com.orasystems.Modelo.Repositores;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author fernando
 */
@XmlType(propOrder = {"repositores", "listaRotaReposicao"})
public class ClientesRepositor {

    private int id;
    private Empresas empresas;
    private ClientesReposicao clientesReposicao;
    private Repositores repositores;
    private List<RotaReposicao> listaRotaReposicao;

    public ClientesRepositor() {
        this.empresas = new Empresas();
        this.clientesReposicao = new ClientesReposicao();
        this.repositores = new Repositores();
        this.listaRotaReposicao = new ArrayList<>();
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

    @XmlElement(name = "repositor", required = true)
    public Repositores getRepositores() {
        return repositores;
    }

    public void setRepositores(Repositores repositores) {
        this.repositores = repositores;
    }

    @XmlElement(name = "rr", required = true)
    public List<RotaReposicao> getListaRotaReposicao() {
        return listaRotaReposicao;
    }

    public void setListaRotaReposicao(List<RotaReposicao> listaRotaReposicao) {
        this.listaRotaReposicao = listaRotaReposicao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.empresas);
        hash = 29 * hash + Objects.hashCode(this.clientesReposicao);
        hash = 29 * hash + Objects.hashCode(this.repositores);
        hash = 29 * hash + Objects.hashCode(this.listaRotaReposicao);
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
        if (!Objects.equals(this.listaRotaReposicao, other.listaRotaReposicao)) {
            return false;
        }
        return true;
    }
}
