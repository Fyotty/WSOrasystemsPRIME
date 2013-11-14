/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.DAO;

import br.com.osprime.Modelo.RotaReposicao;

/**
 *
 * @author fernando
 */
public interface RotaReposicaoDAO {
    
    public RotaReposicao consultaRotaReposicao(RotaReposicao rr);
    
    public RotaReposicao gravaRotaReposicao(RotaReposicao rr);
    
    public RotaReposicao atualizaRotaReposicao(RotaReposicao rr);
    
    public RotaReposicao existeRotaReposicao(RotaReposicao rr);
    
}
