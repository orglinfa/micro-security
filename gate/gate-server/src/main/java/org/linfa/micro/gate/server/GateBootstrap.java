package org.linfa.micro.gate.server;

import org.linfa.micro.gate.ratelimit.EnableAceGateRateLimit;
import org.linfa.micro.gate.ratelimit.config.IUserPrincipal;
import org.linfa.micro.gate.server.config.UserPrincipal;
import org.linfa.micro.gate.server.utils.DBLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients({"org.linfa.micro.auth.client.feign","org.linfa.micro.gate.server.feign"})
@EnableZuulProxy
@EnableScheduling
@EnableAceGateRateLimit
public class GateBootstrap {
    public static void main(String[] args) {
        DBLog.getInstance().start();
        SpringApplication.run(GateBootstrap.class, args);
    }

    @Bean
    @Primary
    IUserPrincipal userPrincipal(){
        return new UserPrincipal();
    }
}
