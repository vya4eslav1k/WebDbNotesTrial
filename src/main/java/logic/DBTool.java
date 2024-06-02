package logic;

import java.sql.*;
import java.util.ArrayList;

public final class DBTool {
    static private final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    static private final String USERNAME = "root";
    static private final String PASSWORD = "root";
    static private Connection connection;
    static private DBTool instance;

    private DBTool() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static DBTool getInstance() {
        if (instance == null) {
            instance = new DBTool();
        }
        return instance;
    }

    public void addUser(String login, String password) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (login, password) VALUES (?, ?);");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean userExists(String login) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE login = ?");
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkPassword(String login, String password) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE login = ?");
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getString("password").equals(password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Note> getNotes(String login, String password) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM note " +
                            "INNER JOIN user ON user.id = note.userId " +
                            "WHERE login=? and password=?" +
                            " ORDER BY lastUpdate DESC");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Note> notes = new ArrayList<>();
            while (resultSet.next()) {
                notes.add(new Note(resultSet.getInt("id"), resultSet.getString("text")));
            }
            return notes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateNote(Integer noteId, String text) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE note SET text = ?, lastUpdate=NOW() WHERE id = ?");
            preparedStatement.setString(1, text);
            preparedStatement.setInt(2, noteId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createNote(String login, String password, String text) {
        int userId = getUserId(login, password);
        if (userId > 0) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO note (userId, text, lastUpdate) VALUES (?, ?, NOW());");
                preparedStatement.setInt(1, userId);
                preparedStatement.setString(2, text);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private int getUserId(String login, String password) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE login = ? and password = ?;");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
            else {
                return -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkIfUserHasNote(String login, String password, int noteId) {
        int userId = getUserId(login, password);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM note WHERE userId=? AND id=?;");
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, noteId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
