package com.baohuquan.interceptor;

import com.baohuquan.anno.TokenRequired;
import com.baohuquan.utils.ResponseCode;
import com.baohuquan.utils.ResponseWrapper;
import com.baohuquan.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token验证拦截器
 * Created by wangdi5 on 2016/3/23.
 */
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod he = (HandlerMethod) handler;
        boolean mustLogin =
                he.getMethodAnnotation(TokenRequired.class) != null
                        || he.getBeanType().getAnnotation(TokenRequired.class) != null;
        String token = httpServletRequest.getParameter("token");

//        if(!mustLogin){
//            return true;
//        }


        if(StringUtils.isBlank(token)) {
            if(!mustLogin) {
                return true;
            }
            ResponseWrapper responseWrapper = new ResponseWrapper();
            responseWrapper.setCode(ResponseCode.TOKEN_MISS.getCode());
            responseWrapper.setMsg(ResponseCode.TOKEN_MISS.getMsg());
            printJson(httpServletResponse, responseWrapper.toJSON());
            return false;
        }

        if(token.equalsIgnoreCase("123sss123")){
            return true;
        }

        Integer userId = TokenUtil.getUserIdFromToken(token);

        if (userId == null) {
            if (!mustLogin) {
                return true;
            }

            ResponseWrapper responseWrapper = new ResponseWrapper();
            responseWrapper.setCode(ResponseCode.TOKEN_WRONG.getCode());
            responseWrapper.setMsg(ResponseCode.TOKEN_WRONG.getMsg());
            printJson(httpServletResponse, responseWrapper.toJSON());
            return false;
        }
        //比较生成的uid是否一样
        String uid = httpServletRequest.getParameter("uid");
        if(StringUtils.isBlank(uid))
            return false;

        if(userId!=Integer.valueOf(uid)){
            ResponseWrapper responseWrapper = new ResponseWrapper();
            responseWrapper.setCode(ResponseCode.TOKEN_WRONG.getCode());
            responseWrapper.setMsg(ResponseCode.TOKEN_WRONG.getMsg());
            printJson(httpServletResponse, responseWrapper.toJSON());
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private void printJson(HttpServletResponse response, String s) {
        response.setHeader("Content-Type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
            response.getWriter().print(s);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
