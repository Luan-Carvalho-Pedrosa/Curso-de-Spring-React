package com.spring_react.spring_react;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class ThisApplication implements Runnable {
    

    public static JLabel label1 = new JLabel("Servidor ativado", SwingConstants.CENTER);



    public static void setLabel1(String message) {
        System.out.println(label1.getText()); 
        label1.setText(message);
    }


    public static void openWindow() {
        label1.setFont(new Font("Calibri", Font.BOLD, 35));
        label1.setForeground(Color.white);

        String iconPath = "C:\\Users\\Luan\\OneDrive\\Projetos\\SpringReact\\spring_react\\src\\main\\resources\\icon\\icon.png";
        ImageIcon icon = new ImageIcon(iconPath);

        JFrame janela = new JFrame();
        janela.setSize(400, 400);
        janela.getContentPane().setBackground(new Color(200, 30, 50));
        janela.add(label1);
        janela.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        janela.setIconImage(icon.getImage());
        janela.setTitle("Servidor");
        janela.setVisible(true);
        System.out.println("abrindo janela");
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        System.out.println("rodando");
        openWindow();
    }

    
        
   
    
}
