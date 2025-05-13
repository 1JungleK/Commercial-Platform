package com.jungle.client.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.jungle.client.service.ClientService;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class RegisterPanel extends JPanel {
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField confirmPasswordField;
    private JTextField emailField;
    private JButton registerButton;
    private JButton cancelButton;

    public RegisterPanel(ClientService clientService) {

        // Set the layout for the panel
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 30, 10, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Username Label and Field
        gbc.gridy = 0;
        usernameField = createStyledTextField("Username");
        add(usernameField, gbc);

        // Password Label and Field
        gbc.gridy = 1;
        passwordField = createStyledPasswordField("Password");
        add(passwordField, gbc);

        // Confirm Password Label and Field
        gbc.gridy = 2;
        confirmPasswordField = createStyledPasswordField("Confirm Password");
        add(confirmPasswordField, gbc);

        // Email Label and Field
        gbc.gridy = 3;
        emailField = createStyledTextField("Email");
        add(emailField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        registerButton = new JButton("Register");
        registerButton.setOpaque(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);
        registerButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        registerButton.setForeground(Color.WHITE);
        registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        cancelButton = new JButton("Cancel");
        cancelButton.setOpaque(false);
        cancelButton.setContentAreaFilled(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setFocusPainted(false);
        cancelButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String email = emailField.getText();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty()) {
                System.out.println("Please fill in all fields.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                System.out.println("Passwords do not match.");
                return;
            }

            // Call the client service to register the user
            clientService.register(username, password, email);
        });

        cancelButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(this).dispose();
        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gradient = new GradientPaint(0, 0, new Color(74, 144, 226), 0, getHeight(), new Color(58, 123, 213));
        g2d.setPaint(gradient);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField textField = new JTextField(20);
        textField.setForeground(Color.WHITE);
        textField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        textField.setOpaque(false);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(255, 255, 255, 150)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        textField.setCaretColor(Color.WHITE);
        
        // 添加占位符效果
        textField.setText(placeholder);
        textField.setForeground(new Color(255, 255, 255, 150));
        
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.WHITE);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(new Color(255, 255, 255, 150));
                    textField.setText(placeholder);
                }
            }
        });
        
        return textField;
    }

    private JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setForeground(Color.WHITE);
        passwordField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        passwordField.setOpaque(false);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(255, 255, 255, 150)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        passwordField.setCaretColor(Color.WHITE);
        
        // 添加占位符效果
        passwordField.setEchoChar((char)0);
        passwordField.setText(placeholder);
        passwordField.setForeground(new Color(255, 255, 255, 150));
        
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(passwordField.getPassword()).equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setEchoChar('•');
                    passwordField.setForeground(Color.WHITE);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getPassword().length == 0) {
                    passwordField.setEchoChar((char)0);
                    passwordField.setForeground(new Color(255, 255, 255, 150));
                    passwordField.setText(placeholder);
                }
            }
        });
        
        return passwordField;
    }

}