package com.happyheng.dao;

import com.happyheng.model.Article;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * Created by happyheng on 17/5/7.
 */
@Service
public class ArticleDao {


    public List<Article> getArticleListFromdb() throws Exception{

        String sql = "SELECT * FROM article ORDER BY create_time DESC LIMIT 0, 10";

        Class.forName("com.mysql.jdbc.Driver");
        Connection mConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/optimize_db", "root", "mytestcon");
        Statement statement = mConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        try {
            List<Article> list = new ArrayList<>();
            //注意指针刚开始是-1位置，这行next()方法，会先判断下一个位置有没有，如果有，指向下一个位置。
            while (resultSet.next()) {
                Article article = new Article();
                article.setId(resultSet.getLong("id"));
                article.setTitle(resultSet.getString("title"));
                article.setContent(resultSet.getString("content"));
                article.setCreate_time(resultSet.getString("create_time"));
                list.add(article);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                statement.close();
                mConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return null;
    }
}
