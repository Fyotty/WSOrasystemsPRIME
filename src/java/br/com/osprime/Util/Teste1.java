/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.Util;

import br.com.orasystems.RN.ProtocoloProcessosRN;
import br.com.orasystems.Utilitarios.OSUtil;
import br.com.osprime.Modelo.Eventos;
import br.com.osprime.Modelo.OcorrenciasEvento;
import br.com.osprime.RN.CargaFullRN;
import br.com.osprime.XML.XMLCargaFull;
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

        OcorrenciasEvento oe = new OcorrenciasEvento();
        Eventos eventos = new Eventos();
        
        StringWriter sw = new StringWriter();
        eventos.setId(20);
        oe.setEventos(eventos);
        oe.setLatitude("-1964651");
        oe.setLongitude("-1964651");
        oe.setNome_responsavel("LUIS FERNANDO COSTA");
        oe.setObservacao("dhsa uihduiashd uihasuid hash");
        oe.setStatus_ocorrencia(1);

        try {
            //Create JAXB context and instantiate marshaller
            JAXBContext context = JAXBContext.newInstance(OcorrenciasEvento.class);
            Marshaller m = context.createMarshaller();

            m.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            sw = new StringWriter();
            m.marshal(oe, sw);
            OSUtil.error(sw.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(sw.toString());

    }
}
