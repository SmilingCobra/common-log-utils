package com.cobra.log;

public class LogSession {
    public static ThreadLocal<LogContext> logSession =new InheritableThreadLocal<>();
}
