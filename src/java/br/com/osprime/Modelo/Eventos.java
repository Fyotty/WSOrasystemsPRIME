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
@XmlType(propOrder = {"id", "clientesReposicao", "ordem_visita", "nome_evento",
    "status_evento", "qtd_fotos_evento", "codigo", "rotaReposicao"})
public class Eventos {

    private int id;
    private Repositores repositores;
    private ClientesReposicao clientesReposicao;
    private int ordem_visita;
    private String nome_evento;
    private int status_evento;
    private int qtd_fotos_evento;
    private Empresas empresas;
    private int codigo;
    private RotaReposicao rotaReposicao;

    public Eventos() {
        this.repositores = new Repositores();
        this.clientesReposicao = new ClientesReposicao();
        this.empresas = new Empresas();
        this.rotaReposicao = new RotaReposicao();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlTransient
    public Repositores getRepositores() {
        return repositores;
    }

    public void setRepositores(Repositores repositores) {
        this.repositores = repositores;
    }

    public ClientesReposicao getClientesReposicao() {
        return clientesReposicao;
    }

    public void setClientesReposicao(ClientesReposicao clientesReposicao) {
        this.clientesReposicao = clientesReposicao;
    }

    public int getOrdem_visita() {
        return ordem_visita;
    }

    public void setNome_evento(String nome_evento) {
        this.nome_evento = nome_evento;
    }

    public int getStatus_evento() {
        return status_evento;
    }

    public void setStatus_evento(int status_evento) {
        this.status_evento = status_evento;
    }

    public int getQtd_fotos_evento() {
        return qtd_fotos_evento;
    }

    public void setQtd_fotos_evento(int qtd_fotos_evento) {
        this.qtd_fotos_evento = qtd_fotos_evento;
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

    public RotaReposicao getRotaReposicao() {
        return rotaReposicao;
    }

    public void setRotaReposicao(RotaReposicao rotaReposicao) {
        this.rotaReposicao = rotaReposicao;
    }

    public String getNome_evento() {
        return nome_evento;
    }

    public void setOrdem_visita(int ordem_visita) {
        this.ordem_visita = ordem_visita;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.repositores);
        hash = 29 * hash + Objects.hashCode(this.clientesReposicao);
        hash = 29 * hash + this.ordem_visita;
        hash = 29 * hash + Objects.hashCode(this.nome_evento);
        hash = 29 * hash + this.status_evento;
        hash = 29 * hash + this.qtd_fotos_evento;
        hash = 29 * hash + Objects.hashCode(this.empresas);
        hash = 29 * hash + this.codigo;
        hash = 29 * hash + Objects.hashCode(this.rotaReposicao);
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
        final Eventos other = (Eventos) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.repositores, other.repositores)) {
            return false;
        }
        if (!Objects.equals(this.clientesReposicao, other.clientesReposicao)) {
            return false;
        }
        if (this.ordem_visita != other.ordem_visita) {
            return false;
        }
        if (!Objects.equals(this.nome_evento, other.nome_evento)) {
            return false;
        }
        if (this.status_evento != other.status_evento) {
            return false;
        }
        if (this.qtd_fotos_evento != other.qtd_fotos_evento) {
            return false;
        }
        if (!Objects.equals(this.empresas, other.empresas)) {
            return false;
        }
        if (this.codigo != other.codigo) {
            return false;
        }
        if (!Objects.equals(this.rotaReposicao, other.rotaReposicao)) {
            return false;
        }
        return true;
    }
}
