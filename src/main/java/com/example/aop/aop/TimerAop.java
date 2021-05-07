package com.example.aop.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class TimerAop {

    //어떤 메서드에 적용할지를 정의하는 메서드
    @Pointcut("execution(* com.example.aop.controller..*.*(..))")
    private void pointCut(){}


    //특정 어노테이션이 붙은 메서드에서만 실행 하도록 지정
    @Pointcut("@annotation(com.example.aop.annotation.Timer)")
    private void enableTimer(){}

    //위의 두가지 조건을 같이 쓸때 아래와 같이 한다.
    //ProceedingJoinPoint는 해당 조인포인트에 대한 정보이며
    //around는 메서드 실행 전 후이기 떄문에
    //proceed()는 해당 메서드의 실행 시점을 의미하며
    //메서드가 리턴되는 것이 있다면 Object로 받을 수 있다 .

    @Around("pointCut() && enableTimer()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {

        //실행전
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        //실행시점
        Object result = joinPoint.proceed();
    
        //실행후
        stopWatch.stop();
        System.out.println("total time : " + stopWatch.getTotalTimeSeconds());
    }

}
