package com.xiaoman.springboot.config.interceptor;

import com.xiaoman.springboot.code.RedisCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @description: 登录拦截器
 * @author: shuxiaoman
 * @time: 2020/7/15 10:15 上午
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    RedisTemplate redisTemplate;


//    private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    /**
     * @description: 登录拦截
     * @author: shuxiaoman
     * @time: 2019/5/23 10:14 上午
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /*logger.info(request.getRequestURI().toString());*/

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
            returnJson(request,response, "2");
            return false;
        } else {
            if (null == tokenCookie.getValue() || "".equals(tokenCookie.getValue())) {
                returnJson(request,response, "2");
                return false;
            } else {
                if (!redisTemplate.opsForSet().isMember(RedisCode.tokenMap.toString(), tokenCookie.getValue())) {
                    returnJson(request,response, "3");
                    return false;
                } else {
                    return true;
                }
            }

        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
//        logger.info("postHandle...");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//        logger.info("afterCompletion...");
    }

    /**
     * @description: 返回登录错误码
     * @author: shuxiaoman
     * @time: 2019/5/26
     */
    private static void returnJson(HttpServletRequest request,HttpServletResponse response, String code) throws Exception {
        System.out.println("登陆拦截："+request.getRequestURI().toString());
        response.addHeader("Grain-Full-Code", code);
        // getOutputStream输出字节，getWriter输出字符
        /*response.getOutputStream().print("you need to login");*/
        response.getWriter().println("you need to login");
        /*response.sendRedirect("/login.html");*/

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
