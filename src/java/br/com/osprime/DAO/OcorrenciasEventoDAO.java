/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.DAO;

import br.com.osprime.Modelo.OcorrenciasEvento;

/**
 *
 * @author fernando
 */
public interface OcorrenciasEventoDAO {
    
    public OcorrenciasEvento gravaOcorrenciasEvento(OcorrenciasEvento ocorrenciasEvento);
    
    public OcorrenciasEvento consultaOcorrenciasEvento(OcorrenciasEvento ocorrenciasEvento);
}
