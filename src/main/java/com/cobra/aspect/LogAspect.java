package com.cobra.aspect;

import com.cobra.log.LogContext;
import com.cobra.log.LogEntity;
import com.cobra.log.LogHelper;
import com.cobra.log.LogSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.UUID;

@Aspect
@Component
public class LogAspect {
    private Logger logger = LoggerFactory.getLogger(LogAspect.class);
    @Pointcut("@annotation(com.cobra.aspect.LogEndpoint)")
    public void controllerAspect(){
    }
    @Around("controllerAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint){
        Long now = System.currentTimeMillis();
        Object result = null;
        try {
            result = joinPoint.proceed();
            Object[] args = joinPoint.getArgs();
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            LogEndpoint logEndpoint = method.getAnnotation(LogEndpoint.class);
            LogContext logContext = LogSession.logSession.get();
            if(logContext == null){
                logContext = new LogContext();
            }
            logContext.setLogId(UUID.randomUUID().toString());
            logContext.setModule(logEndpoint.module());
            LogSession.logSession.set(logContext);
            LogEntity logEntity = LogHelper.monitor(logger).add("title", "mark").add("result", result);
            if(args!=null){
                for(int i = 0;i<args.length;i++){
                    logEntity.add("arg"+i,args[i]);
                }
            }
            logEntity.add("cost",System.currentTimeMillis()-now);
            logEntity.submit();
        } catch (Throwable e) {
            LogHelper.error(logger,e,"方法执行出现异常");
        }

    return result;
    }
}
