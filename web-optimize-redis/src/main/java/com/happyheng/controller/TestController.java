package com.happyheng.controller;

import com.alibaba.fastjson.JSON;
import com.happyheng.dao.ArticleDao;
import com.happyheng.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 *
 *
 * Created by happyheng on 17/5/6.
 */
@RestController
@RequestMapping("/test")
public class TestController {


    private static final String KEY_CACHE_ARTICLE_LIST = "article_list";

    @Autowired
    private ArticleDao articleDao;


    @RequestMapping("/testSelectFromDb")
    public List<Article> testSelectFromDb() throws Exception {

        return articleDao.getArticleListFromdb();
    }

    @RequestMapping("/testSelectFromCache")
    public String testSelectFromCache() throws Exception {

        Jedis jedis = new Jedis("localhost");
        // 先从缓存中取出数据
        String articleListStr = jedis.get(KEY_CACHE_ARTICLE_LIST);
        if (!StringUtils.isEmpty(articleListStr)) {

            return articleListStr;
        } else {

            // 如果数据为空,那么从db中取出来,然后序列化后写入到cache中,并设置1分钟的过期时间
            List<Article> articleList = articleDao.getArticleListFromdb();
            String serializeArticleListStr = JSON.toJSONString(articleList);
            jedis.set(KEY_CACHE_ARTICLE_LIST, serializeArticleListStr);
            jedis.expire(KEY_CACHE_ARTICLE_LIST, 60);
            return serializeArticleListStr;
        }

    }


}