/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.afip.dgi.agencia66.tramites.controller;

import gov.afip.dgi.agencia66.controller.ControladorFiscalJpaController;
import gov.afip.dgi.agencia66.controller.EntityMan;
import gov.afip.dgi.agencia66.model.ControladorFiscal;

/**
 *
 * @author u84062
 */
public class BuscarControladorFiscal {
    
    private final ControladorFiscalJpaController cfc;

    public BuscarControladorFiscal() {
        this.cfc = new ControladorFiscalJpaController(EntityMan.getInstance());
    }

    public ControladorFiscal getIdControladorFiscal(Double solicitud) {
        return buscoPorId(cfc.findCFxSolicitud(solicitud));
    }
    
    public ControladorFiscal getIdControladorFiscal(Double cuit, int ptoVta) {
        return buscoPorId(cfc.findCFxCuitPvta(cuit, ptoVta));
    }
    
    private ControladorFiscal buscoPorId(int id) { return cfc.findControladorFiscal(id); }
    
}
