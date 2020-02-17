package com.accusys.ar;

import com.accusys.ar.controller.EjecutorController;
import com.accusys.ar.service.HelloMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.lang.System.exit;

@SpringBootApplication
public class AgenteSpringBootConsoleApplication implements CommandLineRunner {

    @Autowired
    private HelloMessageService helloService;
    @Autowired
    EjecutorController ejecutor;

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(AgenteSpringBootConsoleApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
       
        
        ejecutor.importarTransaccion(args);
 

        exit(0);
    }
}