import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.io.*;
import java.net.*;

import javax.swing.border.Border;
import javax.swing.text.*;
import javax.swing.text.html.*;

public class JPanelTutorial extends JPanel{
	static BufferedImage image1, image2, bgimage;
	//static ImageLabel bg, il1;
	static int card_width = 65;
	static int card_length = 100;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Dimension dim = new Dimension((707*14)/10,(512*14)/10 +22);
				JFrame frame = new JFrame();
				frame.setTitle("GUI test");
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.add(new JPanelTutorial());
				frame.setSize((707*14)/10 , (512*14)/10);
				//frame.setPreferredSize(dim);
				frame.setMinimumSize(dim);
				frame.setMaximumSize(dim);
				frame.setResizable(false);
				//frame.pack();
				//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				
				frame.setContentPane(new BGImagePanel(bgimage));
				
				JLabel thumb = new JLabel();
				ImageIcon icon = new ImageIcon(image1);
				thumb.setIcon(icon);
				thumb.setSize(card_width, card_length); 
				thumb.addMouseListener(new mousel());
				
				// frame.add(background);
				 frame.add(thumb);
				//ImageLabel imageLabel1 = new ImageLabel(System.getProperty("user.dir")+"/img/intersection.jpg");
				//frame.add(imageLabel1);
				
				frame.setVisible(true);
			}
		});
	}

	public JPanelTutorial(){
		try{
			//bg = new ImageLabel(System.getProperty("user.dir")+"/img/Sab-Map.jpg");
			bgimage = ImageIO.read(new File(System.getProperty("user.dir")+"/img/Sab-Map.jpg"));
			//il1 = new ImageLabel(System.getProperty("user.dir")+"/img/intersection.jpg");
			image1 = ImageIO.read(new File(System.getProperty("user.dir")+"/img/intersection.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g){
		//super.paintComponents(g);
		g.drawImage(bgimage,0,0,(707*14)/10,(512*14)/10,null);		
		// g.drawImage(image1,70,196,card_width,card_length,null);
		// g.drawImage(image1,70+75*1,196,card_width,card_length,null);

		// g.setColor(Color.RED);
		// g.fillRect(10, 10, 100, 50);
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
		g.drawImage(image,0,0,(707*14)/10,(512*14)/10,null);		
    }
}


class mousel implements MouseListener {

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
		e.getComponent().setVisible(false);
	}

}