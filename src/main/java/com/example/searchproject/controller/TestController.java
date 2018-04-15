package com.example.searchproject.controller;

import com.example.searchproject.beans.BaseJsonResponse;
import com.example.searchproject.beans.NewsBean;
import com.example.searchproject.beans.NewsLookUpSuggest;
import com.example.searchproject.beans.TitleIterator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping
public class TestController extends BaseController {
    @RequestMapping(value = "/test")
    public String index(Map<String, Object> model) {
        return "test";
    }

    @RequestMapping(value = "/searchResult")
    public String searchResult(Map<String, Object> model) throws Exception {

//        List<NewsBean>list = new ArrayList<>();
//        list.add(new NewsBean("吃饭","谁","next.com"));
//        list.add(new NewsBean("喝茶","谁","next.com"));
//        list.add(new NewsBean("打<b>豆</b>豆","谁","next.com"));
//        list.add(new NewsBean("打豆豆","啦啦啦","next.com"));
//        list.add(new NewsBean("打豆豆","？？？","next.com"));
        String content = (String) servletRequest.getSession().getAttribute("searchContent");
        ArrayList<NewsBean> list = NewsLookUpSuggest.search(content);

        model.put("list", list);
        model.put("timeAll", String.valueOf(NewsLookUpSuggest.t));
        HttpSession session = servletRequest.getSession();
        model.put("searchContent", session.getAttribute("searchContent"));
        return "result";
    }

    @RequestMapping(value = "/search")
    public String search(Map<String, Object> model) {
        return "search";
    }

    @RequestMapping(value = "/doSearch")
    @ResponseBody
    public BaseJsonResponse doSearch(@RequestParam("name") String name) throws  Exception{
        BaseJsonResponse baseJsonResponse = new BaseJsonResponse();
        ArrayList<NewsBean> newList = TitleIterator.searchTitle(name);
        HashSet<String> set = new HashSet<>();
        for(NewsBean newsBean:newList)
            set.add(newsBean.getTitle());
        baseJsonResponse.setObj(set.toArray());
        // baseJsonResponse.setReturnCode(name+name);
        return baseJsonResponse;
    }


    @RequestMapping(value = "/beginSearch")
    @ResponseBody
    public BaseJsonResponse BeginSearch(@RequestParam("content") String content) {
        BaseJsonResponse baseJsonResponse = new BaseJsonResponse();
        HttpSession session = servletRequest.getSession();
        session.setAttribute("searchContent", content);
        return baseJsonResponse;
    }
}
