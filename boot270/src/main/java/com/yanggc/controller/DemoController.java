package com.yanggc.controller;

import com.yanggc.annotation.Encryption;
import com.yanggc.pojo.DemoResultVO;
import com.yanggc.pojo.base.R;
import com.yanggc.pojo.Staff;
import com.yanggc.pojo.event.StaffEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
public class DemoController {
    @Value("${config.version}")
    private String version;

    @Value("${config.platform}")
    private String platform;

    @Resource
    ContextRefresher contextRefresher;

    @GetMapping("/show/version")
    public String showVersion() {
        return "version=" + version + "-platform=" + platform;
    }

    @Resource
    ApplicationContext applicationContext;

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



    @GetMapping("/test")
    public String test(@RequestParam String param) {


        StaffEvent staffEvent = new StaffEvent(new Staff(1L,param));

        // 发送商品消息
        // this.sendMsg(ProductMsg.MA_UPDATE, productDto.getItemCode());
        // 发布商品变更消息
        staffEvent.pubAppcSiteMsg();


        return param;
    }


    /**
     * 测试返回参数
     * @return
     */
    @Encryption
    @GetMapping("/testEncryptionVO")
    public R<DemoResultVO> testEncryptionVO(){


        return R.("不一样的科技宅");
    }







}
