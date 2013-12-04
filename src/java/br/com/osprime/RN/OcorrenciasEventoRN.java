/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.RN;

import br.com.orasystems.CTR.EmpresasCTR;
import br.com.orasystems.CTR.ProtocoloProcessosCTR;
import br.com.orasystems.CTR.RepositoresCTR;
import br.com.orasystems.Modelo.ListaErros;
import br.com.orasystems.Modelo.Parametros;
import br.com.orasystems.Modelo.ProtocoloProcessos;
import br.com.orasystems.Modelo.Repositores;
import br.com.orasystems.Utilitarios.OSUtil;
import br.com.orasystems.XML.XMLProtocoloProcessos;
import br.com.osprime.CTR.OcorrenciasEventoCTR;
import br.com.osprime.CTR.OcorrenciasEventoFotosCTR;
import br.com.osprime.Modelo.OcorrenciasEvento;
import br.com.osprime.Modelo.OcorrenciasEventoFotos;
import static br.com.osprime.RN.EventosRN.xMLEventos;
import br.com.osprime.XML.XMLEventos;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 *
 * @author fernando
 */
public class OcorrenciasEventoRN {

    public static ListaErros listaErros;

    public void getOcorrenciasEvento(ProtocoloProcessos pp) {
        listaErros = new ListaErros();

        StringWriter sw = new StringWriter();

        String nomeArquivo = OSUtil.geraChave();

        OSUtil.salvarArquivoXMLFormatada(
                pp.getArquivo_enviado(), Parametros.caminho_pasta_xmls + "temp\\", nomeArquivo + ".xml");

        try {

            if (OSUtil.verificaTamanhoArquivo(Parametros.caminho_pasta_xmls + "temp\\" + nomeArquivo + ".xml") <= 306376) {

                FileReader reader = new FileReader(Parametros.caminho_pasta_xmls + "temp\\" + nomeArquivo + ".xml");

                //Converte a String em classe
                JAXBContext context = JAXBContext.newInstance(OcorrenciasEvento.class);
                Unmarshaller um = context.createUnmarshaller();
                OcorrenciasEvento ocorrenciasEvento = (OcorrenciasEvento) um.unmarshal(reader);

                //aqui vai os processos
                EmpresasCTR empresasCTR = new EmpresasCTR();
                ocorrenciasEvento.setEmpresas(empresasCTR.consultaEmpresa(ocorrenciasEvento.getEmpresas()));
                if (ocorrenciasEvento.getEmpresas().getId() > 0) {

                    RepositoresCTR repositoresCTR = new RepositoresCTR();
                    ocorrenciasEvento.getRepositores().setEmpresas(ocorrenciasEvento.getEmpresas());
                    ocorrenciasEvento.setRepositores(repositoresCTR.consultaRepositor(ocorrenciasEvento.getRepositores()));
                    if (ocorrenciasEvento.getRepositores().getId() > 0) {
                        String caminho = Parametros.caminho_imagens
                                + ocorrenciasEvento.getEmpresas().getPasta_vendedores() + "\\"
                                + ocorrenciasEvento.getRepositores().getId();
                        //verifica se o caminho existe se nao cria
                        OSUtil.consultaCriaPasta(caminho);

                        caminho = caminho + "\\" + OSUtil.geraChave() + ".jpg";
                        ocorrenciasEvento.getOcorrenciasEventoFotos().setCaminho_foto(caminho);

                        OcorrenciasEventoCTR ocorrenciasEventoCTR = new OcorrenciasEventoCTR();
                        
                        //verifica se ja existe ocorrencia do evento
                        ocorrenciasEvento = ocorrenciasEventoCTR.consultaOcorrenciasEvento(ocorrenciasEvento);
                        if (ocorrenciasEvento.getId() == 0) {
                            ocorrenciasEvento = ocorrenciasEventoCTR.gravaOcorrenciasEvento(ocorrenciasEvento);
                        } else {
                            ocorrenciasEvento.getOcorrenciasEventoFotos().setOcorrenciasEvento(ocorrenciasEvento);
                            OcorrenciasEventoFotosCTR ocorrenciasEventoFotosCTR = new OcorrenciasEventoFotosCTR();
                            ocorrenciasEvento.setOcorrenciasEventoFotos(
                                    ocorrenciasEventoFotosCTR.gravaOcorrenciasEventoFotos(
                                    ocorrenciasEvento.getOcorrenciasEventoFotos()));
                        }

                        //verifica se nao ocorreu nenhum problema ao gravar as informacoes
                        if (listaErros.getErros().isEmpty()) {
                            salvarArquivo(ocorrenciasEvento);
                        }

                    } else {
                        //repositor nao existe
                        pp.setCodigo(2);
                        pp.setMensagem("Repositor não existe!");
                        listaErros.getErros().add(pp);
                    }

                } else {
                    //empresa nao existe
                    pp.setCodigo(1);
                    pp.setMensagem("Empresa nao existe!");
                    listaErros.getErros().add(pp);
                }

                if (listaErros.getErros().isEmpty()) {
                    pp.setCodigo(100);
                    pp.setIdRetorno(ocorrenciasEvento.getId());
                    pp.setMensagem("Processo de Manutenção de Eventos realizado com sucesso!");
                    listaErros.getErros().add(pp);
                }

            } else {
                pp.setCodigo(1014);
                pp.setMensagem("Arquivo XML ultrapassa o tamanho máximo permitido de 300KB (306376 Bytes)!");
                listaErros.getErros().add(pp);
            }

        } catch (FileNotFoundException | JAXBException e) {
            pp.setCodigo(999);
            pp.setMensagem("Arquivo XML inválido ou não está de acordo com o processo realizado!: " + e.getMessage());
            listaErros.getErros().add(pp);
            OSUtil.error(e.getMessage());
            e.printStackTrace();
        }

        try {
            //Create JAXB context and instantiate marshaller
            XMLProtocoloProcessos xmlpp = new XMLProtocoloProcessos();
            pp.setProcessando("N");
            if (pp.getCodigo() != 100) {
                xmlpp.getListaProcessos().add(listaErros);
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
        System.out.println(sw.toString());
        ProtocoloProcessosCTR.atualizaProtocoloProcessos(pp);
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
                getOcorrenciasEvento(processos);
            }
        }).start();

        XMLProtocoloProcessos xMLProtocoloProcessos = new XMLProtocoloProcessos();
        xMLProtocoloProcessos.setPp(pp);
        return OSUtil.xmlListaProtocoloProcesso(xMLProtocoloProcessos);
    }

    public Image getImage(byte[] bytes, boolean isThumbnail) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Object source = bis; // File or InputStream
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        //Iterator readers = ImageIO.getImageReadersByFormatName("png");
        Iterator readers = ImageIO.getImageReadersByFormatName("jpg");
        ImageReader reader = (ImageReader) readers.next();
        ImageReadParam param = reader.getDefaultReadParam();
        if (isThumbnail) {
            param.setSourceSubsampling(4, 4, 0, 0);
        }
        reader.setInput(iis, true);
        return reader.read(0, param);
    }

    //public void salvarArquivo(byte[] arquivo, String caminho) {
    public void salvarArquivo(OcorrenciasEvento ocorrenciasEvento) {
        try {
            BufferedImage bImagem =
                    (BufferedImage) getImage(ocorrenciasEvento.getOcorrenciasEventoFotos().getImagem(),
                    false);
            //ImageIO.write(bImagem, "PNG", new File("C:\\juju-delicia234444444.png"));
            ImageIO.write(bImagem,
                    "JPG",
                    new File(ocorrenciasEvento.getOcorrenciasEventoFotos().getCaminho_foto()));
        } catch (Exception e) {
            ProtocoloProcessos pp = new ProtocoloProcessos();
            pp.setCodigo(1064);
            pp.setMensagem("Problemas ao salvar a imagem! Erro: " + e.getMessage());
            listaErros.getErros().add(pp);
            e.printStackTrace();
        }
    }
}
