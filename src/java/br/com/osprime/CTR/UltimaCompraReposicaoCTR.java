/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.CTR;

import br.com.osprime.DAO.UltimaCompraReposicaoDAO;
import br.com.osprime.DAO.UltimaCompraReposicaoDAOImp;
import br.com.osprime.Modelo.ClientesReposicao;
import br.com.osprime.Modelo.UltimaCompraReposicao;
import java.util.List;

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

    @Override
    public List<UltimaCompraReposicao> listaUltimaCompraReposicao(ClientesReposicao cr) {
        return dao.listaUltimaCompraReposicao(cr);
    }
    
}
