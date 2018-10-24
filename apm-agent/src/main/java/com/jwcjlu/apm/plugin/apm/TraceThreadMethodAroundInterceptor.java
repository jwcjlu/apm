package com.jwcjlu.apm.plugin.apm;

import com.jwcjlu.apm.MethodAroundInterceptor;
import com.jwcjlu.apm.MethodInvokerResult;

import java.lang.reflect.Method;
import java.util.concurrent.Executors;

public class TraceThreadMethodAroundInterceptor implements MethodAroundInterceptor {
    @Override
    public void beforeMethod(Object targetObject, Method method, Object[] allArguments, Class<?>[] parameterTypes, MethodInvokerResult result) {
        Executors.newScheduledThreadPool(100);
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
