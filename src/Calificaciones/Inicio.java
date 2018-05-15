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
 * @author JoséDaniel
 */
public class Inicio extends javax.swing.JFrame {
Connection con=Coneccion.getConeccion();
    Statement s;
    ResultSet rs;
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
    public Inicio() {
        initComponents();
        Coneccion.Propiedades(this);
        this.setTitle("Inicio");
        
        
    }

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        Colegio = new javax.swing.JLabel();
        cmdIngresoNotas = new javax.swing.JButton();
        cmdInscribir = new javax.swing.JButton();
        cmdSalir = new javax.swing.JButton();
        Logo1 = new javax.swing.JLabel();
        Logo2 = new javax.swing.JLabel();
        Creador = new javax.swing.JLabel();
        cmdImprimir = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnArchivo = new javax.swing.JMenu();
        mnItmIngresoNotas = new javax.swing.JMenuItem();
        mnItmImprimir = new javax.swing.JMenuItem();
        mnItmSalir = new javax.swing.JMenuItem();
        mnOpciones = new javax.swing.JMenu();
        mnItmIngresoAlumnos = new javax.swing.JMenuItem();
        mnItmIngresoMaterias = new javax.swing.JMenuItem();
        mnItmIngresoGrados = new javax.swing.JMenuItem();
        mnItmIngresoMaestros = new javax.swing.JMenuItem();
        mnItmEliminarMateria = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnItmTraslado = new javax.swing.JMenuItem();
        mnAyuda = new javax.swing.JMenu();
        jMnItmAyuda = new javax.swing.JMenuItem();
        jMnItmCreador = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1355, 720));

        lblTitulo.setFont(new java.awt.Font("Cooper Black", 0, 48)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Bienvenido Notas Al Taz Taz");

        Colegio.setFont(new java.awt.Font("Segoe UI Black", 0, 48)); // NOI18N
        Colegio.setForeground(new java.awt.Color(255, 255, 255));
        Colegio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Colegio.setText("Colegio Rafael Landivar");

        cmdIngresoNotas.setBackground(new java.awt.Color(255, 255, 255));
        cmdIngresoNotas.setFont(new java.awt.Font("Bodoni MT", 1, 24)); // NOI18N
        cmdIngresoNotas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Calificaciones/Iconos/Notas.png"))); // NOI18N
        cmdIngresoNotas.setText("Ingresar Notas");
        cmdIngresoNotas.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(102, 153, 255), new java.awt.Color(102, 153, 255), null, null));
        cmdIngresoNotas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdIngresoNotas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdIngresoNotas.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        cmdIngresoNotas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdIngresoNotasActionPerformed(evt);
            }
        });

        cmdInscribir.setBackground(new java.awt.Color(255, 255, 255));
        cmdInscribir.setFont(new java.awt.Font("Bodoni MT", 1, 24)); // NOI18N
        cmdInscribir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Calificaciones/Iconos/Inscripcion.png"))); // NOI18N
        cmdInscribir.setText("Incribir Alumno");
        cmdInscribir.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(102, 153, 255), new java.awt.Color(102, 153, 255), null, null));
        cmdInscribir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdInscribir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdInscribir.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        cmdInscribir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdInscribirActionPerformed(evt);
            }
        });

        cmdSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Calificaciones/Iconos/liberar-icono-6437-96.png"))); // NOI18N
        cmdSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSalirActionPerformed(evt);
            }
        });

        Logo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Logo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Calificaciones/Iconos/Logo.png"))); // NOI18N
        Logo1.setFocusable(false);
        Logo1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        Logo2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Logo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Calificaciones/Iconos/Logo.png"))); // NOI18N

        Creador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Creador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Calificaciones/Iconos/creado.png"))); // NOI18N
        Creador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CreadorMouseClicked(evt);
            }
        });

        cmdImprimir.setBackground(new java.awt.Color(255, 255, 255));
        cmdImprimir.setFont(new java.awt.Font("Bodoni MT", 1, 24)); // NOI18N
        cmdImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Calificaciones/Iconos/printer.png"))); // NOI18N
        cmdImprimir.setText("Imprimir Notas");
        cmdImprimir.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(102, 153, 255), new java.awt.Color(102, 153, 255), null, null));
        cmdImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdImprimir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdImprimir.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        cmdImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdImprimirActionPerformed(evt);
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

        mnOpciones.setText("Opciones");
        mnOpciones.setFont(new java.awt.Font("Bodoni MT", 0, 16)); // NOI18N

        mnItmIngresoAlumnos.setFont(new java.awt.Font("Bodoni MT", 1, 14)); // NOI18N
        mnItmIngresoAlumnos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Calificaciones/Iconos/image/user_add.png"))); // NOI18N
        mnItmIngresoAlumnos.setText("Ingresar Alumnos");
        mnItmIngresoAlumnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnItmIngresoAlumnosActionPerformed(evt);
            }
        });
        mnOpciones.add(mnItmIngresoAlumnos);

        mnItmIngresoMaterias.setFont(new java.awt.Font("Bodoni MT", 1, 14)); // NOI18N
        mnItmIngresoMaterias.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Calificaciones/Iconos/image/add.png"))); // NOI18N
        mnItmIngresoMaterias.setText("Agregar Materias");
        mnItmIngresoMaterias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnItmIngresoMateriasActionPerformed(evt);
            }
        });
        mnOpciones.add(mnItmIngresoMaterias);

        mnItmIngresoGrados.setFont(new java.awt.Font("Bodoni MT", 1, 14)); // NOI18N
        mnItmIngresoGrados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Calificaciones/Iconos/image/group.png"))); // NOI18N
        mnItmIngresoGrados.setText("Ingresar Nuevos Grados");
        mnItmIngresoGrados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnItmIngresoGradosActionPerformed(evt);
            }
        });
        mnOpciones.add(mnItmIngresoGrados);

        mnItmIngresoMaestros.setFont(new java.awt.Font("Bodoni MT", 1, 14)); // NOI18N
        mnItmIngresoMaestros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Calificaciones/Iconos/image/user_white.png"))); // NOI18N
        mnItmIngresoMaestros.setText("Ingresar Maestros");
        mnItmIngresoMaestros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnItmIngresoMaestrosActionPerformed(evt);
            }
        });
        mnOpciones.add(mnItmIngresoMaestros);

        mnItmEliminarMateria.setFont(new java.awt.Font("FreeSans", 1, 14)); // NOI18N
        mnItmEliminarMateria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Calificaciones/Iconos/image/delete.png"))); // NOI18N
        mnItmEliminarMateria.setText("Eliminar Materia");
        mnItmEliminarMateria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnItmEliminarMateriaActionPerformed(evt);
            }
        });
        mnOpciones.add(mnItmEliminarMateria);
        mnOpciones.add(jSeparator1);

        mnItmTraslado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Calificaciones/Iconos/image/broom.png"))); // NOI18N
        mnItmTraslado.setText("Traslados de datos");
        mnOpciones.add(mnItmTraslado);

        jMenuBar1.add(mnOpciones);

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
                .addContainerGap()
                .addComponent(Logo1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Colegio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Logo2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(223, 223, 223)
                        .addComponent(cmdIngresoNotas))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Creador, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
                .addComponent(cmdImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(cmdInscribir)
                        .addGap(297, 297, 297))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Logo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Logo2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addComponent(Colegio, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cmdImprimir)
                        .addGap(53, 53, 53))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(cmdSalir))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmdIngresoNotas)
                                    .addComponent(cmdInscribir))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                                .addComponent(Creador, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnItmIngresoNotasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnItmIngresoNotasActionPerformed

        Coneccion.calificar(this);
        // TODO add your handling code here:
    }//GEN-LAST:event_mnItmIngresoNotasActionPerformed

    private void mnItmIngresoAlumnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnItmIngresoAlumnosActionPerformed

        Coneccion.alumnos();
        // TODO add your handling code here:
    }//GEN-LAST:event_mnItmIngresoAlumnosActionPerformed

    private void mnItmSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnItmSalirActionPerformed

        System.exit(0);
        
        // TODO add your handling code here:
    }//GEN-LAST:event_mnItmSalirActionPerformed

    private void mnItmImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnItmImprimirActionPerformed

        Coneccion.imprimir();
       
        // TODO add your handling code here:
    }//GEN-LAST:event_mnItmImprimirActionPerformed

    private void cmdSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSalirActionPerformed

        System.exit(0);
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdSalirActionPerformed

    private void mnItmIngresoMateriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnItmIngresoMateriasActionPerformed

        Coneccion.validar(JOptionPane.showInputDialog("Ingrese el usuario"),"Materias",con,s,rs,this);
        // TODO add your handling code here:
    }//GEN-LAST:event_mnItmIngresoMateriasActionPerformed

    private void mnItmIngresoGradosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnItmIngresoGradosActionPerformed

        Coneccion.validar(JOptionPane.showInputDialog("Ingrese el usuario"),"Grados",con,s,rs,this);
        // TODO add your handling code here:
    }//GEN-LAST:event_mnItmIngresoGradosActionPerformed

    private void cmdInscribirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdInscribirActionPerformed

        Coneccion.alumnos();
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdInscribirActionPerformed

    private void cmdIngresoNotasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdIngresoNotasActionPerformed

        Coneccion.calificar(this);
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdIngresoNotasActionPerformed

    private void mnItmIngresoMaestrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnItmIngresoMaestrosActionPerformed
     
        Coneccion.validar(JOptionPane.showInputDialog("Ingrese el usuario"),"Maestros",con,s,rs,this);
        // TODO add your handling code here:
    }//GEN-LAST:event_mnItmIngresoMaestrosActionPerformed

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

    private void cmdImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdImprimirActionPerformed

        Coneccion.imprimir();
        
// TODO add your handling code here:
    }//GEN-LAST:event_cmdImprimirActionPerformed

    private void mnItmEliminarMateriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnItmEliminarMateriaActionPerformed
        Coneccion.validar(JOptionPane.showInputDialog("Ingrese el usuario"),"ElimMateria",con,s,rs,this);

        // TODO add your handling code here:
    }//GEN-LAST:event_mnItmEliminarMateriaActionPerformed

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
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new Inicio().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Colegio;
    private javax.swing.JLabel Creador;
    private javax.swing.JLabel Logo1;
    private javax.swing.JLabel Logo2;
    private javax.swing.JButton cmdImprimir;
    private javax.swing.JButton cmdIngresoNotas;
    private javax.swing.JButton cmdInscribir;
    private javax.swing.JButton cmdSalir;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMnItmAyuda;
    private javax.swing.JMenuItem jMnItmCreador;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JMenu mnArchivo;
    private javax.swing.JMenu mnAyuda;
    private javax.swing.JMenuItem mnItmEliminarMateria;
    private javax.swing.JMenuItem mnItmImprimir;
    private javax.swing.JMenuItem mnItmIngresoAlumnos;
    private javax.swing.JMenuItem mnItmIngresoGrados;
    private javax.swing.JMenuItem mnItmIngresoMaestros;
    private javax.swing.JMenuItem mnItmIngresoMaterias;
    private javax.swing.JMenuItem mnItmIngresoNotas;
    private javax.swing.JMenuItem mnItmSalir;
    private javax.swing.JMenuItem mnItmTraslado;
    private javax.swing.JMenu mnOpciones;
    // End of variables declaration//GEN-END:variables
}
