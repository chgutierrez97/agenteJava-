/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accusys.ar.modelDto;


import java.util.List;



public class ListaMacroIO1 {
    
    private List<TransaccionIO> TransaccionList;
    private List<InputIO> InputList;
    private List<PantallaIO> PantallasList;
    private List<PersonaIO> PersonaList;
    private List<RolesIO> RolesList;
    private List<SecurityQuetionIO> SecurityQuetionIOList;
    private List<StatusIO> StatusList;
    private List<TextoPantallaIO> TextoPantallaList;
    private List<UsuarioIO> UsuarioList;
    private List<ExpresionesRegularesIO> ExpresionesList;
    private List<CancelacionesDto> CancelacionesList;


    public List<TransaccionIO> getTransaccionList() {
        return TransaccionList;
    }

    public void setTransaccionList(List<TransaccionIO> TransaccionList) {
        this.TransaccionList = TransaccionList;
    }

    public List<InputIO> getInputList() {
        return InputList;
    }

    public void setInputList(List<InputIO> InputList) {
        this.InputList = InputList;
    }

    public List<PantallaIO> getPantallasList() {
        return PantallasList;
    }

    public void setPantallasList(List<PantallaIO> PantallasList) {
        this.PantallasList = PantallasList;
    }

    public List<PersonaIO> getPersonaList() {
        return PersonaList;
    }

    public void setPersonaList(List<PersonaIO> PersonaList) {
        this.PersonaList = PersonaList;
    }

    public List<RolesIO> getRolesList() {
        return RolesList;
    }

    public void setRolesList(List<RolesIO> RolesList) {
        this.RolesList = RolesList;
    }

    public List<SecurityQuetionIO> getSecurityQuetionIOList() {
        return SecurityQuetionIOList;
    }

    public void setSecurityQuetionIOList(List<SecurityQuetionIO> SecurityQuetionIOList) {
        this.SecurityQuetionIOList = SecurityQuetionIOList;
    }

    public List<StatusIO> getStatusList() {
        return StatusList;
    }

    public void setStatusList(List<StatusIO> StatusList) {
        this.StatusList = StatusList;
    }

    public List<TextoPantallaIO> getTextoPantallaList() {
        return TextoPantallaList;
    }

    public void setTextoPantallaList(List<TextoPantallaIO> TextoPantallaList) {
        this.TextoPantallaList = TextoPantallaList;
    }

    public List<UsuarioIO> getUsuarioList() {
        return UsuarioList;
    }

    public void setUsuarioList(List<UsuarioIO> UsuarioList) {
        this.UsuarioList = UsuarioList;
    }

    
    public List<ExpresionesRegularesIO> getExpresionesList() {
        return ExpresionesList;
    }

    public void setExpresionesList(List<ExpresionesRegularesIO> ExpresionesList) {
        this.ExpresionesList = ExpresionesList;
    }

    public List<CancelacionesDto> getCancelacionesList() {
        return CancelacionesList;
    }

    public void setCancelacionesList(List<CancelacionesDto> CancelacionesList) {
        this.CancelacionesList = CancelacionesList;
    }
   
}
