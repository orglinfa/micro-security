package org.linfa.micro.gate.ratelimit.config.repository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.linfa.micro.gate.ratelimit.config.Rate;
import org.linfa.micro.gate.ratelimit.config.RateLimiter;
import org.linfa.micro.gate.ratelimit.config.properties.RateLimitProperties.Policy;
import org.springframework.data.redis.core.RedisTemplate;
import static java.util.concurrent.TimeUnit.SECONDS;
@RequiredArgsConstructor
@AllArgsConstructor
public class RedisRateLimiter implements RateLimiter {
    private  RedisTemplate template;

    @Override
    @SuppressWarnings("unchecked")
    public Rate consume(final Policy policy, final String key) {
        final Long limit = policy.getLimit();
        final Long refreshInterval = policy.getRefreshInterval();
        final Long current = this.template.boundValueOps(key).increment(1L);
        Long expire = this.template.getExpire(key);
        if (expire == null || expire == -1) {
            this.template.expire(key, refreshInterval, SECONDS);
            expire = refreshInterval;

        }
        return new Rate(key, Math.max(-1, limit - current), SECONDS.toMillis(expire), null);
    }
}
