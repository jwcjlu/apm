package com.jwcjlu.apm.plugin.apm;

import com.jwcjlu.apm.MethodAroundInterceptor;
import com.jwcjlu.apm.MethodInvokerResult;

import java.lang.reflect.Method;

public class TraceThreadMethodAroundInterceptor implements MethodAroundInterceptor {
    @Override
    public void beforeMethod(Object targetObject, Method method, Object[] allArguments, Class<?>[] parameterTypes, MethodInvokerResult result) {
        System.out.println("beforeMethod");
    }

    @Override
    public Object afterMethod(Object targetObject, Method method, Object[] allArguments, Class<?>[] parameterTypes, Object result) {
        System.out.println("afterMethod");
        return null;
    }

    @Override
    public void handleMethodException(Object targetObject, Method method, Object[] allArguments, Class<?>[] parameterTypes, Throwable t) {

    }
}
