package com.yanggc.event;

import com.yanggc.pojo.event.StaffEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

/**
 * 本地监听服务
 */
@Slf4j
public class StaffEventListener implements ApplicationListener<StaffEvent> {


    @Override
    public void onApplicationEvent(StaffEvent event) {

    }



}
