package com.lh.jspj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lh.jspj.mapper")
public class JspjApplication {

    public static void main(String[] args) {
        SpringApplication.run(JspjApplication.class, args);
    }

}
