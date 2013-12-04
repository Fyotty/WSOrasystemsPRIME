/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.DAO;

import br.com.osprime.Modelo.ClientesRepositor;
import br.com.osprime.Modelo.RotaReposicao;

/**
 *
 * @author fernando
 */
public interface ClientesRepositorDAO {
    
    public ClientesRepositor gravaClientesRepositor(ClientesRepositor cr, RotaReposicao rr);
    
    public ClientesRepositor existeClientesRepositor(ClientesRepositor cr);
    
    public void excluiClientesRepositor(ClientesRepositor cr);
    
}
