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
	{{"Road_LeftTopBlock", System.getProperty("user.dir")+"/src/sample/img/game/Road_LeftTopBlock.png"},
	{"Road_RightTopBlock", System.getProperty("user.dir")+"/src/sample/img/game/Road_RightTopBlock.png"},
	{"Road_LongIBlock", System.getProperty("user.dir")+"/src/sample/img/game/Road_LongIBlock.png"},
	{"Road_ShortDeadEnd", System.getProperty("user.dir")+"/src/sample/img/game/Road_ShortDeadEnd.png"},
	{"Road_LongDeadEnd", System.getProperty("user.dir")+"/src/sample/img/game/Road_LongDeadEnd.png"},
	{"Road_LongTBlock", System.getProperty("user.dir")+"/src/sample/img/game/Road_LongTBlock.png"},
	{"Road_FullBlock", System.getProperty("user.dir")+"/src/sample/img/game/Road_FullBlock.png"},
	{"Road_ShortIBlock", System.getProperty("user.dir")+"/src/sample/img/game/Road_ShortIBlock.png"},
	{"Road_ShortTBlock", System.getProperty("user.dir")+"/src/sample/img/game/Road_ShortTBlock.png"},
	{"Road_ShortI", System.getProperty("user.dir")+"/src/sample/img/game/Road_ShortI.png"},
	{"Road_ShortT", System.getProperty("user.dir")+"/src/sample/img/game/Road_ShortT.png"},
	{"Road_LeftTop", System.getProperty("user.dir")+"/src/sample/img/game/Road_LeftTop.png"},
	{"Road_RightTop", System.getProperty("user.dir")+"/src/sample/img/game/Road_RightTop.png"},
	{"Road_LongI", System.getProperty("user.dir")+"/src/sample/img/game/Road_LongI.png"},
	{"Road_LongT", System.getProperty("user.dir")+"/src/sample/img/game/Road_LongT.png"},
	{"Road_Intersection", System.getProperty("user.dir")+"/src/sample/img/game/Road_Intersection.png"},
	{"Func_Break_cart",System.getProperty("user.dir")+"/src/sample/img/game/Func_Break_cart.png"},
	{"Func_Break_lamp",System.getProperty("user.dir")+"/src/sample/img/game/Func_Break_lamp.png"},
	{"Func_Break_pick",System.getProperty("user.dir")+"/src/sample/img/game/Func_Break_pick.png"},
	{"Func_Fix_cart",System.getProperty("user.dir")+"/src/sample/img/game/Func_Fix_cart.png"},
	{"Func_Fix_lamp",System.getProperty("user.dir")+"/src/sample/img/game/Func_Fix_lamp.png"},
	{"Func_Fix_pick",System.getProperty("user.dir")+"/src/sample/img/game/Func_Fix_pick.png"},
	{"Func_Fix_cart&pick",System.getProperty("user.dir")+"/src/sample/img/game/Func_Fix_cart&pick.png"},
	{"Func_Fix_lamp&cart",System.getProperty("user.dir")+"/src/sample/img/game/Func_Fix_lamp&cart.png"},
	{"Func_Fix_pick&lamp",System.getProperty("user.dir")+"/src/sample/img/game/Func_Fix_pick&lamp.png"},
	{"Func_Collapse",System.getProperty("user.dir")+"/src/sample/img/game/Func_Collapse.png"},
	{"Func_Map",System.getProperty("user.dir")+"/src/sample/img/game/Func_Map.png"}};
	public static final int numberOfCards[] = {1, 1, 1, 1, 1, 1, 2, 1, 1, 5, 5, 5, 5, 5, 5, 5, 
	3, 3, 3, 3, 3, 3, 1, 1, 1, 3, 3};
	private MapGUI mapGUI;

	public Shuffler(MapGUI map) {
		deck = new CardGUI[72];
		int count = 0;
		for (int i = 0; i < 27; i++) {
			for (int j = 0; j < numberOfCards[i]; j++) {
				if (i <= 15)
					deck[count] = new CardGUI("file:"+cardInfo[i][1], map, false);
				else
					deck[count] = new CardGUI("file:"+cardInfo[i][1], map, true);
				count++;
			}
		}
		mapGUI = map;
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
			shuffle();
		CardGUI temp = new CardGUI(deck[count].getImage(), mapGUI, deck[count].isFunc());
		count++;
		return temp;
	}
}