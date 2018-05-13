package com.alibaba.dubbo.demo.consumer;


import com.alibaba.dubbo.demo.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class DemoConsumer {
    public static void main(String[] args) throws IOException {
        //Prevent to get IPV6 address,this way only work in debug mode
        //But you can pass use -Djava.net.preferIPv4Stack=true,then it work well whether in debug mode or not
        System.setProperty("java.net.preferIPv4Stack", "true");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/dubbo-demo-consumer.xml"});
        context.start();
        DemoService orderService = (DemoService) context.getBean("demoService"); // get remote service proxy

       orderService.sayHello("hello");
        System.in.read();
    }
}
