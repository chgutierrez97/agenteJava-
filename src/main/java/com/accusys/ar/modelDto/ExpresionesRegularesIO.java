
package com.accusys.ar.modelDto;

import java.io.Serializable;




public class ExpresionesRegularesIO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String codError;
    private String mensajeError;

    public ExpresionesRegularesIO() {
    }

    public ExpresionesRegularesIO(Integer id) {
        this.id = id;
    }

    public ExpresionesRegularesIO(Integer id, String codError, String mensajeError) {
        this.id = id;
        this.codError = codError;
        this.mensajeError = mensajeError;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodError() {
        return codError;
    }

    public void setCodError(String codError) {
        this.codError = codError;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
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
        if (!(object instanceof ExpresionesRegularesIO)) {
            return false;
        }
        ExpresionesRegularesIO other = (ExpresionesRegularesIO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ExpresionesRegularesIO{" + "id=" + id + ", codError=" + codError + ", mensajeError=" + mensajeError + '}';
    }


    
}
