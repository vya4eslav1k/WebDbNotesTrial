package servlets.javaClasses;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.DBTool;

import java.io.IOException;
import java.io.PrintWriter;

public class Registration extends jakarta.servlet.http.HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/registrationForm.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        DBTool dbTool = DBTool.getInstance();
        if (dbTool.userExists(login)){
//            req.getRequestDispatcher("loginIsBusy.html").forward(req, resp);
            PrintWriter printWriter = resp.getWriter();
            printWriter.println("<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Title</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<br>\n" +
                    "<br>\n" +
                    "<h1 align=\"center\">Login is busy!</h1>\n" +
                    "<br>\n" +
                    "<br>\n" +
                    "<center>\n" +
                    "    <form action=\"/registration\">\n" +
                    "        <button>back to registration</button>\n" +
                    "    </form>\n" +
                    "</center>\n" +
                    "</body>\n" +
                    "</html>");
        }
        else {
            dbTool.addUser(login, password);
            resp.sendRedirect("/home?login=" + login + "&password=" + password);
        }
    }
}
