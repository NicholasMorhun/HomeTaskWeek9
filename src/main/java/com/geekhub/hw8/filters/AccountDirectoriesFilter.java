package com.geekhub.hw8.filters;

import com.geekhub.hw8.keys.SessionKeys;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter which guaranteed that user request only him own files
 */
public class AccountDirectoriesFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();

        String userLogin = (String) session.getAttribute(SessionKeys.USER_LOGIN);

        String requestedUri = req.getRequestURI();
        String[] pathItems = requestedUri.split("/");
        if (userLogin.equals(pathItems[2])) {
            filterChain.doFilter(req, servletResponse);
        } else {
            req.getRequestDispatcher("/forbidden").forward(req, servletResponse);
        }

    }

    @Override
    public void destroy() { }

}
