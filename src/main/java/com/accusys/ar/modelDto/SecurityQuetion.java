/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accusys.ar.modelDto;

/**
 *
 * @author MGIAccusys
 */
public class SecurityQuetion {
   
    
    private int id;

    private String preguta;

    private String respuesta;
    
    private Usuario usuario;

    private int usuarioPersonaId;

    private int usuarioRolesId;

    private int usuarioStatusId;

    public SecurityQuetion() {
    }

    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPreguta() {
        return preguta;
    }

    public void setPreguta(String preguta) {
        this.preguta = preguta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public int getUsuarioPersonaId() {
        return usuarioPersonaId;
    }

    public void setUsuarioPersonaId(int usuarioPersonaId) {
        this.usuarioPersonaId = usuarioPersonaId;
    }

    public int getUsuarioRolesId() {
        return usuarioRolesId;
    }

    public void setUsuarioRolesId(int usuarioRolesId) {
        this.usuarioRolesId = usuarioRolesId;
    }

    public int getUsuarioStatusId() {
        return usuarioStatusId;
    }

    public void setUsuarioStatusId(int usuarioStatusId) {
        this.usuarioStatusId = usuarioStatusId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


    @Override
    public int hashCode() {
        int hash = 7;
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
        final SecurityQuetion other = (SecurityQuetion) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SecurityQuetion{" + "id=" + id + ", preguta=" + preguta + ", respuesta=" + respuesta + ", usuarioPersonaId=" + usuarioPersonaId + ", usuarioRolesId=" + usuarioRolesId + ", usuarioStatusId=" + usuarioStatusId + ", usuario=" + usuario + '}';
    }
    
    
    
    
}
