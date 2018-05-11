package org.linfa.sidevar.client.demo.client;


import org.linfa.sidevar.client.demo.entity.Message;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "sidecar-server")
public interface PythonFeignClient {
    //parse param like /message?id=12
    @RequestMapping("/message/{id}")
    List<Message> getMsg(@RequestParam("id") Long id);
    //parse url like /test
    @RequestMapping("/test")
    String getTest();
}
