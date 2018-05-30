package com.wisdom.beauty.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wisdom.beauty.util.ParameterNameUtils;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * FileName: LogInterceptor
 *
 * @author: 赵得良
 * Date:     2018/5/30 0030 17:55
 * Description: 美享-微商平台日志输出
 */
@Aspect
@Component
public class LogInterceptor {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final ObjectMapper mapper = new ObjectMapper();

    @Pointcut("execution(* com.wisdom.beauty.controller..*(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object Interceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodInvocationProceedingJoinPoint methodPoint = (MethodInvocationProceedingJoinPoint) joinPoint;
        Field proxy = methodPoint.getClass().getDeclaredField("methodInvocation");
        proxy.setAccessible(true);
        MethodInvocation methodInvocation = (MethodInvocation) proxy.get(methodPoint);
        return invoke(methodInvocation);
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        String targetName = invocation.getThis().getClass().getSimpleName();
        Method method = invocation.getMethod();
        String methodName = method.getName();
        Object[] args = invocation.getArguments();
        info("[{}.{}]方法传入参数：{}", targetName, methodName, printArgs(args, method));
        try {
            long currentTimeMillis = System.currentTimeMillis();
            Object result = invocation.proceed();
            info("[{}.{}]方法执行完成，耗时：[{}]毫秒", targetName, methodName, (System.currentTimeMillis() - currentTimeMillis));
            info("[{}.{}]方法返回数据打印如下：{}", targetName, methodName, printResult(result));
            return result;
        } catch (Throwable throwable) {
            error("外部接口调用方法[{}.{}]异常：", targetName, methodName, throwable);
            throw throwable;
        }
    }

    private String printArgs(Object[] args, Method method) {
        StringBuffer stringBuffer = new StringBuffer("{");
        try {
            String[] argNames = null;
            try {
                argNames = ParameterNameUtils.getMethodParamNames(method);
            } catch (Exception e) {
                error("获取参数名称异常：", e);
            }
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    String argName = "";
                    if (argNames != null && argNames.length >= i) {
                        argName = argNames[i];
                    }
                    if (args[i] != null) {
                        String value = "";
                        try {
                            value = mapper.writeValueAsString(args[i]);
                        } catch (Exception e) {
                            error("转换参数 \"{}\" 发生异常：", argName, e);
                        }
                        stringBuffer.append("\"").append(argName).append("\"").append(":").append(value).append(",");
                    } else {
                        info("\"{}\"：NULL", argName);
                    }
                }
            }
        } catch (Exception e) {
            error("[接口调用拦截器]打印方法执行参数异常：", e);
        }
        return stringBuffer.deleteCharAt(stringBuffer.length() > 2 ? stringBuffer.length() - 1 : stringBuffer.length() - 0).append("}").toString();
    }

    private String printResult(Object result) {
        if (result != null) {
            try {
                return mapper.writeValueAsString(result);
            } catch (Exception e) {
                error("返回数据打印异常：", e);
            }
        } else {
            info("[返回数据]：NULL");
        }
        return result.toString();
    }

    protected final void error(String msg, Object... objects) {
        logger.error(msg, objects);
    }

    protected final void info(String msg, Object... objects) {
        logger.info(msg, objects);
    }
}