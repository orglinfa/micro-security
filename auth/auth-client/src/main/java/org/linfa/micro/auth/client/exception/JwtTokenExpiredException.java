package org.linfa.micro.auth.client.exception;

public class JwtTokenExpiredException extends Exception {
    public JwtTokenExpiredException(String s){
        super(s);
    }
}
