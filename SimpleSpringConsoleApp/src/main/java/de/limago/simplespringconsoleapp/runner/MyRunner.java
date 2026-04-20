package de.limago.simplespringconsoleapp.runner;

import de.limago.simplespringconsoleapp.demo.Demo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class MyRunner implements CommandLineRunner {



    @Override
    public void run(final String... args) throws Exception {
        System.out.println( "Hello World!");
    }
}
