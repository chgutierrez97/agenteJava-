/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accusys.ar.modelDto;

import java.io.Serializable;
import java.util.Objects;


public class InputIO implements Serializable {

    private static final long serialVersionUID = 1L;
   
    private Integer id;
   
    private String inputType;

    private String inputId;

    private String inputName;

    private String inputLabel;

    private boolean inputRequired;

    private String inputValue;
   

    public InputIO() {
    }

    public InputIO(Integer id) {
        this.id = id;
    }


    public InputIO(Integer id, String inputType, String inputId, String inputName, String inputLabel, boolean inputRequired) {
        this.id = id;
        this.inputType = inputType;
        this.inputId = inputId;
        this.inputName = inputName;
        this.inputLabel = inputLabel;
        this.inputRequired = inputRequired;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getInputId() {
        return inputId;
    }

    public void setInputId(String inputId) {
        this.inputId = inputId;
    }

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public String getInputLabel() {
        return inputLabel;
    }

    public void setInputLabel(String inputLabel) {
        this.inputLabel = inputLabel;
    }

    public boolean getInputRequired() {
        return inputRequired;
    }

    public void setInputRequired(boolean inputRequired) {
        this.inputRequired = inputRequired;
    }

    public String getInputValue() {
        return inputValue;
    }

    public void setInputValue(String inputValue) {
        this.inputValue = inputValue;
    }



    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.id);
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
        final InputIO other = (InputIO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Input{" + "id=" + id + '}';
    }


    
}
