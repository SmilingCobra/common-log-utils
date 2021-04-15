package com.cobra.log;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;

public class LogEntity {

    private Logger logger;

    private JSONObject data;

    public LogEntity(Logger logger){
        this.logger = logger;
        data = new JSONObject();
    }


    public LogEntity add(String title,Object message){
        data.put(title,message);
        return this;
    }

    public void submit(){
        LogContext logContext = LogSession.logSession.get();
        if(logContext!=null){
            data.put("traceId",logContext.getLogId());
            data.put("module",logContext.getModule());
        }
        logger.info(data.toJSONString());
    }


}
