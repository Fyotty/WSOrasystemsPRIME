/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.Modelo;

import br.com.orasystems.Modelo.Empresas;
import br.com.orasystems.Utilitarios.DateAdapter;
import com.google.gson.annotations.SerializedName;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author fernando
 */
@XmlRootElement(name = "CLIENTES")
@XmlType(propOrder = {"id", "codigo", "razao_social", "nome_fantasia", "cnpj",
    "iestad", "contato", "telefone", "endereco", "bairro",
    "cidade", "cep", "estado", "observacao", "email", "dt_ultima_compra",
    "listaUltimaCompraReposicao", "listaRepositores", "listaEventos"})
public class ClientesReposicao {

    private int id;
    @SerializedName("razaoSocial")
    private String razao_social; //ok
    @SerializedName("nomeFantasia")
    private String nome_fantasia; //ok
    private String cnpj; //ok
    private String iestad; //ok
    @SerializedName("ultimaCompra")
    private Date dt_ultima_compra; //ok
    private String contato; //ok
    private String telefone; //ok
    private String endereco; //ok
    private String bairro; //ok
    private String cidade; //ok
    private String cep; //ok
    @SerializedName("uf")
    private String estado; //ok
    private String email; //ok
    @SerializedName("obs")
    private String observacao; //ok
    private transient Empresas empresas;
    private int codigo; //ok
    //@SerializedName("rota")
    //private RotaReposicao rr;
    private transient List<ClientesRepositor> listaRepositores; //ok
    private List<UltimaCompraReposicao> listaUltimaCompraReposicao;
    private List<Eventos> listaEventos;

    public ClientesReposicao() {
        this.empresas = new Empresas();
        //this.rr = new RotaReposicao();
        this.listaRepositores = new ArrayList<>();
        this.listaUltimaCompraReposicao = new ArrayList<>();
        this.listaEventos = new ArrayList<>();
    }

    //@XmlTransient
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRazao_social() {
        return razao_social;
    }

    public void setRazao_social(String razao_social) {
        this.razao_social = razao_social;
    }

    public String getNome_fantasia() {
        return nome_fantasia;
    }

    public void setNome_fantasia(String nome_fantasia) {
        this.nome_fantasia = nome_fantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getIestad() {
        return iestad;
    }

    public void setIestad(String iestad) {
        this.iestad = iestad;
    }

    public Date getDt_ultima_compra() {
        return dt_ultima_compra;
    }

    @XmlElement(name = "dt_ultima_compra", required = false)
    @XmlJavaTypeAdapter(DateAdapter.class)
    public void setDt_ultima_compra(Date dt_ultima_compra) {
        this.dt_ultima_compra = dt_ultima_compra;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
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

    @XmlElement(name = "rota_reposicao", required = true)
    public List<ClientesRepositor> getListaRepositores() {
        return listaRepositores;
    }

    public void setListaRepositores(List<ClientesRepositor> listaRepositores) {
        this.listaRepositores = listaRepositores;
    }

    //@XmlTransient
    public List<UltimaCompraReposicao> getListaUltimaCompraReposicao() {
        return listaUltimaCompraReposicao;
    }

    public void setListaUltimaCompraReposicao(List<UltimaCompraReposicao> listaUltimaCompraReposicao) {
        this.listaUltimaCompraReposicao = listaUltimaCompraReposicao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Eventos> getListaEventos() {
        return listaEventos;
    }

    public void setListaEventos(List<Eventos> listaEventos) {
        this.listaEventos = listaEventos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.id;
        hash = 83 * hash + Objects.hashCode(this.razao_social);
        hash = 83 * hash + Objects.hashCode(this.nome_fantasia);
        hash = 83 * hash + Objects.hashCode(this.cnpj);
        hash = 83 * hash + Objects.hashCode(this.iestad);
        hash = 83 * hash + Objects.hashCode(this.dt_ultima_compra);
        hash = 83 * hash + Objects.hashCode(this.contato);
        hash = 83 * hash + Objects.hashCode(this.telefone);
        hash = 83 * hash + Objects.hashCode(this.endereco);
        hash = 83 * hash + Objects.hashCode(this.bairro);
        hash = 83 * hash + Objects.hashCode(this.cidade);
        hash = 83 * hash + Objects.hashCode(this.cep);
        hash = 83 * hash + Objects.hashCode(this.estado);
        hash = 83 * hash + Objects.hashCode(this.email);
        hash = 83 * hash + Objects.hashCode(this.observacao);
        hash = 83 * hash + Objects.hashCode(this.empresas);
        hash = 83 * hash + this.codigo;
        hash = 83 * hash + Objects.hashCode(this.listaRepositores);
        hash = 83 * hash + Objects.hashCode(this.listaUltimaCompraReposicao);
        hash = 83 * hash + Objects.hashCode(this.listaEventos);
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
        final ClientesReposicao other = (ClientesReposicao) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.razao_social, other.razao_social)) {
            return false;
        }
        if (!Objects.equals(this.nome_fantasia, other.nome_fantasia)) {
            return false;
        }
        if (!Objects.equals(this.cnpj, other.cnpj)) {
            return false;
        }
        if (!Objects.equals(this.iestad, other.iestad)) {
            return false;
        }
        if (!Objects.equals(this.dt_ultima_compra, other.dt_ultima_compra)) {
            return false;
        }
        if (!Objects.equals(this.contato, other.contato)) {
            return false;
        }
        if (!Objects.equals(this.telefone, other.telefone)) {
            return false;
        }
        if (!Objects.equals(this.endereco, other.endereco)) {
            return false;
        }
        if (!Objects.equals(this.bairro, other.bairro)) {
            return false;
        }
        if (!Objects.equals(this.cidade, other.cidade)) {
            return false;
        }
        if (!Objects.equals(this.cep, other.cep)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.observacao, other.observacao)) {
            return false;
        }
        if (!Objects.equals(this.empresas, other.empresas)) {
            return false;
        }
        if (this.codigo != other.codigo) {
            return false;
        }
        if (!Objects.equals(this.listaRepositores, other.listaRepositores)) {
            return false;
        }
        if (!Objects.equals(this.listaUltimaCompraReposicao, other.listaUltimaCompraReposicao)) {
            return false;
        }
        if (!Objects.equals(this.listaEventos, other.listaEventos)) {
            return false;
        }
        return true;
    }
}
