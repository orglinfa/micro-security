package org.linfa.micro.cache.annotation;

import org.linfa.micro.cache.constants.CacheScope;
import org.linfa.micro.cache.parser.ICacheResultParser;
import org.linfa.micro.cache.parser.IKeyGenerator;
import org.linfa.micro.cache.parser.impl.DefaultKeyGenerator;
import org.linfa.micro.cache.parser.impl.DefaultResultParser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.TYPE})
public @interface Cache {
    String key() default "";

    CacheScope scope() default CacheScope.application;

    int expire() default 720;

    String desc() default "";

    Class[] result() default {Object.class};

    Class<? extends ICacheResultParser> parser() default DefaultResultParser.class;

    Class<? extends IKeyGenerator> generator() default DefaultKeyGenerator.class;
}
