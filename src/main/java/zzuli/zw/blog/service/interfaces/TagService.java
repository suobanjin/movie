package zzuli.zw.blog.service.interfaces;
import zzuli.zw.blog.domain.Tag;
import zzuli.zw.blog.service.interfaces.publicInterface.PublicService;

import java.util.List;

/**
 * @ClassName: TypeService
 * @date: 2020/7/11 14:58
 * @author 索半斤
 * @Description: 标签管理相关
 */
public interface TagService extends PublicService<Tag> {
    @Override
    List<Tag> findAll(String name, int page, int limit);

    @Override
    boolean update(String id, String name);

    @Override
    boolean delete(String id);

    @Override
    int delete(List<String> list);

    @Override
    Tag add(Tag tag);

    List<Tag> findIndexTags();

    Integer findTagCount();
}
