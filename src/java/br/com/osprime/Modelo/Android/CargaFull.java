/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.Modelo.Android;

import br.com.osprime.Modelo.ClientesReposicao;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 *
 * @author fernando
 */
public class CargaFull {

    @SerializedName("clientes")
    private List<ClientesReposicao> listaClientesReposicaos;
    
}
