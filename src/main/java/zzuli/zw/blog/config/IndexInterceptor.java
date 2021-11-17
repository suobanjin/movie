package zzuli.zw.blog.config;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import zzuli.zw.blog.utils.DateUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class IndexInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    @SuppressWarnings("unchecked")
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String s = DateUtils.weekOfYear();
        ServletContext servletContext = request.getServletContext();
        Set<String> attribute = (Set<String>) servletContext.getAttribute(s);
        Set<String> total = (Set<String>) servletContext.getAttribute("total");
        if (DateUtils.weekOfMonth(new Date()).equals("1")){
            if (attribute != null){
                attribute = null;
            }
        }
        setAttribute(attribute, request, servletContext, s);
        setAttribute(total, request, servletContext, "total");
    }

    private void setAttribute(Set<String> set,HttpServletRequest request,ServletContext servletContext,String s){
        if (set == null){
            set = new HashSet<>();
        }
        set.add(request.getRemoteAddr());
        servletContext.setAttribute(s, set );
    }
}
