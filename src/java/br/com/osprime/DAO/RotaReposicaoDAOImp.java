/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.DAO;

import br.com.orasystems.DAO.ConnectionFactory;
import br.com.orasystems.Modelo.ProtocoloProcessos;
import br.com.orasystems.Utilitarios.OSUtil;
import br.com.osprime.Modelo.RotaReposicao;
import br.com.osprime.RN.RotaReposicaoRN;
import br.com.osprime.XML.XMLCargaFull;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author fernando
 */
public class RotaReposicaoDAOImp {

    public RotaReposicao consultaRotaReposicao(RotaReposicao rr) {

        String sql = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            ConnectionFactory conexao = new ConnectionFactory();

            sql = "select * from rota_reposicao where ";
            if (rr.getId() > 0) {
                sql = sql + " id = ?";
            } else {
                sql = sql + " codigo_empresa = ? and codigo = ? ";
            }

            stmt = conexao.connection.prepareStatement(sql);
            if (rr.getId() > 0) {
                stmt.setInt(1, rr.getId());
            } else {
                stmt.setInt(1, rr.getEmpresas().getId());
                stmt.setInt(2, rr.getCodigo());
            }

            System.out.println(stmt);
            rs = stmt.executeQuery();

            if (rs.next()) {
                rr.setId(rs.getInt("id"));
                rr.setSegunda(rs.getString("segunda"));
                rr.setTerca(rs.getString("terca"));
                rr.setQuarta(rs.getString("quarta"));
                rr.setQuinta(rs.getString("quinta"));
                rr.setSexta(rs.getString("sexta"));
                rr.setSabado(rs.getString("sabado"));
                rr.setDomingo(rs.getString("domingo"));
                rr.setSequencia(rs.getInt("sequencia"));
                rr.setDescricao(rs.getString("descricao"));
            }

            rs.close();
            stmt.close();
            conexao.connection.close();

        } catch (Exception e) {
            OSUtil.error(e.getMessage());
            OSUtil.error(e.getCause().getMessage());
            e.printStackTrace();
        }
        return rr;
    }

    public RotaReposicao gravaRotaReposicao(RotaReposicao rr) {

        ProtocoloProcessos pp = new ProtocoloProcessos();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            String sql = "insert into rota_reposicao"
                    + " (codigo_empresa, codigo, segunda, terca, quarta, "
                    + "  quinta, sexta, sabado, domingo, sequencia, "
                    + "  descricao) "
                    + " values "
                    + " (?, ?, ?, ?, ?, "
                    + "  ?, ?, ?, ?, ?, "
                    + "  ?) returning id";

            ConnectionFactory conexao = new ConnectionFactory();

            stmt = conexao.connection.prepareStatement(sql);

            int i = 1;
            //codigo_empresa, codigo, segunda, terca, quarta,
            stmt.setInt(i++, rr.getEmpresas().getId());
            stmt.setInt(i++, rr.getCodigo());
            stmt.setString(i++, rr.getSegunda());
            stmt.setString(i++, rr.getTerca());
            stmt.setString(i++, rr.getQuarta());

            //quinta, sexta, sabado, domingo, sequencia,
            stmt.setString(i++, rr.getQuinta());
            stmt.setString(i++, rr.getSexta());
            stmt.setString(i++, rr.getSabado());
            stmt.setString(i++, rr.getDomingo());
            stmt.setInt(i++, rr.getSequencia());

            //descricao
            stmt.setString(i++, rr.getDescricao());

            System.out.println(stmt);

            stmt.execute();
            rs = stmt.getResultSet();
            rs.next();
            rr.setId(rs.getInt("id"));

            stmt.close();
            conexao.connection.close();
        } catch (Exception e) {
            pp.setCodigo(1006);
            pp.setMensagem("Problemas ao cadastrar a Rota de Reposição! " + e.getMessage());
            RotaReposicaoRN.xMLRotaReposicao.getListaErros().getErros().add(pp);
            e.printStackTrace();
        }
        return rr;
    }

    public RotaReposicao atualizaRotaReposicao(RotaReposicao rr) {

        ProtocoloProcessos pp = new ProtocoloProcessos();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {

            String sql = "update rota_reposicao set "
                    + " segunda = ?, terca = ?, quarta = ?, "
                    + "  quinta = ?, sexta = ?, sabado = ?, domingo = ?, sequencia = ?, "
                    + "  descricao = ? "
                    + " where id = ? ";

            ConnectionFactory conexao = new ConnectionFactory();

            stmt = conexao.connection.prepareStatement(sql);

            int i = 1;
            //segunda, terca, quarta,
            stmt.setString(i++, rr.getSegunda());
            stmt.setString(i++, rr.getTerca());
            stmt.setString(i++, rr.getQuarta());

            //quinta, sexta, sabado, domingo, sequencia,
            stmt.setString(i++, rr.getQuinta());
            stmt.setString(i++, rr.getSexta());
            stmt.setString(i++, rr.getSabado());
            stmt.setString(i++, rr.getDomingo());
            stmt.setInt(i++, rr.getSequencia());

            //descricao
            stmt.setString(i++, rr.getDescricao());

            //filtro
            stmt.setInt(i++, rr.getId());

            System.out.println(stmt);

            stmt.execute();

            stmt.close();
            conexao.connection.close();
        } catch (Exception e) {
            pp.setCodigo(1007);
            pp.setMensagem("Problemas ao atualizar a Rota de Reposição! " + e.getMessage());
            RotaReposicaoRN.xMLRotaReposicao.getListaErros().getErros().add(pp);
            e.printStackTrace();
        }
        return rr;
    }

    public RotaReposicao existeRotaReposicao(RotaReposicao rr) {

        String sql = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        ProtocoloProcessos pp = new ProtocoloProcessos();

        try {
            ConnectionFactory conexao = new ConnectionFactory();

            sql = "select id from rota_reposicao where codigo = ? and codigo_empresa = ? ";
            stmt = conexao.connection.prepareStatement(sql);
            stmt.setInt(1, rr.getCodigo());
            stmt.setInt(2, rr.getEmpresas().getId());

            rs = stmt.executeQuery();

            if (rs.next()) {
                rr.setId(rs.getInt("id"));
            }

            rs.close();
            stmt.close();
            conexao.connection.close();

        } catch (Exception e) {
            OSUtil.error(e.getMessage());
            OSUtil.error(e.getCause().getMessage());
            e.printStackTrace();
        }
        return rr;
    }

    public XMLCargaFull listaRotaReposicao(XMLCargaFull xMLCargaFull) {

        String sql = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            ConnectionFactory conexao = new ConnectionFactory();

            sql = "select * "
                    + "  from rota_reposicao "
                    + " where id in (select codigo_rota_reposicao "
                    + "                from clientes_repositor "
                    + "               where codigo_repositor = ?)";

            stmt = conexao.connection.prepareStatement(sql);
            
            stmt.setInt(1, xMLCargaFull.getRepositores().getId());
            
            System.out.println(stmt);
            rs = stmt.executeQuery();

            while (rs.next()) {
                RotaReposicao rr = new RotaReposicao();
                
                rr.setId(rs.getInt("id"));
                rr.setSegunda(rs.getString("segunda"));
                rr.setTerca(rs.getString("terca"));
                rr.setQuarta(rs.getString("quarta"));
                rr.setQuinta(rs.getString("quinta"));
                rr.setSexta(rs.getString("sexta"));
                rr.setSabado(rs.getString("sabado"));
                rr.setDomingo(rs.getString("domingo"));
                rr.setSequencia(rs.getInt("sequencia"));
                rr.setDescricao(rs.getString("descricao"));
                rr.setCodigo(rs.getInt("codigo"));
                
                xMLCargaFull.getListaRotaReposicao().add(rr);
            }

            rs.close();
            stmt.close();
            conexao.connection.close();

        } catch (Exception e) {
            OSUtil.error(e.getMessage());
            OSUtil.error(e.getCause().getMessage());
            e.printStackTrace();
        }
        return xMLCargaFull;
    }
}
