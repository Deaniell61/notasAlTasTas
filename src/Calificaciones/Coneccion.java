/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calificaciones;

import static java.awt.Frame.MAXIMIZED_BOTH;
import java.sql.DriverManager;
import java.sql.*;
import java.text.Normalizer.Form;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author JoséDaniel
 */
public class Coneccion {
 
    public  static Connection getConeccion()
    {
        Connection conexion=null;
         Statement s;
        String baseDatos="copral";
          
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            String servidor = "jdbc:mysql://localhost/"+baseDatos;
            String usuarioDB="root";
            String passwordDB="1234";
            conexion= DriverManager.getConnection(servidor,usuarioDB,passwordDB);
            
            
        }
        catch(ClassNotFoundException | SQLException e)
        {
          JOptionPane.showMessageDialog(null,"Coneccion no se pudo establecer\nError: "+e);
        }
        return conexion;
    }
    public static void Propiedades(JFrame form)
    {
         form.setLocationRelativeTo(null);
         form.getContentPane().setBackground(java.awt.Color.DARK_GRAY);
        form.setResizable(false);
        form.setDefaultCloseOperation(Inicio.EXIT_ON_CLOSE);
        
        form.setSize(1355, 720);
        form.setExtendedState(MAXIMIZED_BOTH);
        
     }
    public static void validar(String user,String pagina,Connection con,Statement s,ResultSet rs,JFrame este)
 {
     String sql="select user,pass from copral.users";
    try
    {
    s=con.createStatement();
    rs=s.executeQuery(sql);
    
    while(rs.next())
    {
        if(rs.getString(1).equals(user))
        {
            if(rs.getString(2).equals(JOptionPane.showInputDialog("Ingrese la contraseña\nUser: "+user)))
            {
                if("Grados".equals(pagina))
                {
                IngresoGrados ner=new IngresoGrados();
                ner.setVisible(true);
                
                }
                else
                {
                    if("Materias".equals(pagina))
                    {
                      IngresoMaterias ner=new IngresoMaterias();
                      ner.setVisible(true);
                      
                    }
                    else
                    {
                         if("Maestros".equals(pagina))
                         {
                            IngresoMaestros ner=new IngresoMaestros();
                            ner.setVisible(true);
                            
                         }else{
                             if("ElimMateria".equals(pagina))
                             {
                                 EliminarMaterias ner=new EliminarMaterias();
                                    ner.setVisible(true);
                             }
                                 
                         }
                    }
                }
            }
        }
    }
    desconectar(con,s,rs);
    }
    catch(SQLException | NumberFormatException e)
    {
      JOptionPane.showMessageDialog(null,"No se puede obtener el idPlan\n"+e);
    }
 }
    public static void desconectar(Connection con,Statement s,ResultSet rs)
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
    public static void imprimir()
    {
        Impresion enviar=new Impresion();
        enviar.setVisible(true);
    }
    public static void alumnos()
    {
      IngresoAlumnos enviar=new IngresoAlumnos();
        
        enviar.setVisible(true);
        
    }
    public static void calificar(JFrame es)
    {
        IngresoCalificaciones enviar= new IngresoCalificaciones();
        
        enviar.setVisible(true);
        es.setVisible(false);
    }
    public static void inicioSalida(JFrame este)
    {
        este.setVisible(false);
    }
    public static void inicio(JFrame este)
    {
        Inicio ner=new Inicio();
        ner.setVisible(true);
        este.setVisible(false);
    }
}
