package org.linfa.micro.auth.server.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.linfa.micro.auth.server.configuration.ClientConfig;
import org.linfa.micro.auth.server.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientTokenInterceptor implements RequestInterceptor {
    private Logger logger = LoggerFactory.getLogger(ClientTokenInterceptor.class);
    @Autowired
    private ClientConfig clientConfig;
    @Autowired
    private ClientService clientService;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        try {
            requestTemplate.header(clientConfig.getClientTokenHeader(), clientService.apply(clientConfig.getClientId(), clientConfig.getClientSecret()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
