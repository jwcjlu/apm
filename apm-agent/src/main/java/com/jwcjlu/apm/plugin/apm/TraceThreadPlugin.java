package com.jwcjlu.apm.plugin.apm;

import com.jwcjlu.apm.plugin.PluginDefine;

public class TraceThreadPlugin implements PluginDefine {
    @Override
    public String enchanceClass() {
        return "com.jwcjlu.apm.annotation.TraceThread";
    }

    @Override
    public String enchanceMethod() {
        return null;
    }

    @Override
    public String getMethodAroundInterceptor() {
        return "";
    }
}
