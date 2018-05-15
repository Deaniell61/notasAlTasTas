
package Calificaciones;

import java.awt.Desktop;
import javax.swing.JOptionPane;
import java.sql.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
/**
 * @author JoséDaniel
 */
public final class IngresoAlumnos extends javax.swing.JFrame {
    Connection con=Coneccion.getConeccion();
    Statement s;
    ResultSet rs;
    String[] MateriaExis=new String[100/*Cantidad de materias*/];
    
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
    txtNombre.setText("");
    txtApellido.setText("");
    txtNombre.requestFocus();
}
    public IngresoAlumnos() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(Inicio.HIDE_ON_CLOSE);
        this.getContentPane().setBackground(java.awt.Color.white);
        this.setResizable(false);
        this.setTitle("Ingreso de Alumnos Nuevos");
        con=Coneccion.getConeccion();
        txtNombre.setText("");
        txtApellido.setText("");
        jCPlan.removeAllItems();
        jCGrado.removeAllItems();
        
        llenarPlan();
        
        
        
        
        txtNombre.requestFocus();
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
        if(rs.getString(2).equals(plan))
        {
        idPlan=Integer.parseInt(rs.getObject(1).toString());
        break;
        }
    }
    desconectar();
    }
    catch(SQLException | NumberFormatException e)
    {
      JOptionPane.showMessageDialog(null,"No se puede obtener el idPlan\n"+e);
    }
     return idPlan;
     
 }
public void llenarPlan() 
{
    
    
    
    String sql="select idPlan,nombrePlan from copral.plan order by idPlan";
    try
    {
    s=con.createStatement();
    rs=s.executeQuery(sql);
    DefaultComboBoxModel value = new DefaultComboBoxModel();
    while(rs.next())
    {
        
        
        value.addElement(new ComboVO(rs.getObject(1).toString(), rs.getObject(2).toString()));
    }
    jCPlan.setModel(value);
    desconectar();
    }
    catch(Exception e)
    {
      JOptionPane.showMessageDialog(null,"No se pudo llenar el combo Plan\n"+e);
    }
    
    

}       
public void llenarGrado(String plan) 
{
    jCGrado.removeAllItems();
    
    
    String sql="select g.nombreGrado,p.nombrePlan,g.idGrado from copral.grados g "
            + "inner join copral.plan p on p.idPlan=g.idPlan where g.idPlan="+plan+" order by g.nombreGrado ";
    try
    {
    s=con.createStatement();
    rs=s.executeQuery(sql);
    DefaultComboBoxModel value = new DefaultComboBoxModel();
    while(rs.next())
    {
        {
            value.addElement(new ComboVO(rs.getObject(3).toString(), rs.getObject(1).toString()));
        }
    }
    jCGrado.setModel(value);
    desconectar();
    }
    catch(Exception e)
    {
      JOptionPane.showMessageDialog(null,"No se pudo llenar el combo grados\n"+e);
    }
    
    

} 

public int getGrado(String grado)
{
    int codGrado=0;
   
    
    String sql="select g.idGrado,p.nombrePlan from copral.grados g "
            + "inner join plan p on p.idPlan=g.idPlan where g.nombreGrado='"+grado+"'"
            + " and p.nombrePlan='"+jCPlan.getSelectedItem().toString()+"';";
    try
    {
    s=con.createStatement();
    rs=s.executeQuery(sql);
    
    while(rs.next())
    {
        if(rs.getString(2).equals(jCPlan.getSelectedItem().toString()))
        {
            codGrado=Integer.parseInt(rs.getObject(1).toString());
        }
        
    }
    
    desconectar();
    }
    catch(SQLException e)
    {
      JOptionPane.showMessageDialog(null,"No se puede saber el grado\n"+e);
    }
    
    return codGrado;
    
}

public int cantidadAlumnos(String grado)
{
    int canAlumnos=0;
    
    String sql="select cantidadAlumnos from copral.grados where idGrado="+getGrado(grado)+";";
    
    try
    {
    s=con.createStatement();
    rs=s.executeQuery(sql);
    
    while(rs.next())
    {
        canAlumnos=(Integer.parseInt(rs.getObject(1).toString()));
        
    }
    canAlumnos++;
    desconectar();
    }
    catch(SQLException | NumberFormatException e)
    {
      JOptionPane.showMessageDialog(null,"No se puede saber cuantos alumnos hay\n"+e);
    }
    
    
    return canAlumnos;
    
}
public int getAlumno(String Apellido,String Nombre)
{
    int codAlumno=0;
    
    
    String sql="select idAlumno from copral.alumnos where nombre='"+Nombre+"'"
            + "and apellido='"+Apellido+"';";
    try
    {
    s=con.createStatement();
    rs=s.executeQuery(sql);
    
    while(rs.next())
    {
        codAlumno=Integer.parseInt(rs.getObject(1).toString());
        
    }
    desconectar();
    
    }
    catch(SQLException e)
    {
      JOptionPane.showMessageDialog(null,"No se puede obtener el Alumno\n"+e);
    }
    
    return codAlumno;
    
}
public int getMateria(String grado,String materia)
{
    int codMateria=0;
    
    
    String sql="select m.idMateria,m.nombreMateria,g.nombreGrado,p.nombrePlan from materias m "
            + "inner join grados g on m.idGrado=g.idGrado "
            + "inner join plan p on g.idPlan=p.idPlan "
            + " where p.nombrePlan='"+jCPlan.getSelectedItem().toString()+"'";
    
    try
    {
    s=con.createStatement();
    rs=s.executeQuery(sql);
    
    
    while(rs.next())
    {
        if(rs.getObject(3).toString().equals(grado) && rs.getString(2).equals(materia) && rs.getString(4).equals(jCPlan.getSelectedItem().toString()))
        {
         codMateria=Integer.parseInt(rs.getObject(1).toString());
         break;
        }
        
    }
    desconectar();
    
    }
    catch(SQLException e)
    {
      JOptionPane.showMessageDialog(null,"No se consiguio la materia\n"+e);
    }
    
    return codMateria;
    
}
public int getCanMaterias(String grado)
{
    int canMaterias=0;
        
     String sql="select cantidadMaterias from grados where idPlan="+grado+" and "
             + "nombreGrado='"+jCGrado.getSelectedItem().toString()+"'";
    
    try
    {
    s=con.createStatement();
    rs=s.executeQuery(sql);
    
    
    while(rs.next())
    {
        

        canMaterias=Integer.parseInt(rs.getObject(1).toString());
        
    }
    desconectar();
    
    }
    catch(SQLException e)
    {
      JOptionPane.showMessageDialog(null,"No se consiguio la cantidad de materias\n"+e);
    }   
    
    return canMaterias;
}
public void getNomMateria(String grado)
{
    int i=0;
        String sql="select m.nombreMateria,m.idMateria,g.nombreGrado,g.idPlan from materias m "
                + "inner join grados g on m.idGrado=g.idGrado";
    try
    {
    s=con.createStatement();
    rs=s.executeQuery(sql);
    
    
    while(rs.next())
    {
        if(rs.getString(4).equals(grado))
        {
            if(rs.getString(3).equals(jCGrado.getSelectedItem().toString()))
            {
                MateriaExis[i]=rs.getObject(1).toString();
                
                i++;
            }
        }
        
        
    }
    desconectar();
    
    }
    catch(SQLException e)
    {
      JOptionPane.showMessageDialog(null,"No se consiguio el nombre de materias\n"+e);
    }
    
}
public void aumentarAlumnos()
{
    try
    {
    desconectar();
     
    
    String sql2="update grados set cantidadAlumnos="+(cantidadAlumnos(jCGrado.getSelectedItem().toString()))
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
public void llenarMas()
{
    
    
    int i;
    int to=getCanMaterias(""+getPlan(jCPlan.getSelectedItem().toString()));
    try
    {
    getNomMateria(""+getPlan(jCPlan.getSelectedItem().toString())); 
    
    for(i=1;i<5;i++)
    {
        
        
        for(int x=0;x<to;x++)
        {
            desconectar();
            
            
    String sql3="insert into notas(idAlumno,idGrado,idBimestre,nota,idMateria,anio)"
            + "values("+getAlumno(txtApellido.getText(),txtNombre.getText())
            + ","+getGrado(jCGrado.getSelectedItem().toString())
            + ","+i+",0,"+getMateria(jCGrado.getSelectedItem().toString(),MateriaExis[x])
            +",2018);";
    
            
            
        s=con.createStatement();
        s.execute(sql3);
        
        
        }
    }
    
    }
    catch(SQLException e)
    {
       JOptionPane.showMessageDialog(null, "Error llenando las notas del alumno\n"+e); 
    }
}
public boolean buscarMaterias()
{
    boolean existe=false;
    
    desconectar();
    
        ComboVO dp = (ComboVO)jCGrado.getSelectedItem();
        String codigo = dp.getIdDepartamento();
        String nombre = dp.getDepartamento();
        
    String idGrado=codigo;
    String sql="select idGrado from copral.materias where "
            + "idGrado='"+idGrado+"';";
    try
    {
    
    s=con.createStatement();
    rs=s.executeQuery(sql);
    
    while(rs.next())
    {
        if((rs.getString(1)).equals(idGrado) )
        {
           
               existe=true;
           
           
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
public boolean buscarExistentes()
{
    boolean existe=false;
    
    desconectar();
    String sql="select nombre,apellido,idGrado from copral.alumnos where "
            + "nombre='"+txtNombre.getText()+"' and apellido='"+txtApellido.getText()+"';";
    try
    {
    
    s=con.createStatement();
    rs=s.executeQuery(sql);
    
    while(rs.next())
    {
        if((rs.getString(1)+" "+rs.getString(2)).equals(txtNombre.getText()+" "+txtApellido.getText()) )
        {
           
               existe=true;
           
           
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
public void ingresarDatos()
 {
     if(!txtNombre.getText().equals("")&&!txtApellido.getText().equals(""))
     {
         if(!buscarExistentes())
         {
            if(buscarMaterias())
            {
                try
                {



                    String sql="insert into alumnos(nombre,apellido,idGrado,idPlan) values"
                                + "('"+txtNombre.getText()+"','"+txtApellido.getText()+"',"
                                +getGrado(jCGrado.getSelectedItem().toString())+","+getPlan(jCPlan.getSelectedItem().toString())+");";



                            s=con.createStatement();
                            s.execute(sql);

                            aumentarAlumnos();

                             llenarMas();


                            desconectar();


                }
                catch(SQLException e)
                {
                    JOptionPane.showMessageDialog(null,"No se puede ingresar el alumno\n"+e);
                }
                catch(NullPointerException p)
                {

                }
            }else
            {
                JOptionPane.showMessageDialog(null,"Aun no ha ingresado materias a este grado");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Ese alumno ya esta ingresado en ese Grado"); 
                MostrarAlumno(txtNombre.getText(),txtApellido.getText());
            limpiar();
        }
     }
     else
     {
         JOptionPane.showMessageDialog(null, "Debe ingresar nombre y apellido");
         
     }
     
 }

 void MostrarAlumno(String Nombre,String Apellido)
 {
     desconectar();
    String sql="select a.nombre,a.apellido,g.nombreGrado,p.nombrePlan from copral.alumnos a "
            + "inner join grados g on g.idGrado=a.idGrado "
            + "inner join plan p on p.idPlan=g.idPlan where "
            + "nombre='"+txtNombre.getText()+"' and apellido='"+txtApellido.getText()+"';";
    try
    {
    
    s=con.createStatement();
    rs=s.executeQuery(sql);
    
    while(rs.next())
    {
        if((rs.getString(1)+" "+rs.getString(2)).equals(txtNombre.getText()+" "+txtApellido.getText()) )
        {
           
               JOptionPane.showMessageDialog(null,"Nombre: "+rs.getString(1)+" "+rs.getString(2)+"\n"
                       + "Grado: "+rs.getString(3)+"\nPlan: "+rs.getString(4));
           
           
        }
        
    }
    
    desconectar();
    }
    catch(Exception e)
    {
      JOptionPane.showMessageDialog(null,"No Existe el Alumno\n"+e);
    }
 }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNombre = new javax.swing.JLabel();
        lblApellido = new javax.swing.JLabel();
        lblPlan = new javax.swing.JLabel();
        jCPlan = new javax.swing.JComboBox();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        cmdGuardar = new javax.swing.JButton();
        cmdCancelar = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        jCGrado = new javax.swing.JComboBox();
        lblGrado = new javax.swing.JLabel();
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

        lblNombre.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblNombre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNombre.setText("Nombres");

        lblApellido.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblApellido.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblApellido.setText("Apellidos");

        lblPlan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblPlan.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPlan.setText("Plan");

        jCPlan.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jCPlan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCPlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCPlanActionPerformed(evt);
            }
        });

        txtNombre.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtNombre.setText("jTextField1");
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreKeyPressed(evt);
            }
        });

        txtApellido.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtApellido.setText("jTextField1");
        txtApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtApellidoKeyPressed(evt);
            }
        });

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

        lblTitulo.setFont(new java.awt.Font("Cooper Black", 0, 24)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Ingreso de Alumnos");

        jCGrado.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jCGrado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblGrado.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblGrado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblGrado.setText("Grado");

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
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Creador)
                                .addGap(89, 89, 89)
                                .addComponent(cmdGuardar)
                                .addGap(18, 18, 18)
                                .addComponent(cmdCancelar))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(34, 34, 34)
                                            .addComponent(lblGrado, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jCGrado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(lblApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblPlan, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jCPlan, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 26, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPlan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCPlan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGrado, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCGrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(110, 110, 110)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmdGuardar)
                            .addComponent(cmdCancelar))
                        .addGap(46, 46, 46))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(Creador))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCancelarActionPerformed

        Coneccion.inicioSalida(this);
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdCancelarActionPerformed

    private void cmdGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdGuardarActionPerformed
        //llenarMas();
       
        
        ingresarDatos();
        limpiar();
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdGuardarActionPerformed

    private void txtNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyPressed

        
        if(evt.getKeyChar()==KeyEvent.VK_ENTER)
        {
            txtApellido.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreKeyPressed

    private void txtApellidoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyPressed

         String tecla=""+evt.getKeyChar();
        
        if(evt.getKeyChar()==KeyEvent.VK_ENTER)
        {
            if(jCPlan.getSelectedItem()!="")
            {
            ingresarDatos();
            limpiar();
            }
            else
            {
                jCPlan.requestFocus();
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoKeyPressed

    private void jCPlanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCPlanActionPerformed

        try
        {
        
        ComboVO dp = (ComboVO)jCPlan.getSelectedItem();
        String codigo = dp.getIdDepartamento();
        String nombre = dp.getDepartamento();
        
        llenarGrado(codigo);
        }
        catch(Exception e)
        {
            
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jCPlanActionPerformed

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
            java.util.logging.Logger.getLogger(IngresoAlumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IngresoAlumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IngresoAlumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IngresoAlumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IngresoAlumnos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Creador;
    private javax.swing.JButton cmdCancelar;
    private javax.swing.JButton cmdGuardar;
    private javax.swing.JComboBox jCGrado;
    private javax.swing.JComboBox jCPlan;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMnItmAyuda;
    private javax.swing.JMenuItem jMnItmCreador;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblGrado;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPlan;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JMenu mnArchivo;
    private javax.swing.JMenu mnAyuda;
    private javax.swing.JMenuItem mnItmImprimir;
    private javax.swing.JMenuItem mnItmIngresoNotas;
    private javax.swing.JMenuItem mnItmSalir;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
