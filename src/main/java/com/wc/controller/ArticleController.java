package com.wc.controller;

import com.wc.pojo.Article;
import com.wc.pojo.Result;
import com.wc.service.ArticleService;
import com.wc.utils.JwtUtil;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    public Result list(/*@RequestHeader(name = "Authorization") String token, HttpServletResponse response*/){
//        try {
//            Map<String,Object> claims = JwtUtil.parseToken(token);
//            return Result.success("文章");
//        }catch (Exception e){
//            response.setStatus(401);
//            return Result.error("未登录");
//        }
        return Result.success("文章");
    }


    @PostMapping
    public Result add(@RequestBody @Validated Article article)
    {
        articleService.add(article);
        return Result.success();
    }

}
