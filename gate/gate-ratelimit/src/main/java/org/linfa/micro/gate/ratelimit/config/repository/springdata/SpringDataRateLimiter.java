package org.linfa.micro.gate.ratelimit.config.repository.springdata;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.linfa.micro.gate.ratelimit.config.Rate;
import org.linfa.micro.gate.ratelimit.config.repository.AbstractRateLimiter;

import java.util.Optional;

@RequiredArgsConstructor
@AllArgsConstructor
public class SpringDataRateLimiter extends AbstractRateLimiter {

    private  IRateLimiterRepository repository;

    @Override
    protected Rate getRate(String key) {
        return this.repository.findById(key).get();
    }

    @Override
    protected void saveRate(Rate rate) {
        this.repository.save(rate);
    }


}
