/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.DAO;

import br.com.orasystems.DAO.ConnectionFactory;
import br.com.orasystems.Modelo.ProtocoloProcessos;
import br.com.osprime.Modelo.RepositorDespesas;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author fernando
 */
public class RepositorDespesasDAOImp {
    
    public RepositorDespesas gravaClientesReposicao(RepositorDespesas rd) {

        ProtocoloProcessos pp = new ProtocoloProcessos();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            String sql = "insert into repositor_despesas"
                    + " (codigo_empresa, codigo_repositor, data, descricao, valor, "
                    + "  observacao) "
                    + " values "
                    + " (?, ?, ?, ?, ?, "
                    + "  ? ) returning id";

            ConnectionFactory conexao = new ConnectionFactory();

            stmt = conexao.connection.prepareStatement(sql);

            int i = 1;
            //codigo_empresa, codigo_repositor, data, descricao, valor,
            stmt.setInt(i++, rd.getEmpresas().getId());
            stmt.setInt(i++, rd.getRepositores().getId());
            stmt.setDate(i++, rd.getData());
            stmt.setString(i++, rd.getDescricao());
            stmt.setDouble(i++, rd.getValor());

            //observacao
            stmt.setString(i++, rd.getObservacao());
            
            System.out.println(stmt);

            stmt.execute();
            rs = stmt.getResultSet();
            rs.next();
            rd.setId(rs.getInt("id"));

            stmt.close();
            conexao.connection.close();

        } catch (Exception e) {
            pp.setCodigo(1010);
            pp.setMensagem("Problemas ao cadastrar o Cliente de Reposição! " + e.getMessage());
            e.printStackTrace();
        }
        return rd;
    }
    
}
