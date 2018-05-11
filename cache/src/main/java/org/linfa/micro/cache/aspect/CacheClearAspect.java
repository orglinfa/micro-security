package org.linfa.micro.cache.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.linfa.micro.cache.annotation.CacheClear;
import org.linfa.micro.cache.api.CacheAPI;
import org.linfa.micro.cache.constants.CacheScope;
import org.linfa.micro.cache.parser.IKeyGenerator;
import org.linfa.micro.cache.parser.impl.DefaultKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Service
@Slf4j
public class CacheClearAspect {
    @Autowired
    private IKeyGenerator keyParser;
    @Autowired
    private CacheAPI cacheAPI;
//    protected Logger log = Logger.getLogger(getClass());
    private ConcurrentHashMap<String, IKeyGenerator> generatorMap = new ConcurrentHashMap();

    @Pointcut("@annotation(org.linfa.micro.cache.annotation.CacheClear)")
    public void aspect() {}

    @Around("aspect()&&@annotation(anno)")
    public Object interceptor(ProceedingJoinPoint invocation, CacheClear anno)
            throws Throwable
    {
        MethodSignature signature = (MethodSignature)invocation.getSignature();
        Method method = signature.getMethod();
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] arguments = invocation.getArgs();
        String key = "";
        if (StringUtils.isNotBlank(anno.key()))
        {
            key = getKey(anno, anno.key(), CacheScope.application, parameterTypes, arguments);

            this.cacheAPI.remove(key);
        }
        else if (StringUtils.isNotBlank(anno.pre()))
        {
            key = getKey(anno, anno.pre(), CacheScope.application, parameterTypes, arguments);

            this.cacheAPI.removeByPre(key);
        }
        else if (anno.keys().length > 1)
        {
            for (String tmp : anno.keys())
            {
                tmp = getKey(anno, tmp, CacheScope.application, parameterTypes, arguments);

                this.cacheAPI.removeByPre(tmp);
            }
        }
        return invocation.proceed();
    }

    private String getKey(CacheClear anno, String key, CacheScope scope, Class<?>[] parameterTypes, Object[] arguments)
            throws InstantiationException, IllegalAccessException
    {
        String generatorClsName = anno.generator().getName();
        IKeyGenerator keyGenerator = null;
        if (anno.generator().equals(DefaultKeyGenerator.class))
        {
            keyGenerator = this.keyParser;
        }
        else if (this.generatorMap.containsKey(generatorClsName))
        {
            keyGenerator = (IKeyGenerator)this.generatorMap.get(generatorClsName);
        }
        else
        {
            keyGenerator = (IKeyGenerator)anno.generator().newInstance();
            this.generatorMap.put(generatorClsName, keyGenerator);
        }
        String finalKey = keyGenerator.getKey(key, scope, parameterTypes, arguments);
        return finalKey;
    }
}
