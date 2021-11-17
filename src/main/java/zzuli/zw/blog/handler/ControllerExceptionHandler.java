package zzuli.zw.blog.handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import zzuli.zw.blog.exception.PageNotFoundException;
import zzuli.zw.blog.exception.SearchNotFountException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e){
        logger.error("Request Url : {} , Exception : {}",request.getRequestURL(),e);
        ModelAndView modelAndView = new ModelAndView();
        if (e instanceof PageNotFoundException){
            modelAndView.setViewName("error/404");
            return modelAndView;
        }else if (e instanceof SearchNotFountException){
            modelAndView.setViewName("blog/searchFail");
            return modelAndView;
        }
        modelAndView.addObject("url", request.getRequestURL());
        modelAndView.addObject("exception", e);
        modelAndView.setViewName("error/error");
        return modelAndView;
    }
}
