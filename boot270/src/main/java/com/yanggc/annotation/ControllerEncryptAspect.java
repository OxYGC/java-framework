package com.yanggc.annotation;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Component
@Aspect
public class ControllerEncryptAspect {


    @Around("@annotation(com.yanggc.annotation.Encryption)")
    public Object encryptPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        Class<?> aClass = joinPoint.proceed().getClass();
        Object Obj = aClass.newInstance();
        // 获取注解
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Encryption annotation = method.getAnnotation(Encryption.class);

        // 如果被标识了，则进行加密
        if (annotation != null) {
            // 进行加密
            Field phoneNo = aClass.getField("phoneNo");
            phoneNo.set(Obj, "*****");
//            String encrypt = EncryptUtil.encryptByAes(JSON.toJSONString(resultVo.getData()));
//            resultVo.setData(encrypt);
        }
        return Obj;
    }

}
