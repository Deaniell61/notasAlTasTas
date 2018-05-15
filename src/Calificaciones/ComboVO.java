/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calificaciones;

/**
 *
 * @author leinad
 */
public class ComboVO {
    private String id;
    private String text;
    
    public ComboVO(){}
    
    public ComboVO(String id, String nombre){
        this.id = id;
        this.text = nombre;
    }

    public String getIdDepartamento() {
        return id;
    }

    public void setIdDepartamento(String idDepartamento) {
        this.id = idDepartamento;
    }

    public String getDepartamento() {
        return text;
    }

    public void setDepartamento(String departamento) {
        this.text = departamento;
    }
    
    public String toString(){
        return this.text;
    }
}
