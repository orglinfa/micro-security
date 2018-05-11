package org.linfa.micro.cache.api;

import org.linfa.micro.cache.entity.CacheBean;

import java.util.List;

public abstract interface CacheAPI {
    public abstract String get(String paramString);

    public abstract void set(String paramString, Object paramObject, int paramInt);

    public abstract void set(String paramString1, Object paramObject, int paramInt, String paramString2);

    public abstract Long remove(String paramString);

    public abstract Long remove(String... paramVarArgs);

    public abstract Long removeByPre(String paramString);

    public abstract List<CacheBean> getCacheBeanByPre(String paramString);

    public abstract List<CacheBean> listAll();

    public abstract boolean isEnabled();

    public abstract String addSys(String paramString);
}
