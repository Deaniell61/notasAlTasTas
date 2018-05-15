package Calificaciones;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import javax.swing.JOptionPane;
import java.sql.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.HeadlessException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * @author JoséDaniel
 */
public final class Impresion extends javax.swing.JFrame {

    Connection con=Coneccion.getConeccion();
    Statement s;
    ResultSet rs;
    boolean plan=false,grado=false,alumno=false;
    String ImpTo="Imprimir Todos";
    String ImpGra="Imprimir Grados";
    String ImpAlu="Imprimir Alumno";
            
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
    
    public Impresion() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(Inicio.HIDE_ON_CLOSE);
        this.getContentPane().setBackground(java.awt.Color.white);
        this.setResizable(false);
        this.setTitle("Impresion");
        
        jCOpcionImprimir.removeAllItems();
        jCGrado.removeAllItems();
        jCAlumnos.removeAllItems();
        jCPlan.removeAllItems();
      
        llenarOpcionesImpresion();
    }
   public int cantidadGrados()
    {
        int numeroGrados=0;
        try
       {
           
           
           String numero="select count(*) from copral.grados";
           s=con.createStatement();
           rs=s.executeQuery(numero);
           if(rs.next())
           {
               numeroGrados=rs.getInt("count(*)");
           }
           
           
           desconectar();
       }
       catch(Exception e)
       {
           JOptionPane.showMessageDialog(null,"Error al consultar\n"+e);
       }
       return numeroGrados; 
    } 

   public int cantidadAlumnos(String grado)
{
    int canAlumnos=0;
    
    String sql="select cantidadAlumnos from copral.grados where idGrado="+getGrado(grado)+" and idPlan="+getIdPlan(jCPlan.getSelectedItem().toString())+";";
    try
    {
    s=con.createStatement();
    rs=s.executeQuery(sql);
    
    while(rs.next())
    {
        canAlumnos=(Integer.parseInt(rs.getObject(1).toString()));
        
    }
    
    desconectar();
    }
    catch(SQLException | NumberFormatException e)
    {
      JOptionPane.showMessageDialog(null,"No se puede saber cuantos alumnos hay");
    }
    
    
    return canAlumnos+1;
    
}
   
public static PdfPTable tablaEncabezado(){
    PdfPTable tabla = new PdfPTable(14);
     /*celda = new PdfPCell(new Phrase("Celda tomando 2 espacios verticales"));
    celda.setRowspan(2);
    tabla.addCell(celda);*/
    PdfPCell celda;
    
//tabla.addCell("Materia");
    
    celda = new PdfPCell(new Phrase("Materia"));
    celda.setHorizontalAlignment(1);
    celda.setColspan(5);
    tabla.addCell(celda);
    
    celda = new PdfPCell(new Phrase("Bimestre 1"));
    celda.setHorizontalAlignment(1);
    celda.setColspan(2);
    tabla.addCell(celda);
    
    celda = new PdfPCell(new Phrase("Bimestre 2"));
    celda.setHorizontalAlignment(1);
    celda.setColspan(2);
    tabla.addCell(celda);
    
    celda = new PdfPCell(new Phrase("Bimestre 3"));
    celda.setHorizontalAlignment(1);
    celda.setColspan(2);
    tabla.addCell(celda);
    
    celda = new PdfPCell(new Phrase("Bimestre 4"));
    celda.setHorizontalAlignment(1);
    celda.setColspan(2);
    tabla.addCell(celda);
   
    tabla.addCell("Total");
    
    
 
    return tabla;
  }

public PdfPTable tablaFirmas(){
    PdfPTable tabla = new PdfPTable(9);
     /*celda = new PdfPCell(new Phrase("Celda tomando 2 espacios verticales"));
    celda.setRowspan(2);
    tabla.addCell(celda);*/
    PdfPCell celda;
    
//tabla.addCell("Materia");
    
    celda = new PdfPCell(new Phrase("Firmas"));
    celda.setRowspan(2);
    tabla.addCell(celda);
    
    celda = new PdfPCell(new Phrase("Bimestre 1"));
    celda.setColspan(2);
    tabla.addCell(celda);
    
    celda = new PdfPCell(new Phrase("Bimestre 2"));
    celda.setColspan(2);
    tabla.addCell(celda);
    
    celda = new PdfPCell(new Phrase("Bimestre 3"));
    celda.setColspan(2);
    tabla.addCell(celda);
    
    celda = new PdfPCell(new Phrase("Bimestre 4"));
    celda.setColspan(2);
    tabla.addCell(celda);
    
    celda = new PdfPCell(new Phrase(" "));
    celda.setColspan(2);
    tabla.addCell(celda);
    
    celda = new PdfPCell(new Phrase(" "));
    celda.setColspan(2);
    tabla.addCell(celda);
    
    celda = new PdfPCell(new Phrase(" "));
    celda.setColspan(2);
    tabla.addCell(celda);
    
    celda = new PdfPCell(new Phrase(" "));
    celda.setColspan(2);
    tabla.addCell(celda);
   
    
    
 
    return tabla;
  }
   
public static PdfPTable agregarMateria(String materia,String[] bimestres){
    PdfPTable tabla = new PdfPTable(14);
    PdfPCell celda;
    
    Paragraph par0 = new Paragraph();
    par0.setFont(FontFactory.getFont("Arial",8, BaseColor.BLACK));
    par0.add(materia);
    celda = new PdfPCell(new Phrase(par0));
    celda.setHorizontalAlignment(0);
    celda.setColspan(5);
    
    tabla.addCell(celda);
    Paragraph par1 = new Paragraph();
    par1.setAlignment(Paragraph.ALIGN_CENTER);
    
    if((Integer.parseInt(bimestres[0]))<60){
    if("0".equals(bimestres[0]))
    {
    par1.setFont(FontFactory.getFont("Sans",14, BaseColor.WHITE));}
    else
    {
    par1.setFont(FontFactory.getFont("Sans",14, BaseColor.RED));}}
    else
    {
    par1.setFont(FontFactory.getFont("Sans",14, BaseColor.BLACK));}
    
      par1.add(bimestres[0]);
    celda = new PdfPCell(par1);
    celda.setHorizontalAlignment(1);
    celda.setColspan(2);
    tabla.addCell(celda);
    
    Paragraph par2 = new Paragraph();
    par2.setAlignment(Paragraph.ALIGN_CENTER);
    
    if((Integer.parseInt(bimestres[1]))<60){
    if("0".equals(bimestres[1]))
    {
    par2.setFont(FontFactory.getFont("Sans",14, BaseColor.WHITE));}
    else
    {
    par2.setFont(FontFactory.getFont("Sans",14, BaseColor.RED));}}
    else
    {
    par2.setFont(FontFactory.getFont("Sans",14, BaseColor.BLACK));}
    
      par2.add(bimestres[1]);
    celda = new PdfPCell(new Phrase(par2));
    celda.setHorizontalAlignment(1);
    celda.setColspan(2);
    tabla.addCell(celda);
    
    Paragraph par3 = new Paragraph();
    par3.setAlignment(Paragraph.ALIGN_CENTER);
    
    if((Integer.parseInt(bimestres[2]))<60){
    if("0".equals(bimestres[2]))
    {
    par3.setFont(FontFactory.getFont("Sans",14, BaseColor.WHITE));}
    else
    {
    par3.setFont(FontFactory.getFont("Sans",14, BaseColor.RED));}}
    else
    {
    par3.setFont(FontFactory.getFont("Sans",14, BaseColor.BLACK));}
    
      par3.add(bimestres[2]);
    celda = new PdfPCell(new Phrase(par3));
    celda.setHorizontalAlignment(1);
    celda.setColspan(2);
    tabla.addCell(celda);
    
    Paragraph par4 = new Paragraph();
    par4.setAlignment(Paragraph.ALIGN_CENTER);
    
    if((Integer.parseInt(bimestres[3]))<60){
    if("0".equals(bimestres[3]))
    {
    par4.setFont(FontFactory.getFont("Sans",14, BaseColor.WHITE));}
    else
    {
    par4.setFont(FontFactory.getFont("Sans",14, BaseColor.RED));}}
    else
    {
    par4.setFont(FontFactory.getFont("Sans",14, BaseColor.BLACK));}
    
      par4.add(bimestres[3]);
    celda = new PdfPCell(par4);
    celda.setHorizontalAlignment(1);
    celda.setColspan(2);
    tabla.addCell(celda);
   
    Paragraph par5 = new Paragraph();
    par5.setAlignment(Paragraph.ALIGN_CENTER);
    
    if(Float.parseFloat(bimestres[4])<60){
        if("0".equals(bimestres[4]))
    {
    par5.setFont(FontFactory.getFont("Sans",14, BaseColor.WHITE));}
    else
    {
    par5.setFont(FontFactory.getFont("Sans",14, BaseColor.RED));}}
    else{
    
    par5.setFont(FontFactory.getFont("Sans",14, BaseColor.BLACK));}
    
      par5.add(bimestres[4]);
    celda = new PdfPCell(par5);
    celda.setHorizontalAlignment(1);
    celda.setColspan(2);
    tabla.addCell(celda);
    
    
    
 
    return tabla;
  }

    public  void imprimirTodos(int grados) throws FileNotFoundException, DocumentException, BadElementException, IOException
   {
      
      String direccion=System.getProperty("user.dir")+"/Todas Las Notas.pdf"; 
      FileOutputStream archivo = new FileOutputStream(direccion);
      Document documento = new Document(PageSize.LETTER);
      
      PdfWriter.getInstance(documento, archivo);
      documento.addTitle("Calificaciones");
      documento.addAuthor("DeaNiell");
      Statement sNotas,sGrados,sAlumnos,sMaterias,sPlan;
      ResultSet rsNotas,rsGrados,rsAlumnos,rsMaterias,rsPlan;
      
      Paragraph parrafo = new Paragraph();
      parrafo.setAlignment(Paragraph.ALIGN_LEFT);
      parrafo.setFont(FontFactory.getFont("Sans",12,Font.BOLD, BaseColor.BLACK));
      
      Paragraph parrafo2 = new Paragraph();
      parrafo2.setAlignment(Paragraph.ALIGN_RIGHT);
      parrafo2.setFont(FontFactory.getFont("Sans",12,Font.BOLD, BaseColor.BLACK));
      
      
      try
      {
          
         documento.open(); 
          sPlan=con.createStatement();
          String sqlPlan="select idPlan,nombrePlan from plan";
          rsPlan=sPlan.executeQuery(sqlPlan);
            
          while(rsPlan.next())
          {
          sGrados=con.createStatement();
          String sqlGrados="select idGrado,nombreGrado,graduando from grados where idplan="+rsPlan.getString(1);
          rsGrados=sGrados.executeQuery(sqlGrados);
            
          while(rsGrados.next())
          {
          
         sAlumnos= con.createStatement();
         String sqlAlumnos="select nombre,apellido,idAlumno from alumnos where idGrado="+rsGrados.getString(1);
         rsAlumnos=sAlumnos.executeQuery(sqlAlumnos);
         
            while(rsAlumnos.next())
             
            {
                parrafo.removeAll(parrafo);
                parrafo2.removeAll(parrafo2);
                Image imagen = Image.getInstance("EncabezadoCopral.jpg");
                imagen.setAlignment(Element.ALIGN_CENTER);
                documento.add(imagen);
                parrafo2.add("Año: 2018");
                parrafo.add("Grado: "+rsGrados.getString(2)+"\t\t\t\t\t"
                        + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t Plan: "+(rsPlan.getString(2))+"\n\n");
                parrafo.add("\t\t\t\tNombre de Alumno(a): "+rsAlumnos.getString(1)+" "+rsAlumnos.getString(2)+"\n\n\n");
                documento.add(parrafo2);
                documento.add(parrafo);
                documento.add(tablaEncabezado());
                String sqlMaterias="select nombreMateria,idMateria from materias where idGrado="+rsGrados.getString(1);
                    sMaterias=con.createStatement();
                    rsMaterias=sMaterias.executeQuery(sqlMaterias);
                    String[] bimestres=new String[5];
                    
                        while(rsMaterias.next())
                        {
                            
                            int total=0,cont=0;
                           
                            String sqlNotas="select nota,idbimestre from notas where idMateria="+rsMaterias.getString(2)
                                    +" and idAlumno="+rsAlumnos.getString(3);
                                sNotas=con.createStatement();
                                
                                rsNotas=sNotas.executeQuery(sqlNotas);
                            
                                    while(rsNotas.next())
                                    {
                                        switch(rsNotas.getString(2)){
                                        
                                            case "1":{bimestres[0]=rsNotas.getString(1);
                                        
                                         total=total+(Integer.parseInt(rsNotas.getString(1)));
                            
                                            if(Integer.parseInt(rsNotas.getString(1))>0)
                                            {
                                                cont++;
                                            } break;}
                                            case "2":{bimestres[1]=rsNotas.getString(1);
                                        
                                         total=total+(Integer.parseInt(rsNotas.getString(1)));
                            
                                            if(Integer.parseInt(rsNotas.getString(1))>0)
                                            {
                                                cont++;
                                            }break;}
                                            case "3":{bimestres[2]=rsNotas.getString(1);
                                        
                                         total=total+(Integer.parseInt(rsNotas.getString(1)));
                            
                                            if(Integer.parseInt(rsNotas.getString(1))>0)
                                            {
                                                cont++;
                                            }break;}
                                            case "4":{bimestres[3]=rsNotas.getString(1);
                                        
                                         total=total+(Integer.parseInt(rsNotas.getString(1)));
                            
                                            if(Integer.parseInt(rsNotas.getString(1))>0)
                                            {
                                                cont++;
                                            }
                                            if("1".equals(rsGrados.getString(3))){
                                                cont=3;
                                            }
                                            break;}
                                    }
                                    }
                                    rsNotas.close();
                                    sNotas.close();
                                     if(cont>0)
                                        {
                                            total=total/cont;
                                        }
                        bimestres[4]=""+total;
                        documento.add(agregarMateria(rsMaterias.getString(1),bimestres));
                        
                        }
                        rsMaterias.close();
                        sMaterias.close();
                    
                Paragraph par = new Paragraph();
      par.setAlignment(Paragraph.ALIGN_CENTER);
      par.setFont(FontFactory.getFont("Sans",14, BaseColor.BLACK));
      
      
      par.add("\n\n\nDireccion.");
      documento.add(par);
      documento.newPage();
            }
             sAlumnos.close(); 
             rsAlumnos.close();
      
          }
          sGrados.close();
      rsGrados.close();
      }  
          
      documento.close();
      rsPlan.close();
      sPlan.close();
      
      
      }
      catch (Exception e)
      {
          JOptionPane.showMessageDialog(null, "Error:"+e);
      }
      
      
      
   }
    
    public  void imprimirGrados(String grado) throws FileNotFoundException, DocumentException, BadElementException, IOException
   {
      
      String direccion=System.getProperty("user.dir")+"/Notas Grado "+grado+".pdf"; 
      FileOutputStream archivo = new FileOutputStream(direccion);
      Document documento = new Document(PageSize.LETTER);
      
      PdfWriter.getInstance(documento, archivo);
      documento.addTitle("Calificaciones");
      documento.addAuthor("DeaNiell");
      Statement sNotas,sGrados,sAlumnos,sMaterias;
      ResultSet rsNotas,rsGrados,rsAlumnos,rsMaterias;
      
      Paragraph parrafo = new Paragraph();
      parrafo.setAlignment(Paragraph.ALIGN_LEFT);
      parrafo.setFont(FontFactory.getFont("Sans",12,Font.BOLD, BaseColor.BLACK));
      
      Paragraph parrafo2 = new Paragraph();
      parrafo2.setAlignment(Paragraph.ALIGN_RIGHT);
      parrafo2.setFont(FontFactory.getFont("Sans",12,Font.BOLD, BaseColor.BLACK));
      
      
      try
      {
          
         documento.open(); 
         
          sGrados=con.createStatement();
          String sqlGrados="select idGrado,nombreGrado from grados where idplan="+getIdPlan(jCPlan.getSelectedItem().toString());
          rsGrados=sGrados.executeQuery(sqlGrados);
            
          while(rsGrados.next())
          {
              if(rsGrados.getString(2).equals(grado))
                {
         sAlumnos= con.createStatement();
         String sqlAlumnos="select nombre,apellido,idAlumno from alumnos where idGrado="+rsGrados.getString(1)+" and idplan="+getIdPlan(jCPlan.getSelectedItem().toString());
         rsAlumnos=sAlumnos.executeQuery(sqlAlumnos);
         
            while(rsAlumnos.next())
             
            {
                
                parrafo.removeAll(parrafo);
                parrafo2.removeAll(parrafo2);
                Image imagen = Image.getInstance("EncabezadoCopral.jpg");
                imagen.setAlignment(Element.ALIGN_CENTER);
                documento.add(imagen);
                parrafo2.add("Año: 2018");
                parrafo.add("Grado: "+rsGrados.getString(2)+"\t\t\t\t\t"
                        + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t Plan: "+getPlan(rsGrados.getString(2),getIdPlan(jCPlan.getSelectedItem().toString())+"")+"\n\n");
                parrafo.add("\t\t\t\tNombre de Alumno(a): "+rsAlumnos.getString(1)+" "+rsAlumnos.getString(2)+"\n\n\n");
                documento.add(parrafo2);
                documento.add(parrafo);
                documento.add(tablaEncabezado());
                String sqlMaterias="select nombreMateria,idMateria from materias where idGrado="+rsGrados.getString(1);
                    sMaterias=con.createStatement();
                    rsMaterias=sMaterias.executeQuery(sqlMaterias);
                    String[] bimestres=new String[5];
                    
                        while(rsMaterias.next())
                        {
                            
                            int total=0,cont=0;
                            int i=0;
                            String sqlNotas="select nota,idbimestre from notas where idMateria="+rsMaterias.getString(2)
                                    +" and idAlumno="+rsAlumnos.getString(3);
                                sNotas=con.createStatement();
                                rsNotas=sNotas.executeQuery(sqlNotas);
                            
                                    while(rsNotas.next())
                                    {
                                       switch(rsNotas.getString(2)){
                                        
                                            case "1":{bimestres[0]=rsNotas.getString(1);
                                        
                                         total=total+(Integer.parseInt(rsNotas.getString(1)));
                            
                                            if(Integer.parseInt(rsNotas.getString(1))>0)
                                            {
                                                cont++;
                                            } break;}
                                            case "2":{bimestres[1]=rsNotas.getString(1);
                                        
                                         total=total+(Integer.parseInt(rsNotas.getString(1)));
                            
                                            if(Integer.parseInt(rsNotas.getString(1))>0)
                                            {
                                                cont++;
                                            }break;}
                                            case "3":{bimestres[2]=rsNotas.getString(1);
                                        
                                         total=total+(Integer.parseInt(rsNotas.getString(1)));
                            
                                            if(Integer.parseInt(rsNotas.getString(1))>0)
                                            {
                                                cont++;
                                            }break;}
                                            case "4":{bimestres[3]=rsNotas.getString(1);
                                        
                                         total=total+(Integer.parseInt(rsNotas.getString(1)));
                            
                                            if(Integer.parseInt(rsNotas.getString(1))>0)
                                            {
                                                cont++;
                                            }break;}
                                    }
                                    }
                                    rsNotas.close();
                                    sNotas.close();
                                     if(cont>0)
                                        {
                                            total=total/cont;
                                        }
                        bimestres[4]=""+total;
                        documento.add(agregarMateria(rsMaterias.getString(1),bimestres));
                        
                        }
                        rsMaterias.close();
                        sMaterias.close();
                    
                Paragraph par = new Paragraph();
      par.setAlignment(Paragraph.ALIGN_CENTER);
      par.setFont(FontFactory.getFont("Sans",14, BaseColor.BLACK));
      
      
      par.add("\n\n\nDireccion.");
      documento.add(par);
      documento.newPage();
                 
            }
            sAlumnos.close(); 
             rsAlumnos.close();
                }
             
          
          }
      sGrados.close();
      rsGrados.close();
      
            documento.close();
          
      }
      catch (Exception e)
      {
          JOptionPane.showMessageDialog(null, "Error:"+e);
      }
     
      
      
   }
    
    public  void imprimirAlumno(String grado,String alumno) throws FileNotFoundException, DocumentException, BadElementException, IOException
   {
      
      String direccion=System.getProperty("user.dir")+"/Notas de "+alumno+" de "+grado+".pdf"; 
      FileOutputStream archivo = new FileOutputStream(direccion);
      Document documento = new Document(PageSize.LETTER);
      
      PdfWriter.getInstance(documento, archivo);
      documento.addTitle("Calificaciones");
      documento.addAuthor("DeaNiell");
      Statement sNotas,sGrados,sAlumnos,sMaterias;
      ResultSet rsNotas,rsGrados,rsAlumnos,rsMaterias;
      
      Paragraph parrafo = new Paragraph();
      parrafo.setAlignment(Paragraph.ALIGN_LEFT);
      parrafo.setFont(FontFactory.getFont("Sans",12,Font.BOLD, BaseColor.BLACK));
      
      Paragraph parrafo2 = new Paragraph();
      parrafo2.setAlignment(Paragraph.ALIGN_RIGHT);
      parrafo2.setFont(FontFactory.getFont("Sans",12,Font.BOLD, BaseColor.BLACK));
      
      
      try
      {
          
         documento.open(); 
         
          sGrados=con.createStatement();
          String sqlGrados="select idGrado,nombreGrado from grados";
          rsGrados=sGrados.executeQuery(sqlGrados);
            
          while(rsGrados.next())
          {
              if(rsGrados.getString(2).equals(grado))
                {
         sAlumnos= con.createStatement();
         String sqlAlumnos="select nombre,apellido,idAlumno from alumnos where idGrado="+rsGrados.getString(1);
         rsAlumnos=sAlumnos.executeQuery(sqlAlumnos);
         
            while(rsAlumnos.next())
             
            {
                
               if(alumno.equals(rsAlumnos.getString(1)+" "+rsAlumnos.getString(2)))
                {
                
                parrafo.removeAll(parrafo);
                parrafo2.removeAll(parrafo2);
                Image imagen = Image.getInstance("EncabezadoCopral.jpg");
                imagen.setAlignment(Element.ALIGN_CENTER);
                documento.add(imagen);
                parrafo2.add("Año: 2018");
                parrafo.add("Grado: "+rsGrados.getString(2)+"\t\t\t\t\t"
                        + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t Plan: "+getPlan(rsGrados.getString(2),getIdPlan(jCPlan.getSelectedItem().toString())+"")+"\n\n");
                parrafo.add("\t\t\t\tNombre de Alumno(a): "+rsAlumnos.getString(1)+" "+rsAlumnos.getString(2)+"\n\n\n");
                documento.add(parrafo2);
                documento.add(parrafo);
                documento.add(tablaEncabezado());
                String sqlMaterias="select nombreMateria,idMateria from materias where idGrado="+rsGrados.getString(1);
                    sMaterias=con.createStatement();
                    rsMaterias=sMaterias.executeQuery(sqlMaterias);
                    String[] bimestres=new String[5];
                    
                        while(rsMaterias.next())
                        {
                            
                            int total=0;
                            int cont=0;
                            String sqlNotas="select nota,idbimestre from notas where idMateria="+rsMaterias.getString(2)
                                    +" and idAlumno="+rsAlumnos.getString(3);
                                sNotas=con.createStatement();
                                rsNotas=sNotas.executeQuery(sqlNotas);
                            
                                    while(rsNotas.next())
                                    {
                                        switch(rsNotas.getString(2)){
                                        
                                            case "1":{bimestres[0]=rsNotas.getString(1);
                                        
                                         total=total+(Integer.parseInt(rsNotas.getString(1)));
                            
                                            if(Integer.parseInt(rsNotas.getString(1))>0)
                                            {
                                                cont++;
                                            } break;}
                                            case "2":{bimestres[1]=rsNotas.getString(1);
                                        
                                         total=total+(Integer.parseInt(rsNotas.getString(1)));
                            
                                            if(Integer.parseInt(rsNotas.getString(1))>0)
                                            {
                                                cont++;
                                            }break;}
                                            case "3":{bimestres[2]=rsNotas.getString(1);
                                        
                                         total=total+(Integer.parseInt(rsNotas.getString(1)));
                            
                                            if(Integer.parseInt(rsNotas.getString(1))>0)
                                            {
                                                cont++;
                                            }break;}
                                            case "4":{bimestres[3]=rsNotas.getString(1);
                                        
                                         total=total+(Integer.parseInt(rsNotas.getString(1)));
                            
                                            if(Integer.parseInt(rsNotas.getString(1))>0)
                                            {
                                                cont++;
                                            }break;}
                                    }
                                    }
                                    rsNotas.close();
                                    sNotas.close();
                                     if(cont>0)
                                        {
                                            total=total/cont;
                                        }
                        bimestres[4]=Float.toString(total);
                        documento.add(agregarMateria(rsMaterias.getString(1),bimestres));
                        
                        }
                        rsMaterias.close();
                        sMaterias.close();
                    
                Paragraph par = new Paragraph();
      par.setAlignment(Paragraph.ALIGN_CENTER);
      par.setFont(FontFactory.getFont("Sans",14, BaseColor.BLACK));
      
      
      par.add("\n\n\nDireccion.");
      documento.add(par);
      documento.newPage();
                 
                }
            }
            sAlumnos.close(); 
             rsAlumnos.close();
                }
             
          
          }
      sGrados.close();
      rsGrados.close();
      
            documento.close();
          
      }
      catch (SQLException | IOException | DocumentException | NumberFormatException e)
      {
          JOptionPane.showMessageDialog(null, "Error :"+e);
      }
     
      
      
   }
public void llenarOpcionesImpresion()
{
   jCOpcionImprimir.insertItemAt(ImpTo,0);
   jCOpcionImprimir.insertItemAt(ImpGra,1);
   jCOpcionImprimir.insertItemAt(ImpAlu,2);
   jCOpcionImprimir.setSelectedIndex(0);
}
public int getIdPlan(String plan)
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
    desconectar();
     return idPlan;
     
 }
public String getPlan(String grado,String idPlan)
{
    String plana="";
    
    String sql="select p.nombrePlan,g.nombreGrado from copral.plan p "
            + "inner join grados g on g.idPlan=p.idPlan where p.idPlan="+idPlan;
    try
    {
    s=con.createStatement();
    rs=s.executeQuery(sql);
    
    while(rs.next())
    {
        if(rs.getString(2).equals(grado))
        {
        plana=rs.getObject(1).toString();
        }
        
    }
    
    desconectar();
    }
    catch(SQLException | NumberFormatException e)
    {
      JOptionPane.showMessageDialog(null,"No se puede saber cual es el plan\n"+e);
    }
    
    
    return plana;
    
}
public void llenarPlan()
{
   if(plan==false)
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
    catch(SQLException e)
    {
      JOptionPane.showMessageDialog(null,"Imposible llenar el combo Grado");
    }
    plan=true;
   }
}



public void llenarGrado() 
{
  
    
         
    
    if(grado==false)
    {
    String sql="select nombreGrado from copral.grados where idPlan="+getIdPlan(jCPlan.getSelectedItem().toString())+" order by nombreGrado desc";
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
    grado=true;
    }

}
public int getGrado(String grado)
{
    int codGrado=0;
   
    
    String sql="select idGrado from copral.grados where nombreGrado='"+grado+"' and idPlan="
            +getIdPlan(jCPlan.getSelectedItem().toString())+" ;";
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
public void llenarAlumnos()
{
   
    try
    {
        String sql="select nombre,apellido from copral.alumnos where idGrado="+getGrado(jCGrado.getSelectedItem().toString())+" order by apellido desc";
    
    s=con.createStatement();
    rs=s.executeQuery(sql);
    
    while(rs.next())
    {
        jCAlumnos.insertItemAt(rs.getString(1)+" "+rs.getString(2),0);
    }
   
     desconectar();   

    }
    catch(Exception  e)
    {
      JOptionPane.showMessageDialog(null,"Imposible llenar el combo Alumnos\n"+e);
    }
    
         
}

public void queImprimir()
{
    try{
        if(jCOpcionImprimir.getSelectedItem().toString().equals(ImpTo))
        {
            JOptionPane.showMessageDialog(null,"Se imprimiran todas las notas de todos los alumnos de todos los grados.");
            imprimirTodos(cantidadGrados());
            JOptionPane.showMessageDialog(null,"Se ha generado un Archivo PDF");
        }
        else
        {
            if(jCOpcionImprimir.getSelectedItem().toString().equals(ImpGra))
            {
                JOptionPane.showMessageDialog(null,"Se Imprimiran las notas del grado: "
                                +jCGrado.getSelectedItem().toString());
                imprimirGrados(jCGrado.getSelectedItem().toString());
                JOptionPane.showMessageDialog(null,"Se ha generado un Archivo PDF");
            }
            else
            {
                if(jCOpcionImprimir.getSelectedItem().toString().equals(ImpAlu))
                {
                    JOptionPane.showMessageDialog(null,"Se Imprimiran las notas del Alumno: "
                                +jCAlumnos.getSelectedItem().toString()
                            +"\nPerteneciente al grado: "+jCGrado.getSelectedItem().toString());
                 
                    imprimirAlumno(jCGrado.getSelectedItem().toString(),jCAlumnos.getSelectedItem().toString());
                    JOptionPane.showMessageDialog(null,"Se ha generado un Archivo PDF");
                }
            }
        }    
        }
        catch(HeadlessException | DocumentException | IOException e)
        {   
        }
}

public void llenarOpciones(String deDonde)
{
    try
    {
    switch(deDonde)
    {
        case "Plan":
        {
            if(jCOpcionImprimir.getSelectedItem().toString().equals(ImpTo))
            {
                jCPlan.removeAllItems();
               jCGrado.removeAllItems();
               jCAlumnos.removeAllItems();
                
            }
            else
            {
               llenarPlan();
                        if(jCOpcionImprimir.getSelectedItem().toString().equals(ImpAlu))
                        {
                            if(!jCGrado.getSelectedItem().toString().equals(""))
                            {
                            llenarAlumnos();
                            }
                        }
               
            }
            break;
        }
        case "Grado":
        {
            if((ImpTo).equals(jCOpcionImprimir.getSelectedItem().toString()))
            {
                jCPlan.removeAllItems();
               jCGrado.removeAllItems();
               jCAlumnos.removeAllItems();
                
            }else
                {
                    if(jCPlan.getSelectedItem().toString().equals(""))
                {
                llenarPlan();
                jCGrado.removeAllItems();
                jCAlumnos.removeAllItems();
                
                }else
                {
                     llenarGrado();
               
                }
               
               
                }
            break;
        }
        case "Alumnos":
        {
            if(jCOpcionImprimir.getSelectedItem().toString().equals(ImpTo))
            {
                jCPlan.removeAllItems();
               jCGrado.removeAllItems();
               jCAlumnos.removeAllItems();
                
            }else
                {
                    if(jCPlan.getSelectedItem().toString().equals(""))
                {
                    
                    jCGrado.removeAllItems();
                    jCAlumnos.removeAllItems();
                
                }else
                {
               if(jCGrado.getSelectedItem().toString().equals(""))
                    {
                    
                    jCAlumnos.removeAllItems();
                        
                    }else
                    {
                    
                        if(jCOpcionImprimir.getSelectedItem().toString().equals(ImpAlu))
                        {
                            llenarAlumnos();
                        }
                    }
               
                }
               
               
                }
            break;
        }
    }
    }
    catch(Exception e)
    {
       
              
                
    }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        jCOpcionImprimir = new javax.swing.JComboBox();
        lblImpresion = new javax.swing.JLabel();
        cmdImprimir = new javax.swing.JButton();
        cmdCancelar = new javax.swing.JButton();
        jCGrado = new javax.swing.JComboBox();
        jCAlumnos = new javax.swing.JComboBox();
        lblGrado = new javax.swing.JLabel();
        lblAlumno = new javax.swing.JLabel();
        jCPlan = new javax.swing.JComboBox();
        lblPlan = new javax.swing.JLabel();
        Creador = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnArchivo = new javax.swing.JMenu();
        mnItmIngresoNotas = new javax.swing.JMenuItem();
        mnItmInicio = new javax.swing.JMenuItem();
        mnItmSalir = new javax.swing.JMenuItem();
        mnAyuda = new javax.swing.JMenu();
        jMnItmAyuda = new javax.swing.JMenuItem();
        jMnItmCreador = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitulo.setFont(new java.awt.Font("Cooper Black", 0, 24)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Area para Imprimir");

        jCOpcionImprimir.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jCOpcionImprimir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCOpcionImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCOpcionImprimirActionPerformed(evt);
            }
        });

        lblImpresion.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblImpresion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImpresion.setText("Opciones de Impresión");

        cmdImprimir.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        cmdImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Calificaciones/Iconos/image/printer.png"))); // NOI18N
        cmdImprimir.setText("Imprimir");
        cmdImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdImprimirActionPerformed(evt);
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
        jCGrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCGradoActionPerformed(evt);
            }
        });

        jCAlumnos.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jCAlumnos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblGrado.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblGrado.setText("Grados");

        lblAlumno.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblAlumno.setText("Alumnos");

        jCPlan.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jCPlan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCPlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCPlanActionPerformed(evt);
            }
        });

        lblPlan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
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

        mnItmInicio.setFont(new java.awt.Font("Bodoni MT", 1, 14)); // NOI18N
        mnItmInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Calificaciones/Iconos/image/home.png"))); // NOI18N
        mnItmInicio.setText("Inicio");
        mnItmInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnItmInicioActionPerformed(evt);
            }
        });
        mnArchivo.add(mnItmInicio);

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
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(lblImpresion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Creador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cmdImprimir)
                .addGap(62, 62, 62)
                .addComponent(cmdCancelar)
                .addGap(192, 192, 192))
            .addGroup(layout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblPlan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblAlumno, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblGrado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCGrado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCAlumnos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCPlan, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(110, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCOpcionImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(161, 161, 161))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblImpresion, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 187, Short.MAX_VALUE)
                        .addComponent(Creador))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCOpcionImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCPlan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPlan))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCGrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGrado))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCAlumnos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAlumno))
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmdCancelar)
                            .addComponent(cmdImprimir))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCancelarActionPerformed

        Coneccion.inicioSalida(this);
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdCancelarActionPerformed

    private void cmdImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdImprimirActionPerformed
        queImprimir();
// TODO add your handling code here:
    }//GEN-LAST:event_cmdImprimirActionPerformed

    private void jCOpcionImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCOpcionImprimirActionPerformed
        llenarOpciones("Plan");
       
        // TODO add your handling code here:
    }//GEN-LAST:event_jCOpcionImprimirActionPerformed

    private void jCGradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCGradoActionPerformed
        llenarOpciones("Alumnos");
      
        // TODO add your handling code here:
    }//GEN-LAST:event_jCGradoActionPerformed

    private void jCPlanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCPlanActionPerformed
        llenarOpciones("Grado");
        
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

        IngresoCalificaciones enviar= new IngresoCalificaciones();

        enviar.setVisible(true);
        this.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_mnItmIngresoNotasActionPerformed

    private void mnItmInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnItmInicioActionPerformed

        Impresion enviar=new Impresion();
        enviar.setVisible(true);
        this.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_mnItmInicioActionPerformed

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
            java.util.logging.Logger.getLogger(Impresion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Impresion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Impresion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Impresion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Impresion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Creador;
    private javax.swing.JButton cmdCancelar;
    private javax.swing.JButton cmdImprimir;
    private javax.swing.JComboBox jCAlumnos;
    private javax.swing.JComboBox jCGrado;
    private javax.swing.JComboBox jCOpcionImprimir;
    private javax.swing.JComboBox jCPlan;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMnItmAyuda;
    private javax.swing.JMenuItem jMnItmCreador;
    private javax.swing.JLabel lblAlumno;
    private javax.swing.JLabel lblGrado;
    private javax.swing.JLabel lblImpresion;
    private javax.swing.JLabel lblPlan;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JMenu mnArchivo;
    private javax.swing.JMenu mnAyuda;
    private javax.swing.JMenuItem mnItmIngresoNotas;
    private javax.swing.JMenuItem mnItmInicio;
    private javax.swing.JMenuItem mnItmSalir;
    // End of variables declaration//GEN-END:variables
}
