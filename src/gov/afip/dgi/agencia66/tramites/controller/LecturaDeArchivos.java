/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.afip.dgi.agencia66.tramites.controller;

import gov.afip.dgi.agencia66.tramites.view.LeerControladorFiscal;
import gov.afip.dgi.agencia66.util.FormateaSalidasTexto;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTextField;
/**
 *
 * @author u84062
 */
public class LecturaDeArchivos {
    
    private String cuit, razon_social, codigo, numero, fecha_extraccion, fecha_inicio, fecha_fin, documento, txtCuit ;
    FormateaSalidasTexto fst = new FormateaSalidasTexto();
    
    public void setDocumento(String documento) {
        this.documento = fst.cuitADocumento(documento);
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFecha_extraccion() {
        return fecha_extraccion;
    }

    public void setFecha_extraccion(String fecha_extraccion) {
        this.fecha_extraccion = fecha_extraccion;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public int getPtoVta() {
        return ptoVta;
    }

    public void setPtoVta(int ptoVta) {
        this.ptoVta = ptoVta;
    }

    public int getCompIni() {
        return compIni;
    }

    public void setCompIni(int compIni) {
        this.compIni = compIni;
    }

    public int getCompFinal() {
        return compFinal;
    }

    public void setCompFinal(int compFinal) {
        this.compFinal = compFinal;
    }

    public int getUltimoB() {
        return ultimoB;
    }

    public void setUltimoB(int ultimoB) {
        this.ultimoB = ultimoB;
    }

    public int getUltimoA() {
        return ultimoA;
    }

    public void setUltimoA(int ultimoA) {
        this.ultimoA = ultimoA;
    }

    public int getCancelados() {
        return cancelados;
    }

    public void setCancelados(int cancelados) {
        this.cancelados = cancelados;
    }

    public int getBloqueo() {
        return bloqueo;
    }

    public void setBloqueo(int bloqueo) {
        this.bloqueo = bloqueo;
    }

    /** javadoc 
     * @author JJC
     * @return importe total de la lectura del CF
     */
    public Double getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(Double importeTotal) {
        this.importeTotal = importeTotal;
    }

    public Double getImporteIva() {
        return importeIva;
    }

    public void setImporteIva(Double importeIva) {
        this.importeIva = importeIva;
    }
    
    public String getTxtCuit() { return txtCuit; }
    
    private int ptoVta, compIni, compFinal, ultimoB, ultimoA, cancelados, bloqueo;
    private Double importeTotal, importeIva, noGrabado;

    public Double getNoGrabado() {
        return noGrabado;
    }

    public void setNoGrabado(Double noGrabado) {
        this.noGrabado = noGrabado;
    }
    
   public void leerArchivo(String file) {
    File archivo = null;
    FileReader fr = null;
    BufferedReader br = null;
    
        try {
             //archivo = new File("D:\\tmp\\" +file);
             archivo = new File(file);
             fr = new FileReader (archivo);
             br = new BufferedReader(fr);
             
            String linea;
            String prefijo;
            

            while((linea=br.readLine())!=null) {
                prefijo = linea.substring(0,1);

                if(prefijo.equals("1") || prefijo.equals("5") ) {
                    //System.out.println(linea);
                    //cuit = fst.formatoCuit(linea.substring(1,12));
                    setCuit(linea.substring(1,12));
                    //cuit = linea.substring(1,12);
                    razon_social = linea.substring(12,52).trim();
                    setDocumento(cuit);
                    codigo = linea.substring(52,55);
                    numero = linea.substring(55,62);
                    fecha_extraccion = "20"+linea.substring(62,68);
                    ptoVta = Integer.parseInt(linea.substring(68,72));
                    compIni = Integer.parseInt(linea.substring(72,76));
                    fecha_inicio = "20"+linea.substring(76,82);
                    compFinal = Integer.parseInt(linea.substring(82,86)); //new BigDecimal(linea.substring(82,90));
                    fecha_fin = "20"+linea.substring(86,92);
                    ultimoB = Integer.parseInt(linea.substring(92,100));
                    ultimoA = Integer.parseInt(linea.substring(100,108));
                    importeTotal = new Double(linea.substring(108,126));
                    importeIva = new Double(linea.substring(126,144));
                    //noGrabado = new Double(linea.substring(144,160));
                    bloqueo = Integer.parseInt(linea.substring(160,164));
                    
                    
                    
                    SimpleDateFormat entrada = new SimpleDateFormat("yyMMdd");
                    SimpleDateFormat salida = new SimpleDateFormat("dd/MM/yyyy");
                    
                    Date fechaFormateada = entrada.parse(fecha_extraccion);
                    
                    
                    
                    setTxtCuit(fst.formatoCuit(cuit));
                    setTxtRazonSocial(razon_social.trim());
                    
                    
                   /* txt.setText("Razon_social: " +razon_social.trim());
                    txt.append(System.getProperty("line.separator"));
                    txt.append("CUIT: " +cuit); */
                    
                    System.out.println("Razon_social: " +razon_social.trim());
                    System.out.println("CUIT: " +cuit);
                    System.out.println("Código de controlador: " +codigo);
                    System.out.println("Numero de controlador: " +numero);
                    System.out.println("fecha Extraccion: " +salida.format(fechaFormateada));
                    System.out.println("Punto de Vta: " +ptoVta);
                    System.out.println("Z inicio: " +compIni);
                    fechaFormateada = entrada.parse(fecha_inicio);
                    //setFecha_inicio(fechaFormateada);
                    System.out.println("fecha inicio: " +salida.format(fechaFormateada));
                    System.out.println("Z Final: " +compFinal);
                    fechaFormateada = entrada.parse(fecha_fin);
                    System.out.println("fecha final: " +salida.format(fechaFormateada));
                    System.out.println("Ultimo C " +ultimoB);
                    System.out.println("Ultimo A " +ultimoA);
                    System.out.println("Importe total " +importeTotal);
                    System.out.println("Importe iva " +importeIva);
                    System.out.println("Total bloqueos " +bloqueo);
                    
                    
                    
                    
                }
            }
            
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try{                    
                if( null != fr ){   
                    fr.close();     
                }                  
            }catch (Exception e2){ 
                e2.printStackTrace();
            }
        }
       
   }

    public String getDocumento() {
        return documento;        
    }

    private void setTxtCuit(String formatoCuit) {
        txtCuit = formatoCuit;
    }

    private void setTxtRazonSocial(String trim) {
        razon_social = trim;
    }
}
