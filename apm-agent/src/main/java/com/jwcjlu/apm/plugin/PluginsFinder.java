package com.jwcjlu.apm.plugin;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;

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
            return builder;
        }
        return ClassEnchancePluginDefine.enchance(typeDescription,builder,classLoader,pluginMap.get(typeDescription.getName()));

    }

}
