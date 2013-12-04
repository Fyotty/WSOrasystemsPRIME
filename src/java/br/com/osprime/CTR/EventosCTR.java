/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.CTR;

import br.com.osprime.DAO.EventosDAO;
import br.com.osprime.DAO.EventosDAOImp;
import br.com.osprime.Modelo.ClientesReposicao;
import br.com.osprime.Modelo.Eventos;
import java.util.List;

/**
 *
 * @author fernando
 */
public class EventosCTR implements EventosDAO {
    
    EventosDAOImp dao = new EventosDAOImp();

    @Override
    public Eventos consultaEventos(Eventos eventos) {
        return dao.consultaEventos(eventos);
    }

    @Override
    public Eventos gravaEventos(Eventos eventos) {
        return dao.gravaEventos(eventos);
    }

    @Override
    public Eventos atualizaEventos(Eventos eventos) {
        return dao.atualizaEventos(eventos);
    }

    @Override
    public Eventos existeEventos(Eventos eventos) {
        return dao.existeEventos(eventos);
    }

    @Override
    public List<Eventos> listaEventos(ClientesReposicao cr) {
        return dao.listaEventos(cr);
    }
    
}
