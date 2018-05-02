/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.afip.dgi.agencia66.tramites.view;

import gov.afip.dgi.agencia66.model.ControladorFiscal;
import javax.swing.JOptionPane;
import gov.afip.dgi.agencia66.tramites.controller.BuscarControladorFiscal;
import gov.afip.dgi.agencia66.tramites.util.ImprimeActaAnexoCF;
import gov.afip.dgi.agencia66.util.FormateaSalidasTexto;
import gov.afip.dgi.agencia66.util.Validaciones;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.TimeZone;
import javax.swing.ImageIcon;
/**
 *
 * @author u84062
 */
public class BuscarTramiteCFP extends javax.swing.JFrame {

    /**
     * Creates new form BuscarTramiteCFP
     */
    
    private ControladorFiscal _cf = new ControladorFiscal();
    
    public BuscarTramiteCFP() {
        URL url = getClass().getResource("/gov/afip/dgi/agencia66/tramites/view/icono.png");
        ImageIcon icono = new ImageIcon(url);
        setIconImage(icono.getImage());

        initComponents();
        deshabilitaComponentes();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        chkSolicitud = new javax.swing.JCheckBox();
        chkCuit = new javax.swing.JCheckBox();
        btnBuscarSolicitud = new javax.swing.JButton();
        btnBuscarCuit = new javax.swing.JButton();
        txtSolicitud = new javax.swing.JTextField();
        lblSolicitud = new javax.swing.JLabel();
        lblCuit = new javax.swing.JLabel();
        txtCuit = new javax.swing.JTextField();
        lblPtoVta = new javax.swing.JLabel();
        txtPtoVta = new javax.swing.JTextField();
        btnSalir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDatos = new javax.swing.JTextArea();
        btnImprime = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Buscar por:");

        chkSolicitud.setText("Solicitud");
        chkSolicitud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSolicitudActionPerformed(evt);
            }
        });

        chkCuit.setText("CUIT + Puesto Venta");
        chkCuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCuitActionPerformed(evt);
            }
        });

        btnBuscarSolicitud.setText("Buscar");
        btnBuscarSolicitud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarSolicitudActionPerformed(evt);
            }
        });

        btnBuscarCuit.setText("Buscar");
        btnBuscarCuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarCuitActionPerformed(evt);
            }
        });

        lblSolicitud.setText("Solicitud");

        lblCuit.setText("CUIT:");

        lblPtoVta.setText("Punto de Venta: ");

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        txtDatos.setColumns(20);
        txtDatos.setRows(5);
        jScrollPane1.setViewportView(txtDatos);

        btnImprime.setText("Imprimir");
        btnImprime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimeActionPerformed(evt);
            }
        });

        jLabel21.setBackground(java.awt.Color.white);
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/afip/dgi/agencia66/tramites/view/afip.png"))); // NOI18N

        lblTitulo.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        lblTitulo.setText("BUSCAR TRAMITES REALIZADOS");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblCuit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCuit)
                                .addGap(18, 18, 18)
                                .addComponent(lblPtoVta)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPtoVta, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(82, 82, 82)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblSolicitud)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtSolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(32, 32, 32)
                                        .addComponent(btnBuscarSolicitud))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(chkSolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(chkCuit, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 146, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(btnSalir)
                                        .addGap(56, 56, 56))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(btnBuscarCuit)
                                        .addGap(209, 209, 209))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(198, 198, 198)
                                        .addComponent(btnImprime)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 6, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(chkSolicitud)
                            .addComponent(chkCuit))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSolicitud)
                            .addComponent(btnBuscarSolicitud))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCuit)
                            .addComponent(txtCuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPtoVta)
                            .addComponent(txtPtoVta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscarCuit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnImprime)
                        .addGap(12, 12, 12)
                        .addComponent(btnSalir)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chkSolicitudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSolicitudActionPerformed
       
        if(chkSolicitud.isSelected()) {
            habilitaSolicitud();
            deshabilitaCuit();
            chkCuit.setSelected(false);
        } else {
            desahabilitaSolicitud();
        }

    }//GEN-LAST:event_chkSolicitudActionPerformed

    private void chkCuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCuitActionPerformed
    
        if(chkCuit.isSelected()) {
            habilitaCuit();
            desahabilitaSolicitud();
            chkSolicitud.setSelected(false);
        } else {
            deshabilitaCuit();
        }
        
    }//GEN-LAST:event_chkCuitActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnBuscarSolicitudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarSolicitudActionPerformed
       if(esNumerico(txtSolicitud.getText())) {
           buscaSolicitud();
       } else {
          JOptionPane.showMessageDialog(this, "Debe ingresar en formato numerico", "Error de datos", JOptionPane.ERROR_MESSAGE ); 
       }
    }//GEN-LAST:event_btnBuscarSolicitudActionPerformed

    private void btnBuscarCuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarCuitActionPerformed
       if(esNumerico(txtPtoVta.getText()) && !txtCuit.getText().isEmpty()) {
           buscaCuit();
       } else {
          JOptionPane.showMessageDialog(this, "Debe ingresar en formato numerico de Pto Venta y un cuit válido", "Error de datos", JOptionPane.ERROR_MESSAGE ); 
       }




        
    }//GEN-LAST:event_btnBuscarCuitActionPerformed

    private void btnImprimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimeActionPerformed
        ImprimeActaAnexoCF imprime = new ImprimeActaAnexoCF();
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("America/Argentina/Buenos_Aires"));
        int ann = cal.get(Calendar.YEAR);
        imprime.imprime(_cf, ann);
    }//GEN-LAST:event_btnImprimeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BuscarTramiteCFP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BuscarTramiteCFP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BuscarTramiteCFP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BuscarTramiteCFP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BuscarTramiteCFP().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarCuit;
    private javax.swing.JButton btnBuscarSolicitud;
    private javax.swing.JButton btnImprime;
    private javax.swing.JButton btnSalir;
    private javax.swing.JCheckBox chkCuit;
    private javax.swing.JCheckBox chkSolicitud;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblCuit;
    private javax.swing.JLabel lblPtoVta;
    private javax.swing.JLabel lblSolicitud;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField txtCuit;
    private javax.swing.JTextArea txtDatos;
    private javax.swing.JTextField txtPtoVta;
    private javax.swing.JTextField txtSolicitud;
    // End of variables declaration//GEN-END:variables

    private void deshabilitaComponentes() {
        desahabilitaSolicitud();
        deshabilitaCuit();
        deshabilitaEncontrado();
    }


    private void desahabilitaSolicitud() {
        lblSolicitud.setVisible(false);
        txtSolicitud.setVisible(false);
        btnBuscarSolicitud.setVisible(false);
    }

    private void deshabilitaCuit() {
        lblCuit.setVisible(false);
        txtCuit.setVisible(false);
        lblPtoVta.setVisible(false);
        txtPtoVta.setVisible(false);
        btnBuscarCuit.setVisible(false);
    }
    private void habilitaSolicitud() {
        lblSolicitud.setVisible(true);
        txtSolicitud.setVisible(true);
        btnBuscarSolicitud.setVisible(true);
    }

    private void habilitaCuit() {
        lblCuit.setVisible(true);
        txtCuit.setVisible(true);
        lblPtoVta.setVisible(true);
        txtPtoVta.setVisible(true);
        btnBuscarCuit.setVisible(true);
    }

    private void buscaSolicitud() {
        //ControladorFiscal cf = new ControladorFiscal();
        BuscarControladorFiscal bcf;
        bcf = new BuscarControladorFiscal();
        if(txtSolicitud.getText().isEmpty()) {
          JOptionPane.showMessageDialog(this, "Debe ingresar un numero de Solicitud Válido", "Ingrese la Solicitud", JOptionPane.ERROR_MESSAGE );
        } else {
            _cf = bcf.getIdControladorFiscal(Double.parseDouble(txtSolicitud.getText()));
            if(_cf != null) {
                muestraEncontrado();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontro esa solicitud", "Registro NO Hallado", JOptionPane.INFORMATION_MESSAGE );
            }
        }
    }
    
      private void buscaCuit() {
        //ControladorFiscal cf = new ControladorFiscal();
        BuscarControladorFiscal bcf;
        bcf = new BuscarControladorFiscal();
        if(txtCuit.getText().isEmpty() || txtPtoVta.getText().isEmpty()) {
          JOptionPane.showMessageDialog(this, "Debe ingresar un numero CUIT y Punto de Venta Validos", "Ingrese los datos", JOptionPane.ERROR_MESSAGE );
        } else {
            _cf = bcf.getIdControladorFiscal(Double.parseDouble(txtCuit.getText()), Integer.parseInt(txtPtoVta.getText()));
            if(_cf != null) {
                muestraEncontrado();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontro ese CUIT - Pto Venta", "Registro NO Hallado", JOptionPane.INFORMATION_MESSAGE );
            }
        }
          
    }

    private void muestraEncontrado() {
       FormateaSalidasTexto fst = new FormateaSalidasTexto();
       txtDatos.setVisible(true);
       btnImprime.setVisible(true);
       DecimalFormat df = new DecimalFormat("###########");
       String cuit = fst.formatoCuit(String.valueOf(df.format(_cf.getCuit()))) ;
       String leyenda = "Se imprimirán los datos de: " +_cf.getRazonSocial() +" \nde CUIT: " +cuit
                        +"\n Solicitud: " +df.format(_cf.getSolicitud())
                        + "\n Puesto de Venta: " +_cf.getPuestoVenta() +"\n Procesado el: " +_cf.getFechaPoceso()
                        + "\n por el agente: " +_cf.getAgente();
       txtDatos.setText(leyenda);
    }

    private void deshabilitaEncontrado() {
       txtDatos.setVisible(false);
       btnImprime.setVisible(false);
    }

    private boolean esNumerico(String cadena) {
        try {
		Integer.parseInt(cadena);
		return true;
	} catch (NumberFormatException nfe){
		return false;
	}
    }
    
    
}