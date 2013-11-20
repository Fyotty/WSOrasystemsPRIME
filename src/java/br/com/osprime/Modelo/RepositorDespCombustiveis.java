/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.Modelo;

import br.com.orasystems.Modelo.Empresas;
import br.com.orasystems.Modelo.Repositores;
import java.sql.Date;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author fernando
 */
@XmlType(propOrder = {"repositores", "empresas", "data", "dia_semana", "local_partida",
    "local_chegada", "km_inicial", "km_final", "total_km", "media_veiculo",
    "consumo_dia", "preco_combustivel", "subtotal", "veiculo", "placa"})
public class RepositorDespCombustiveis {

    private long id;
    private Date data;
    private String dia_semana;
    private String local_partida;
    private String local_chegada;
    private double km_inicial;
    private double km_final;
    private double total_km;
    private double media_veiculo;
    private double consumo_dia;
    private double preco_combustivel;
    private double subtotal;
    private String veiculo;
    private String placa;
    private Empresas empresas;
    private Repositores repositores;

    public RepositorDespCombustiveis() {
        this.empresas = new Empresas();
        this.repositores = new Repositores();
    }

    @XmlTransient
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDia_semana() {
        return dia_semana;
    }

    public void setDia_semana(String dia_semana) {
        this.dia_semana = dia_semana;
    }

    public String getLocal_partida() {
        return local_partida;
    }

    public void setLocal_partida(String local_partida) {
        this.local_partida = local_partida;
    }

    public String getLocal_chegada() {
        return local_chegada;
    }

    public void setLocal_chegada(String local_chegada) {
        this.local_chegada = local_chegada;
    }

    public double getKm_inicial() {
        return km_inicial;
    }

    public void setKm_inicial(double km_inicial) {
        this.km_inicial = km_inicial;
    }

    public double getKm_final() {
        return km_final;
    }

    public void setKm_final(double km_final) {
        this.km_final = km_final;
    }

    public double getTotal_km() {
        return total_km;
    }

    public void setTotal_km(double total_km) {
        this.total_km = total_km;
    }

    public double getMedia_veiculo() {
        return media_veiculo;
    }

    public void setMedia_veiculo(double media_veiculo) {
        this.media_veiculo = media_veiculo;
    }

    public double getConsumo_dia() {
        return consumo_dia;
    }

    public void setConsumo_dia(double consumo_dia) {
        this.consumo_dia = consumo_dia;
    }

    public double getPreco_combustivel() {
        return preco_combustivel;
    }

    public void setPreco_combustivel(double preco_combustivel) {
        this.preco_combustivel = preco_combustivel;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
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
}
