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
import br.com.osprime.CTR.ClientesReposicaoCTR;
import br.com.osprime.CTR.RotaReposicaoCTR;
import br.com.osprime.Modelo.ClientesReposicao;
import static br.com.osprime.RN.RotaReposicaoRN.xMLRotaReposicao;
import br.com.osprime.XML.XMLClientesReposicao;
import br.com.osprime.XML.XMLRotaReposicao;
import java.io.File;
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
public class ClientesReposicaoRN {

    public static XMLClientesReposicao xMLClientesReposicao;

    public String getClientesReposicao(ProtocoloProcessos pp) {
        xMLRotaReposicao = new XMLRotaReposicao();

        StringWriter sw = new StringWriter();

        String nomeArquivo = OSUtil.geraChave();

        OSUtil.salvarArquivoXMLFormatada(
                pp.getArquivo_enviado(), Parametros.caminho_pasta_xmls + "temp\\", nomeArquivo + ".xml");

        try {

            if (OSUtil.verificaTamanhoArquivo("./temp/" + nomeArquivo + ".xml") <= 306376) {

                FileReader reader = new FileReader("./temp/" + nomeArquivo + ".xml");

                //Converte a String em classe
                JAXBContext context = JAXBContext.newInstance(XMLClientesReposicao.class);
                Unmarshaller um = context.createUnmarshaller();
                xMLClientesReposicao = (XMLClientesReposicao) um.unmarshal(reader);

                validaEmpresaRepositor(xMLClientesReposicao);
                if (xMLClientesReposicao.getListaErros().getErros().isEmpty()) {

                    for (ClientesReposicao cr : xMLClientesReposicao.getListaClientesReposicaos()) {

                        cr.setEmpresas(xMLClientesReposicao.getEmpresas());
                        //Valida o Cadastro
                        validaObjeto(cr);
                        if (xMLClientesReposicao.getListaErros().getErros().isEmpty()) {
                            ClientesReposicaoCTR cTR = new ClientesReposicaoCTR();
                            cr = cTR.existeClientesReposicao(cr);
                            if (cr.getId() == 0) {
                                cr = cTR.gravaClientesReposicao(cr);
                            } else {
                                cr = cTR.atualizaClientesReposicao(cr);
                            }
                        }
                    }

                }

                if (xMLClientesReposicao.getListaErros().getErros().isEmpty()) {
                    pp.setCodigo(100);
                    pp.setMensagem("Processo de Manutenção do Cliente de Reposição realizado com sucesso!");
                    xMLClientesReposicao.getListaErros().getErros().add(pp);
                }

            } else {
                pp.setCodigo(1014);
                pp.setMensagem("Arquivo XML ultrapassa o tamanho máximo permitido de 300KB (306376 Bytes)!");
                xMLClientesReposicao.getListaErros().getErros().add(pp);
            }
            
        } catch (FileNotFoundException | JAXBException e) {
            pp.setCodigo(999);
            pp.setMensagem("Arquivo XML inválido ou não está de acordo com o processo realizado!: " + e.getMessage());
            xMLClientesReposicao.getListaErros().getErros().add(pp);
            OSUtil.error(e.getMessage());
        }

        try {
            //Create JAXB context and instantiate marshaller
            JAXBContext context = JAXBContext.newInstance(ListaErros.class);
            Marshaller m = context.createMarshaller();

            m.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            sw = new StringWriter();
            m.marshal(xMLClientesReposicao.getListaErros(), sw);
        } catch (Exception e) {
            OSUtil.error(e.getMessage());
        }

        return sw.toString();
    }

    public void validaObjeto(ClientesReposicao cr) {
        ProtocoloProcessos pp = new ProtocoloProcessos();

        if (cr.getCodigo() == 0) {
            pp.setCodigo(70);
            pp.setMensagem("Código do cliente de reposição não foi informado!");
            xMLClientesReposicao.getListaErros().getErros().add(pp);
        }

        if (!OSUtil.validaString(cr.getRazao_social())) {
            pp.setCodigo(71);
            pp.setMensagem("Não foi informada a Razão Social ou o Nome do cliente de reposição!");
            xMLClientesReposicao.getListaErros().getErros().add(pp);
        }

        if (OSUtil.validaString(cr.getRazao_social())) {
            if (cr.getRazao_social().length() > 100) {
                pp.setCodigo(72);
                pp.setMensagem("A Razão Social ou o Nome do cliente de reposição não pode ultrapassar 100 caracteres! Corrigir cadastro.");
                xMLClientesReposicao.getListaErros().getErros().add(pp);
            }
        }

        if (!OSUtil.validaString(cr.getNome_fantasia())) {
            pp.setCodigo(73);
            pp.setMensagem("Não foi informado o Nome Fantasia do cliente de reposição!");
            xMLClientesReposicao.getListaErros().getErros().add(pp);
        }

        if (OSUtil.validaString(cr.getRazao_social())) {
            if (cr.getRazao_social().length() > 100) {
                pp.setCodigo(74);
                pp.setMensagem("O Nome Fantasia ou o Nome do cliente de reposição não pode ultrapassar 100 caracteres! Por favor corrigir cadastro.");
                xMLClientesReposicao.getListaErros().getErros().add(pp);
            }
        }

        if (!OSUtil.validaString(cr.getCnpj())) {
            pp.setCodigo(75);
            pp.setMensagem("Não foi informado o CNPJ ou CPF do cliente de reposição!");
            xMLClientesReposicao.getListaErros().getErros().add(pp);
        } else {
            cr.setCnpj(OSUtil.limpaCNPJ(cr.getCnpj()));
        }

        if (OSUtil.validaString(cr.getCnpj())) {
            if (cr.getCnpj().length() >= 14) {
                if (!OSUtil.isCNPJ(cr.getCnpj())) {
                    pp.setCodigo(76);
                    pp.setMensagem("Problemas ao validar o CNPJ do cliente de reposição!");
                    xMLClientesReposicao.getListaErros().getErros().add(pp);
                }
            }
        }

        if (!OSUtil.validaString(cr.getIestad())) {
            pp.setCodigo(77);
            pp.setMensagem("Não foi informada a I.E. ou R.G. do cliente de reposição!");
            xMLClientesReposicao.getListaErros().getErros().add(pp);
        }

        if (cr.getRr().getCodigo() == 0) {
            pp.setCodigo(78);
            pp.setMensagem("Não foi informada a rota de reposição do cliente de reposição! Caso não houver informe uma padrão.");
            xMLClientesReposicao.getListaErros().getErros().add(pp);
        }

        if (cr.getRr().getCodigo() > 0) {
            RotaReposicaoCTR rotaReposicaoCTR = new RotaReposicaoCTR();
            cr.getRr().setEmpresas(cr.getEmpresas());
            cr.setRr(rotaReposicaoCTR.consultaRotaReposicao(cr.getRr()));
            if (cr.getRr().getId() <= 0) {
                pp.setCodigo(1001);
                pp.setMensagem("Rota de Reposição não existe!");
                xMLClientesReposicao.getListaErros().getErros().add(pp);
            }
        }
    }

    public void validaEmpresaRepositor(XMLClientesReposicao xMLClientesReposicao) {
        ProtocoloProcessos pp = new ProtocoloProcessos();

        xMLClientesReposicao.getEmpresas().setCnpj(OSUtil.limpaCNPJ(xMLClientesReposicao.getEmpresas().getCnpj()));
        if (!OSUtil.validaString(xMLClientesReposicao.getEmpresas().getCnpj())) {
            pp.setCodigo(33);
            pp.setMensagem("É obrigatório informar o CNPJ da empresa!");
            xMLClientesReposicao.getListaErros().getErros().add(pp);
        }

        if (OSUtil.validaString(xMLClientesReposicao.getEmpresas().getCnpj())) {
            if (!OSUtil.isCNPJ(xMLClientesReposicao.getEmpresas().getCnpj())) {
                pp.setCodigo(29);
                pp.setMensagem("Problemas ao validar o CNPJ da Empresa!");
                xMLClientesReposicao.getListaErros().getErros().add(pp);
            }
        }

        if (OSUtil.validaString(xMLClientesReposicao.getEmpresas().getCnpj())) {
            if (xMLClientesReposicao.getEmpresas().getCnpj().length() != 14) {
                pp.setCodigo(34);
                pp.setMensagem("O CNPJ da empresa deve conter 14 caracteres!");
                xMLClientesReposicao.getListaErros().getErros().add(pp);
            }
        }

        if (xMLClientesReposicao.getListaErros().getErros().isEmpty()) {
            EmpresasCTR empresasCTR = new EmpresasCTR();
            xMLClientesReposicao.setEmpresas(empresasCTR.consultaEmpresa(xMLClientesReposicao.getEmpresas()));
        }

        if (xMLClientesReposicao.getEmpresas().getId() <= 0) {
            pp.setCodigo(1);
            pp.setMensagem("Empresa nao existe!");
            xMLClientesReposicao.getListaErros().getErros().add(pp);
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
                getClientesReposicao(processos);
            }
        }).start();

        return OSUtil.xmlRetornoProtocoloProcesso(pp);
    }
}
