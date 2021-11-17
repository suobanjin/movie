package zzuli.zw.blog.controller.admin;

import com.wf.captcha.GifCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: VerifyCodeController
 * @date: 2020/7/10 18:25
 * @author 索半斤
 * @Description: 验证码
 */
@Controller("verifyCodeController")
public class VerifyCodeController {

    /**
     * @// TODO: 2020/6/11 获取验证码
     * @param request request
     * @param response response
     * @throws IOException io流异常
     */
    @RequestMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 使用gif验证码
        GifCaptcha gifCaptcha = new GifCaptcha(130,48,4);
        CaptchaUtil.out(gifCaptcha, request, response);
    }
}
