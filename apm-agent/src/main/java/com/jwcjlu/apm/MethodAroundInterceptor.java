package com.jwcjlu.apm;

import java.lang.reflect.Method;

public interface MethodAroundInterceptor {

    public void beforeMethod(Object targetObject, Method method, Object[] allArguments, Class<?>[] parameterTypes, MethodInvokerResult result);

    public Object afterMethod(Object targetObject, Method method, Object[] allArguments, Class<?>[] parameterTypes, Object result);

    public void handleMethodException(Object targetObject, Method method, Object[] allArguments, Class<?>[] parameterTypes, Throwable t);
}
