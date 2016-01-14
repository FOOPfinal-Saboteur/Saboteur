import java.lang.*;
import java.util.*;
import java.io.*;

public class Card{
	/* Member variable */
	protected boolean is_function;
	protected boolean is_road;
	private String my_item;
	private String my_type;
	/* Constructor */
	public Card(){
		is_function = false;
		is_road = false;
	}
	public Card(String type, String item){
		switch (type){
			case "fix":
			case "break":
			case "map":
			case "collapse":
				is_road = false;
				is_function = true;
				my_item = new String(item);
				my_type = new String(type);
				break;
			default:
				System.out.println("[ERROR] WTF do you want?? You bitch");
		}
	}
	public Card(String cmd){
		switch (cmd){
			case "intersection":
			case "longT":
			case "shortT":
			case "longI":
			case "LeftTop":
			case "RightTop":
			case "fullblock":
			case "longTblock":
			case "shortTblock":
			case "LeftTopblock":
			case "RightTopblock":
			case "longDeadEnd":
			case "shortDeadEnd":
				is_road = true;
				is_function = false;
				my_type = new String(cmd);
				break;
			default:
				System.out.println("[ERROR] WTF do you want?? You bastard");
		}	
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
		RoadCard toReturn = new RoadCard(my_type);
		return toReturn;
	
	}
	public boolean IsFunction(){return is_function;}
	public boolean IsRoad(){return is_road;}
	/* Method */ 
	public String toString(){
		if(is_function)
			return new String("Function_Card");
		else if(is_road)
			return new String("Road_Card");
		else
			return new  String("Empty");
	}
}
