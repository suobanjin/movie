package zzuli.zw.blog.service.interfaces;

import java.util.List;

public interface BlogTagService {
    int insertTags(List<String> tags,String blog);
    int updateTags(List<String> tags,String blog);
    int deleteTagsByBlog(String blog);
    int deleteTagsByTag(String tag);
    int deleteTagsByList(List<String> tags);
}
