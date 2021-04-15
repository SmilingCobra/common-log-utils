package com.cobra.log;

import org.slf4j.Logger;

public class LogHelper {

    public static LogEntity monitor(Logger logger){
        return new LogEntity(logger);
    }

    public static void error(Logger logger,Throwable e,String description){
        LogHelper.monitor(logger).add("title","出现异常").add("e",e.getMessage()).add("description",description).submit();
    }
}
