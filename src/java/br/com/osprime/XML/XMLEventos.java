/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.XML;

import br.com.orasystems.Modelo.Empresas;
import br.com.orasystems.Modelo.ListaErros;
import br.com.orasystems.Modelo.Repositores;
import br.com.osprime.Modelo.Eventos;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fernando
 */
@XmlRootElement(name = "EVENTOS")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLEventos {

    private Empresas empresas;
    private Repositores repositores;
    @XmlElement(name = "listaEventos")
    private List<Eventos> listaEventos;
    private ListaErros listaErros;

    public XMLEventos() {
        this.empresas = new Empresas();
        this.repositores = new Repositores();
        this.listaEventos = new ArrayList<>();
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

    public List<Eventos> getListaEventos() {
        return listaEventos;
    }

    public void setListaEventos(List<Eventos> listaEventos) {
        this.listaEventos = listaEventos;
    }

    public ListaErros getListaErros() {
        return listaErros;
    }

    public void setListaErros(ListaErros listaErros) {
        this.listaErros = listaErros;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.empresas);
        hash = 37 * hash + Objects.hashCode(this.repositores);
        hash = 37 * hash + Objects.hashCode(this.listaEventos);
        hash = 37 * hash + Objects.hashCode(this.listaErros);
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
        final XMLEventos other = (XMLEventos) obj;
        if (!Objects.equals(this.empresas, other.empresas)) {
            return false;
        }
        if (!Objects.equals(this.repositores, other.repositores)) {
            return false;
        }
        if (!Objects.equals(this.listaEventos, other.listaEventos)) {
            return false;
        }
        if (!Objects.equals(this.listaErros, other.listaErros)) {
            return false;
        }
        return true;
    }
}
