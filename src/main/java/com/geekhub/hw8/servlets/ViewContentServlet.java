package com.geekhub.hw8.servlets;

import com.geekhub.hw8.keys.SessionKeys;
import com.geekhub.hw8.storage.FileInstance;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

import java.io.IOException;
import java.util.List;

import static com.geekhub.hw8.storage.RootDirectory.PATH_TO_SANDBOX;

@WebServlet("/storage/*")
public class ViewContentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getRequestURI().endsWith("/")) {
            showDirectory(req, resp);
        } else {
            showFile(req, resp);
        }
    }

    private void showDirectory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String userLogin = (String) session.getAttribute(SessionKeys.USER_LOGIN);

        String userRootDir = "/storage/" + userLogin;

        String currentPath = req.getRequestURI().substring(userRootDir.length());
        if (currentPath.endsWith("/")) {
            currentPath = currentPath.substring(0, currentPath.length() - 1);
        }

        String backDir = "";
        int lastIndexOfSlash = currentPath.lastIndexOf("/");
        if (lastIndexOfSlash > 0) {
            backDir = currentPath.substring(0, lastIndexOfSlash);
        }

        Path absolutePath = Paths.get(PATH_TO_SANDBOX + userLogin + currentPath);

        List<FileInstance> files = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(absolutePath)) {
            for (Path item : directoryStream) {
                files.add(getFileDataFromPath(item));
            }
        }

        req.setAttribute("userLogin", userLogin);
        req.setAttribute("currentPath", currentPath);
        req.setAttribute("userRootDir", userRootDir);
        req.setAttribute("backDir", backDir);
        req.setAttribute("files", files);
        req.getRequestDispatcher("/WEB-INF/pages/viewDirectory.jsp").forward(req, resp);
    }

    private FileInstance getFileDataFromPath(Path directoryItem) throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(directoryItem, BasicFileAttributes.class);

        String name = directoryItem.getFileName().toString();
        boolean isDirectory = attrs.isDirectory();
        long size = Files.size(directoryItem);
        String creationTime = FileInstance.FILE_CREATION_DATE_TIME_FORMAT.format(attrs.creationTime().toMillis());

        return new FileInstance(name, isDirectory, size, creationTime);
    }

    // ===============================================================================================

    private void showFile(HttpServletRequest req, HttpServletResponse resp) {
        resp.setStatus(404); // TODO implement it
    }

}
