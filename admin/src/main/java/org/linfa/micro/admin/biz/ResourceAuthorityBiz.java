package org.linfa.micro.admin.biz;


import org.linfa.micro.admin.entity.ResourceAuthority;
import org.linfa.micro.admin.mapper.ResourceAuthorityMapper;
import org.linfa.micro.common.biz.BaseBiz;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ResourceAuthorityBiz extends BaseBiz<ResourceAuthorityMapper,ResourceAuthority> {
}
