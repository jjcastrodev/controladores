/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.afip.dgi.agencia66.tramites.view;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author u84062
 */
public class Inicio {

    /**
     * @param args the command line arguments
     */
    
    private final static Logger LOG_VIEW = Logger.getLogger("gov.afip.dgi.agencia66.tramites.view");
    private final static Logger LOG_CONTROLLER = Logger.getLogger("gov.afip.dgi.agencia66.tramites.controller");
    private final static Logger LOG_MODEL = Logger.getLogger("gov.afip.dgi.agencia66.tramites.model");
    
    private final static Logger LOGGER = Logger.getLogger("gov.afip.dgi.agencia66.tramites.view.Inicio");
    
    public static void main(String[] args) {

            Handler consoleHandler = new ConsoleHandler();
            Handler fileHandler = null;
        try {
            fileHandler = new FileHandler("bitacora.log", false);
        } catch (IOException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            fileHandler.setFormatter(simpleFormatter);
            
            LOG_VIEW.addHandler(consoleHandler);
            LOG_VIEW.addHandler(fileHandler);
            LOG_CONTROLLER.addHandler(consoleHandler);
            LOG_CONTROLLER.addHandler(fileHandler);
            consoleHandler.setLevel(Level.ALL);
            fileHandler.setLevel(Level.ALL);

            
            //Logger.getLogger(Inicio.class.getName()).log(Level.INFO, "Comenzando el proceso a", "Comenzando el proceso");
            //LogManager.getLogManager().readConfiguration(new FileInputStream("d:\\desarrollo/workspace/ControladoresFiscales/log.properties"));
            LOGGER.log(Level.INFO, "Comienzo");
            System.out.println("En PPal");
            Logger.getLogger(Inicio.class.getName()).log(Level.INFO, null, "Comenzando el proceso");
        
        
        PantallaPrincipal pp = new PantallaPrincipal();
        pp.setVisible(true);
    }
    
}
