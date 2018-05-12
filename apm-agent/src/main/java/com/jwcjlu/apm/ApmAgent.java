package com.jwcjlu.apm;

import com.jwcjlu.apm.plugin.PluginsFinder;
import com.jwcjlu.apm.remote.RemoteConfig;
import io.netty.util.internal.StringUtil;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;
import java.lang.instrument.Instrumentation;
public class ApmAgent {
    public static void premain(String agentArgs, Instrumentation instrumentation)  {
       if(StringUtil.isNullOrEmpty(agentArgs)){
           System.out.println("error");
       }
        RemoteConfig.parseAndInit(agentArgs);
        PluginsFinder.initialize();
        new AgentBuilder.Default()
                .type(ElementMatchers.any())
                .transform(new Transformer())
                .installOn(instrumentation);
    }
    private static class Transformer implements AgentBuilder.Transformer {
        @Override
        public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {
            try {
                return PluginsFinder.define(typeDescription,builder,classLoader);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return builder;
        }
    }

}
