package com.peristic.banana.peripherals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginGUI extends JFrame {

    private static final long serialVersionUID = -6921462126880570161L;

    public static void main(String[] args) {
        new LoginGUI();
    }

    JButton blogin = new JButton("Login");
    JButton bsignup = new JButton("Sign Up");
    JTextField txuser = new JTextField(15);
    JPasswordField pass = new JPasswordField(15);
    JLabel titleLabel = new JLabel("Welcome to Banana Game!");
    
    LoginData ldata = new LoginData();

    LoginGUI() {
        super("Banana Game Login");
        setSize(700, 500); 
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load background image
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/resources/banana.png"));
        Image backgroundImage = backgroundIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);

        // Set up main panel with custom paintComponent to draw background
        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        // Custom Colors
        Color buttonColor = new Color(255, 204, 51);  // Banana yellow
        Color textColor = Color.BLACK; // Changed to black

        // Set up title label
        titleLabel.setBounds(100, 20, 500, 50);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        titleLabel.setForeground(textColor);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Set bounds and styles for components
        txuser.setBounds(250, 150, 200, 30);
        pass.setBounds(250, 200, 200, 30);
        blogin.setBounds(230, 300, 100, 40);
        bsignup.setBounds(370, 300, 100, 40);

        blogin.setBackground(buttonColor);
        blogin.setForeground(Color.BLACK);
        bsignup.setBackground(buttonColor);
        bsignup.setForeground(Color.BLACK);

        // Add components to panel
        panel.add(titleLabel);
        panel.add(blogin);
        panel.add(bsignup);
        panel.add(txuser);
        panel.add(pass);

        // Add panel to frame
        getContentPane().add(panel);
        setVisible(true);

        // Initialize action listeners for buttons
        actionlogin();
        actionSignUp(); 
    }

    public void actionlogin() {
        blogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String puname = txuser.getText();
                String ppaswd = String.valueOf(pass.getPassword());
                if (ldata.checkPassword(puname, ppaswd)) { 
                    GameGUI theGame = new GameGUI(puname); 
                    theGame.setVisible(true); 
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong Password / Username");
                    txuser.setText("");
                    pass.setText("");
                    txuser.requestFocus();
                }
            }
        });
    }

    public void actionSignUp() {
        bsignup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                SignUpGUI signUpForm = new SignUpGUI();
                signUpForm.setVisible(true);
                dispose();
            }
        });
    }
}
