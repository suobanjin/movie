package zzuli.zw.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import zzuli.zw.blog.domain.BlogTag;
import zzuli.zw.blog.domain.Tag;
import zzuli.zw.blog.mapper.BlogTagMapper;
import zzuli.zw.blog.service.interfaces.BlogTagService;
import zzuli.zw.blog.service.interfaces.publicInterface.PublicService;
import zzuli.zw.blog.utils.UUIDUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 索半斤
 * @ClassName: BlogTagServiceImpl
 * @date: 2020/7/15 22:31
 * @Description: 博客标签之间的关系
 */
@Service
public class BlogTagServiceImpl implements BlogTagService {
    private PublicService<Tag> tagService;
    private BlogTagMapper blogTagMapper;

    @Autowired
    public void setBlogTagMapper(BlogTagMapper blogTagMapper) {
        this.blogTagMapper = blogTagMapper;
    }

    @Autowired
    public void setTagService(@Qualifier("tagService") PublicService<Tag> tagService) {
        this.tagService = tagService;
    }

    /**
     * @MethodName: insertTags
     * @date: 2020/7/15 22:31
     * @author 索半斤
     * @Description: 插入博客的标签（关系），待优化
     */
    @Override
    public int insertTags(List<String> tags, String blog) {
        if (isTagError(tags)) return -1;
        int result = 0;
        for (String tag : tags) {
            BlogTag blogTag = new BlogTag(UUIDUtils.uuid(), tag, blog);
            int i = blogTagMapper.insertOne(blogTag);
            result += i;
        }
        return result;
    }

    private boolean isTagError(List<?> tags) {
        List<Tag> tagList = tagService.findAll(null, -1, -1);
        List<String> tagIds = new ArrayList<>();
        for (Tag tag : tagList) {
            tagIds.add(tag.getId());
        }
        return !tagIds.containsAll(tags);
    }

    /**
     * @MethodName: updateTags
     * @date: 2020/7/16 15:09
     * @author 索半斤
     * @Description: 有待优化
     */
    @Override
    public int updateTags(List<String> tags, String blog) {
        //判断tags是否正确
        if (tags.size() != 0) {
            if (isTagError(tags)) return -1;
        }
        //查找属于该blog的tags
        List<BlogTag> list = blogTagMapper.findByBlog(blog);
        //如果tags参数为空，而list不为空，说明用户更新了tags，将该blog所有的tags删除
        if (tags.size() == 0) {
            if (list != null && list.size() != 0) return deleteTagsByBlog(blog);
            //如果tags为空且list为空，说明标签没有修改，为空
            return 1;
        }
        //如果tags不为空，而list也不为空
        if (list != null && list.size() != 0) {
            //tags和list内容相同，说明没有更新，
            if (tags.size() == list.size()) {
                if (list.containsAll(tags)) return 1;
            }
            //如果内容不同，则先将旧的标签删除
            int i = deleteTagsByBlog(blog);
            //如果旧标签删除完成，插入新的标签
            if (i >= 1) return insertTags(tags, blog);
            return -1;
        }
        //如果tags不为空而list为空，说明tags更新了，且数据库中不存在任何标签，直接插入即可
        return insertTags(tags, blog);
    }

    @Override
    public int deleteTagsByBlog(String blog) {
        return blogTagMapper.deleteTagsByBlog(blog);
    }

    @Override
    public int deleteTagsByTag(String tag) {
        return blogTagMapper.deleteTagsByTag(tag);
    }

    @Override
    public int deleteTagsByList(List<String> tags) {
        return blogTagMapper.deleteTagsByList(tags);
    }


}
