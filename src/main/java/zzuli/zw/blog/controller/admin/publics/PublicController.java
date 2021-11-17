package zzuli.zw.blog.controller.admin.publics;

import com.github.pagehelper.Page;
import org.springframework.util.StringUtils;
import zzuli.zw.blog.domain.JsonResult;
import zzuli.zw.blog.service.interfaces.publicInterface.PublicService;
import zzuli.zw.blog.utils.RegxUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: PublicController
 * @date: 2020/7/13 10:48
 * @author 索半斤
 * @Description: 标签和分类公用的Controller逻辑封装
 */
public class PublicController {
    /**
     * @MethodName: edit
     * @date: 2020/7/13 10:46
     * @author 索半斤
     * @Description: 编辑
     */
    public static JsonResult<String> edit(String id, String name, PublicService<?> publicService){
        JsonResult<String> jsonResult = new JsonResult<>();
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(name)){
            jsonResult.setCode(2);
            jsonResult.setMsg("异常请求!");
            return jsonResult;
        }
        if (RegxUtils.ifHaveSpecial(name)){
            jsonResult.setCode(2);
            jsonResult.setMsg("不得包含特殊字符");
            return jsonResult;
        }
        boolean isUpdate = publicService.update(id, name);
        if (!isUpdate){
            jsonResult.setCode(2);
            jsonResult.setMsg("修改失败，请稍后再试!");
            return jsonResult;
        }
        jsonResult.setCode(1);
        jsonResult.setMsg("修改成功");
        return jsonResult;
    }
    /**
     * @MethodName: findAll
     * @date: 2020/7/13 10:46
     * @author 索半斤
     * @Description: 查询所有，分页，条件查询
     */
    public static <T> JsonResult<T> findAll(String requestStr,int page,int limit,PublicService<T> publicService){
        if (requestStr != null && (requestStr.trim().equals(""))){
            requestStr = null;
        }
        List<T> allTags = publicService.findAll(requestStr, page, limit);
        JsonResult<T> jsonResult = new JsonResult<>();
        jsonResult.setCode(0);
        jsonResult.setData(allTags);
        if (page != -1 && limit != -1) {
            int total = (int) ((Page<T>) allTags).getTotal();
            jsonResult.setCount(total);
        }
        return jsonResult;
    }
    /**
     * @MethodName: delete
     * @date: 2020/7/13 10:46
     * @author 索半斤
     * @Description: 删除单个
     */
    public static  JsonResult<String> delete(String id,PublicService<?> publicService){
        JsonResult<String> jsonResult = new JsonResult<>();
        if (StringUtils.isEmpty(id)){
            jsonResult.setCode(2);
            jsonResult.setMsg("非法的请求");
            return jsonResult;
        }
        boolean isDelete = publicService.delete(id);
        if (!isDelete){
            jsonResult.setCode(2);
            jsonResult.setMsg("删除失败，请稍后再试!");
            return jsonResult;
        }
        jsonResult.setCode(1);
        jsonResult.setMsg("删除成功!");
        return jsonResult;
    }

    /**
     * @MethodName: batchDelete
     * @date: 2020/7/13 10:47
     * @author 索半斤
     * @Description: 批量删除
     */
    public static JsonResult<String> batchDelete(String[] names,PublicService<?> publicService){
        JsonResult<String> jsonResult = new JsonResult<>();
        if (names.length == 0){
            jsonResult.setCode(2);
            jsonResult.setMsg("非法的请求");
            return jsonResult;
        }
        List<String> list = Arrays.asList(names);
        int i = publicService.delete(list);
        if (i == 0){
            jsonResult.setCode(2);
            jsonResult.setMsg("删除失败，请稍后重试!");
            return jsonResult;
        }
        if (i == names.length){
            jsonResult.setCode(1);
            jsonResult.setMsg("删除成功");
            return jsonResult;
        }
        jsonResult.setCode(3);
        jsonResult.setMsg("部分数据未删除，请重新尝试!");
        return jsonResult;
    }
    /**
     * @MethodName: addOne
     * @date: 2020/7/13 10:47
     * @author 索半斤
     * @Description: 添加单个
     */
    public static <T> JsonResult<T> addOne(PublicService<T> publicService,T t,JsonResult<T> jsonResult){
        T isAdd = publicService.add(t);
        if (isAdd == null){
            jsonResult.setCode(2);
            jsonResult.setMsg("添加失败，请检查后重试!");
            return jsonResult;
        }
        List<T> list = new ArrayList<>();
        list.add(isAdd);
        jsonResult.setCode(1);
        jsonResult.setData(list);
        jsonResult.setMsg("添加成功!");
        return jsonResult;
    }

    /**
     * @MethodName: isTrueString
     * @date: 2020/7/13 10:47
     * @author 索半斤
     * @Description: 判断字符串是否合法
     */
    public static <T> JsonResult<T> isTrueString(String name){
        JsonResult<T> jsonResult;
        if (StringUtils.isEmpty(name)){
            jsonResult = new JsonResult<>();
            jsonResult.setCode(2);
            jsonResult.setMsg("非法的请求!");
            return jsonResult;
        }
        if (RegxUtils.ifHaveSpecial(name)){
            jsonResult = new JsonResult<>();
            jsonResult.setCode(2);
            jsonResult.setMsg("不得包含特殊字符");
            return jsonResult;
        }
       return null;
    }
}
