/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.osprime.Servico;

import br.com.orasystems.RN.EmpresasRN;
import br.com.orasystems.RN.ProtocoloProcessosRN;
import br.com.orasystems.RN.RepositorRN;
import br.com.osprime.RN.CargaFullRN;
import br.com.osprime.RN.ClientesReposicaoRN;
import br.com.osprime.RN.EventosRN;
import br.com.osprime.RN.OcorrenciasEventoRN;
import br.com.osprime.RN.RepositorDespCombustiveisRN;
import br.com.osprime.RN.RepositorDespesasRN;
import br.com.osprime.RN.RotaReposicaoRN;
import br.com.osprime.RN.UltimaCompraReposicaoRN;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.soap.SOAPBinding;

/**
 *
 * @author fernando
 */
@WebService(serviceName = "WSPrime")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class WSPrime {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "recebeEmpresa")
    public String recebeEmpresa(@WebParam(name = "xmlEmpresa") String xmlEmpresa) {
        EmpresasRN rN = new EmpresasRN();
        
        return rN.getEmpresa(xmlEmpresa);
    }
    
    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "recebeRepositor")
    public String recebeRepositor(@WebParam(name = "xmlRepositor") String xmlRepositor) {
        RepositorRN rN = new RepositorRN();
        
        return rN.getRepositor(xmlRepositor);
    }
    
    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "recebeClientesReposicao")
    public String recebeClientesReposicao(@WebParam(name = "xmlClientesReposicao") String xmlClientesReposicao) {
        ClientesReposicaoRN rN = new ClientesReposicaoRN();
        
        return rN.getProtocoloProcesso(xmlClientesReposicao);
    }
    
    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "recebeRotaReposicao")
    public String recebeRotaReposicao(@WebParam(name = "xmlRotaReposicao") String xmlRotaReposicao) {
        RotaReposicaoRN rN = new RotaReposicaoRN();
        
        return rN.getProtocoloProcesso(xmlRotaReposicao);
    }
    
    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "loginRepositor")
    public String loginRepositor(@WebParam(name = "xmlLoginRepositor") String xmlLoginRepositor) {
        RepositorRN rN = new RepositorRN();
        
        return rN.getLoginRepositor(xmlLoginRepositor);
    }
    
    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "recebeRepositorDespesas")
    public String recebeRepositorDespesas(@WebParam(name = "xmlRepositorDespesas") String xmlRepositorDespesas) {
        RepositorDespesasRN rN = new RepositorDespesasRN();
        
        return rN.getProtocoloProcesso(xmlRepositorDespesas);
    }
    
    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "consultaProtocoloProcesso")
    public String consultaProtocoloProcesso(@WebParam(name = "xmlProtocoloProcesso") String xmlProtocoloProcesso) {
        ProtocoloProcessosRN rN = new ProtocoloProcessosRN();
        
        return rN.retornaXMLSolicitacaoProtocolo(xmlProtocoloProcesso);
    }
    
    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "recebeRepositorDespCombustiveis")
    public String recebeRepositorDespCombustiveis(@WebParam(name = "xmlRepositorDespCombustiveis") String xmlRepositorDespCombustiveis) {
        RepositorDespCombustiveisRN rN = new RepositorDespCombustiveisRN();
        
        return rN.getProtocoloProcesso(xmlRepositorDespCombustiveis);
    }
    
    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "recebeUltimaCompraReposicao")
    public String recebeUltimaCompraReposicao(@WebParam(name = "xmlUltimaCompraReposicao") String xmlUltimaCompraReposicao) {
        UltimaCompraReposicaoRN rN = new UltimaCompraReposicaoRN();
        
        return rN.getProtocoloProcesso(xmlUltimaCompraReposicao);
    }
    
    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "recebeEvento")
    public String recebeEvento(@WebParam(name = "xmlEvento") String xmlEvento) {
        EventosRN rN = new EventosRN();
        
        return rN.getProtocoloProcesso(xmlEvento);
    }
    
    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "atualizaCargaFull")
    public String atualizaCargaFull(@WebParam(name = "xmlCargaFull") String xmlCargaFull) {
        CargaFullRN rN = new CargaFullRN();
        
        return rN.getProtocoloProcesso(xmlCargaFull);
    }
    
    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "recebeOcorrenciasEvento")
    public String recebeOcorrenciasEvento(@WebParam(name = "xmlOcorrenciasEvento") String xmlOcorrenciasEvento) {
        OcorrenciasEventoRN rN = new OcorrenciasEventoRN();
        
        return rN.getProtocoloProcesso(xmlOcorrenciasEvento);
    }
}
