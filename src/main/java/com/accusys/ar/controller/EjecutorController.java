package com.accusys.ar.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public Screen5250 screen;
    public boolean conectado;
    public static Session5250 sessions = null;
    public String pantalla;
    public String[] parametros;

    @Value("${ruta.archivo}")
    public String rutaArchivo;

    public String nombreArchivo = "transaccion-transTest1-1581515580843.json";
    @Autowired
    UtilRobot util;

    public void importarTransaccion(String[] args) throws InterruptedException {
        this.parametros = args;
        // if (args.length>0) {
        //nombreArchivo = args[0];
        boolean flag = true;
        TransaccionExport export = new TransaccionExport();
        JSONParser parser = new JSONParser();
        String jsonString = "";
        Gson gson = new Gson();
        try {
            System.out.println("ruta del Archivo " + rutaArchivo + nombreArchivo);
            Object obj = parser.parse(new FileReader(rutaArchivo + nombreArchivo));
            JSONObject jsonObject = (JSONObject) obj;
            jsonString = jsonObject.toString();
            export = gson.fromJson(jsonString, TransaccionExport.class);
            String respuestaSimukador = simuladorAs(export.getListaPantalla());
            System.out.println(respuestaSimukador);
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
//        }else{
//            System.out.println("ruta del Archivo ---->"+rutaArchivo + nombreArchivo);
//            System.err.println("codError:1001, Favor ingrezar nombre del archivo json por los parametros");
//        }
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
        System.out.println(sb);
        return pantalla;
    }

    private Export findParam(String indice) {
        Export exp = new Export();
        exp.setFlag(false);
        String aux = "";
        

        if (util.comparadorDeCaracteres(indice, "*")) {
            
            indice = indice.split(":")[0];
            indice = pantalla+"-P"+indice.split("_")[1];

            for (String parametro : parametros) {
                if (util.comparadorDeCaracteres(parametro, indice)) {
                    exp.setFlag(true);
                    aux = parametro.split("-")[1].split(":")[1];
                    if (aux.length() > 0) {
                        exp.setDescripcion(aux);
                    } else {
                        exp.setFlag(false);
                    }
                }
            }
        } else {

        }

        return exp;
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

    private Screen5250 connect(String servidor, String usuario, String clave) {
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
            printScreen(screen);
            return screen;
        } catch (UnknownHostException ex) {
            Logger.getLogger(EjecutorController.class.getName()).log(Level.SEVERE, null, ex);
            return screen;
        } catch (IllegalStateException ex) {
            Logger.getLogger(EjecutorController.class.getName()).log(Level.SEVERE, null, ex);
            return screen;
        } catch (InterruptedException ex) {
            Logger.getLogger(EjecutorController.class.getName()).log(Level.SEVERE, null, ex);
            return screen;
        } //To change body of generated methods, choose Tools | Templates.
    }

    public Export ExpresionesAS4(String textoDePantalla, Integer idExpresion) {
        Export flag = new Export();
        Boolean process = true;
        if (idExpresion > 0) {
            ExpresionesRegularesIO ExpresionAs = util.getExpresionById(idExpresion);
            if (util.comparadorDeCaracteres(textoDePantalla, ExpresionAs.getCodError())) {
                flag.setDescripcion(ExpresionAs.getMensajeError());
                process = false;
            }
        } else {
            List<ExpresionesRegularesIO> expresionesAS = util.getExpresionAll();
            for (ExpresionesRegularesIO expresionRegular : expresionesAS) {
                if (util.comparadorDeCaracteres(textoDePantalla, expresionRegular.getCodError())) {
                    flag.setDescripcion(expresionRegular.getMensajeError());
                    process = false;
                }
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

    public String simuladorAs(List<PantallaDto> listaActual) {
        //listPatallaSiluladora.clear();
        String[] dataForm = new String[70];
        String scrits = "";
        int indice = 0;
        try {
            for (PantallaDto pantallaDto : listaActual) {

                PantallaDto panti = new PantallaDto();
                scrits = pantallaDto.getScrips();
                dataForm = pantallaDto.getScrips().split(",");
                pantallaDto.setId(null);
                pantalla = "P" + dataForm[1].split(":")[1];
                String actExp = dataForm[5];
                actExp = actExp.split(":")[1];
                actExp = actExp.replace("*", "");
                if (scrits.contains("conec")) {
                    boolean flag2 = true;
                    //pantallaDto.setPantallaNumero(listPatalla.size() + 1);
                    String host = dataForm[6];
                    findParam(host);
                    host = host.split(":")[1];
                    host = host.replace("*", "");
                    String usuario = dataForm[7];
                    usuario = usuario.split(":")[1];
                    usuario = usuario.replace("*", "");
                    String clave = dataForm[8];
                    clave = clave.split(":")[1];
                    clave = clave.replace("*", "");
                    screen = connect(host, usuario, clave);
                    System.out.println(getScreenAsString(screen));
                    //panti.setTextoPantalla(printScreen(screen));

                    if (sessions.isConnected()) {
                        String idCiclo = dataForm[2].split(":")[1];
                        Integer numInt = Integer.valueOf(dataForm[3].split(":")[1]);
                        Integer expresionId = Integer.valueOf(dataForm[4].split(":")[1]);
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
                                            String pantalla = getScreenAsString(screen).trim();
                                            System.out.println(pantalla);
                                            if (expresionId > 0) {
                                                Export expReq = ExpresionesAS4(getScreenAsString(screen).trim(), expresionId);
                                                if (expReq.getFlag()) {
                                                    int longitud = listaActual.size();

                                                    if (longitud > (indice + 1)) {
                                                        PantallaDto pantallaSiguiente = listaActual.get(indice + 1);

                                                        if (pantallaSiguiente.getInputs().size() > 0) {
                                                            if (pantallaSiguiente.getScrips().contains("opt")) {

                                                            }
                                                            String texto = (pantallaSiguiente.getInputs().get(0).getValue()).trim();
                                                            PantallaDto pant = new PantallaDto();
                                                            if (util.comparadorDeCaracteres(pantalla, texto)) {
                                                                pant.setTextoPantalla(printScreen(screen));

                                                                break;
                                                            } else {
                                                                if (operacionesAlternativas(getScreenAsString(screen), listaActual, "conec")) {
                                                                    pant.setTextoPantalla(printScreen(screen));

                                                                    throw new ExcepcionBaseMsn("Codigo:0010,Ejecucion de pantalla alternativa");
                                                                } else {
                                                                    pant.setTextoPantalla(printScreen(screen));

                                                                    throw new ExcepcionBaseMsn("Codigo:0020,Pantalla no fue reconocidad en proceso programado por el administrador de procesos");
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        break;
                                                    }
                                                    Thread.sleep(2000L);

                                                } else {
                                                    Boolean a = true;
                                                    PantallaDto pant = new PantallaDto();
                                                    if (actExp == "i") {

                                                        pant.setTextoPantalla(printScreen(screen));

                                                        throw new ExcepcionBaseMsn("Codigo:0010,Ejecucion de pantalla alternativa");

                                                    }

                                                    // manejar el accion programada para la expresion Mostrar pantalla o teclear [Enter] u otra tecla.                                                    
                                                }
                                            } else {
                                                int longitud = listaActual.size();
                                                if (longitud > (indice + 1)) {
                                                    PantallaDto pantallaSiguiente = listaActual.get(indice + 1);
                                                    if (pantallaSiguiente.getInputs().size() > 0) {
                                                        String texto = (pantallaSiguiente.getInputs().get(0).getValue()).trim();
                                                        PantallaDto pant = new PantallaDto();
                                                        if (util.comparadorDeCaracteres(pantalla, texto)) {
                                                            pant.setTextoPantalla(printScreen(screen));

                                                            break;
                                                        } else {
                                                            if (operacionesAlternativas(getScreenAsString(screen), listaActual, "conec")) {
                                                                pant.setTextoPantalla(printScreen(screen));

                                                                throw new ExcepcionBaseMsn("Codigo:0010,Ejecucion de pantalla alternativa");
                                                            } else {
                                                                pant.setTextoPantalla(printScreen(screen));

                                                                throw new ExcepcionBaseMsn("Codigo:0020,Pantalla no fue reconocidad en proceso programado por el administrador de procesos");
                                                            }
                                                        }
                                                    } else {
                                                        break;
                                                    }
                                                } else if (longitud == (indice + 1)) {
                                                    break;
                                                }
                                                Thread.sleep(2000L);

                                            }

                                        }

                                    } else {

                                        //emitir una excceion no tiene cantidad de repeticiones 
                                        throw new ExcepcionBaseMsn("Codigo:0020,La expresion de ciclo for no posee numero de iteraciones.");
                                    }
                                    break;

                                case "w":
                                    // segmento de ciclo While de la conexion;   
                                    do {
                                        ScreenFields sf = screen.getScreenFields();
                                        Thread.sleep(3000L);
                                        ScreenField userField = sf.getField(0);
                                        userField.setString(usuario);
                                        ScreenField passField = sf.getField(1);
                                        passField.setString(clave);
                                        screen.sendKeys("[enter]");
                                        Thread.sleep(3000L);

                                        int longitud = listaActual.size();
                                        String pantalla = getScreenAsString(screen).trim();
                                        System.out.println(pantalla);
                                        if (expresionId > 0) {
                                            Export expReq = ExpresionesAS4(getScreenAsString(screen).trim(), expresionId);
                                            if (expReq.getFlag()) {
                                                if (longitud > (indice + 1)) {
                                                    PantallaDto pantallaSiguiente = listaActual.get(indice + 1);
                                                    if (pantallaSiguiente.getInputs().size() > 0) {
                                                        String texto = (pantallaSiguiente.getInputs().get(0).getValue()).trim();
                                                        PantallaDto pant = new PantallaDto();
                                                        if (util.comparadorDeCaracteres(pantalla, texto)) {
                                                            pant.setTextoPantalla(printScreen(screen));

                                                            flag2 = false;
                                                        } else {
                                                            if (operacionesAlternativas(getScreenAsString(screen), listaActual, "conec")) {
                                                                pant.setTextoPantalla(printScreen(screen));

                                                                throw new ExcepcionBaseMsn("Codigo:0010,Ejecucion de pantalla alternativa");
                                                            } else {
                                                                pant.setTextoPantalla(printScreen(screen));

                                                                throw new ExcepcionBaseMsn("Codigo:0020,Pantalla no fue reconocidad en proceso programado por el administrador de procesos");
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    flag2 = false;
                                                }
                                                Thread.sleep(2000L);

                                            } else {
                                                Boolean a = true;
                                                PantallaDto pant = new PantallaDto();
                                                if (actExp == "i") {
                                                    pant.setTextoPantalla(printScreen(screen));

                                                    flag2 = false;
                                                }
                                            }
                                        } else {
                                            if (longitud > (indice + 1)) {
                                                PantallaDto pantallaSiguiente = listaActual.get(indice + 1);
                                                if (pantallaSiguiente.getInputs().size() > 0) {
                                                    String texto = (pantallaSiguiente.getInputs().get(0).getValue()).trim();
                                                    PantallaDto pant = new PantallaDto();
                                                    if (util.comparadorDeCaracteres(pantalla, texto)) {
                                                        pant.setTextoPantalla(printScreen(screen));

                                                        flag2 = false;
                                                    } else {
                                                        if (operacionesAlternativas(getScreenAsString(screen), listaActual, "conec")) {
                                                            pant.setTextoPantalla(printScreen(screen));

                                                            throw new ExcepcionBaseMsn("Codigo:0010,Ejecucion de pantalla alternativa");
                                                        } else {
                                                            pant.setTextoPantalla(printScreen(screen));

                                                            throw new ExcepcionBaseMsn("Codigo:0020,Pantalla no fue reconocidad en proceso programado por el administrador de procesos");
                                                        }
                                                    }
                                                }
                                            } else {
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
                            String pantalla = getScreenAsString(screen).trim();
                            System.out.println(pantalla);
                            if (expresionId > 0) {
                                Export expReq = ExpresionesAS4(pantalla, expresionId);
                                if (expReq.getFlag()) {

                                    int longitud = listaActual.size();

                                    if (longitud > (indice + 1)) {
                                        PantallaDto pantallaSiguiente = listaActual.get(indice + 1);
                                        if (pantallaSiguiente.getInputs().size() > 0) {
                                            String texto = (pantallaSiguiente.getInputs().get(0).getValue()).trim();
                                            PantallaDto pant = new PantallaDto();
                                            if (util.comparadorDeCaracteres(pantalla, texto)) {
                                                pant.setTextoPantalla(printScreen(screen));

                                            } else {
                                                if (operacionesAlternativas(getScreenAsString(screen), listaActual, "conec")) {
                                                    pant.setTextoPantalla(printScreen(screen));

                                                    throw new ExcepcionBaseMsn("Codigo:0010,error manejado modulo de conexion");
                                                } else {
                                                    pant.setTextoPantalla(printScreen(screen));

                                                    throw new ExcepcionBaseMsn("Codigo:0020,error en panatalla no manejado");
                                                }
                                            }
                                        }
                                    }
                                    Thread.sleep(2000L);

                                } else {
                                    Boolean a = true;
                                    PantallaDto pant = new PantallaDto();
                                    if (actExp == "i") {
                                        pant.setTextoPantalla(printScreen(screen));

                                        throw new ExcepcionBaseMsn("Codigo:0020,error en panatalla no manejado");
                                    } else if (actExp == "r") {
                                        userField.setString(usuario);
                                        passField.setString(clave);
                                        screen.sendKeys("[enter]");
                                        pant.setTextoPantalla(printScreen(screen));

                                    }

                                }
                            } else {
                                int longitud = listaActual.size();

                                if (longitud > (indice + 1)) {
                                    PantallaDto pantallaSiguiente = listaActual.get(indice + 1);
                                    if (pantallaSiguiente.getInputs().size() > 0) {
                                        String texto = (pantallaSiguiente.getInputs().get(0).getValue()).trim();
                                        PantallaDto pant = new PantallaDto();
                                        if (util.comparadorDeCaracteres(pantalla, texto)) {
                                            pant.setTextoPantalla(printScreen(screen));

                                        } else {
                                            if (operacionesAlternativas(getScreenAsString(screen), listaActual, "conec")) {
                                                pant.setTextoPantalla(printScreen(screen));

                                                throw new ExcepcionBaseMsn("Codigo:0010,error manejado modulo de conexion");
                                            } else {
                                                pant.setTextoPantalla(printScreen(screen));

                                                throw new ExcepcionBaseMsn("Codigo:0020,error en panatalla no manejado");
                                            }
                                        }
                                    }
                                }
                                Thread.sleep(2000L);

                            }

                        }
                    } else {
                        throw new ExcepcionBaseMsn("Codigo:0002, Error Rota Conexion remota con el servidor AS400");
                    }
                    indice++;
                } else if (scrits.contains("oper")) {
                    boolean flag2 = true;
                    operaciones(dataForm);

                    System.out.println(getScreenAsString(screen).trim());
                    // pantallaDto.setPantallaNumero(listPatalla.size() + 1);

                    if (sessions.isConnected()) {
                        String idCiclo = dataForm[4].split(":")[1];
                        Integer numInt = Integer.valueOf(dataForm[5].split(":")[1]);
                        Integer expresionId = Integer.valueOf(dataForm[6].split(":")[1]);
                        if (!idCiclo.equals("0")) {
                            switch (idCiclo) {
                                case "f":
                                    // segmento de ciclo for de la operaciones
                                    if (numInt > 0) {
                                        for (int j = 0; j < numInt; j++) {

                                            String pantallaTexto = getScreenAsString(screen).trim();
                                            if (expresionId > 0) {
                                                Export expReq = ExpresionesAS4(pantallaTexto, expresionId);
                                                if (expReq.getFlag()) {
                                                    int longitud = listaActual.size();
                                                    if (longitud > (indice + 1)) {
                                                        PantallaDto pantallaSiguiente = listaActual.get(indice + 1);
                                                        if (pantallaSiguiente.getInputs().size() > 0) {
                                                            String texto = pantallaSiguiente.getInputs().get(0).getValue().trim();
                                                            PantallaDto pant = new PantallaDto();
                                                            if (util.comparadorDeCaracteres(pantallaTexto, texto)) {
                                                                pant.setTextoPantalla(printScreen(screen));

                                                                break;
                                                            } else {
                                                                if (operacionesAlternativas(getScreenAsString(screen), listaActual, "oper")) {
                                                                    pant.setTextoPantalla(printScreen(screen));

                                                                    throw new ExcepcionBaseMsn("Codigo:0010,Ejecucion de pantalla alternativa");
                                                                } else {
                                                                    //printScreen2(screen);
                                                                    pant.setTextoPantalla(printScreen(screen));

                                                                    throw new ExcepcionBaseMsn("Codigo:0010,Pantalla no fue reconocidad en proceso programado por el administrador de procesos");
                                                                }
                                                            }
                                                        } else {
                                                            break;
                                                        }
                                                    } else {
                                                        break;
                                                    }
                                                } else {

                                                    // manejar el accion programada para la expresion Mostrar pantalla o teclear [Enter] u otra tecla.
                                                    Boolean a = true;
                                                    PantallaDto pant = new PantallaDto();
                                                    if (actExp == "i") {

                                                        pant.setTextoPantalla(printScreen(screen));
                                                        throw new ExcepcionBaseMsn("Codigo:0010,Ejecucion de pantalla alternativa");

                                                    }

                                                }
                                            } else {

                                                int longitud = listaActual.size();
                                                if (longitud > (indice + 1)) {
                                                    PantallaDto pantallaSiguiente = listaActual.get(indice + 1);
                                                    if (pantallaSiguiente.getInputs().size() > 0) {
                                                        String texto = pantallaSiguiente.getInputs().get(0).getValue().trim();
                                                        PantallaDto pant = new PantallaDto();
                                                        if (util.comparadorDeCaracteres(pantallaTexto, texto)) {
                                                            pant.setTextoPantalla(printScreen(screen));

                                                            break;
                                                        } else {
                                                            if (operacionesAlternativas(getScreenAsString(screen), listaActual, "oper")) {
                                                                pant.setTextoPantalla(printScreen(screen));

                                                                throw new ExcepcionBaseMsn("Codigo:0010,Ejecucion de pantalla alternativa");
                                                            } else {
                                                                //printScreen2(screen);
                                                                pant.setTextoPantalla(printScreen(screen));

                                                                throw new ExcepcionBaseMsn("Codigo:0010,Pantalla no fue reconocidad en proceso programado por el administrador de procesos");
                                                            }
                                                        }

                                                    } else {
                                                        break;
                                                    }
                                                } else {
                                                    break;
                                                }
                                            }
                                        }
                                    } else {
                                        //emitir una excceion no tiene cantidad de repeticiones 
                                        throw new ExcepcionBaseMsn("Codigo:0020,La expresion de ciclo for no posee numero de iteraciones.");
                                    }

                                case "w":
                                    // segmento de ciclo while de la operaciones
                                    do {
                                        operaciones(dataForm);
                                        int longitud = listaActual.size();
                                        String pantalla = getScreenAsString(screen).trim();
                                        if (expresionId > 0) {
                                            Export expReq = ExpresionesAS4(getScreenAsString(screen).trim(), expresionId);
                                            if (expReq.getFlag()) {
                                                if (longitud > (indice + 1)) {
                                                    PantallaDto flag1 = listaActual.get(indice + 1);
                                                    String texto = flag1.getInputs().get(0).getValue().trim();
                                                    if (util.comparadorDeCaracteres(pantalla, texto)) {
                                                        panti.setTextoPantalla(printScreen(screen));

                                                        flag2 = false;
                                                    } else {
                                                        if (operacionesAlternativas(getScreenAsString(screen), listaActual, "oper")) {
                                                            panti.setTextoPantalla(printScreen(screen));

                                                            throw new ExcepcionBaseMsn("Codigo:0010,error manejado modulo de conexion");
                                                        } else {
                                                            //printScreen2(screen);
                                                            panti.setTextoPantalla(printScreen(screen));

                                                            throw new ExcepcionBaseMsn("Codigo:0010,error en panatalla con manejado");

                                                        }
                                                        /// hacer un for buscando dentro de las pantalla alternativas el texto en pantalla si no se encuentra guardar pantalla en el log.
                                                    }

                                                } else {
                                                    flag2 = false;
                                                }

                                            } else {

                                                Boolean a = true;
                                                PantallaDto pant = new PantallaDto();
                                                if (actExp == "i") {

                                                    pant.setTextoPantalla(printScreen(screen));

                                                    throw new ExcepcionBaseMsn("Codigo:0010,Ejecucion de pantalla alternativa");

                                                }

                                                // manejar el accion programada para la expresion Mostrar pantalla o teclear [Enter] u otra tecla.   
                                            }

                                        } else {

                                            if (longitud > (indice + 1)) {
                                                PantallaDto flag1 = listaActual.get(indice + 1);
                                                String texto = flag1.getInputs().get(0).getValue().trim();
                                                if (util.comparadorDeCaracteres(pantalla, texto)) {
                                                    panti.setTextoPantalla(printScreen(screen));

                                                    flag2 = false;
                                                } else {
                                                    if (operacionesAlternativas(getScreenAsString(screen), listaActual, "oper")) {
                                                        panti.setTextoPantalla(printScreen(screen));

                                                        throw new ExcepcionBaseMsn("Codigo:0010,error manejado modulo de conexion");
                                                    } else {
                                                        //printScreen2(screen);
                                                        panti.setTextoPantalla(printScreen(screen));

                                                        throw new ExcepcionBaseMsn("Codigo:0010,error en panatalla con manejado");

                                                    }
                                                    /// hacer un for buscando dentro de las pantalla alternativas el texto en pantalla si no se encuentra guardar pantalla en el log.
                                                }

                                            } else {
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

                                    if (longitud > (indice + 1)) {
                                        PantallaDto flag1 = listaActual.get(indice + 1);
                                        String texto = flag1.getInputs().get(0).getValue().trim();
                                        if (util.comparadorDeCaracteres(pantalla, texto)) {
                                            panti.setTextoPantalla(printScreen(screen));

                                            flag2 = false;
                                        } else {
                                            if (operacionesAlternativas(getScreenAsString(screen), listaActual, "oper")) {
                                                panti.setTextoPantalla(printScreen(screen));

                                                throw new ExcepcionBaseMsn("Codigo:0010,error manejado modulo de conexion");
                                            } else {
                                                //printScreen2(screen);
                                                panti.setTextoPantalla(printScreen(screen));

                                                throw new ExcepcionBaseMsn("Codigo:0010,error en panatalla con manejado");

                                            }

                                        }

                                    }

                                } else {

                                    Boolean a = true;
                                    PantallaDto pant = new PantallaDto();
                                    if (actExp == "i") {
                                        pant.setTextoPantalla(printScreen(screen));

                                        throw new ExcepcionBaseMsn("Codigo:0010,Ejecucion de pantalla alternativa");
                                    } else if (actExp == "r") {
                                        operaciones(dataForm);
                                        pant.setTextoPantalla(printScreen(screen));

                                    }
                                }
                            } else {
                                if (longitud > (indice + 1)) {
                                    PantallaDto flag1 = listaActual.get(indice + 1);
                                    String texto = flag1.getInputs().get(0).getValue().trim();
                                    if (util.comparadorDeCaracteres(pantalla, texto)) {
                                        panti.setTextoPantalla(printScreen(screen));

                                        flag2 = false;
                                    } else {
                                        if (operacionesAlternativas(getScreenAsString(screen), listaActual, "oper")) {
                                            panti.setTextoPantalla(printScreen(screen));

                                            throw new ExcepcionBaseMsn("Codigo:0010,error manejado modulo de conexion");
                                        } else {
                                            printScreen2(screen);
                                            panti.setTextoPantalla(printScreen(screen));

                                            throw new ExcepcionBaseMsn("Codigo:0010,error en panatalla con manejado");

                                        }
                                    }
                                }
                            }
                        }

                    } else {
                        throw new ExcepcionBaseMsn("Codigo:0002, Error Rota Conexion remota con el servidor AS400");
                    }

                    indice++;

                }
                //listPatalla.add(pantallaDto);
            }
            PantallaDto pant = new PantallaDto();
            pant.setTextoPantalla(printScreen(screen));

            sessions.disconnect();

        } catch (ExcepcionBaseMsn ex) {
            System.err.println(ex.getMessage());
            sessions.disconnect();

            return "";
        } catch (InterruptedException ex) {
            System.err.print(ex.getMessage());
            sessions.disconnect();
            Logger.getLogger(EjecutorController.class.getName()).log(Level.SEVERE, null, ex);

            return "";
        }
        return "";

    }

    public void operaciones(String[] dataForm) {
        ScreenFields sf = screen.getScreenFields();
        try {
            Thread.sleep(3000L);
            for (int i = 7; i < dataForm.length; i++) {
                String datos = dataForm[i];
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
            System.out.println(getScreenAsString(screen));
//            exploreScreenFields(screen);
//            exploreScreenFieldsInputs

        } catch (InterruptedException ex) {
            Logger.getLogger(EjecutorController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
