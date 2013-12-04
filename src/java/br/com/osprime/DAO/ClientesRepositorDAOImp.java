/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.DAO;

import br.com.orasystems.DAO.ConnectionFactory;
import br.com.orasystems.Modelo.ProtocoloProcessos;
import br.com.orasystems.Utilitarios.OSUtil;
import br.com.osprime.Modelo.ClientesRepositor;
import br.com.osprime.Modelo.RotaReposicao;
import br.com.osprime.RN.RotaReposicaoRN;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author fernando
 */
public class ClientesRepositorDAOImp {

    public ClientesRepositor gravaClientesRepositor(ClientesRepositor cr, RotaReposicao rr) {

        ProtocoloProcessos pp = new ProtocoloProcessos();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            String sql = "insert into clientes_repositor"
                    + " (codigo_empresa, codigo_repositor, codigo_cliente_reposicao, codigo_rota_reposicao) "
                    + " values "
                    + " (?, ?, ?, ?) returning id";

            ConnectionFactory conexao = new ConnectionFactory();

            stmt = conexao.connection.prepareStatement(sql);

            int i = 1;
            //codigo_cliente_reposicao, codigo_repositor, codigo_empresa
            stmt.setInt(i++, cr.getEmpresas().getId());
            stmt.setInt(i++, cr.getRepositores().getId());
            stmt.setInt(i++, cr.getClientesReposicao().getId());
            stmt.setInt(i++, rr.getId());

            System.out.println(stmt);

            stmt.execute();
            rs = stmt.getResultSet();
            rs.next();
            cr.setId(rs.getInt("id"));

            stmt.close();
            conexao.connection.close();
        } catch (Exception e) {
            pp.setCodigo(1010);
            pp.setMensagem("Problemas ao cadastrar o Cliente de Reposição! " + e.getMessage());
            RotaReposicaoRN.xMLRotaReposicao.getListaErros().getErros().add(pp);
            e.printStackTrace();
        }
        return cr;
    }

    public ClientesRepositor existeClientesRepositor(ClientesRepositor cr) {

        String sql = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            ConnectionFactory conexao = new ConnectionFactory();

            sql = "select id from clientes_repositor "
                    + "where codigo_empresa = ? "
                    + "and codigo_repositor = ? "
                    + "and codigo_cliente_reposicao = ? ";

            stmt = conexao.connection.prepareStatement(sql);
            stmt.setInt(1, cr.getEmpresas().getId());
            stmt.setInt(2, cr.getRepositores().getId());
            stmt.setInt(3, cr.getClientesReposicao().getId());

            rs = stmt.executeQuery();

            if (rs.next()) {
                cr.setId(rs.getInt("id"));
            }

            rs.close();
            stmt.close();
            conexao.connection.close();

        } catch (Exception e) {
            OSUtil.error(e.getMessage());
            OSUtil.error(e.getCause().getMessage());
            e.printStackTrace();
        }
        return cr;
    }

    public void excluiClientesRepositor(ClientesRepositor cr) {

        String sql = null;
        PreparedStatement stmt = null;

        try {
            ConnectionFactory conexao = new ConnectionFactory();

            sql = "delete from clientes_repositor "
                    + "where codigo_empresa = ? "
                    + "and codigo_repositor = ? "
                    + "and codigo_cliente_reposicao = ? ";

            stmt = conexao.connection.prepareStatement(sql);
            stmt.setInt(1, cr.getEmpresas().getId());
            stmt.setInt(2, cr.getRepositores().getId());
            stmt.setInt(3, cr.getClientesReposicao().getId());

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
