package com.accusys.ar.modelDto;

import java.util.List;

public class PantallaDto {

    private Long id;
    private Integer pantallaNumero;
    private boolean active;
    private boolean activeKey;
    private List<InputDto> inputs;
    private List<String> textoPantalla;
    private List<AccionKeyboarDto> listAcciones; 
    private String action;
    private String scrips;
    private String waccionar;
    private  Integer idTransaccion;

    public PantallaDto() {
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPantallaNumero() {
        return pantallaNumero;
    }

    public void setPantallaNumero(Integer pantallaNumero) {
        this.pantallaNumero = pantallaNumero;
    }

    public List<InputDto> getInputs() {
        return inputs;
    }

    public void setInputs(List<InputDto> inputs) {
        this.inputs = inputs;
    }

    public List<String> getTextoPantalla() {
        return textoPantalla;
    }

    public void setTextoPantalla(List<String> textoPantalla) {
        this.textoPantalla = textoPantalla;
    }

   

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<AccionKeyboarDto> getListAcciones() {
        return listAcciones;
    }

    public void setListAcciones(List<AccionKeyboarDto> listAcciones) {
        this.listAcciones = listAcciones;
    }

    public boolean isActiveKey() {
        return activeKey;
    }

    public void setActiveKey(boolean activeKey) {
        this.activeKey = activeKey;
    }

    public String getScrips() {
        return scrips;
    }

    public void setScrips(String scrips) {
        this.scrips = scrips;
    }

    public String getWaccionar() {
        return waccionar;
    }

    public void setWaccionar(String waccionar) {
        this.waccionar = waccionar;
    }

    public Integer getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Integer idTransaccion) {
        this.idTransaccion = idTransaccion;
    }
    
//    @Override
//    public String toString() {
//        return "PantallaDto{" + "id=" + id + ", pantallaNumero=" + pantallaNumero + ", active=" + active + ", activeKey=" + activeKey + ", inputs=" + inputs + ", textoPantalla=" + textoPantalla + ", listAcciones=" + listAcciones + ", action=" + action + ", scrips=" + scrips + ", waccionar=" + waccionar + '}';
//    }

    @Override
    public String toString() {
        return "PantallaDto{" + "id=" + id + ", pantallaNumero=" + pantallaNumero + ", active=" + active + ", activeKey=" + activeKey + ", inputs=" + inputs + ", textoPantalla=" + textoPantalla + ", listAcciones=" + listAcciones + ", action=" + action + ", scrips=" + scrips + ", waccionar=" + waccionar + ", idTransaccion=" + idTransaccion + '}';
    }
    
}
