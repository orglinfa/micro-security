package org.linfa.micro.gate.ratelimit.config;

import org.linfa.micro.gate.ratelimit.config.properties.RateLimitProperties;

public interface RateLimiter {
    /**
     * @param policy - Template for which rates should be created in case there's no rate limit associated with the key
     * @param key    - Unique key that identifies a request
     * @return a view of a user's rate request limit
     */
    Rate consume(RateLimitProperties.Policy policy, String key);
}
