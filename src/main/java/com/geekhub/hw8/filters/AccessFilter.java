package com.geekhub.hw8.filters;

import com.geekhub.hw8.beans.CloudPocketUser;
import com.geekhub.hw8.dao.UserDao;
import com.geekhub.hw8.keys.CookiesKeys;
import com.geekhub.hw8.keys.SessionKeys;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AccessFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();

        Cookie userLoginCookie = getCookieByName(CookiesKeys.USER_LOGIN, req);
        Cookie userTokenCookie = getCookieByName(CookiesKeys.USER_TOKEN, req);
        String userLoginFromCookie = (userLoginCookie == null) ? null : userLoginCookie.getValue();
        String userTokenFromCookie = (userTokenCookie == null) ? null : userTokenCookie.getValue();

        if (userLoginFromCookie == null || userTokenFromCookie == null) {
            req.setAttribute("errMsg", "403 Forbidden. Login please");
            req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
        }

        String userLoginFromSession = (String) session.getAttribute(SessionKeys.USER_LOGIN);
        String userTokenFromSession = (String) session.getAttribute(SessionKeys.USER_TOKEN);
        if (userLoginFromSession == null || userTokenFromSession == null) {
            UserDao userDao = UserDao.getInstance();
            CloudPocketUser user = userDao.getUserByLogin(userLoginFromCookie);
            userLoginFromSession = user.getLogin();
            session.setAttribute(SessionKeys.USER_LOGIN, userLoginFromSession);
            userTokenFromSession = user.getPasswordMd5();
            session.setAttribute(SessionKeys.USER_TOKEN, userTokenFromSession);
        }

        if (userLoginFromCookie.equals(userLoginFromSession) && userTokenFromCookie.equals(userTokenFromSession)) {
            filterChain.doFilter(req, resp);
        } else {
            req.setAttribute("errMsg", "403 Forbidden. Wrong user token.");
            req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
        }
    }

    private Cookie getCookieByName(String cookieName, HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) return null;

        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals(cookieName)) {
                return cookie;
            }
        }
        return null;
    }

    @Override
    public void destroy() { }

}
