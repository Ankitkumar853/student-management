import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class StudentManagementSystem {
    public static void main(String[] args) {
        new MainMenu();
    }
}

class MainMenu extends JFrame {
    JButton registerButton, viewDetailsButton, manageAttendanceButton;

    MainMenu() {
        setTitle("Student Management System - Main Menu");
        setSize(500, 300);
        setLayout(new BorderLayout());
        
        JLabel headerLabel = new JLabel("Welcome to Student Management System", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(headerLabel, BorderLayout.NORTH);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        
        registerButton = new JButton("Register Student");
        viewDetailsButton = new JButton("View Student Details");
        manageAttendanceButton = new JButton("Manage Attendance");
        
        buttonPanel.add(registerButton);
        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(manageAttendanceButton);
        
        add(buttonPanel, BorderLayout.CENTER);
        
        registerButton.addActionListener(e -> new StudentRegistrationPage());
        viewDetailsButton.addActionListener(e -> new StudentDetailsPage());
        manageAttendanceButton.addActionListener(e -> new UpdateAttendancePage());
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}