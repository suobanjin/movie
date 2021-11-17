package zzuli.zw.blog.service.interfaces;

import zzuli.zw.blog.domain.Blog;
import zzuli.zw.blog.domain.Page;

import java.util.List;

public interface BlogService {
    /**
     * @MethodName: findBlogs
     * @date: 2020/7/16 17:14
     * @author 索半斤
     * @Description: 查找所有博客
     */
    List<Blog> findBlogs(String title,String type,boolean isCommend,int page,int limit);
    /**
     * @MethodName: deleteBlog
     * @date: 2020/7/16 17:07
     * @author 索半斤
     * @Description: 删除博客
     */
    boolean deleteBlog(String id);
    /**
     * @MethodName: deletesBlog
     * @date: 2020/7/16 17:07
     * @author 索半斤
     * @Description: 批量删除博客
     */
    int deleteBlogs(List<String> blog);
    /**
     * @MethodName: publishBlog
     * @date: 2020/7/16 17:08
     * @author 索半斤
     * @Description: 发布博客
     */
    boolean publishBlog(Blog blog);
    /**
     * @MethodName: findById
     * @date: 2020/7/16 17:09
     * @author 索半斤
     * @Description: 根据id查找blog
     */
    Blog findById(String id);
    /**
     * @MethodName: findBlogContentById
     * @date: 2020/7/16 17:10
     * @author 索半斤
     * @Description: 根据blog的id查找blog的内容
     */
    Blog findBlogContentById(String id);
    /**
     * @MethodName: saveBlog
     * @date: 2020/7/16 17:13
     * @author 索半斤
     * @Description: 保存博客
     */
    boolean saveBlog(Blog blog);

    /**
     * @MethodName: findIndexInfo
     * @date: 2020/7/17 12:13
     * @author 索半斤
     * @Description: 查找首页的初始化信息
     */
    Page<Blog> findIndexInfo(int page, int limit);

    int findCount();

    List<Blog> findCommendBlog();

    Page<Blog> findSearch(int page,int limit,String query);

    Blog findDetailBlogById(String id);

    boolean updateViews(String id);

    boolean updateLike(String id);
    Page<Blog> findByType(int page,int limit,String typeId);

    Page<Blog> findBlogByTag(String tagId,int page,int limit);

    Integer findBlogsCount();
    Integer findSaveBlogsCount();
    Blog findMostViewsBlog();

    List<Blog> findHotBlogs();

    List<Blog> findTimelineInfo();

    List<Blog> findDetailList(String typeId,String id,String[] tags);
    List<Blog> findDetailFavoriteList();
    Blog findBefore(String id);
    Blog findAfter(String id);
    List<Blog> findRandomBlog(String id);
}
