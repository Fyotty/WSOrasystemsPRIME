/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.DAO;

import br.com.osprime.Modelo.ClientesRepositor;

/**
 *
 * @author fernando
 */
public interface ClientesRepositorDAO {
    
    public ClientesRepositor gravaClientesRepositor(ClientesRepositor cr);
    
    public ClientesRepositor existeClientesRepositor(ClientesRepositor cr);
    
    public void excluiClientesRepositor(ClientesRepositor cr);
    
}
