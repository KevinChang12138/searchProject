package com.example.searchproject.beans;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;

public class ParseXml {

    public static ArrayList<NewsBean> init(String Path, ArrayList<NewsBean> al) throws Exception {

        Document dc;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        dc = db.parse(Path);
        dc.getDocumentElement().normalize();
        NodeList newsList = dc.getElementsByTagName("doc");
        System.out.println(newsList.getLength());
        for (int i = 0; i < newsList.getLength(); i++) {
         //   System.out.println("=================下面开始遍历第" + (i + 1) + "条新闻的内容=================");
            Node book = newsList.item(i);
            Element eBook = (Element) book;
            String content = "";
            Node temp = eBook.getElementsByTagName("content")
                    .item(0).getFirstChild();
            if (temp != null)
                content = temp.getNodeValue();
            String url = eBook.getElementsByTagName("url")
                    .item(0).getFirstChild().getNodeValue();
            if (eBook.getElementsByTagName("contenttitle")
                    .item(0).getFirstChild() == null) {
              //  System.out.println("wa" + (i + 1));
                continue;
            }
            String title = eBook.getElementsByTagName("contenttitle")
                    .item(0).getFirstChild().getNodeValue();
            NewsBean nw = new NewsBean(content, title, url);
            al.add(nw);
        }
        return al;


    }

}
