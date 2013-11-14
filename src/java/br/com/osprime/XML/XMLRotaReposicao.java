/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.XML;

import br.com.orasystems.Modelo.Empresas;
import br.com.orasystems.Modelo.ListaErros;
import br.com.osprime.Modelo.RotaReposicao;
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
@XmlRootElement(name = "ROTAREPOSICAO")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLRotaReposicao {

    private Empresas empresas;
    @XmlElement(name = "lista")
    private List<RotaReposicao> listaRotaReposicao;
    private ListaErros listaErros;

    public XMLRotaReposicao() {
        this.listaRotaReposicao = new ArrayList<>();
        this.empresas = new Empresas();
        this.listaErros = new ListaErros();
    }

    public Empresas getEmpresas() {
        return empresas;
    }

    public void setEmpresas(Empresas empresas) {
        this.empresas = empresas;
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
}
