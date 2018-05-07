package com.wisdom.weixin.interceptor;

import com.google.gson.Gson;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.JedisUtils;
import com.wisdom.common.util.WeixinUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    @Pointcut("execution(* com.wisdom.weixin.controller..*(..)) && " +
            "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerMethodPointcut(){}

    /**
     * 拦截器具体实现
     * @param pjp
     * @return JsonResult（被拦截方法的执行结果，或需要登录的错误提示。）
     */
    @Around("controllerMethodPointcut()")
    public Object Interceptor(ProceedingJoinPoint pjp) throws Throwable {

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod(); //获取被拦截的方法

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        // 判断该方法是否加了@LoginRequired 注解
        if(method.isAnnotationPresent(LoginRequired.class)){
            Map<String, String> tokenValue = getHeadersInfo(request);
            String userType = tokenValue.get("usertype");
            String token = "";
            if(userType==null||userType.equals(""))
            {
                token = tokenValue.get("logintoken");
                //验证token有效性
                int loginTokenPeriod = ConfigConstant.logintokenPeriod;
                String userInfo = JedisUtils.get(token);
                if(userInfo==null)
                {
                    ResponseDTO<String> responseDto=new ResponseDTO<String>();
                    responseDto.setResult(StatusConstant.FAILURE);
                    responseDto.setErrorInfo(StatusConstant.TOKEN_ERROR);
                    return responseDto;
                }
                else
                {
                    String openId = WeixinUtil.getUserOpenId(session,request);
                    UserInfoDTO userInfoDTO = (new Gson()).fromJson(userInfo,UserInfoDTO.class);
                    if(openId==null||!openId.equals(userInfoDTO.getUserOpenid()))
                    {
                        ResponseDTO<String> responseDto=new ResponseDTO<String>();
                        responseDto.setResult(StatusConstant.FAILURE);
                        responseDto.setErrorInfo(StatusConstant.TOKEN_ERROR);
                        return responseDto;
                    }
                }
                JedisUtils.set(token,userInfo,loginTokenPeriod);
            }
            else
            {
                token = tokenValue.get("beautylogintoken");
                int loginTokenPeriod = ConfigConstant.logintokenPeriod;
                String userInfo = JedisUtils.get(token);
                if(userInfo==null)
                {
                    ResponseDTO<String> responseDto=new ResponseDTO<String>();
                    responseDto.setResult(StatusConstant.FAILURE);
                    responseDto.setErrorInfo(StatusConstant.TOKEN_ERROR);
                    return responseDto;
                }
                JedisUtils.set(token,userInfo,loginTokenPeriod);
            }
        }

        return pjp.proceed();
    }

    //get request headers
    private static Map<String, String> getHeadersInfo(HttpServletRequest request) {
        HashMap<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

}