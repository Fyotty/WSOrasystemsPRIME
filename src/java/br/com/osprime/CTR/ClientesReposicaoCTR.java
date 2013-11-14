/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.CTR;

import br.com.osprime.DAO.ClientesReposicaoDAO;
import br.com.osprime.DAO.ClientesReposicaoDAOImp;
import br.com.osprime.Modelo.ClientesReposicao;

/**
 *
 * @author fernando
 */
public class ClientesReposicaoCTR implements ClientesReposicaoDAO {
    
    ClientesReposicaoDAOImp dao = new ClientesReposicaoDAOImp();

    @Override
    public ClientesReposicao consultaClientesReposicao(ClientesReposicao cr) {
        return dao.consultaClientesReposicao(cr);
    }

    @Override
    public ClientesReposicao gravaClientesReposicao(ClientesReposicao cr) {
        return dao.gravaClientesReposicao(cr);
    }

    @Override
    public ClientesReposicao atualizaClientesReposicao(ClientesReposicao cr) {
        return dao.atualizaClientesReposicao(cr);
    }

    @Override
    public ClientesReposicao existeClientesReposicao(ClientesReposicao cr) {
        return dao.existeClientesReposicao(cr);
    }
    
}
