package org.linfa.micro.cache;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({AutoConfiguration.class})
@Documented
@Inherited
public @interface EnableAceCache {
}
