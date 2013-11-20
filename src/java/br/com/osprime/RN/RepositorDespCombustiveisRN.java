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
import br.com.osprime.CTR.RepositorDespCombustiveisCTR;
import br.com.osprime.CTR.RepositorDespesasCTR;
import br.com.osprime.Modelo.RepositorDespCombustiveis;
import br.com.osprime.Modelo.RepositorDespesas;
import br.com.osprime.XML.XMLRepositorDespCombustiveis;
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
public class RepositorDespCombustiveisRN {

    public static XMLRepositorDespCombustiveis xMLRepositorDespCombustiveis;

    public void getRepositorDespCombustiveis(ProtocoloProcessos pp) {
        xMLRepositorDespCombustiveis = new XMLRepositorDespCombustiveis();

        StringWriter sw = new StringWriter();

        String nomeArquivo = OSUtil.geraChave();

        OSUtil.salvarArquivoXMLFormatada(
                pp.getArquivo_enviado(), Parametros.caminho_pasta_xmls + "temp\\", nomeArquivo + ".xml");

        try {

            if (OSUtil.verificaTamanhoArquivo(Parametros.caminho_pasta_xmls + "temp\\" + nomeArquivo + ".xml") <= 306376) {

                FileReader reader = new FileReader(Parametros.caminho_pasta_xmls + "temp\\" + nomeArquivo + ".xml");

                //Converte a String em classe
                JAXBContext context = JAXBContext.newInstance(XMLRepositorDespCombustiveis.class);
                Unmarshaller um = context.createUnmarshaller();
                xMLRepositorDespCombustiveis = (XMLRepositorDespCombustiveis) um.unmarshal(reader);
                EmpresasCTR empresasCTR = new EmpresasCTR();
                RepositoresCTR repositoresCTR = new RepositoresCTR();
                for (RepositorDespCombustiveis rdc : xMLRepositorDespCombustiveis.getListaRepositorDespCombustiveis()) {

                    rdc.setEmpresas(empresasCTR.consultaEmpresa(xMLRepositorDespCombustiveis.getEmpresas()));
                    xMLRepositorDespCombustiveis.getRepositores().setEmpresas(rdc.getEmpresas());
                    //rd.getRepositores().setEmpresas(rd.getEmpresas());
                    rdc.setRepositores(repositoresCTR.consultaRepositor(xMLRepositorDespCombustiveis.getRepositores()));
                    //Valida o Cadastro
                    validaObjeto(rdc);
                    if (xMLRepositorDespCombustiveis.getListaErros().getErros().isEmpty()) {
                        RepositorDespCombustiveisCTR cTR = new RepositorDespCombustiveisCTR();
                        rdc = cTR.gravaRepositorDespCombustiveis(rdc);
                    }
                }

                if (xMLRepositorDespCombustiveis.getListaErros().getErros().isEmpty()) {
                    pp.setCodigo(100);
                    pp.setMensagem("Despesas com combutivel incluidas com sucesso!");
                    xMLRepositorDespCombustiveis.getListaErros().getErros().add(pp);
                }

            } else {
                pp.setCodigo(1014);
                pp.setMensagem("Arquivo XML ultrapassa o tamanho máximo permitido de 300KB (306376 Bytes)!");
                xMLRepositorDespCombustiveis.getListaErros().getErros().add(pp);
            }

        } catch (FileNotFoundException | JAXBException e) {
            pp.setCodigo(999);
            pp.setMensagem("Arquivo XML inválido ou não está de acordo com o processo realizado!: " + e.getMessage());
            xMLRepositorDespCombustiveis.getListaErros().getErros().add(pp);
            OSUtil.error(e.getMessage());
            e.printStackTrace();
        }

        try {
            //Create JAXB context and instantiate marshaller
            XMLProtocoloProcessos xmlpp = new XMLProtocoloProcessos();
            pp.setProcessando("N");
            if (pp.getCodigo() != 100) {
                xmlpp.getListaProcessos().add(xMLRepositorDespCombustiveis.getListaErros());
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

    public void validaObjeto(RepositorDespCombustiveis rdc) {
        ProtocoloProcessos pp = new ProtocoloProcessos();

        rdc.getEmpresas().setCnpj(OSUtil.limpaCNPJ(rdc.getEmpresas().getCnpj()));
        if (!OSUtil.validaString(rdc.getEmpresas().getCnpj())) {
            pp.setCodigo(33);
            pp.setMensagem("É obrigatório informar o CNPJ da empresa!");
            xMLRepositorDespCombustiveis.getListaErros().getErros().add(pp);
        }

        if (OSUtil.validaString(rdc.getEmpresas().getCnpj())) {
            if (!OSUtil.isCNPJ(rdc.getEmpresas().getCnpj())) {
                pp = new ProtocoloProcessos();
                pp.setCodigo(29);
                pp.setMensagem("Problemas ao validar o CNPJ da Empresa!");
                xMLRepositorDespCombustiveis.getListaErros().getErros().add(pp);
            }
        }

        if (OSUtil.validaString(rdc.getEmpresas().getCnpj())) {
            if (rdc.getEmpresas().getCnpj().length() != 14) {
                pp = new ProtocoloProcessos();
                pp.setCodigo(34);
                pp.setMensagem("O CNPJ da empresa deve conter 14 caracteres!");
                xMLRepositorDespCombustiveis.getListaErros().getErros().add(pp);
            }
        }

        if (!OSUtil.validaString(rdc.getRepositores().getDocumento())) {
            pp = new ProtocoloProcessos();
            pp.setCodigo(62);
            pp.setMensagem("É obrigatório informar o documento do repositor.");
            xMLRepositorDespCombustiveis.getListaErros().getErros().add(pp);
        }
        /*fshdaifhauisdhfuiasdhuifh uisadhfuihasduihas*/
        if (rdc.getData() == null) {
            pp = new ProtocoloProcessos();
            pp.setCodigo(1042);
            pp.setMensagem("É obrigatório informar a data despesa com o combustível.");
            xMLRepositorDespCombustiveis.getListaErros().getErros().add(pp);
        }

        if (!OSUtil.validaString(rdc.getDia_semana())) {
            pp = new ProtocoloProcessos();
            pp.setCodigo(1043);
            pp.setMensagem("É obrigatório informar o dia da semana.");
            xMLRepositorDespCombustiveis.getListaErros().getErros().add(pp);
        }

        if (!OSUtil.validaString(rdc.getLocal_partida())) {
            pp = new ProtocoloProcessos();
            pp.setCodigo(1044);
            pp.setMensagem("É obrigatório informar o local de partida.");
            xMLRepositorDespCombustiveis.getListaErros().getErros().add(pp);
        }

        if (!OSUtil.validaString(rdc.getLocal_chegada())) {
            pp = new ProtocoloProcessos();
            pp.setCodigo(1045);
            pp.setMensagem("É obrigatório informar o local de chegada.");
            xMLRepositorDespCombustiveis.getListaErros().getErros().add(pp);
        }

        if (rdc.getKm_inicial() == 0) {
            pp = new ProtocoloProcessos();
            pp.setCodigo(1046);
            pp.setMensagem("É obrigatório informar o KM inicial.");
            xMLRepositorDespCombustiveis.getListaErros().getErros().add(pp);
        }

        if (rdc.getKm_final() == 0) {
            pp = new ProtocoloProcessos();
            pp.setCodigo(1047);
            pp.setMensagem("É obrigatório informar o KM final.");
            xMLRepositorDespCombustiveis.getListaErros().getErros().add(pp);
        }

        if (rdc.getTotal_km() == 0) {
            pp = new ProtocoloProcessos();
            pp.setCodigo(1048);
            pp.setMensagem("É obrigatório informar o total de KM.");
            xMLRepositorDespCombustiveis.getListaErros().getErros().add(pp);
        }

        if (rdc.getMedia_veiculo() == 0) {
            pp = new ProtocoloProcessos();
            pp.setCodigo(1049);
            pp.setMensagem("É obrigatório informar a média do veiculo.");
            xMLRepositorDespCombustiveis.getListaErros().getErros().add(pp);
        }

        if (rdc.getConsumo_dia() == 0) {
            pp = new ProtocoloProcessos();
            pp.setCodigo(1050);
            pp.setMensagem("É obrigatório informar o consumo do dia.");
            xMLRepositorDespCombustiveis.getListaErros().getErros().add(pp);
        }

        if (rdc.getPreco_combustivel() == 0) {
            pp = new ProtocoloProcessos();
            pp.setCodigo(1051);
            pp.setMensagem("É obrigatório informar o preço do combustível.");
            xMLRepositorDespCombustiveis.getListaErros().getErros().add(pp);
        }

        if (rdc.getSubtotal() == 0) {
            pp = new ProtocoloProcessos();
            pp.setCodigo(1052);
            pp.setMensagem("É obrigatório informar o subtotal.");
            xMLRepositorDespCombustiveis.getListaErros().getErros().add(pp);
        }

        if (!OSUtil.validaString(rdc.getVeiculo())) {
            pp = new ProtocoloProcessos();
            pp.setCodigo(1053);
            pp.setMensagem("É obrigatório informar a descrição do veículo.");
            xMLRepositorDespCombustiveis.getListaErros().getErros().add(pp);
        }

        if (!OSUtil.validaString(rdc.getPlaca())) {
            pp = new ProtocoloProcessos();
            pp.setCodigo(1054);
            pp.setMensagem("É obrigatório informar a placa do veículo.");
            xMLRepositorDespCombustiveis.getListaErros().getErros().add(pp);
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
                getRepositorDespCombustiveis(processos);
            }
        }).start();

        XMLProtocoloProcessos xMLProtocoloProcessos = new XMLProtocoloProcessos();
        xMLProtocoloProcessos.setPp(pp);
        return OSUtil.xmlListaProtocoloProcesso(xMLProtocoloProcessos);
    }
}
