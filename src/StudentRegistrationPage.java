import javax.swing.*;
import java.awt.*;
import java.sql.*;

class StudentRegistrationPage extends JFrame {
    JTextField nameField, idField, dobField, emailField, contactField, addressField, courseField;
    JComboBox<String> genderBox;
    JButton submitButton, backButton;

    StudentRegistrationPage() {
        setTitle("Student Registration");
        setSize(500, 550);
        setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Register New Student", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);
        
        JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        
        formPanel.add(new JLabel("Student ID:"));
        idField = new JTextField();
        formPanel.add(idField);
        
        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);
        
        formPanel.add(new JLabel("DOB (YYYY-MM-DD):"));
        dobField = new JTextField();
        formPanel.add(dobField);
        
        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);
        
        formPanel.add(new JLabel("Contact No:"));
        contactField = new JTextField();
        formPanel.add(contactField);
        
        formPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        formPanel.add(addressField);
        
        formPanel.add(new JLabel("Gender:"));
        genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        formPanel.add(genderBox);
        
        formPanel.add(new JLabel("Course:"));
        courseField = new JTextField();
        formPanel.add(courseField);
        
        add(formPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        submitButton = new JButton("Submit");
        backButton = new JButton("Back to Main Menu");
        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        submitButton.addActionListener(e -> saveStudent());
        backButton.addActionListener(e -> {
            dispose();
            new MainMenu();
        });
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    void saveStudent() {
        Connection conn = DBConnection.connect();
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO students (student_id, name, dob, email, contact_no, address, gender, course, attendance) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");) {
            stmt.setString(1, idField.getText());
            stmt.setString(2, nameField.getText());
            stmt.setString(3, dobField.getText());
            stmt.setString(4, emailField.getText());
            stmt.setString(5, contactField.getText());
            stmt.setString(6, addressField.getText());
            stmt.setString(7, genderBox.getSelectedItem().toString());
            stmt.setString(8, courseField.getText());
            stmt.setInt(9, 0); // Default attendance to 0
    
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student Registered Successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
