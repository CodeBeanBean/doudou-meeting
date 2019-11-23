package com.doudou.project.weixin.api.accesstoken;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.EventListener;

/**
 * @Description:
 * @Company:
 * @Author: 窦刘柱
 * @Date:2019/11/22
 * @Time:19:34
 */
@WebListener
public class AccessTokenListener implements ServletContextListener {
     @Override
     public void contextInitialized(ServletContextEvent sce) {
         System.out.println("项目启动");
         new AccessTokenThread().start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
