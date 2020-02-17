/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accusys.ar.modelDto;

import java.io.Serializable;
import java.util.Date;



public class PersonaIO implements Serializable {

    
    private Integer id;

    private String nombre;
    
    private String apellido;

    private int dni;
  
    private Date fechaCarga;
    


    public PersonaIO() {
    }

    public PersonaIO(Integer id) {
        this.id = id;
    }

    public PersonaIO(Integer id, String nombre, String apellido, int dni, Date fechaCarga) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaCarga = fechaCarga;
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

//    @XmlTransient
//    public Collection<Usuario> getUsuarioCollection() {
//        return usuarioCollection;
//    }
//
//    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
//        this.usuarioCollection = usuarioCollection;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonaIO)) {
            return false;
        }
        PersonaIO other = (PersonaIO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ar.ser.model.Persona[ id=" + id + " ]";
    }
    
}
