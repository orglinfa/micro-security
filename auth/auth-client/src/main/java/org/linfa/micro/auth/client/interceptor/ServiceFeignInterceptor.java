package org.linfa.micro.auth.client.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.linfa.micro.auth.client.config.ServiceAuthConfig;
import org.linfa.micro.auth.client.config.UserAuthConfig;
import org.linfa.micro.auth.client.jwt.ServiceAuthUtil;
import org.linfa.micro.common.context.BaseContextHandler;
import org.springframework.beans.factory.annotation.Autowired;
@Slf4j
public class ServiceFeignInterceptor implements RequestInterceptor {
//    private Logger logger = LoggerFactory.getLogger(ServiceFeignInterceptor.class);
    @Autowired
    private ServiceAuthConfig serviceAuthConfig;
    @Autowired
    private UserAuthConfig userAuthConfig;
    @Autowired
    private ServiceAuthUtil serviceAuthUtil;

    public ServiceFeignInterceptor() {
    }


    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(serviceAuthConfig.getTokenHeader(), serviceAuthUtil.getClientToken());
        requestTemplate.header(userAuthConfig.getTokenHeader(), BaseContextHandler.getToken());
    }

    public void setServiceAuthConfig(ServiceAuthConfig serviceAuthConfig) {
        this.serviceAuthConfig = serviceAuthConfig;
    }

    public void setUserAuthConfig(UserAuthConfig userAuthConfig) {
        this.userAuthConfig = userAuthConfig;
    }

    public void setServiceAuthUtil(ServiceAuthUtil serviceAuthUtil) {
        this.serviceAuthUtil = serviceAuthUtil;
    }
}
