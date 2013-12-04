/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.CTR;

import br.com.osprime.DAO.ClientesRepositorDAO;
import br.com.osprime.DAO.ClientesRepositorDAOImp;
import br.com.osprime.Modelo.ClientesRepositor;
import br.com.osprime.Modelo.RotaReposicao;

/**
 *
 * @author fernando
 */
public class ClientesRepositorCTR implements ClientesRepositorDAO {
    
    ClientesRepositorDAOImp dao = new ClientesRepositorDAOImp();

    @Override
    public ClientesRepositor gravaClientesRepositor(ClientesRepositor cr, RotaReposicao rr) {
        return dao.gravaClientesRepositor(cr, rr);
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
