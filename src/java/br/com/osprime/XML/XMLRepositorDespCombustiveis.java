/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.XML;

import br.com.orasystems.Modelo.Empresas;
import br.com.orasystems.Modelo.ListaErros;
import br.com.orasystems.Modelo.Repositores;
import br.com.osprime.Modelo.RepositorDespCombustiveis;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fernando
 */
@XmlRootElement(name = "DESPCOMBUSTIVELREPOSITOR")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLRepositorDespCombustiveis {

    private Empresas empresas;
    private Repositores repositores;
    @XmlElement(name = "listaDespCombutivel")
    private List<RepositorDespCombustiveis> listaRepositorDespCombustiveis;
    private ListaErros listaErros;

    public XMLRepositorDespCombustiveis() {
        this.empresas = new Empresas();
        this.repositores = new Repositores();
        this.listaRepositorDespCombustiveis = new ArrayList<>();
        this.listaErros = new ListaErros();
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

    public List<RepositorDespCombustiveis> getListaRepositorDespCombustiveis() {
        return listaRepositorDespCombustiveis;
    }

    public void setListaRepositorDespCombustiveis(List<RepositorDespCombustiveis> listaRepositorDespCombustiveis) {
        this.listaRepositorDespCombustiveis = listaRepositorDespCombustiveis;
    }

    public ListaErros getListaErros() {
        return listaErros;
    }

    public void setListaErros(ListaErros listaErros) {
        this.listaErros = listaErros;
    }
}
