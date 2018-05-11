package org.linfa.micro.cache.api.impl;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.linfa.micro.cache.api.CacheAPI;
import org.linfa.micro.cache.config.RedisConfig;
import org.linfa.micro.cache.entity.CacheBean;
import org.linfa.micro.cache.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CacheRedis implements CacheAPI{
    @Autowired
    private RedisConfig redisConfig;
    @Autowired
    private IRedisService redisCacheService;

    public String get(String key)
    {
        if (!isEnabled()) {
            return null;
        }
        if (StringUtils.isBlank(key)) {
            return null;
        }
        CacheBean cache = getCacheBean(key);
        if (cache != null)
        {
            if (cache.getExpireTime().getTime() > new Date().getTime()) {
                return this.redisCacheService.get(cache.getKey());
            }
            this.redisCacheService.del(new String[] { addSys(key) });
            this.redisCacheService.del(new String[] { cache.getKey() });
        }
        return null;
    }

    public void set(String key, Object value, int expireMin)
    {
        set(key, value, expireMin, "");
    }

    /**
     * 根据key移除value
     * @param key
     * @return
     */
    public Long remove(String key)
    {
        if (!isEnabled()) {
            return Long.valueOf(0L);
        }
        if (StringUtils.isBlank(key)) {
            return Long.valueOf(0L);
        }
        try
        {
            CacheBean cache = getCacheBean(key);
            if (cache != null)
            {
                this.redisCacheService.del(new String[] { addSys(key) });
                this.redisCacheService.del(new String[] { cache.getKey() });
            }
        }
        catch (Exception e)
        {
            return Long.valueOf(0L);
        }
        return Long.valueOf(1L);
    }

    /**
     * 批量移除
     * @param keys
     * @return
     */
    public Long remove(String... keys)
    {
        if (!isEnabled()) {
            return null;
        }
        try
        {
            for (int i = 0; i < keys.length; i++) {
                remove(keys[i]);
            }
        }
        catch (Exception e)
        {
            return Long.valueOf(0L);
        }
        return Long.valueOf(1L);
    }

    /**
     * 根据超时移除
     * @param pre
     * @return
     */
    public Long removeByPre(String pre)
    {
        if (!isEnabled()) {
            return Long.valueOf(0L);
        }
        if (StringUtils.isBlank(pre)) {
            return Long.valueOf(0L);
        }
        try
        {
            Set<String> result = this.redisCacheService.getByPre(addSys(pre));
            List<String> list = new ArrayList();
            for (String key : result)
            {
                CacheBean cache = getCacheBean(key);
                list.add(cache.getKey());
            }
            this.redisCacheService.del((String[])list.toArray(new String[0]));
            this.redisCacheService.delPre(addSys(pre));
        }
        catch (Exception e)
        {
            return Long.valueOf(0L);
        }
        return Long.valueOf(1L);
    }

    private CacheBean getCacheBean(String key)
    {
        key = addSys(key);
        CacheBean cache = null;
        try
        {
            cache = (CacheBean)JSON.parseObject(this.redisCacheService.get(key), CacheBean.class);
        }
        catch (Exception e)
        {
            cache = new CacheBean();
            cache.setKey(key);
            cache.setExpireTime(this.redisCacheService.getExpireDate(key));
        }
        return cache;
    }

    public String addSys(String key)
    {
        String result = key;
        String sys = this.redisConfig.getSysName();
        if (key.startsWith(sys)) {
            result = key;
        } else {
            result = sys + ":" + key;
        }
        return result;
    }

    public void set(String key, Object value, int expireMin, String desc)
    {
        if ((StringUtils.isBlank(key)) || (value == null) || (StringUtils.isBlank(value.toString()))) {
            return;
        }
        if (!isEnabled()) {
            return;
        }
        String realValue = "";
        if ((value instanceof String)) {
            realValue = value.toString();
        } else {
            realValue = JSON.toJSONString(value, false);
        }
        String realKey = "i_" + addSys(key);
        Date time = new DateTime(this.redisCacheService.getExpireDate(realKey)).plusMinutes(expireMin).toDate();
        CacheBean cache = new CacheBean(realKey, desc, time);
        String result = JSON.toJSONString(cache, false);
        this.redisCacheService.set(addSys(key), result, expireMin * 60);
        this.redisCacheService.set(realKey, realValue, expireMin * 60);
    }

    public List<CacheBean> listAll()
    {
        Set<String> result = this.redisCacheService.getByPre(addSys(""));
        List<CacheBean> caches = new ArrayList();
        if (result == null) {
            return caches;
        }
        Iterator<String> it = result.iterator();
        String key = "";
        CacheBean cache = null;
        while (it.hasNext())
        {
            cache = null;
            key = (String)it.next();
            try
            {
                cache = (CacheBean)JSON.parseObject(this.redisCacheService.get(key), CacheBean.class);
            }
            catch (Exception e)
            {
                cache = new CacheBean();
                cache.setKey(key);
                cache.setExpireTime(this.redisCacheService.getExpireDate(key));
            }
            if (cache != null)
            {
                cache.setKey(key);
                caches.add(cache);
            }
        }
        return caches;
    }

    public List<CacheBean> getCacheBeanByPre(String pre)
    {
        if (StringUtils.isBlank(pre)) {
            return new ArrayList();
        }
        Set<String> result = this.redisCacheService.getByPre(pre);
        Iterator<String> it = result.iterator();
        List<CacheBean> caches = new ArrayList();
        String key = "";
        CacheBean cache = null;
        while (it.hasNext())
        {
            key = (String)it.next();
            try
            {
                cache = (CacheBean) JSON.parseObject(this.redisCacheService.get(key), CacheBean.class);
            }
            catch (Exception e)
            {
                cache = new CacheBean();
                cache.setKey(key);
                cache.setExpireTime(this.redisCacheService.getExpireDate(key));
            }
            cache.setKey(key);
            caches.add(cache);
        }
        return caches;
    }

    public boolean isEnabled()
    {
        return Boolean.parseBoolean(this.redisConfig.getEnable());
    }
}
