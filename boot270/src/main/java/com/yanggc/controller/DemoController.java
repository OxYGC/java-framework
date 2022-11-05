package com.yanggc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @Author: YangGC
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/test/{req}")
    public String test(@PathVariable("req") String req) {
        return "server received params: "+req;
    }

}
