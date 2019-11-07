package com.sparky.lirenadmin.interceptor;

import com.sparky.lirenadmin.annotations.NeedLogin;
import com.sparky.lirenadmin.utils.TokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {
    private static Logger logger = LoggerFactory.getLogger(LoginHandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.getMethodAnnotation(NeedLogin.class) == null){
                return true;
            }
            String token = request.getHeader("token");
            if (token == null ) {
                logger.error("验证失败，token 为空");
                response.setStatus(401);
                return false;
            }
            if (null == TokenManager.unsign(token, String.class)) {
                logger.error("验证失败，token已过期");
                response.setStatus(401);
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //获取请求是否有上下文，如果有附加到返回值
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
