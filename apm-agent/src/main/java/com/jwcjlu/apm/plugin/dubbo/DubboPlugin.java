package com.jwcjlu.apm.plugin.dubbo;

import com.jwcjlu.apm.plugin.PluginDefine;

public class DubboPlugin implements PluginDefine {
    @Override
    public String enchanceClass() {
        return "com.alibaba.dubbo.monitor.support.MonitorFilter";
    }

    @Override
    public String enchanceMethod() {
        return "invoke";
    }

    @Override
    public String getMethodAroundInterceptor() {
        return "com.jwcjlu.apm.plugin.dubbo.interceptor.DubboMethodAroundInterceptor";
    }
}
