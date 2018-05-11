package org.linfa.micro.cache.parser;

import java.lang.reflect.Type;

public abstract interface ICacheResultParser {
    public abstract Object parse(String paramString, Type paramType, Class<?>... paramVarArgs);
}
