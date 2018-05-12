package com.jwcjlu.apm;

import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class InstMethodInter {
    private MethodAroundInterceptor interceptor;
    public InstMethodInter(MethodAroundInterceptor interceptor){
        this.interceptor=interceptor;
    }
    @RuntimeType
    public Object intercept(@This Object targetObject,
                            @AllArguments Object[] allArguments,
                            @SuperCall Callable<?> zuper,
                            @Origin Method method
    ) throws Throwable {


        MethodInvokerResult result = new MethodInvokerResult();
        try {
            interceptor.beforeMethod(targetObject, method, allArguments, method.getParameterTypes(),
                    result);
        } catch (Throwable t) {
            System.out.println(String.format( "class[{}] before method[{}] intercept failure", targetObject.getClass(), method.getName()));
        }

        Object ret = null;
        try {
            if (!result.isContinue()) {
                ret = result._ret();
            } else {
                ret = zuper.call();
            }
        } catch (Throwable t) {
            try {
                interceptor.handleMethodException(targetObject, method, allArguments, method.getParameterTypes(),
                        t);
            } catch (Throwable t2) {
                System.out.println(String.format( "class[{}] handle method[{}] exception failure", targetObject.getClass(), method.getName()));
            }
            throw t;
        } finally {
            try {
                ret = interceptor.afterMethod(targetObject, method, allArguments, method.getParameterTypes(),
                        ret);
            } catch (Throwable t) {
                System.out.println(String.format("class[{}] after method[{}] intercept failure",  targetObject.getClass(), method.getName()));
            }
        }
        return ret;
    }
}
