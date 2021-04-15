package com.cobra.aspect;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface LogEndpoint {

     String module();
     String description() default "暂无描述";





}
