package org.linfa.micro.cache.service;

import redis.clients.jedis.BinaryClient;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract interface IRedisService {
    public abstract String get(String paramString);

    public abstract Set<String> getByPre(String paramString);

    public abstract String set(String paramString1, String paramString2);

    public abstract String set(String paramString1, String paramString2, int paramInt);

    public abstract Long delPre(String paramString);

    public abstract Long del(String... paramVarArgs);

    public abstract Long append(String paramString1, String paramString2);

    public abstract Boolean exists(String paramString);

    public abstract Long setnx(String paramString1, String paramString2);

    public abstract String setex(String paramString1, String paramString2, int paramInt);

    public abstract Long setrange(String paramString1, String paramString2, int paramInt);

    public abstract List<String> mget(String... paramVarArgs);

    public abstract String mset(String... paramVarArgs);

    public abstract Long msetnx(String... paramVarArgs);

    public abstract String getset(String paramString1, String paramString2);

    public abstract String getrange(String paramString, int paramInt1, int paramInt2);

    public abstract Long incr(String paramString);

    public abstract Long incrBy(String paramString, Long paramLong);

    public abstract Long decr(String paramString);

    public abstract Long decrBy(String paramString, Long paramLong);

    public abstract Long serlen(String paramString);

    public abstract Long hset(String paramString1, String paramString2, String paramString3);

    public abstract Long hsetnx(String paramString1, String paramString2, String paramString3);

    public abstract String hmset(String paramString, Map<String, String> paramMap);

    public abstract String hget(String paramString1, String paramString2);

    public abstract List<String> hmget(String paramString, String... paramVarArgs);

    public abstract Long hincrby(String paramString1, String paramString2, Long paramLong);

    public abstract Boolean hexists(String paramString1, String paramString2);

    public abstract Long hlen(String paramString);

    public abstract Long hdel(String paramString, String... paramVarArgs);

    public abstract Set<String> hkeys(String paramString);

    public abstract List<String> hvals(String paramString);

    public abstract Map<String, String> hgetall(String paramString);

    public abstract Long lpush(String paramString, String... paramVarArgs);

    public abstract Long rpush(String paramString, String... paramVarArgs);

    public abstract Long linsert(String paramString1, BinaryClient.LIST_POSITION paramLIST_POSITION, String paramString2, String paramString3);

    public abstract String lset(String paramString1, Long paramLong, String paramString2);

    public abstract Long lrem(String paramString1, long paramLong, String paramString2);

    public abstract String ltrim(String paramString, long paramLong1, long paramLong2);

    public abstract String lpop(String paramString);

    public abstract String rpop(String paramString);

    public abstract String rpoplpush(String paramString1, String paramString2);

    public abstract String lindex(String paramString, long paramLong);

    public abstract Long llen(String paramString);

    public abstract List<String> lrange(String paramString, long paramLong1, long paramLong2);

    public abstract Long sadd(String paramString, String... paramVarArgs);

    public abstract Long srem(String paramString, String... paramVarArgs);

    public abstract String spop(String paramString);

    public abstract Set<String> sdiff(String... paramVarArgs);

    public abstract Long sdiffstore(String paramString, String... paramVarArgs);

    public abstract Set<String> sinter(String... paramVarArgs);

    public abstract Long sinterstore(String paramString, String... paramVarArgs);

    public abstract Set<String> sunion(String... paramVarArgs);

    public abstract Long sunionstore(String paramString, String... paramVarArgs);

    public abstract Long smove(String paramString1, String paramString2, String paramString3);

    public abstract Long scard(String paramString);

    public abstract Boolean sismember(String paramString1, String paramString2);

    public abstract String srandmember(String paramString);

    public abstract Set<String> smembers(String paramString);

    public abstract Long zadd(String paramString1, double paramDouble, String paramString2);

    public abstract Long zrem(String paramString, String... paramVarArgs);

    public abstract Double zincrby(String paramString1, double paramDouble, String paramString2);

    public abstract Long zrank(String paramString1, String paramString2);

    public abstract Long zrevrank(String paramString1, String paramString2);

    public abstract Set<String> zrevrange(String paramString, long paramLong1, long paramLong2);

    public abstract Set<String> zrangebyscore(String paramString1, String paramString2, String paramString3);

    public abstract Set<String> zrangeByScore(String paramString, double paramDouble1, double paramDouble2);

    public abstract Long zcount(String paramString1, String paramString2, String paramString3);

    public abstract Long zcard(String paramString);

    public abstract Double zscore(String paramString1, String paramString2);

    public abstract Long zremrangeByRank(String paramString, long paramLong1, long paramLong2);

    public abstract Long zremrangeByScore(String paramString, double paramDouble1, double paramDouble2);

    public abstract Set<String> keys(String paramString);

    public abstract String type(String paramString);

    public abstract Date getExpireDate(String paramString);
}
