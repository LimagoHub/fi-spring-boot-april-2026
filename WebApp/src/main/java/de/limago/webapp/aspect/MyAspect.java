package de.limago.webapp.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@Aspect
public class MyAspect {

    private static Logger logger = Logger.getLogger(MyAspect.class.getName());

    @Before(value="execution(public * de.limago.webapp.presentation.controller.PersonenController.*(..))")
    public void beforeAdvice(JoinPoint joinPoint) {
        logger.warning( String.format(
                "##################### Methode  %s wurde aufgerufen ########################"
                , joinPoint.getSignature().getName()));
    }

    @AfterReturning(value="execution(public * de.limago.webapp.presentation.controller.PersonenController.*(..))",returning = "result")
    public void afterReturningAdvice(JoinPoint joinPoint, Object result) {
        logger.warning(String.format("############################# Afterreturning: %s ######################", joinPoint.getSignature().getName()));
        logger.warning(String.format("############################# Result: %s ######################", result.toString()));
    }
    @AfterThrowing(value="execution(public * de.limago.webapp.presentation.controller.PersonenController.*(..))", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        logger.warning(String.format("############################# Afterreturning: %s ######################", joinPoint.getSignature().getName()));
        logger.warning(String.format("############################# Exception: %s ######################", ex.toString()));
    }

    @After(value="execution(public * de.limago.webapp.presentation.controller.PersonenController.*(..))")
    public void after(JoinPoint joinPoint) {
        logger.warning(String.format("############################# After: %s ######################", joinPoint.getSignature().getName()));

    }

    @Around(value="execution(public * de.limago.webapp.presentation.controller.PersonenController.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        
        return  joinPoint.proceed();

    }





}
