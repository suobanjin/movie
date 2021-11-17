package zzuli.zw.blog.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import zzuli.zw.blog.domain.Blog;
import zzuli.zw.blog.service.interfaces.*;
import java.util.List;
@Controller
public class AdminIndexController {
    private TypesService typesService;
    private TagService tagService;
    private BlogService blogService;
    private CommentService commentService;
    private VisitService visitService;

    @Autowired
    public void setVisitService(VisitService visitService) {
        this.visitService = visitService;
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Autowired
    public void setTypesService(TypesService typesService) {
        this.typesService = typesService;
    }

    @GetMapping("/admin/index.html")
    public String count(Model model){
        int weekCount = visitService.findWeekCount();
        int allCount = visitService.findAllCount();
        model.addAttribute("week", weekCount);
        model.addAttribute("total", allCount);
        Integer typesCount = typesService.findTypesCount();
        model.addAttribute("types", typesCount);
        Integer tagCount = tagService.findTagCount();
        model.addAttribute("tags", tagCount);
        Integer blogCount = blogService.findBlogsCount();
        model.addAttribute("allBlog", blogCount);
        int count = blogService.findCount();
        model.addAttribute("publishBlog", count);
        Integer saveBlogCount = blogService.findSaveBlogsCount();
        model.addAttribute("saveBlog", saveBlogCount);
        model.addAttribute("comment", commentService.findCommentCount());
        Blog blog = blogService.findMostViewsBlog();
        model.addAttribute("blog", blog);
        List<Blog> hotBlog = blogService.findHotBlogs();
        model.addAttribute("hot", hotBlog);
        return "admin/homepage2";
    }

}
