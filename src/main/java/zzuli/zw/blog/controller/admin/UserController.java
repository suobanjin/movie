package zzuli.zw.blog.controller.admin;

import cn.hutool.crypto.asymmetric.RSA;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zzuli.zw.blog.domain.JsonResult;
import zzuli.zw.blog.domain.User;
import zzuli.zw.blog.service.interfaces.UserService;
import zzuli.zw.blog.utils.CookieAge;
import zzuli.zw.blog.utils.KeyUtils;
import zzuli.zw.blog.utils.MD5Utils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class UserController {
    private UserService userService;
    private StringRedisTemplate redisTemplate;
    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * @MethodName: login
     * @date: 2020/7/11 11:28
     * @author 索半斤
     * @Description: 登录，校验，记住密码，验证码，密码加密
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<String> login(@Validated User user, BindingResult bindingResult,
                                    @RequestParam(name = "code") String code,
                                    @RequestParam(name = "remember",required = false) Boolean remember,
                                    HttpServletResponse response,
                                    HttpServletRequest request,
                                    String publicKey){
        JsonResult<String> jsonResult = new JsonResult<>();
        //验证码
        if (!CaptchaUtil.ver(code,request)){
            CaptchaUtil.clear(request);
            jsonResult.setCode(0);
            jsonResult.setMsg("验证码错误！");
            return jsonResult;
        }
        //校验
        if (bindingResult.hasErrors()){
            List<ObjectError> errors = bindingResult.getAllErrors();
            Set<String> errorMessages = new HashSet<>();
            for (ObjectError error : errors) {
                errorMessages.add(error.getDefaultMessage());
            }
            if (errorMessages.size() != 0){
                jsonResult.setCode(0);
                String result = Arrays.toString(errorMessages.toArray());
                jsonResult.setMsg(result);
                return jsonResult;
            }
        }
        String password = KeyUtils.checkPassword(user.getPassword(), publicKey,redisTemplate);
        if (password == null){
            jsonResult.fail("未知的错误!");
            CaptchaUtil.clear(request);
            return jsonResult;
        }
        if (password.length() < 6 || password.length() > 16){
            jsonResult.fail("密码错误!");
            CaptchaUtil.clear(request);
            return jsonResult;
        }
        //密码加密
        String md5Password = MD5Utils.encode(password);
        //登录
        user.setPassword(md5Password);
        User login = userService.login(user);
        if (login == null){
            jsonResult.setCode(0);
            jsonResult.setMsg("用户名或者密码错误!");
            CaptchaUtil.clear(request);
            return jsonResult;
        }
        jsonResult.setCode(1);
        CaptchaUtil.clear(request);
        //记住密码
        if (remember != null && remember){
            Cookie cookie01 = new Cookie("username", user.getUsername());
            cookie01.setMaxAge(CookieAge.WEEK.Int());
            response.addCookie(cookie01);
            Cookie cookie02 = new Cookie("password", password);
            cookie02.setMaxAge(CookieAge.WEEK.Int());
            response.addCookie(cookie02);
            Cookie cookie03 = new Cookie("remember", "true");
            cookie03.setMaxAge(CookieAge.WEEK.Int());
            response.addCookie(cookie03);
        }else{
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(CookieAge.ZERO.Int());
                response.addCookie(cookie);
            }
        }
        login.setPassword(password);
        request.getSession().setAttribute("user", login);
        return jsonResult;
    }

    /**
     * @MethodName: logout
     * @date: 2020/7/11 12:04
     * @author 索半斤
     * @Description: 注销
     */
    @GetMapping("/logout")
    public String logout(HttpSession session){
        Object user = session.getAttribute("user");
        if (user == null){
            return "redirect:/admin/login.html";
        }
        session.removeAttribute("user");
        return "redirect:/admin/login.html";
    }

    @GetMapping(value = "/getKey")
    @ResponseBody
    public JsonResult<String> getKeyOfRSA() {
        JsonResult<String> jsonResult = new JsonResult<>();
        RSA rsa = new RSA();
        String privateKeyBase64 = rsa.getPrivateKeyBase64();
        String publicKeyBase64 = rsa.getPublicKeyBase64();
        // 使用redis进行保存公钥私钥对
        redisTemplate.boundValueOps(publicKeyBase64).set(privateKeyBase64);
        jsonResult.ok(publicKeyBase64);
        return jsonResult;
    }
}
