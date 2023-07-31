package ru.spring.school.online.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(0)
@Log4j2
public class LoggingAspect {

    @Before("ru.spring.school.online.aspect.PointcutDescr.allControllerGetMethods()")
    public void beforeAllControllerGetMethodsLoggingAdvice(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        log.info("{}.{}: some one is trying to {}", methodSignature.getDeclaringTypeName(), methodSignature.getName(), methodSignature.getName());
    }
}
