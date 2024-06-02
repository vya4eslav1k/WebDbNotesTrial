package servlets.javaClasses;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.DBTool;

import java.io.IOException;

public class UpdateNote extends jakarta.servlet.http.HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int noteId = Integer.parseInt(req.getParameter("noteId"));
        String text = req.getParameter("text");
        String password = req.getParameter("password");
        String login = req.getParameter("login");
        DBTool dbTool = DBTool.getInstance();
        if (dbTool.checkIfUserHasNote(login, password, noteId)) {
            dbTool.updateNote(noteId, text);
        }
        resp.sendRedirect("/login");
    }
}
