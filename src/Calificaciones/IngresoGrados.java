/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calificaciones;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author JoséDaniel
 */
public final class IngresoGrados extends javax.swing.JFrame {
    Connection con=Coneccion.getConeccion();
    Statement s;
    ResultSet rs;
    String[] MateriaExis=new String[30/*Cantidad de materias*/];
    
public void desconectar()
{
    try{
    //con.close();
        rs.close();
        s.close();
    }
    catch(Exception e)
    {
        JOptionPane.showMessageDialog(null,"Error al Cerrar\n"+e);
    }
}
public void limpiar()
{
    txtNuevoGrado.setText("");
    txtNuevoGrado.requestFocus();
    
}
  
public boolean buscarExistentes()
{
    boolean existe=false;
    String comp;
    comp=""+getPlan(jCPlan.getSelectedItem().toString());
    desconectar();
    String sql="select nombreGrado,idPlan from copral.grados;";
    try
    {
    
    s=con.createStatement();
    rs=s.executeQuery(sql);
    
    while(rs.next())
    {
        if(rs.getString(1).equals(txtNuevoGrado.getText()) )
        {
           if(rs.getString(2).equals(comp))
           {
               existe=true;
           }
           
        }
        
    }
    
    desconectar();
    }
    catch(Exception e)
    {
      JOptionPane.showMessageDialog(null,"No se pudo buscar Repetidos\n"+e);
    }
    
    
    return existe;
}
public IngresoGrados() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(Inicio.HIDE_ON_CLOSE);
        this.getContentPane().setBackground(java.awt.Color.white);
        this.setResizable(false);
        this.setTitle("Ingreso de Grados Nuevos");
        con=Coneccion.getConeccion();
        jCPlan.removeAllItems();
        llenarPlan();
    }

    
    public void llenarPlan() 
{
    
    
    
    String sql="select nombrePlan from copral.plan order by idPlan desc";
    try
    {
    s=con.createStatement();
    rs=s.executeQuery(sql);
    
    while(rs.next())
    {
        jCPlan.insertItemAt(rs.getObject(1).toString(),0);
    }
    desconectar();
    }
    catch(Exception e)
    {
      JOptionPane.showMessageDialog(null,"No se pudo llenar el combo grados\n"+e);
    }
    
    

}
    
public int getPlan(String plan)
 {
     int idPlan=0;
     String sql="select idPlan,nombrePlan from copral.plan where nombrePlan='"+plan+"';";
    try
    {
    s=con.createStatement();
    rs=s.executeQuery(sql);
    
    while(rs.next())
    {
        idPlan=Integer.parseInt(rs.getObject(1).toString());
    }
    desconectar();
    }
    catch(SQLException | NumberFormatException e)
    {
      JOptionPane.showMessageDialog(null,"No se puede obtener el idPlan\n"+e);
    }
     return idPlan;
     
 }

public void ingresarGrado()
{
     if(!txtNuevoGrado.getText().equals(""))
     try
    {
        
    
        
    String sql="insert into grados(nombreGrado,cantidadAlumnos,cantidadMaterias,idPlan) values"
            + "('"+txtNuevoGrado.getText()+"',0,0,"+getPlan(jCPlan.getSelectedItem().toString())+");";
    
    
    
    s=con.createStatement();
    s.execute(sql);
    
    
    
    
    
    
    desconectar();
    
    
    }
    catch(SQLException e)
    {
      JOptionPane.showMessageDialog(null,"No se puede ingresar el Grado\n"+e);
    }
    
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCPlan = new javax.swing.JComboBox();
        txtNuevoGrado = new javax.swing.JTextField();
        lblPlan = new javax.swing.JLabel();
        lblNuevoGrado = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        cmdGuardar = new javax.swing.JButton();
        cmdCancelar = new javax.swing.JButton();
        Creador = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnArchivo = new javax.swing.JMenu();
        mnItmIngresoNotas = new javax.swing.JMenuItem();
        mnItmImprimir = new javax.swing.JMenuItem();
        mnItmSalir = new javax.swing.JMenuItem();
        mnAyuda = new javax.swing.JMenu();
        jMnItmAyuda = new javax.swing.JMenuItem();
        jMnItmCreador = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jCPlan.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jCPlan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtNuevoGrado.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        lblPlan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblPlan.setText("Plan");

        lblNuevoGrado.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblNuevoGrado.setText("Grado");

        lblTitulo.setFont(new java.awt.Font("Cooper Black", 0, 24)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Ingreso de Grados");

        cmdGuardar.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        cmdGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Calificaciones/Iconos/image/add.png"))); // NOI18N
        cmdGuardar.setText("Guardar");
        cmdGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdGuardarActionPerformed(evt);
            }
        });

        cmdCancelar.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        cmdCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Calificaciones/Iconos/image/cancel.png"))); // NOI18N
        cmdCancelar.setText("Cancelar");
        cmdCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCancelarActionPerformed(evt);
            }
        });

        Creador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Calificaciones/Iconos/creado.png"))); // NOI18N
        Creador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CreadorMouseClicked(evt);
            }
        });

        jMenuBar1.setFont(new java.awt.Font("Bodoni MT Black", 0, 14)); // NOI18N

        mnArchivo.setText("Archivo");
        mnArchivo.setFont(new java.awt.Font("Bodoni MT", 0, 16)); // NOI18N

        mnItmIngresoNotas.setFont(new java.awt.Font("Bodoni MT", 1, 14)); // NOI18N
        mnItmIngresoNotas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Calificaciones/Iconos/image/document_add.png"))); // NOI18N
        mnItmIngresoNotas.setText("Ingresar Notas");
        mnItmIngresoNotas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnItmIngresoNotasActionPerformed(evt);
            }
        });
        mnArchivo.add(mnItmIngresoNotas);

        mnItmImprimir.setFont(new java.awt.Font("Bodoni MT", 1, 14)); // NOI18N
        mnItmImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Calificaciones/Iconos/image/printer.png"))); // NOI18N
        mnItmImprimir.setText("Imprimir Notas");
        mnItmImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnItmImprimirActionPerformed(evt);
            }
        });
        mnArchivo.add(mnItmImprimir);

        mnItmSalir.setFont(new java.awt.Font("Bodoni MT", 1, 14)); // NOI18N
        mnItmSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Calificaciones/Iconos/image/close.png"))); // NOI18N
        mnItmSalir.setText("Salir");
        mnItmSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnItmSalirActionPerformed(evt);
            }
        });
        mnArchivo.add(mnItmSalir);

        jMenuBar1.add(mnArchivo);

        mnAyuda.setText("Ayuda");
        mnAyuda.setFont(new java.awt.Font("Bodoni MT", 0, 16)); // NOI18N

        jMnItmAyuda.setFont(new java.awt.Font("Bodoni MT", 1, 14)); // NOI18N
        jMnItmAyuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Calificaciones/Iconos/image/help.png"))); // NOI18N
        jMnItmAyuda.setText("Ayuda");
        mnAyuda.add(jMnItmAyuda);

        jMnItmCreador.setFont(new java.awt.Font("Bodoni MT", 1, 14)); // NOI18N
        jMnItmCreador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Calificaciones/Iconos/creador.png"))); // NOI18N
        jMnItmCreador.setText("Creador");
        mnAyuda.add(jMnItmCreador);

        jMenuBar1.add(mnAyuda);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(84, 84, 84)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblPlan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblNuevoGrado, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCPlan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtNuevoGrado)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(129, 129, 129)
                                    .addComponent(cmdGuardar)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(cmdCancelar))
                                .addComponent(Creador)))
                        .addGap(161, 161, 161)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCPlan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPlan))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNuevoGrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNuevoGrado))
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdCancelar)
                    .addComponent(cmdGuardar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(Creador))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdGuardarActionPerformed

        if(!txtNuevoGrado.getText().equals(""))
        {
            if(!buscarExistentes())
            {
                ingresarGrado();
                limpiar();
            }
            else
            {
                JOptionPane.showMessageDialog(null,"El grado ya existe en este plan");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Debe ingresar un grado");
        }
// TODO add your handling code here:
    }//GEN-LAST:event_cmdGuardarActionPerformed

    private void cmdCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCancelarActionPerformed

         Coneccion.inicioSalida(this);
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdCancelarActionPerformed

    private void CreadorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CreadorMouseClicked

        try {
            Desktop.getDesktop().browse(new URI("https://www.facebook.com/DEANIELL6195"));
        } catch (IOException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_CreadorMouseClicked

    private void mnItmIngresoNotasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnItmIngresoNotasActionPerformed

        Coneccion.calificar(this);
        // TODO add your handling code here:
    }//GEN-LAST:event_mnItmIngresoNotasActionPerformed

    private void mnItmImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnItmImprimirActionPerformed

        Coneccion.imprimir();

        // TODO add your handling code here:
    }//GEN-LAST:event_mnItmImprimirActionPerformed

    private void mnItmSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnItmSalirActionPerformed

        System.exit(0);

        // TODO add your handling code here:
    }//GEN-LAST:event_mnItmSalirActionPerformed

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
            java.util.logging.Logger.getLogger(IngresoGrados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IngresoGrados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IngresoGrados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IngresoGrados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IngresoGrados().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Creador;
    private javax.swing.JButton cmdCancelar;
    private javax.swing.JButton cmdGuardar;
    private javax.swing.JComboBox jCPlan;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMnItmAyuda;
    private javax.swing.JMenuItem jMnItmCreador;
    private javax.swing.JLabel lblNuevoGrado;
    private javax.swing.JLabel lblPlan;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JMenu mnArchivo;
    private javax.swing.JMenu mnAyuda;
    private javax.swing.JMenuItem mnItmImprimir;
    private javax.swing.JMenuItem mnItmIngresoNotas;
    private javax.swing.JMenuItem mnItmSalir;
    private javax.swing.JTextField txtNuevoGrado;
    // End of variables declaration//GEN-END:variables
}
