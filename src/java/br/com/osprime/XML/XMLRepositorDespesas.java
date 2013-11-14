/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.XML;

import br.com.orasystems.Modelo.Empresas;
import br.com.orasystems.Modelo.ListaErros;
import br.com.orasystems.Modelo.Repositores;
import br.com.osprime.Modelo.RepositorDespesas;
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
@XmlRootElement(name = "DESPESASREPOSITOR")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLRepositorDespesas {

    private Empresas empresas;
    private Repositores repositores;
    @XmlElement(name = "listaDespesas")
    private List<RepositorDespesas> listaRepositorDespesas;
    private ListaErros listaErros;

    public XMLRepositorDespesas() {
        this.empresas = new Empresas();
        this.repositores = new Repositores();
        this.listaRepositorDespesas = new ArrayList<>();
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

    public List<RepositorDespesas> getListaRepositorDespesas() {
        return listaRepositorDespesas;
    }

    public void setListaRepositorDespesas(List<RepositorDespesas> listaRepositorDespesas) {
        this.listaRepositorDespesas = listaRepositorDespesas;
    }

    public ListaErros getListaErros() {
        return listaErros;
    }

    public void setListaErros(ListaErros listaErros) {
        this.listaErros = listaErros;
    }
}
