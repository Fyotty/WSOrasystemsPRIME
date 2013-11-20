/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.RN;

import br.com.orasystems.CTR.EmpresasCTR;
import br.com.orasystems.CTR.ProtocoloProcessosCTR;
import br.com.orasystems.Modelo.Empresas;
import br.com.orasystems.Modelo.Parametros;
import br.com.orasystems.Modelo.ProtocoloProcessos;
import br.com.orasystems.Utilitarios.OSUtil;
import br.com.orasystems.XML.XMLProtocoloProcessos;
import br.com.osprime.CTR.ClientesReposicaoCTR;
import br.com.osprime.CTR.UltimaCompraReposicaoCTR;
import br.com.osprime.Modelo.ClientesReposicao;
import br.com.osprime.Modelo.UltimaCompraReposicao;
import br.com.osprime.XML.XMLUltimaCompraReposicao;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author fernando
 */
public class UltimaCompraReposicaoRN {

    public static XMLUltimaCompraReposicao xMLUltimaCompraReposicao;

    public void getUltimaCompraReposicao(ProtocoloProcessos pp) {
        xMLUltimaCompraReposicao = new XMLUltimaCompraReposicao();

        StringWriter sw = null;

        try {
            String nomeArquivo = OSUtil.geraChave();

            OSUtil.salvarArquivoXMLFormatada(
                    pp.getArquivo_enviado(), Parametros.caminho_pasta_xmls + "temp\\", nomeArquivo + ".xml");

            if (OSUtil.verificaTamanhoArquivo(Parametros.caminho_pasta_xmls + "temp\\" + nomeArquivo + ".xml") <= 306376) {

                FileReader reader = new FileReader(Parametros.caminho_pasta_xmls + "temp\\" + nomeArquivo + ".xml");

                //Converte a String em classe
                JAXBContext context = JAXBContext.newInstance(XMLUltimaCompraReposicao.class);
                Unmarshaller um = context.createUnmarshaller();
                xMLUltimaCompraReposicao = (XMLUltimaCompraReposicao) um.unmarshal(reader);

                validaEmpresa(xMLUltimaCompraReposicao.getEmpresas());
                if (xMLUltimaCompraReposicao.getListaErros().getErros().isEmpty()) {

                    for (ClientesReposicao cr : xMLUltimaCompraReposicao.getListaClientesReposicao()) {

                        cr.setEmpresas(xMLUltimaCompraReposicao.getEmpresas());
                        //Valida o Cadastro
                        validaClientesReposicao(cr);
                        if (xMLUltimaCompraReposicao.getListaErros().getErros().isEmpty()) {
                            UltimaCompraReposicao compraReposicao = new UltimaCompraReposicao();
                            compraReposicao.setEmpresas(xMLUltimaCompraReposicao.getEmpresas());
                            compraReposicao.setClientesReposicao(cr);
                            UltimaCompraReposicaoCTR ultimaCompraReposicaoCTR = new UltimaCompraReposicaoCTR();
                            ultimaCompraReposicaoCTR.excluiUltimaCompraReposicao(compraReposicao);

                            for (UltimaCompraReposicao ucr : cr.getListaUltimaCompraReposicao()) {
                                //Valida Cadastro
                                validaObjeto(ucr);
                                if (xMLUltimaCompraReposicao.getListaErros().getErros().isEmpty()) {
                                    ucr.setEmpresas(compraReposicao.getEmpresas());
                                    ucr.setClientesReposicao(cr);
                                    ultimaCompraReposicaoCTR.gravaUltimaCompraReposicao(ucr);
                                }
                            }
                        } else {
                            pp.setCodigo(1018);
                            pp.setMensagem("Arquivo enviado contêm erros de validação.");
                        }

                    }

                    if (xMLUltimaCompraReposicao.getListaErros().getErros().isEmpty()) {
                        pp.setCodigo(100);
                        pp.setMensagem("Processo de manutenção da ultima compra do Cliente de Reposição realizado com sucesso!");
                        xMLUltimaCompraReposicao.getListaErros().getErros().add(pp);
                    }

                } else {
                    pp.setCodigo(1018);
                    pp.setMensagem("Arquivo enviado contêm erros de validação.");
                }

            } else {
                pp.setCodigo(1014);
                pp.setMensagem("Arquivo XML ultrapassa o tamanho máximo permitido de 300KB (306376 Bytes)!");
                xMLUltimaCompraReposicao.getListaErros().getErros().add(pp);
            }

        } catch (FileNotFoundException | JAXBException e) {
            pp.setCodigo(999);
            pp.setMensagem("Arquivo XML inválido ou não está de acordo com o processo realizado!: " + e.getMessage());
            xMLUltimaCompraReposicao.getListaErros().getErros().add(pp);
            OSUtil.error(e.getMessage());
        }

        try {
            //Create JAXB context and instantiate marshaller
            XMLProtocoloProcessos xmlpp = new XMLProtocoloProcessos();
            pp.setProcessando("N");
            if (pp.getCodigo() != 100) {
                xmlpp.getListaProcessos().add(xMLUltimaCompraReposicao.getListaErros());
            }
            xmlpp.setPp(pp);

            JAXBContext context = JAXBContext.newInstance(XMLProtocoloProcessos.class);
            Marshaller m = context.createMarshaller();

            m.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            sw = new StringWriter();
            m.marshal(xmlpp, sw);
            pp.setArquivo_retorno(sw.toString());
        } catch (Exception e) {
            OSUtil.error(e.getMessage());
        }
        ProtocoloProcessosCTR.atualizaProtocoloProcessos(pp);
    }

    public void validaObjeto(UltimaCompraReposicao ucr) {
        ProtocoloProcessos pp = new ProtocoloProcessos();

        if (ucr.getCodigo_produto() <= 0) {
            pp.setCodigo(75);
            pp.setMensagem("Não foi informado o código do produto " + ucr.getDescricao_produto());
            xMLUltimaCompraReposicao.getListaErros().getErros().add(pp);
        }

        if (!OSUtil.validaString(ucr.getDescricao_produto())) {
            pp = new ProtocoloProcessos();
            pp.setCodigo(75);
            pp.setMensagem("Não foi informado a descrição do produto " + ucr.getCodigo_produto());
            xMLUltimaCompraReposicao.getListaErros().getErros().add(pp);
        }

        if (OSUtil.validaString(ucr.getDescricao_produto())) {
            if (ucr.getDescricao_produto().length() > 100) {
                pp = new ProtocoloProcessos();
                pp.setCodigo(76);
                pp.setMensagem("A descrição do produto ultrapassa a quantidade limite de 100 caracteres!");
                xMLUltimaCompraReposicao.getListaErros().getErros().add(pp);
            }
        }

        if (!OSUtil.validaString(ucr.getUnidade())) {
            pp = new ProtocoloProcessos();
            pp.setCodigo(75);
            pp.setMensagem("Não foi informado a unidade do produto!");
            xMLUltimaCompraReposicao.getListaErros().getErros().add(pp);
        }

        if (OSUtil.validaString(ucr.getUnidade())) {
            if (ucr.getUnidade().length() > 4) {
                pp = new ProtocoloProcessos();
                pp.setCodigo(76);
                pp.setMensagem("A unidade do produto ultrapassa a quantidade limite de 4 caracteres!");
                xMLUltimaCompraReposicao.getListaErros().getErros().add(pp);
            }
        }

        if (ucr.getQuantidade() <= 0) {
            pp = new ProtocoloProcessos();
            pp.setCodigo(76);
            pp.setMensagem("A quantidade não pode ser menor ou igual a zero!");
            xMLUltimaCompraReposicao.getListaErros().getErros().add(pp);
        }
    }

    public void validaClientesReposicao(ClientesReposicao cr) {
        ProtocoloProcessos pp = new ProtocoloProcessos();

        if (cr.getCodigo() <= 0) {
            pp.setCodigo(75);
            pp.setMensagem("Não foi informado o cliente de reposição!");
            xMLUltimaCompraReposicao.getListaErros().getErros().add(pp);
        }

        if (xMLUltimaCompraReposicao.getListaErros().getErros().isEmpty()) {
            ClientesReposicaoCTR clientesReposicaoCTR = new ClientesReposicaoCTR();
            cr = clientesReposicaoCTR.consultaClientesReposicao(cr);

            if (cr.getId() <= 0) {
                pp = new ProtocoloProcessos();
                pp.setCodigo(1);
                pp.setMensagem("Cliente Reposição não existe!");
                xMLUltimaCompraReposicao.getListaErros().getErros().add(pp);
            }
        }

    }

    public void validaEmpresa(Empresas empresas) {
        ProtocoloProcessos pp = new ProtocoloProcessos();

        empresas.setCnpj(OSUtil.limpaCNPJ(empresas.getCnpj()));
        if (!OSUtil.validaString(empresas.getCnpj())) {
            pp.setCodigo(33);
            pp.setMensagem("É obrigatório informar o CNPJ da empresa!");
            xMLUltimaCompraReposicao.getListaErros().getErros().add(pp);
        }

        if (OSUtil.validaString(empresas.getCnpj())) {
            if (!OSUtil.isCNPJ(empresas.getCnpj())) {
                pp = new ProtocoloProcessos();
                pp.setCodigo(29);
                pp.setMensagem("Problemas ao validar o CNPJ da Empresa!");
                xMLUltimaCompraReposicao.getListaErros().getErros().add(pp);
            }
        }

        if (OSUtil.validaString(empresas.getCnpj())) {
            if (empresas.getCnpj().length() != 14) {
                pp = new ProtocoloProcessos();
                pp.setCodigo(34);
                pp.setMensagem("O CNPJ da empresa deve conter 14 caracteres!");
                xMLUltimaCompraReposicao.getListaErros().getErros().add(pp);
            }
        }

        if (xMLUltimaCompraReposicao.getListaErros().getErros().isEmpty()) {
            EmpresasCTR empresasCTR = new EmpresasCTR();
            empresas = empresasCTR.consultaEmpresa(empresas);

            if (empresas.getId() <= 0) {
                pp = new ProtocoloProcessos();
                pp.setCodigo(1);
                pp.setMensagem("Empresa nao existe!");
                xMLUltimaCompraReposicao.getListaErros().getErros().add(pp);
            }
        }

    }

    public String getProtocoloProcesso(String arquivoXML) {

        byte[] theByteArray = arquivoXML.getBytes();

        ProtocoloProcessos pp = new ProtocoloProcessos();
        pp.setXml_envio(theByteArray);
        pp.setArquivo_enviado(arquivoXML);
        pp.setChave_protocolo(OSUtil.geraChave());

        ProtocoloProcessosCTR ctr = new ProtocoloProcessosCTR();
        /*
         * Grava o Protocolo de envio
         */
        pp = ctr.gravaProtocoloProcessos(pp);

        final ProtocoloProcessos processos = pp;

        new Thread(new Runnable() {
            @Override
            public void run() {
                getUltimaCompraReposicao(processos);
            }
        }).start();

        XMLProtocoloProcessos xMLProtocoloProcessos = new XMLProtocoloProcessos();
        xMLProtocoloProcessos.setPp(pp);
        return OSUtil.xmlListaProtocoloProcesso(xMLProtocoloProcessos);
    }
}
