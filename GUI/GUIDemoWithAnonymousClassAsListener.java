import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUIDemoWithAnonymousClassAsListener{
    public static void main(String[] argv){
        JFrame frm = new MyFrame(640, 480);
    }
}

class MyFrame extends JFrame{
    public MyFrame(int width, int height){
	setSize(width, height);
	
	CountLabel lbl = new CountLabel();
	this.add(lbl);

	JButton btn = new IncreaseButton(lbl);
	this.add(btn);

	JButton btn2 = new DecreaseButton(lbl);
	this.add(btn2);

	FlowLayout layout = new FlowLayout();
	this.setLayout(layout);

	setVisible(true);
    }
}

class CountLabel extends JLabel{
    private int count;

    public CountLabel(){
	super("0");
    }

    private void setCount(int newcount){
	count = newcount;
	setText("" + count);
    }

    private void increaseCount(){
	setCount(count+1);
    }
    
    private void decreaseCount(){
	setCount(count-1);
    }

    public void setText(String s){
	try{
	    count = Integer.parseInt(s);
	}
	catch(NumberFormatException e){
	    count = 0;
	}
	super.setText("" + count);
    }

    public ActionListener getIncListener(){
	return new ActionListener(){
	    public void actionPerformed(ActionEvent e){
		(CountLabel.this).increaseCount();
	    }
	};
    }

    public ActionListener getDecListener(){
	return new ActionListener(){
	    public void actionPerformed(ActionEvent e){
		(CountLabel.this).decreaseCount();
	    }
	};
    }
}//class CountLabel


class IncreaseButton extends JButton{
    private CountLabel lbl;

    public IncreaseButton(CountLabel l){
	lbl = l;
	setText("increase");
	addActionListener(lbl.getIncListener());
    }
}

class DecreaseButton extends JButton{
    private CountLabel lbl;

    public DecreaseButton(CountLabel l){
	lbl = l;
	setText("decrease");
	addActionListener(lbl.getDecListener());
    }
}
