package org.linfa.micro.gate.ratelimit.config.repository.springdata;

import org.linfa.micro.gate.ratelimit.config.Rate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRateLimiterRepository extends CrudRepository<Rate, String> {
}
