package cn.yht.util;

import cn.yht.model.MdbPeriodical;
import cn.yht.model.Periodical;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YHT on 2015/6/18.
 */
public class MdbPeriodicalUtil {

    public static List<Periodical> mdbToPeriodicals(List<MdbPeriodical> mdbPeriodicals) {
        List<Periodical> periodicals = new ArrayList<>();
        for(MdbPeriodical mdbPeriodical : mdbPeriodicals){
            Periodical periodical = new Periodical();
            periodical.setNo(mdbPeriodical.getNo());
            periodical.setOriginalName(mdbPeriodical.getOriginalName());
            periodical.setTranslatedName(mdbPeriodical.getTranslatedName());
            //设置刊名首字母
            periodical.setOriginalNameSpell(chineseToFirstSpell(periodical.getOriginalName()));
            //设置periodical的Reserve4为0
            periodical.setReserve4("0");
            periodical.setCycle(mdbPeriodical.getCycle());
            periodical.setDateIssued(mdbPeriodical.getDateIssued());
            periodical.setOrganization(mdbPeriodical.getOrganization());
            periodical.setLocation(mdbPeriodical.getLocation());
            periodical.setAddress(mdbPeriodical.getAddress());
            periodical.setZip(mdbPeriodical.getZip());
            periodical.setTelephone(mdbPeriodical.getTelephone());
            periodical.setEmail(mdbPeriodical.getEmail());
            periodical.setPostNo(mdbPeriodical.getPostNo());
            periodical.setCn(mdbPeriodical.getCn());
            periodical.setIssn(mdbPeriodical.getIssn());
            periodical.setWebUrl(mdbPeriodical.getWebUrl());
            periodical.setRemark(mdbPeriodical.getRemark());
            periodical.setCollection(mdbPeriodical.getCollection());
            periodical.setPclass(mdbPeriodical.getPclass());
            periodical.setCallno(mdbPeriodical.getCallno());
            periodical.setReserve8(mdbPeriodical.getReserve8());
            periodicals.add(periodical);
        }
        return periodicals;
    }

    /**
     *
     * @param originalName 返回中文拼音首字母
     * @return
     */
    public static String chineseToFirstSpell(String originalName){
        return ChineseToSpell.getFirstSpell(originalName).substring(0, 1);
    }
}
