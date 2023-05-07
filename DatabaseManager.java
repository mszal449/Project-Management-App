import java.sql.*;

public class DatabaseManager {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public void connectToDatabase() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String user = "new_admin";
        String password = "password";
        connection = DriverManager.getConnection(url, user, password);
    }

    public void disconnectFromDatabase() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    // Other methods for executing SQL queries and updates
}