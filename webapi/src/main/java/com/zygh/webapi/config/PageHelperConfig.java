package com.zygh.webapi.config;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class PageHelperConfig {

    @Bean
    public PageHelper getPageHelper(){

        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();

        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");

        //pageHelper.setProperties(properties);

        PageInterceptor interceptor = new PageInterceptor();
        interceptor.setProperties(properties);

        //添加插件
        new SqlSessionFactoryBean().setPlugins(new Interceptor[]{ interceptor });

        return pageHelper;
    }
}
