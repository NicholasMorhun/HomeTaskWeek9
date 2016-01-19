package com.geekhub.hw9.servlets;

import javax.servlet.annotation.WebServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.geekhub.hw9.keys.SessionKeys;
import com.geekhub.hw9.utils.ZipUtils;

import static com.geekhub.hw9.storage.RootDirectory.PATH_TO_SANDBOX;

/**
 * Allows to user download files
 */
@WebServlet("/download/*")
public class DownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String userLogin = (String) session.getAttribute(SessionKeys.USER_LOGIN);
        String userRootDir = "/download/" + userLogin;
        String currentPath = URLDecoder.decode(req.getRequestURI(), "UTF-8").substring(userRootDir.length());
        Path absolutePath = Paths.get(PATH_TO_SANDBOX + userLogin + currentPath);
        String fileName = getFileNameFromPath(absolutePath);
        if (!Files.exists(absolutePath)) {
            req.getRequestDispatcher("/error404").forward(req, resp);
        }

        OutputStream out = resp.getOutputStream();

        if (Files.isDirectory(absolutePath)) {
            resp.setContentType("application/zip");
            resp.setHeader("Content-disposition", "attachment; filename=\"" + fileName + ".zip\"");
            processDownloadFolder(absolutePath, out);
        } else {
            String mimeType = getServletContext().getMimeType(fileName);
            resp.setContentType(mimeType);

            String inline = req.getParameter("inline");
            boolean isInline = inline != null && inline.equals("true");
            if (isInline) {
                resp.setHeader("Content-disposition", "inline; filename=" + fileName);
            } else {
                resp.setHeader("Content-disposition", "attachment; filename=" + fileName);
            }

            processDownloadFile(absolutePath, out);
        }
    }

    private String getFileNameFromPath(Path absolutePath) {
        String path = absolutePath.toString();
        return path.substring(path.lastIndexOf("/") + 1);
    }

    private void processDownloadFile(Path absolutePath, OutputStream out) throws IOException {
        int b;
        try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(absolutePath.toFile()));
             BufferedOutputStream os = new BufferedOutputStream(out)
        ) {
            while ((b = is.read()) != -1) {
                os.write(b);
            }
        }
    }

    private void processDownloadFolder(Path absolutePath, OutputStream out) throws IOException {
        String folderForZip = absolutePath.toString();
        String zipArchiveName = folderForZip + ".zip";

        ZipUtils.zipFolder(folderForZip, zipArchiveName);

        Path zipArchivePath = Paths.get(zipArchiveName);
        processDownloadFile(zipArchivePath, out);
        Files.delete(zipArchivePath);
    }

}
