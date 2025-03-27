// import java.sql.*;
// class DBConnection {
//     static Connection connect() {
//         try {
//             Class.forName("com.mysql.cj.jdbc.Driver");
//             Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdb", "root", "");
//             System.out.println("Database connected successfully!");
//             return conn;
//         } catch (Exception e) {
//             System.err.println("Database connection failed: " + e.getMessage());
//             e.printStackTrace();
//             return null;
//         }
//     }
// }
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DBConnection {
    static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdb", "root", "");
            System.out.println("Database connected successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection failed! Check MySQL service and credentials.");
            e.printStackTrace();
        }
        return conn;
    }
}
