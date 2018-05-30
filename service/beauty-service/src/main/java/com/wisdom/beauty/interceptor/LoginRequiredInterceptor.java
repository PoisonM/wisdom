package com.wisdom.beauty.interceptor;

import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.util.JedisUtils;
import com.wisdom.common.util.LoginUtil;
import com.wisdom.common.util.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.util.GenericSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class LoginRequiredInterceptor {

    /**
     * 定义拦截规则：拦截com.xjj.web.controller包下面的所有类中，有@RequestMapping注解的方法。
     */
    @Pointcut("execution(* com.wisdom.beauty.controller..*(..)) && " +
            "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerMethodPointcut(){}

    /**
     * 拦截器具体实现
     * @param pjp
     * @return JsonResult（被拦截方法的执行结果，或需要登录的错误提示。）
     */
    @Around("controllerMethodPointcut()&& @annotation(com.wisdom.beauty.interceptor.LoginRequired)")
    public Object Interceptor(ProceedingJoinPoint pjp) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        ResponseDTO responseDTO = LoginUtil.processLoginInterceptor(request);
       if(responseDTO.getResult().equals(StatusConstant.FAILURE))
       {
           return responseDTO;
       }
       else
       {
           return pjp.proceed();
       }
    }


}