package org.linfa.micro.auth.client.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.linfa.micro.auth.client.config.ServiceAuthConfig;
import org.linfa.micro.auth.client.exception.JwtIllegalArgumentException;
import org.linfa.micro.auth.client.exception.JwtSignatureException;
import org.linfa.micro.auth.client.exception.JwtTokenExpiredException;
import org.linfa.micro.auth.client.feign.ServiceAuthFeign;
import org.linfa.micro.auth.common.event.AuthRemoteEvent;
import org.linfa.micro.auth.common.util.jwt.IJWTInfo;
import org.linfa.micro.auth.common.util.jwt.JWTHelper;
import org.linfa.micro.common.msg.BaseResponse;
import org.linfa.micro.common.msg.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration //用于定义配置类，可替换xml文件
@Slf4j
@EnableScheduling //快速启动任务调动功能，提供基于多种规则的任务调用功能
public class ServiceAuthUtil implements ApplicationListener<AuthRemoteEvent> {
    @Autowired
    private ServiceAuthConfig serviceAuthConfig;
    @Autowired
    private ServiceAuthFeign serviceAuthFeign;
    private List<String> allowedClient;
    private String clientToken;


    public IJWTInfo getInfoFromToken(String token) throws Exception {
        try {
            return JWTHelper.getInfoFromToken(token, serviceAuthConfig.getPubKeyPath());
        } catch (ExpiredJwtException ex) {
            throw new JwtTokenExpiredException("Client token expired!");
        } catch (SignatureException ex) {
            throw new JwtSignatureException("Client token signature error!");
        } catch (IllegalArgumentException ex) {
            throw new JwtIllegalArgumentException("Client token is null or empty!");
        }
    }

    public void refreshAllowedClient() {
        log.info("refresh allowedClient.....");
        BaseResponse resp = serviceAuthFeign.getAllowedClient(serviceAuthConfig.getClientId(), serviceAuthConfig.getClientSecret());
        if (resp.getStatus() == 200) {
            ObjectRestResponse<List<String>> allowedClient = (ObjectRestResponse<List<String>>) resp;
            this.allowedClient = allowedClient.getData();
        }
    }


    @Scheduled(cron = "0 0 0/1 * * ?")
    public void refreshClientToken() {
        log.info("refresh client token.....");
        BaseResponse resp = serviceAuthFeign.getAccessToken(serviceAuthConfig.getClientId(), serviceAuthConfig.getClientSecret());
        if (resp.getStatus() == 200) {
            ObjectRestResponse<String> clientToken = (ObjectRestResponse<String>) resp;
            this.clientToken = clientToken.getData();
        }
    }


    public String getClientToken() {
        if (this.clientToken == null) {
            this.refreshClientToken();
        }
        return clientToken;
    }

    public List<String> getAllowedClient() {
        if (this.allowedClient == null) {
            this.refreshAllowedClient();
        }
        return allowedClient;
    }

    @Override
    public void onApplicationEvent(AuthRemoteEvent authRemoteEvent) {
        this.allowedClient = authRemoteEvent.getAllowedClient();
    }
}
