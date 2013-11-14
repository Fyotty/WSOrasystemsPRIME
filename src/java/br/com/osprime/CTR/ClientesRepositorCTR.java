/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.CTR;

import br.com.osprime.DAO.ClientesRepositorDAO;
import br.com.osprime.DAO.ClientesRepositorDAOImp;
import br.com.osprime.Modelo.ClientesRepositor;

/**
 *
 * @author fernando
 */
public class ClientesRepositorCTR implements ClientesRepositorDAO {
    
    ClientesRepositorDAOImp dao = new ClientesRepositorDAOImp();

    @Override
    public ClientesRepositor gravaClientesRepositor(ClientesRepositor cr) {
        return dao.gravaClientesRepositor(cr);
    }

    @Override
    public ClientesRepositor existeClientesRepositor(ClientesRepositor cr) {
        return dao.existeClientesRepositor(cr);
    }

    @Override
    public void excluiClientesRepositor(ClientesRepositor cr) {
        dao.excluiClientesRepositor(cr);
    }
    
}
