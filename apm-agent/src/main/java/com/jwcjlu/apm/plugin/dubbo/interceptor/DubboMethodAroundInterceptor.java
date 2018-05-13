package com.jwcjlu.apm.plugin.dubbo.interceptor;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcContext;
import com.jwcjlu.apm.MethodAroundInterceptor;
import com.jwcjlu.apm.MethodInvokerResult;
import com.jwcjlu.apm.context.CarrierContext;
import com.jwcjlu.apm.context.ContextManager;
import com.jwcjlu.apm.context.SpanEntry;
import io.netty.util.internal.StringUtil;

import java.lang.reflect.Method;
import java.util.Random;
import java.util.UUID;

public class DubboMethodAroundInterceptor implements MethodAroundInterceptor {
    @Override
    public void beforeMethod(Object targetObject, Method method, Object[] allArguments, Class<?>[] parameterTypes, MethodInvokerResult result) {
        Invoker invoker = (Invoker)allArguments[0];
        Invocation invocation = (Invocation)allArguments[1];
        RpcContext rpcContext = RpcContext.getContext();
        boolean isConsumer = rpcContext.isConsumerSide();
        URL requestURL = invoker.getUrl();
        final String host = requestURL.getHost();
        final int port = requestURL.getPort();
        SpanEntry  span=null;
        if(isConsumer){
            CarrierContext carrier=new CarrierContext();
            span= ContextManager.createSpan(generateOperationName(requestURL,invocation),carrier,host+port);
            System.out.println("beforeMethod consumer "+generateOperationName(requestURL,invocation));
            rpcContext.getAttachments().put("traceId",carrier.getTraceId());
            rpcContext.getAttachments().put("spanId",carrier.getSpanId());
        }else{
            String traceId=rpcContext.getAttachment("traceId");
            String spandId=rpcContext.getAttachment("spanId");
            CarrierContext carrier=new CarrierContext(traceId,spandId);
            span= ContextManager.createSpan(generateOperationName(requestURL,invocation),carrier,host+port);
            System.out.println("beforeMethod provider "+generateOperationName(requestURL,invocation));
        }
        span.start();

    }

    @Override
    public Object afterMethod(Object targetObject, Method method, Object[] allArguments, Class<?>[] parameterTypes, Object result) {
        System.out.println("afterMethod consumer "+targetObject);
        ContextManager.stopSpan();
        return result;
    }

    @Override
    public void handleMethodException(Object targetObject, Method method, Object[] allArguments, Class<?>[] parameterTypes, Throwable t) {

    }
    private String generateOperationName(URL requestURL, Invocation invocation) {
        StringBuilder operationName = new StringBuilder();
        operationName.append(requestURL.getPath());
        operationName.append("." + invocation.getMethodName() + "(");
        for (Class<?> classes : invocation.getParameterTypes()) {
            operationName.append(classes.getSimpleName() + ",");
        }

        if (invocation.getParameterTypes().length > 0) {
            operationName.delete(operationName.length() - 1, operationName.length());
        }

        operationName.append(")");

        return operationName.toString();
    }

}
