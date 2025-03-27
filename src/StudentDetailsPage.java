import javax.swing.*;
import java.awt.*;
import java.sql.*;

class StudentDetailsPage extends JFrame {
    JTable studentTable;
    JTextField searchField;
    JButton searchButton, deleteButton, updateButton, backButton;

    StudentDetailsPage() {
        setTitle("Student Details");
        setSize(800, 500);
        setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Student Details", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);
        
        JPanel topPanel = new JPanel(new FlowLayout());
        searchField = new JTextField(10);
        searchButton = new JButton("Search by ID");
        deleteButton = new JButton("Delete Student");
        updateButton = new JButton("Update Student");
        backButton = new JButton("Back to Main Menu");
        
        topPanel.add(new JLabel("Student ID:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(deleteButton);
        topPanel.add(updateButton);
        topPanel.add(backButton);
        
        add(topPanel, BorderLayout.NORTH);
        
        studentTable = new JTable();
        add(new JScrollPane(studentTable), BorderLayout.CENTER);
        
        loadStudentData();
        
        searchButton.addActionListener(e -> searchStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        updateButton.addActionListener(e -> updateStudent());
        backButton.addActionListener(e -> {
            dispose();
            new MainMenu();
        });
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    // void loadStudentData() {
    //     Connection conn = DBConnection.connect();
    //     if (conn == null) {
    //         JOptionPane.showMessageDialog(this, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
    //         return;
    //     }
    
    //     try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM students")) {
    //         ResultSet rs = stmt.executeQuery();
    //         studentTable.setModel(new StudentTableModel(rs));
    //     } catch (SQLException ex) {
    //         JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    //         ex.printStackTrace();
    //     }
    // }
    void loadStudentData() {
        Connection conn = DBConnection.connect();
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM students ORDER BY student_id")) {
            ResultSet rs = stmt.executeQuery();
            studentTable.setModel(new StudentTableModel(rs));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    void searchStudent() {
        String studentId = searchField.getText();
        if (studentId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Student ID", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        Connection conn = DBConnection.connect();
        if (conn == null) return;
        
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM students WHERE student_id = ?")) {
            stmt.setString(1, studentId);
            ResultSet rs = stmt.executeQuery();
            studentTable.setModel(new StudentTableModel(rs));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    void deleteStudent() {
        String studentId = searchField.getText();
        if (studentId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Student ID", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        Connection conn = DBConnection.connect();
        if (conn == null) return;
        
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM students WHERE student_id = ?")) {
            stmt.setString(1, studentId);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student Deleted Successfully!");
            loadStudentData();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    void updateStudent() {
        String studentId = searchField.getText();
        if (studentId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Student ID", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        String newName = JOptionPane.showInputDialog("Enter New Name");
        
        if (newName == null) return;
    
        Connection conn = DBConnection.connect();
        if (conn == null) return;
        
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE students SET name = ? WHERE student_id = ?")) {
            stmt.setString(1, newName);
            stmt.setString(2, studentId);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student Updated Successfully!");
            loadStudentData();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
