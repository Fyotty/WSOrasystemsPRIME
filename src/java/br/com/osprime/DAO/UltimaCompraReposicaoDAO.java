/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.DAO;

import br.com.osprime.Modelo.UltimaCompraReposicao;

/**
 *
 * @author fernando
 */
public interface UltimaCompraReposicaoDAO {
    
    public UltimaCompraReposicao gravaUltimaCompraReposicao(UltimaCompraReposicao ucr);
    
    public void excluiUltimaCompraReposicao(UltimaCompraReposicao ucr);    
}
