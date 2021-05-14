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
public class Export {
  
    
private static final long serialVersionUID = 1L;
  
    private Integer id;

    private String descripcion;
    
    private Boolean flag;
    
    private String accion;
  
   
    public Export() {
    }

    public Export(Integer id) {
        this.id = id;
    }

    public Export(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Export(Integer id, String descripcion, Boolean flag) {
        this.id = id;
        this.descripcion = descripcion;
        this.flag = flag;
    }

    public Export(Integer id, String descripcion, Boolean flag, String accion) {
        this.id = id;
        this.descripcion = descripcion;
        this.flag = flag;
        this.accion = accion;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Export)) {
            return false;
        }
        Export other = (Export) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

//    @Override
//    public String toString() {
//        return "Export{" + "id=" + id + ", descripcion=" + descripcion + ", flag=" + flag + '}';
//    }
//  

    @Override
    public String toString() {
        return "Export{" + "id=" + id + ", descripcion=" + descripcion + ", flag=" + flag + ", accion=" + accion + '}';
    }
    
}
