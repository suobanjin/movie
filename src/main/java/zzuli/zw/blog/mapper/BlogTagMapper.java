package zzuli.zw.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import zzuli.zw.blog.domain.BlogTag;

import java.util.List;

@Mapper
@Repository
public interface BlogTagMapper {
    int insertOne(BlogTag blogTag);
    List<BlogTag> findByBlog(String id);
    int deleteTagsByBlog(String blog);
    int deleteTagsByTag(@Param("tagId") String tagId);
    int deleteTagsByList(List<String> tags);
}
