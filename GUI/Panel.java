//import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Panel extends JPanel{
	BufferedImage image1;

	public Panel(){
		try {
			image1 = ImageIO.read(new File(System.getProperty("user.dir")+"/img/fix_pick.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paintComponet(Graphics g){
		super.paintComponents(g);
		g.drawImage(image,100,100,117,168,null);
	}
}