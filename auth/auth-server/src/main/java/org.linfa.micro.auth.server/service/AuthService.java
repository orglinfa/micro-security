package org.linfa.micro.auth.server.service;

public interface AuthService {
    String login(String username, String password) throws Exception;
    String refresh(String oldToken);
    void validate(String token) throws Exception;
    Boolean invalid(String token);
}
