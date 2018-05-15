package Calificaciones;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * @author JoséDaniel
 */
public final class IngresoCalificaciones extends javax.swing.JFrame 
{
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
   
    public IngresoCalificaciones() {
        initComponents();
        Coneccion.Propiedades(this);
        
        this.setTitle("Ingreso de Notas");
        
       con=Coneccion.getConeccion();
        
                  jCGrado.disable();
                  jCMaterias.disable();
        jCMaterias.removeAllItems();
        jCGrado.removeAllItems();
        
        DefaultComboBoxModel value = new DefaultComboBoxModel();
        
        value.addElement(new ComboVO("", ""));
        jCMaterias.setModel(value);
        jCGrado.setModel(value);
        jCPlan.setModel(value);
    
        llenarPlan();
        llenarAlumnos();
        llenarBimestres();
        
        jCGrado.requestFocus();
    }

void mostrarDatos(String idPlan,String idGrado,String idMateria){
        
    
    DefaultTableModel modelo=new DefaultTableModel();
       
        
        modelo.addColumn("Id");
        modelo.addColumn("Apellido");
        modelo.addColumn("Nombre");
        modelo.addColumn("Nota");
        
        tbAlumnos.setModel(modelo);
        tbAlumnos.getColumnModel().getColumn(0).setMaxWidth(0);

        tbAlumnos.getColumnModel().getColumn(0).setMinWidth(0);

        tbAlumnos.getColumnModel().getColumn(0).setPreferredWidth(0);


        String sql="";
        
                
           
        
        ComboVO dp = (ComboVO)jCBimestres.getSelectedItem();
            String idBimestre = dp.getIdDepartamento();
        
         sql="SELECT g.nombreGrado,a.nombre,b.bimestre,a.apellido,m.nombreMateria,n.nota,g.idPlan,a.idAlumno FROM notas n "
                    + "inner join grados g on n.idGrado=g.idGrado "
                    + "inner join alumnos a on n.idAlumno=a.idAlumno "
                    + "inner join bimestres b on n.idBimestre=b.idBimestre "
                    + "inner join materias m on n.idMateria=m.idMateria where n.idMateria="+idMateria+" and n.idGrado="+idGrado+" and g.idPlan="+idPlan+" and n.idBimestre="+idBimestre+";";
        String datos[]=new String[4];
        try {
            s=con.createStatement();
            rs=s.executeQuery(sql);
        
        while(rs.next())
        {
                     
            datos[0]= rs.getString(8);
            datos[1]= rs.getString(4);
            datos[2]= rs.getString(2);
            datos[3]= rs.getString(6);
            
            modelo.addRow(datos);
          
        
        
        
        
        
        
        
        }
        tbAlumnos.setModel(modelo);
desconectar();
        
        } catch (Exception ex) {
            
           }
        
        
        
        
        
    }    
    
public void llenarMaterias(String grado,int idplan)
{
     
        
    String com;
    
    ComboVO dp = (ComboVO)jCPlan.getSelectedItem();
        String idPlan = dp.getIdDepartamento();
    ComboVO dp1 = (ComboVO)jCGrado.getSelectedItem();
        String idGrado = dp1.getIdDepartamento();
    String sql="select m.idMateria,m.nombreMateria,g.nombreGrado,g.idPlan from copral.materias m "
            + "inner join grados g on m.idGrado=g.idGrado where m.idGrado="+idGrado+" and g.idPlan="+idPlan+" order by nombreMateria ";
    try
    {
    s=con.createStatement();
    rs=s.executeQuery(sql);
    DefaultComboBoxModel value = new DefaultComboBoxModel();
value.addElement(new ComboVO("", ""));
    while(rs.next())
    {
        
               
                    value.addElement(new ComboVO(rs.getObject(1).toString(), rs.getObject(2).toString()));
           
    }
    
    jCMaterias.setModel(value);
      
desconectar();
    }
    catch(SQLException e)
    {
      JOptionPane.showMessageDialog(null,"Imposible llenar el combo Materias");
    }

}
public void llenarPlan() 
{
    
        jCPlan.removeAllItems();
    
    
    String sql="select idPlan,nombrePlan from copral.plan order by idPlan ";
    try
    {
    s=con.createStatement();
    rs=s.executeQuery(sql);
        DefaultComboBoxModel value = new DefaultComboBoxModel();
value.addElement(new ComboVO("", ""));
    while(rs.next())
    {
        value.addElement(new ComboVO(rs.getObject(1).toString(), rs.getObject(2).toString()));
      
    }
   jCPlan.setModel(value);
     desconectar();   

    }
    catch(SQLException e)
    {
      JOptionPane.showMessageDialog(null,"Imposible llenar el combo Grado");
    }
    
    

}

public void llenarGrado(String plan) 
{
    
        jCGrado.removeAllItems();
    
    ComboVO dp = (ComboVO)jCPlan.getSelectedItem();
        String idPlan = dp.getIdDepartamento();
    String sql="select g.idGrado,g.nombreGrado,p.nombrePlan from copral.grados g "
            + "inner join copral.plan p on p.idPlan=g.idPlan where g.idPlan="+idPlan+" order by g.nombreGrado ";
    try
    {
    s=con.createStatement();
    rs=s.executeQuery(sql);
        DefaultComboBoxModel value = new DefaultComboBoxModel();
value.addElement(new ComboVO("", ""));
    while(rs.next())
    {
        
            value.addElement(new ComboVO(rs.getObject(1).toString(), rs.getObject(2).toString()));
        
        
    }
    jCGrado.setModel(value);
    desconectar();
    }
    catch(Exception e)
    {
      JOptionPane.showMessageDialog(null,"No se pudo llenar el combo grados\n"+e);
    }
    
    

}

public void llenarAlumnos()
{
   
         
}
public void llenarBimestres()
{
    jCBimestres.removeAllItems();
    
    
    String sql="select idBimestre,bimestre from copral.bimestres order by bimestre";
    try
    {
    s=con.createStatement();
    rs=s.executeQuery(sql);
        DefaultComboBoxModel value = new DefaultComboBoxModel();
         value.addElement(new ComboVO("", ""));
    while(rs.next())
    {
        value.addElement(new ComboVO(rs.getObject(1).toString(), rs.getObject(2).toString()));
        
    }
    jCBimestres.setModel(value);
    desconectar();
    
    }
    catch(SQLException e)
    {
      JOptionPane.showMessageDialog(null,"Imposible llenar el combo Bimestres");
    }
    
    

}

public int getGrado(String grado)
{
    int codGrado=0;
   
    
    String sql="select idGrado from copral.grados where nombreGrado='"+grado+"' and idPlan="+getPlan(jCPlan.getSelectedItem().toString())+";";
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
      JOptionPane.showMessageDialog(null,"Imposible llenar el combo Bimestres");
    }
    
    return codGrado;
    
}
    


public int getMateria(String grado,String Materia)
{
    int codMateria=0;
    
    
    String sql="select m.idMateria,g.nombreGrado,m.nombreMateria from materias m inner join grados g on m.idGrado=g.idGrado "
            + "where g.idplan="+getPlan(jCPlan.getSelectedItem().toString());
    
    try
    {
    s=con.createStatement();
    rs=s.executeQuery(sql);
    
    
    while(rs.next())
    {
        if(rs.getObject(2).toString().equals(grado) && rs.getString(3).equals(Materia))
        {
        codMateria=Integer.parseInt(rs.getObject(1).toString());
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

public int getBimestre(String bimestre)
{
    int codBimestre=0;
    
    
    String sql="select idBimestre from copral.bimestres where bimestre='"+bimestre+"';";
    try
    {
    s=con.createStatement();
    rs=s.executeQuery(sql);
    
    while(rs.next())
    {
        codBimestre=Integer.parseInt(rs.getObject(1).toString());
        
    }
    desconectar();
    
    }
    catch(SQLException e)
    {
      JOptionPane.showMessageDialog(null,"No se puede obtener el Bimestre");
    }
    
    return codBimestre;
    
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

public void ingresarNotas()
{
    

    try
    {
      
    for(int i=0;i<(tbAlumnos.getRowCount());i++)
    {
        
    String sql="update copral.notas set nota="+tbAlumnos.getValueAt(i,3)+" where idMateria="
            +getMateria(jCGrado.getSelectedItem().toString(),jCMaterias.getSelectedItem().toString())+" and idGrado="
            +getGrado(jCGrado.getSelectedItem().toString())+" and idBimestre="
            +getBimestre(jCBimestres.getSelectedItem().toString())+" and idAlumno="+tbAlumnos.getValueAt(i,0).toString()+"";
    
        s=con.createStatement();
        s.executeUpdate(sql);
        desconectar();
    }
    
    JOptionPane.showMessageDialog(null, "Las notas se guardaron correctamente");
    
    
    
    }
    catch(SQLException e)
    {
      JOptionPane.showMessageDialog(null,"No se puede actualizar\n"+e);
    }
}

public int getPlan(String plan)
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
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCGrado = new javax.swing.JComboBox();
        lblComboGrado = new javax.swing.JLabel();
        jCBimestres = new javax.swing.JComboBox();
        lblBimestre = new javax.swing.JLabel();
        cmdGuardar = new javax.swing.JButton();
        cmdCancelar = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        lblComboMateria = new javax.swing.JLabel();
        jCMaterias = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbAlumnos = new javax.swing.JTable();
        jCPlan = new javax.swing.JComboBox();
        lblPlan = new javax.swing.JLabel();
        Creador = new javax.swing.JLabel();
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
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnItmTraslado = new javax.swing.JMenuItem();
        mnAyuda = new javax.swing.JMenu();
        jMnItmAyuda = new javax.swing.JMenuItem();
        jMnItmCreador = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1355, 720));

        jCGrado.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jCGrado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCGrado.setEnabled(false);
        jCGrado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jCGradoFocusLost(evt);
            }
        });
        jCGrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCGradoActionPerformed(evt);
            }
        });

        lblComboGrado.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblComboGrado.setForeground(new java.awt.Color(255, 255, 255));
        lblComboGrado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblComboGrado.setText("Grado");

        jCBimestres.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jCBimestres.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCBimestres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBimestresActionPerformed(evt);
            }
        });

        lblBimestre.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblBimestre.setForeground(new java.awt.Color(255, 255, 255));
        lblBimestre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBimestre.setText("Bimestre");

        cmdGuardar.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        cmdGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Calificaciones/Iconos/image/down.png"))); // NOI18N
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
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Ingreso de Notas");

        lblComboMateria.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblComboMateria.setForeground(new java.awt.Color(255, 255, 255));
        lblComboMateria.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblComboMateria.setText("Materia");

        jCMaterias.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jCMaterias.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCMaterias.setEnabled(false);
        jCMaterias.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jCMateriasFocusGained(evt);
            }
        });
        jCMaterias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCMateriasActionPerformed(evt);
            }
        });

        tbAlumnos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tbAlumnos.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tbAlumnos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tbAlumnos);

        jCPlan.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jCPlan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCPlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCPlanActionPerformed(evt);
            }
        });

        lblPlan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblPlan.setForeground(new java.awt.Color(255, 255, 255));
        lblPlan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPlan.setText("Plan");

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
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1227, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblComboGrado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCGrado, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblBimestre, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                            .addComponent(jCBimestres, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jCPlan, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jCMaterias, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblPlan, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblComboMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Creador)
                        .addGap(416, 416, 416)
                        .addComponent(cmdGuardar)
                        .addGap(100, 100, 100)
                        .addComponent(cmdCancelar))
                    .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 1317, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblComboGrado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCGrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(lblBimestre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCBimestres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblComboMateria)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCMaterias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(lblPlan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCPlan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmdGuardar)
                            .addComponent(cmdCancelar)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Creador)))
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCGradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCGradoActionPerformed

        try{
            ComboVO dp = (ComboVO)jCPlan.getSelectedItem();
            String idPlan = dp.getIdDepartamento();
            ComboVO dp1 = (ComboVO)jCGrado.getSelectedItem();
            String idGrado = dp1.getIdDepartamento();
            ComboVO dp2 = (ComboVO)jCMaterias.getSelectedItem();
            String idMateria = dp2.getIdDepartamento();
            
            mostrarDatos(idPlan,idGrado,idMateria);
        }catch(Exception e){
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jCGradoActionPerformed

    private void jCBimestresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBimestresActionPerformed

        
            try{
            ComboVO dp = (ComboVO)jCPlan.getSelectedItem();
            String idPlan = dp.getIdDepartamento();
            ComboVO dp1 = (ComboVO)jCGrado.getSelectedItem();
            String idGrado = dp1.getIdDepartamento();
            ComboVO dp2 = (ComboVO)jCMaterias.getSelectedItem();
            String idMateria = dp2.getIdDepartamento();
            
            mostrarDatos(idPlan,idGrado,idMateria);
        }catch(Exception e){
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jCBimestresActionPerformed

    private void jCMateriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCMateriasActionPerformed

       
        
         try{
            ComboVO dp = (ComboVO)jCPlan.getSelectedItem();
            String idPlan = dp.getIdDepartamento();
            ComboVO dp1 = (ComboVO)jCGrado.getSelectedItem();
            String idGrado = dp1.getIdDepartamento();
            ComboVO dp2 = (ComboVO)jCMaterias.getSelectedItem();
            String idMateria = dp2.getIdDepartamento();
            
            mostrarDatos(idPlan,idGrado,idMateria);
        }catch(Exception e){
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jCMateriasActionPerformed

    private void cmdGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdGuardarActionPerformed

        
        ingresarNotas();
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdGuardarActionPerformed

    private void cmdCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCancelarActionPerformed
         Coneccion.inicio(this);
        
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdCancelarActionPerformed

    private void jCGradoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jCGradoFocusLost

        try
        {
            jCMaterias.enable();
           jCMaterias.removeAllItems(); 
        llenarMaterias(jCGrado.getSelectedItem().toString(),getPlan(jCPlan.getSelectedItem().toString()));
        }
        catch(Exception e)
        {
            
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jCGradoFocusLost

    private void jCMateriasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jCMateriasFocusGained

        
        // TODO add your handling code here:
    }//GEN-LAST:event_jCMateriasFocusGained

    private void jCPlanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCPlanActionPerformed
        try
                {
                  jCGrado.enable();
                    llenarGrado(jCPlan.getSelectedItem().toString());  
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

    private void mnItmIngresoAlumnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnItmIngresoAlumnosActionPerformed

        Coneccion.alumnos();
        // TODO add your handling code here:
    }//GEN-LAST:event_mnItmIngresoAlumnosActionPerformed

    private void mnItmIngresoMateriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnItmIngresoMateriasActionPerformed

        Coneccion.validar(JOptionPane.showInputDialog("Ingrese el usuario"),"Materias",con,s,rs,this);
        // TODO add your handling code here:
    }//GEN-LAST:event_mnItmIngresoMateriasActionPerformed

    private void mnItmIngresoGradosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnItmIngresoGradosActionPerformed

        Coneccion.validar(JOptionPane.showInputDialog("Ingrese el usuario"),"Grados",con,s,rs,this);
        // TODO add your handling code here:
    }//GEN-LAST:event_mnItmIngresoGradosActionPerformed

    private void mnItmIngresoMaestrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnItmIngresoMaestrosActionPerformed

        Coneccion.validar(JOptionPane.showInputDialog("Ingrese el usuario"),"Maestros",con,s,rs,this);
        // TODO add your handling code here:
    }//GEN-LAST:event_mnItmIngresoMaestrosActionPerformed

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
            java.util.logging.Logger.getLogger(IngresoCalificaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IngresoCalificaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IngresoCalificaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IngresoCalificaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IngresoCalificaciones().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Creador;
    private javax.swing.JButton cmdCancelar;
    private javax.swing.JButton cmdGuardar;
    private javax.swing.JComboBox jCBimestres;
    private javax.swing.JComboBox jCGrado;
    private javax.swing.JComboBox jCMaterias;
    private javax.swing.JComboBox jCPlan;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMnItmAyuda;
    private javax.swing.JMenuItem jMnItmCreador;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel lblBimestre;
    private javax.swing.JLabel lblComboGrado;
    private javax.swing.JLabel lblComboMateria;
    private javax.swing.JLabel lblPlan;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JMenu mnArchivo;
    private javax.swing.JMenu mnAyuda;
    private javax.swing.JMenuItem mnItmImprimir;
    private javax.swing.JMenuItem mnItmIngresoAlumnos;
    private javax.swing.JMenuItem mnItmIngresoGrados;
    private javax.swing.JMenuItem mnItmIngresoMaestros;
    private javax.swing.JMenuItem mnItmIngresoMaterias;
    private javax.swing.JMenuItem mnItmIngresoNotas;
    private javax.swing.JMenuItem mnItmSalir;
    private javax.swing.JMenuItem mnItmTraslado;
    private javax.swing.JMenu mnOpciones;
    private javax.swing.JTable tbAlumnos;
    // End of variables declaration//GEN-END:variables
}
