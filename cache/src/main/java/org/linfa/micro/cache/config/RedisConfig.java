package org.linfa.micro.cache.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.linfa.micro.cache.utils.PropertiesLoaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;

@Configuration
@Getter
@Setter
public class RedisConfig {
    @Autowired
    private Environment env;
    private JedisPool pool;
    private String maxActive;
    private String maxIdle;
    private String maxWait;
    private String host;
    private String password;
    private String timeout;
    private String database;
    private String port;
    private String enable;
    private String sysName;

    @PostConstruct
    public void init()
    {
        PropertiesLoaderUtils prop = new PropertiesLoaderUtils(new String[] { "application.properties" });
        this.host = prop.getProperty("redis.host");
        if (StringUtils.isBlank(this.host))
        {
            this.host = this.env.getProperty("redis.host");
            this.maxActive = this.env.getProperty("redis.pool.maxActive");
            this.maxIdle = this.env.getProperty("redis.pool.maxIdle");
            this.maxWait = this.env.getProperty("redis.pool.maxWait");
            this.password = this.env.getProperty("redis.password");
            this.timeout = this.env.getProperty("redis.timeout");
            this.database = this.env.getProperty("redis.database");
            this.port = this.env.getProperty("redis.port");
            this.sysName = this.env.getProperty("redis.sysName");
            this.enable = this.env.getProperty("redis.enable");
        }
        else
        {
            this.maxActive = prop.getProperty("redis.pool.maxActive");
            this.maxIdle = prop.getProperty("redis.pool.maxIdle");
            this.maxWait = prop.getProperty("redis.pool.maxWait");
            this.password = prop.getProperty("redis.password");
            this.timeout = prop.getProperty("redis.timeout");
            this.database = prop.getProperty("redis.database");
            this.port = prop.getProperty("redis.port");
            this.sysName = prop.getProperty("redis.sysName");
            this.enable = prop.getProperty("redis.enable");
        }
    }

    @Bean
    public JedisPoolConfig constructJedisPoolConfig()
    {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.parseInt(this.maxActive));
        config.setMaxIdle(Integer.parseInt(this.maxIdle));
        config.setMaxWaitMillis(Integer.parseInt(this.maxWait));
        config.setTestOnBorrow(true);

        return config;
    }

    @Bean(name={"pool"})
    public JedisPool constructJedisPool()
    {
        String ip = this.host;
        int port = Integer.parseInt(this.port);
        String password = this.password;
        int timeout = Integer.parseInt(this.timeout);
        int database = Integer.parseInt(this.database);
        if (null == this.pool) {
            if (StringUtils.isBlank(password)) {
                this.pool = new JedisPool(constructJedisPoolConfig(), ip, port, timeout);
            } else {
                this.pool = new JedisPool(constructJedisPoolConfig(), ip, port, timeout, password, database);
            }
        }
        return this.pool;
    }

  /*  public String getMaxActive()
    {
        return this.maxActive;
    }

    public void setMaxActive(String maxActive)
    {
        this.maxActive = maxActive;
    }

    public String getMaxIdle()
    {
        return this.maxIdle;
    }

    public void setMaxIdle(String maxIdle)
    {
        this.maxIdle = maxIdle;
    }

    public String getMaxWait()
    {
        return this.maxWait;
    }

    public void setMaxWait(String maxWait)
    {
        this.maxWait = maxWait;
    }

    public String getHost()
    {
        return this.host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getTimeout()
    {
        return this.timeout;
    }

    public void setTimeout(String timeout)
    {
        this.timeout = timeout;
    }

    public String getDatabase()
    {
        return this.database;
    }

    public void setDatabase(String database)
    {
        this.database = database;
    }

    public String getSysName()
    {
        return this.sysName;
    }

    public void setSysName(String sysName)
    {
        this.sysName = sysName;
    }

    public String getEnable()
    {
        return this.enable;
    }

    public void setEnable(String enable)
    {
        this.enable = enable;
    }

    public String getPort()
    {
        return this.port;
    }

    public void setPort(String port)
    {
        this.port = port;
    }*/
}
