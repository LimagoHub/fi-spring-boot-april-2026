package de.limago.webapp.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(public * de.limago.webapp.presentation.controller.PersonenController.*(..))")
    public void personControllerMethod(){}
}
