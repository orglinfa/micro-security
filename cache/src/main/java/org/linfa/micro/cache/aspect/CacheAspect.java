package org.linfa.micro.cache.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.linfa.micro.cache.annotation.Cache;
import org.linfa.micro.cache.api.CacheAPI;
import org.linfa.micro.cache.parser.ICacheResultParser;
import org.linfa.micro.cache.parser.IKeyGenerator;
import org.linfa.micro.cache.parser.impl.DefaultKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Service
@Slf4j
public class CacheAspect {
    @Autowired
    private IKeyGenerator keyParser;
    @Autowired
    private CacheAPI cacheAPI;
//    protected Logger log = Logger.getLogger(getClass());
    private ConcurrentHashMap<String, ICacheResultParser> parserMap = new ConcurrentHashMap();
    private ConcurrentHashMap<String, IKeyGenerator> generatorMap = new ConcurrentHashMap();

    @Pointcut("@annotation(com.ace.cache.annotation.Cache)")
    public void aspect() {}

    @Around("aspect()&&@annotation(anno)")
    public Object interceptor(ProceedingJoinPoint invocation, Cache anno)
            throws Throwable
    {
        MethodSignature signature = (MethodSignature)invocation.getSignature();
        Method method = signature.getMethod();
        Object result = null;
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] arguments = invocation.getArgs();
        String key = "";
        String value = "";
        try
        {
            key = getKey(anno, parameterTypes, arguments);
            value = this.cacheAPI.get(key);
            Type returnType = method.getGenericReturnType();
            result = getResult(anno, result, value, returnType);
        }
        catch (Exception e)
        {
            this.log.error("缓存获取失败：" + key, e);
        }
        finally
        {
            if (result == null)
            {
                result = invocation.proceed();
                if (StringUtils.isNotBlank(key)) {
                    this.cacheAPI.set(key, result, anno.expire());
                }
            }
        }
        return result;
    }

    private String getKey(Cache anno, Class<?>[] parameterTypes, Object[] arguments)
            throws InstantiationException, IllegalAccessException
    {
        String generatorClsName = anno.generator().getName();
        IKeyGenerator keyGenerator = null;
        if (anno.generator().equals(DefaultKeyGenerator.class))
        {
            keyGenerator = this.keyParser;
        }
        else if (this.generatorMap.contains(generatorClsName))
        {
            keyGenerator = (IKeyGenerator)this.generatorMap.get(generatorClsName);
        }
        else
        {
            keyGenerator = (IKeyGenerator)anno.generator().newInstance();
            this.generatorMap.put(generatorClsName, keyGenerator);
        }
        String key = keyGenerator.getKey(anno.key(), anno.scope(), parameterTypes, arguments);

        return key;
    }

    private Object getResult(Cache anno, Object result, String value, Type returnType)
            throws InstantiationException, IllegalAccessException
    {
        String parserClsName = anno.parser().getName();
        ICacheResultParser parser = null;
        if (this.parserMap.containsKey(parserClsName))
        {
            parser = (ICacheResultParser)this.parserMap.get(parserClsName);
        }
        else
        {
            parser = (ICacheResultParser)anno.parser().newInstance();
            this.parserMap.put(parserClsName, parser);
        }
        if (parser != null) {
            if (anno.result()[0].equals(Object.class)) {
                result = parser.parse(value, returnType, null);
            } else {
                result = parser.parse(value, returnType, anno.result());
            }
        }
        return result;
    }
}
