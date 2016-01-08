import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JPanelTutorial extends JPanel{
	BufferedImage image1, image2;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame();
				frame.setTitle("GUI test");
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.add(new JPanelTutorial());
				frame.setSize(960,720);
				//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.setVisible(true);
			}
		});
	}

	public JPanelTutorial(){
		try {
			image1 = ImageIO.read(new File(System.getProperty("user.dir")+"/img/fix_pick.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(image1,200,200,117,168,null);
		// g.setColor(Color.RED);
		// g.fillRect(10, 10, 100, 50);
	}
}