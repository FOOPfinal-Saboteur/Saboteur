import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.*;
import java.net.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.*;
import javax.swing.text.html.*;

public class DraftGUI implements MouseListener {

  private JPanel jpPack;
  private JPanel jpCards;
  private JPanel jpInfo;
  private JPanel jpChat;
  private TextField tf;
  private StyledDocument doc;
  private JTextPane tp;
  private JScrollPane sp;

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

  //private Client client;

  public void GUI() throws IOException {

    JFrame frame = new JFrame("Draft");

    //set the size to fullscreen to start
    frame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width-100, Toolkit.getDefaultToolkit().getScreenSize().height-100);

    //set the content pane, we'll add everything to it and then add it to the frame
    JPanel contentPane = new JPanel();
    contentPane.setSize(Toolkit.getDefaultToolkit().getScreenSize().width-100, Toolkit.getDefaultToolkit().getScreenSize().height-100);
    contentPane.setLayout(new GridBagLayout());

    //creates some panels with some default values for now
    JPanel jpCards = new JPanel(new BorderLayout());
    jpCards.setOpaque(true); //ensures it paints every pixel
    jpCards.setBackground(Color.GRAY);

    JPanel jpInfo = new JPanel();
    jpInfo.setOpaque(true);
    jpInfo.setBackground(Color.LIGHT_GRAY);

    JPanel jpPack = new JPanel(new GridBagLayout());
    jpPack.setOpaque(true);
    jpPack.setBackground(Color.DARK_GRAY);

    //grab some info to make the JTextPane and make it scroll
    //this.client = client;
    tf = new TextField();
    doc = new DefaultStyledDocument();
    tp = new JTextPane(doc);
    tp.setEditable(false);
    //tf.addActionListener(this.client);
    sp = new JScrollPane(tp);

    //adding the quantities to the chat panel
    JPanel jpChat = new JPanel();
    jpChat.setLayout(new BorderLayout());
    jpChat.add("North", tf);
    jpChat.add("Center", sp);

    //a new GridBagConstraints used to set the properties/location of the panels
    GridBagConstraints c = new GridBagConstraints(); 

    //adding some panels to the content pane
    //set it to start from the top left of the quadrant if it's too small
    c.anchor = GridBagConstraints.FIRST_LINE_START; 
    c.fill = GridBagConstraints.BOTH; //set it to fill both vertically and horizontally
    c.gridx = 0; //set it to quadrant x=0 and
    c.gridy = 0; //set it to quadrant y=0
    c.weightx = 0.5;
    c.weighty = 0.5;
    contentPane.add(jpCards, c);

    c.gridx = 1;
    c.gridy = 0;
    c.weightx = 0.5;
    c.weighty = 0.5;
    contentPane.add(jpInfo, c);

    c.gridx = 0;
    c.gridy = 1;
    c.weightx = 0.5;
    c.weighty = 0.5;
    contentPane.add(jpPack, c);

    c.gridx = 1;
    c.gridy = 1;
    c.weightx = 0.5;
    c.weighty = 0.5;
    contentPane.add(jpChat, c);

    //set some necessary values 
    frame.setContentPane(contentPane);
    frame.setLocationByPlatform(true);
    frame.setVisible(true);

    //This code works for adding an Image
    //need to learn how to specify a path not dependent on the specific users's machine
    //this is not a high priority for now though
    GridBagConstraints d = new GridBagConstraints();
    d.gridx = 0;
    d.gridy = 0;

    ImageLabel imageLabel1 = new ImageLabel(System.getProperty("user.dir")+"/img/Sab-Map.jpg");

    imageLabel1.setPreferredSize(new Dimension(707, 512));
    jpPack.add(imageLabel1, d);

    ImageLabel imageLabel2 = new ImageLabel("path-to-file");
    imageLabel2.setPreferredSize(new Dimension(223, 310));
    ImageLabel imageLabel3 = new ImageLabel("path-to-file");
    imageLabel3.setPreferredSize(new Dimension(223, 310));
    d.gridx = 1;
    jpPack.add(imageLabel2, d);
    d.gridy = 1;
    jpPack.add(imageLabel3, d);

    imageLabel1.addMouseListener(this);
    imageLabel2.addMouseListener(this);
    imageLabel3.addMouseListener(this);

    //223, 310 are the aspect values for a card image, width, height
    //these need to be maintained as the GUI size changes

  }

  public static void main(String[] arg) throws Exception{
    DraftGUI tmp = new DraftGUI();
    tmp.GUI();
  }
}


class ImageLabel extends JLabel {
  Image image;
  ImageObserver imageObserver; 

  // constructor with filename     
  ImageLabel(String filename) {
    ImageIcon icon = new ImageIcon(filename);
    image = icon.getImage();
    imageObserver = icon.getImageObserver();
  }

  // constructor with icon
  ImageLabel(ImageIcon icon) {
    image = icon.getImage();
    imageObserver = icon.getImageObserver();
  }

  // overload setIcon method
  void setIcon(ImageIcon icon) {
    image = icon.getImage();
    imageObserver = icon.getImageObserver();
  }

  // overload paint()
  public void paint( Graphics g ) {
    super.paint( g );
    g.drawImage(image,  0 , 0 , getWidth() , getHeight() , imageObserver);
  }
}
