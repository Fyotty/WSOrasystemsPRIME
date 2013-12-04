/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.RN;

import br.com.orasystems.CTR.EmpresasCTR;
import br.com.orasystems.CTR.ProtocoloProcessosCTR;
import br.com.orasystems.CTR.RepositoresCTR;
import br.com.orasystems.Modelo.Parametros;
import br.com.orasystems.Modelo.ProtocoloProcessos;
import br.com.orasystems.Utilitarios.OSUtil;
import br.com.orasystems.XML.XMLProtocoloProcessos;
import br.com.osprime.CTR.ClientesReposicaoCTR;
import br.com.osprime.CTR.RotaReposicaoCTR;
import br.com.osprime.XML.XMLCargaFull;
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
public class CargaFullRN {

    public static XMLCargaFull xMLCargaFull;

    public void getCargaFull(ProtocoloProcessos pp) {
        xMLCargaFull = new XMLCargaFull();

        StringWriter sw = new StringWriter();

        String nomeArquivo = OSUtil.geraChave();

        OSUtil.salvarArquivoXMLFormatada(
                pp.getArquivo_enviado(), Parametros.caminho_pasta_xmls + "temp\\", nomeArquivo + ".xml");

        try {

            if (OSUtil.verificaTamanhoArquivo(Parametros.caminho_pasta_xmls + "temp\\" + nomeArquivo + ".xml") <= 306376) {

                FileReader reader = new FileReader(Parametros.caminho_pasta_xmls + "temp\\" + nomeArquivo + ".xml");

                //Converte a String em classe
                JAXBContext context = JAXBContext.newInstance(XMLCargaFull.class);
                Unmarshaller um = context.createUnmarshaller();
                xMLCargaFull = (XMLCargaFull) um.unmarshal(reader);
                EmpresasCTR empresasCTR = new EmpresasCTR();
                xMLCargaFull.setEmpresas(empresasCTR.consultaEmpresa(xMLCargaFull.getEmpresas()));

                RepositoresCTR repositoresCTR = new RepositoresCTR();
                xMLCargaFull.getRepositores().setEmpresas(xMLCargaFull.getEmpresas());
                xMLCargaFull.setRepositores(repositoresCTR.consultaRepositor(xMLCargaFull.getRepositores()));
                validaObjeto(xMLCargaFull);
                if (xMLCargaFull.getListaErros().getErros().isEmpty()) {
                    RotaReposicaoCTR rotaReposicaoCTR = new RotaReposicaoCTR();
                    xMLCargaFull = rotaReposicaoCTR.listaRotaReposicao(xMLCargaFull);

                    ClientesReposicaoCTR clientesReposicaoCTR = new ClientesReposicaoCTR();
                    xMLCargaFull = clientesReposicaoCTR.listaClientesReposicao(xMLCargaFull);

                    try {
                        //Create JAXB context and instantiate marshaller
                        JAXBContext contextCarga = JAXBContext.newInstance(XMLCargaFull.class);
                        Marshaller m = contextCarga.createMarshaller();

                        m.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
                        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

                        m.marshal(xMLCargaFull, sw);
                        System.out.println(sw.toString());
                    } catch (Exception e) {
                        OSUtil.error(e.getMessage());
                    }


                } else {
                    pp.setCodigo(1018);
                    pp.setMensagem("Arquivo enviado contêm erros de validação.");
                    xMLCargaFull.getListaErros().getErros().add(pp);
                }


                if (xMLCargaFull.getListaErros().getErros().isEmpty()) {
                    pp.setCodigo(100);
                    pp.setMensagem("Processo de Manutenção de Eventos realizado com sucesso!");
                    xMLCargaFull.getListaErros().getErros().add(pp);
                }

            } else {
                pp.setCodigo(1014);
                pp.setMensagem("Arquivo XML ultrapassa o tamanho máximo permitido de 300KB (306376 Bytes)!");
                xMLCargaFull.getListaErros().getErros().add(pp);
            }

        } catch (FileNotFoundException | JAXBException e) {
            pp.setCodigo(999);
            pp.setMensagem("Arquivo XML inválido ou não está de acordo com o processo realizado!: " + e.getMessage());
            xMLCargaFull.getListaErros().getErros().add(pp);
            OSUtil.error(e.getMessage());
            e.printStackTrace();
        }

        try {
            //Create JAXB context and instantiate marshaller
            XMLProtocoloProcessos xmlpp = new XMLProtocoloProcessos();
            pp.setArquivo_retorno(sw.toString());
            pp.setProcessando("N");
            if (pp.getCodigo() != 100) {
                xmlpp.getListaProcessos().add(xMLCargaFull.getListaErros());
            }
            xmlpp.setPp(pp);

            JAXBContext context = JAXBContext.newInstance(XMLProtocoloProcessos.class);
            Marshaller m = context.createMarshaller();

            m.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            sw = new StringWriter();
            m.marshal(xmlpp, sw);
            if (pp.getCodigo() != 100) {
                pp.setArquivo_retorno(sw.toString());
            }
        } catch (Exception e) {
            OSUtil.error(e.getMessage());
        }
        ProtocoloProcessosCTR.atualizaProtocoloProcessos(pp);
    }

    public void validaObjeto(XMLCargaFull xmlCargaFull) {
        ProtocoloProcessos pp = new ProtocoloProcessos();

        xmlCargaFull.getEmpresas().setCnpj(OSUtil.limpaCNPJ(xmlCargaFull.getEmpresas().getCnpj()));
        if (!OSUtil.validaString(xmlCargaFull.getEmpresas().getCnpj())) {
            pp.setCodigo(33);
            pp.setMensagem("É obrigatório informar o CNPJ da empresa!");
            xMLCargaFull.getListaErros().getErros().add(pp);
            pp = new ProtocoloProcessos();
        }

        if (OSUtil.validaString(xmlCargaFull.getEmpresas().getCnpj())) {
            if (!OSUtil.isCNPJ(xmlCargaFull.getEmpresas().getCnpj())) {
                pp.setCodigo(29);
                pp.setMensagem("Problemas ao validar o CNPJ da Empresa!");
                xMLCargaFull.getListaErros().getErros().add(pp);
                pp = new ProtocoloProcessos();
            }
        }

        if (OSUtil.validaString(xmlCargaFull.getEmpresas().getCnpj())) {
            if (xmlCargaFull.getEmpresas().getCnpj().length() != 14) {
                pp.setCodigo(34);
                pp.setMensagem("O CNPJ da empresa deve conter 14 caracteres!");
                xMLCargaFull.getListaErros().getErros().add(pp);
                pp = new ProtocoloProcessos();
            }
        }

        if (!OSUtil.validaString(xmlCargaFull.getRepositores().getDocumento())) {
            pp.setCodigo(62);
            pp.setMensagem("É obrigatório informar o documento do repositor.");
            xMLCargaFull.getListaErros().getErros().add(pp);
            pp = new ProtocoloProcessos();
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
                getCargaFull(processos);
            }
        }).start();

        XMLProtocoloProcessos xMLProtocoloProcessos = new XMLProtocoloProcessos();
        xMLProtocoloProcessos.setPp(pp);
        return OSUtil.xmlListaProtocoloProcesso(xMLProtocoloProcessos);
    }
}
