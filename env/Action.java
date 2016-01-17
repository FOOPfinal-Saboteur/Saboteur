import java.lang.*;
import java.util.*;
import java.io.*;
public class Action{
	private boolean isOnMap;
	private boolean isFunction;
	private Card myCard;
	private RoadCard road;
	private FunctionCard func;
	private ToWhere toWhere;
	private int fromWho;
	private int toWhom;
	//Constructor
	public Action(){
		isOnMap = false;
		isFunction = false;
		myCard = null;
		road = null;
		func = null;
		toWhere = new ToWhere(-1,-1);
		toWhom = -1;
		fromWho = -1;
	}
		// all card need to send these num
	public Action(Card card, int _x, int _y, int from, int whom){
		myCard = new Card(card);
		if(card.IsFunction()){
			func = card.Function();
			road = null;
			isFunction = true;
			isOnMap = (func.isCollapse()) ? true:false;
		}
		if(card.IsRoad()){
			isOnMap = true;
			road = card.Road();
			func = null;
			isFunction = false;
		}
		toWhere = new ToWhere(_x,_y);
		fromWho = from;
		toWhom = whom;
	}
	//Accessor
	public boolean getIsOnMap(){return isOnMap;}
	public boolean getIsFunction(){return isFunction;}
	public int getFromWho(){return fromWho;}
	public int getToWhom(){return toWhom;}
	public ToWhere getToWhere{return toWhere;}
	public Card getCard(){return new Card(myCard);}
	public RoadCard getRoadCard(){return new RoadCard(road);}
	public FunctionCard getFunctionCard(){return new FunctionCard(func);}

}
