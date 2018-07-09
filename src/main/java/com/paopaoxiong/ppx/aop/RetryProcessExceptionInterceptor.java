package com.paopaoxiong.ppx.aop;

import com.paopaoxiong.ppx.annotation.RetryProcess;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
public class RetryProcessExceptionInterceptor {

    @AfterThrowing(pointcut=("execution(* com.paopaoxiong.ppx..*(..)) && @annotation(com.paopaoxiong.ppx.annotation.RetryProcess)"))
    public void tryAgain(JoinPoint point) {
        System.out.println("------------开始重试------------");
        try {
            Object object = point.getTarget();
            System.out.println("null="+object.getClass());
            Field field = object.getClass().getDeclaredField("userService");
            field.setAccessible(true);
            MethodSignature methodSignature = (MethodSignature) point.getSignature();
            RetryProcess retryProcess = methodSignature.getMethod().getAnnotation(RetryProcess.class);
            int count = 0;
            if (count < retryProcess.value()) {
                System.out.println("开始重试第"+(count+1));
                MethodInvocationProceedingJoinPoint methodPoint = ((MethodInvocationProceedingJoinPoint) point);
                methodPoint.proceed();
            }
        } catch (Throwable throwable) {
            System.out.println("重试失败"+throwable.getMessage());
            //tryAgain(point);
        }
    }
}
