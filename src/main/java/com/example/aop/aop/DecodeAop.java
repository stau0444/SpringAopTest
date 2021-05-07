package com.example.aop.aop;


import com.example.aop.dto.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Aspect
@Component
public class DecodeAop {
    //어떤 메서드에 적용할지를 정의하는 메서드
    @Pointcut("execution(* com.example.aop.controller..*.*(..))")
    private void pointCut(){}


    //특정 어노테이션이 붙은 메서드에서만 실행 하도록 지정
    @Pointcut("@annotation(com.example.aop.annotation.Decode)")
    private void enableDecode(){}



    //메소드 실행전에 User정보에서 email를 가져와 암호화 한다.
    @Before("pointCut()&&enableDecode()")
    public void before(JoinPoint joinPoint) throws UnsupportedEncodingException {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            //받아온 데이터의 타입이 User일경우 
            if(arg instanceof User){
                //받아온 데이터를 User로 형 변환하고
                User user = User.class.cast(arg);
                //User에서 email 을 꺼내서
                String baseEmail = user.getEmail();
                //base64로 변환한후에
                String email = new String(Base64.getDecoder().decode(baseEmail),"UTF-8");
                //user에 set해준다.
                user.setEmail(email);

            }
        }
    }

    @AfterReturning(value = "pointCut()&&enableDecode()", returning = "returnObj")
    public void afterReturn(JoinPoint joinPoint , Object returnObj) throws UnsupportedEncodingException {
        if(returnObj instanceof User){
            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                //내보낼 데이터의 타입이 User일경우
                if(arg instanceof User){
                    //내보낼 데이터를 User로 형 변환하고
                    User user = User.class.cast(arg);
                    //User에서 email 을 꺼내서
                    String baseEmail = user.getEmail();
                    //String으로 변환한 후에
                    String email = new String(Base64.getEncoder().encodeToString(baseEmail.getBytes(StandardCharsets.UTF_8)));
                    //user에 set해준다.
                    user.setEmail(email);
                }
            }
        }
    }

}
