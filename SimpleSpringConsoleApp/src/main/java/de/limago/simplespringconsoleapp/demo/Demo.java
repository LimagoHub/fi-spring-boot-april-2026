package de.limago.simplespringconsoleapp.demo;


import de.limago.simplespringconsoleapp.translator.Translator;
import jakarta.annotation.PostConstruct;

import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component

//@Scope("singleton")  // wird beim Programmstart erzeugt und lebt bis Programm zerstoert wird
//@Lazy
@Scope("prototype")
public class Demo {


    private final Translator translator;


    // AutoWiring by Type
    // im Konfliktfall zusaetzlich by Name
    public Demo( Translator translator) {
        this.translator = translator;
        System.out.println(translator.translate("Ctor Demo"));
    }

    @PostConstruct
    public void init() {
        System.out.println(translator.translate("Hallo Welt!"));
    }

    @PreDestroy
    public void close() {
        System.out.println("Demo closed");
    }
}
