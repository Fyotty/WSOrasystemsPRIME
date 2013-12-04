/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.DAO;

import br.com.osprime.Modelo.ClientesReposicao;
import br.com.osprime.Modelo.Eventos;
import java.util.List;

/**
 *
 * @author fernando
 */
public interface EventosDAO {
    
    public Eventos consultaEventos(Eventos eventos);
    
    public Eventos gravaEventos(Eventos eventos);
    
    public Eventos atualizaEventos(Eventos eventos);
    
    public Eventos existeEventos(Eventos eventos);
    
    public List<Eventos> listaEventos(ClientesReposicao cr);
    
}
