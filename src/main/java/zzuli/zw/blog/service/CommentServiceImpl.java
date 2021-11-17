package zzuli.zw.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zzuli.zw.blog.domain.Comment;
import zzuli.zw.blog.mapper.CommentMapper;
import zzuli.zw.blog.service.interfaces.CommentService;
import zzuli.zw.blog.utils.UUIDUtils;
import zzuli.zw.blog.utils.sensiUtils.SensitiveFilter;

import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentMapper commentMapper;
    private List<Comment> tempReplys = new ArrayList<>();
    private SensitiveFilter filter;

    @Autowired
    public void setFilter(SensitiveFilter filter) {
         this.filter = filter;
    }

    @Autowired
    public void setCommentMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Override
    public List<Comment> findAllComments(String blogId) {
        return findByParentIdNull("-1", blogId);
    }

    /**
     * @MethodName: saveOneComment
     * @date: 2020/7/19 22:59
     * @author 索半斤
     * @Description: 保存用户的评论信息
     */
    @Override
    public boolean saveOneComment(Comment comment) {
        comment.setId(UUIDUtils.uuid());
        comment.setCreateTime(new Date());
        String content = comment.getContent();
        String filterString = filter.filter(content, '*');
        if (!filterString.equals(content)){
            comment.setContent("用户输入包含违禁词,已被系统删除!");
        }
        return commentMapper.insertOne(comment) >= 1;
    }

    /**
     * @MethodName: findByParentIdNull
     * @date: 2020/7/19 23:00
     * @author 索半斤
     * @Description: 查找父级评论，父级评论是指最上层的评论，它的parentId为 -1
     */
    @Override
    public List<Comment> findByParentIdNull(String parentId, String blogId) {
        //查询出父节点
        List<Comment> comments = commentMapper.findByParentIdNull(parentId, blogId);
        List<Comment> children = commentMapper.findChildren(blogId);
        for (Comment comment : comments) {
            String id = comment.getId();
            String parentNickname1 = comment.getNickname();
            //父级评论的一级评论
            List<Comment> childComments = findByParentIdNotNull(id, children);
            //查询出子评论
            combineChildren(childComments, parentNickname1,children);
            comment.setReplyComments(tempReplys);
            tempReplys = new ArrayList<>();
        }
        return comments;
    }

    @Override
    public Integer findCountByBlogId(String blogId) {
        return commentMapper.findCountByBlogId(blogId);
    }

    @Override
    public Integer findCommentCount() {
        return commentMapper.findCommentCount();
    }

    /**
     * @MethodName: findByParentIdNotNull
     * @date: 2020/7/19 23:01
     * @author 索半斤
     * @Description: 查找父级id不为零的子集评论
     */
    private List<Comment> findByParentIdNotNull(String id, List<Comment> children) {
        Set<Comment> firstComments = new HashSet<>();
        ListIterator<Comment> listIterator = children.listIterator();
        while (listIterator.hasNext()) {
            Comment next = listIterator.next();
            if (id.equals(next.getParentCommentId())) {
                firstComments.add(next);
                listIterator.remove();
            }
        }
        return new ArrayList<>(firstComments);
    }

    /**
     * @MethodName: combineChildren
     * @date: 2020/7/19 23:02
     * @author 索半斤
     * @Description: 查找第一层子评论以及第一层评论的子评论
     */
    private void combineChildren(List<Comment> childComments, String parentNickname1,List<Comment> children) {
        //判断是否有一级子回复
        if (childComments.size() > 0) {
            //循环找出子评论的id
            for (Comment childComment : childComments) {
                String parentNickname = childComment.getNickname();
                childComment.setParentNickname(parentNickname1);
                tempReplys.add(childComment);
                String childId = childComment.getId();
                //查询二级以及所有子集回复
                recursively(childId, parentNickname,children);
            }
        }
    }

    /**
     * @MethodName: recursively
     * @date: 2020/7/19 23:02
     * @author 索半斤
     * @Description: 递归查找出所有的深层子评论信息
     */
    private void recursively(String childId,String parentNickname1,List<Comment> children) {
        //根据子一级评论的id找到子二级评论
        List<Comment> replayComments = findByParentIdNotNull(childId,children);

        if(replayComments.size() > 0){
            for(Comment replayComment : replayComments){
                String parentNickname = replayComment.getNickname();
                replayComment.setParentNickname(parentNickname1);
                String replayId = replayComment.getId();
                tempReplys.add(replayComment);
                //循环迭代找出子集回复
                recursively(replayId,parentNickname,children);
            }
        }
    }
}
