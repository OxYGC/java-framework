package com.yanggc.annotation.log.resolver;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Description:
 * 服务交互收到报文处理
 * @author: YangGC
 */
@Aspect
@Component
public class InteractRecdLogResolver {

    Logger logger = LoggerFactory.getLogger(InteractRecdLogResolver.class);

    @Pointcut("@annotation(com.yanggc.annotation.log.ServeInteractRecdLog)")
    public void interactRecdLogPointCut() {
        logger.info("收到数据报文");
    }

    @Pointcut("execution(* com..yanggc.controller..*.*(..))")
    public void operExceptionLogPoinCut() {
        logger.error("异常数据报文");
    }


    @Before(value = "interactRecdLogPointCut()")
    public void InteractRecdLogBefore(JoinPoint joinPoint) throws IOException {
        logger.warn("i am before");
        Object[] args = joinPoint.getArgs();
        List<Object> reqArgs = Arrays.asList(args);
        for ( Object  object : reqArgs) {
            logger.info("收到数据报文"+object.toString());
        }
    }


    @AfterReturning(value = "interactRecdLogPointCut()", returning = "returnParams")
    public void InteractRecdLog(JoinPoint joinPoint, Object returnParams) throws IOException {
        Object[] args = joinPoint.getArgs();
        List<Object> reqArgs = Arrays.asList(args);
        for ( Object  object : reqArgs) {
            //todo 数据持久化到 Mongo / InnoDB / ES
            System.out.println("数据持久化...." + object.toString());
        }
        System.out.println(returnParams.toString());
    }

}
