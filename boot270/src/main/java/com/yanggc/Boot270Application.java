package com.yanggc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Description:
 *
 * @author: YangGC
 */
@EnableDiscoveryClient
@SpringBootApplication
public class Boot270Application {

    public static void main(String[] args) {
        SpringApplication.run(Boot270Application.class, args);
    }

}
