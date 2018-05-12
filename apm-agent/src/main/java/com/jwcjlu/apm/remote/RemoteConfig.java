package com.jwcjlu.apm.remote;

import com.jwcjlu.apm.util.ArgsParser;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RemoteConfig {
    public  static String  GRPC_HOST ;
    public  static int  GRPC_PORT;
    public static String APPLICATIONNAME;
    public static void parseAndInit(String agrs){
        Map<String, String> map=argsToMap(agrs);
        APPLICATIONNAME=map.get("applicationName");
        GRPC_HOST=map.get("host");
        GRPC_PORT=Integer.valueOf(map.get("port"));

    }
    private static Map<String, String> argsToMap(String agentArgs) {
        ArgsParser argsParser = new ArgsParser();
        Map<String, String> agentArgsMap = argsParser.parse(agentArgs);
        return agentArgsMap;
    }
}
