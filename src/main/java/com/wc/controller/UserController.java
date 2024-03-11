package com.wc.controller;

import com.wc.pojo.Result;
import com.wc.pojo.User;
import com.wc.service.UserService;
import com.wc.utils.JwtUtil;
import com.wc.utils.Md5Util;
import com.wc.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.apache.ibatis.annotations.Update;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password){
        User user = userService.findByUserName(username);
        if(user==null){
            userService.register(username, password);
            return Result.success();
        }else {
            return Result.error("用户名已经被占用");
        }
    }

    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password){
        User user = userService.findByUserName(username);
        if(user==null){
            return Result.error("该用户不存在");
        }else {
            String md5Password = Md5Util.getMD5String(password);
            if(md5Password.equals(user.getPassword())){
                Map<String,Object>claims = new HashMap<>();
                claims.put("id", user.getId());
                claims.put("username", user.getUsername());
                String token = JwtUtil.genToken(claims);
                return Result.success(token);
            }else {
                return Result.error("密码错误");
            }
        }
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/) {
       /* Map<String, Object> map = JwtUtil.parseToken(token);
        String username = (String) map.get("username");*/
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params){
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if(oldPwd.isEmpty()||newPwd.isEmpty()||rePwd.isEmpty()){
            return Result.error("缺少必要的参数");
        }

        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        if (!user.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码填写错误");
        }
        if (!rePwd.equals(newPwd)){
            return Result.error("两次密码不一致");
        }

        userService.updatePwd(newPwd);
        return Result.success();

    }

}
