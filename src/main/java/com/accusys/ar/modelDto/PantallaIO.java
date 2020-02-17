/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accusys.ar.modelDto;



import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;


public class PantallaIO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
   
    private int pantallaNumero;
    
    private boolean pantallaActive;
   
    private boolean pantallaActivekey;
    
    private String pantallaAccionTecla;
    
    private String pantallaAction;
   
    private String pantallaScrips;
    
    private Collection<InputIO> inputCollection;   

    private Collection<TextoPantallaIO> textoPantallaCollection;
    
    private Integer idTransaccion;
    
    
    


    public PantallaIO() {
    }

    public PantallaIO(Integer id) {
        this.id = id;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public int getPantallaNumero() {
        return pantallaNumero;
    }

    public void setPantallaNumero(int pantallaNumero) {
        this.pantallaNumero = pantallaNumero;
    }

    public boolean getPantallaActive() {
        return pantallaActive;
    }

    public void setPantallaActive(boolean pantallaActive) {
        this.pantallaActive = pantallaActive;
    }

    public boolean getPantallaActivekey() {
        return pantallaActivekey;
    }

    public void setPantallaActivekey(boolean pantallaActivekey) {
        this.pantallaActivekey = pantallaActivekey;
    }

    public String getPantallaAccionTecla() {
        return pantallaAccionTecla;
    }

    public void setPantallaAccionTecla(String pantallaAccionTecla) {
        this.pantallaAccionTecla = pantallaAccionTecla;
    }

    public String getPantallaAction() {
        return pantallaAction;
    }

    public void setPantallaAction(String pantallaAction) {
        this.pantallaAction = pantallaAction;
    }

    public String getPantallaScrips() {
        return pantallaScrips;
    }

    public void setPantallaScrips(String pantallaScrips) {
        this.pantallaScrips = pantallaScrips;
    }

    public Collection<InputIO> getInputCollection() {
        return inputCollection;
    }

    public void setInputCollection(Collection<InputIO> inputCollection) {
        this.inputCollection = inputCollection;
    }

    public Collection<TextoPantallaIO> getTextoPantallaCollection() {
        return textoPantallaCollection;
    }

    public void setTextoPantallaCollection(Collection<TextoPantallaIO> textoPantallaCollection) {
        this.textoPantallaCollection = textoPantallaCollection;
    }

    public Integer getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Integer idTransaccion) {
        this.idTransaccion = idTransaccion;
    }
    
    
    


    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final PantallaIO other = (PantallaIO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pantalla{" + "id=" + id + '}';
    }

}
