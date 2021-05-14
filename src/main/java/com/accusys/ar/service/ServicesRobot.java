package com.accusys.ar.service;

import com.accusys.ar.modelDto.CancelacionesDto;
import com.accusys.ar.modelDto.ListaMacroIO;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@PropertySource("classpath:application.properties")
public class ServicesRobot {

    @Value("${paht.url.service}")
    String urlpaht;

    public List<CancelacionesDto> getCancelacionesAll() {
        final String url = urlpaht + "findAllCancelacion";
        RestTemplate restTemplate = new RestTemplate();
        ListaMacroIO result = restTemplate.getForObject(url, ListaMacroIO.class);
        return result.getCancelacionesList();
    }

    public Boolean actualizarCancelacionById(Integer id, String valor, Integer flag) {
        final String url = urlpaht + "findAllCancelacion";
        final String url2 = urlpaht + "saveCancelacion";
        CancelacionesDto cancelacion = new CancelacionesDto();
        RestTemplate restTemplate = new RestTemplate();
        ListaMacroIO result = restTemplate.getForObject(url, ListaMacroIO.class);
        for (CancelacionesDto cancelacionesDto : result.getCancelacionesList()) {
            if (cancelacionesDto.getId() == id) {
                cancelacion = cancelacionesDto;
                break;
            }
        }
        if ((!valor.equals("")) && valor != null) {
            cancelacion.setOpion(valor);
        }
        if (flag != null) {
            cancelacion.setFlag(flag);
        }

        CancelacionesDto cancelacionUpdate = restTemplate.postForObject(url2, cancelacion, CancelacionesDto.class);
        return (cancelacionUpdate.getOpion().equals(cancelacion.getOpion())) ? true : false;
    }

    public Boolean crearCancelacion(CancelacionesDto cancelacion) {

        final String url = urlpaht + "saveCancelacion";
        RestTemplate restTemplate2 = new RestTemplate();
        CancelacionesDto cancelacionUpdate = restTemplate2.postForObject(url, cancelacion, CancelacionesDto.class);

        return (cancelacionUpdate.getOpion().equals(cancelacion.getOpion())) ? true : false;
    }

    public CancelacionesDto crearCancelacion2(CancelacionesDto cancelacion) {

        final String url = urlpaht + "saveCancelacion";
        RestTemplate restTemplate = new RestTemplate();
        CancelacionesDto cancelacionUpdate = restTemplate.postForObject(url, cancelacion, CancelacionesDto.class);

        return cancelacionUpdate;
    }

    public CancelacionesDto crearCancelacion3(CancelacionesDto cancelacion) {

        final String url = urlpaht + "saveCancelacionGet";
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(url)
                .queryParam("flag", cancelacion.getFlag())
                .queryParam("op", cancelacion.getOpion())
                .queryParam("alterna", cancelacion.getAlterna())
                .queryParam("proceso", cancelacion.getProceso());
        CancelacionesDto result = restTemplate.getForObject(builder.toUriString(), CancelacionesDto.class);

        return result;
    }

    public CancelacionesDto getCancelacionByName(String nameExpresion) {

        final String url = urlpaht + "findCancelacionByName";
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(url)
                .queryParam("name", nameExpresion);
        CancelacionesDto result = restTemplate.getForObject(builder.toUriString(), CancelacionesDto.class);

        return result;
    }

    public CancelacionesDto getCancelacionById(Integer id) {

        final String url = urlpaht + "findCancelacionById";
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(url)
                .queryParam("id", id);
        CancelacionesDto result = restTemplate.getForObject(builder.toUriString(), CancelacionesDto.class);

        return result;
    }

    public CancelacionesDto getCancelById(Integer idExpresion) {

        final String url = urlpaht + "findCancelacionById";
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(url)
                .queryParam("id", idExpresion);
        CancelacionesDto result = restTemplate.getForObject(builder.toUriString(), CancelacionesDto.class);

        return result;
    }

    public Boolean getEliminarCancelacionById(Integer idExpresion) {

        final String url = urlpaht + "deleteCancelacionById";
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(url)
                .queryParam("id", idExpresion);
        Boolean result = restTemplate.getForObject(builder.toUriString(), Boolean.class);

        return result;
    }

    
    public Boolean delCancelacionById(Integer id) {

        final String url = urlpaht+"deleteCancelacionById";
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(url)
                .queryParam("id", id);
        Boolean result = restTemplate.getForObject(builder.toUriString(), Boolean.class);
        //System.out.println(result);
        return result;
    }
}
