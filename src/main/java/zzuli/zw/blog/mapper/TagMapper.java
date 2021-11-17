package zzuli.zw.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import zzuli.zw.blog.domain.Tag;
import java.util.List;

@Mapper
@Repository
public interface TagMapper {
    List<Tag> findAll(String tag);
    int updateOne(String id, String tag);
    int deleteOne(String id);
    int deleteBatch(List<String> tags);
    int insertOne(Tag tag);
    Tag findByTagName(String tagName);
    List<Tag> findIndexTags();
    Integer findTagCount();
}
