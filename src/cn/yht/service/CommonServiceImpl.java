package cn.yht.service;

import cn.yht.model.Article;
import cn.yht.model.MdbArticle;
import cn.yht.model.MdbPeriodical;
import cn.yht.model.Periodical;
import cn.yht.model.mapper.ArticleMapper;
import cn.yht.model.mapper.MdbArticleMapper;
import cn.yht.model.mapper.MdbPeriodicalMapper;
import cn.yht.model.mapper.PeriodicalMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by YHT on 2015/6/12.
 */
@Service
public class CommonServiceImpl implements CommonService {
    @Resource
    private  PeriodicalMapper periodicalMapper;
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private MdbArticleMapper mdbArticleMapper;
    @Resource
    private MdbPeriodicalMapper mdbPeriodicalMapper;

    /**
     * @param periodicalName 刊名表名称
     * @return 获取刊名表数据
     */
    @Override
    public List<MdbPeriodical> getPeriodicalByMdb(String periodicalName){
        return mdbPeriodicalMapper.getPeriodicalByMdb(periodicalName);
    }

    /**
     * @param periodicalList 传入刊名list
     * @return 获得其中含有相同记录的list
     */
    @Override
    public List<Periodical> getViewSameList(List<Periodical> periodicalList) {
        List<Periodical> periodicals = new ArrayList<>();
        for(Periodical periodical :periodicalList){
            /**
             * 遍历列表，获得相同记录列表，若相同记录数大于1，则加入需调整的列表
             */
            List<Periodical> sameRecord = periodicalMapper.getSamePVRecord(periodical);
            if(sameRecord.size() > 1){
                periodicals.add(sameRecord.get(0));
            }
        }
        return periodicals;
    }

    /**
     *
     * @param periodical 传入一本刊
     * @return  返回periodical_view表中相同记录的刊
     */
    @Override
    public Periodical getSamePVRecord(Periodical periodical) {
        if(periodicalMapper.getSamePVRecord(periodical).size() > 0){
            return periodicalMapper.getSamePVRecord(periodical).get(0);
        }
        else{
            return null;
        }
    }

    /**
     *
     * @param periodical 传入一本刊
     * @return  返回periodical表中相同记录的刊
     */
    @Override
    public Periodical getSamePRecorcd(Periodical periodical) {
        return periodicalMapper.getSamePRecord(periodical);
    }


    @Override
    public long getPVMaxId() {
        return periodicalMapper.getPVMaxId();
    }

    @Override
    public long getPMaxId() {
        return periodicalMapper.getPMaxId();
    }

    @Override
    public long getArticleMaxId() {
        return articleMapper.getArticleMaxId();
    }

    @Override
    public void insertPV(Periodical periodical) {
        periodicalMapper.insertPV(periodical);
    }

    @Override
    public void updatePV(Periodical periodical) {
        periodicalMapper.updatePV(periodical);
    }

    @Override
    public void insertPeriodical(Periodical periodical) {
        periodicalMapper.insertPeriodical(periodical);
    }

    @Override
    public void insertArticle(Article article) {
        articleMapper.insertArticle(article);
    }

    @Override
    public List<MdbArticle> getMdbArticles(String articleName, String pNo) {
        HashMap<String, String> params = new HashMap<>();
        params.put("tableName", articleName);
        params.put("pNo", pNo);
        return mdbArticleMapper.getMdbArticles(params);
    }

    @Override
    public void replaceCR() {
        articleMapper.replaceCR();
    }

    @Override
    public void updateReserve4() {
        articleMapper.updateReserve4();
    }


}
