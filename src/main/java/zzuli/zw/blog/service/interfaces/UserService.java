package zzuli.zw.blog.service.interfaces;

import zzuli.zw.blog.domain.User;

/**
 * @ClassName: UserService
 * @date: 2020/7/10 11:33
 * @author 索半斤
 * @Description: UserService接口设计
 */
public interface UserService {
    User login(User user);
    User findIndexUserInfo();
}
