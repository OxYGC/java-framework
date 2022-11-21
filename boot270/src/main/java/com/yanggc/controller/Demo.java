package com.yanggc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @Author: YangGC
 * DateTime: 11-18
 */
@RefreshScope
@RestController
@RequestMapping("/demo")
public class Demo {
    @Value("${config.version}")
    private String version;

    @Value("${config.platform}")
    private String platform;

    @Resource
    ContextRefresher contextRefresher;

    @Resource
    ApplicationContext applicationContext;

    @GetMapping("/show/version")
    public String showVersion() {
        return "version=" + version + "-platform=" + platform;
    }



    /**
     * 动态刷新配置
     *
     * @return
     */
    @GetMapping("/refresh")
    public String refresh() {
        Map<String, Object> tempProperties = new HashMap<>();
        tempProperties.put("config.version", 20888);
        tempProperties.put("config.platform", "windows");
        MapPropertySource propertySource = new MapPropertySource("dynamic", tempProperties);

        ConfigurableEnvironment contextEnvironment = (ConfigurableEnvironment) applicationContext.getEnvironment();
        //将修改后的配置设置到environment中
        contextEnvironment.getPropertySources().addFirst(propertySource);
        //异步调用refresh方法，避免阻塞一直等待无响应
        new Thread(() -> contextRefresher.refresh()).start();
        return "version=" + version + "-platform=" + platform;
    }


}
