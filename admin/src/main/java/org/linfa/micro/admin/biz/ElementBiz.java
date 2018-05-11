package org.linfa.micro.admin.biz;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import org.linfa.micro.admin.entity.Element;
import org.linfa.micro.admin.mapper.ElementMapper;
import org.linfa.micro.common.biz.BaseBiz;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ElementBiz extends BaseBiz<ElementMapper,Element> {
    @Cache(key="permission:ele:u{1}")
    public List<Element> getAuthorityElementByUserId(String userId){
        return mapper.selectAuthorityElementByUserId(userId);
    }
    public List<Element> getAuthorityElementByUserId(String userId,String menuId){
        return mapper.selectAuthorityMenuElementByUserId(userId,menuId);
    }

    @Override
    @Cache(key="permission:ele")
    public List<Element> selectListAll() {
        return super.selectListAll();
    }

    @Override
    @CacheClear(keys={"permission:ele","permission"})
    public void insertSelective(Element entity) {
        super.insertSelective(entity);
    }

    @Override
    @CacheClear(keys={"permission:ele","permission"})
    public void updateSelectiveById(Element entity) {
        super.updateSelectiveById(entity);
    }
}
