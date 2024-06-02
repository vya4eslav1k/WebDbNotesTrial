package servlets.javaClasses;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.DBTool;

import java.io.IOException;

public class Home extends jakarta.servlet.http.HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (login == null || password == null) {
            resp.sendRedirect("/login");
        }
        else {
            DBTool dbTool = DBTool.getInstance();
            if (!dbTool.userExists(login)) {
                req.getRequestDispatcher("noUserFound.html").forward(req,resp);
            }
            else {
                if (dbTool.checkPassword(login,password)) {
                    resp.addCookie(new Cookie("password", password));
                    resp.addCookie(new Cookie("login", login));
                    req.getRequestDispatcher("home.jsp").forward(req,resp);
                }
                else {
                    req.getRequestDispatcher("wrongPassword.html").forward(req,resp);
                }
            }
        }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

    }
}
