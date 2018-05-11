package org.linfa.micro.cache.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.linfa.micro.cache.api.CacheAPI;
import org.linfa.micro.cache.config.RedisConfig;
import org.linfa.micro.cache.entity.CacheBean;
import org.linfa.micro.cache.service.ICacheManager;
import org.linfa.micro.cache.vo.CacheTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CacheManagerImpl implements ICacheManager {

    @Autowired
    private Environment env;
    @Autowired
    private CacheAPI cacheAPI;
    @Autowired
    private RedisConfig redisConfig;

    public void removeAll()
    {
        this.cacheAPI.removeByPre(this.redisConfig.getSysName());
    }

    public void remove(String key)
    {
        this.cacheAPI.remove(key);
    }

    public void remove(List<CacheBean> caches)
    {
        String[] keys = new String[caches.size()];
        int i = 0;
        for (CacheBean cache : caches)
        {
            keys[i] = cache.getKey();
            i++;
        }
        this.cacheAPI.remove(keys);
    }

    public void removeByPre(String pre)
    {
        if (!pre.contains(this.redisConfig.getSysName())) {
            pre = this.redisConfig.getSysName() + ":" + pre + "*";
        }
        this.cacheAPI.removeByPre(pre);
    }

    public List<CacheTree> getAll()
    {
        List<CacheBean> caches = this.cacheAPI.listAll();
        List<CacheTree> cts = toTree(caches);
        return cts;
    }

    private List<CacheTree> toTree(List<CacheBean> caches)
    {
        List<CacheTree> result = new ArrayList();
        Set<CacheTree> cts = new HashSet();
        CacheTree ct = new CacheTree();
        for (CacheBean cache : caches)
        {
            String[] split = cache.getKey().split(":");
            for (int i = split.length - 1; i > 0; i--)
            {
                if (i == split.length - 1) {
                    ct = new CacheTree(cache);
                } else {
                    ct = new CacheTree();
                }
                if (i - 1 >= 0)
                {
                    ct.setId(split[i]);
                    ct.setParentId(split[(i - 1)].endsWith(this.redisConfig.getSysName()) ? "-1" : split[(i - 1)]);
                }
                if (split.length == 2) {
                    cts.remove(ct);
                }
                cts.add(ct);
            }
        }
        result.addAll(cts);
        return result;
    }

    public List<CacheTree> getByPre(String pre)
    {
        if (StringUtils.isBlank(pre)) {
            return getAll();
        }
        if (!pre.contains(this.redisConfig.getSysName())) {
            pre = this.redisConfig.getSysName() + "*" + pre;
        }
        return toTree(this.cacheAPI.getCacheBeanByPre(pre));
    }

    public CacheAPI getCacheAPI()
    {
        return this.cacheAPI;
    }

    public void setCacheAPI(CacheAPI cacheAPI)
    {
        this.cacheAPI = cacheAPI;
    }

    public void update(String key, int hour)
    {
        String value = this.cacheAPI.get(key);
        this.cacheAPI.set(key, value, hour * 60);
    }

    public void update(List<CacheBean> caches, int hour)
    {
        for (CacheBean cache : caches)
        {
            String value = this.cacheAPI.get(cache.getKey());
            this.cacheAPI.set(cache.getKey(), value, hour);
        }
    }

    public String get(String key)
    {
        return this.cacheAPI.get(key);
    }
}
