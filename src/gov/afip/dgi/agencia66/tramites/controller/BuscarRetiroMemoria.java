/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.afip.dgi.agencia66.tramites.controller;

import gov.afip.dgi.agencia66.controller.EntityMan;
import gov.afip.dgi.agencia66.controller.RetiroMemoriaJpaController;

public class BuscarRetiroMemoria {
    
    private final RetiroMemoriaJpaController rmjpa;

    public BuscarRetiroMemoria() {
        this.rmjpa = new RetiroMemoriaJpaController(EntityMan.getInstance());
    }
    
    public boolean existeRM(int idCF) {
        System.out.println("Me llega el id en Controller: " +idCF);
        return rmjpa.exiteRM(idCF);
        
    }

    
}
