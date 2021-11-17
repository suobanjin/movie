package zzuli.zw.blog.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzuli.zw.blog.domain.Blog;
import zzuli.zw.blog.domain.Page;
import zzuli.zw.blog.domain.Type;
import zzuli.zw.blog.mapper.BlogMapper;
import zzuli.zw.blog.service.interfaces.BlogService;
import zzuli.zw.blog.service.interfaces.BlogTagService;
import zzuli.zw.blog.service.interfaces.CommentService;
import zzuli.zw.blog.service.interfaces.TypesService;
import zzuli.zw.blog.utils.HtmlToText;
import zzuli.zw.blog.utils.MarkdownUtils;
import zzuli.zw.blog.utils.StringUtils;
import zzuli.zw.blog.utils.UUIDUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {
    private BlogMapper blogMapper;
    private BlogTagService blogTagService;
    private TypesService typesService;
    private CommentService commentService;
    private StringRedisTemplate redisTemplate;
    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @Autowired
    public void setTypesService(@Qualifier("typesService") TypesService typesService) {
        this.typesService = typesService;
    }

    @Autowired
    public void setBlogTagService(BlogTagService blogTagService) {
        this.blogTagService = blogTagService;
    }

    @Autowired
    public void setBlogMapper(BlogMapper blogMapper) {
        this.blogMapper = blogMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Blog> findBlogs(String title, String type, boolean isCommend, int page, int limit) {
        if (StringUtils.isNullOrEmptyNotSpace(title)){
            title = null;
        }
        if (StringUtils.isNullOrEmptyNotSpace(type)){
            type = null;
        }
        PageHelper.startPage(page, limit);
        return blogMapper.findBlogs(title, type, isCommend);
    }


    @Override
    @Transactional
    public boolean deleteBlog(String id) {
        int isDelete = blogMapper.deleteBlog(id);
        if (isDelete == 1){
            redisTemplate.boundHashOps("blog").delete(id);
            redisTemplate.boundHashOps("description").delete(id);
        }
        return isDelete == 1;
    }

    @Override
    @Transactional
    public int deleteBlogs(List<String> blog) {
        int isDelete = blogMapper.deleteBlogs(blog);
        if (isDelete >= 1){
            redisTemplate.boundHashOps("blog").delete(blog);
            redisTemplate.boundHashOps("description").delete(blog);
        }
        return isDelete;
    }

    @Override
    @Transactional
    public boolean publishBlog(Blog blog) {
        return publishOrSave(blog, "publish");
    }
    /**
     * @MethodName: publishOrSave
     * @date: 2020/7/16 17:08
     * @author 索半斤
     * @Description: 封装方法，保存或者发布
     */
    private boolean publishOrSave(Blog blog,String method){
        //判断所选类型是否存在，不存在则返回false
        Type type = typesService.findById(blog.getTypeId());
        if (type == null) return false;
        String content = blog.getContent();
        String description = blog.getDescription();
        if (StringUtils.isNullOrEmptyNotSpace(description)){
            String html = MarkdownUtils.markdownToHtml(content);
            String convert = HtmlToText.convert(html);
            String trimAllWhitespace = org.springframework.util.StringUtils.trimAllWhitespace(convert);
            description = trimAllWhitespace.substring(0, 160);
            blog.setDescription(description);
        }
        redisTemplate.boundHashOps("description").put(blog.getId(), description);
        //判断blog的id非空且内容不为空
        if (!StringUtils.isNullOrEmptyNotSpace(blog.getId())){
            //判断blog的id是否正确
            if (blogMapper.findBlogNotNull(blog.getId()) == null) return false;
            //修改更新时间
            blog.setUpdateDate(new Date());
            //修改保存状态
            if (method.equals("save")) {
                blog.setSave(true);
            }else blog.setSave(false);
            //获取tagsId
            List<String> tags = blog.getTagId();
            //如果tags不存在，说明tags为空，初始化tags
            if (tags == null) tags = new ArrayList<>();
            int i = blogTagService.updateTags(tags, blog.getId());
            int flag;
            if (i >= 1){
                flag = blogMapper.updateBlog(blog);
                if (flag >= 1){
                    String html = MarkdownUtils.markdownToHtmlExtensions(blog.getContent());
                    redisTemplate.boundHashOps("blog").put(blog.getId(), html);
                    return true;
                }
                return false;
            }
            return false;
        }else {
            //这种情况属于首次发表的情况
            blog.setId(UUIDUtils.uuid());
            blog.setCreateDate(new Date());
            blog.setUpdateDate(new Date());
            blog.setLike(0);
            if (method.equals("save")) {
                blog.setSave(true);
            }else blog.setSave(false);
            blog.setViews(0);
            blog.setCommentCount(0);
            if (blog.getTagId() != null && blog.getTagId().size() != 0) {
                int i = blogTagService.insertTags(blog.getTagId(), blog.getId());
                if (i == -1 || i == 0) return false;
            }
            int isInsert =  blogMapper.insertOne(blog);
            if (isInsert >= 1) {
                String html = MarkdownUtils.markdownToHtmlExtensions(blog.getContent());
                redisTemplate.boundHashOps("blog").put(blog.getId(), html);
            }
            return isInsert >= 1;
        }
    }


    @Override
    @Transactional
    public boolean saveBlog(Blog blog) {
        return publishOrSave(blog, "save");
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Blog> findIndexInfo(int page, int limit) {
        return findSearchOrIndex(page, limit, null, "index");
    }

    @Override
    @Transactional(readOnly = true)
    public int findCount() {
        return blogMapper.findCount();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Blog> findCommendBlog() {
        return blogMapper.findCommendBlog();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Blog> findSearch(int page, int limit, String query) {
        if (StringUtils.isNullOrEmptyNotSpace(query)) return null;
        if (query.length() > 10){
            query = query.substring(0, 10);
        }
        return findSearchOrIndex(page, limit, query, "query");
    }

    @Override
    @Transactional
    public Blog findDetailBlogById(String id) {
        if (StringUtils.isNullOrEmptyNotSpace(id)) return null;
        Blog blog =  blogMapper.findDetailBlogById(id);
        if (blog == null) return null;
        String html = (String) redisTemplate.boundHashOps("blog").get(blog.getId());
        if (StringUtils.isNullOrEmptyNotSpace(html)){
            html = MarkdownUtils.markdownToHtmlExtensions(blogMapper.findBlogContentById(blog.getId()));
        }
        blog.setContent(html);
        blog.setCommentCount(commentService.findCountByBlogId(id));
        return blog;
    }

    @Override
    @Transactional
    public boolean updateViews(String id) {
        return blogMapper.updateViewsById(id) >= 1;
    }

    @Override
    @Transactional
    public boolean updateLike(String id) {
        return blogMapper.updateLikeById(id) >= 1;
    }

    private Page<Blog> findSearchOrIndex(int page,int limit,String query,String method){
        Page<Blog> blogPage = new Page<>();
        blogPage.setLimit(limit);
        int count = 0;
        if (method.equals("query")) {
            count = blogMapper.findSearchCount(query);
        }else if (method.equals("index")){
            count = blogMapper.findCount();
        }
        blogPage.setRecords(count);
        page = setPageNumber(blogPage, page);
        int pageCode = (page - 1) * limit;
        List<Blog> indexInfo = null;
        if (method.equals("query")){
            indexInfo = blogMapper.findSearch(pageCode, limit,query);
        }else if (method.equals("index")){
            indexInfo = blogMapper.findIndexInfo(pageCode, limit);
        }
        if (indexInfo == null || indexInfo.size() == 0) return null;
        getDescription(indexInfo);
        blogPage.setPageBean(indexInfo);
        return blogPage;
    }
    private void getDescription(List<Blog> list){
        for (Blog blog : list) {
            String description = (String) redisTemplate.boundHashOps("description").get(blog.getId());
            if (description == null){
                description = blogMapper.findBlogDescriptionById(blog.getId());
                redisTemplate.boundHashOps("description").put(blog.getId(), description);
            }
            blog.setDescription(description);
        }
    }
    @Override
    @Transactional(readOnly = true)
    public Page<Blog> findByType(int page,int limit,String typeId){
       return findBlogByTypeOrTag(typeId, page, limit, "type");
    }

    private Page<Blog> findBlogByTypeOrTag(String id,int page,int limit,String method){
        if (StringUtils.isNullOrEmptyNotSpace(id))return null;
        Page<Blog> blogPage = new Page<>();
        blogPage.setLimit(limit);
        int count = 0;
        if (method.equals("type")){
            count = blogMapper.findCountByType(id);
        }else if (method.equals("tag")) count = blogMapper.findCountByTag(id);
        blogPage.setRecords(count);
        page = setPageNumber(blogPage, page);
        int pageCode = (page - 1) * limit;
        List<Blog> blogs = null;
        if (method.equals("type")){
            blogs = blogMapper.findByTypeId(pageCode, limit, id);
        }else if (method.equals("tag")) blogs = blogMapper.findBlogByTag(id, pageCode, limit);
        if (blogs == null || blogs.size() == 0) return null;
        getDescription(blogs);
        blogPage.setPageBean(blogs);
        return blogPage;
    }
    @Override
    public Page<Blog> findBlogByTag(String tagId, int page, int limit) {
       return findBlogByTypeOrTag(tagId, page, limit, "tag");
    }

    @Override
    public Integer findBlogsCount() {
        return blogMapper.findAllBlogCount();
    }

    @Override
    public Integer findSaveBlogsCount() {
        return blogMapper.findSaveBlogCount();
    }

    @Override
    public Blog findMostViewsBlog() {
        return blogMapper.findMostViewsBlog();
    }

    @Override
    public List<Blog> findHotBlogs() {
        return blogMapper.findHotBlogs();
    }

    @Override
    public List<Blog> findTimelineInfo() {
        return blogMapper.findTimelineInfo();
    }

    @Override
    public List<Blog> findDetailList(String typeId,String id,String[] tags) {
        List<Blog> detailList = blogMapper.findDetailList(typeId, id);
        List<Blog> blogByTags = null;
        if (tags != null && tags.length != 0) {
            List<String> list = Arrays.asList(tags);
            blogByTags = blogMapper.findBlogByTags(list,id);
        }
        if (detailList == null || detailList.size() == 0){
            return blogByTags;
        }
        else if (blogByTags == null || blogByTags.size() == 0){
            return detailList;
        }
        List<Blog> finalBlogByTags = blogByTags;
        List<Blog> collect = detailList.parallelStream().
                filter((blog) -> finalBlogByTags.
                        parallelStream().
                        noneMatch(blog1 -> blog1.getTitle().equals(blog.getTitle()))).
                collect(Collectors.toList());
        blogByTags.addAll(collect);
        List<Blog> collect1 = blogByTags.parallelStream().sorted((o1,o2)-> (int) (o2.getViews() - o1.getViews())).collect(Collectors.toList());
        return collect1.parallelStream().limit(8).collect(Collectors.toList());
    }

    @Override
    public List<Blog> findDetailFavoriteList() {
        return blogMapper.findDetailFavoriteList();
    }

    @Override
    public Blog findBefore(String id) {
        return blogMapper.findBefore(id);
    }

    @Override
    public Blog findAfter(String id) {
        return blogMapper.findAfter(id);
    }

    @Override
    public List<Blog> findRandomBlog(String id) {
        List<Blog> blogList = blogMapper.randomBlog();
        Collections.shuffle(blogList);
        return blogList.parallelStream()
                .filter(o1->!o1.getId().equals(id))
                .limit(6)
                .sorted((o1,o2) -> (int) (o2.getViews() - o1.getViews()))
                .collect(Collectors.toList());
    }

    private int setPageNumber(Page<Blog> blogPage,int page){
        if (page >= blogPage.getPages()){
            page = blogPage.getPages();
            blogPage.setNextPage(page);
        }else{
            blogPage.setNextPage(page + 1);
        }
        if (page <= 1){
            page = 1;
            blogPage.setPreviousPage(page);
        }else{
            blogPage.setPreviousPage(page - 1);
        }
        blogPage.setPage(page);
        return page;
    }


    @Override
    @Transactional(readOnly = true)
    public Blog findById(String id) {
        if (StringUtils.isNullOrEmptyNotSpace(id)) return null;
        Blog blog = blogMapper.findById(id);
        if (blog == null) return null;
        String description = (String) redisTemplate.boundHashOps("description").get(blog.getId());
        if (description == null){
            description = blogMapper.findBlogDescriptionById(blog.getId());
            redisTemplate.boundHashOps("description").put(blog.getId(), description);
        }
        blog.setDescription(description);
        return blog;
    }


    @Override
    @Transactional(readOnly = true)
    public Blog findBlogContentById(String id) {
        Blog blog =  blogMapper.findBlogIdAndTitle(id);
        if (blog != null) {
            String blogContent = (String) redisTemplate.boundHashOps("blog").get(blog.getId());
            if (StringUtils.isNullOrEmptyNotSpace(blogContent)) {
                blogContent = blogMapper.findBlogContentById(blog.getId());
                blogContent = MarkdownUtils.markdownToHtmlExtensions(blogContent);
            }
            blog.setContent(blogContent);
            return blog;
        }
        return null;
    }

}
