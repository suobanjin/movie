package zzuli.zw.blog.controller.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import zzuli.zw.blog.domain.JsonResult;
import zzuli.zw.blog.utils.UploadUtils;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UploadController {
    @Value("${baseFilePath}")
    private String BASE_FILE_PATH ;

    /**
     * @MethodName: upload
     * @date: 2020/12/14 15:25
     * @author 索半斤
     * @Description: 用来上传图片
     */
    @RequestMapping(value = "/admin/upload",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public JsonResult<String> images(MultipartFile file, HttpServletRequest request){
        String imageUrl = UploadUtils.upload(file, BASE_FILE_PATH, request);
        JsonResult<String> jsonResult = new JsonResult<>();
        if (imageUrl == null){
            jsonResult.setCode(2);
            jsonResult.setMsg("上传错误!");
        }
        jsonResult.setCode(1);
        jsonResult.setMsg(imageUrl);
        return jsonResult;
    }

    /**
     * @MethodName: uploadMovie
     * @date: 2020/12/14 15:24
     * @author 索半斤
     * @Description: 该方法用来上传影片
     */
    @RequestMapping(value = "/admin/uploadMovie",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public JsonResult<String> movie(MultipartFile file,HttpServletRequest request){
        String movieUrl = UploadUtils.uploadMovie(file, BASE_FILE_PATH, request);
        JsonResult<String> jsonResult = new JsonResult<>();
        if (movieUrl == null){
            jsonResult.setCode(2);
            jsonResult.setMsg("上传错误!");
        }
        jsonResult.setCode(1);
        jsonResult.setMsg(movieUrl);
        return jsonResult;

    }
}
