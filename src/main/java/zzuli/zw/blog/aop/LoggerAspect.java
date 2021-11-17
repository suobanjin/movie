package zzuli.zw.blog.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zzuli.zw.blog.domain.RequestLog;
import javax.servlet.http.HttpServletRequest;


/**
 * @ClassName: LoggerAspect
 * @date: 2020/7/9 19:35
 * @author 索半斤
 * @Description: 通过切面记录日志
 */
@Component
@Aspect
public class LoggerAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private HttpServletRequest request;

    @Autowired
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Pointcut("execution(* zzuli.zw.blog.controller.*.*.*(..))")
    public void joinPoints(){}

    @Before("joinPoints()")
    public void doBefore(JoinPoint joinPoint){
        StringBuffer requestURL = request.getRequestURL();
        String ip = request.getRemoteAddr();
        String method =joinPoint.getSignature().getDeclaringTypeName() + "  method:" +request.getMethod();
        Object[] objects = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(requestURL.toString(), ip, method, objects);
        logger.info("Request : {}",requestLog);
    }

    @AfterReturning(returning = "result",pointcut = "joinPoints()")
    public void doReturn(Object result){
        logger.info("return : {}",result);
    }

    @AfterThrowing(throwing ="exception",pointcut = "joinPoints()")
    public void doException(Exception exception){
        logger.error("logException",exception);
    }


}
