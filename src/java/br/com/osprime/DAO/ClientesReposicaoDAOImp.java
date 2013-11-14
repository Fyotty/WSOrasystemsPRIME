/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.DAO;

import br.com.orasystems.CTR.RepositoresCTR;
import br.com.orasystems.DAO.ConnectionFactory;
import br.com.orasystems.Modelo.ProtocoloProcessos;
import br.com.orasystems.Utilitarios.OSUtil;
import br.com.osprime.CTR.ClientesRepositorCTR;
import br.com.osprime.Modelo.ClientesReposicao;
import br.com.osprime.Modelo.ClientesRepositor;
import br.com.osprime.RN.ClientesReposicaoRN;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author fernando
 */
public class ClientesReposicaoDAOImp {

    public ClientesReposicao consultaClientesReposicao(ClientesReposicao cr) {

        String sql = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            ConnectionFactory conexao = new ConnectionFactory();

            sql = "select * from clientes_reposicao where codigo_empresa = ? and codigo = ? ";

            stmt = conexao.connection.prepareStatement(sql);
            stmt.setInt(1, cr.getEmpresas().getId());
            stmt.setInt(2, cr.getCodigo());

            System.out.println(stmt);
            rs = stmt.executeQuery();

            if (rs.next()) {
                cr.setId(rs.getInt("id"));
                cr.setRazao_social(rs.getString("razao_social"));
                cr.setNome_fantasia(rs.getString("nome_fantasia"));
                cr.setCnpj(rs.getString("cnpj"));
                cr.setIestad(rs.getString("iestad"));
                cr.setDt_ultima_compra(rs.getDate("dt_ultima_compra"));
                cr.setContato(rs.getString("contato"));
                cr.setTelefone(rs.getString("telefone"));
                cr.setEndereco(rs.getString("endereco"));
                cr.setBairro(rs.getString("bairro"));
                cr.setCidade(rs.getString("cidade"));
                cr.setCep(rs.getString("cep"));
                cr.setEstado(rs.getString("estado"));
                cr.setObservacao(rs.getString("observacao"));
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

    public ClientesReposicao gravaClientesReposicao(ClientesReposicao cr) {

        ProtocoloProcessos pp = new ProtocoloProcessos();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            String sql = "insert into clientes_reposicao"
                    + " (razao_social, nome_fantasia, cnpj, iestad, dt_ultima_compra, "
                    + "  contato, telefone, endereco, bairro, cidade, "
                    + "  cep, estado, observacao, codigo_empresa, codigo, "
                    + " codigo_rota_reposicao) "
                    + " values "
                    + " (?, ?, ?, ?, ?, "
                    + "  ?, ?, ?, ?, ?, "
                    + "  ?, ?, ?, ?, ?, "
                    + "  ?) returning id";

            ConnectionFactory conexao = new ConnectionFactory();

            stmt = conexao.connection.prepareStatement(sql);

            int i = 1;
            //razao_social, nome_fantasia, cnpj, iestad, dt_ultima_compra,
            stmt.setString(i++, cr.getRazao_social());
            stmt.setString(i++, cr.getNome_fantasia());
            stmt.setString(i++, cr.getCnpj());
            stmt.setString(i++, cr.getIestad());
            stmt.setDate(i++, cr.getDt_ultima_compra());

            //contato, telefone, endereco, bairro, cidade,
            stmt.setString(i++, cr.getContato());
            stmt.setString(i++, cr.getTelefone());
            stmt.setString(i++, cr.getEndereco());
            stmt.setString(i++, cr.getBairro());
            stmt.setString(i++, cr.getCidade());

            //cep, estado, observacao, codigo_empresa, codigo,
            stmt.setString(i++, cr.getCep());
            stmt.setString(i++, cr.getEstado());
            stmt.setString(i++, cr.getObservacao());
            stmt.setInt(i++, cr.getEmpresas().getId());
            stmt.setInt(i++, cr.getCodigo());

            //codigo_rota_reposicao
            stmt.setInt(i++, cr.getRr().getId());

            System.out.println(stmt);

            stmt.execute();
            rs = stmt.getResultSet();
            rs.next();
            cr.setId(rs.getInt("id"));

            stmt.close();
            conexao.connection.close();

            //Grava os repositores desse cliente
            for (ClientesRepositor clientesRepositor : cr.getListaRepositores()) {
                clientesRepositor.setEmpresas(cr.getEmpresas());
                clientesRepositor.setClientesReposicao(cr);

                validaObjeto(clientesRepositor);
                if (ClientesReposicaoRN.xMLClientesReposicao.getListaErros().getErros().isEmpty()) {
                    ClientesRepositorCTR clientesRepositorCTR = new ClientesRepositorCTR();

                    switch (clientesRepositor.getOperacao()) {
                        case "I": {
                            clientesRepositor = clientesRepositorCTR.existeClientesRepositor(clientesRepositor);
                            if (clientesRepositor.getId() <= 0) {
                                clientesRepositor = clientesRepositorCTR.gravaClientesRepositor(clientesRepositor);
                            }
                        }
                        break;
                        case "D": {
                            clientesRepositorCTR.excluiClientesRepositor(clientesRepositor);
                        }
                        break;
                    }
                }
            }

        } catch (Exception e) {
            pp.setCodigo(1010);
            pp.setMensagem("Problemas ao cadastrar o Cliente de Reposição! " + e.getMessage());
            ClientesReposicaoRN.xMLClientesReposicao.getListaErros().getErros().add(pp);
            e.printStackTrace();
        }
        return cr;
    }

    public ClientesReposicao atualizaClientesReposicao(ClientesReposicao cr) {

        ProtocoloProcessos pp = new ProtocoloProcessos();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {

            String sql = "update clientes_reposicao set "
                    + " razao_social = ?, nome_fantasia = ?, cnpj = ?, iestad = ?, dt_ultima_compra = ?, "
                    + "  contato = ?, telefone = ?, endereco = ?, bairro = ?, cidade = ?, "
                    + "  cep = ?, estado = ?, observacao = ?,  "
                    + " codigo_rota_reposicao = ? "
                    + " where id = ? ";

            ConnectionFactory conexao = new ConnectionFactory();

            stmt = conexao.connection.prepareStatement(sql);

            int i = 1;
            //razao_social, nome_fantasia, cnpj, iestad, dt_ultima_compra,
            stmt.setString(i++, cr.getRazao_social());
            stmt.setString(i++, cr.getNome_fantasia());
            stmt.setString(i++, cr.getCnpj());
            stmt.setString(i++, cr.getIestad());
            stmt.setDate(i++, cr.getDt_ultima_compra());

            //contato, telefone, endereco, bairro, cidade,
            stmt.setString(i++, cr.getContato());
            stmt.setString(i++, cr.getTelefone());
            stmt.setString(i++, cr.getEndereco());
            stmt.setString(i++, cr.getBairro());
            stmt.setString(i++, cr.getCidade());

            //cep, estado, observacao, 
            stmt.setString(i++, cr.getCep());
            stmt.setString(i++, cr.getEstado());
            stmt.setString(i++, cr.getObservacao());

            //codigo_rota_reposicao
            stmt.setInt(i++, cr.getRr().getId());

            //filtro
            stmt.setInt(i++, cr.getId());

            System.out.println(stmt);

            stmt.execute();

            stmt.close();
            conexao.connection.close();

            //Grava os repositores desse cliente
            for (ClientesRepositor clientesRepositor : cr.getListaRepositores()) {
                clientesRepositor.setEmpresas(cr.getEmpresas());
                clientesRepositor.setClientesReposicao(cr);

                validaObjeto(clientesRepositor);
                if (ClientesReposicaoRN.xMLClientesReposicao.getListaErros().getErros().isEmpty()) {
                    ClientesRepositorCTR clientesRepositorCTR = new ClientesRepositorCTR();

                    switch (clientesRepositor.getOperacao()) {
                        case "I": {
                            clientesRepositor = clientesRepositorCTR.existeClientesRepositor(clientesRepositor);
                            if (clientesRepositor.getId() <= 0) {
                                clientesRepositor = clientesRepositorCTR.gravaClientesRepositor(clientesRepositor);
                            }
                        }
                        break;
                        case "D": {
                            clientesRepositorCTR.excluiClientesRepositor(clientesRepositor);
                        }
                        break;
                    }
                }
            }

        } catch (Exception e) {
            pp.setCodigo(1011);
            pp.setMensagem("Problemas ao atualizar a Rota de Reposição! " + e.getMessage());
            ClientesReposicaoRN.xMLClientesReposicao.getListaErros().getErros().add(pp);
            e.printStackTrace();
        }
        return cr;
    }

    public ClientesReposicao existeClientesReposicao(ClientesReposicao cr) {

        String sql = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        ProtocoloProcessos pp = new ProtocoloProcessos();

        try {
            ConnectionFactory conexao = new ConnectionFactory();

            sql = "select id from clientes_reposicao where codigo = ? and codigo_empresa = ? ";
            stmt = conexao.connection.prepareStatement(sql);
            stmt.setInt(1, cr.getCodigo());
            stmt.setInt(2, cr.getEmpresas().getId());

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

    public void validaObjeto(ClientesRepositor clientesRepositor) {
        ProtocoloProcessos pp = new ProtocoloProcessos();
        clientesRepositor.getRepositores().setEmpresas(clientesRepositor.getEmpresas());

        if (!OSUtil.validaString(clientesRepositor.getRepositores().getDocumento())) {
            pp.setCodigo(62);
            pp.setMensagem("É obrigatório informar o documento do repositor.");
            ClientesReposicaoRN.xMLClientesReposicao.getListaErros().getErros().add(pp);
        }

        if (ClientesReposicaoRN.xMLClientesReposicao.getListaErros().getErros().isEmpty()) {
            RepositoresCTR repositoresCTR = new RepositoresCTR();
            clientesRepositor.setRepositores(repositoresCTR.consultaRepositor(clientesRepositor.getRepositores()));

            if (clientesRepositor.getRepositores().getId() <= 0) {
                pp.setCodigo(2);
                pp.setMensagem("Repositor nao existe! Documento " + clientesRepositor.getRepositores().getDocumento());
                ClientesReposicaoRN.xMLClientesReposicao.getListaErros().getErros().add(pp);
            }
        }

    }
}
