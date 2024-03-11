package com.wc.service.impl;

import com.wc.mapper.UserMapper;
import com.wc.pojo.User;
import com.wc.service.UserService;
import com.wc.utils.Md5Util;
import com.wc.utils.ThreadLocalUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUserName(String username) {

        return userMapper.findByUserName(username);
    }

    @Override
    public void register(String username, String password) {
        String md5Password = Md5Util.getMD5String(password);

        userMapper.add(username, md5Password);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avaterUrl) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avaterUrl, id);
    }

    @Override
    public void updatePwd(String newPwd) {
        String md5NewPwd = Md5Util.getMD5String(newPwd);
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePwd(md5NewPwd, id);
    }
}
