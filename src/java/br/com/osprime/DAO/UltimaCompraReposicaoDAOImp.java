/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.DAO;

import br.com.orasystems.DAO.ConnectionFactory;
import br.com.orasystems.Modelo.ProtocoloProcessos;
import br.com.orasystems.Utilitarios.OSUtil;
import br.com.osprime.Modelo.UltimaCompraReposicao;
import br.com.osprime.RN.RotaReposicaoRN;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author fernando
 */
public class UltimaCompraReposicaoDAOImp {
    
    public UltimaCompraReposicao gravaUltimaCompraReposicao(UltimaCompraReposicao ucr) {

        ProtocoloProcessos pp = new ProtocoloProcessos();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            
            String sql = "insert into ultima_compra_reposicao"
                    + " (codigo_empresa, codigo_cliente_reposicao, codigo_produto, descricao_produto, unidade, "
                    + "  quantidade) "
                    + " values "
                    + " (?, ?, ?, ?, ?, "
                    + "  ?) returning id";

            ConnectionFactory conexao = new ConnectionFactory();

            stmt = conexao.connection.prepareStatement(sql);

            int i = 1;
            //codigo_empresa, codigo_cliente_reposicao, codigo_produto, descricao_produto, unidade
            stmt.setInt(i++, ucr.getEmpresas().getId());
            stmt.setInt(i++, ucr.getClientesReposicao().getId());
            stmt.setLong(i++, ucr.getCodigo_produto());
            stmt.setString(i++, ucr.getDescricao_produto());
            stmt.setString(i++, ucr.getUnidade());
            
            //quantidade
            stmt.setDouble(i++, ucr.getQuantidade());
            
            System.out.println(stmt);
            
            stmt.execute();
            rs = stmt.getResultSet();
            rs.next();
            ucr.setId(rs.getInt("id"));
            
            stmt.close();
            conexao.connection.close();
        } catch (Exception e) {
            pp.setCodigo(1010);
            pp.setMensagem("Problemas ao gravar a Ultima Compra! "+e.getMessage());
            RotaReposicaoRN.xMLRotaReposicao.getListaErros().getErros().add(pp);
            e.printStackTrace();
        }
        return ucr;
    }
        
    public void excluiUltimaCompraReposicao(UltimaCompraReposicao ucr) {

        String sql = null;
        PreparedStatement stmt = null;

        try {
            ConnectionFactory conexao = new ConnectionFactory();

            sql = "delete from ultima_compra_reposicao "
                    + "where codigo_empresa = ? "
                    + "and codigo_cliente_reposicao = ? ";
            
            stmt = conexao.connection.prepareStatement(sql);
            stmt.setInt(1, ucr.getEmpresas().getId());
            stmt.setInt(2, ucr.getClientesReposicao().getId());

            System.out.println(stmt);
            
            stmt.execute();

            stmt.close();
            conexao.connection.close();

        } catch (Exception e) {
            OSUtil.error(e.getMessage());
            OSUtil.error(e.getCause().getMessage());
            e.printStackTrace();
        }
    }
    
}
