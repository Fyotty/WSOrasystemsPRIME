/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.DAO;

import br.com.orasystems.DAO.ConnectionFactory;
import br.com.orasystems.Modelo.ProtocoloProcessos;
import br.com.osprime.Modelo.OcorrenciasEventoFotos;
import br.com.osprime.RN.OcorrenciasEventoRN;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author fernando
 */
public class OcorrenciasEventoFotosDAOImp {
    
    public OcorrenciasEventoFotos gravaOcorrenciasEventoFotos(OcorrenciasEventoFotos ocorrenciasEventoFotos) {

        ProtocoloProcessos pp = new ProtocoloProcessos();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        ConnectionFactory conexao = new ConnectionFactory();
        try {

            String sql = "insert into ocorrencias_evento_fotos "
                    + " (codigo_ocorrencia_evento, caminho_foto, foto_tipo, data_captura, observacao) "
                    + " values "
                    + " (?, ?, ?, ?, ?) returning id";
            
            stmt = conexao.connection.prepareStatement(sql);

            int i = 1;
            //codigo_ocorrencia_evento, caminho_foto, foto_tipo, data_captura, observacao,
            stmt.setLong(i++, ocorrenciasEventoFotos.getOcorrenciasEvento().getId());
            stmt.setString(i++, ocorrenciasEventoFotos.getCaminho_foto());
            stmt.setInt(i++, ocorrenciasEventoFotos.getFoto_tipo());
            stmt.setTimestamp(i++, ocorrenciasEventoFotos.getData_captura());
            stmt.setString(i++, ocorrenciasEventoFotos.getObservacao());

            System.out.println(stmt);

            stmt.execute();
            
            rs = stmt.getResultSet();
            rs.next();
            ocorrenciasEventoFotos.setId(rs.getInt("id"));

            stmt.close();
            conexao.connection.close();

        } catch (Exception e) {
            pp.setCodigo(1063);
            pp.setMensagem("Problemas ao cadastrar as informações da foto! " + e.getMessage());
            OcorrenciasEventoRN.listaErros.getErros().add(pp);
            e.printStackTrace();
        }
        return ocorrenciasEventoFotos;
    }
    
}
