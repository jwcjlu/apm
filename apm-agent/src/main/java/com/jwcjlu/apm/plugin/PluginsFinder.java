package com.jwcjlu.apm.plugin;

import com.jwcjlu.apm.InstMethodInter;
import com.jwcjlu.apm.annotation.TraceThread;
import com.jwcjlu.apm.plugin.apm.TraceThreadMethodAroundInterceptor;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
public class PluginsFinder {
    private  static Map<String,PluginDefine> pluginMap=new ConcurrentHashMap<>();
    public static void initialize(){
        ServiceLoader serviceLoad=  ServiceLoader.load(PluginDefine.class);
        Iterator<PluginDefine> next=serviceLoad.iterator();
        while(next.hasNext()){
            PluginDefine plugin=next.next();
            pluginMap.put(plugin.enchanceClass(),plugin);
        }
    }

    public static DynamicType.Builder<?> define(TypeDescription typeDescription, DynamicType.Builder<?> builder,
                                                ClassLoader classLoader)throws ClassNotFoundException,InstantiationException, IllegalAccessException{
        if(!pluginMap.containsKey(typeDescription.getName())){
            return builder.method(
                    ElementMatchers.isAnnotatedWith(TraceThread.class)
            ).intercept(
                    MethodDelegation.to(new InstMethodInter(new TraceThreadMethodAroundInterceptor()))
            );
        }
        return ClassEnchancePluginDefine.enchance(typeDescription,builder,classLoader,pluginMap.get(typeDescription.getName()));

    }

}
