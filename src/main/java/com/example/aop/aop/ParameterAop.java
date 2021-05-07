package com.example.aop.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class ParameterAop {

    //어떤 메서드에 적용할지를 정의하는 메서드
    @Pointcut("execution(* com.example.aop.controller..*.*(..))")
    private void pointCut(){}

    @Before("pointCut()")
    public void before(JoinPoint joinPoint){

        //어떤 메서드가 사용됬는지 정보를 methodSignature로  확인할 수 있다 .
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        System.out.println(method.getName());

        //들어오는 데이터를 배열에 담는다.
        Object [] args = joinPoint.getArgs();

        for (Object obj : args) {
            System.out.println("method:" + obj.getClass().getSimpleName());
            System.out.println("value:" + obj);
        }
    }

    //value 는 어떤 메서드에서 수행할지 지정하며 , returning은 확인이 필요한 객체를 지정한다.
    @AfterReturning(value = "pointCut()",returning = "obj")
    public void afterReturn(JoinPoint joinPoint , Object obj){
        System.out.println("return Obj");
        System.out.println(obj);
    }

}
