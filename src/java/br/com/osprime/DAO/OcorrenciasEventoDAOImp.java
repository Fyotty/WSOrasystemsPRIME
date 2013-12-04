/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.DAO;

import br.com.orasystems.DAO.ConnectionFactory;
import br.com.orasystems.Modelo.ProtocoloProcessos;
import br.com.orasystems.Utilitarios.OSUtil;
import br.com.osprime.CTR.EventosCTR;
import br.com.osprime.CTR.OcorrenciasEventoFotosCTR;
import br.com.osprime.Modelo.OcorrenciasEvento;
import br.com.osprime.RN.OcorrenciasEventoRN;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author fernando
 */
public class OcorrenciasEventoDAOImp {

    public OcorrenciasEvento gravaOcorrenciasEvento(OcorrenciasEvento ocorrenciasEvento) {

        ProtocoloProcessos pp = new ProtocoloProcessos();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        ConnectionFactory conexao = new ConnectionFactory();
        try {

            String sql = "insert into ocorrencias_evento "
                    + " (codigo_evento, data_inicio, latitude, longitude, "
                    + "  status_ocorrencia, data_termino, data_criacao, observacao, "
                    + "  nome_responsavel) "
                    + " values "
                    + " (?, ?, ?, ?, "
                    + "  ?, ?, ?, ?, "
                    + "  ?) returning id";

            //Inicia a transação no caso de um possivel erro
            conexao.IniciaTranzacao();

            stmt = conexao.connection.prepareStatement(sql);

            int i = 1;
            //codigo_evento, data_inicio, latitude, longitude,
            stmt.setInt(i++, ocorrenciasEvento.getEventos().getId());
            stmt.setTimestamp(i++, ocorrenciasEvento.getData_inicio());
            stmt.setString(i++, ocorrenciasEvento.getLatitude());
            stmt.setString(i++, ocorrenciasEvento.getLongitude());

            //status_ocorrencia, data_termino, data_criacao, observacao,
            stmt.setInt(i++, ocorrenciasEvento.getStatus_ocorrencia());
            stmt.setTimestamp(i++, ocorrenciasEvento.getData_termino());
            stmt.setTimestamp(i++, ocorrenciasEvento.getData_criacao());
            stmt.setString(i++, ocorrenciasEvento.getObservacao());

            //nome_responsavel
            stmt.setString(i++, ocorrenciasEvento.getNome_responsavel());

            System.out.println(stmt);

            stmt.execute();

            rs = stmt.getResultSet();
            rs.next();
            ocorrenciasEvento.setId(rs.getInt("id"));
            
            OcorrenciasEventoFotosCTR ocorrenciasEventoFotosCTR = new OcorrenciasEventoFotosCTR();
            ocorrenciasEvento.getOcorrenciasEventoFotos().setOcorrenciasEvento(ocorrenciasEvento);
            ocorrenciasEvento.setOcorrenciasEventoFotos(
                    ocorrenciasEventoFotosCTR.gravaOcorrenciasEventoFotos(
                    ocorrenciasEvento.getOcorrenciasEventoFotos()));

            //Finaliza a transacao
            conexao.CommitTranzacao();

            stmt.close();
            conexao.connection.close();

        } catch (Exception e) {
            //Extorna a transacao
            conexao.RollbackTranzacao();
            pp.setCodigo(1062);
            pp.setMensagem("Problemas ao cadastrar a ocorrência do evento! " + e.getMessage());
            OcorrenciasEventoRN.listaErros.getErros().add(pp);
            e.printStackTrace();
        }
        return ocorrenciasEvento;
    }
    
    public OcorrenciasEvento consultaOcorrenciasEvento(OcorrenciasEvento ocorrenciasEvento) {

        String sql = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            ConnectionFactory conexao = new ConnectionFactory();

            sql = "select * from ocorrencias_evento "
                    + "where id = ? ";

            stmt = conexao.connection.prepareStatement(sql);
            stmt.setLong(1, ocorrenciasEvento.getId());

            System.out.println(stmt);
            rs = stmt.executeQuery();

            if (rs.next()) {
                ocorrenciasEvento.getEventos().setId(rs.getInt("codigo_evento"));
                EventosCTR eventosCTR = new EventosCTR();
                ocorrenciasEvento.setEventos(ocorrenciasEvento.getEventos());
                
                ocorrenciasEvento.setData_inicio(rs.getTimestamp("data_inicio"));
                ocorrenciasEvento.setLatitude(rs.getString("latitude"));
                ocorrenciasEvento.setLongitude(rs.getString("longitude"));
                ocorrenciasEvento.setNome_responsavel(rs.getString("nome_responsavel"));
            }

            rs.close();
            stmt.close();
            conexao.connection.close();

        } catch (Exception e) {
            OSUtil.error(e.getMessage());
            OSUtil.error(e.getCause().getMessage());
            e.printStackTrace();
        }
        return ocorrenciasEvento;
    }
}
