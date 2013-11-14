/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.CTR;

import br.com.osprime.DAO.UltimaCompraReposicaoDAO;
import br.com.osprime.DAO.UltimaCompraReposicaoDAOImp;
import br.com.osprime.Modelo.UltimaCompraReposicao;

/**
 *
 * @author fernando
 */
public class UltimaCompraReposicaoCTR implements UltimaCompraReposicaoDAO {
    
    UltimaCompraReposicaoDAOImp dao = new UltimaCompraReposicaoDAOImp();

    @Override
    public UltimaCompraReposicao gravaUltimaCompraReposicao(UltimaCompraReposicao ucr) {
        return dao.gravaUltimaCompraReposicao(ucr);
    }

    @Override
    public void excluiUltimaCompraReposicao(UltimaCompraReposicao ucr) {
        dao.excluiUltimaCompraReposicao(ucr);
    }
    
}
