/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.DAO;

import br.com.osprime.Modelo.ClientesReposicao;
import br.com.osprime.XML.XMLCargaFull;

/**
 *
 * @author fernando
 */
public interface ClientesReposicaoDAO {
    
    public ClientesReposicao consultaClientesReposicao(ClientesReposicao cr);
    
    public ClientesReposicao gravaClientesReposicao(ClientesReposicao cr);
    
    public ClientesReposicao atualizaClientesReposicao(ClientesReposicao cr);
    
    public ClientesReposicao existeClientesReposicao(ClientesReposicao cr);
    
    public XMLCargaFull listaClientesReposicao(XMLCargaFull xMLCargaFull);;
    
}
