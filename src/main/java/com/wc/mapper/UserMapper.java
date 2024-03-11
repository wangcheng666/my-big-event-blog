package com.wc.mapper;

import com.wc.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Select("select * from user where username=#{username}")
    public User findByUserName(String username);

    @Insert("insert into user(username, password,create_time, update_time)"+
            "values (#{username}, #{password}, now(), now())"
    )
    public void add(String username, String password);

    @Update("update user set nickname=#{nickname},email=#{email},update_time=#{updateTime} where id=#{id}")
    void update(User user);

    @Update("update user set user_pic=#{avaterUrl}, update_time=now() where id=#{id}")
    void updateAvatar(String avaterUrl, Integer id);

    @Update("update user set password=#{newPwd}, update_time=now() where id=#{id}")
    void updatePwd(String newPwd, Integer id);
}
