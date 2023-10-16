package ru.spring.school.online.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class PointcutDescr {

    @Pointcut("execution(* ru.spring.school.online.controller..*.get*(..))")
    public void allControllerGetMethods() {
    }

    @Pointcut("execution(* ru.spring.school.online.controller..*.process*(..))")
    public void allControllerProcessMethods() {
    }

    @Pointcut("execution(* ru.spring.school.online.repository..*.find*(..))")
    public void allRepositoryFindMethods() {
    }

    @Pointcut("execution(* ru.spring.school.online.repository..*.save*(..))")
    public void allRepositorySaveMethods() {
    }

    @Pointcut("execution(* ru.spring.school.online.repository..*.delete*(..))")
    public void allRepositoryDeleteMethods() {
    }
}
