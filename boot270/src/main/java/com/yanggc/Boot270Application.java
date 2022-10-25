package com.yanggc;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Description:
 *
 * @author: YangGC
 */
@EnableBatchProcessing
@SpringBootApplication
public class Boot270Application {

    public static void main(String[] args) {
        SpringApplication.run(Boot270Application.class, args);
    }

}
