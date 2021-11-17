package zzuli.zw.blog.controller.admin;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zzuli.zw.blog.domain.*;
import zzuli.zw.blog.service.interfaces.BlogService;
import zzuli.zw.blog.service.interfaces.UserService;
import zzuli.zw.blog.service.interfaces.publicInterface.PublicService;
import zzuli.zw.blog.utils.StringUtils;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class BlogController {
    private BlogService blogService;
    private PublicService<Type> typesService;
    private PublicService<Tag> tagService;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
   public void setTypesService(@Qualifier("typesService") PublicService<Type> typesService){
        this.typesService = typesService;
    }

    @Autowired
    public void setTagService(@Qualifier("tagService") PublicService<Tag> tagService) {
        this.tagService = tagService;
    }

    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping({"/blogList","/blogList.html"})
    public String blogList(Model model){
        List<Type> types = typesService.findAll(null, -1, -1);
        model.addAttribute("types", types);
        return "admin/blog/blogList";
    }

    @GetMapping("/tableBlog")
    @ResponseBody
    public JsonResult<Blog> tableBlog(@RequestParam(name = "title",required = false,defaultValue = "") String title,
                                      @RequestParam(name = "typeId",required = false,defaultValue = "") String typeId,
                                      @RequestParam(name = "isCommend",required = false,defaultValue = "false") boolean isCommend,
                                      int page,int limit){
        JsonResult<Blog> jsonResult = new JsonResult<>();
        List<Blog> blogs = blogService.findBlogs(title, typeId, isCommend, page, limit);
        int count = (int)(((Page<Blog>)blogs).getTotal());
        jsonResult.setCode(0);
        jsonResult.setCount(count);
        jsonResult.setData(blogs);
        return jsonResult;
    }

    @GetMapping("/deleteBlog/{id}")
    @ResponseBody
    public JsonResult<String> deleteBlog(@PathVariable(name = "id") String id){
        JsonResult<String> jsonResult = new JsonResult<>();
        if (StringUtils.isNullOrEmptyNotSpace(id)){
            jsonResult.setCode(2);
            jsonResult.setMsg("非法的访问");
            return jsonResult;
        }
        boolean isDelete = blogService.deleteBlog(id);
        if (!isDelete){
            jsonResult.setCode(2);
            jsonResult.setMsg("删除失败，请稍后尝试");
            return jsonResult;
        }
        jsonResult.setCode(1);
        return jsonResult;
    }
    @PostMapping("/deleteBlogs")
    @ResponseBody
    public JsonResult<String> deleteBlogs(@RequestParam(name = "blogs[]") String[] blogs){
        JsonResult<String> jsonResult = new JsonResult<>();
        if (blogs.length == 0){
            jsonResult.setCode(2);
            jsonResult.setMsg("非法的请求参数");
            return jsonResult;
        }
        for (String blog : blogs) {
            if (StringUtils.isNullOrEmptyNotSpace(blog)){
                jsonResult.setCode(2);
                jsonResult.setMsg("非法的请求参数");
                return jsonResult;
            }
        }
        List<String> list = Arrays.asList(blogs);
        int isDelete = blogService.deleteBlogs(list);
        if (isDelete != list.size()){
            jsonResult.setCode(2);
            jsonResult.setMsg("请求参数异常，部分数据未正确删除");
            return jsonResult;
        }
        jsonResult.setCode(1);
        return jsonResult;
    }

    @GetMapping({"/writeBlog.html","/writeBlog"})
    public String writeBlog(Model model){
        List<Type> types = typesService.findAll(null, -1, -1);
        model.addAttribute("types", types);
        List<Tag> tags = tagService.findAll(null, -1, -1);
        model.addAttribute("tags", tags);
        return "admin/blog/writeBlog";
    }

    @PostMapping("/blog/publish")
    @ResponseBody
    public JsonResult<String> publish(@Validated Blog blog, BindingResult bindingResult, @RequestParam(name = "tagIds[]",required = false) String[] tagIds, HttpSession session){
        return publishOrSave(blog, bindingResult, tagIds, "发布失败了!", "publish",session);
    }

    @PostMapping("/blog/save")
    @ResponseBody
    public JsonResult<String> save(@Validated Blog blog,BindingResult bindingResult,@RequestParam(name = "tagIds[]") String[] tagIds,HttpSession session){
        return publishOrSave(blog, bindingResult, tagIds, "保存失败了", "save",session);
    }

    private JsonResult<String> publishOrSave(Blog blog,BindingResult bindingResult,
                                             String[] tagIds,String message,String method,
                                             HttpSession session){
        JsonResult<String> jsonResult = hasError(bindingResult);
        if (jsonResult != null) return jsonResult;
        jsonResult = new JsonResult<>();
        if (tagIds != null && tagIds.length > 0) {
            List<String> list = arraysToList(tagIds);
            Set<String> set = new HashSet<>(list);
            if (list.size() != set.size()) {
                list = new ArrayList<>(set);
            }
            blog.setTagId(list);
        }
        User user = (User) session.getAttribute("user");
        if (StringUtils.isNullOrEmptyNotSpace(blog.getAuthor())){
            blog.setAuthor(user.getNickName());
        }
        boolean success;
        if (method.equals("save")) {
            success = blogService.saveBlog(blog);
        }else {
            success = blogService.publishBlog(blog);
        }
        if (!success){
            jsonResult.setCode(2);
            jsonResult.setMsg(message);
            return jsonResult;
        }
        jsonResult.setCode(1);
        return jsonResult;
    }

    private <T> List<T> arraysToList(T[] t){
        if (t != null && t.length != 0){
            return Arrays.asList(t);
        }
        return null;
    }

    private JsonResult<String> hasError(BindingResult bindingResult){
        return getStringJsonResult(bindingResult);
    }

    static JsonResult<String> getStringJsonResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            JsonResult<String> jsonResult = new JsonResult<>();
            List<ObjectError> errors = bindingResult.getAllErrors();
            StringBuffer sb = new StringBuffer();
            sb.append("[");
            for (int i = 0; i < errors.size(); i++) {
                String defaultMessage = errors.get(i).getDefaultMessage();
                sb.append(defaultMessage);
                if (i != errors.size() - 1){
                    sb.append(",");
                }
            }
            sb.append("]");
            String errorMsg =  sb.toString();
            if (errorMsg.length() == 2){
                errorMsg = "[请求异常，请检查参数是否正确]";
            }
            jsonResult.setCode(2);
            jsonResult.setMsg(errorMsg);
            return jsonResult;
        }
        return null;
    }

    @GetMapping("/blog/editBlog/{id}")
    public String editBlog(@PathVariable(name = "id") String id,Model model){
        Blog blog = blogService.findById(id);
        List<Tag> tagList = blog.getTags();
        List<String> tagIds = new ArrayList<>();
        for (Tag tag : tagList) {
            tagIds.add(tag.getId());
        }
        model.addAttribute("tagIds", tagIds);
        model.addAttribute("blog", blog);
        List<Type> types = typesService.findAll(null, -1, -1);
        model.addAttribute("types", types);
        List<Tag> tags = tagService.findAll(null, -1, -1);
        model.addAttribute("tags", tags);
        return "admin/blog/editBlog";


    }

    @GetMapping("/blog/preBlog/{id}")
    public String preBlog(@PathVariable(name = "id") String id,Model model){
        Blog byId = blogService.findBlogContentById(id);
        if (byId == null) return "error/404";
        model.addAttribute("blog", byId);
        return "admin/blog/preBlog";
    }

    @GetMapping("/blog/keepAlive")
    public void keepAlive(HttpSession session){
        Object user = session.getAttribute("user");
        if (user == null) {
            User userInfo = userService.findIndexUserInfo();
            session.setAttribute("user", userInfo);
        }
    }

}
