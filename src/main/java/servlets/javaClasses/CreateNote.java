package servlets.javaClasses;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.DBTool;

import java.io.IOException;

public class CreateNote extends jakarta.servlet.http.HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        DBTool dbTool = DBTool.getInstance();
        dbTool.createNote(login,password,"New note");
        resp.sendRedirect("/login");
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

    }
}
