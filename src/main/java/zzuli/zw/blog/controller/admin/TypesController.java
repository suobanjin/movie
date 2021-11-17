package zzuli.zw.blog.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zzuli.zw.blog.controller.admin.publics.PublicController;
import zzuli.zw.blog.domain.JsonResult;
import zzuli.zw.blog.domain.Type;
import zzuli.zw.blog.service.interfaces.publicInterface.PublicService;

/**
 * @ClassName: TypeController
 * @date: 2020/7/11 14:55
 * @author 索半斤
 * @Description: 类别管理控制器
 */
@Controller
@RequestMapping("/admin")
public class TypesController {
   private PublicService<Type> publicService;

   @Autowired
    public void setPublicService(@Qualifier("typesService") PublicService<Type> publicService) {
        this.publicService = publicService;
    }

    @RequestMapping(value = "/types",method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<Type> types(@RequestParam(required = false,name = "type") String type,
                                  @RequestParam(name = "page") int page,
                                  @RequestParam(name = "limit") int limit){
        return PublicController.findAll(type, page, limit, publicService);
    }

    @GetMapping("/editOneType/{id}/{typeName}")
    @ResponseBody
    public JsonResult<String> edit(@PathVariable(name = "id")String id,
                                   @PathVariable(name = "typeName") String typeName){
       return PublicController.edit(id, typeName, publicService);
    }
    @GetMapping("/deleteType/{id}")
    @ResponseBody
    public JsonResult<String> deleteType(@PathVariable(name = "id") String id){
        return PublicController.delete(id, publicService);
    }

    @PostMapping("/batchDeleteType")
    @ResponseBody
    public JsonResult<String> batchDelete(@RequestParam(name = "types[]") String[] types){
        return PublicController.batchDelete(types, publicService);
    }

    @GetMapping("/addOneType/{type}")
    @ResponseBody
    public JsonResult<Type> addOneType(@PathVariable(name = "type") String type){
        JsonResult<Type> jsonResult = PublicController.isTrueString(type);
        if (jsonResult != null) return jsonResult;
        jsonResult = new JsonResult<>();
        Type newType = new Type();
        newType.setTypeName(type);
        return PublicController.addOne(publicService, newType,jsonResult);
    }
}
