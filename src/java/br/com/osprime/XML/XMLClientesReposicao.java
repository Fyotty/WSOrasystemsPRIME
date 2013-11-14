/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.XML;

import br.com.orasystems.Modelo.Empresas;
import br.com.orasystems.Modelo.ListaErros;
import br.com.orasystems.Modelo.Repositores;
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
@XmlRootElement(name = "CLIENTESREPOSICAO")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLClientesReposicao {
    
    private Empresas empresas;
    @XmlElement(name = "lista")
    private List<ClientesReposicao> listaClientesReposicaos;
    private ListaErros listaErros;

    public XMLClientesReposicao() {
        this.empresas = new Empresas();
        this.listaClientesReposicaos = new ArrayList<>();
        this.listaErros = new ListaErros();
    }

    public Empresas getEmpresas() {
        return empresas;
    }

    public void setEmpresas(Empresas empresas) {
        this.empresas = empresas;
    }

    public List<ClientesReposicao> getListaClientesReposicaos() {
        return listaClientesReposicaos;
    }

    public void setListaClientesReposicaos(List<ClientesReposicao> listaClientesReposicaos) {
        this.listaClientesReposicaos = listaClientesReposicaos;
    }

    public ListaErros getListaErros() {
        return listaErros;
    }

    public void setListaErros(ListaErros listaErros) {
        this.listaErros = listaErros;
    }
}
