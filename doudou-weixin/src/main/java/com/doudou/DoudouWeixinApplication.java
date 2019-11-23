package com.doudou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
/**
 * 在SpringBootApplication上使用@ServletComponentScan注解后，
 * Servlet、Filter、Listener可以直接通过@WebServlet、@WebFilter、
 * \@WebListener注解 自动注册，无需其他代码。
 */
@SpringBootApplication
@ServletComponentScan
public class DoudouWeixinApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoudouWeixinApplication.class, args);
    }

}
