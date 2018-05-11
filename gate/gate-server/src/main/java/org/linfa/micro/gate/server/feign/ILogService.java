package org.linfa.micro.gate.server.feign;

import org.linfa.micro.api.vo.log.LogInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("micro-admin")
public interface ILogService {
    @RequestMapping(value="/api/log/save",method = RequestMethod.POST)
    public void saveLog(LogInfo info);
}
