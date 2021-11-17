package zzuli.zw.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import zzuli.zw.blog.domain.User;

@Mapper
@Repository
public interface UserMapper {
    //根据用户名查询用户
    User findByUsername(String username);
    User findIndexUserInfo();
}
