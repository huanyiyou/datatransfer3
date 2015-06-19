package cn.yht.model.mapper;

import cn.yht.model.MdbArticle;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Created by YHT on 2015/6/18.
 */
@Repository
public interface MdbArticleMapper {
    List<MdbArticle> getMdbArticles(HashMap<String,String> params);
}
