import java.lang.*;
import java.util.*;
import java.io.*;

public class Card{
	/* Member variable */
	protected boolean is_function;
	protected boolean is_road;
	protected boolean is_rotate;
	private String my_item;
	private String my_type;
	/* Constructor */
	public Card(){
		is_function = false;
		is_road = false;
		is_rotate = false;
	}
	public Card(String type, String item){
		if(type.equals("fix")||
		   type.equals("break")||
		   type.equals("map")||
		   type.equals("collapse")
		){
				is_road = false;
				is_function = true;
				is_rotate = false;
				my_item = new String(item);
				my_type = new String(type);
		}
		else
			System.out.println("[ERROR] WTF do you want?? You bitch");
		
	}
	public Card(String cmd){
		if(cmd.equals("intersection")||
		   cmd.equals("longT")||
		   cmd.equals("shortT")||
		   cmd.equals("longI")||
		   cmd.equals("shortI")||
		   cmd.equals("LeftTop")||
		   cmd.equals("RightTop")||
		   cmd.equals("fullblock")||
		   cmd.equals("longTblock")||
		   cmd.equals("shortTblock")||
		   cmd.equals("LeftTopblock")||
		   cmd.equals("RightTopblock")||
		   cmd.equals("longIblock")||
		   cmd.equals("shortIblock")||
		   cmd.equals("longDeadEnd")||
		   cmd.equals("shortDeadEnd")
		   ){
			is_road = true;
			is_function = false;
			is_rotate = false;
			my_type = new String(cmd);
		}
		else
			System.out.println("[ERROR] WTF do you want?? You bastard");
	
	}
	public Card(String cmd, boolean rotate){
		if(cmd.equals("intersection")||
		   cmd.equals("longT")||
		   cmd.equals("shortT")||
		   cmd.equals("longI")||
		   cmd.equals("shortI")||
		   cmd.equals("LeftTop")||
		   cmd.equals("RightTop")||
		   cmd.equals("fullblock")||
		   cmd.equals("longTblock")||
		   cmd.equals("shortTblock")||
		   cmd.equals("LeftTopblock")||
		   cmd.equals("RightTopblock")||
		   cmd.equals("longIblock")||
		   cmd.equals("shortIblock")||
		   cmd.equals("longDeadEnd")||
		   cmd.equals("shortDeadEnd")
		   ){
			is_road = true;
			is_function = false;
			is_rotate = rotate;
			my_type = new String(cmd);
		}
		else
			System.out.println("[ERROR] WTF do you want?? You bastard");
	
	}
	public Card(Card card){
		if(card.IsFunction()){
			is_road = false;
			is_function = true;
			is_rotate = false;
			my_type = card.getType();
			my_item = card.getItem();
		}
		if(card.IsRoad()){
			is_road =true;
			is_function = false;
			is_rotate = card.IsRotate();
			my_type = card.getType();
		}
//		System.out.println(my_type +"  How\n");
	}
	/* Accessor */
	public FunctionCard Function(){
		if(!is_function){
			System.out.println("[ERROR] This is not a function card!! You asshole!!");
			return null;
		}
		FunctionCard toReturn = new FunctionCard(my_type,my_item);
		return toReturn;	
	} 
	public RoadCard Road(){
		if(!is_road){
			System.out.println("[ERROR] This is not a road card!! You whore!!");
			return null;
		}
		RoadCard toReturn = new RoadCard(my_type,is_rotate);
		return toReturn;
	
	}
	public boolean rotateCard(){
		if(is_function)
			return false;
		is_rotate = !is_rotate;
		return true;
	}
	public String getItem(){return new String(my_item);}
	public String getType(){return new String(my_type);}
	public boolean IsFunction(){return is_function;}
	public boolean IsRoad(){return is_road;}
	public boolean IsRotate(){return is_rotate;};
	/* Method */ 
	public String toString(){
		if(is_function)
			return new String(new FunctionCard(my_type,my_item).toString());
		else if(is_road)
			return new String(new RoadCard(my_type,is_rotate).toString());
		else
			return new  String("Empty");
	}
}
