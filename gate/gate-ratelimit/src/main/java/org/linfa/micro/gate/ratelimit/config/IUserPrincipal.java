package org.linfa.micro.gate.ratelimit.config;

import javax.servlet.http.HttpServletRequest;

public interface IUserPrincipal {
    String getName(HttpServletRequest request);
}
