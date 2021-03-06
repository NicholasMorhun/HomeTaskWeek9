package com.geekhub.hw9.servlets;

import com.geekhub.hw9.beans.CloudPocketUser;
import com.geekhub.hw9.dao.UserDao;
import com.geekhub.hw9.keys.CookiesKeys;
import com.geekhub.hw9.utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userLogin = req.getParameter("login");
        String userPassword = req.getParameter("password");
        String userPasswordMd = Utils.getMd5Hash(userPassword);

        CloudPocketUser user = UserDao.getInstance().getUserByLogin(userLogin);
        if (user != null && user.getPasswordMd5().equals(userPasswordMd)) {
            Cookie loginCookie = new Cookie(CookiesKeys.USER_LOGIN, userLogin);
            loginCookie.setMaxAge(60*60*24 * 30);
            resp.addCookie(loginCookie);

            Cookie userTokenCookie = new Cookie(CookiesKeys.USER_TOKEN, userPasswordMd);
            userTokenCookie.setMaxAge(60*60*24 * 30);
            resp.addCookie(userTokenCookie);

            resp.sendRedirect("/storage/" + userLogin + "/");
        } else {
            req.setAttribute("oldLogin", userLogin);
            req.setAttribute("errMsg", "Wrong login or password");
            doGet(req, resp);
        }
    }

}
