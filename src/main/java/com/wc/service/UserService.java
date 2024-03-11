package com.wc.service;

import com.wc.pojo.User;

public interface UserService {
    public User findByUserName(String username);
    public void register(String username, String password);

    void update(User user);

    void updateAvatar(String avaterUrl);

    void updatePwd(String newPwd);
}
