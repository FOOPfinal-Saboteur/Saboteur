//import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Panel extends JPanel{
	BufferedImage image;

	public Panel(){
		try{
			image = ImageIO.read(new File("/Users/leinadshih/Desktop/BurnTheWay.png"));
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public void paintComponet(Graphics g){
		super.paintComponents(g);
		g.drawImage(image, 0,0,null);
	}
}