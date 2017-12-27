package com.paopaoxiong.ppx.aop;

import com.paopaoxiong.ppx.authorization.UserInfoUtil;
import com.paopaoxiong.ppx.common.SystemLogOperation;
import com.paopaoxiong.ppx.model.system.SysLog;
import com.paopaoxiong.ppx.model.system.User;
import com.paopaoxiong.ppx.service.system.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.jar.JarEntry;

@Aspect
@Component
public class SystemLogAop {

    @Autowired
    private SysLogService sysLogService;

    @Pointcut("@annotation(com.paopaoxiong.ppx.common.SystemLogOperation)")
    private void pointcut(){

    }

    @AfterReturning("pointcut()")
    public void advice(JoinPoint joinPoint){
        User currentUser = UserInfoUtil.CurrentUserInfo();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method  =signature.getMethod();
        SystemLogOperation log = method.getAnnotation(SystemLogOperation.class);
        SysLog sysLog = new SysLog();
        sysLog.setModule(log.module());
        sysLog.setDescription(log.description());
        sysLog.setMethod(log.mehtod());
        sysLog.setUserId(currentUser != null?currentUser.getId():null);
        sysLogService.add(sysLog);
    }
}
