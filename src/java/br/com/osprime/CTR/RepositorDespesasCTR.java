/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.CTR;

import br.com.osprime.DAO.RepositorDespesasDAO;
import br.com.osprime.DAO.RepositorDespesasDAOImp;
import br.com.osprime.Modelo.RepositorDespesas;

/**
 *
 * @author fernando
 */
public class RepositorDespesasCTR implements RepositorDespesasDAO{

    @Override
    public RepositorDespesas gravaRepositorDespesas(RepositorDespesas rd) {
        RepositorDespesasDAOImp dao = new RepositorDespesasDAOImp();
        return dao.gravaRepositorDespesas(rd);
    }
    
}
