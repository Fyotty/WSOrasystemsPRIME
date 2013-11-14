/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.XML;

import br.com.orasystems.Modelo.Empresas;
import br.com.orasystems.Modelo.ListaErros;
import br.com.osprime.Modelo.ClientesReposicao;
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
@XmlRootElement(name = "ULTIMACOMPRAREPOSICAO")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLUltimaCompraReposicao {

    private Empresas empresas;
    @XmlElement(name = "lista")
    private List<ClientesReposicao> listaClientesReposicao;
    private ListaErros listaErros;

    public XMLUltimaCompraReposicao() {
        this.empresas = new Empresas();
        this.listaClientesReposicao = new ArrayList<>();
        this.listaErros = new ListaErros();
    }

    public Empresas getEmpresas() {
        return empresas;
    }

    public void setEmpresas(Empresas empresas) {
        this.empresas = empresas;
    }

    public List<ClientesReposicao> getListaClientesReposicao() {
        return listaClientesReposicao;
    }

    public void setListaClientesReposicao(List<ClientesReposicao> listaClientesReposicao) {
        this.listaClientesReposicao = listaClientesReposicao;
    }

    public ListaErros getListaErros() {
        return listaErros;
    }

    public void setListaErros(ListaErros listaErros) {
        this.listaErros = listaErros;
    }
}
