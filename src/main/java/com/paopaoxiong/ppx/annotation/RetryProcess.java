package com.paopaoxiong.ppx.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Component
public @interface RetryProcess {

    /**
     * 重试的次数
     * @return
     */
    int value() default 1;
}
