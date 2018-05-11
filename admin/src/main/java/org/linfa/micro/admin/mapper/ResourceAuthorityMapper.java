package org.linfa.micro.admin.mapper;

import org.apache.ibatis.annotations.Param;
import org.linfa.micro.admin.entity.ResourceAuthority;
import tk.mybatis.mapper.common.Mapper;

/**
 *
 */
public interface ResourceAuthorityMapper extends Mapper<ResourceAuthority> {
    /**
     * 根据权限id和资源类型删除权限
     * @param authorityId
     * @param resourceType
     */
    public void deleteByAuthorityIdAndResourceType(@Param("authorityId") String authorityId, @Param("resourceType") String resourceType);

}
