package com.geekhub.hw8.servlets;

import com.geekhub.hw8.keys.SessionKeys;
import com.geekhub.hw8.storage.FileInstance;

import javax.servlet.annotation.WebServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.geekhub.hw8.storage.RootDirectory.PATH_TO_SANDBOX;

/**
 * View user's file in web page
 */
@WebServlet("/view/*")
public class ViewFileServlet extends HttpServlet {

    private static final Set<String> PLAIN_TEXT = new HashSet<>(Arrays.asList(
            "txt"
    ));
    private static final Set<String> IMAGES = new HashSet<>(Arrays.asList(
            "bmp","jpg","jpeg","png","gif","ico"
    ));
    private static final Set<String> SOURCE_CODE = new HashSet<>(Arrays.asList(
            "c","cpp","java","pas","js","php","py","pl","scala","sql","css","html","xml","sh"
    ));
    private static final Set<String> AUDIO = new HashSet<>(Arrays.asList(
            "mp3","ogg","wav"
    ));

    private static final Map<String, String> syntaxHighlighterBrushesNames = new HashMap<>();

    @Override
    public void init() throws ServletException {
        syntaxHighlighterBrushesNames.put("c", "shBrushCpp");
        syntaxHighlighterBrushesNames.put("cpp", "shBrushCpp");
        syntaxHighlighterBrushesNames.put("java", "shBrushJava");
        syntaxHighlighterBrushesNames.put("pas", "shBrushDelphi");
        syntaxHighlighterBrushesNames.put("js", "shBrushJScript");
        syntaxHighlighterBrushesNames.put("php", "shBrushPhp");
        syntaxHighlighterBrushesNames.put("py", "shBrushPython");
        syntaxHighlighterBrushesNames.put("pl", "shBrushPerl");
        syntaxHighlighterBrushesNames.put("scala", "shBrushScala");
        syntaxHighlighterBrushesNames.put("sql", "shBrushSql");
        syntaxHighlighterBrushesNames.put("css", "shBrushCss");
        syntaxHighlighterBrushesNames.put("html", "shBrushXml");
        syntaxHighlighterBrushesNames.put("xml", "shBrushXml");
        syntaxHighlighterBrushesNames.put("sh", "shBrushBash");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getRequestURI().endsWith("/")) {
            String newUrl = req.getRequestURI().replaceFirst("view", "storage");
            resp.sendRedirect(newUrl);
        }

        HttpSession session = req.getSession();
        String userLogin = (String) session.getAttribute(SessionKeys.USER_LOGIN);
        String userRootDir = "/view/" + userLogin;
        String currentPath = URLDecoder.decode(req.getRequestURI(), "UTF-8").substring(userRootDir.length());
        Path absolutePath = Paths.get(PATH_TO_SANDBOX + userLogin + currentPath);
        if (! Files.exists(absolutePath)) {
            req.getRequestDispatcher("/WEB-INF/pages/errors/notFound.jsp").forward(req, resp);
        }

        showFile(absolutePath, req, resp);
    }

    private void showFile(Path absolutePath, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FileInstance fileInstance = FileInstance.getFileDataFromPath(absolutePath);
        String fileExt = fileInstance.getFileExtension();

        String urlToFile = req.getRequestURI().replaceFirst("view", "download") + "?inline=true";
        req.setAttribute("urlToFile", urlToFile);
        req.setAttribute("fileName", fileInstance.getFileName());
        req.setAttribute("fileExt", fileExt);

        if (PLAIN_TEXT.contains(fileExt)) {
            resp.sendRedirect(urlToFile);
        } else if (IMAGES.contains(fileExt)) {
            req.getRequestDispatcher("/WEB-INF/pages/view/image.jsp").forward(req, resp);
        } else if (SOURCE_CODE.contains(fileExt)) {
            req.setAttribute("brushName", syntaxHighlighterBrushesNames.get(fileExt));
            req.getRequestDispatcher("/WEB-INF/pages/view/source_code.jsp").forward(req, resp);
        } else if (AUDIO.contains(fileExt)) {
            req.getRequestDispatcher("/WEB-INF/pages/view/audio.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher(urlToFile).forward(req, resp);
        }

    }

}
