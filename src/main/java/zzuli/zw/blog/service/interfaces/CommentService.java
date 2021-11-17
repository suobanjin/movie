package zzuli.zw.blog.service.interfaces;

import zzuli.zw.blog.domain.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findAllComments(String blogId);
    boolean saveOneComment(Comment comment);
    List<Comment> findByParentIdNull(String parentId,String blogId);
    Integer findCountByBlogId(String blogId);
    Integer findCommentCount();
}
