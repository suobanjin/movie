package zzuli.zw.blog.config;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * @ClassName: LoginInterceptor
 * @date: 2020/7/10 11:22
 * @author 索半斤
 * @Description: 登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();
        if (StringUtils.isEmpty(requestURI)){
            return false;
        }
         //对登录页面请求放行
        if (requestURI.equals("/admin/login.html") || requestURI.equals("/admin/login.html/") ||
            requestURI.equals("/admin/getKey")){
            return true;
        }
        //如果用户直接请求login而没有带任何的参数，那么就提示非法访问
        if (requestURI.contains("/admin/login")){
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String code = request.getParameter("code");
            if (request.getMethod().equalsIgnoreCase("get")){
                request.setAttribute("msg", "非法的请求方式");
                request.getRequestDispatcher("/admin/login.html").forward(request,response);
                return false;
            }else if (StringUtils.isEmpty(username) && StringUtils.isEmpty(password) && StringUtils.isEmpty(code)){
                request.setAttribute("msg", "非法访问");
                request.getRequestDispatcher("/admin/login.html").forward(request,response);
                return false;
            }else{
                return true;
            }
        }
        //拦截后台管理的相关请求(以admin开头)，判断用户是否有权限进入后台
        if (requestURI.indexOf("/admin") == 0 && requestURI.contains("/admin")){
            Object admin = session.getAttribute("user");
            if (admin == null){
                request.setAttribute("msg", "没有权限访问，请先登录");
                request.getRequestDispatcher("/admin/login.html").forward(request,response);
                return false;
            }
        }
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
