package org.linfa.micro.auth.client.feign;

import org.linfa.micro.common.msg.ObjectRestResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@FeignClient(value = "${auth.serviceId}",configuration = {})
public interface ServiceAuthFeign {
    @RequestMapping(value = "/client/myClient")
    public ObjectRestResponse<List<String>> getAllowedClient(@RequestParam("serviceId") String serviceId, @RequestParam("secret") String secret);
    @RequestMapping(value = "/client/token",method = RequestMethod.POST)
    public ObjectRestResponse getAccessToken(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret);

}
