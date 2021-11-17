package zzuli.zw.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import zzuli.zw.blog.domain.Type;

import java.util.List;

@Mapper
@Repository
public interface TypesMapper {
    List<Type> findAll(String type);
    int updateOne(String id,String typeName);
    int deleteOne(String id);
    int deleteBatch(List<String> types);
    int insertOne(Type type);
    Type findByTypeName(String typeName);
    Type findById(String id);
    List<Type> findTypeAndCount();
    Integer findTypeCount();
}
