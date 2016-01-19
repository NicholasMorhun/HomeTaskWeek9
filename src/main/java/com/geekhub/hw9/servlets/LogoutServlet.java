package com.geekhub.hw9.servlets;

import com.geekhub.hw9.keys.CookiesKeys;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();

        Cookie userLoginCookie = new Cookie(CookiesKeys.USER_LOGIN, "");
        userLoginCookie.setMaxAge(0);
        resp.addCookie(userLoginCookie);

        Cookie userTokenCookie = new Cookie(CookiesKeys.USER_TOKEN, "");
        userTokenCookie.setMaxAge(0);
        resp.addCookie(userTokenCookie);

        resp.sendRedirect("/index.jsp");
    }

}
