package zzuli.zw.blog.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzuli.zw.blog.domain.Tag;
import zzuli.zw.blog.mapper.TagMapper;
import zzuli.zw.blog.service.interfaces.BlogTagService;
import zzuli.zw.blog.service.interfaces.TagService;
import zzuli.zw.blog.service.interfaces.publicInterface.PublicService;
import zzuli.zw.blog.utils.UUIDUtils;

import java.util.List;

/**
 * @ClassName: TagServiceImpl
 * @date: 2020/7/12 21:31
 * @author 索半斤
 * @Description: 标签管理相关
 */
@Service("tagService")
public class TagServiceImpl implements TagService {
    private TagMapper tagMapper;
    private BlogTagService blogTagService;

    @Autowired
    public void setBlogTagService(BlogTagService blogTagService) {
        this.blogTagService = blogTagService;
    }

    @Autowired
    public void setTagMapper(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tag> findAll(String tagName, int page, int limit) {
        if (page == -1 || limit == -1) return tagMapper.findAll(tagName);
        PageHelper.startPage(page, limit);
        return  tagMapper.findAll(tagName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(String id, String typeName) {
        int i = tagMapper.updateOne(id, typeName);
        return i >= 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(String id) {
        return tagMapper.deleteOne(id) >= 1 && blogTagService.deleteTagsByTag(id) >= 1;
    }

    @Override
    @Transactional
    public int delete(List<String> tags) {
        return tagMapper.deleteBatch(tags) + blogTagService.deleteTagsByList(tags);
    }

    @Override
    @Transactional
    public Tag add(Tag tag) {
        Tag byTagName = tagMapper.findByTagName(tag.getTagName());
        if (byTagName != null){
            return null;
        }
        String uuid = UUIDUtils.uuid();
        tag.setId(uuid);
        return tagMapper.insertOne(tag) >= 1 ? tag : null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tag> findIndexTags() {
        return tagMapper.findIndexTags();
    }

    @Override
    public Integer findTagCount() {
        return tagMapper.findTagCount();
    }


}
