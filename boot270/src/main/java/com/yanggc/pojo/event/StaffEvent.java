package com.yanggc.pojo.event;

import com.yanggc.pojo.Staff;
import com.yanggc.util.ApplicationContextHolder;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;


/**
 * 员工事件
 */
@Slf4j
@ToString
public class StaffEvent extends ApplicationEvent {

    Logger logger = LoggerFactory.getLogger(StaffEvent.class);


    /**
     * 消息类型：更新用户，值为: {@value}
     */
    public static final String ET_UPDATE = "update";


    private Staff staff;
    private String action;


    /**
     * 发布事件 (发送C端站内信)
     */
    public void pubAppcSiteMsg() {
        ApplicationContext context = ApplicationContextHolder.getApplicationContext();
        if (null != context) {
            logger.debug("发布事件：{}", this);
            context.publishEvent(this);
        } else {
            logger.warn("无法获取到当前Spring上下文信息，不能够发布事件");
        }
    }




    public StaffEvent(Staff staff) {
        super(staff);
        this.staff = staff;
    }


    public StaffEvent(Staff staff, String action) {

        super(staff);
        this.staff = staff;
    }


    public StaffEvent(Object source, Clock clock) {
        super(source, clock);
    }


    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
