package com.geekhub.hw8.servlets;

import com.geekhub.hw8.keys.SessionKeys;
import com.geekhub.hw8.utils.ZipUtils;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static com.geekhub.hw8.storage.RootDirectory.PATH_TO_SANDBOX;

/**
 * Allows to user upload files
 */
@WebServlet("/upload/*")
@MultipartConfig
public class UploadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String userLogin = (String) session.getAttribute(SessionKeys.USER_LOGIN);

        String userRootDirPrefix = "/upload/" + userLogin;
        String currentPath = URLDecoder.decode(req.getRequestURI(), "UTF-8").substring(userRootDirPrefix.length());
        Path absolutePath = Paths.get(PATH_TO_SANDBOX + userLogin + currentPath);

        String folderName = req.getParameter("name");
        if (folderName == null || folderName.equals("")) {
            resp.sendRedirect((String) session.getAttribute(SessionKeys.LAST_PATH));
        }

        createFolder(absolutePath, folderName);
        resp.sendRedirect((String) session.getAttribute(SessionKeys.LAST_PATH));
    }

    private void createFolder(Path absolutePath, String folderName) throws IOException {
        Path newDirectoryPath = absolutePath.resolve(folderName);
        if (!Files.exists(newDirectoryPath)) {
            Files.createDirectory(newDirectoryPath);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String userLogin = (String) session.getAttribute(SessionKeys.USER_LOGIN);

        Part filePart = req.getPart("file");
        String fileName = filePart.getSubmittedFileName();

        String userRootDirPrefix = "/upload/" + userLogin;
        String currentPath = URLDecoder.decode(req.getRequestURI(), "UTF-8").substring(userRootDirPrefix.length());
        Path absolutePath = Paths.get(PATH_TO_SANDBOX + userLogin + currentPath);
        Path fileAbsolutePath  = absolutePath.resolve(fileName);

        try (InputStream fileContent = filePart.getInputStream()) {
            Files.copy(fileContent, fileAbsolutePath);
        }

        String itemType = req.getParameter("type");
        switch (itemType) {
            case "archive":
                if (fileName.endsWith(".zip")) {
                    fileName = fileName.substring(0, fileName.length() - 4);
                } else {
                    Path origin = fileAbsolutePath;
                    fileAbsolutePath = fileAbsolutePath.getParent().resolve(fileName + ".tmp");
                    Files.move(origin ,fileAbsolutePath , StandardCopyOption.REPLACE_EXISTING);
                }
                unzip(fileAbsolutePath, absolutePath.resolve(fileName));
                Files.delete(fileAbsolutePath);
                break;
        }

        resp.sendRedirect((String) session.getAttribute(SessionKeys.LAST_PATH));
    }

    private void unzip(Path fileAbsolutePath, Path destAbsolutePath) throws IOException {
        if (! ZipUtils.isValidZipArchive(fileAbsolutePath.toFile())) {
            throw new RuntimeException("Uploaded file is not correct zip archive");
        }

        ZipUtils.unzip(fileAbsolutePath.toString(), destAbsolutePath.toString());
    }

}
