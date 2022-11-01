package com.yanggc.annotation.log;

import java.lang.annotation.*;

/**
 * Description:
 * 服务交互日志
 * @Author: Yanggc
 * DateTime:10-15
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServeInteractRecdLog {

}