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
	{{"Road_LeftTopBlock", "/img/game/Road_LeftTopBlock.png", "false"},
	{"Road_RightTopBlock", "/img/game/Road_RightTopBlock.png", "false"},
	{"Road_LongIBlock", "/img/game/Road_LongIBlock.png", "false"},
	{"Road_ShortDeadEnd", "/img/game/Road_ShortDeadEnd.png", "false"},
	{"Road_LongDeadEnd", "/img/game/Road_LongDeadEnd.png", "false"},
	{"Road_LongTBlock", "/img/game/Road_LongTBlock.png", "false"},
	{"Road_FullBlock", "/img/game/Road_FullBlock.png", "false"},
	{"Road_ShortIBlock", "/img/game/Road_ShortIBlock.png", "false"},
	{"Road_ShortTBlock", "/img/game/Road_ShortTBlock.png", "false"},
	{"Road_ShortI", "/img/game/Road_ShortI.png", "false"},
	{"Road_ShortT", "/img/game/Road_ShortT.png", "false"},
	{"Road_LeftTop", "/img/game/Road_LeftTop.png", "false"},
	{"Road_RightTop", "/img/game/Road_RightTop.png", "false"},
	{"Road_LongI", "/img/game/Road_LongI.png", "false"},
	{"Road_LongT", "/img/game/Road_LongT.png", "false"},
	{"Road_Intersection", "/img/game/Road_Intersection.png", "false"},
	{"Func_Break_cart","/img/game/Func_Break_cart.png", "true"},
	{"Func_Break_lamp","/img/game/Func_Break_lamp.png", "true"},
	{"Func_Break_pick","/img/game/Func_Break_pick.png", "true"},
	{"Func_Fix_cart","/img/game/Func_Fix_cart.png", "true"},
	{"Func_Fix_lamp","/img/game/Func_Fix_lamp.png", "true"},
	{"Func_Fix_pick","/img/game/Func_Fix_pick.png", "true"},
	{"Func_Fix_cart&pick","/img/game/Func_Fix_cart&pick.png", "true"},
	{"Func_Fix_lamp&cart","/img/game/Func_Fix_lamp&cart.png", "true"},
	{"Func_Fix_pick&lamp","/img/game/Func_Fix_pick&lamp.png", "true"},
	{"Func_Collapse","/img/game/Func_Collapse.png", "true"},
	{"Func_Map","/img/game/Func_Map.png", "true"}};
	public static final int numberOfCards[] = {1, 1, 1, 1, 1, 1, 2, 1, 1, 5, 5, 5, 5, 5, 5, 5, 
	3, 3, 3, 3, 3, 3, 1, 1, 1, 3, 3};

	public Shuffler() {
		deck = new CardGUI[72];
		int count = 0;
		for (int i = 0; i < 27; i++) {
			for (int j = 0; j < numberOfCards[i]; j++) {
				if (i <= 15)
					deck[count] = new CardGUI("file:"+System.getProperty("user.dir")+"/src/sample"+cardInfo[i][1], false);
				else
					deck[count] = new CardGUI("file:"+System.getProperty("user.dir")+"/src/sample"+cardInfo[i][1], true);
				count++;
			}
		}
		count = 0;
	}

	public void shuffle() {
		Random randomGenerator = new Random();
		for (int i = 71; i > 0; i--)
		{
			int r = randomGenerator.nextInt(i);
			CardGUI temp = deck[i];
			deck[i] = deck[r];
			deck[r] = temp;
		}
		count = 0;
	}
	public CardGUI takeCard() {
		if (count > 71)
			return null;
		CardGUI temp = new CardGUI(deck[count].getImage(), deck[count].isFunc());
		count++;
		return temp;
	}
}