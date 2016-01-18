package com.geekhub.hw8.servlets;

import com.geekhub.hw8.beans.CloudPocketUser;
import com.geekhub.hw8.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userLogin = req.getParameter("login");
        String userPassword = req.getParameter("password");
        String confirmationOfUserPassword = req.getParameter("confirm_password");

        if (! userPassword.equals(confirmationOfUserPassword)) {
            req.setAttribute("errMsg", "Passwords do not match");
            req.setAttribute("loginValue", userLogin);
            doGet(req, resp);
            return;
        }
        if (userPassword.equals("")) {
            req.setAttribute("errMsg", "You may not use empty password");
            req.setAttribute("loginValue", userLogin);
            doGet(req, resp);
        }

        UserDao userDao = UserDao.getInstance();
        CloudPocketUser user = userDao.getUserByLogin(userLogin);
        if (user == null) {
            userDao.addUser(userLogin, userPassword);
            req.setAttribute("login", userLogin);
            req.setAttribute("password", userPassword);
            req.getRequestDispatcher("/login").forward(req, resp);
        } else {
            req.setAttribute("errMsg", "\"" + userLogin + "\"" + " already exists");
            doGet(req, resp);
        }
    }

}
