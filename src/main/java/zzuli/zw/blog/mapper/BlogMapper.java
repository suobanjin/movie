package zzuli.zw.blog.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import zzuli.zw.blog.domain.Archives;
import zzuli.zw.blog.domain.Blog;

import java.util.List;

@Mapper
@Repository
public interface BlogMapper {
    List<Blog> findBlogs(String title,String type,boolean isCommend);
    int deleteBlog(String id);
    int deleteBlogs(List<String> list);
    int insertOne(Blog blog);
    Blog findById(String id);
    String findBlogContentById(@Param("id") String id);
    Blog findBlogNotNull(String id);
    int updateBlog(Blog blog);
    List<Blog> findIndexInfo(int page,int limit);
    int findCount();
    List<Blog> findCommendBlog();
    List<Blog> findSearch(int page,int limit,String query);
    Integer findSearchCount(String query);
    Blog findDetailBlogById(String id);
    int updateViewsById(String id);
    int updateLikeById(String id);
    List<Blog> findByTypeId(@Param("page") int page, @Param("limit") int limit,@Param("typeId") String typeId);
    Integer findCountByType(@Param("typeId") String typeId);
    List<Blog> findBlogByTag(@Param("tagId") String tagId,@Param("page") int page,@Param("limit") int limit);
    Integer findCountByTag(@Param("tagId") String tagId);
    @MapKey("year")
    List<Archives> findArchives();
    Blog findBlogIdAndTitle(String id);
    String findBlogDescriptionById(@Param("id") String id);
    Integer findAllBlogCount();
    Integer findSaveBlogCount();
    Blog findMostViewsBlog();
    List<Blog> findHotBlogs();
    List<Blog> findTimelineInfo();
    List<Blog> findDetailList(@Param("typeId") String typeId,@Param("id") String id);
    List<Blog> findDetailFavoriteList();
    Blog findBefore(@Param("id") String id);
    Blog findAfter(@Param("id") String id);
    List<Blog> findBlogByTags( List<String> tags,@Param("id") String id);
    List<Blog> randomBlog();
}
