package com.example.searchproject.beans;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.search.suggest.Lookup;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class NewsLookUpSuggest {

    public static AnalyzingInfixSuggester suggester;
    public static double t;

    private static ArrayList<NewsBean> lookup(AnalyzingInfixSuggester suggester, String name) throws Exception {
        List<Lookup.LookupResult> results = suggester.lookup(name, 10, true, true);
        System.out.println("-- \"" + name + "\"");
        ArrayList<NewsBean> arrayList = new ArrayList<>();
        for (Lookup.LookupResult result : results) {
            //.out.println(result.key);
            System.out.println(result.highlightKey);

            //从payload中反序列化出Product对象
            BytesRef bytesRef = result.payload;
            ByteArrayInputStream bis = new ByteArrayInputStream(bytesRef.bytes);
            ObjectInputStream inputStream = new ObjectInputStream(bis);
            NewsBean newsBean = (NewsBean) inputStream.readObject();

            //  NewsBean nextBean = new NewsBean();//result.highlightKey.toString(),newsBean.getTitle(),newsBean.getUrl());
            String content = result.highlightKey.toString();
            String title = newsBean.getTitle();
            String url = newsBean.getUrl();

            if (content.length() > 256) {
                int in = content.indexOf(name);
                content = content.substring(Math.max(0,in-30),Math.min(Math.max(0,in-30)+255,content.length()-1));
                content += "...";
            }
            content = content.replace("<b>","<b style=\"background: #00FF00 \">");
            if (url.length() > 72) {
                url = url.substring(0, 72);
                url += "...";
            }
            NewsBean nextBean = new NewsBean(content, title, url);
            arrayList.add(nextBean);
        }
        return arrayList;
    }

    public static ArrayList<NewsBean> search(String s) throws Exception {
        if (suggester == null) {
            String Outpath = "./";
            Directory directory = FSDirectory.open(new File(Outpath + "suggestindex").toPath());
            SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();//分词器 武汉市长春药(药)
            suggester = new AnalyzingInfixSuggester(directory, analyzer);

            //创建Product测试数据
            ArrayList<NewsBean> newsBeans = new ArrayList<NewsBean>();
            newsBeans = ParseXml.init(Outpath + "news_all.xml", newsBeans);

            // 创建测试索引
            suggester.build(new NewsOneIterator(newsBeans.iterator()));

        }
        // 开始搜索
        //lookup(suggester, "美好");
        //suggester.update(new  BytesRef("信息检2索"), null,22,null);

        Long t1 = System.currentTimeMillis();
        ArrayList<NewsBean> arrayList = lookup(suggester, s);
        Long t2 = System.currentTimeMillis();
        t = 0.001 * (t2 - t1);
        return arrayList;
    }
}
