/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accusys.ar.modelDto;

import java.util.Date;

/**
 *
 * @author MGIAccusys
 */
public class Persona {
    

    private Integer id;
    private String nombre;
    private String apellido;
    private int dni;
    private Date fechaCarga;
  
    public Persona() {
        
    }
    
    

    public Persona(Integer id, String nombre, String apellido, int dni, Date fechaCarga) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaCarga = fechaCarga;
    }

    public Persona(String nombre, String apellido, int dni, Date fechaCarga) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Persona [id=");
        builder.append(id);
        builder.append(", nombre=");
        builder.append(nombre);
        builder.append(", apellido=");
        builder.append(apellido);
        builder.append(", dni=");
        builder.append(dni);
        builder.append(", fechaCarga=");
        builder.append(fechaCarga);
        builder.append("]");
        return builder.toString();
    }
    
}
