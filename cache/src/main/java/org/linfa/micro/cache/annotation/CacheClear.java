package org.linfa.micro.cache.annotation;

import org.linfa.micro.cache.parser.IKeyGenerator;
import org.linfa.micro.cache.parser.impl.DefaultKeyGenerator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.TYPE})
public @interface CacheClear {
    String pre() default "";

    String key() default "";

    String[] keys() default {""};

    Class<? extends IKeyGenerator> generator() default DefaultKeyGenerator.class;
}
