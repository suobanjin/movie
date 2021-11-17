package zzuli.zw.blog.controller.admin;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zzuli.zw.blog.domain.JsonResult;
import zzuli.zw.blog.domain.Visit;
import zzuli.zw.blog.service.interfaces.VisitService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class VisitController {
    private VisitService visitService;
    @Autowired
    public void setVisitService(VisitService visitService) {
        this.visitService = visitService;
    }

    @RequestMapping("/visit")
    @ResponseBody
    public JsonResult<Visit> visit(int page,int limit){
        JsonResult<Visit> jsonResult = new JsonResult<>();
        List<Visit> all = visitService.findAll(page, limit);
        int count = (int)(((Page<Visit>)all).getTotal());
        jsonResult.setCode(0);
        jsonResult.setCount(count);
        jsonResult.setData(all);
        return jsonResult;
    }
}
