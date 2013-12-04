/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.CTR;

import br.com.osprime.DAO.OcorrenciasEventoFotosDAO;
import br.com.osprime.DAO.OcorrenciasEventoFotosDAOImp;
import br.com.osprime.Modelo.OcorrenciasEventoFotos;

/**
 *
 * @author fernando
 */
public class OcorrenciasEventoFotosCTR implements OcorrenciasEventoFotosDAO {

    @Override
    public OcorrenciasEventoFotos gravaOcorrenciasEventoFotos(OcorrenciasEventoFotos ocorrenciasEventoFotos) {
        OcorrenciasEventoFotosDAOImp dao = new OcorrenciasEventoFotosDAOImp();
        return dao.gravaOcorrenciasEventoFotos(ocorrenciasEventoFotos);
    }
}
