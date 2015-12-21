import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JPanelTutorial extends JPanel{
	BufferedImage image;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.add(new JPanelTutorial());
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.setVisible(true);
			}
		});
	}

	public JPanelTutorial(){
		try {
			image = ImageIO.read(new File("/Users/leinadshih/Desktop/BurnTheWay.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(image, 0,0,null);
		g.setColor(Color.RED);
		g.fillRect(10, 10, 100, 50);
	}
}