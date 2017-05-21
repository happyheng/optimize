package com.happyheng.controller;

import com.alibaba.fastjson.JSON;
import com.happyheng.dao.ArticleDao;
import com.happyheng.model.Article;
import com.happyheng.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 *
 * Created by happyheng on 17/5/18.
 */
@RestController
@RequestMapping("/testList")
public class TestListController {


    private static final String KEY_CACHE_THOUSAND_ARTICLE_LIST = "article_thousand_list";

    @Autowired
    private ArticleDao articleDao;

    @RequestMapping("/getArticleList")
    public List<Article> getArticleList(Page page) throws Exception{


        // 判断页数以及数量是否超标
        int pageIndex = page.getPageIndex();
        int pageSize = page.getPageSize();
        if (pageIndex <= 0 || pageSize <= 0){
            return null;
        }
        if (pageIndex * pageSize > ArticleDao.PAGE_MAX_NUM){
            return null;
        }

        // 从redis中获取1000条数据
        Jedis jedis = new Jedis("localhost");
        String articleThousandListStr = jedis.get(KEY_CACHE_THOUSAND_ARTICLE_LIST);
        List<Article> articleList;
        if (StringUtils.isEmpty(articleThousandListStr)){

            // 如果没有,从数据库中直接取出1000条, 并将其缓存到redis中
            articleList = articleDao.getThousandArticleListFromdb();
            jedis.set(KEY_CACHE_THOUSAND_ARTICLE_LIST, JSON.toJSONString(articleList));
            jedis.expire(KEY_CACHE_THOUSAND_ARTICLE_LIST, 60);
        } else {

            // 将String反序列化为Article列表
            articleList = JSON.parseArray(articleThousandListStr, Article.class);
        }

        // 从列表中选出指定的列表数据
        List<Article> result = articleList.subList((pageIndex-1) * pageSize, pageIndex * pageSize);
        return result;
    }
}
