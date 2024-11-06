package com.peristic.banana.peripherals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SignUpGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    JButton bsignUp = new JButton("Sign Up");
    JButton bback = new JButton("Back");
    JPanel panel = new JPanel();
    JLabel nameLabel = new JLabel("Name:");
    JLabel usernameLabel = new JLabel("Username:");
    JLabel passwordLabel = new JLabel("Password:");
    JTextField txname = new JTextField(15);
    JTextField txuser = new JTextField(15);
    JPasswordField pass = new JPasswordField(15);

    LoginData ldata = new LoginData();

    SignUpGUI() {
        super("Sign Up");

        setSize(400, 250); // Adjust size to match the layout
        setLocation(500, 280);
        panel.setLayout(null); // Use absolute layout

        // Set background color to dark mode
        panel.setBackground(Color.BLACK);

        // Set text color to white for labels
        nameLabel.setForeground(Color.WHITE);
        usernameLabel.setForeground(Color.WHITE);
        passwordLabel.setForeground(Color.WHITE);

        // Set custom fonts (optional)
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        nameLabel.setFont(labelFont);
        usernameLabel.setFont(labelFont);
        passwordLabel.setFont(labelFont);

        // Position the components
        nameLabel.setBounds(80, 30, 100, 20);
        txname.setBounds(180, 30, 200, 25);

        usernameLabel.setBounds(80, 70, 100, 20);
        txuser.setBounds(180, 70, 200, 25);

        passwordLabel.setBounds(80, 110, 100, 20);
        pass.setBounds(180, 110, 200, 25);

        bsignUp.setBounds(150, 170, 100, 35);
        bback.setBounds(270, 170, 100, 35);

        // Customize the buttons (purple color for 'Sign Up')
        bsignUp.setBackground(new Color(128, 0, 128));
        bsignUp.setForeground(Color.WHITE);

        // Add all components to the panel
        panel.add(nameLabel);
        panel.add(txname);
        panel.add(usernameLabel);
        panel.add(txuser);
        panel.add(passwordLabel);
        panel.add(pass);
        panel.add(bsignUp);
        panel.add(bback);

        // Add the panel to the frame
        getContentPane().add(panel);

        // Set default close operation and visibility
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Initialize button actions
        actionSignUp();
        actionBack();
    }

    public void actionSignUp() {
        bsignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String name = txname.getText();
                String username = txuser.getText();
                String password = String.valueOf(pass.getPassword());

                if (ldata.signUp(name, username, password)) {
                    JOptionPane.showMessageDialog(null, "Sign-Up Successful!");
                    LoginGUI loginForm = new LoginGUI();
                    loginForm.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Sign-Up Failed. Try Again.");
                    txname.setText("");
                    txuser.setText("");
                    pass.setText("");
                }
            }
        });
    }

    public void actionBack() {
        bback.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                LoginGUI loginForm = new LoginGUI();
                loginForm.setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        new SignUpGUI();
    }
}
