
package com.accusys.ar.modelDto;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CancelacionesDto implements Serializable {

    private static final long serialVersionUID = 1L;
  
    private Integer id;
    private String proceso;
    private Integer flag;
    private Date fecha;
    private String opion;   
    private String alterna;

    
    public CancelacionesDto() {
    }

    public CancelacionesDto(Integer id) {
        this.id = id;
    }

    public CancelacionesDto(Integer id, String proceso, Integer flag, Date fecha) {
        this.id = id;
        this.proceso = proceso;
        this.flag = flag;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getFechaString() {
        String fechaString = new String(""); 
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY"); 
        fechaString = format.format(fecha);
        return fechaString;
    }
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getOpion() {
        return opion;
    }

    public void setOpion(String opion) {
        this.opion = opion;
    }

    public String getAlterna() {
        return alterna;
    }

    public void setAlterna(String alterna) {
        this.alterna = alterna;
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
        if (!(object instanceof CancelacionesDto)) {
            return false;
        }
        CancelacionesDto other = (CancelacionesDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CancelacionesDto{" + "id=" + id + ", proceso=" + proceso + ", flag=" + flag + ", fecha=" + fecha + ", opion=" + opion + ", alterna=" + alterna + '}';
    }


    
}
