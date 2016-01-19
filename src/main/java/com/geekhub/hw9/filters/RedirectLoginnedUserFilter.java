package com.geekhub.hw9.filters;

import com.geekhub.hw9.keys.CookiesKeys;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectLoginnedUserFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        Cookie userLoginCookie = getCookieByName(CookiesKeys.USER_LOGIN, req);
        String userLoginFromCookie = (userLoginCookie == null) ? null : userLoginCookie.getValue();
        if (userLoginFromCookie == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            HttpServletResponse resp = (HttpServletResponse) servletResponse;
            resp.sendRedirect("/storage/" + userLoginFromCookie + "/");
        }
    }

    private Cookie getCookieByName(String cookieName, HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) return null;

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie;
            }
        }
        return null;
    }

    @Override
    public void destroy() {

    }
}
