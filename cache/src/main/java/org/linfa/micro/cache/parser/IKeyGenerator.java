package org.linfa.micro.cache.parser;

import org.linfa.micro.cache.constants.CacheScope;

public abstract  class IKeyGenerator {
    public static final String LINK = "_";

    public String getKey(String key, CacheScope scope, Class<?>[] parameterTypes, Object[] arguments)
    {
        StringBuffer sb = new StringBuffer("");
        key = buildKey(key, scope, parameterTypes, arguments);
        sb.append(key);
        if ((CacheScope.user.equals(scope)) &&
                (getUserKeyGenerator() != null)) {
            sb.append("_").append(getUserKeyGenerator().getCurrentUserAccount());
        }
        return sb.toString();
    }

    public abstract IUserKeyGenerator getUserKeyGenerator();

    public abstract String buildKey(String paramString, CacheScope paramCacheScope, Class<?>[] paramArrayOfClass, Object[] paramArrayOfObject);
}
