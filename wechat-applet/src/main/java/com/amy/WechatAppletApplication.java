package com.amy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@MapperScan("com.amy.**.mapper")
public class WechatAppletApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatAppletApplication.class, args);
    }

}
