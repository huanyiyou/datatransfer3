package cn.yht.model.mapper;

import cn.yht.model.Article;
import org.springframework.stereotype.Repository;

/**
 * Created by YHT on 2015/6/17.
 */
@Repository
public interface ArticleMapper {
    long getArticleMaxId();

    void insertArticle(Article article);

    void replaceCR();

    void updateReserve4();
}
