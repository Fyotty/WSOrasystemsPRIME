/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.Util;

import br.com.osprime.RN.EventosRN;

/**
 *
 * @author fernando
 */
public class Teste2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"yes\"?><EVENTOS><empresas><cnpj>09116205000101</cnpj></empresas><repositores><documento>21482933896</documento></repositores><listaEventos><clientesReposicao><codigo>418</codigo></clientesReposicao><orden_visita>45</orden_visita><nome_evento>BASTAO REFIL OCEAN L  BRISA</nome_evento><status_evento>0</status_evento><qtd_fotos_evento>1</qtd_fotos_evento><codigo>4</codigo><rotaReposicao><codigo>7</codigo></rotaReposicao></listaEventos><listaEventos><clientesReposicao><codigo>951</codigo></clientesReposicao><orden_visita>54</orden_visita><nome_evento>PEDRA SANIT 12X1 L BRISA</nome_evento><status_evento>0</status_evento><qtd_fotos_evento>3</qtd_fotos_evento><codigo>6</codigo><rotaReposicao><codigo>9</codigo></rotaReposicao></listaEventos><listaEventos><clientesReposicao><codigo>1911</codigo></clientesReposicao><orden_visita>13</orden_visita><nome_evento>CERABRILL LIQUIDA 200ML</nome_evento><status_evento>0</status_evento><qtd_fotos_evento>10</qtd_fotos_evento><codigo>7</codigo><rotaReposicao><codigo>9</codigo></rotaReposicao></listaEventos></EVENTOS>";
        
        EventosRN eventosRN = new EventosRN();
        
        eventosRN.getProtocoloProcesso(xml);
    }
}
