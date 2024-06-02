<%@ page import="logic.DBTool" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="logic.Note" %><%--
  Created by IntelliJ IDEA.
  User: Максим
  Date: 29.05.2024
  Time: 20:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Home page</title>
</head>
<body>
<h4>Your profile:</h4>
login: <% out.println(request.getParameter("login")); %><br>
password: <% out.println(request.getParameter("password")); %><br>
<input type="button" value="Another account" onclick="window.location='/login?logout=true'">
<h1 align="center">Notes:</h1>

<center>
    <form method="post" action="/createNote?login=<%out.println(request.getParameter("login"));%>&password=<%out.println(request.getParameter("password"));%>">
        <input type="submit" value="New note">
    </form>
<%
    String login = request.getParameter("login");
    String password = request.getParameter("password");
    DBTool dbTool = DBTool.getInstance();
    ArrayList<Note> notes = dbTool.getNotes(login, password);
    for (Note note : notes) {
        out.println("<form method=\"post\" action=\"/updateNote?noteId="+ note.getId() + "&login=" + login + "&password=" + password + "\">");
        out.println("<textarea rows=\"10\" cols=\"100\" name=\"text\">");
        out.println(note.getText());
        out.println("</textarea>");
        out.println("<input type=\"submit\" value=\"Save\">");
        out.println("</form>");
    }
%>
</center>
</body>
</html>
