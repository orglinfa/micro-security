package org.linfa.micro.cache.service;

import org.linfa.micro.cache.entity.CacheBean;
import org.linfa.micro.cache.vo.CacheTree;

import java.util.List;

public abstract interface ICacheManager {
    public abstract void removeAll();

    public abstract void remove(String paramString);

    public abstract void remove(List<CacheBean> paramList);

    public abstract void removeByPre(String paramString);

    public abstract List<CacheTree> getAll();

    public abstract List<CacheTree> getByPre(String paramString);

    public abstract void update(String paramString, int paramInt);

    public abstract void update(List<CacheBean> paramList, int paramInt);

    public abstract String get(String paramString);
}
