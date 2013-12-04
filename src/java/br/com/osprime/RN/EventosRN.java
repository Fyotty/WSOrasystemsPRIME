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
import br.com.osprime.CTR.EventosCTR;
import br.com.osprime.CTR.RotaReposicaoCTR;
import br.com.osprime.Modelo.Eventos;
import br.com.osprime.XML.XMLEventos;
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
public class EventosRN {

    public static XMLEventos xMLEventos;

    public void getEventos(ProtocoloProcessos pp) {
        xMLEventos = new XMLEventos();

        StringWriter sw = new StringWriter();

        String nomeArquivo = OSUtil.geraChave();

        OSUtil.salvarArquivoXMLFormatada(
                pp.getArquivo_enviado(), Parametros.caminho_pasta_xmls + "temp\\", nomeArquivo + ".xml");

        try {

            if (OSUtil.verificaTamanhoArquivo(Parametros.caminho_pasta_xmls + "temp\\" + nomeArquivo + ".xml") <= 306376) {

                FileReader reader = new FileReader(Parametros.caminho_pasta_xmls + "temp\\" + nomeArquivo + ".xml");

                //Converte a String em classe
                JAXBContext context = JAXBContext.newInstance(XMLEventos.class);
                Unmarshaller um = context.createUnmarshaller();
                xMLEventos = (XMLEventos) um.unmarshal(reader);


                validaEmpresaRepositor(xMLEventos);
                if (xMLEventos.getListaErros().getErros().isEmpty()) {

                    EmpresasCTR empresasCTR = new EmpresasCTR();
                    xMLEventos.setEmpresas(empresasCTR.consultaEmpresa(xMLEventos.getEmpresas()));

                    RepositoresCTR repositoresCTR = new RepositoresCTR();
                    xMLEventos.getRepositores().setEmpresas(xMLEventos.getEmpresas());
                    xMLEventos.setRepositores(repositoresCTR.consultaRepositor(xMLEventos.getRepositores()));



                    ClientesReposicaoCTR clientesReposicaoCTR = new ClientesReposicaoCTR();
                    RotaReposicaoCTR rotaReposicaoCTR = new RotaReposicaoCTR();

                    for (Eventos eventos : xMLEventos.getListaEventos()) {

                        //seta os valores da empresa
                        eventos.setEmpresas(xMLEventos.getEmpresas());

                        //seta os valores do repositor
                        eventos.setRepositores(xMLEventos.getRepositores());

                        //set os valores do cliente
                        eventos.getClientesReposicao().setEmpresas(xMLEventos.getEmpresas());
                        eventos.setClientesReposicao(clientesReposicaoCTR.consultaClientesReposicao(eventos.getClientesReposicao()));

                        //set os valores da rota de reposicao
                        eventos.getRotaReposicao().setEmpresas(xMLEventos.getEmpresas());
                        eventos.setRotaReposicao(rotaReposicaoCTR.consultaRotaReposicao(eventos.getRotaReposicao()));

                        //Valida o Cadastro
                        validaObjeto(eventos);
                        if (xMLEventos.getListaErros().getErros().isEmpty()) {
                            EventosCTR cTR = new EventosCTR();
                            eventos = cTR.existeEventos(eventos);
                            if (eventos.getId() == 0) {
                                eventos = cTR.gravaEventos(eventos);
                            } else {
                                eventos = cTR.atualizaEventos(eventos);
                            }
                        } else {
                            pp.setCodigo(1018);
                            pp.setMensagem("Arquivo enviado contêm erros de validação.");
                        }
                    }
                }

                if (xMLEventos.getListaErros().getErros().isEmpty()) {
                    pp.setCodigo(100);
                    pp.setMensagem("Processo de Manutenção de Eventos realizado com sucesso!");
                    xMLEventos.getListaErros().getErros().add(pp);
                }

            } else {
                pp.setCodigo(1014);
                pp.setMensagem("Arquivo XML ultrapassa o tamanho máximo permitido de 300KB (306376 Bytes)!");
                xMLEventos.getListaErros().getErros().add(pp);
            }

        } catch (FileNotFoundException | JAXBException e) {
            pp.setCodigo(999);
            pp.setMensagem("Arquivo XML inválido ou não está de acordo com o processo realizado!: " + e.getMessage());
            xMLEventos.getListaErros().getErros().add(pp);
            OSUtil.error(e.getMessage());
            e.printStackTrace();
        }

        try {
            //Create JAXB context and instantiate marshaller
            XMLProtocoloProcessos xmlpp = new XMLProtocoloProcessos();
            pp.setProcessando("N");
            if (pp.getCodigo() != 100) {
                xmlpp.getListaProcessos().add(xMLEventos.getListaErros());
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

    public void validaEmpresaRepositor(XMLEventos xMLEventos) {
        ProtocoloProcessos pp = new ProtocoloProcessos();

        xMLEventos.getEmpresas().setCnpj(OSUtil.limpaCNPJ(xMLEventos.getEmpresas().getCnpj()));
        if (!OSUtil.validaString(xMLEventos.getEmpresas().getCnpj())) {
            pp.setCodigo(33);
            pp.setMensagem("É obrigatório informar o CNPJ da empresa!");
            xMLEventos.getListaErros().getErros().add(pp);
            pp = new ProtocoloProcessos();
        }

        if (OSUtil.validaString(xMLEventos.getEmpresas().getCnpj())) {
            if (!OSUtil.isCNPJ(xMLEventos.getEmpresas().getCnpj())) {
                pp.setCodigo(29);
                pp.setMensagem("Problemas ao validar o CNPJ da Empresa!");
                xMLEventos.getListaErros().getErros().add(pp);
                pp = new ProtocoloProcessos();
            }
        }

        if (OSUtil.validaString(xMLEventos.getEmpresas().getCnpj())) {
            if (xMLEventos.getEmpresas().getCnpj().length() != 14) {
                pp.setCodigo(34);
                pp.setMensagem("O CNPJ da empresa deve conter 14 caracteres!");
                xMLEventos.getListaErros().getErros().add(pp);
                pp = new ProtocoloProcessos();
            }
        }

        if (!OSUtil.validaString(xMLEventos.getRepositores().getDocumento())) {
            pp.setCodigo(62);
            pp.setMensagem("É obrigatório informar o documento do repositor.");
            xMLEventos.getListaErros().getErros().add(pp);
            pp = new ProtocoloProcessos();
        }
    }

    public void validaObjeto(Eventos eventos) {
        ProtocoloProcessos pp = new ProtocoloProcessos();

        if (eventos.getClientesReposicao().getId() == 0) {
            pp.setCodigo(70);
            pp.setMensagem("Código do cliente de reposição não foi informado!"
                    + "Corrigir cadastro, codigo = " + eventos.getCodigo());
            xMLEventos.getListaErros().getErros().add(pp);
            pp = new ProtocoloProcessos();
        }

        if (eventos.getRotaReposicao().getId() == 0) {
            pp.setCodigo(45);
            pp.setMensagem("Código da rota de reposição não foi informado!"
                    + "Corrigir cadastro, codigo = " + eventos.getCodigo());
            xMLEventos.getListaErros().getErros().add(pp);
            pp = new ProtocoloProcessos();
        }

        if (!OSUtil.validaString(eventos.getNome_evento())) {
            pp.setCodigo(1057);
            pp.setMensagem("Nome do evento não foi informado!"
                    + "Corrigir cadastro, codigo = " + eventos.getCodigo());
            xMLEventos.getListaErros().getErros().add(pp);
            pp = new ProtocoloProcessos();
        }

        if (eventos.getQtd_fotos_evento() == 0) {
            pp.setCodigo(1058);
            pp.setMensagem("Quantidade de fotos do evento não foi informado!"
                    + "Corrigir cadastro, codigo = " + eventos.getCodigo());
            xMLEventos.getListaErros().getErros().add(pp);
            pp = new ProtocoloProcessos();
        }

        if (eventos.getCodigo() == 0) {
            pp.setCodigo(1059);
            pp.setMensagem("Código do evento não foi informado!"
                    + "Corrigir cadastro, codigo = " + eventos.getCodigo());
            xMLEventos.getListaErros().getErros().add(pp);
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
                getEventos(processos);
            }
        }).start();

        XMLProtocoloProcessos xMLProtocoloProcessos = new XMLProtocoloProcessos();
        xMLProtocoloProcessos.setPp(pp);
        return OSUtil.xmlListaProtocoloProcesso(xMLProtocoloProcessos);
    }
}
