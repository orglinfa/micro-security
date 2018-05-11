package org.linfa.micro.auth.client.exception;

public class JwtSignatureException extends Exception {
    public JwtSignatureException(String s){
        super(s);
    }
}
