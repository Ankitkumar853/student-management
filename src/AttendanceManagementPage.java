import javax.swing.*;
import java.awt.*;
import java.sql.*;

class UpdateAttendancePage extends JFrame {
    JLabel studentIdLabel, nameLabel, attendanceLabel;
    JButton markPresentButton, nextButton, backButton;
    ResultSet studentResultSet;
    
    UpdateAttendancePage() {
        setTitle("Update Attendance");
        setSize(400, 300);
        setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Increase Attendance", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);
        
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        
        formPanel.add(new JLabel("Student ID:"));
        studentIdLabel = new JLabel("-");
        formPanel.add(studentIdLabel);
        
        formPanel.add(new JLabel("Name:"));
        nameLabel = new JLabel("-");
        formPanel.add(nameLabel);
        
        formPanel.add(new JLabel("Current Attendance:"));
        attendanceLabel = new JLabel("-");
        formPanel.add(attendanceLabel);
        
        add(formPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        markPresentButton = new JButton("Mark Present");
        nextButton = new JButton("Next");
        backButton = new JButton("Back to Main Menu");
        buttonPanel.add(markPresentButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        markPresentButton.addActionListener(e -> increaseAttendance());
        nextButton.addActionListener(e -> moveToNextStudent());
        backButton.addActionListener(e -> {
            dispose();
            new MainMenu();
        });
        
        fetchAllStudents();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    void fetchAllStudents() {
        Connection conn = DBConnection.connect();
        if (conn == null) return;
    
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT student_id, name, attendance FROM students ORDER BY student_id", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            studentResultSet = stmt.executeQuery();
            
            if (studentResultSet.next()) {
                displayStudent();
            } else {
                JOptionPane.showMessageDialog(this, "No students found!", "Error", JOptionPane.ERROR_MESSAGE);
                dispose();
                new MainMenu();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    void displayStudent() {
        try {
            if (studentResultSet != null && !studentResultSet.isAfterLast()) {
                studentIdLabel.setText(studentResultSet.getString("student_id"));
                nameLabel.setText(studentResultSet.getString("name"));
                attendanceLabel.setText(String.valueOf(studentResultSet.getInt("attendance")));
            } else {
                JOptionPane.showMessageDialog(this, "All students have been processed", "Info", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new MainMenu();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    void increaseAttendance() {
        try {
            if (studentResultSet == null || studentResultSet.isAfterLast()) {
                JOptionPane.showMessageDialog(this, "No more students to update", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
    
            String studentId = studentIdLabel.getText();
            Connection conn = DBConnection.connect();
            if (conn == null) return;
    
            try (PreparedStatement stmt = conn.prepareStatement("UPDATE students SET attendance = attendance + 1 WHERE student_id = ?")) {
                stmt.setString(1, studentId);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Attendance Increased Successfully!");
    
                if (studentResultSet.next()) {
                    displayStudent();
                } else {
                    JOptionPane.showMessageDialog(this, "All students have been marked", "Info", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new MainMenu();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    void moveToNextStudent() {
        try {
            if (studentResultSet.next()) {
                displayStudent();
            } else {
                JOptionPane.showMessageDialog(this, "All students have been processed", "Info", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new MainMenu();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
