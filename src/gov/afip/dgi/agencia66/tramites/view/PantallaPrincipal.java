/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.afip.dgi.agencia66.tramites.view;

import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author u84062
 */
public class PantallaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form PantallaPrincipal
     */
    public PantallaPrincipal() {
        initComponents();
        URL url = getClass().getResource("/gov/afip/dgi/agencia66/tramites/view/icono.png");
        ImageIcon icono = new ImageIcon(url);
        setIconImage(icono.getImage());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuCF = new javax.swing.JMenu();
        opcLeerCF = new javax.swing.JMenuItem();
        opcBuscarCF = new javax.swing.JMenuItem();
        opcAltaTarea = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        opcGenerarEstadistica = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        opcVerEstadisticas = new javax.swing.JMenuItem();
        mSistema = new javax.swing.JMenu();
        opcSalir = new javax.swing.JMenuItem();

        jMenu1.setText("jMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        menuCF.setText("Controladores Fiscales");

        opcLeerCF.setText("Leer CF");
        opcLeerCF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcLeerCFActionPerformed(evt);
            }
        });
        menuCF.add(opcLeerCF);

        opcBuscarCF.setText("Buscar CF");
        opcBuscarCF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcBuscarCFActionPerformed(evt);
            }
        });
        menuCF.add(opcBuscarCF);

        jMenuBar1.add(menuCF);

        opcAltaTarea.setText("Tareas");

        jMenuItem3.setText("Alta deTarea");
        opcAltaTarea.add(jMenuItem3);

        opcGenerarEstadistica.setText("Buscar Tarea");
        opcAltaTarea.add(opcGenerarEstadistica);

        jMenu4.setText("Estadisticas");

        jMenuItem5.setText("Generar Estadistica");
        jMenu4.add(jMenuItem5);

        opcVerEstadisticas.setText("Ver Estadisticas");
        jMenu4.add(opcVerEstadisticas);

        opcAltaTarea.add(jMenu4);

        jMenuBar1.add(opcAltaTarea);

        mSistema.setText("Sistema");

        opcSalir.setText("Salir");
        opcSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcSalirActionPerformed(evt);
            }
        });
        mSistema.add(opcSalir);

        jMenuBar1.add(mSistema);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void opcLeerCFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcLeerCFActionPerformed
        //new AltaCF().setVisible(true);
        new LeerControladorFiscal().setVisible(true);
        
    }//GEN-LAST:event_opcLeerCFActionPerformed

    private void opcSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcSalirActionPerformed
        salir();
    }//GEN-LAST:event_opcSalirActionPerformed

    private void opcBuscarCFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcBuscarCFActionPerformed
        new BuscarTramiteCFP().setVisible(true);
    }//GEN-LAST:event_opcBuscarCFActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new PantallaPrincipal().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenu mSistema;
    private javax.swing.JMenu menuCF;
    private javax.swing.JMenu opcAltaTarea;
    private javax.swing.JMenuItem opcBuscarCF;
    private javax.swing.JMenuItem opcGenerarEstadistica;
    private javax.swing.JMenuItem opcLeerCF;
    private javax.swing.JMenuItem opcSalir;
    private javax.swing.JMenuItem opcVerEstadisticas;
    // End of variables declaration//GEN-END:variables

    private void salir() {
        System.exit(0);
    }
}
