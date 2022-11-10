package com.yanggc.pojo;

/**
 * Description:
 *
 * @author: YangGC
 */
public class Test {
    public static void main(String[] args) {
        Thread thread2;
        Thread thread1;
        thread2= new Thread(() -> {
            synchronized (Test.class) {
                try {
                    System.out.println("hello world2 start");
                    Test.class.notify();
                    Thread.sleep(10000L);
                    System.out.println("hello world2 end");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread1 = new Thread(() -> {
            synchronized (Test.class) {
                try {
                    thread2.start();
                    System.out.println("hello world1 start");
                    Test.class.wait();
                    System.out.println("hello world1 end");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread1.start();
    }
}
