package de.limago.simplespringconsoleapp.runner;

import de.limago.simplespringconsoleapp.demo.Demo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class MyRunner implements CommandLineRunner {

    private final Demo demo;

    public MyRunner(final Demo demo) {
        this.demo = demo;
    }

    @Override
    public void run(final String... args) throws Exception {
        System.out.println( "Hello World!");
    }
}
