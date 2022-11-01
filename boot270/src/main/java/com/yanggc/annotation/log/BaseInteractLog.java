package com.yanggc.annotation.log;

/**
 * Description:
 *
 * @Author: YangGC
 * DateTime:10-18
 */
public interface BaseInteractLog<T> {
    void recordLog(T t);
}
