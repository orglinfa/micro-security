package org.linfa.micro.cache.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration("cacheWebConfig")
public class CacheWebConfig extends WebMvcConfigurerAdapter {
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler(new String[] { "/static/cache/**" }).addResourceLocations(new String[] { "classpath:/META-INF/static/" });

        super.addResourceHandlers(registry);
    }
}
