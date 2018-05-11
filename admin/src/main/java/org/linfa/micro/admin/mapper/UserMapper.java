package org.linfa.micro.admin.mapper;

import org.apache.ibatis.annotations.Param;
import org.linfa.micro.admin.entity.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 *
 */
public interface UserMapper extends Mapper<User> {
    /**
     * 根据分组id 获取用户列表
     * @param groupId
     * @return
     */
    public List<User> selectMemberByGroupId(@Param("groupId") int groupId);

    /**
     * 根据分组id获取组长信息
     * @param groupId
     * @return
     */
    public List<User> selectLeaderByGroupId(@Param("groupId") int groupId);
}
