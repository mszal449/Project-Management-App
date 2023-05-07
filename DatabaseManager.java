import java.sql.*;
import java.util.Arrays;

public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection connection;

    // prywatny konstruktor i statyczna metoda getInstance gwarantują,
    // że będzie istniał tylko jeden obiekt zarządający bazą danych na raz
    private DatabaseManager() {}

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public void connectToDatabase() throws SQLException {
        String url = "jdbc:sqlite:mydatabase.db";
        connection = DriverManager.getConnection(url);
    }

    public void disconnectFromDatabase() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public void addUser(User user) throws SQLException {
        // sprawdzenie, czy email już istnieje w bazie
        String checkSql = "SELECT COUNT(*) FROM users WHERE email=?";
        PreparedStatement checkStmt = connection.prepareStatement(checkSql);
        checkStmt.setString(1, user.login_data.email);
        ResultSet checkResult = checkStmt.executeQuery();
        checkResult.next();
        int count = checkResult.getInt(1);
        if (count > 0) {
            throw new SQLException("Email already exists in database");
        }
        // jeśli nie, to go dodajemy
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.name);
        statement.setString(2, user.login_data.email);
        statement.setString(3, user.login_data.password);
        statement.executeUpdate();
    }

    public boolean checkCredentials(String email, char[] password) throws SQLException {
        String pswd = String.valueOf(password);
        String checkSql = "SELECT COUNT(*) FROM users WHERE email=? AND password=?";
        PreparedStatement statement = connection.prepareStatement(checkSql);
        statement.setString(1, email);
        statement.setString(2, pswd);
        ResultSet checkResult = statement.executeQuery();
        return checkResult.next() && checkResult.getInt(1) > 0;
    }

    // Other methods for executing SQL queries and updates
}