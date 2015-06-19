package cn.yht.service;

import cn.yht.model.Article;
import cn.yht.model.MdbArticle;
import cn.yht.model.MdbPeriodical;
import cn.yht.model.Periodical;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by YHT on 2015/6/12.
 */
@Transactional
public interface CommonService {
    List<MdbPeriodical> getPeriodicalByMdb(String periodicalName);

    List<Periodical> getViewSameList(List<Periodical> periodicalList);

    Periodical getSamePVRecord(Periodical periodical);

    Periodical getSamePRecorcd(Periodical periodical);

    long getPVMaxId();

    long getPMaxId();

    long getArticleMaxId();

    void insertPV(Periodical periodical);

    void updatePV(Periodical periodical);

    void insertPeriodical(Periodical periodical);

    void insertArticle(Article article);

    List<MdbArticle> getMdbArticles(String articleName, String pNo);

    void replaceCR();

    void updateReserve4();
}
