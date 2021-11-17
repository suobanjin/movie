package zzuli.zw.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzuli.zw.blog.domain.User;
import zzuli.zw.blog.mapper.UserMapper;
import zzuli.zw.blog.service.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;
    @Autowired
    public void setUserMapper( UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    @Override
    @Transactional(readOnly = true)
    public User login(User user) {
        User result = userMapper.findByUsername(user.getUsername());
        if (result == null){
            return null;
        }
        return result.getPassword().equals(user.getPassword()) ? result : null;
    }

    @Override
    public User findIndexUserInfo() {
        return userMapper.findIndexUserInfo();
    }
}
