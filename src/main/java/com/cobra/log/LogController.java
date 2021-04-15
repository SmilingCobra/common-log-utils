package com.cobra.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/log")
public class LogController {

    private Logger logger = LoggerFactory.getLogger(LogController.class);
@RequestMapping("/logTest")
    public String logTest(String name){
    LogContext logContext = LogSession.logSession.get();
    if(logContext == null){
        logContext = new LogContext();
    }
    logContext.setLogId(UUID.randomUUID().toString());
    logContext.setModule("logTest");
    LogSession.logSession.set(logContext);

    LogHelper.monitor(logger).add("title","开始时记录日志").add("info","test start").submit();


    LogHelper.monitor(logger).add("title","结束时记录日志").add("name",name).submit();
        return "success";
    }

}
