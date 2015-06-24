package cn.yht.util;

import cn.yht.common.Transfer;
import cn.yht.model.Article;
import cn.yht.model.MdbArticle;

/**
 * Created by YHT on 2015/6/18.
 */
public class MdbArticleUtil {
    public static Article mdbToArticle(MdbArticle mdbArticle) {
        String[] str701 = {mdbArticle.getM701_a(),mdbArticle.getM701_b(),mdbArticle.getM701_c(),mdbArticle.getM701_4(),mdbArticle.getM701_5(),mdbArticle.getM701_6()};
        String[] str711 = {mdbArticle.getM711_a(),mdbArticle.getM711_b(),mdbArticle.getM711_c(),mdbArticle.getM711_4(),mdbArticle.getM711_5(),mdbArticle.getM711_6()};
        String[] str702 = {mdbArticle.getM702_a(),mdbArticle.getM702_b(),mdbArticle.getM702_c()};
        Article article = new Article();
        article.setNo(mdbArticle.getM001());
        article.setTitle(mdbArticle.getM200());
        article.setYearVolPage(handleM250(mdbArticle.getM250_a(), mdbArticle.getM250_b(), mdbArticle.getM250_c(), mdbArticle.getM250_d()));
        article.setYear(mdbArticle.getM250_a());
        article.setAabstract(removePreString(mdbArticle.getM330()));
        article.setPeriodicalName(mdbArticle.getM463());
        article.setNameInTitle(replacePreBySlash(mdbArticle.getM600()));
        article.setKeywords(replacePreBySlash(mdbArticle.getM610()));
        article.setAclass(replacePreBySlash(mdbArticle.getM690()));
        article.setAuthorAndType(addPreStr(str701));
        article.setCorporateAndType(addPreStr(str711));
        article.setAuthorCompany(addThreeSlashes(str702));
        article.setAuthor(addThreeSlashes(str701));
        article.setCorporate(addThreeSlashes(str711));
        article.setFundProject(replacePreBySlash(mdbArticle.getM900_a()));
        article.setFkDbId(Long.parseLong(Transfer.dbId));
        article.setType(Transfer.transferType);
        article.setReserve1(mdbArticle.getM250_a());
        article.setReserve2(mdbArticle.getM250_d());
        article.setReserve3(mdbArticle.getM701_a());
        article.setReserve4(mdbArticle.getM250_b());
        article.setReserve5(getVol(mdbArticle.getM250_c()));
        article.setReserve6(mdbArticle.getM702_a());
        article.setReserve7(mdbArticle.getM702_b());
        article.setReserve8(mdbArticle.getM701_b());
        article.setReserve9(mdbArticle.getM701_c());
        article.setReserve10(mdbArticle.getM701_4());
        article.setReserve11(mdbArticle.getM701_5());
        article.setReserve12(mdbArticle.getM701_6());
        article.setReserve13(mdbArticle.getM702_c());
        article.setReserve14(mdbArticle.getM711_a());
        article.setReserve15(mdbArticle.getM711_b());
        article.setReserve16(mdbArticle.getM711_c());
        article.setReserve17(mdbArticle.getM711_4());
        article.setReserve18(mdbArticle.getM711_5());
        article.setReserve19(mdbArticle.getM711_6());
        return article;
    }

    /**
     * 提取刊期
     * @param year_vol_page
     * @return
     */
    private static String getVol(String year_vol_page) {
        if(null != year_vol_page && !year_vol_page.equals("")){
            String vol = "";
            if (year_vol_page.contains("(") && year_vol_page.contains(")")) {
                vol = year_vol_page.substring(year_vol_page.indexOf("(") + 1,
                        year_vol_page.indexOf(")"));
            }
            return vol;
        }
        return "";
    }

    /**
     * 将数组前三个加上"/"后返回
     * @param strings
     * @return
     */
    private static String addThreeSlashes(String[] strings) {
        String result = "";
        for(int i = 0; i < 3; i++){
            if(null != strings[i] && !strings[i].equals("")){
                result = result + "/" + strings[i];
            }
        }
        return result;
    }

    /**
     * 依次加前缀"/a", "/b", "/c", "/4", "/5", "/6"
     * @param strs
     * @return
     */
    private static String addPreStr(String[] strs) {
        String pre[] = { "/a", "/b", "/c", "/4", "/5", "/6" };
        String result = "";
        for(int i=0; i<strs.length; i++){
            if(null != strs[i] && !strs[i].equals("")){
                result = result + pre[i] + strs[i];
            }
        }
        return  result;
    }

    /**
     * 替换"$a”，”$b”，”$c"为"/"
     * @param str
     * @return
     */
    private static String replacePreBySlash(String str) {
        if(str.contains("$")){
            return str.replace('$', '/').replaceAll("/a", "/").replaceAll("/b", "/").replaceAll("/c", "/");
        }
        return str;
    }

    /**
     * 去除前缀$a
     * @param m330
     * @return
     */
    private static String removePreString(String m330) {
        if(m330.contains("$a")){
            return m330.substring((m330.indexOf("$")+2));
        }
        return m330;
    }

    private static String handleM250(String m250_a, String m250_b, String m250_c, String m250_d) {
        return "/a"+m250_a+"/b"+m250_b+m250_c+"/c"+m250_d;
    }
}
