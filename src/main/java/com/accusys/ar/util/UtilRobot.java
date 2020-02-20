/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accusys.ar.util;

import com.accusys.ar.modelDto.ExpresionesRegularesIO;
import com.accusys.ar.modelDto.ListaMacroIO;
import com.accusys.ar.modelDto.Persona;
import com.accusys.ar.modelDto.Usuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author MGIAccusys
 */
@Service
@PropertySource("classpath:application.properties")
public class UtilRobot {
     @Value("${paht.url.service}")
    String urlpaht;

    public boolean ifValidUserExist(Usuario usuario) {
        RestTemplate restTemplate = new RestTemplate();
        String usuarioFindUrl = urlpaht+"findUsuarioByLogin";
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(usuarioFindUrl)
                .queryParam("login", usuario.getUsuario());  
        String yu = builder.toUriString();
        System.out.println(" builder  Yu - - - > " + yu + "| en >> ifValidUserExist");
        Usuario result = restTemplate.getForObject(builder.toUriString(), Usuario.class);
        if (result.getUsuario() != null) {
            System.out.println("Ya existe el User Name en ifValidUserExist" + result.getUsuario());
            return true;
        } else {
            return false;
        }
    }

    public boolean ifValidPersonExist(Persona persona) {
        RestTemplate restTemplate = new RestTemplate();
        String personaFindUrl = urlpaht+"findPersonaByDNI";
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(personaFindUrl)
                // Add query parameter
                .queryParam("dni", persona.getDni());

        String yu = builder.toUriString();
        System.out.println(" builder  Yu - - - > " + yu + "|UtilRobot - ifValidPersonExist");
        Persona result = restTemplate.getForObject(builder.toUriString(), Persona.class);
        if (result.getDni() == 0) {
            return false;
        } else {
            System.out.println("Ya existe la persona con el DNI ingresado en  % % %  ifValidPersonExist" + result.getDni());
            return true;
        }
    }

    public ExpresionesRegularesIO getExpresionById(Integer idExpresion) {
        final String url = urlpaht+"findExpresionById";
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(url)
                .queryParam("id", idExpresion);
        ExpresionesRegularesIO result = restTemplate.getForObject(builder.toUriString(), ExpresionesRegularesIO.class);

        return result;
    }

    public List<ExpresionesRegularesIO> getExpresionAll() {
        final String url = urlpaht+"findAllExpresion";
        RestTemplate restTemplate = new RestTemplate();
        ListaMacroIO result = restTemplate.getForObject(url, ListaMacroIO.class);
        //System.out.println(result);
        return result.getExpresionesList();
    }

    public boolean comparadorDeCaracteres(String sTexto, String sTextoBuscado) {
        sTexto = sTexto.toLowerCase();
        sTextoBuscado = sTextoBuscado.toLowerCase();
        boolean flag = false;
        int contador = 0;
        if (sTexto.indexOf(sTextoBuscado) > -1) {
            flag = true;
        }
        if (sTexto.contains("" + sTextoBuscado)) {
            flag = true;
        }
        return flag;
    }
//    

}
