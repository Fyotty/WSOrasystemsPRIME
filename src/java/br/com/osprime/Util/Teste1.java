/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.Util;

import br.com.orasystems.RN.ProtocoloProcessosRN;
import br.com.osprime.RN.RotaReposicaoRN;

/**
 *
 * @author fernando
 */
public class Teste1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"yes\"?>\n" +
"<ROTAREPOSICAO>\n" +
"    <empresas>\n" +
"        <cnpj>71690382000170</cnpj>\n" +
"    </empresas>\n" +
"    <lista>\n" +
"        <codigo>37</codigo>\n" +
"        <segunda>N</segunda>\n" +
"        <terca>N</terca>\n" +
"        <quarta>N</quarta>\n" +
"        <quinta>S</quinta>\n" +
"        <sexta>N</sexta>\n" +
"        <sabado>N</sabado>\n" +
"        <domingo>N</domingo>\n" +
"        <sequencia>0</sequencia>\n" +
"        <descricao>QUINTA-FEIRA</descricao>\n" +
"    </lista>\n" +
"	<lista>\n" +
"        <codigo>59</codigo>\n" +
"        <segunda>S</segunda>\n" +
"        <terca>N</terca>\n" +
"        <quarta>N</quarta>\n" +
"        <quinta>N</quinta>\n" +
"        <sexta>N</sexta>\n" +
"        <sabado>N</sabado>\n" +
"        <domingo>N</domingo>\n" +
"        <sequencia>0</sequencia>\n" +
"        <descricao></descricao>\n" +
"    </lista>\n" +
"</ROTAREPOSICAO>";
        
        RotaReposicaoRN rN = new RotaReposicaoRN();
        String retorno = rN.getProtocoloProcesso(xml);
        System.out.println("*** Arquivo retorno 1 *** " + retorno);
        
        ProtocoloProcessosRN pPRN = new ProtocoloProcessosRN();
        
        String fimRetorno = pPRN.retornaXMLSolicitacaoProtocolo(retorno);
        System.out.println("*** Arquivo retorno 2 *** " + fimRetorno);
        
    }
}
