/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.CTR;

import br.com.osprime.DAO.RotaReposicaoDAO;
import br.com.osprime.DAO.RotaReposicaoDAOImp;
import br.com.osprime.Modelo.RotaReposicao;
import br.com.osprime.XML.XMLCargaFull;

/**
 *
 * @author fernando
 */
public class RotaReposicaoCTR implements RotaReposicaoDAO{
    
    RotaReposicaoDAOImp dao = new RotaReposicaoDAOImp();

    @Override
    public RotaReposicao consultaRotaReposicao(RotaReposicao rr) {
        return dao.consultaRotaReposicao(rr);
    }

    @Override
    public RotaReposicao gravaRotaReposicao(RotaReposicao rr) {
        return dao.gravaRotaReposicao(rr);
    }

    @Override
    public RotaReposicao atualizaRotaReposicao(RotaReposicao rr) {
        return dao.atualizaRotaReposicao(rr);
    }

    @Override
    public RotaReposicao existeRotaReposicao(RotaReposicao rr) {
        return dao.existeRotaReposicao(rr);
    }

    @Override
    public XMLCargaFull listaRotaReposicao(XMLCargaFull xMLCargaFull) {
        return dao.listaRotaReposicao(xMLCargaFull);
    }
    
}
