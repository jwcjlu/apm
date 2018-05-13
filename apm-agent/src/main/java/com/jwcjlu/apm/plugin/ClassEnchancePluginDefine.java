package com.jwcjlu.apm.plugin;

import com.jwcjlu.apm.InstMethodInter;
import com.jwcjlu.apm.MethodAroundInterceptor;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.matcher.NameMatcher;
import net.bytebuddy.matcher.StringMatcher;
public  class ClassEnchancePluginDefine {


    public static DynamicType.Builder<?> enchance(TypeDescription typeDescription, DynamicType.Builder<?> builder,
                                                  ClassLoader classLoader, PluginDefine pd)
            throws ClassNotFoundException,InstantiationException, IllegalAccessException{
        boolean  isNullClassLoad=false;
        if(classLoader==null){
            classLoader= Thread.currentThread().getContextClassLoader();
            isNullClassLoad=true;
        }
        DynamicType.Builder<?>   newClassBuilder =enchanceInstanceMethod(typeDescription,builder,classLoader,pd);
        if(isNullClassLoad){
            classLoader=null;
        }

        return newClassBuilder;

    }
    private static  DynamicType.Builder<?> enchanceInstanceMethod(TypeDescription typeDescription, DynamicType.Builder<?> builder,
                                                                  ClassLoader classLoader, PluginDefine pd) throws
            IllegalAccessException, InstantiationException, ClassNotFoundException {
        Class<MethodAroundInterceptor>interceptorClass=(Class<MethodAroundInterceptor>)classLoader.loadClass(pd.getMethodAroundInterceptor());

        DynamicType.Builder<?>  newClassBuilder= builder.method(
                new NameMatcher(new StringMatcher(pd.enchanceMethod(), StringMatcher.Mode.EQUALS_FULLY))
        ).intercept(
                MethodDelegation.to(new InstMethodInter(interceptorClass.newInstance()))
        );
        return newClassBuilder;

    }
    public static  DynamicType.Builder<?> enchanceAnnotationMethod(TypeDescription typeDescription, DynamicType.Builder<?> builder,
                                                                  ClassLoader classLoader, PluginDefine pd,Class clazz) throws
            IllegalAccessException, InstantiationException, ClassNotFoundException {
        Class<MethodAroundInterceptor>interceptorClass=(Class<MethodAroundInterceptor>)classLoader.loadClass(pd.getMethodAroundInterceptor());

        DynamicType.Builder<?>  newClassBuilder= builder.method(
                ElementMatchers.isAnnotatedWith(clazz)
        ).intercept(
                MethodDelegation.to(new InstMethodInter(interceptorClass.newInstance()))
                        .andThen(SuperMethodCall.INSTANCE)
        );
        return newClassBuilder;

    }


}
