/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.CTR;

import br.com.osprime.DAO.RepositorDespCombustiveisDAO;
import br.com.osprime.DAO.RepositorDespCombustiveisDAOImp;
import br.com.osprime.Modelo.RepositorDespCombustiveis;

/**
 *
 * @author fernando
 */
public class RepositorDespCombustiveisCTR implements RepositorDespCombustiveisDAO {

    @Override
    public RepositorDespCombustiveis gravaRepositorDespCombustiveis(RepositorDespCombustiveis rdc) {
        RepositorDespCombustiveisDAOImp dao = new RepositorDespCombustiveisDAOImp();
        return dao.gravaRepositorDespCombustiveis(rdc);
    }
}
