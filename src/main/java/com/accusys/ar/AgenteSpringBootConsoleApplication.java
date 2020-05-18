package com.accusys.ar;

import com.accusys.ar.controller.EjecutorController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.lang.System.exit;

@SpringBootApplication

public class AgenteSpringBootConsoleApplication implements CommandLineRunner {

    @Autowired
    EjecutorController ejecutor;

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(AgenteSpringBootConsoleApplication.class);
        app.setBannerMode(Banner.Mode.OFF);

        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        String[] nombres = {"transaccion-ejemplo1-1589511212428.json"};//,"P1-F2:ACCUSYS"
        args = nombres;
        if (args.length > 0) {
            ejecutor.importarTransaccion(args);
        } else {
            System.err.println("Codigo:0001 No hay variables de inicio de operacion favor verificar  ");
        }
        exit(0);
    }
}
