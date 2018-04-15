package com.example.searchproject.beans;

import java.io.Serializable;

public class NewsBean implements Serializable {
    private String content;
    private String title;
    private String url;
    public NewsBean(String content, String title, String url)
    {
        this.content=content;
        this.title=title;
        this.url=url;
    }
    public NewsBean(){};
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
