package com.geekhub.hw9.servlets.errors;

import com.geekhub.hw9.keys.SessionKeys;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/forbidden")
public class ForbiddenAccessToForeignFile extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String userLogin = (session != null ? (String) session.getAttribute(SessionKeys.USER_LOGIN) : null);
        if (userLogin == null) {
            resp.sendRedirect("/login");
        } else {
            String userRootDir = "/storage/" + userLogin + "/";
            req.setAttribute("homeLink", userRootDir);
            req.getRequestDispatcher("/WEB-INF/pages/errors/forbidden.jsp").forward(req, resp);
        }
    }

}
