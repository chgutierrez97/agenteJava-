/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accusys.ar.modelDto;

import java.io.Serializable;
import java.util.Objects;


public class TextoPantallaIO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String texto;
  
 

    public TextoPantallaIO() {
    }

    public TextoPantallaIO(String texto) {
        this.texto = texto;
    }

    public TextoPantallaIO(Integer id, String texto) {
        this.id = id;
        this.texto = texto;
    }

    


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }



    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.id);
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
        final TextoPantallaIO other = (TextoPantallaIO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TextoPantalla{" + "id=" + id + ", texto=" + texto + '}';
    }
    
}
