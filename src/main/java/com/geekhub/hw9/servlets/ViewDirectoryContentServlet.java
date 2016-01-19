package com.geekhub.hw9.servlets;

import com.geekhub.hw9.keys.SessionKeys;
import com.geekhub.hw9.storage.FileInstance;

import javax.servlet.annotation.WebServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.geekhub.hw9.storage.RootDirectory.PATH_TO_SANDBOX;

@WebServlet("/storage/*")
public class ViewDirectoryContentServlet extends HttpServlet {

    private static final Comparator<FileInstance> comparatorByName = (o1, o2) -> {
        if (o1.getIsDirectory() != o2.getIsDirectory()) {
            if (o1.getIsDirectory()) {
                return -1;
            } else {
                return 1;
            }
        } else {
            return o1.getFileName().compareTo(o2.getFileName());
        }
    };

    private static final Comparator<FileInstance> comparatorBySize = (o1, o2) -> {
        if (o1.getIsDirectory() != o2.getIsDirectory()) {
            if (o1.getIsDirectory()) {
                return -1;
            } else {
                return 1;
            }
        } else {
            if (o1.getIsDirectory()) {
                return o1.getFileName().compareTo(o2.getFileName());
            } else {
                return Long.compare(o2.getFileSize(), o1.getFileSize());
            }
        }
    };

    private static final Comparator<FileInstance> comparatorByType = (o1, o2) -> {
        if (o1.getIsDirectory() != o2.getIsDirectory()) {
            if (o1.getIsDirectory()) {
                return -1;
            } else {
                return 1;
            }
        } else {
            if (o1.getIsDirectory()) {
                return o1.getFileName().compareTo(o2.getFileName());
            } else {
                return o1.getFileExtension().compareTo(o2.getFileExtension());
            }
        }
    };

    private static final Comparator<FileInstance> comparatorByDate = (o1, o2) -> {
        if (o1.getIsDirectory() != o2.getIsDirectory()) {
            if (o1.getIsDirectory()) {
                return -1;
            } else {
                return 1;
            }
        } else {
            return Long.compare(o2.getCreationDate(), o1.getCreationDate());
        }
    };

    private static final String SORT_PARAMETER_NAME = "sort";
    private static final String REVERSE_PARAMETER_NAME = "reverse";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getRequestURI().endsWith("/")) {
            showDirectory(req, resp);
        } else {
            String newUrl = req.getRequestURI().replaceFirst("storage", "view");
            resp.sendRedirect(newUrl);
        }
    }

    private void showDirectory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String userLogin = (String) session.getAttribute(SessionKeys.USER_LOGIN);

        String userRootDir = "/storage/" + userLogin;

        String currentPath = URLDecoder.decode(req.getRequestURI(), "UTF-8").substring(userRootDir.length());
        if (currentPath.endsWith("/")) {
            currentPath = currentPath.substring(0, currentPath.length() - 1);
        }

        String backDir = "";
        int lastIndexOfSlash = currentPath.lastIndexOf("/");
        if (lastIndexOfSlash > 0) {
            backDir = currentPath.substring(0, lastIndexOfSlash);
        }

        Path absolutePath = Paths.get(PATH_TO_SANDBOX + userLogin + currentPath);
        if (! Files.exists(absolutePath)) {
            req.getRequestDispatcher("/WEB-INF/pages/errors/notFound.jsp").forward(req, resp);
        }

        session.setAttribute(SessionKeys.LAST_PATH, userRootDir + currentPath + "/");

        List<FileInstance> files = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(absolutePath)) {
            for (Path item : directoryStream) {
                files.add(FileInstance.getFileDataFromPath(item));
            }
        }

        String sortBy = req.getParameter(SORT_PARAMETER_NAME);
        if (sortBy == null) {
            sortBy = "Name";
        }
        Boolean reverseSortOrder = Boolean.valueOf(req.getParameter(REVERSE_PARAMETER_NAME));
        if (reverseSortOrder == null) {
            reverseSortOrder = false;
        }
        sortFiles(files, sortBy, reverseSortOrder);

        req.setAttribute("userLogin", userLogin);
        req.setAttribute("currentPath", currentPath);
        req.setAttribute("userRootDir", userRootDir);
        req.setAttribute("backDir", backDir);
        req.setAttribute("files", files);
        req.setAttribute("sortBy", sortBy);
        req.setAttribute("reverseOrder", reverseSortOrder);
        req.getRequestDispatcher("/WEB-INF/pages/view_directory.jsp").forward(req, resp);
    }

    private void sortFiles(List<FileInstance> files, String by, boolean reverse) {
        switch (by) {
            case "Name":
                Collections.sort(files, comparatorByName);
                break;
            case "Size":
                Collections.sort(files, comparatorBySize);
                break;
            case "Type":
                Collections.sort(files, comparatorByType);
                break;
            case "Date":
                Collections.sort(files, comparatorByDate);
                break;
            default:
                Collections.sort(files, comparatorByName);
        }
        if (reverse) {
            Collections.reverse(files);
        }
    }

}
