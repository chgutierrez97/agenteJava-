/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accusys.ar.modelDto;

import java.util.Collection;
import java.util.Date;

/**
 *
 * @author MGIAccusys
 */
public class Usuario {
    
    private Integer id;
    private String usuario;
    private String clave;
    private Date fechaCarga;

    private Collection<Transaccion> transaccionCollection;
  
    private Collection<SecurityQuetion> securityQuetionCollection;

    private Persona persona;

    private Roles roles;

    private Status status;

    
    
    public Usuario() {
    
    }
    
    public Usuario(Integer id) {
        this.id = id;
    }

    
    public Usuario(Integer id, String usuario, String clave, Date fechaCarga, Persona persona, Roles roles, Status status) {
        this.id = id;
        this.usuario = usuario;
        this.clave = clave;
        this.fechaCarga = fechaCarga;
        this.persona = persona;
        this.roles = roles;
        this.status = status;
    }

    
    public Usuario(int id, String usuario, String clave, Collection<Transaccion> transaccionCollection, Collection<SecurityQuetion> securityQuetionCollection, Persona persona, Roles roles, Status status) {
        this.id = id;
        this.usuario = usuario;
        this.clave = clave;
        this.transaccionCollection = transaccionCollection;
        this.securityQuetionCollection = securityQuetionCollection;
        this.persona = persona;
        this.roles = roles;
        this.status = status;
    }    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public Collection<Transaccion> getTransaccionCollection() {
        return transaccionCollection;
    }

    public void setTransaccionCollection(Collection<Transaccion> transaccionCollection) {
        this.transaccionCollection = transaccionCollection;
    }

    public Collection<SecurityQuetion> getSecurityQuetionCollection() {
        return securityQuetionCollection;
    }

    public void setSecurityQuetionCollection(Collection<SecurityQuetion> securityQuetionCollection) {
        this.securityQuetionCollection = securityQuetionCollection;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    
    
   @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }



    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + '}';
    }
    
    
    
}
