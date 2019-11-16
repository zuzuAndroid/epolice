package com.zygh.webapi.config;

import com.zygh.webapi.component.SqlStatementInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan({"com.zygh.webapi.dao","mapper"})
@Profile({"dev"})
public class MyBatisConfig {

    SqlStatementInterceptor sqlStatementInterceptor() {
        return new SqlStatementInterceptor();
    }
}
