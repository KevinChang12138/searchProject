package com.example.searchproject.beans;

import org.apache.lucene.search.suggest.InputIterator;
import org.apache.lucene.util.BytesRef;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class NewsOneIterator implements InputIterator {
    private Iterator<NewsBean> NewsBeanIterator;
    private NewsBean currentNews;
    NewsOneIterator(Iterator<NewsBean>NewsBeanIterator)
    {
        this.NewsBeanIterator=NewsBeanIterator;
    }
    @Override
    public long weight() {
        return currentNews.getUrl().length();
    }

    @Override
    public BytesRef payload() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(currentNews);

            out.close();
            return new BytesRef(bos.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Well that's unfortunate.");
        }
    }

    @Override
    public boolean hasPayloads() {
        return true;
    }

    @Override
    public Set<BytesRef> contexts() {

            Set<BytesRef> regions = new HashSet<BytesRef>();
            regions.add(new BytesRef(currentNews.getTitle()));
            regions.add(new BytesRef(currentNews.getContent()));

            return regions;

    }

    @Override
    public boolean hasContexts() {
        return true;
    }

    @Override
    public BytesRef next() throws IOException {
        if(NewsBeanIterator.hasNext())
        {
            currentNews=NewsBeanIterator.next();
            try {
                //返回当前Project的name值，把product类的name属性值作为key
                String s = currentNews.getTitle() + " " +currentNews.getContent();
                return new BytesRef(s.getBytes("UTF8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Couldn't convert to UTF-8", e);
            }
        }
        else
        {
            return null;
        }
    }
}
