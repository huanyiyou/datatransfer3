package cn.yht.util;

/**
 * Created by Admin on 2014/5/28.
 */
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;


public class XMLPaser {

    public static HashMap<String,String> paserXML(String nameOfXML,String configTag){
        SAXReader reader = new SAXReader();
        HashMap<String, String> result = new HashMap<>();
        try {
            // 读取XML文件
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(nameOfXML);
            Document doc = reader.read(is);
            Element root = doc.getRootElement();
            //得到database节点
            Element database = (Element)root.selectSingleNode("//demo/"+configTag);
            List list = database.elements(); //得到database元素下的子元素集合
            /*
             * 循环遍历集合中的每一个元素
             * 将每一个元素的元素名和值在控制台中打印出来
             */
            for(Object obj:list){
                Element element = (Element)obj;
                //getName()是元素名,getText()是元素值
                result.put(element.getName(), element.getText());
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return result;
    }
}