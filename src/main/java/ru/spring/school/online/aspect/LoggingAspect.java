package ru.spring.school.online.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import ru.spring.school.online.model.security.User;

import java.util.Arrays;

@Component
@Aspect
@Order(0)
@Log4j2
public class LoggingAspect {

    @Before("ru.spring.school.online.aspect.PointcutDescr.allControllerGetMethods()")
    public void beforeAllControllerGetMethodsLoggingAdvice(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        log.debug("{}.{}: someone is trying to {}", methodSignature.getDeclaringTypeName(), methodSignature.getName(), methodSignature.getName());
    }

    @Before("ru.spring.school.online.aspect.PointcutDescr.allControllerProcessMethods()")
    public void beforeAllControllerProcessMethodsLoggingAdvice(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        log.debug("{}.{}: processing", methodSignature.getDeclaringTypeName(), methodSignature.getName());
    }

    @AfterReturning(value = "ru.spring.school.online.aspect.PointcutDescr.allControllerProcessMethods()", returning = "page")
    public void afterReturningAllControllerProcessMethodsLoggingAdvice(JoinPoint joinPoint, Object page) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        for (Object arg:args){
            if (arg instanceof User user){
                log.info("Current user {}", user);
            }

            if (arg instanceof Errors errors){
                if (errors.hasErrors()){
                    log.warn("Got validation exceptions: {}", errors);
                }
            }

            if (arg instanceof Model model){
                log.debug("Current model args:\n{}", model.asMap());
            }
        }
        log.debug("New page: {}", page);
        log.debug("{}.{}: finished", methodSignature.getDeclaringTypeName(), methodSignature.getName());
    }

    @Before("ru.spring.school.online.aspect.PointcutDescr.allRepositoryFindMethods()")
    public void beforeAllRepositoryFindMethodsLoggingAdvice(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        log.debug("{}.{}: trying to find", methodSignature.getDeclaringTypeName(), methodSignature.getName());
        log.debug("Current args: {}", Arrays.toString(joinPoint.getArgs()));
    }

    @Before("ru.spring.school.online.aspect.PointcutDescr.allRepositorySaveMethods()")
    public void beforeAllRepositorySaveMethodsLoggingAdvice(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        log.debug("{}.{}: trying to save", methodSignature.getDeclaringTypeName(), methodSignature.getName());
        log.debug("Current args: {}", Arrays.toString(joinPoint.getArgs()));
    }

    @Before("ru.spring.school.online.aspect.PointcutDescr.allRepositorySaveMethods()")
    public void beforeAllRepositoryDeleteMethodsLoggingAdvice(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        log.debug("{}.{}: trying to delete", methodSignature.getDeclaringTypeName(), methodSignature.getName());
        log.debug("Current args: {}", Arrays.toString(joinPoint.getArgs()));
    }

}
