package com.xiaoman.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author shuxiaoman
 * @ClassName LoginInterceptor
 * @date 2019/5/9
 * @Description //TODO 登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    RedisTemplate redisTemplate;


//    private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    /**
     * @Author xiaoman
     * @Description //TODO 登录拦截
     * @Date 2019/5/23
     **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        logger.info(request.getRequestURI().toString());

        Cookie tokenCookie = null;
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    tokenCookie = cookie;
                }
            }
        }

        if (null == tokenCookie) {
            returnJson(response, "2");
//            response.sendRedirect("/login.html");
//            logger.info("登录拦截:浏览器没发送token");
            return false;
        } else {
            if (null == tokenCookie.getValue() || "".equals(tokenCookie.getValue())) {
                returnJson(response, "2");
//                response.sendRedirect("/login.html");
//                logger.info("登录拦截:发送的token为空");
                return false;
            } else {
//                if(0 == redisTemplate.opsForList().range(tokenCookie.getValue()).size()){
                if (!redisTemplate.opsForSet().isMember("tokenMap", tokenCookie.getValue())) {
                    returnJson(response, "3");
//                    response.sendRedirect("/login.html");
//                    logger.info("登录拦截:token在redis中失效");
                    return false;
                } else {
                    return true;
                }
            }

        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        logger.info("postHandle...");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        logger.info("afterCompletion...");
    }


    /**
     * @Author xiaoman
     * @Description //TODO 返回登录错误码
     * @Date 2019/5/26
     **/
    private static void returnJson(HttpServletResponse response, String code) throws Exception {
        response.addHeader("Grain-Full-Code", code);
//        PrintWriter writer = null;
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/html; charset=utf-8");
//        try {
//            writer = response.getWriter();
//            String jsonStr  = JSONObject.toJSONString(bean);
//            writer.print(jsonStr);
//        } catch (IOException e) {
////            logger.error("login of response error",e);
//        } finally {
//            if(writer != null){
//                writer.close();
//            }
//        }
    }

}
