/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.CTR;

import br.com.osprime.DAO.OcorrenciasEventoDAO;
import br.com.osprime.DAO.OcorrenciasEventoDAOImp;
import br.com.osprime.Modelo.OcorrenciasEvento;

/**
 *
 * @author fernando
 */
public class OcorrenciasEventoCTR implements OcorrenciasEventoDAO{
    OcorrenciasEventoDAOImp dao = new OcorrenciasEventoDAOImp();
    
    @Override
    public OcorrenciasEvento gravaOcorrenciasEvento(OcorrenciasEvento ocorrenciasEvento) {
        return dao.gravaOcorrenciasEvento(ocorrenciasEvento);
    }

    @Override
    public OcorrenciasEvento consultaOcorrenciasEvento(OcorrenciasEvento ocorrenciasEvento) {
        return dao.consultaOcorrenciasEvento(ocorrenciasEvento);
    }
    
}
