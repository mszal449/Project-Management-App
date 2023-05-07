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

    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.name);
        statement.setString(2, user.login_data.email);
        statement.setString(3, user.login_data.password);
        statement.executeUpdate();
    }

    // Other methods for executing SQL queries and updates
}