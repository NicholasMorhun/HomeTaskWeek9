package com.geekhub.hw8.filters;

import com.geekhub.hw8.keys.SessionKeys;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter that guaranteed that user request only him own files
 */
public class AccountDirectoriesFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();

        String userLogin = (String) session.getAttribute(SessionKeys.USER_LOGIN);
        String userRootDir = "/storage/" + userLogin + "/";

        String requestedUri = req.getRequestURI();
        if (requestedUri.startsWith(userRootDir)) {
            filterChain.doFilter(req, servletResponse);
        } else {
            req.getRequestDispatcher("/forbidden").forward(req, servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
}
