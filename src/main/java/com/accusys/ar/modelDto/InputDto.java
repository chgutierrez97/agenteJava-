/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accusys.ar.modelDto;


public class InputDto {
    private String type;
    private String id;
    private Integer idInp;
    private String name;
    private String value;
    private String label;
    private boolean required;

    public InputDto() {
    }

    public InputDto(String type, String id, String name, String value, String label, boolean required) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.value = value;
        this.label = label;
        this.required = required;
    }
    

    public boolean getRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIdInp() {
        return idInp;
    }

    public void setIdInp(Integer idInp) {
        this.idInp = idInp;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "InputDto{" + "type=" + type + ", id=" + id + ", name=" + name + ", value=" + value + ", label=" + label + ", required=" + required + '}';
    }
}
