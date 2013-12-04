/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.XML;

import br.com.orasystems.Modelo.Empresas;
import br.com.orasystems.Modelo.ListaErros;
import br.com.orasystems.Modelo.Repositores;
import br.com.osprime.Modelo.ClientesReposicao;
import br.com.osprime.Modelo.RotaReposicao;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fernando nome criado pelo meu nobre amigo Alan, ele e muito criativo.
 *
 */
@XmlRootElement(name = "CargaFull")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLCargaFull {

    private Empresas empresas;
    private Repositores repositores;
    private List<RotaReposicao> listaRotaReposicao;
    private ListaErros listaErros;
    private List<ClientesReposicao> listaClientesReposicao;

    public XMLCargaFull() {
        this.empresas = new Empresas();
        this.repositores = new Repositores();
        this.listaRotaReposicao = new ArrayList<>();
        this.listaErros = new ListaErros();
        this.listaClientesReposicao = new ArrayList<>();
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

    public List<RotaReposicao> getListaRotaReposicao() {
        return listaRotaReposicao;
    }

    public void setListaRotaReposicao(List<RotaReposicao> listaRotaReposicao) {
        this.listaRotaReposicao = listaRotaReposicao;
    }

    public ListaErros getListaErros() {
        return listaErros;
    }

    public void setListaErros(ListaErros listaErros) {
        this.listaErros = listaErros;
    }

    public List<ClientesReposicao> getListaClientesReposicao() {
        return listaClientesReposicao;
    }

    public void setListaClientesReposicao(List<ClientesReposicao> listaClientesReposicao) {
        this.listaClientesReposicao = listaClientesReposicao;
    }
}
