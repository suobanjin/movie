package zzuli.zw.blog.controller.admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zzuli.zw.blog.controller.admin.publics.PublicController;
import zzuli.zw.blog.domain.JsonResult;
import zzuli.zw.blog.domain.Tag;
import zzuli.zw.blog.service.interfaces.TagService;
/**
 * @ClassName: TypeController
 * @date: 2020/7/11 14:55
 * @author 索半斤
 * @Description: 标签管理控制器
 */
@Controller
@RequestMapping("/admin")
public class TagController {
    private TagService publicService;

    @Autowired
    public void setPublicService(@Qualifier("tagService") TagService publicService) {
        this.publicService = publicService;
    }


    @RequestMapping(value = "/tags",method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<Tag> types(@RequestParam(required = false,name = "tag") String tag,
                                  @RequestParam(name = "page") int page,
                                  @RequestParam(name = "limit") int limit){
        return PublicController.findAll(tag, page, limit, publicService);
    }

    @GetMapping("/editOneTag/{id}/{tagName}")
    @ResponseBody
    public JsonResult<String> edit(@PathVariable(name = "id")String id,
                                   @PathVariable(name = "tagName") String tagName){
        return PublicController.edit(id, tagName, publicService);
    }
    @GetMapping("/deleteTag/{id}")
    @ResponseBody
    public JsonResult<String> deleteType(@PathVariable(name = "id") String id){
        return PublicController.delete(id, publicService);
    }


    @PostMapping("/batchDeleteTag")
    @ResponseBody
    public JsonResult<String> batchDelete(@RequestParam(name = "tags[]") String[] tags){
        return PublicController.batchDelete(tags, publicService);
    }

    @GetMapping("/addOneTag/{tag}")
    @ResponseBody
    public JsonResult<Tag> addOneType(@PathVariable(name = "tag") String tag){
        JsonResult<Tag> jsonResult = PublicController.isTrueString(tag);
        if (jsonResult != null) return jsonResult;
        jsonResult = new JsonResult<>();
        Tag newTag = new Tag();
        newTag.setTagName(tag);
        return PublicController.addOne(publicService, newTag,jsonResult);
    }
}
