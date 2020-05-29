package com.accusys.ar.controller;

import com.accusys.ar.AgenteSpringBootConsoleApplication;
import org.springframework.stereotype.Service;
import com.accusys.ar.modelDto.Export;
import com.accusys.ar.modelDto.ExpresionesRegularesIO;
import com.accusys.ar.modelDto.PantallaDto;
import com.accusys.ar.modelDto.TransaccionExport;
import com.accusys.ar.util.ExcepcionBaseMsn;
import com.accusys.ar.util.UtilRobot;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.tn5250j.Session5250;
import org.tn5250j.beans.ProtocolBean;
import org.tn5250j.framework.tn5250.Screen5250;
import org.tn5250j.framework.tn5250.ScreenField;
import org.tn5250j.framework.tn5250.ScreenFields;
import org.tn5250j.framework.tn5250.ScreenPlanes;

@Service
@PropertySource("classpath:application.properties")
public class EjecutorController {

    private static final Logger log = LogManager.getLogger(AgenteSpringBootConsoleApplication.class.getName());

    public Screen5250 screen;
    public boolean conectado;
    public static Session5250 sessions = null;
    public String pantalla;
    public String pantalla2;
    public String[] parametros;

    @Value("${ruta.archivo}")
    public String rutaArchivo;

    @Value("${trazaGuia}")
    public String trazaGuia;

    @Value("${num.inten.close}")
    public int numIntClose;

    public String nombreArchivo = "";
    @Autowired
    UtilRobot util;

    public void importarTransaccion(String[] args) throws InterruptedException {
        this.parametros = args;
        if (args.length > 0) {
            nombreArchivo = args[0];
            boolean flag = true;
            TransaccionExport export = new TransaccionExport();
            JSONParser parser = new JSONParser();
            String jsonString = "";
            Gson gson = new Gson();
            try {
                Object obj = parser.parse(new FileReader(rutaArchivo + nombreArchivo));
                JSONObject jsonObject = (JSONObject) obj;
                jsonString = jsonObject.toString();
                export = gson.fromJson(jsonString, TransaccionExport.class);
                simuladorAs(export);

            } catch (FileNotFoundException e) {
                System.err.println("codError:1002," + e.getMessage());
                //manejo de error
            } catch (IOException e) {
                e.printStackTrace();
                //manejo de error
            } catch (ParseException e) {
                System.err.println("codError:1003," + e.getMessage());
                //manejo de error
            }
        } else {
            log.warn("ruta del Archivo ---->" + rutaArchivo + nombreArchivo);
            System.out.println("ruta del Archivo ---->" + rutaArchivo + nombreArchivo);
            log.warn("codError:1001, Favor ingrezar nombre del archivo json por los parametros");
            System.err.println("codError:1001, Favor ingrezar nombre del archivo json por los parametros");
        }
    }

    private String getScreenAsString(Screen5250 screen) {

        char[] buffer = new char[1920];
        screen.GetScreen(buffer, 1920, ScreenPlanes.PLANE_TEXT);
        return new String(buffer);
    }

    private List<String> printScreen(Screen5250 screen) {
        String showme = getScreenAsString(screen);
        String sb = "";
        List<String> pantalla = new ArrayList<>();

        for (int i = 0; i < showme.length(); i += 80) {
            String sb2 = "";
            sb2 += showme.substring(i, i + 80);

            sb += " \n ";
            pantalla.add(sb2);
        }
        //System.out.println(sb);
        return pantalla;
    }

    private String printScreen1(Screen5250 screen) {
        String showme = getScreenAsString(screen);
        String sb = "";

        for (int i = 0; i < showme.length(); i += 80) {
            sb += showme.substring(i, i + 80);
            sb += "\n";
        }
        //System.out.println(sb);
        return sb;
    }

    private String printScreenLinea(Screen5250 screen, String Expresion) {
        String showme = getScreenAsString(screen);
        String sb = "";
        String sc = "";
        for (int i = 0; i < showme.length(); i += 80) {
            sc = showme.substring(i, i + 80);
            if (util.comparadorDeCaracteres(sc, Expresion)) {
                sb += sc;
            }
            //sb += showme.substring(i, i + 80);
            sb += "\n";
        }
        //System.out.println(sb);
        return sb;
    }

    public boolean isOnlyNumber(String value) {
        boolean ret = false;
        ret = value.matches("^[0-9]+$");
        return ret;
    }

    private String findParam(String indice) {
        Export exp = new Export();
        exp.setFlag(false);
        String aux = "", aux2 = "", valor = "", valor2 = "", valorNum = "";
        if (util.comparadorDeCaracteres2(indice, "*")) {
            valor2 = indice.split(":")[0];
            valor = indice;
            valorNum = valor2.split("_")[1];
            if (isOnlyNumber(valorNum)) {
                aux2 = pantalla2 + "-F" + (Integer.valueOf(valor2.split("_")[1]));
                for (String parametro : parametros) {
                    if (util.comparadorDeCaracteres(parametro.split(":")[0], aux2)) {
                        exp.setFlag(true);
                        aux = parametro.split("-")[1].split(":")[1];
                        if (aux.length() > 0) {
                            exp.setDescripcion(aux);
                            valor = valor2 + ":" + aux;
                        } else {
                            valor = indice;
                            exp.setFlag(false);
                        }
                    }
                }
            }
        } else {
            valor = indice;
            exp.setFlag(false);
        }
        return valor;
    }

    private void printScreen2(Screen5250 screen) {
        String showme = getScreenAsString(screen);
        String sb = "";

        for (int i = 0; i < showme.length(); i += 80) {
            sb += showme.substring(i, i + 80);
            sb += "\n";
        }
        //System.out.println(sb);
    }
    
    
    private Screen5250 connect(String servidor, String usuario, String clave, String devName) {
        ProtocolBean pb = new ProtocolBean(usuario, clave);
        Screen5250 screen = null;
        try {
            pb.setHostName(servidor);
            if (!devName.equals("")) {
                pb.setDeviceName(devName);
            }
            sessions = pb.getSession();
            pb.connect();
            screen = sessions.getScreen();
            Thread.sleep(3000L);
            conectado = sessions.isConnected();
            System.err.println("Is connected? - " + sessions.isConnected());
            printScreen(screen);
            return screen;
        } catch (UnknownHostException ex) {
            return screen;
        } catch (IllegalStateException ex) {
            return screen;
        } catch (InterruptedException ex) {
            return screen;
        }
        //To change body of generated methods, choose Tools | Templates.
    }

    private Screen5250 connect2(String servidor, String usuario, String clave) {
        ProtocolBean pb = new ProtocolBean(usuario, clave);
        Screen5250 screen = null;
        try {
            pb.setHostName(servidor);
            
            
            sessions = pb.getSession();
            pb.connect();
            screen = sessions.getScreen();
            Thread.sleep(3000L);
            conectado = sessions.isConnected();
            //System.err.println("Is connected? - " + sessions.isConnected());
            //printScreen(screen);
            return screen;
        } catch (UnknownHostException ex) {
            return screen;
        } catch (IllegalStateException ex) {
            return screen;
        } catch (InterruptedException ex) {
            return screen;
        }
    }

    public Export ExpresionesAS4(String textoDePantalla, Integer idExpresion) {
        Export flag = new Export();
        Boolean process = true;
        if (idExpresion > 0) {
            ExpresionesRegularesIO ExpresionAs = util.getExpresionById(idExpresion);
            if (util.comparadorDeCaracteres(textoDePantalla, ExpresionAs.getCodError())) {
                flag.setDescripcion(printScreenLinea(screen, ExpresionAs.getCodError()));
                //flag.setDescripcion(ExpresionAs.getMensajeError());
                process = false;
            }
        }
        flag.setFlag(process);

        return flag;
    }

    public Boolean operacionesAlternativas(String textoDePantalla, List<PantallaDto> listaActual, String operacion) {
        Boolean process = false;
        String[] dataForm2 = new String[70];
        for (PantallaDto pantallaDto1 : listaActual) {
            dataForm2 = pantallaDto1.getScrips().split(",");
            String num = (pantallaDto1.getScrips().split(",")[1]);
            String textComparador = (pantallaDto1.getScrips().split(",")[2].split(":")[1]);
            if (pantallaDto1.getScrips().contains("opc") && textoDePantalla.contains(textComparador)) {
                if (operacion != "conec") {
                    operaciones(dataForm2);
                }
                process = true;
            }
        }
        return process;
    }

    private Boolean procesado(List<PantallaDto> listaActual, int indice) throws ExcepcionBaseMsn {
        int longitud = listaActual.size();
        Boolean flag = false;

        if (longitud > (indice + 1)) {
            PantallaDto pantallaSiguiente = listaActual.get(indice + 1);

            if (pantallaSiguiente.getInputs().size() > 0) {
                if (!(pantallaSiguiente.getScrips().contains("opt"))) {
                    String texto = (pantallaSiguiente.getInputs().get(0).getValue()).trim();
                    PantallaDto pant = new PantallaDto();
                    if (util.comparadorDeCaracteres(getScreenAsString(screen), texto)) {
                        pant.setTextoPantalla(printScreen(screen));
                        flag = true;
                    } else {
                        if (operacionesAlternativas(getScreenAsString(screen), listaActual, "conec")) {
                            pant.setTextoPantalla(printScreen(screen));
                            throw new ExcepcionBaseMsn("Codigo:0010,\n" + printScreen1(screen));
                        } else {
                            pant.setTextoPantalla(printScreen(screen));
                            throw new ExcepcionBaseMsn("Codigo:0020,\n" + printScreen1(screen));
                        }
                    }
                }
            }
        } else {
            //System.out.println(printScreen1(screen));
            flag = true;
            throw new ExcepcionBaseMsn("Codigo:0020,\n" + printScreen1(screen));

        }
        return flag;
    }

    public void localizadorPantalla(String[] array) {
        for (String texto : array) {
            if (util.comparadorDeCaracteres2(texto, "w_flagPantalla")) {
                String actExp = texto.split(":")[1];
                System.out.println(trazaGuia + actExp);
                log.warn(trazaGuia + actExp);
            }
        }

    }

    public void cierreOperaciones(List<PantallaDto> listaPantallaCierre) {
        String[] dataForm2 = new String[100];
        Boolean flagCierre = true;
        String script = "";
        List<PantallaDto> listPantalla = listaPantallaCierre;
        int index = 0;
         int closeC = 0;
        for (PantallaDto pantallaDto1 : listPantalla) {
            index++;
            dataForm2 = pantallaDto1.getScrips().split(",");
            if (index <= (listPantalla.size() - 1)) {
               
                String textComparador = listPantalla.get(index).getScrips().split(",")[2].split(":")[1];
                do {
                    closeC++;
                    operaciones(dataForm2);
                    if (util.comparadorDeCaracteres(getScreenAsString(screen).trim(), textComparador)) {
                        flagCierre = false;
                    }
                    if(closeC == numIntClose){
                        flagCierre = false;
                    }
                    
                    
                } while (flagCierre);
            } else {
                operaciones(dataForm2);
            }
        }
        if (sessions != null) {
            sessions.disconnect();
        }

    }

    public void simuladorAs(TransaccionExport export) throws UnsupportedEncodingException {
        List<PantallaDto> listaActual = export.getListaPantalla();
        //listPatallaSiluladora.clear();
        String[] dataForm = new String[70];
        String scrits = "";
        int indice = 0;
        try {
            bucle1:
            for (PantallaDto pantallaDto : listaActual) {
                PantallaDto panti = new PantallaDto();
                scrits = pantallaDto.getScrips();
                scrits = URLDecoder.decode(scrits, "UTF-8");
                String pantallaScrip = pantallaDto.getScrips();
                pantallaScrip = URLDecoder.decode(pantallaScrip, "UTF-8");
                dataForm = pantallaScrip.split(",");
                pantallaDto.setId(null);
                pantalla2 = "P" + dataForm[1].split(":")[1];
                String actExp = dataForm[5];
                actExp = actExp.split(":")[1];
                actExp = actExp.replace("*", "");

                if (scrits.contains("conec")) {

                    boolean flag2 = true;
                    String devName = "", host = "", usuario = "", clave = "";
                    if (util.comparadorDeCaracteres(scrits, "w_deviceName")) {

                        devName = dataForm[7];
                        devName = devName.split(":")[1];
                        devName = devName.replace("*", "");
                        host = dataForm[8];
                        host = findParam(host);
                        host = host.split(":")[1];
                        host = host.replace("*", "");
                        usuario = dataForm[9];
                        usuario = findParam(usuario);
                        usuario = usuario.split(":")[1];
                        usuario = usuario.replace("*", "");
                        clave = dataForm[10];
                        clave = findParam(clave);
                        clave = clave.split(":")[1];
                        clave = clave.replace("*", "");

                    } else {

                        host = dataForm[7];
                        host = host.split(":")[1];
                        host = host.replace("*", "");
                        usuario = dataForm[8];
                        usuario = usuario.split(":")[1];
                        usuario = usuario.replace("*", "");
                        clave = dataForm[9];
                        clave = clave.split(":")[1];
                        clave = clave.replace("*", "");

                    }

                    screen = connect(host, usuario, clave, devName);
                    log.warn(printScreen1(screen));
                    //System.out.println(getScreenAsString(screen));
                    if (sessions.isConnected()) {
                        String idCiclo = dataForm[2].split(":")[1];
                        Integer numInt = Integer.valueOf(dataForm[3].split(":")[1]);
                        Integer expresionId = Integer.valueOf(dataForm[4].split(":")[1]);

                        localizadorPantalla(dataForm);
                        if (!idCiclo.equals("0")) {
                            switch (idCiclo) {
                                // segmento de ciclo for de la conexion;
                                case "f":
                                    if (numInt > 0) {
                                        for (int i = 0; i < numInt; i++) {
                                            ScreenFields sf = screen.getScreenFields();
                                            Thread.sleep(3000L);
                                            ScreenField userField = sf.getField(0);
                                            userField.setString(usuario);
                                            ScreenField passField = sf.getField(1);
                                            passField.setString(clave);
                                            screen.sendKeys("[enter]");
                                            Thread.sleep(3000L);
                                            String pantallas = getScreenAsString(screen).trim();

                                            log.warn(printScreen1(screen));
//                                            System.out.println(pantalla);
                                            if (expresionId > 0) {
                                                Export expReq = ExpresionesAS4(getScreenAsString(screen).trim(), expresionId);
                                                if (expReq.getFlag()) {
                                                    if (procesado(listaActual, indice)) {
                                                        break;
                                                    }
                                                    Thread.sleep(2000L);
                                                } else {
                                                    Boolean a = true;
                                                    PantallaDto pant = new PantallaDto();
                                                    if (actExp.equals("i")) {
                                                        pant.setTextoPantalla(printScreen(screen));
                                                        //throw new ExcepcionBaseMsn("Codigo:0020,\n" + printScreen1(screen));
                                                        throw new ExcepcionBaseMsn("Codigo:0020,\n" + expReq.getDescripcion());
                                                    } else if (actExp.equals("e")) {
                                                        pant.setTextoPantalla(printScreen(screen));
//                                                        throw new ExcepcionBaseMsn("Codigo:0020,\n" + printScreen1(screen));
                                                        throw new ExcepcionBaseMsn("Codigo:0010,\n" + expReq.getDescripcion());
                                                    }
                                                }
                                            } else {
                                                if (procesado(listaActual, indice)) {
                                                    break;
                                                }
                                                Thread.sleep(2000L);
                                            }
                                        }
                                    } else {
                                        //emitir una excceion no tiene cantidad de repeticiones 
                                        throw new ExcepcionBaseMsn("Codigo:0003,La expresion de ciclo for no posee numero de iteraciones.");
                                    }
                                    break;
                                case "w":
                                    // segmento de ciclo While de la conexion;   
                                    do {
                                        String texto = "";
                                        ScreenFields sf = screen.getScreenFields();
                                        Thread.sleep(3000L);
                                        ScreenField userField = sf.getField(0);
                                        userField.setString(usuario);
                                        ScreenField passField = sf.getField(1);
                                        passField.setString(clave);
                                        screen.sendKeys("[enter]");
                                        Thread.sleep(3000L);
                                        int longitud = listaActual.size();

                                        log.warn(printScreen1(screen));
//                                        System.out.println(pantalla);
                                        if (expresionId > 0) {
                                            Export expReq = ExpresionesAS4(getScreenAsString(screen).trim(), expresionId);
                                            if (expReq.getFlag()) {
                                                if (procesado(listaActual, indice)) {
                                                    flag2 = false;
                                                }
                                                Thread.sleep(2000L);
                                            } else {
                                                PantallaDto pant = new PantallaDto();
                                                if (actExp.equals("i")) {
                                                    pant.setTextoPantalla(printScreen(screen));
                                                    flag2 = false;
                                                    //texto = "Codigo:0020,\n" + printScreen1(screen);
                                                    texto = "Codigo:0020,\n" + expReq.getDescripcion();
                                                    throw new ExcepcionBaseMsn("Codigo:0020,\n" + expReq.getDescripcion());
                                                } else if (actExp.equals("e")) {
                                                    pant.setTextoPantalla(printScreen(screen));
                                                    flag2 = false;
                                                    //texto = "Codigo:0010,\n" + printScreen1(screen);
                                                    texto = "Codigo:0010,\n" + expReq.getDescripcion();
                                                    throw new ExcepcionBaseMsn("Codigo:0020,\n" + expReq.getDescripcion());
                                                }
                                            }
                                        } else {
                                            if (procesado(listaActual, indice)) {
                                                flag2 = false;
                                            }
                                            Thread.sleep(2000L);
                                        }
                                    } while (flag2);
                                    break;
                            }
                        } else {
                            ScreenFields sf = screen.getScreenFields();
                            Thread.sleep(3000L);
                            ScreenField userField = sf.getField(0);
                            userField.setString(usuario);
                            ScreenField passField = sf.getField(1);
                            passField.setString(clave);
                            screen.sendKeys("[enter]");
                            Thread.sleep(3000L);

                            log.warn(printScreen1(screen));
                            // System.out.println(pantalla);
                            if (expresionId > 0) {
                                Export expReq = ExpresionesAS4(pantalla, expresionId);
                                if (expReq.getFlag()) {
                                    int longitud = listaActual.size();
                                    procesado(listaActual, indice);
                                    Thread.sleep(2000L);
                                } else {
                                    Boolean a = true;
                                    PantallaDto pant = new PantallaDto();
                                    if (actExp.equals("i")) {
                                        pant.setTextoPantalla(printScreen(screen));
                                        // throw new ExcepcionBaseMsn("Codigo:0020,\n" + printScreen1(screen));
                                        throw new ExcepcionBaseMsn("Codigo:0020,\n" + expReq.getDescripcion());
                                    } else if (actExp.equals("e")) {

                                        pant.setTextoPantalla(printScreen(screen));
                                        //throw new ExcepcionBaseMsn("Codigo:0010,\n" + printScreen1(screen));
                                        throw new ExcepcionBaseMsn("Codigo:0010,\n" + expReq.getDescripcion());
                                    } else if (actExp.equals("r")) {
                                        userField.setString(usuario);
                                        passField.setString(clave);
                                        screen.sendKeys("[enter]");
                                        pant.setTextoPantalla(printScreen(screen));
                                    }
                                }
                            } else {
                                procesado(listaActual, indice);
                                Thread.sleep(2000L);
                            }
                        }
                    } else {
                        throw new ExcepcionBaseMsn("Codigo:0002, Error Rota Conexion remota con el servidor AS400");
                    }

                    indice++;
                } else if (scrits.contains("oper")) {
                    localizadorPantalla(dataForm);
                    boolean flag2 = true;
                    if (sessions.isConnected()) {
                        String idCiclo = dataForm[4].split(":")[1];
                        Integer numInt = Integer.valueOf(dataForm[5].split(":")[1]);
                        Integer expresionId = Integer.valueOf(dataForm[6].split(":")[1]);
                        if (!idCiclo.equals("0")) {
                            switch (idCiclo) {
                                case "f":
                                    if (numInt > 0) {
                                        for (int j = 0; j < numInt; j++) {
                                            operaciones(dataForm);
                                            String pantallaTexto = getScreenAsString(screen).trim();
                                            if (expresionId > 0) {
                                                Export expReq = ExpresionesAS4(pantallaTexto, expresionId);
                                                if (expReq.getFlag()) {
                                                    if (procesado(listaActual, indice)) {
                                                        break;
                                                    }
                                                } else {
                                                    // manejar el accion programada para la expresion Mostrar pantalla o teclear [Enter] u otra tecla.
                                                    Boolean a = true;
                                                    PantallaDto pant = new PantallaDto();
                                                    if (actExp.equals("i")) {
                                                        pant.setTextoPantalla(printScreen(screen));
                                                        //throw new ExcepcionBaseMsn("Codigo:0020,\n" + printScreen1(screen));
                                                        throw new ExcepcionBaseMsn("Codigo:0020,\n" + expReq.getDescripcion());
                                                    } else if (actExp.equals("e")) {
                                                        pant.setTextoPantalla(printScreen(screen));
//                                                        throw new ExcepcionBaseMsn("Codigo:0010,\n" + printScreen1(screen));
                                                        throw new ExcepcionBaseMsn("Codigo:0010,\n" + expReq.getDescripcion());
                                                    }
                                                }
                                            } else {
                                                if (procesado(listaActual, indice)) {
                                                    break;
                                                }
                                            }
                                        }
                                    } else {
                                        //emitir una excceion no tiene cantidad de repeticiones 
                                        throw new ExcepcionBaseMsn("Codigo:0002,La expresion de ciclo for no posee numero de iteraciones.");
                                    }
                                    break;

                                case "w":
                                    // segmento de ciclo while de la operaciones
                                    do {
                                        operaciones(dataForm);
                                        int longitud = listaActual.size();
                                        String pantalla = getScreenAsString(screen).trim();
                                        if (expresionId > 0) {
                                            Export expReq = ExpresionesAS4(getScreenAsString(screen).trim(), expresionId);
                                            if (expReq.getFlag()) {
                                                if (procesado(listaActual, indice)) {
                                                    flag2 = false;
                                                }
                                            } else {
                                                Boolean a = true;
                                                PantallaDto pant = new PantallaDto();
                                                if (actExp.equals("i")) {
                                                    pant.setTextoPantalla(printScreen(screen));
                                                    //throw new ExcepcionBaseMsn("Codigo:0020,\n" + printScreen1(screen));
                                                    throw new ExcepcionBaseMsn("Codigo:0020,\n" + expReq.getDescripcion());
                                                } else if (actExp.equals("e")) {
                                                    pant.setTextoPantalla(printScreen(screen));
                                                    //throw new ExcepcionBaseMsn("Codigo:0010,\n" + printScreen1(screen));
                                                    throw new ExcepcionBaseMsn("Codigo:0010,\n" + expReq.getDescripcion());
                                                }
                                            }
                                        } else {
                                            if (procesado(listaActual, indice)) {
                                                flag2 = false;
                                            }
                                        }
                                    } while (flag2);
                                    break;
                            }
                        } else {
                            operaciones(dataForm);
                            int longitud = listaActual.size();
                            String pantalla = getScreenAsString(screen).trim();
                            if (expresionId > 0) {
                                Export expReq = ExpresionesAS4(getScreenAsString(screen).trim(), expresionId);
                                if (expReq.getFlag()) {
                                    if (procesado(listaActual, indice)) {
                                        flag2 = false;
                                    }
                                } else {
                                    Boolean a = true;
                                    PantallaDto pant = new PantallaDto();
                                    if (actExp.equals("i")) {
                                        pant.setTextoPantalla(printScreen(screen));
                                        //throw new ExcepcionBaseMsn("Codigo:0020,\n" + printScreen1(screen));
                                        throw new ExcepcionBaseMsn("Codigo:0020,\n" + expReq.getDescripcion());
                                    } else if (actExp.equals("e")) {
                                        pant.setTextoPantalla(printScreen(screen));
                                        //throw new ExcepcionBaseMsn("Codigo:0010,\n" + printScreen1(screen));
                                        throw new ExcepcionBaseMsn("Codigo:0010,\n" + expReq.getDescripcion());
                                    } else if (actExp.equals("r")) {
                                        operaciones(dataForm);
                                        pant.setTextoPantalla(printScreen(screen));
                                    }
                                }
                            } else {
                                if (procesado(listaActual, indice)) {
                                    flag2 = false;
                                }
                            }
                        }

                    } else {
                        throw new ExcepcionBaseMsn("Codigo:0002, Error Rota Conexion remota con el servidor AS400");
                    }
                    indice++;
                }
            }
            PantallaDto pant = new PantallaDto();
            pant.setTextoPantalla(printScreen(screen));
            //sessions.disconnect();
            cierreOperaciones(export.getListaPantallaCierre());
        } catch (ExcepcionBaseMsn ex) {
            String procesado = ex.getMessage();
            //sessions.disconnect();
            cierreOperaciones(export.getListaPantallaCierre());
            if (util.comparadorDeCaracteres(procesado, "0020")) {
                System.out.println(procesado);
            } else {
                System.err.println(procesado);
            }

        } catch (InterruptedException ex) {
            System.err.print(ex.getMessage());
            cierreOperaciones(export.getListaPantallaCierre());
            //Logger.getLogger(EjecutorController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void operaciones(String[] dataForm) {
        ScreenFields sf = screen.getScreenFields();
        try {
            Thread.sleep(3000L);
            for (int i = 9; i < dataForm.length; i++) {
                String datos = dataForm[i];
                datos = findParam(datos);
                String[] datoAux = datos.split(":");
                String indice = datoAux[0].split("_")[1];
                String valor = datoAux[1];
                valor = valor.replace("*", "");;

                if (indice.equals("0")) {
                    ScreenField field_0 = sf.getField(0);
                    field_0.setString(valor);
                }
                if (indice.equals("1")) {
                    ScreenField field_1 = sf.getField(1);
                    field_1.setString(valor);
                }
                if (indice.equals("2")) {
                    ScreenField field_2 = sf.getField(2);
                    field_2.setString(valor);
                }
                if (indice.equals("3")) {
                    ScreenField field_3 = sf.getField(3);
                    field_3.setString(valor);
                }

                if (indice.equals("4")) {
                    ScreenField field_4 = sf.getField(4);
                    field_4.setString(valor);
                }

                if (indice.equals("5")) {
                    ScreenField field_5 = sf.getField(5);
                    field_5.setString(valor);
                }

                if (indice.equals("6")) {
                    ScreenField field_6 = sf.getField(6);
                    field_6.setString(valor);
                }

                if (indice.equals("7")) {
                    ScreenField field_7 = sf.getField(7);
                    field_7.setString(valor);
                }

                if (indice.equals("8")) {
                    ScreenField field_8 = sf.getField(8);
                    field_8.setString(valor);
                }

                if (indice.equals("9")) {
                    ScreenField field_9 = sf.getField(9);
                    field_9.setString(valor);
                }

                if (indice.equals("10")) {
                    ScreenField field_10 = sf.getField(10);
                    field_10.setString(valor);
                }

                if (indice.equals("11")) {
                    ScreenField field_11 = sf.getField(11);
                    field_11.setString(valor);
                }

                if (indice.equals("12")) {
                    ScreenField field_12 = sf.getField(12);
                    field_12.setString(valor);
                }

                if (indice.equals("13")) {
                    ScreenField field_13 = sf.getField(13);
                    field_13.setString(valor);
                }

                if (indice.equals("14")) {
                    ScreenField field_14 = sf.getField(14);
                    field_14.setString(valor);
                }

                if (indice.equals("15")) {
                    ScreenField field_15 = sf.getField(15);
                    field_15.setString(valor);
                }

                if (indice.equals("16")) {
                    ScreenField field_16 = sf.getField(16);
                    field_16.setString(valor);
                }

                if (indice.equals("17")) {
                    ScreenField field_17 = sf.getField(17);
                    field_17.setString(valor);
                }

                if (indice.equals("18")) {
                    ScreenField field_18 = sf.getField(18);
                    field_18.setString(valor);
                }

                if (indice.equals("19")) {
                    ScreenField field_19 = sf.getField(19);
                    field_19.setString(valor);
                }

                if (indice.equals("20")) {
                    ScreenField field_20 = sf.getField(20);
                    field_20.setString(valor);
                }
                if (indice.equals("21")) {
                    ScreenField field_21 = sf.getField(21);
                    field_21.setString(valor);
                }

                if (indice.equals("22")) {
                    ScreenField field_22 = sf.getField(22);
                    field_22.setString(valor);
                }

                if (indice.equals("23")) {
                    ScreenField field_23 = sf.getField(23);
                    field_23.setString(valor);
                }

                if (indice.equals("24")) {
                    ScreenField field_24 = sf.getField(24);
                    field_24.setString(valor);
                }

                if (indice.equals("25")) {
                    ScreenField field_25 = sf.getField(25);
                    field_25.setString(valor);
                }

                if (indice.equals("26")) {
                    ScreenField field_26 = sf.getField(26);
                    field_26.setString(valor);
                }

                if (indice.equals("27")) {
                    ScreenField field_27 = sf.getField(27);
                    field_27.setString(valor);
                }

                if (indice.equals("28")) {
                    ScreenField field_28 = sf.getField(28);
                    field_28.setString(valor);
                }

                if (indice.equals("29")) {
                    ScreenField field_29 = sf.getField(29);
                    field_29.setString(valor);
                }

                if (indice.equals("30")) {
                    ScreenField field_30 = sf.getField(30);
                    field_30.setString(valor);
                }

                if (indice.equals("31")) {
                    ScreenField field_31 = sf.getField(31);
                    field_31.setString(valor);
                }

                if (indice.equals("32")) {
                    ScreenField field_32 = sf.getField(32);
                    field_32.setString(valor);
                }

                if (indice.equals("33")) {
                    ScreenField field_33 = sf.getField(33);
                    field_33.setString(valor);
                }

                if (indice.equals("34")) {
                    ScreenField field_34 = sf.getField(34);
                    field_34.setString(valor);
                }

                if (indice.equals("35")) {
                    ScreenField field_35 = sf.getField(35);
                    field_35.setString(valor);
                }

                if (indice.equals("36")) {
                    ScreenField field_36 = sf.getField(36);
                    field_36.setString(valor);
                }

                if (indice.equals("37")) {
                    ScreenField field_37 = sf.getField(37);
                    field_37.setString(valor);
                }

                if (indice.equals("38")) {
                    ScreenField field_38 = sf.getField(38);
                    field_38.setString(valor);
                }

                if (indice.equals("39")) {
                    ScreenField field_39 = sf.getField(39);
                    field_39.setString(valor);
                }

                if (indice.equals("40")) {
                    ScreenField field_40 = sf.getField(40);
                    field_40.setString(valor);
                }

                if (indice.equals("41")) {
                    ScreenField field_41 = sf.getField(41);
                    field_41.setString(valor);
                }

                if (indice.equals("42")) {
                    ScreenField field_42 = sf.getField(42);
                    field_42.setString(valor);
                }

                if (indice.equals("43")) {
                    ScreenField field_43 = sf.getField(43);
                    field_43.setString(valor);
                }

                if (indice.equals("44")) {
                    ScreenField field_44 = sf.getField(44);
                    field_44.setString(valor);
                }

                if (indice.equals("45")) {
                    ScreenField field_45 = sf.getField(45);
                    field_45.setString(valor);
                }

                if (indice.equals("46")) {
                    ScreenField field_46 = sf.getField(46);
                    field_46.setString(valor);
                }

                if (indice.equals("47")) {
                    ScreenField field_47 = sf.getField(47);
                    field_47.setString(valor);
                }

                if (indice.equals("48")) {
                    ScreenField field_48 = sf.getField(48);
                    field_48.setString(valor);
                }

                if (indice.equals("49")) {
                    ScreenField field_49 = sf.getField(49);
                    field_49.setString(valor);
                }
            }

            if (dataForm[3].split(":")[1].equals("[enter]")) {
                screen.sendKeys("[enter]");
            } else {
                screen.sendKeys(dataForm[3].split(":")[1]);
            }

            Thread.sleep(3000L);
            String pantalla = printScreen1(screen);
            log.warn(pantalla);


        } catch (InterruptedException ex) {
            //          Logger.getLogger(EjecutorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
