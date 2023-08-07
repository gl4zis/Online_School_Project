package ru.spring.school.online.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class PointcutDescr {

    @Pointcut("execution(* ru.spring.school.online.controller..*.get*(..))")
    public void allControllerGetMethods() {
    }
}
