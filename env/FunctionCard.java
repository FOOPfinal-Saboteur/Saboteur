import java.lang.*;
import java.util.*;
import java.io.*;

public class FunctionCard{

	/* Member variable */
		/* 0:pick, 1:oil_lamp, 2:mine_cart
		   3:0+1, 4:1+2, 5:2+0 */
	private boolean break_card = false; // type0  
	private boolean fix_card = false; // type1
	private int kind = -1;

	private boolean map_card = false; // type2
	private boolean collapse_card = false; // type3

	/* Constructor */
	public FunctionCard(int type, int item){
		switch(type){
			case 0: 
				break_card = true;
				kind = item;
				break;
			case 1: 
				fix_card = true;
				kind = item;
				break;
			case 2: 
				map_card = true;
				break;
			case 3: 
				collapse_card = true;
				break;
			default: 
				System.out.println("[ERROR] FunctionCard new error: No such kind");
				break;
		}
	}
	public FunctionCard(FunctionCard old){ 
			kind = old.itemKind();
			if(old.isBreak())break_card = true;
			else if(old.isFix())fix_card = true;
			else if(old.isMap())map_card = true;
			else if(old.isCollapse())collapse_card = true;
			else{
				System.out.println("[ERROR] FunctionCard new error: No such kind");
			}
		
	}

	public FunctionCard(String type, String item){
		if(type.equals("break")){
			break_card = true;
			if(item.equals("pick")){
				kind = 0;
			}else if(item.equals("oil_lamp")){
				kind = 1;
			}else if(item.equals("mine_cart")){
				kind = 2;
			}
		}else if(type.equals("fix")){
			fix_card = true;
			if(item.equals("pick")){
				kind = 0;
			}else if(item.equals("oil_lamp")){
				kind = 1;
			}else if(item.equals("mine_cart")){
				kind = 2;
			}else if(item.equals("pick & oil_lamp")){
				kind = 3;
			}else if(item.equals("oil_lamp & mine_cart")){
				kind = 4;
			}else if(item.equals("mine_cart & pick")){
				kind = 5;
			}
		}else if(type.equals("map")){
			map_card = true;
		}else if(type.equals("collapse")){
			collapse_card = true;
		}else {
			System.out.println("[ERROR] FunctionCard new error: No such kind");
		}
	}

	/* Accessor */
	public boolean isBreak(){ return break_card; }
	public boolean isFix(){ return fix_card; }
	public boolean isMap(){ return map_card; }
	public boolean isCollapse(){ return collapse_card; }
		/* For break & fix */
	public int itemKind(){ return kind;}
	public String kindStr(){
		String toReturn = new String();
		switch kind{
			case 0:
				toReturn = new String("pick");
				break;
			case 1:
				toReturn = new String("oil_lamp");
				break;
			case 2:
				toReturn = new String("mine_cart");
				break;
			case 3:
				toReturn = new String("pick & oil_lamp");
				break;
			case 4:
				toReturn = new String("oil_lamp & mine_cart");
				break;
			case 5:
				toReturn = new String("mine_cart & pick");
				break;
		}
		return new String(toReturn);
	}

	/* Method */ 

	//to String
	public String toString(){
		String toReturn = new String();
		if(map_card)
			toReturn = toReturn.concat("map");
		if(collapse_card)
			toReturn = toReturn.concat("collasp");
		if(map_card || collapse_card)
			return toReturn;
		if(break_card)
			toReturn = toReturn.concat("break:");
		if(fix_card)
			toReturn = toReturn.concat("fix:");
		switch(kind){
			case 0:
				toReturn = toReturn.concat("pick");
				break;
			case 1:
				toReturn = toReturn.concat("oil_lamp");
				break;
			case 2:
				toReturn = toReturn.concat("mine_cart");
				break;
			case 3:
				toReturn = toReturn.concat("pick & oil_lamp");
				break;
			case 4:
				toReturn = toReturn.concat("oil_lamp & mine_cart");
				break;
			case 5:
				toReturn = toReturn.concat("mine_cart & pick");
				break;
			default:	
				toReturn = new String("ERROR");
		}
		return toReturn;
	}
}
