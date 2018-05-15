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
public final class IngresoMaterias extends javax.swing.JFrame {

   
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

public boolean buscarExistentes()
{
    boolean existe=false;
    String comp="";
    comp=""+getGrado(jCGrado.getSelectedItem().toString());
    desconectar();
    String sql="select m.nombreMateria,g.idGrado,g.idPlan from copral.materias m "
            + "inner join grados g on m.idGrado=g.idGrado;";
    try
    {
    
    s=con.createStatement();
    rs=s.executeQuery(sql);
    
    while(rs.next())
    {
        if(rs.getString(1).equals(txtNuevaMateria.getText()) )
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
public void limpiar()
{
    txtNuevaMateria.setText("");
    txtNuevaMateria.requestFocus();
    
}
    public IngresoMaterias() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(Inicio.HIDE_ON_CLOSE);
        this.getContentPane().setBackground(java.awt.Color.white);
        this.setResizable(false);
        this.setTitle("Ingreso de Materias Nuevas");
       jCPlan.removeAllItems();
        jCGrado.removeAllItems();
        
        jCMaestro.removeAllItems();
        
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
      JOptionPane.showMessageDialog(null,"No se pudo llenar el combo Plan\n"+e);
    }
    
    

}
public void llenarMaestros() 
{
    
    
    
    String sql="select nombreMaestro,apellidoMaestro from copral.maestros where idPlan="+getPlan(jCPlan.getSelectedItem().toString())+" order by idMaestro desc";
    try
    {
    s=con.createStatement();
    rs=s.executeQuery(sql);
    
    while(rs.next())
    {
        jCMaestro.insertItemAt(rs.getObject(1).toString()+" "+rs.getObject(2).toString(),0);
    }
    desconectar();
    }
    catch(Exception e)
    {
      JOptionPane.showMessageDialog(null,"No se pudo llenar el combo Maestros\n"+e);
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
public void llenarGrado() 
{
  
    
 
    String sql="select nombreGrado from copral.grados where idPlan="+getPlan1(jCPlan.getSelectedItem().toString())+" order by nombreGrado desc";
    try
    {
    s=con.createStatement();
    rs=s.executeQuery(sql);
    
    while(rs.next())
    {
        jCGrado.insertItemAt(rs.getObject(1).toString(),0);
    }
   
     desconectar();   

    }
    catch(SQLException e)
    {
      JOptionPane.showMessageDialog(null,"Imposible llenar el combo Grado");
    }
    

}

public int getCanMaterias(String grado)
{
    int canMaterias=0;
        
     String sql="select cantidadMaterias from grados where idPlan="+grado+" and nombreGrado='"+jCGrado.getSelectedItem().toString()+"'";
    
    try
    {
    s=con.createStatement();
    rs=s.executeQuery(sql);
    
    
    while(rs.next())
    {
        

        canMaterias=Integer.parseInt(rs.getObject(1).toString())+1;
        
    }
    desconectar();
    
    }
    catch(SQLException e)
    {
      JOptionPane.showMessageDialog(null,"No se consiguio la cantidad de materias\n"+e);
    }   
    
    return canMaterias;
}
public int getPlan1(String plan)
{
    int idPlan=0;
    String sql="select idPlan,nombrePlan from copral.plan where nombrePlan='"+plan+"'";
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
    catch(SQLException e)
    {
      JOptionPane.showMessageDialog(null,"Imposible llenar el combo Grado");
    }    
    return idPlan;
}
public void aumentarMaterias()
{
    try
    {
    desconectar();
     
    
    String sql2="update grados set cantidadMaterias="+(getCanMaterias(""+getPlan1(jCPlan.getSelectedItem().toString())))
            +" where idGrado="+getGrado(jCGrado.getSelectedItem().toString());
    
    
    s=con.createStatement();
    s.executeUpdate(sql2);
    
    desconectar();
    }
    catch(SQLException e)
    {
        JOptionPane.showMessageDialog(null, "Error de actualizacion"+e);
    }
}


public int getGrado(String grado)
{
    int codGrado=0;
   
    
    String sql="select idGrado from copral.grados where nombreGrado='"+grado+"' and idPlan="
            +getPlan1(jCPlan.getSelectedItem().toString())+" ";
    try
    {
    s=con.createStatement();
    rs=s.executeQuery(sql);
    
    while(rs.next())
    {
        codGrado=Integer.parseInt(rs.getObject(1).toString());
        
    }
    
    desconectar();
    }
    catch(SQLException e)
    {
      JOptionPane.showMessageDialog(null,"no hay id de grado");
    }
    
    return codGrado;
    
}
public String getMaestro(String maestro)
{
    String idma="";
    String apell=maestro.substring(maestro.length()-3,maestro.length());
    maestro=maestro.substring(0, 4);
    
    String sql="select idMaestro from copral.maestros where nombreMaestro like '%"+maestro+"%' and "
            + "apellidoMaestro like '%"+apell+"%'";
    
   // JOptionPane.showMessageDialog(null, sql);
   
    try
    {
    s=con.createStatement();
    rs=s.executeQuery(sql);
    
    while(rs.next())
    {
        idma=rs.getObject(1).toString();
        
        
    }
    
    desconectar();
    }
    catch(SQLException e)
    {
      JOptionPane.showMessageDialog(null,"no hay id de maestros");
    }
    
    return idma;
}
public void ingresarMaterias()
{
    
    String idGrado=getGrado(jCGrado.getSelectedItem().toString())+"";
    String sql="insert into materias(nombreMateria,idMaestro,idGrado) values('"
            + txtNuevaMateria.getText()+"',"+getMaestro(jCMaestro.getSelectedItem().toString())
            +","+idGrado+")";
    
    try
    {
    s=con.createStatement();
    s.execute(sql);
        aumentarMaterias();
            String squery="select idmateria from materias where nombreMateria='"
            + txtNuevaMateria.getText()+"' and idMaestro="+getMaestro(jCMaestro.getSelectedItem().toString())
            +" and idGrado="+idGrado+"";
            s=con.createStatement();
            ResultSet rsrs=s.executeQuery(squery);
            while(rsrs.next())
                {
                    String idMateria=rsrs.getString(1);
                    for(int i=1;i<=4;i++){
                        int idBimestre=i;
                        String squeryAlumnos="select idAlumno from alumnos where idGrado="+idGrado+"";
                                Statement ss=con.createStatement();
                                ResultSet rsAlumnos=ss.executeQuery(squeryAlumnos);
                                while(rsAlumnos.next()){
                                    String idAlumno=rsAlumnos.getString(1);
                                    String squeryNotas="insert into notas(idAlumno,idGrado,idBimestre,nota,idMateria,anio)"
                                            + "values("+idAlumno+","+idGrado+","+idBimestre+",0,"+idMateria+",2017)";
                                    Statement sss=con.createStatement();
                                    sss.execute(squeryNotas);
                                    sss.close();
                                }
                                ss.close();
                                
                    }
                    JOptionPane.showMessageDialog(null, "Materia ingresada con exito");

                }
   
    desconectar();
    
    }
    catch(SQLException | NumberFormatException e)
    {
      JOptionPane.showMessageDialog(null,"No se puede ingresar la materia\n"+e);
    }
   
    
}

/**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblPlan = new javax.swing.JLabel();
        lblNuevoGrado = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        cmdGuardar = new javax.swing.JButton();
        jCPlan = new javax.swing.JComboBox();
        cmdCancelar = new javax.swing.JButton();
        jCGrado = new javax.swing.JComboBox();
        txtNuevaMateria = new javax.swing.JTextField();
        lblMateria = new javax.swing.JLabel();
        jCMaestro = new javax.swing.JComboBox();
        lblMaestro = new javax.swing.JLabel();
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

        lblPlan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblPlan.setText("Plan");

        lblNuevoGrado.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblNuevoGrado.setText("Grado");

        lblTitulo.setFont(new java.awt.Font("Cooper Black", 0, 24)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Ingreso de Materias");

        cmdGuardar.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        cmdGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Calificaciones/Iconos/image/add.png"))); // NOI18N
        cmdGuardar.setText("Guardar");
        cmdGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdGuardarActionPerformed(evt);
            }
        });

        jCPlan.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jCPlan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCPlan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jCPlanFocusLost(evt);
            }
        });
        jCPlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCPlanActionPerformed(evt);
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

        jCGrado.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jCGrado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtNuevaMateria.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        lblMateria.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblMateria.setText("Materia");

        jCMaestro.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jCMaestro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblMaestro.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblMaestro.setText("Maestro(a)");

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cmdGuardar)
                .addGap(50, 50, 50)
                .addComponent(cmdCancelar)
                .addGap(245, 245, 245))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 737, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Creador))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblPlan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNuevoGrado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMaestro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jCPlan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCMaestro, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCGrado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNuevaMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCPlan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPlan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNuevoGrado)
                    .addComponent(jCGrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCMaestro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaestro))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNuevaMateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMateria))
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdCancelar)
                    .addComponent(cmdGuardar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Creador)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdGuardarActionPerformed

        if(!txtNuevaMateria.getText().equals(""))
        {
            if(!buscarExistentes())
            {
               ingresarMaterias();
                limpiar(); 
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Esta materia ya existe en este Grado");
                limpiar();
            }
        
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Debe escribir un nombre para la Materia");
            
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

    private void jCPlanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCPlanActionPerformed
        try
        {
            jCGrado.removeAllItems();
        llenarGrado();
        }
        catch(Exception e)
        {
            
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jCPlanActionPerformed

    private void jCPlanFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jCPlanFocusLost
            jCMaestro.removeAllItems();
       try{
            llenarMaestros();
       }
       catch(Exception e)
       {
           
       }
        // TODO add your handling code here:
    }//GEN-LAST:event_jCPlanFocusLost

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
            java.util.logging.Logger.getLogger(IngresoMaterias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IngresoMaterias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IngresoMaterias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IngresoMaterias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IngresoMaterias().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Creador;
    private javax.swing.JButton cmdCancelar;
    private javax.swing.JButton cmdGuardar;
    private javax.swing.JComboBox jCGrado;
    private javax.swing.JComboBox jCMaestro;
    private javax.swing.JComboBox jCPlan;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMnItmAyuda;
    private javax.swing.JMenuItem jMnItmCreador;
    private javax.swing.JLabel lblMaestro;
    private javax.swing.JLabel lblMateria;
    private javax.swing.JLabel lblNuevoGrado;
    private javax.swing.JLabel lblPlan;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JMenu mnArchivo;
    private javax.swing.JMenu mnAyuda;
    private javax.swing.JMenuItem mnItmImprimir;
    private javax.swing.JMenuItem mnItmIngresoNotas;
    private javax.swing.JMenuItem mnItmSalir;
    private javax.swing.JTextField txtNuevaMateria;
    // End of variables declaration//GEN-END:variables
}
