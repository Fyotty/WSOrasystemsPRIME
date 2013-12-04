/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.DAO;

import br.com.orasystems.DAO.ConnectionFactory;
import br.com.orasystems.Modelo.ProtocoloProcessos;
import br.com.osprime.Modelo.RepositorDespCombustiveis;
import br.com.osprime.RN.RepositorDespCombustiveisRN;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author fernando
 */
public class RepositorDespCombustiveisDAOImp {
    
    public RepositorDespCombustiveis gravaRepositorDespCombustiveis(RepositorDespCombustiveis rdc) {

        ProtocoloProcessos pp = new ProtocoloProcessos();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            String sql = "insert into repositor_desp_combustiveis"
                    + " (data, dia_semana, local_partida, local_chegada, km_inicial, "
                    + "  km_final, total_km, media_veiculo, consumo_dia,  preco_combustivel, "
                    + "  subtotal, veiculo, placa, codigo_empresa, codigo_repositor) "
                    + " values "
                    + " (?, ?, ?, ?, ?, "
                    + "  ?, ?, ?, ?, ?,"
                    + "  ?, ?, ?, ?, ?) returning id";

            ConnectionFactory conexao = new ConnectionFactory();

            stmt = conexao.connection.prepareStatement(sql);

            int i = 1;
            //data, dia_semana, local_partida, local_chegada, km_inicial,
            stmt.setDate(i++, rdc.getData());
            stmt.setString(i++, rdc.getDia_semana());
            stmt.setString(i++, rdc.getLocal_partida());
            stmt.setString(i++, rdc.getLocal_chegada());
            stmt.setDouble(i++, rdc.getKm_inicial());

            //km_final, total_km, media_veiculo, consumo_dia,  preco_combustivel,
            stmt.setDouble(i++, rdc.getKm_final());
            stmt.setDouble(i++, rdc.getTotal_km());
            stmt.setDouble(i++, rdc.getMedia_veiculo());
            stmt.setDouble(i++, rdc.getConsumo_dia());
            stmt.setDouble(i++, rdc.getPreco_combustivel());
            
            //subtotal, veiculo, placa, codigo_empresa, codigo_repositor
            stmt.setDouble(i++, rdc.getSubtotal());
            stmt.setString(i++, rdc.getVeiculo());
            stmt.setString(i++, rdc.getPlaca());
            stmt.setInt(i++, rdc.getEmpresas().getId());
            stmt.setInt(i++, rdc.getRepositores().getId());
            
            System.out.println(stmt);

            stmt.execute();
            rs = stmt.getResultSet();
            rs.next();
            rdc.setId(rs.getInt("id"));

            stmt.close();
            conexao.connection.close();

        } catch (Exception e) {
            pp.setCodigo(1056);
            pp.setMensagem("Problemas ao cadastrar as despesas com combutíveis do repositor! " + e.getMessage());
            RepositorDespCombustiveisRN.xMLRepositorDespCombustiveis.getListaErros().getErros().add(pp);
            e.printStackTrace();
        }
        return rdc;
    }
    
}
