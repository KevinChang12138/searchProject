package com.example.searchproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by linjiale on 2017/7/21.
 */
@Controller
public class BaseController {

    @Autowired
    protected HttpServletRequest servletRequest;

    @Autowired
    protected HttpServletResponse servletResponse;


    public int getUserId() {
        HttpSession session = servletRequest.getSession();
        int companyId = (int) session.getAttribute("userId");
        return companyId;
    }

    protected String getCookie(String cookieName) {
        Cookie[] cookies = servletRequest.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    protected void addCookie(String name, String value) {
        addCookie(name, value, "/");
    }

    protected void addCookie(String name, String value, String path) {
        addCookie(name, value, path, -1);
    }

    protected void addCookie(String name, String value, String path, int maxAge) {
        Cookie cookie = new Cookie(name.trim(), value.trim());
        cookie.setMaxAge(maxAge);
        cookie.setPath(path);
        servletResponse.addCookie(cookie);
    }
}
