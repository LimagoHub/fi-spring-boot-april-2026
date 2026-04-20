package de.limago.simplespringconsoleapp.translator;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
//@Primary
//@Qualifier("lower")
@Profile("production")
public class ToLowerTransLator implements Translator{

    @Override
    public String translate(final String text) {
        return text.toLowerCase();
    }
}
