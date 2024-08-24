package com.aop;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ProfilingAspect {

    @Pointcut("execution(* *.*(..))")
    public void publicOperation(){}

    @Around("publicOperation()")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        Object ret = pjp.proceed();
        System.out.println(pjp.getSignature());
        return ret;
    }
}