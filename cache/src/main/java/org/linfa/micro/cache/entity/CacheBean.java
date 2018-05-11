package org.linfa.micro.cache.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CacheBean {
    private String key = "";
    private String desc = "";
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date expireTime;

    public CacheBean(String key, String desc, Date expireTime)
    {
        this.key = key;
        this.desc = desc;
        this.expireTime = expireTime;
    }

    public CacheBean(String key, Date expireTime)
    {
        this.key = key;
        this.expireTime = expireTime;
    }

/*    public CacheBean() {}

    public String getKey()
    {
        return this.key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getDesc()
    {
        return this.desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public Date getExpireTime()
    {
        return this.expireTime;
    }

    public void setExpireTime(Date expireTime)
    {
        this.expireTime = expireTime;
    }*/
}
