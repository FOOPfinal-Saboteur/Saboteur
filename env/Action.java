import java.lang.*;
import java.util.*;
import java.io.*;
class ToWhere{
	int x;
	int y;
	ToWhere(int _x,_y){
		x = _x;
		y = _y;
	}
}
public class Action{
	private boolean isOnMap;
	private boolean isFunction;
	private RoadCard road;
	private FunctionCard func;
	private ToWhere toWhere;
	private int toWhom;
	//Constructor
	public Action(){
		isOnMap = false;
		isFunction = false;
		road = null;
		func = null;
		toWhere = new ToWhere(-1,-1);
		toWhom = -1;
	}
		// all card need to send these num
	public Action(Card card, int _x, int _y, int whom){
		if(card.isFunction()){
			func = card.FunctionCard();
			road = null;
			isFunction = true;
			isOnMap = (func.isCollaspe) ? true:false;
		}
		if(card.isRoad()){
			isOnMap = true;
			road = card.RoadCard();
			func = null;
			isFunction = false;
		}
		toWhere = new ToWhere(_x,_y);
		toWhome = whom;
	}
	//Accessor
	public boolean getIsOnMap(){return isOnMap;}
	public boolean getIsFunction(){return isFunction;}
	public RoadCard getRoadCard(){return new RoadCard(road);}
	public FuctionCard getFunctionCard(){return new FunctionCard(func);}

}
