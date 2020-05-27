package com.accusys.ar.modelDto;


import java.io.Serializable;
import java.util.List;

public class TransaccionExport implements Serializable {

    private static final long serialVersionUID = 1L;

    
    private TransaccionIO transaccion;
    private List<PantallaDto> listaPantalla;
    private List<PantallaDto> listaPantallaCierre;

    public TransaccionExport() {
    }

    public TransaccionIO getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(TransaccionIO transaccion) {
        this.transaccion = transaccion;
    }


    public List<PantallaDto> getListaPantalla() {
        return listaPantalla;
    }

    public void setListaPantalla(List<PantallaDto> listaPantalla) {
        this.listaPantalla = listaPantalla;
    }

    public List<PantallaDto> getListaPantallaCierre() {
        return listaPantallaCierre;
    }

    public void setListaPantallaCierre(List<PantallaDto> listaPantallaCierre) {
        this.listaPantallaCierre = listaPantallaCierre;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + transaccion.getId();
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
        
        return true;
    }

    @Override
    public String toString() {
        return "TransaccionExport{" + "transaccion=" + transaccion + ", listaPantalla=" + listaPantalla + ", listaPantallaCierre=" + listaPantallaCierre + '}';
    }
}

    
