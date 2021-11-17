package zzuli.zw.blog.controller.admin;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zzuli.zw.blog.domain.*;
import zzuli.zw.blog.service.interfaces.MovieService;
import zzuli.zw.blog.utils.StringUtils;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class MovieController {
    private MovieService movieService;

    @Autowired
    public void setBlogService(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping({"/movieList","/movieList.html"})
    public String blogList(){
        return "admin/blog/movieList";
    }

    @GetMapping("/tableMovie")
    @ResponseBody
    public JsonResult<Movie> tableBlog(int page,int limit){
        JsonResult<Movie> jsonResult = new JsonResult<>();
        List<Movie> movies = movieService.findMovies(page, limit);
        int count = (int)(((Page<Movie>)movies).getTotal());
        jsonResult.setCode(0);
        jsonResult.setCount(count);
        jsonResult.setData(movies);
        return jsonResult;
    }

    @GetMapping("/deleteMovie/{id}")
    @ResponseBody
    public JsonResult<String> deleteMovie(@PathVariable(name = "id") String id){
        JsonResult<String> jsonResult = new JsonResult<>();
        if (StringUtils.isNullOrEmptyNotSpace(id)){
            jsonResult.setCode(2);
            jsonResult.setMsg("非法的访问");
            return jsonResult;
        }
        boolean isDelete = movieService.deleteMovie(id);
        if (!isDelete){
            jsonResult.setCode(2);
            jsonResult.setMsg("删除失败，请稍后尝试");
            return jsonResult;
        }
        jsonResult.setCode(1);
        return jsonResult;
    }
    @PostMapping("/deleteMovies")
    @ResponseBody
    public JsonResult<String> deleteBlogs(@RequestParam(name = "movies[]") String[] movies){
        JsonResult<String> jsonResult = new JsonResult<>();
        if (movies.length == 0){
            jsonResult.setCode(2);
            jsonResult.setMsg("非法的请求参数");
            return jsonResult;
        }
        for (String movie : movies) {
            if (StringUtils.isNullOrEmptyNotSpace(movie)){
                jsonResult.setCode(2);
                jsonResult.setMsg("非法的请求参数");
                return jsonResult;
            }
        }
        List<String> list = Arrays.asList(movies);
        int isDelete = movieService.deleteMovies(list);
        if (isDelete != list.size()){
            jsonResult.setCode(2);
            jsonResult.setMsg("请求参数异常，部分数据未正确删除");
            return jsonResult;
        }
        jsonResult.setCode(1);
        return jsonResult;
    }

    @GetMapping({"/writeMovie.html","/writeMovie"})
    public String writeBlog(){
        return "admin/blog/writeMovie";
    }

    @PostMapping("/movie/publish")
    @ResponseBody
    public JsonResult<String> publish(@Validated Movie blog, BindingResult bindingResult, HttpSession session){
        return publishOrSave(blog, bindingResult, session);
    }

    private JsonResult<String> publishOrSave(Movie movie, BindingResult bindingResult,
                                             HttpSession session){
        JsonResult<String> jsonResult = hasError(bindingResult);
        if (jsonResult != null) return jsonResult;
        jsonResult = new JsonResult<>();
        User user = (User) session.getAttribute("user");
        if (StringUtils.isNullOrEmptyNotSpace(movie.getAuthor())){
            movie.setAuthor(user.getNickName());
        }
        boolean success;
        success = movieService.publishMovie(movie);
        if (!success){
            jsonResult.setCode(2);
            jsonResult.setMsg("发布失败了!");
            return jsonResult;
        }
        jsonResult.setCode(1);
        return jsonResult;
    }

    private JsonResult<String> hasError(BindingResult bindingResult){
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

    @GetMapping("/movie/preMovie/{id}")
    public String preMovie(@PathVariable(name = "id") String id,Model model){
        Movie byId = movieService.findById(id);
        if (byId == null) return "error/404";
        model.addAttribute("movie", byId);
        return "admin/blog/preMovie";
    }
    @GetMapping("/movie/editMovie/{id}")
    public String editMovie(@PathVariable(name = "id") String id,Model model){
        Movie movie = movieService.findById(id);
        if (movie == null) return "error/404";
        model.addAttribute("movie", movie);
        return "admin/blog/editMovie";
    }

    @PostMapping("/movie/edit/publish")
    @ResponseBody
    public JsonResult<String> edit(Movie movie,BindingResult bindingResult){
        JsonResult<String> jsonResult = new JsonResult<>();
        if (bindingResult.hasErrors()){
            jsonResult.fail("服务器异常");
            return jsonResult;
        }
        boolean isEdit = movieService.editMovie(movie);
        if (isEdit){
            jsonResult.ok();
            return jsonResult;
        }
        jsonResult.fail();
        return jsonResult;
    }
}
