package org.linfa.micro.auth.server.feign;

import org.linfa.micro.api.vo.user.UserInfo;
import org.linfa.micro.auth.server.configuration.FeignConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(value = "micro-admin",configuration = FeignConfiguration.class)
public interface IUserService {
    @RequestMapping(value = "/api/user/validate", method = RequestMethod.POST)
    public UserInfo validate(@RequestParam("username") String username, @RequestParam("password") String password);
}
