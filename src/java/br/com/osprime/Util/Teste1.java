/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.Util;

import br.com.orasystems.Utilitarios.OSUtil;
import br.com.osprime.Modelo.ClientesReposicao;
import br.com.osprime.Modelo.UltimaCompraReposicao;
import br.com.osprime.RN.UltimaCompraReposicaoRN;
import br.com.osprime.XML.XMLUltimaCompraReposicao;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

/**
 *
 * @author fernando
 */
public class Teste1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        StringWriter sw = null;

        /*XMLClientesReposicao xMLClientesReposicao = new XMLClientesReposicao();
         xMLClientesReposicao.getEmpresas().setCnpj("71690382000170");

         EmpresasCTR empresasCTR = new EmpresasCTR();
         xMLClientesReposicao.setEmpresas(empresasCTR.consultaEmpresa(xMLClientesReposicao.getEmpresas()));

         ClientesReposicaoCTR cTR = new ClientesReposicaoCTR();
         ClientesReposicao cr = new ClientesReposicao();
         cr.setCodigo(11);
         cr.setEmpresas(xMLClientesReposicao.getEmpresas());
         cr = cTR.consultaClientesReposicao(cr);

         RotaReposicaoCTR rotaReposicaoCTR = new RotaReposicaoCTR();
         cr.getRr().setCodigo(15);
         cr.getRr().setEmpresas(xMLClientesReposicao.getEmpresas());
         cr.setRr(rotaReposicaoCTR.consultaRotaReposicao(cr.getRr()));

         ClientesRepositor clientesRepositor = new ClientesRepositor();
         clientesRepositor.getRepositores().setDocumento("32643150848");
         clientesRepositor.setOperacao("I");
         cr.getListaRepositores().add(clientesRepositor);

         clientesRepositor = new ClientesRepositor();
         clientesRepositor.getRepositores().setDocumento("329005788876");
         clientesRepositor.setOperacao("D");
         cr.getListaRepositores().add(clientesRepositor);

         xMLClientesReposicao.getListaClientesReposicaos().add(cr);*/

        XMLUltimaCompraReposicao xMLUltimaCompraReposicao = new XMLUltimaCompraReposicao();
        xMLUltimaCompraReposicao.getEmpresas().setCnpj("12345678000199");

        ClientesReposicao clientesReposicao = new ClientesReposicao();
        clientesReposicao.setCodigo(10);
        UltimaCompraReposicao ultimaCompraReposicao = new UltimaCompraReposicao();
        ultimaCompraReposicao.setCodigo_produto(1234567);
        ultimaCompraReposicao.setDescricao_produto("PRODUTO 1");
        ultimaCompraReposicao.setUnidade("UND");
        ultimaCompraReposicao.setQuantidade(12);
        clientesReposicao.getListaUltimaCompraReposicao().add(ultimaCompraReposicao);
        xMLUltimaCompraReposicao.getListaClientesReposicao().add(clientesReposicao);

        clientesReposicao = new ClientesReposicao();
        clientesReposicao.setCodigo(11);
        ultimaCompraReposicao = new UltimaCompraReposicao();
        ultimaCompraReposicao.setCodigo_produto(78945656);
        ultimaCompraReposicao.setDescricao_produto("PRODUTO 2");
        ultimaCompraReposicao.setUnidade("UND");
        ultimaCompraReposicao.setQuantidade(15);
        clientesReposicao.getListaUltimaCompraReposicao().add(ultimaCompraReposicao);
        xMLUltimaCompraReposicao.getListaClientesReposicao().add(clientesReposicao);

        clientesReposicao = new ClientesReposicao();
        clientesReposicao.setCodigo(12);
        ultimaCompraReposicao = new UltimaCompraReposicao();
        ultimaCompraReposicao.setCodigo_produto(98854133);
        ultimaCompraReposicao.setDescricao_produto("PRODUTO 3");
        ultimaCompraReposicao.setUnidade("UND");
        ultimaCompraReposicao.setQuantidade(19);
        clientesReposicao.getListaUltimaCompraReposicao().add(ultimaCompraReposicao);

        ultimaCompraReposicao = new UltimaCompraReposicao();
        ultimaCompraReposicao.setCodigo_produto(78945656);
        ultimaCompraReposicao.setDescricao_produto("PRODUTO 2");
        ultimaCompraReposicao.setUnidade("UND");
        ultimaCompraReposicao.setQuantidade(15);
        clientesReposicao.getListaUltimaCompraReposicao().add(ultimaCompraReposicao);
        xMLUltimaCompraReposicao.getListaClientesReposicao().add(clientesReposicao);

        try {
            //Create JAXB context and instantiate marshaller
            JAXBContext context = JAXBContext.newInstance(XMLUltimaCompraReposicao.class);
            Marshaller m = context.createMarshaller();

            m.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            sw = new StringWriter();
            m.marshal(xMLUltimaCompraReposicao, sw);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OSUtil.info(sw.toString());

        String arqXML = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"yes\"?>\n"
                + "<ULTIMACOMPRAREPOSICAO>\n"
                + "    <empresas>\n"
                + "        <cnpj>71690382000170</cnpj>\n"
                + "    </empresas>\n"
                + "    <lista>\n"
                + "        <codigo>10</codigo>\n"
                + "        <listaUltimaCompraReposicao>\n"
                + "            <codigo_produto>1234567</codigo_produto>\n"
                + "            <descricao_produto>PRODUTO 1</descricao_produto>\n"
                + "            <unidade>UND</unidade>\n"
                + "            <quantidade>12.0</quantidade>\n"
                + "        </listaUltimaCompraReposicao>\n"
                + "    </lista>\n"
                + "    <lista>\n"
                + "        <codigo>11</codigo>\n"
                + "        <listaUltimaCompraReposicao>\n"
                + "            <codigo_produto>78945656</codigo_produto>\n"
                + "            <descricao_produto>PRODUTO 2</descricao_produto>\n"
                + "            <unidade>UND</unidade>\n"
                + "            <quantidade>15.0</quantidade>\n"
                + "        </listaUltimaCompraReposicao>\n"
                + "    </lista>\n"
                + "    <lista>\n"
                + "        <codigo>12</codigo>\n"
                + "        <listaUltimaCompraReposicao>\n"
                + "            <codigo_produto>98854133</codigo_produto>\n"
                + "            <descricao_produto>PRODUTO 3</descricao_produto>\n"
                + "            <unidade>UND</unidade>\n"
                + "            <quantidade>19.0</quantidade>\n"
                + "        </listaUltimaCompraReposicao>\n"
                + "        <listaUltimaCompraReposicao>\n"
                + "            <codigo_produto>78945656</codigo_produto>\n"
                + "            <descricao_produto>PRODUTO 2</descricao_produto>\n"
                + "            <unidade>UND</unidade>\n"
                + "            <quantidade>15.0</quantidade>\n"
                + "        </listaUltimaCompraReposicao>\n"
                + "    </lista>\n"
                + "</ULTIMACOMPRAREPOSICAO>";

        UltimaCompraReposicaoRN ultimaCompraReposicaoRN = new UltimaCompraReposicaoRN();

        OSUtil.info(ultimaCompraReposicaoRN.getUltimaCompraReposicao(arqXML));
    }
}
