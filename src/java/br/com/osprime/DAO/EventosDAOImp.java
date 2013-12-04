/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.DAO;

import br.com.orasystems.DAO.ConnectionFactory;
import br.com.orasystems.Modelo.ProtocoloProcessos;
import br.com.orasystems.Utilitarios.OSUtil;
import br.com.osprime.Modelo.ClientesReposicao;
import br.com.osprime.Modelo.Eventos;
import br.com.osprime.RN.EventosRN;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fernando
 */
public class EventosDAOImp {

    public Eventos consultaEventos(Eventos eventos) {

        String sql = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            ConnectionFactory conexao = new ConnectionFactory();

            sql = "select * from eventos "
                    + "where codigo_empresa = ? "
                    + "and codigo_repositor = ? "
                    + "and codigo_cliente_reposicao = ? "
                    + "and codigo_rota_reposicao = ?";

            stmt = conexao.connection.prepareStatement(sql);
            stmt.setInt(1, eventos.getEmpresas().getId());
            stmt.setInt(2, eventos.getRepositores().getId());
            stmt.setInt(3, eventos.getClientesReposicao().getId());
            stmt.setInt(4, eventos.getRotaReposicao().getId());

            System.out.println(stmt);
            rs = stmt.executeQuery();

            if (rs.next()) {
                eventos.setId(rs.getInt("id"));
                eventos.setOrdem_visita(rs.getInt("ordem_visita"));
                eventos.setNome_evento(rs.getString("nome_evento"));
                eventos.setStatus_evento(rs.getInt("status_evento"));
                eventos.setQtd_fotos_evento(rs.getInt("qtd_fotos_evento"));
                eventos.setCodigo(rs.getInt("codigo"));
            }

            rs.close();
            stmt.close();
            conexao.connection.close();

        } catch (Exception e) {
            OSUtil.error(e.getMessage());
            OSUtil.error(e.getCause().getMessage());
            e.printStackTrace();
        }
        return eventos;
    }

    public Eventos gravaEventos(Eventos eventos) {

        ProtocoloProcessos pp = new ProtocoloProcessos();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            String sql = "insert into eventos"
                    + " (codigo_repositor, codigo_cliente_reposicao, ordem_visita, nome_evento, "
                    + "  status_evento, qtd_fotos_evento, codigo_empresa, codigo, codigo_rota_reposicao) "
                    + " values "
                    + " (?, ?, ?, ?, "
                    + "  ?, ?, ?, ?, ?) returning id";

            ConnectionFactory conexao = new ConnectionFactory();

            stmt = conexao.connection.prepareStatement(sql);

            int i = 1;
            //codigo_repositor, codigo_cliente_reposicao, orden_visita, nome_evento, dt_hora_prevista,
            stmt.setInt(i++, eventos.getRepositores().getId());
            stmt.setInt(i++, eventos.getClientesReposicao().getId());
            stmt.setInt(i++, eventos.getOrdem_visita());
            stmt.setString(i++, eventos.getNome_evento());

            //status_evento, qtd_fotos_evento, codigo_empresa, codigo, codigo_rota_reposicao
            stmt.setInt(i++, eventos.getStatus_evento());
            stmt.setInt(i++, eventos.getQtd_fotos_evento());
            stmt.setInt(i++, eventos.getEmpresas().getId());
            stmt.setInt(i++, eventos.getCodigo());
            stmt.setInt(i++, eventos.getRotaReposicao().getId());

            System.out.println(stmt);

            stmt.execute();
            rs = stmt.getResultSet();
            rs.next();
            eventos.setId(rs.getInt("id"));

            stmt.close();
            conexao.connection.close();

        } catch (Exception e) {
            pp.setCodigo(1060);
            pp.setMensagem("Problemas ao cadastrar o Evento! " + e.getMessage());
            EventosRN.xMLEventos.getListaErros().getErros().add(pp);
            e.printStackTrace();
        }
        return eventos;
    }

    public Eventos atualizaEventos(Eventos eventos) {

        ProtocoloProcessos pp = new ProtocoloProcessos();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {

            String sql = "update eventos set "
                    + " ordem_visita = ?, nome_evento = ?, "
                    + " status_evento = ?, qtd_fotos_evento = ?"
                    + "where id = ? ";

            ConnectionFactory conexao = new ConnectionFactory();

            stmt = conexao.connection.prepareStatement(sql);

            int i = 1;
            //orden_visita, nome_evento, dt_hora_prevista,
            stmt.setInt(i++, eventos.getOrdem_visita());
            stmt.setString(i++, eventos.getNome_evento());

            //status_evento, qtd_fotos_evento,
            stmt.setInt(i++, eventos.getStatus_evento());
            stmt.setInt(i++, eventos.getQtd_fotos_evento());

            //filtro
            stmt.setInt(i++, eventos.getId());

            System.out.println(stmt);

            stmt.execute();

            stmt.close();
            conexao.connection.close();

        } catch (Exception e) {
            pp.setCodigo(1061);
            pp.setMensagem("Problemas ao atualizar Evento! " + e.getMessage());
            EventosRN.xMLEventos.getListaErros().getErros().add(pp);
            e.printStackTrace();
        }
        return eventos;
    }

    public Eventos existeEventos(Eventos eventos) {

        String sql = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        ProtocoloProcessos pp = new ProtocoloProcessos();

        try {
            ConnectionFactory conexao = new ConnectionFactory();

            sql = "select * from eventos "
                    + "where codigo_empresa = ? "
                    + "and codigo_repositor = ? "
                    + "and codigo_cliente_reposicao = ? "
                    + "and codigo_rota_reposicao = ?";

            stmt = conexao.connection.prepareStatement(sql);
            stmt.setInt(1, eventos.getEmpresas().getId());
            stmt.setInt(2, eventos.getRepositores().getId());
            stmt.setInt(3, eventos.getClientesReposicao().getId());
            stmt.setInt(4, eventos.getRotaReposicao().getId());

            rs = stmt.executeQuery();

            if (rs.next()) {
                eventos.setId(rs.getInt("id"));
            }

            rs.close();
            stmt.close();
            conexao.connection.close();

        } catch (Exception e) {
            OSUtil.error(e.getMessage());
            OSUtil.error(e.getCause().getMessage());
            e.printStackTrace();
        }
        return eventos;
    }

    public List<Eventos> listaEventos(ClientesReposicao cr) {

        String sql = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Eventos> lista = new ArrayList<>();

        try {
            ConnectionFactory conexao = new ConnectionFactory();

            sql = "select * "
                    + "  from eventos e "
                    + " where e.codigo_cliente_reposicao = ?";

            stmt = conexao.connection.prepareStatement(sql);
            stmt.setInt(1, cr.getId());

            System.out.println(stmt);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Eventos eventos = new Eventos();
                
                eventos.setId(rs.getInt("id"));
                eventos.setOrdem_visita(rs.getInt("ordem_visita"));
                eventos.setNome_evento(rs.getString("nome_evento"));
                eventos.setStatus_evento(rs.getInt("status_evento"));
                eventos.setQtd_fotos_evento(rs.getInt("qtd_fotos_evento"));
                eventos.setCodigo(rs.getInt("codigo"));
                eventos.getRotaReposicao().setId(rs.getInt("codigo_rota_reposicao"));
                
                lista.add(eventos);
            }

            rs.close();
            stmt.close();
            conexao.connection.close();

        } catch (Exception e) {
            OSUtil.error(e.getMessage());
            OSUtil.error(e.getCause().getMessage());
            e.printStackTrace();
        }
        return lista;
    }
}
