import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JPanelTutorial extends JPanel{
	BufferedImage image1, image2, bgimage;
	int card_width = 65;
	int card_length = 100;

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
				frame.setVisible(true);
			}
		});
	}

	public JPanelTutorial(){
		try {
			bgimage = ImageIO.read(new File(System.getProperty("user.dir")+"/img/Sab-Map.jpg"));
			image1 = ImageIO.read(new File(System.getProperty("user.dir")+"/img/intersection.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g){
		//super.paintComponents(g);
		g.drawImage(bgimage,0,0,(707*14)/10,(512*14)/10,null);		
		g.drawImage(image1,70,196,card_width,card_length,null);
		g.drawImage(image1,70+75*1,196,card_width,card_length,null);
		g.drawImage(image1,70+75*2,196,card_width,card_length,null);
		g.drawImage(image1,70+75*3,196,card_width,card_length,null);
		g.drawImage(image1,70+75*4,196,card_width,card_length,null);
		g.drawImage(image1,70+75*5,196,card_width,card_length,null);
		g.drawImage(image1,70+75*6,196,card_width,card_length,null);
		g.drawImage(image1,70+75*7,196,card_width,card_length,null);
		g.drawImage(image1,70+75*8,196,card_width,card_length,null);

		// g.setColor(Color.RED);
		// g.fillRect(10, 10, 100, 50);
	}
}