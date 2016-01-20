package sample;
import java.lang.*;
import java.io.*;
import java.util.*;

//image
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Shuffler {
	private CardGUI[] deck;
	private int count;
	//{CardName, CardURL}
	public static final String cardInfo[][] = 
	{{"Road_LeftTopBlock", "file:"+System.getProperty("user.dir")+"/src/sample/img/game/Road_LeftTopBlock.png", "false"},
	{"Road_RightTopBlock", "file:"+System.getProperty("user.dir")+"/src/sample/img/game/Road_RightTopBlock.png", "false"},
	{"Road_LongIBlock", "file:"+System.getProperty("user.dir")+"/src/sample/img/game/Road_LongIBlock.png", "false"},
	{"Road_ShortDeadEnd", "file:"+System.getProperty("user.dir")+"/src/sample/img/game/Road_ShortDeadEnd.png", "false"},
	{"Road_LongDeadEnd", "file:"+System.getProperty("user.dir")+"/src/sample/img/game/Road_LongDeadEnd.png", "false"},
	{"Road_LongTBlock", "file:"+System.getProperty("user.dir")+"/src/sample/img/game/Road_LongTBlock.png", "false"},
	{"Road_FullBlock", "file:"+System.getProperty("user.dir")+"/src/sample/img/game/Road_FullBlock.png", "false"},
	{"Road_ShortIBlock", "file:"+System.getProperty("user.dir")+"/src/sample/img/game/Road_ShortIBlock.png", "false"},
	{"Road_ShortTBlock", "file:"+System.getProperty("user.dir")+"/src/sample/img/game/Road_ShortTBlock.png", "false"},
	{"Road_ShortI", "file:"+System.getProperty("user.dir")+"/src/sample/img/game/Road_ShortI.png", "false"},
	{"Road_ShortT", "file:"+System.getProperty("user.dir")+"/src/sample/img/game/Road_ShortT.png", "false"},
	{"Road_LeftTop", "file:"+System.getProperty("user.dir")+"/src/sample/img/game/Road_LeftTop.png", "false"},
	{"Road_RightTop", "file:"+System.getProperty("user.dir")+"/src/sample/img/game/Road_RightTop.png", "false"},
	{"Road_LongI", "file:"+System.getProperty("user.dir")+"/src/sample/img/game/Road_LongI.png", "false"},
	{"Road_LongT", "file:"+System.getProperty("user.dir")+"/src/sample/img/game/Road_LongT.png", "false"},
	{"Road_Intersection", "file:"+System.getProperty("user.dir")+"/src/sample/img/game/Road_Intersection.png", "false"},
	{"Func_Break_cart","file:"+System.getProperty("user.dir")+"/src/sample/img/game/Func_Break_cart.png", "true"},
	{"Func_Break_lamp","file:"+System.getProperty("user.dir")+"/src/sample/img/game/Func_Break_lamp.png", "true"},
	{"Func_Break_pick","file:"+System.getProperty("user.dir")+"/src/sample/img/game/Func_Break_pick.png", "true"},
	{"Func_Fix_cart","file:"+System.getProperty("user.dir")+"/src/sample/img/game/Func_Fix_cart.png", "true"},
	{"Func_Fix_lamp","file:"+System.getProperty("user.dir")+"/src/sample/img/game/Func_Fix_lamp.png", "true"},
	{"Func_Fix_pick","file:"+System.getProperty("user.dir")+"/src/sample/img/game/Func_Fix_pick.png", "true"},
	{"Func_Fix_cart&pick","file:"+System.getProperty("user.dir")+"/src/sample/img/game/Func_Fix_cart&pick.png", "true"},
	{"Func_Fix_lamp&cart","file:"+System.getProperty("user.dir")+"/src/sample/img/game/Func_Fix_lamp&cart.png", "true"},
	{"Func_Fix_pick&lamp","file:"+System.getProperty("user.dir")+"/src/sample/img/game/Func_Fix_pick&lamp.png", "true"},
	{"Func_Collapse","file:"+System.getProperty("user.dir")+"/src/sample/img/game/Func_Collapse.png", "true"},
	{"Func_Map","file:"+System.getProperty("user.dir")+"/src/sample/img/game/Func_Map.png", "true"}};
	public static final int numberOfCards[] = {1, 1, 1, 1, 1, 1, 2, 1, 1, 5, 5, 5, 5, 5, 5, 5, 
	3, 3, 3, 3, 3, 3, 1, 1, 1, 3, 3};
	

	public Shuffler() {
		deck = new CardGUI[72];
		int counter = 0;
		for (int i = 0; i < 27; i++) {
			for (int j = 0; j < numberOfCards[i]; j++) {
				deck[counter] = new CardGUI(cardInfo[i][1], i);
				counter++;
			}
		}
		count = 0;
	}

	public void shuffle() {
		Random randomGenerator = new Random();
		for (int i = 72; i > 1; i--)
		{
			int r = randomGenerator.nextInt(i);
			CardGUI temp = deck[i - 1];
			deck[i - 1] = deck[r];
			deck[r] = temp;
		}
		count = 0;
	}
	public CardGUI takeCard() {
		if (count > 71)
			return null;
		CardGUI temp = new CardGUI(deck[count].getImage(), deck[count].getCardID());
		count++;
		return temp;
	}
}