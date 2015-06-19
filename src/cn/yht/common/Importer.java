package cn.yht.common;

import cn.yht.model.Article;
import cn.yht.model.MdbArticle;
import cn.yht.model.MdbPeriodical;
import cn.yht.model.Periodical;
import cn.yht.service.CommonService;
import cn.yht.util.MdbArticleUtil;
import cn.yht.util.MdbPeriodicalUtil;

import java.io.*;
import java.util.List;

/**
 * Created by YHT on 2015/6/12.
 */
public class Importer {
    private CommonService commonService;
    public void importData() {
        // 初始化commonService
        commonService = (CommonService) Transfer.applicationContext.getBean("commonServiceImpl");
        //获取需导入的刊名表数据
        List<MdbPeriodical> mdbPeriodicals = commonService.getPeriodicalByMdb(Transfer.hashMapConfig.get("periodicalName"));
        List<Periodical> periodicals = MdbPeriodicalUtil.mdbToPeriodicals(mdbPeriodicals);
        if(1 == 0){
        //检查本次periodicals中是否在periodical_view表中有重复记录
//        List<Periodical> samePeriodicalList = commonService.getViewSameList(periodicals);
//        if (samePeriodicalList.size() > 0) {
//            //若相同记录数大于1则将该条记录的
//            System.out.println("periodical_view有重复记录，共" + samePeriodicalList.size() + "条");
//            for (Periodical periodical : samePeriodicalList) {
//                writerTxt(periodical.getOriginalName() + "  " + periodical.getLocation());
//            }
        } else {
            int insertArticleCount = 0;
            int insertPeriodicalCount = 0;
            int insertPeriodicalViewCount = 0;
            int updatePeriodicalViewCount = 0;

            //若相同记录数没有大于1的记录，则遍历periodicals.getPeriodicalList()判断是否是重复记录
            for (Periodical periodical : periodicals) {
                periodical.setReserve18(Transfer.dbId);
                //periodical_view表
                if (null == commonService.getSamePVRecord(periodical)) {
                    periodical.setId(commonService.getPVMaxId()+1);
                    periodical.setFkPVId(periodical.getId());
                    insertPeriodicalView(periodical);
                    insertPeriodicalViewCount++;
                    System.out.println("新增periodical_view："+periodical.getOriginalName());
                } else {
                    Periodical samePeriodical = commonService.getSamePVRecord(periodical);
                    periodical.setId(samePeriodical.getId());
                    periodical.setFkPVId(samePeriodical.getId());
                    updatePeriodicalView(periodical, samePeriodical);
                    updatePeriodicalViewCount++;
                    System.out.println("更新periodical_view："+periodical.getOriginalName());
                }

                //periodical表
                if (null == commonService.getSamePRecorcd(periodical)){
                    periodical.setId(commonService.getPMaxId() + 1);
                    insertPeriodical(periodical);
                    insertPeriodicalCount++;
                    System.out.println("新增periodical："+periodical.getOriginalName());
                } else {
                    Periodical samePeriodical = commonService.getSamePRecorcd(periodical);
                    periodical.setId(samePeriodical.getId());
                }

                //article表
                List<MdbArticle> mdbArticles = commonService.getMdbArticles(Transfer.hashMapConfig.get("articleName"), periodical.getNo());
                for(MdbArticle mdbArticle : mdbArticles){
                    Article article = MdbArticleUtil.mdbToArticle(mdbArticle);
                    if(null != article){
                        insertArticle(article, periodical);
                        insertArticleCount++;
                        System.out.println("新增article："+article.getTitle());
                    }
                }
                commonService.replaceCR();
                commonService.updateReserve4();

                System.out.println("共新增periodical_view："+updatePeriodicalViewCount+"篇");
                System.out.println("共更新periodical_view："+insertPeriodicalViewCount+"篇");
                System.out.println("共新增periodical："+insertPeriodicalCount+"篇");

                System.out.println("一共导入："+insertArticleCount+"篇article");
            }
        }
    }

    private void insertPeriodical(Periodical periodical) {
        periodical.setCreateDate(Transfer.getTime());

        commonService.insertPeriodical(periodical);
    }

    private void insertArticle(Article article, Periodical periodical) {
        article.setId(commonService.getArticleMaxId() + 1);
        article.setCreateDate(Transfer.getTime());
        article.setFkPNo(periodical.getId());
        article.setCallno(periodical.getCallno());
        article.setCollection(periodical.getCollection());

        commonService.insertArticle(article);
    }




    private void insertPeriodicalView(Periodical periodical) {
        periodical.setCreateDate(Transfer.getTime());
        periodical.setFk_db_id(Transfer.fk_db_id);

        commonService.insertPV(periodical);
    }

    private void updatePeriodicalView(Periodical periodical, Periodical samePeriodical){

        //若配置文件中fk_db_id为空或数据库中的值包含配置文件中的值，则该字段依旧为数据库中的值
        if(Transfer.fk_db_id.equals("") || samePeriodical.getFk_db_id().contains(Transfer.fk_db_id)){
            periodical.setFk_db_id(samePeriodical.getFk_db_id());
        }
        //若数据库中的字段为空，则该字段等于配置文件中的值
        else if(samePeriodical.getFk_db_id().trim().equals("")){
            periodical.setFk_db_id(Transfer.fk_db_id);
        }
        else{
            periodical.setFk_db_id(samePeriodical.getFk_db_id() + "," + Transfer.fk_db_id);
        }

        periodical.setUpdateDate(Transfer.getTime());

        commonService.updatePV(periodical);
    }


//    /**
//     * 从需导入的刊名表中获取数据
//     * @return
//     */
//    public List<Periodical> getPeriodicals(){
//        return commonService.getPeriodicals(Transfer.hashMapConfig.get("periodicalName"));
//    }


    /**
     * 将重复记录写入到txt中
     * @param content
     */
    private void writerTxt(String content) {
        BufferedWriter fw = null;
        try {
            File file = new File("D://periodical_view重复列表" + Transfer.getFileTime() + ".txt");
            fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8")); // 指定编码格式，以免读取时中文字符异常
            fw.append(content);
            fw.newLine();
            fw.flush(); // 全部写入缓存中的内容
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
