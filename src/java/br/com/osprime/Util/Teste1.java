/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.Util;

import br.com.orasystems.CTR.EmpresasCTR;
import br.com.orasystems.CTR.RepositoresCTR;
import br.com.orasystems.Utilitarios.OSUtil;
import br.com.osprime.Modelo.RepositorDespesas;
import br.com.osprime.XML.XMLRepositorDespesas;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        
        SimpleDateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = fromFormat.parse("2013-11-18");
        } catch (ParseException ex) {
            Logger.getLogger(Teste1.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(date);

        XMLRepositorDespesas xMLRepositorDespesas = new XMLRepositorDespesas();

        xMLRepositorDespesas.getEmpresas().setCnpj("09116205000101");
        EmpresasCTR empresasCTR = new EmpresasCTR();
        xMLRepositorDespesas.setEmpresas(empresasCTR.consultaEmpresa(xMLRepositorDespesas.getEmpresas()));

        xMLRepositorDespesas.getRepositores().setDocumento("59979836172");
        RepositoresCTR repositoresCTR = new RepositoresCTR();
        xMLRepositorDespesas.setRepositores(repositoresCTR.consultaRepositor(xMLRepositorDespesas.getRepositores()));

        RepositorDespesas rd = new RepositorDespesas();

        rd.setEmpresas(xMLRepositorDespesas.getEmpresas());
        rd.setRepositores(xMLRepositorDespesas.getRepositores());
        rd.setData(date);
        rd.setDescricao("DESPESAS COM MANUTENÇÃO DA MOTO");
        rd.setValor(120.00);
        rd.setObservacao("TROCA DA RELAÇÃO R$80,00 E CORRENTE R$40,00");
        xMLRepositorDespesas.getListaRepositorDespesas().add(rd);

        rd = new RepositorDespesas();
        rd.setEmpresas(xMLRepositorDespesas.getEmpresas());
        rd.setRepositores(xMLRepositorDespesas.getRepositores());
        rd.setData(date);
        rd.setDescricao("PAPELARIA");
        rd.setValor(19.90);
        rd.setObservacao("");
        xMLRepositorDespesas.getListaRepositorDespesas().add(rd);

        try {
            //Create JAXB context and instantiate marshaller
            JAXBContext context = JAXBContext.newInstance(XMLRepositorDespesas.class);
            Marshaller m = context.createMarshaller();

            m.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            sw = new StringWriter();
            m.marshal(xMLRepositorDespesas, sw);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OSUtil.info(sw.toString());
    }
}
