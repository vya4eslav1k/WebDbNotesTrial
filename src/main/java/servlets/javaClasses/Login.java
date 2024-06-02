package servlets.javaClasses;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean sendLoginForm = true;
        Cookie[] cookies = req.getCookies();
        if (cookies != null && !Boolean.parseBoolean(req.getParameter("logout"))) {
            String login = null;
            String password = null;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("login")) login = cookie.getValue();
                else if (cookie.getName().equals("password")) password = cookie.getValue();
            }
            if (login != null && password != null) {
                sendLoginForm = false;
                resp.sendRedirect("/home?login=" + login + "&password=" + password);
            }
        }
        if(sendLoginForm) {
            req.getRequestDispatcher("/loginForm.html").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  {

    }
}
