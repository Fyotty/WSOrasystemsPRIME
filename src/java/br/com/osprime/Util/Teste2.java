/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.Util;

import br.com.orasystems.RN.ProtocoloProcessosRN;
import br.com.osprime.RN.ClientesReposicaoRN;

/**
 *
 * @author fernando
 */
public class Teste2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"yes\"?>"
                + "<CLIENTESREPOSICAO><empresas><cnpj>09116205000101</cnpj></empresas>"
                + "<lista><codigo>1358</codigo><razao_social>ECONOMICO SECOS E MOLHADOS LTDA EPP</razao_social><nome_fantasia>SUPERMERCADO ECONOMICO</nome_fantasia><cnpj>04116864000124</cnpj><iestad>103328980</iestad><contato>WALBER</contato><telefone>1734426979</telefone><endereco>AV MARECHAL EMILIO RODRIGUES R  JUNIOR, 38</endereco><bairro>CENTRO</bairro><cidade>NAZARIO</cidade><cep>76180-000</cep><estado>GO</estado><dt_ultima_compra>2013-05-24</dt_ultima_compra><email>economico@gmail.com</email><observacao>ALTERADO PARA TESTE</observacao><rr><codigo>7</codigo></rr><rr><codigo>17</codigo></rr><rr><codigo>22</codigo></rr><listaRepositores><repositores><documento>21482933896</documento></repositores><operacao>I</operacao></listaRepositores><listaRepositores><repositores><documento>166.943.858-97</documento></repositores><operacao>I</operacao></listaRepositores><listaRepositores><repositores><documento>181486908</documento></repositores><operacao>I</operacao></listaRepositores></lista><lista><codigo>856</codigo><razao_social>FURACAO PRODUTOS  PROFISSIONAIS LTDA</razao_social><nome_fantasia>FURACAO PRODUTOS</nome_fantasia><cnpj>12572487000166</cnpj><iestad>104822260</iestad><contato>SR MARIO</contato><telefone>06436130849</telefone><endereco>AV PRESIDENTE  VARGAS, 1737</endereco><bairro>SETOR CENTRAL</bairro><cidade>RIO VERDE</cidade><cep>75903-290</cep><estado>GO</estado><dt_ultima_compra>2013-08-02</dt_ultima_compra><email>rafaelfuracaoprodutos@hotmail.com</email><observacao></observacao><rr><codigo>17</codigo></rr><listaRepositores><repositores><documento>21482933896</documento></repositores><operacao>I</operacao></listaRepositores></lista><lista><codigo>418</codigo><razao_social>A  CLEMENTINO DA SILVA</razao_social><nome_fantasia>SACOLAO SANTO ANTONIO</nome_fantasia><cnpj>07783019000109</cnpj><iestad>103965602</iestad><contato></contato><telefone> 6233574329</telefone><endereco>AV TRANSBRASILIANA  49    QD 92 LT 04</endereco><bairro>CENTRO</bairro><cidade>URUACU</cidade><cep>76400-000</cep><estado>GO</estado><dt_ultima_compra>2013-07-19</dt_ultima_compra><email>leila_souza19@hotmail.com</email><observacao></observacao><rr><codigo>7</codigo></rr><rr><codigo>9</codigo></rr><rr><codigo>15</codigo></rr><rr><codigo>2</codigo></rr><listaRepositores><repositores><documento>21482933896</documento></repositores><operacao>I</operacao></listaRepositores><listaRepositores><repositores><documento>166.943.858-97</documento></repositores><operacao>I</operacao></listaRepositores><listaRepositores><repositores><documento>181486908</documento></repositores><operacao>I</operacao></listaRepositores><listaRepositores><repositores><documento>92452680800</documento></repositores><operacao>I</operacao></listaRepositores></lista><lista><codigo>1018</codigo><razao_social>PAEZE  ALIMENTOS LTDA</razao_social><nome_fantasia>PAEZE SUPERMERCADO</nome_fantasia><cnpj>04711892000190</cnpj><iestad>103483080</iestad><contato>DENILSON</contato><telefone>06436221333</telefone><endereco>R JOAO VAIANO, SN</endereco><bairro>SETOR MORADA DO SOL</bairro><cidade>RIO VERDE</cidade><cep>75908-785</cep><estado>GO</estado><dt_ultima_compra>2013-07-26</dt_ultima_compra><email>nfe@paeze.com.br</email><observacao></observacao><rr><codigo>2</codigo></rr><listaRepositores><repositores><documento>21482933896</documento></repositores><operacao>I</operacao></listaRepositores></lista><lista><codigo>1659</codigo><razao_social>AMPLA RIO COM DE ALIM LTDA</razao_social><nome_fantasia>SUPERMERCADO ECONOMIA II</nome_fantasia><cnpj>12073833000161</cnpj><iestad>104743387</iestad><contato>RICARDO</contato><telefone>6436208800</telefone><endereco>RUA RECIFE, 219</endereco><bairro>VILA MARIA</bairro><cidade>RIO VERDE</cidade><cep>75900-242</cep><estado>GO</estado><dt_ultima_compra>2013-08-30</dt_ultima_compra><email>ECONOMIA32@GMAIL.COM</email><observacao></observacao><rr><codigo>9</codigo></rr><listaRepositores><repositores><documento>21482933896</documento></repositores><operacao>I</operacao></listaRepositores></lista></CLIENTESREPOSICAO>";
        
        ClientesReposicaoRN rN = new ClientesReposicaoRN();
        String retorno = rN.getProtocoloProcesso(xml);
        System.out.println("*** Arquivo retorno 1 *** " + retorno);
        
        ProtocoloProcessosRN pPRN = new ProtocoloProcessosRN();
        
        String fimRetorno = pPRN.retornaXMLSolicitacaoProtocolo(retorno);
        System.out.println("*** Arquivo retorno 2 *** " + fimRetorno);
        
    }
}
