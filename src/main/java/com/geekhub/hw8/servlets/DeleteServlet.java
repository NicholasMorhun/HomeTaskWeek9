package com.geekhub.hw8.servlets;

import com.geekhub.hw8.keys.SessionKeys;

import javax.servlet.annotation.WebServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.geekhub.hw8.storage.RootDirectory.PATH_TO_SANDBOX;

/**
 * Deletes files and folders
 */
@WebServlet("/delete/*")
public class DeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String userLogin = (String) session.getAttribute(SessionKeys.USER_LOGIN);
        String userRootDir = "/delete/" + userLogin;
        String currentPath = req.getRequestURI().substring(userRootDir.length());
        Path absolutePath = Paths.get(PATH_TO_SANDBOX + userLogin + currentPath);
        String itemNameForDelete = req.getParameter("name");
        Path itemForDeletePath = absolutePath.resolve(itemNameForDelete);
        recursiveDelete(itemForDeletePath.toFile());
    }

    private void recursiveDelete(File itemForDelete) throws IOException {
        if (itemForDelete == null) {
            throw new IllegalArgumentException();
        }

        if (itemForDelete.isDirectory()) {
            for (File file : itemForDelete.listFiles()) {
                recursiveDelete(file);
            }
        }
        if (!itemForDelete.delete()) {
            throw new IOException("Failed to delete file: " + itemForDelete);
        }
    }

}
