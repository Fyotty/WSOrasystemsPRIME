/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.RN;

import br.com.orasystems.CTR.EmpresasCTR;
import br.com.orasystems.CTR.ProtocoloProcessosCTR;
import br.com.orasystems.Modelo.ListaErros;
import br.com.orasystems.Modelo.Parametros;
import br.com.orasystems.Modelo.ProtocoloProcessos;
import br.com.orasystems.Utilitarios.OSUtil;
import br.com.orasystems.XML.XMLProtocoloProcessos;
import br.com.osprime.CTR.RotaReposicaoCTR;
import br.com.osprime.Modelo.RotaReposicao;
import br.com.osprime.XML.XMLRotaReposicao;
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
public class RotaReposicaoRN {

    public static XMLRotaReposicao xMLRotaReposicao;

    public void getRotaReposicao(ProtocoloProcessos pp) {
        xMLRotaReposicao = new XMLRotaReposicao();

        StringWriter sw = new StringWriter();

        String nomeArquivo = OSUtil.geraChave();

        OSUtil.salvarArquivoXMLFormatada(
                pp.getArquivo_enviado(), Parametros.caminho_pasta_xmls + "temp\\", nomeArquivo + ".xml");

        try {

            if (OSUtil.verificaTamanhoArquivo(Parametros.caminho_pasta_xmls + "temp\\" + nomeArquivo + ".xml") <= 306376) {

                FileReader reader = new FileReader(Parametros.caminho_pasta_xmls + "temp\\" + nomeArquivo + ".xml");

                //Converte a String em classe
                JAXBContext context = JAXBContext.newInstance(XMLRotaReposicao.class);
                Unmarshaller um = context.createUnmarshaller();
                xMLRotaReposicao = (XMLRotaReposicao) um.unmarshal(reader);
                EmpresasCTR empresasCTR = new EmpresasCTR();
                for (RotaReposicao rr : xMLRotaReposicao.getListaRotaReposicao()) {

                    rr.setEmpresas(empresasCTR.consultaEmpresa(xMLRotaReposicao.getEmpresas()));
                    //Valida o Cadastro
                    validaObjeto(rr);
                    if (xMLRotaReposicao.getListaErros().getErros().isEmpty()) {
                        RotaReposicaoCTR cTR = new RotaReposicaoCTR();
                        rr = cTR.existeRotaReposicao(rr);
                        if (rr.getId() == 0) {
                            rr = cTR.gravaRotaReposicao(rr);
                        } else {
                            rr = cTR.atualizaRotaReposicao(rr);
                        }
                    } else {
                        pp.setCodigo(1018);
                        pp.setMensagem("Arquivo enviado contêm erros de validação.");
                    }
                }

                if (xMLRotaReposicao.getListaErros().getErros().isEmpty()) {
                    pp.setCodigo(100);
                    pp.setMensagem("Processo de Manutenção de Rota de Reposição realizado com sucesso!");
                    xMLRotaReposicao.getListaErros().getErros().add(pp);
                }

            } else {
                pp.setCodigo(1014);
                pp.setMensagem("Arquivo XML ultrapassa o tamanho máximo permitido de 300KB (306376 Bytes)!");
                xMLRotaReposicao.getListaErros().getErros().add(pp);
            }

        } catch (FileNotFoundException | JAXBException e) {
            pp.setCodigo(999);
            pp.setMensagem("Arquivo XML inválido ou não está de acordo com o processo realizado!: " + e.getMessage());
            xMLRotaReposicao.getListaErros().getErros().add(pp);
            OSUtil.error(e.getMessage());
        }

        try {
            //Create JAXB context and instantiate marshaller
            XMLProtocoloProcessos xmlpp = new XMLProtocoloProcessos();
            pp.setProcessando("N");
            if (pp.getCodigo() != 100) {
                xmlpp.setListaProcessos(xMLRotaReposicao.getListaErros().getErros());
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

    public void validaObjeto(RotaReposicao rr) {
        ProtocoloProcessos pp = new ProtocoloProcessos();

        rr.getEmpresas().setCnpj(OSUtil.limpaCNPJ(rr.getEmpresas().getCnpj()));
        if (!OSUtil.validaString(rr.getEmpresas().getCnpj())) {
            pp.setCodigo(33);
            pp.setMensagem("É obrigatório informar o CNPJ da empresa!");
            xMLRotaReposicao.getListaErros().getErros().add(pp);
            pp = new ProtocoloProcessos();
        }

        if (OSUtil.validaString(rr.getEmpresas().getCnpj())) {
            if (!OSUtil.isCNPJ(rr.getEmpresas().getCnpj())) {
                pp.setCodigo(29);
                pp.setMensagem("Problemas ao validar o CNPJ da Empresa!");
                xMLRotaReposicao.getListaErros().getErros().add(pp);
                pp = new ProtocoloProcessos();
            }
        }

        if (OSUtil.validaString(rr.getEmpresas().getCnpj())) {
            if (rr.getEmpresas().getCnpj().length() != 14) {
                pp.setCodigo(34);
                pp.setMensagem("O CNPJ da empresa deve conter 14 caracteres!");
                xMLRotaReposicao.getListaErros().getErros().add(pp);
                pp = new ProtocoloProcessos();
            }
        }

        if (rr.getCodigo() == 0) {
            pp.setCodigo(45);
            pp.setMensagem("Código da rota de reposição não foi informado!");
            xMLRotaReposicao.getListaErros().getErros().add(pp);
            pp = new ProtocoloProcessos();
        }

        if (!OSUtil.validaString(rr.getDescricao())) {
            pp.setCodigo(1017);
            pp.setMensagem("Descrição da rota de reposição não foi informada!");
            xMLRotaReposicao.getListaErros().getErros().add(pp);
            pp = new ProtocoloProcessos();
        }

        if (!OSUtil.validaString(rr.getSegunda())) {
            rr.setSegunda("N");
        }

        if (OSUtil.validaString(rr.getSegunda())) {
            if ((rr.getSegunda().length() >= 1)
                    && (!rr.getSegunda().equals("S"))
                    && (!rr.getSegunda().equals("N"))) {
                pp.setCodigo(46);
                pp.setMensagem("A informação que está dentro da tag segunda é invalida! Está tag só aceita S ou N.");
                xMLRotaReposicao.getListaErros().getErros().add(pp);
                pp = new ProtocoloProcessos();
            }
        }

        if (!OSUtil.validaString(rr.getTerca())) {
            rr.setTerca("N");
        }

        if (OSUtil.validaString(rr.getTerca())) {
            if ((rr.getTerca().length() >= 1)
                    && (!rr.getTerca().equals("S"))
                    && (!rr.getTerca().equals("N"))) {
                pp.setCodigo(47);
                pp.setMensagem("A informação que está dentro da tag terca é invalida! Está tag só aceita S ou N.");
                xMLRotaReposicao.getListaErros().getErros().add(pp);
                pp = new ProtocoloProcessos();
            }
        }

        if (!OSUtil.validaString(rr.getQuarta())) {
            rr.setQuarta("N");
        }

        if (OSUtil.validaString(rr.getQuarta())) {
            if ((rr.getQuarta().length() >= 1)
                    && (!rr.getQuarta().equals("S"))
                    && (!rr.getQuarta().equals("N"))) {
                pp.setCodigo(48);
                pp.setMensagem("A informação que está dentro da tag quarta é invalida! Está tag só aceita S ou N.");
                xMLRotaReposicao.getListaErros().getErros().add(pp);
                pp = new ProtocoloProcessos();
            }
        }

        if (!OSUtil.validaString(rr.getQuinta())) {
            rr.setQuinta("N");
        }

        if (OSUtil.validaString(rr.getQuinta())) {
            if ((rr.getQuinta().length() >= 1)
                    && (!rr.getQuinta().equals("S"))
                    && (!rr.getQuinta().equals("N"))) {
                pp.setCodigo(49);
                pp.setMensagem("A informação que está dentro da tag quinta é invalida! Está tag só aceita S ou N.");
                xMLRotaReposicao.getListaErros().getErros().add(pp);
                pp = new ProtocoloProcessos();
            }
        }

        if (!OSUtil.validaString(rr.getSexta())) {
            rr.setSexta("N");
        }

        if (OSUtil.validaString(rr.getSexta())) {
            if ((rr.getSexta().length() >= 1)
                    && (!rr.getSexta().equals("S"))
                    && (!rr.getSexta().equals("N"))) {
                pp.setCodigo(50);
                pp.setMensagem("A informação que está dentro da tag sexta é invalida! Está tag só aceita S ou N.");
                xMLRotaReposicao.getListaErros().getErros().add(pp);
                pp = new ProtocoloProcessos();
            }
        }

        if (!OSUtil.validaString(rr.getSabado())) {
            rr.setSabado("N");
        }

        if (OSUtil.validaString(rr.getSabado())) {
            if ((rr.getSabado().length() >= 1)
                    && (!rr.getSabado().equals("S"))
                    && (!rr.getSabado().equals("N"))) {
                pp.setCodigo(51);
                pp.setMensagem("A informação que está dentro da tag sabado é invalida! Está tag só aceita S ou N.");
                xMLRotaReposicao.getListaErros().getErros().add(pp);
                pp = new ProtocoloProcessos();
            }
        }

        if (!OSUtil.validaString(rr.getDomingo())) {
            rr.setDomingo("N");
        }

        if (OSUtil.validaString(rr.getDomingo())) {
            if ((rr.getDomingo().length() >= 1)
                    && (!rr.getDomingo().equals("S"))
                    && (!rr.getDomingo().equals("N"))) {
                pp.setCodigo(52);
                pp.setMensagem("A informação que está dentro da tag domingo é invalida! Está tag só aceita S ou N.");
                xMLRotaReposicao.getListaErros().getErros().add(pp);
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
                getRotaReposicao(processos);
            }
        }).start();

        XMLProtocoloProcessos xMLProtocoloProcessos = new XMLProtocoloProcessos();
        xMLProtocoloProcessos.setPp(pp);
        return OSUtil.xmlListaProtocoloProcesso(xMLProtocoloProcessos);
    }
}
