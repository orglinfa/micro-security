package org.linfa.micro.auth.client.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.linfa.micro.auth.client.config.UserAuthConfig;
import org.linfa.micro.auth.client.exception.JwtIllegalArgumentException;
import org.linfa.micro.auth.client.exception.JwtSignatureException;
import org.linfa.micro.auth.client.exception.JwtTokenExpiredException;
import org.linfa.micro.auth.common.util.jwt.IJWTInfo;
import org.linfa.micro.auth.common.util.jwt.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserAuthUtil {
    @Autowired
    private UserAuthConfig userAuthConfig;
    public IJWTInfo getInfoFromToken(String token) throws Exception {
        try {
            return JWTHelper.getInfoFromToken(token, userAuthConfig.getPubKeyPath());
        }catch (ExpiredJwtException ex){
            throw new JwtTokenExpiredException("User token expired!");
        }catch (SignatureException ex){
            throw new JwtSignatureException("User token signature error!");
        }catch (IllegalArgumentException ex){
            throw new JwtIllegalArgumentException("User token is null or empty!");
        }
    }
}
