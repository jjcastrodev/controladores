/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.afip.dgi.agencia66.tramites.controller;

import gov.afip.dgi.agencia66.model.Agentes;
import gov.afip.dgi.agencia66.model.ParCaracter;
import gov.afip.dgi.agencia66.model.Tramites;
import gov.afip.dgi.agencia66.model.ParLoader;
import gov.afip.dgi.agencia66.util.bd.ConectarMultiBases;
import gov.afip.dgi.agencia66.controller.*;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JTextField;
import static org.eclipse.persistence.jpa.jpql.utility.CollectionTools.array;

/**
 *
 * @author u84062
 */
public class LoaderDeInicio {
    
    private ConectarMultiBases cmb;
    
   /* public LoaderDeInicio() {
        
            cmb = new ConectarMultiBases();
            cmb.conectarSqlite("//D:\\desarrollo\\sistemas.bd");
    }
    */
    
    /*
      @deprecated utilizar getOperadores()
       Se utiiza cuando no quiero usar el modelo jpa e hibernate
       se deben manejar los datos de la BD a mano, haciendo los querys
             
    */
    @Deprecated public ArrayList cargarOperadores() {
        
        ArrayList<Agentes> a_agente = new ArrayList<Agentes>();
        Agentes agente;
        try {
            cmb = new ConectarMultiBases();
            cmb.conectarSqlite("//d:\\desarrollo\\bd\\sistemas.db");
            PreparedStatement ps = cmb.getConection().prepareStatement("select nombre, legajo from agentes");
            ResultSet res = ps.executeQuery();
            String n, le;
            while(res.next()) {
                agente = new Agentes();
                agente.setNombre(res.getString(1));
                agente.setLegajo(res.getString(2));
                a_agente.add(agente);
            }
        }
        catch(SQLException sql) {
            System.err.println("Error: " +sql);
        }
        return a_agente;
    }
    
    public List getOperadores() {
        AgentesJpaController ajpc = new AgentesJpaController(EntityMan.getInstance());
        List lista = ajpc.findAgentesEntities();
         return lista;
    }
    
    public List getTramites() {
        TramitesJpaController tjpc = new TramitesJpaController(EntityMan.getInstance());
        List lista = tjpc.findTramitesEntities();
        return lista;
    }
    
    public List getCaracter() {
        ParCaracterJpaController pcjp = new ParCaracterJpaController(EntityMan.getInstance());
        List lista = pcjp.findParCaracterEntities();
        return lista;
    }

    public List getListaLoader() {
        ParLoaderJpaController pl = new ParLoaderJpaController(EntityMan.getInstance());
        return pl.findParLoaderEntities();
    }

}
