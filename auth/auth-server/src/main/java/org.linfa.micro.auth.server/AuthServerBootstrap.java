package org.linfa.micro.auth.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@MapperScan("org.linfa.micro.auth.server.mapper")
@RemoteApplicationEventScan(basePackages = "org.linfa.micro.auth.common.event")
public class AuthServerBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(AuthServerBootstrap.class, args);
    }
}
