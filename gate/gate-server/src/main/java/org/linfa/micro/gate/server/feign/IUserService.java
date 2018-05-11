package org.linfa.micro.gate.server.feign;

import feign.Param;
import feign.RequestLine;
import org.linfa.micro.api.vo.authority.PermissionInfo;

import java.util.List;

public interface IUserService {
    @RequestLine(value = "GET /api/user/un/{username}/permissions")
    public List<PermissionInfo> getPermissionByUsername(@Param("username") String username);
    @RequestLine(value = "GET /api/permissions")
    List<PermissionInfo> getAllPermissionInfo();
}
