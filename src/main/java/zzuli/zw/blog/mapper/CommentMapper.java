package zzuli.zw.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import zzuli.zw.blog.domain.Comment;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {
    int insertOne(Comment comment);
    List<Comment> findChildren(String blogId);
    List<Comment> findByParentIdNull(String parentId,String blogId);
    Integer findCountByBlogId(@Param("blogId") String blogId);
    Integer findCommentCount();
}
