import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.awt.image.ImageObserver;
import java.io.*;
import java.net.*;

import javax.swing.border.Border;
import javax.swing.text.*;
import javax.swing.text.html.*;

public class SwingResizeJFrame {
    static Image bgimage;
    public SwingResizeJFrame() {
        JTextField TextField1 = new JTextField("firstTextField");
        JTextField TextField2 = new JTextField("secondTextField");
        JPanel firstPanel = new JPanel();
        firstPanel.setLayout(new GridLayout(0, 2, 10, 10));
        JLabel lbl1 = new JLabel(" Welcome to Saboteur!!! ", SwingConstants.CENTER);
        //lbl1.setText(" Welcome to Saboteur!!! ");
        firstPanel.add(lbl1);
        //firstPanel.add(TextField1);
        //firstPanel.add(TextField2);

        JComboBox comboBox1 = new JComboBox(new Object[]{3,4,5,6,7,8,9,10});
        // Object[] tmp = new Object[];
        // JComboBox comboBox2 = new JComboBox(tmp);
        JLabel lbl2 = new JLabel();
        lbl2.setText("  Number of Player:");
        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(new GridLayout(0, 3, 10, 10));
        secondPanel.add(lbl2);
        secondPanel.add(comboBox1);

        //secondPanel.add(comboBox2);
        try{
            bgimage = ImageIO.read(new File(System.getProperty("user.dir")+"/img/saboteur2.jpg"));
        }catch(IOException e){
            e.printStackTrace();
        }       

        JFrame frame = new JFrame();
        frame.setTitle("SwingResizeJFrame test");
        frame.setLayout(new GridLayout(2, 1, 10, 10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new BGImagePanel(bgimage));
        frame.add(firstPanel);
        frame.add(secondPanel);
        frame.pack();
        frame.setLocation(200, 200);
        
        FlowLayout layout = new FlowLayout();
        frame.setLayout(layout);
       
        Dimension dim = new Dimension(720,512);      
        frame.setMinimumSize(dim);
        frame.setMaximumSize(dim);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SwingResizeJFrame demo = new SwingResizeJFrame();
            }
        });
    }
}

class BGImagePanel extends JComponent {
    private Image image;
    public BGImagePanel(Image image) {
        this.image = image;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image,0,0,720,512,null);     
    }
}