package zzuli.zw.blog.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Locale;

/**
 * @ClassName: WebConfig
 * @date: 2020/7/11 17:33
 * @author 索半斤
 * @Description: 自定义SpringMVC配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * @MethodName: addViewControllers
     * @date: 2020/7/11 17:34
     * @author 索半斤
     * @Description: 用来做路由控制，控制页面的转发
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin/addSort").setViewName("admin/blog/addSort");
        registry.addViewController("/admin/addTag").setViewName("admin/blog/addTag");
        registry.addViewController("/admin/blogType").setViewName("admin/blog/blogTypes");
        registry.addViewController("/admin/blogTag").setViewName("admin/blog/blogTags");
        registry.addViewController("/admin/editSort").setViewName("admin/blog/editType");
        registry.addViewController("/admin/editTag").setViewName("admin/blog/editTag");
        registry.addViewController("/admin/login.html").setViewName("admin/user/login");
        registry.addViewController("/admin/blogVisit.html").setViewName("admin/blog/blogVisit");
    }

    /**
     * @MethodName: addInterceptors
     * @date: 2020/7/11 17:35
     * @author 索半斤
     * @Description: 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).
                addPathPatterns("/admin/**").
                excludePathPatterns("/static/**","/usr/image/**");
        registry.addInterceptor(new IndexInterceptor()).
                addPathPatterns("/","/index.html").
                excludePathPatterns("/static/**","/usr/image/**");
    }
}
