package com.paopaoxiong.ppx.common;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLogOperation {

    String module() default "";//模块

    String mehtod() default "";//方法

    String description() default ""; //描述
}
