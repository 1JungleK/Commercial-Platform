package com.jungle.client.ui;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;

import com.jungle.client.service.ClientService;
import com.jungle.protocol.Response;
import com.jungle.protocol.ResponseStatus;

public class LoginForm extends JFrame {
    ClientService clientService;
    public LoginForm(ClientService clientService) {
        this.clientService = clientService;

        setTitle("Market of Jungle");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20)); // 圆角窗口
        
        // 主面板 - 使用渐变背景
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(0, 0, new Color(74, 144, 226), 0, getHeight(), new Color(58, 123, 213));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));
        
        // 标题面板
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BorderLayout());
        
        // 应用名称
        JLabel appName = new JLabel("Market of Jungle", SwingConstants.CENTER);
        appName.setFont(new Font("Raleway", Font.BOLD, 24));
        appName.setForeground(Color.WHITE);
        titlePanel.add(appName, BorderLayout.SOUTH);
        
        // 表单面板
        JPanel formPanel = new JPanel();
        formPanel.setOpaque(false);
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        
        // 用户名输入框
        JTextField usernameField = createStyledTextField("username");
        gbc.gridy = 0;
        formPanel.add(usernameField, gbc);
        
        // 密码输入框
        JPasswordField passwordField = createStyledPasswordField("password");
        gbc.gridy = 1;
        formPanel.add(passwordField, gbc);
        
        // 记住我复选框
        JCheckBox rememberMe = new JCheckBox("Remember Me");
        rememberMe.setOpaque(false);
        rememberMe.setForeground(Color.WHITE);
        rememberMe.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        gbc.gridy = 2;
        formPanel.add(rememberMe, gbc);
        
        // 登录按钮
        JButton loginButton = createStyledButton("Login");
        gbc.gridy = 3;
        formPanel.add(loginButton, gbc);
        
        // 底部链接
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        bottomPanel.setOpaque(false);
        
        JButton forgotPassword = new JButton("Forgot Password?");
        forgotPassword.setOpaque(false);
        forgotPassword.setContentAreaFilled(false); 
        forgotPassword.setBorderPainted(false);
        forgotPassword.setFocusPainted(false);
        forgotPassword.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        forgotPassword.setForeground(Color.WHITE);
        forgotPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        JButton register = new JButton("Register");
        register.setOpaque(false);
        register.setContentAreaFilled(false);
        register.setBorderPainted(false);
        register.setFocusPainted(false);
        register.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        register.setForeground(Color.WHITE);
        register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        bottomPanel.add(forgotPassword);
        bottomPanel.add(register);
        
        // 关闭按钮
        JButton closeButton = new JButton("×");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 26));
        closeButton.setForeground(Color.WHITE);
        closeButton.setContentAreaFilled(false);
        closeButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> System.exit(0));
        
        // 组装界面
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setOpaque(false); 
        northPanel.add(closeButton, BorderLayout.EAST); 
        northPanel.add(titlePanel, BorderLayout.SOUTH); 

        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // 登录按钮事件
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                
                Response response = clientService.login(username, password);

                if (response != null && response.getStatus() == ResponseStatus.SUCCESS) {
                    JOptionPane.showMessageDialog(LoginForm.this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginForm.this, "Login failed: " + (response != null ? response.getMessage() : "Unknown error"), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    
    // 创建样式化文本框
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
    
    // 创建样式化密码框
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
    
    // 创建样式化按钮
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2.setColor(new Color(255, 255, 255, 100));
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(255, 255, 255, 150));
                } else {
                    g2.setColor(Color.WHITE);
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        
        button.setContentAreaFilled(false);
        button.setForeground(new Color(58, 123, 213));
        button.setFont(new Font("微软雅黑", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        return button;
    }
}
