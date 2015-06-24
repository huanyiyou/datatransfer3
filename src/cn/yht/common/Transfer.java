package cn.yht.common;

import cn.yht.util.XMLPaser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by YHT on 2015/6/10.
 */
public class Transfer {
    public static HashMap<String,String> hashMapConfig;
    public static int transferType;
    public static String dbId;
    public static String fk_db_id;
    public static ApplicationContext applicationContext;
    public final static Logger logger = LoggerFactory.getLogger(cn.yht.common.Transfer.class);

    static {
        applicationContext = new ClassPathXmlApplicationContext("spring/spring-config.xml");
        hashMapConfig = XMLPaser.paserXML("config.xml", "config");
        dbId =hashMapConfig.get("dbId");
        if(hashMapConfig.get("transferType").startsWith("目次")){
            transferType = 1;
        }else if(hashMapConfig.get("transferType").startsWith("篇名")){
            transferType = 2;
        }else {
            transferType = 0;
        }
        fk_db_id = hashMapConfig.get("fk_db_id").trim();
    }


    public static String getTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    public static String getFileTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(new Date());
    }

    public static void main(String[] args){
        new Transfer();
        if(transferType == 0){
            logger.info("transferType没有值");
        }else {
            Importer importer = new Importer();
            importer.importData();
        }
    }
}
