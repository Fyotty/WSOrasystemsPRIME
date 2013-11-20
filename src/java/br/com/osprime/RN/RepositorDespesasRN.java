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
import br.com.osprime.CTR.RepositorDespesasCTR;
import br.com.osprime.Modelo.RepositorDespesas;
import br.com.osprime.XML.XMLRepositorDespesas;
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
public class RepositorDespesasRN {

    public static XMLRepositorDespesas xMLRepositorDespesas;

    public void getRepositorDespesas(ProtocoloProcessos pp) {
        xMLRepositorDespesas = new XMLRepositorDespesas();

        StringWriter sw = new StringWriter();

        String nomeArquivo = OSUtil.geraChave();

        OSUtil.salvarArquivoXMLFormatada(
                pp.getArquivo_enviado(), Parametros.caminho_pasta_xmls + "temp\\", nomeArquivo + ".xml");

        try {

            if (OSUtil.verificaTamanhoArquivo(Parametros.caminho_pasta_xmls + "temp\\" + nomeArquivo + ".xml") <= 306376) {

                FileReader reader = new FileReader(Parametros.caminho_pasta_xmls + "temp\\" + nomeArquivo + ".xml");

                //Converte a String em classe
                JAXBContext context = JAXBContext.newInstance(XMLRepositorDespesas.class);
                Unmarshaller um = context.createUnmarshaller();
                xMLRepositorDespesas = (XMLRepositorDespesas) um.unmarshal(reader);
                EmpresasCTR empresasCTR = new EmpresasCTR();
                RepositoresCTR repositoresCTR = new RepositoresCTR();
                for (RepositorDespesas rd : xMLRepositorDespesas.getListaRepositorDespesas()) {

                    rd.setEmpresas(empresasCTR.consultaEmpresa(xMLRepositorDespesas.getEmpresas()));
                    xMLRepositorDespesas.getRepositores().setEmpresas(rd.getEmpresas());
                    //rd.getRepositores().setEmpresas(rd.getEmpresas());
                    rd.setRepositores(repositoresCTR.consultaRepositor(xMLRepositorDespesas.getRepositores()));
                    //Valida o Cadastro
                    validaObjeto(rd);
                    if (xMLRepositorDespesas.getListaErros().getErros().isEmpty()) {
                        RepositorDespesasCTR cTR = new RepositorDespesasCTR();
                        rd = cTR.gravaRepositorDespesas(rd);
                    }
                }

                if (xMLRepositorDespesas.getListaErros().getErros().isEmpty()) {
                    pp.setCodigo(100);
                    pp.setMensagem("Despesas incluidas com sucesso!");
                    xMLRepositorDespesas.getListaErros().getErros().add(pp);
                }

            } else {
                pp.setCodigo(1014);
                pp.setMensagem("Arquivo XML ultrapassa o tamanho máximo permitido de 300KB (306376 Bytes)!");
                xMLRepositorDespesas.getListaErros().getErros().add(pp);
            }

        } catch (FileNotFoundException | JAXBException e) {
            pp.setCodigo(999);
            pp.setMensagem("Arquivo XML inválido ou não está de acordo com o processo realizado!: " + e.getMessage());
            xMLRepositorDespesas.getListaErros().getErros().add(pp);
            OSUtil.error(e.getMessage());
            e.printStackTrace();
        }
        
        try {
            //Create JAXB context and instantiate marshaller
            XMLProtocoloProcessos xmlpp = new XMLProtocoloProcessos();
            pp.setProcessando("N");
            if (pp.getCodigo() != 100) {
                xmlpp.getListaProcessos().add(xMLRepositorDespesas.getListaErros());
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
            e.printStackTrace();
        }
        ProtocoloProcessosCTR.atualizaProtocoloProcessos(pp);

    }

    public void validaObjeto(RepositorDespesas rd) {
        ProtocoloProcessos pp = new ProtocoloProcessos();

        rd.getEmpresas().setCnpj(OSUtil.limpaCNPJ(rd.getEmpresas().getCnpj()));
        if (!OSUtil.validaString(rd.getEmpresas().getCnpj())) {
            pp.setCodigo(33);
            pp.setMensagem("É obrigatório informar o CNPJ da empresa!");
            xMLRepositorDespesas.getListaErros().getErros().add(pp);
        }

        if (OSUtil.validaString(rd.getEmpresas().getCnpj())) {
            if (!OSUtil.isCNPJ(rd.getEmpresas().getCnpj())) {
                pp = new ProtocoloProcessos();
                pp.setCodigo(29);
                pp.setMensagem("Problemas ao validar o CNPJ da Empresa!");
                xMLRepositorDespesas.getListaErros().getErros().add(pp);
            }
        }

        if (OSUtil.validaString(rd.getEmpresas().getCnpj())) {
            if (rd.getEmpresas().getCnpj().length() != 14) {
                pp = new ProtocoloProcessos();
                pp.setCodigo(34);
                pp.setMensagem("O CNPJ da empresa deve conter 14 caracteres!");
                xMLRepositorDespesas.getListaErros().getErros().add(pp);
            }
        }

        if (!OSUtil.validaString(rd.getRepositores().getDocumento())) {
            pp = new ProtocoloProcessos();
            pp.setCodigo(62);
            pp.setMensagem("É obrigatório informar o documento do repositor.");
            xMLRepositorDespesas.getListaErros().getErros().add(pp);
        }
        
        if (rd.getRepositores().getId() == 0) {
            pp = new ProtocoloProcessos();
            pp.setCodigo(2);
            pp.setMensagem("Repositor não existe!");
            xMLRepositorDespesas.getListaErros().getErros().add(pp);
        }
        
        if (!OSUtil.validaString(rd.getDescricao())) {
            pp = new ProtocoloProcessos();
            pp.setCodigo(1014);
            pp.setMensagem("É obrigatório informar a descrição da despesa.");
            xMLRepositorDespesas.getListaErros().getErros().add(pp);
        }
        
        if (rd.getData() == null) {
            pp = new ProtocoloProcessos();
            pp.setCodigo(1015);
            pp.setMensagem("É obrigatório informar a data da despesa.");
            xMLRepositorDespesas.getListaErros().getErros().add(pp);
        }
        
        if (rd.getValor() <= 0) {
            pp = new ProtocoloProcessos();
            pp.setCodigo(1016);
            pp.setMensagem("É obrigatório informar o valor da despesa.");
            xMLRepositorDespesas.getListaErros().getErros().add(pp);
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
                getRepositorDespesas(processos);
            }
        }).start();

        XMLProtocoloProcessos xMLProtocoloProcessos = new XMLProtocoloProcessos();
        xMLProtocoloProcessos.setPp(pp);
        return OSUtil.xmlListaProtocoloProcesso(xMLProtocoloProcessos);
    }
}
