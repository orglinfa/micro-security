package org.linfa.micro.admin.biz;

import org.linfa.micro.admin.entity.GroupType;
import org.linfa.micro.admin.mapper.GroupTypeMapper;
import org.linfa.micro.common.biz.BaseBiz;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class GroupTypeBiz extends BaseBiz<GroupTypeMapper,GroupType> {
}
