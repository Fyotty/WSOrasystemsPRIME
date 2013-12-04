/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.DAO;

import br.com.osprime.Modelo.ClientesReposicao;
import br.com.osprime.Modelo.UltimaCompraReposicao;
import java.util.List;

/**
 *
 * @author fernando
 */
public interface UltimaCompraReposicaoDAO {
    
    public UltimaCompraReposicao gravaUltimaCompraReposicao(UltimaCompraReposicao ucr);
    
    public void excluiUltimaCompraReposicao(UltimaCompraReposicao ucr);    
    
    public List<UltimaCompraReposicao> listaUltimaCompraReposicao(ClientesReposicao cr);
}
