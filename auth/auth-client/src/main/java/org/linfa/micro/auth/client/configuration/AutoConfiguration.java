package org.linfa.micro.auth.client.configuration;

import org.linfa.micro.auth.client.config.ServiceAuthConfig;
import org.linfa.micro.auth.client.config.UserAuthConfig;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"org.linfa.micro.auth.client","org.linfa.micro.auth.common.event"})
@RemoteApplicationEventScan(basePackages = "org.linfa.micro.auth.common.event")
public class AutoConfiguration {
    @Bean
    ServiceAuthConfig getServiceAuthConfig(){
        return new ServiceAuthConfig();
    }

    @Bean
    UserAuthConfig getUserAuthConfig(){
        return new UserAuthConfig();
    }
}
