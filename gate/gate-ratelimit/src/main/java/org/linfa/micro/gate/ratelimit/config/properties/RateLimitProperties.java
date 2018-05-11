package org.linfa.micro.gate.ratelimit.config.properties;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
@Data
@NoArgsConstructor
@Validated
@ConfigurationProperties(RateLimitProperties.PREFIX)
public class RateLimitProperties {
    public static final String PREFIX = "zuul.ratelimit";

    private Policy defaultPolicy;
    @NotNull
    private Map<String, Policy> policies = Maps.newHashMap();
    private boolean behindProxy;
    private boolean enabled;
    @NotNull
    @Value("${spring.application.name:rate-limit-application}")
    private String keyPrefix;
    @NotNull
    private Repository repository = Repository.IN_MEMORY;

    public enum Repository {
        REDIS, CONSUL, JPA, IN_MEMORY
    }

    public Optional<Policy> getPolicy(String key) {
        return Optional.ofNullable(policies.getOrDefault(key, defaultPolicy));
    }
    @Getter
    @Setter
    public static class Policy {

        @NotNull
        private Long refreshInterval = TimeUnit.MINUTES.toSeconds(1L);
        @NotNull
        private Long limit;
        @NotNull
        private List<Type> type = Lists.newArrayList();

        public enum Type {
            ORIGIN, USER, URL
        }

        public void setRefreshInterval(Long refreshInterval) {
            this.refreshInterval = refreshInterval;
        }

        public void setLimit(Long limit) {
            this.limit = limit;
        }

        public void setType(List<Type> type) {
            this.type = type;
        }
    }
}
