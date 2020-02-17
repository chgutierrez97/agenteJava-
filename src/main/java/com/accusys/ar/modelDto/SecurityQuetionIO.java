/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accusys.ar.modelDto;

import java.io.Serializable;


public class SecurityQuetionIO implements Serializable {

    private static final long serialVersionUID = 1L;
   
    private int id;
    
    private String preguta;
    
    private String respuesta;
    
    private UsuarioIO usuario;

    public SecurityQuetionIO() {
    }

    public SecurityQuetionIO(int id) {
        this.id = id;
    }

    public SecurityQuetionIO(int id, String preguta, String respuesta, UsuarioIO usuario) {
        this.id = id;
        this.preguta = preguta;
        this.respuesta = respuesta;
        this.usuario = usuario;
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

    public UsuarioIO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioIO usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
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
        final SecurityQuetionIO other = (SecurityQuetionIO) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SecurityQuetionIO{" + "id=" + id + ", preguta=" + preguta + ", respuesta=" + respuesta + ", usuario=" + usuario + '}';
    }

}
