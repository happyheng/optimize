package com.happyheng.controller;

import com.happyheng.dao.ArticleDao;
import com.happyheng.model.Article;
import com.happyheng.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * Created by happyheng on 17/5/18.
 */
@RestController
@RequestMapping("/testList")
public class TestListController {

    @Autowired
    private ArticleDao articleDao;

    @RequestMapping("/getArticleList")
    public List<Article> getArticleList(Page page){

        return null;
    }
}
