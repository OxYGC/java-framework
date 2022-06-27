package com.yanggc;

import com.yanggc.service.CanalClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * Description:
 *
 * @author: YangGC
 */
@SpringBootApplication
public class Boot270Application implements CommandLineRunner {

    @Resource
    CanalClient canalClient;

    public static void main(String[] args) {
        SpringApplication.run(Boot270Application.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        canalClient.run();
    }
}
