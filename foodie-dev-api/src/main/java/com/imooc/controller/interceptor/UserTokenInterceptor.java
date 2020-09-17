package com.imooc.controller.interceptor;

import com.imooc.utils.ApiResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.RedisOperator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description:TODO
 * Create Time:2020/9/17 0017 下午 2:42
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class UserTokenInterceptor implements HandlerInterceptor {

    public static final String REDIS_USER_TOKEN = "redis_user_token";
    @Autowired
    protected RedisOperator redisOperator;

    /**
     * 进入到Contoller之前
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进去拦截器");
        String headUserId = request.getHeader("headerUserId");
        String headUserToken = request.getHeader("headerUserToken");
        if (StringUtils.isBlank(headUserId) || StringUtils.isBlank(headUserToken)) {
            rerurnErrorResponse(response,ApiResult.errorMsg("userId或token不存在，请登录"));
            return false;
        }

        String token = redisOperator.get(REDIS_USER_TOKEN + ":" + headUserId);
        if (StringUtils.isBlank(token)) {
            rerurnErrorResponse(response,ApiResult.errorMsg("没有token缓存，请登录"));
            return false;
        }else{
            if (!token.equals(headUserToken)) {
                rerurnErrorResponse(response,ApiResult.errorMsg("账号在异地登录，请重新登录"));
                return false;
            }

        }
        return true;
    }

    /**
     * Controller之后，渲染之前
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        System.out.println("Controller之后，渲染之前");
    }

    /**
     * 渲染之后
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        System.out.println("渲染之后");

    }

    private void rerurnErrorResponse(HttpServletResponse response, ApiResult apiResult) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json");
        OutputStream outputStream=null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(JsonUtils.objectToJson(apiResult).getBytes("utf-8"));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
