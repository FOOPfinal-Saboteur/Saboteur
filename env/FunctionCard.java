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

	private boolean map_card; // type2
	private boolean collapse_card; // type3

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

	public FunctionCard(String type, String item){
		if(type == "break"){
			break_card = true;
			if(item == "pick"){
				kind = 0;
			}else if(item == "oil_lamp"){
				kind = 1;
			}else if(item == "mine_cart"){
				kind = 2;
			}
		}else if(type == "fix"){
			fix_card = true;
			if(item == "pick"){
				kind = 0;
			}else if(item == "oil_lamp"){
				kind = 1;
			}else if(item == "mine_cart"){
				kind = 2;
			}else if(item == "pick & oil_lamp"){
				kind = 3;
			}else if(item == "oil_lamp & mine_cart"){
				kind = 4;
			}else if(item == "mine_cart & pick"){
				kind = 5;
			}
		}else if(type == "map"){
			map_card = true;
		}else if(type == "collapse"){
			collapse_card = true;
		}else {
			System.out.println("[ERROR] FunctionCard new error: No such kind");
		}
	}

	/* Accessor */
	public boolean isBreak(){ return break_card; }
	public boolean isFix(){ return fix_card; }
	public boolean isMap(){ return map_card; }
	public boolean isCollape(){ return collapse_card; }
		/* For break & fix */
	public int itemKind(){ return kind;}

	/* Method */ 
	
}