import java.lang.*;
import java.util.*;
import java.io.*;

public class ActiveStatus{
	/* Three status */
	private boolean pick; // 十字鎬
	private boolean oil_lamp; // 油燈
	private boolean mine_cart; // 礦車

	/* Constructor */
	public ActiveStatus(){
		pick = true;
		oil_lamp = true;
		mine_cart = true;
	}

	/* Accessor */
	public boolean pickOK(){ return pick; }
	public boolean oil_lampOK(){ return oil_lamp; }
	public boolean mine_cartOK(){ return mine_cart; }
	public boolean isActive(){
		return pick && oil_lamp && mine_cart;
	}
	/* Mutator */
	public void destroy(String cmd){
		if(cmd == "pick"){
			pick = false;
		}else if(cmd == "oil_lamp"){
			oil_lamp = false;
		}else if(cmd == "mine_cart"){
			mine_cart = false;
		}
	}

	public void fix(String cmd){
		if(cmd == "pick"){
			pick = true;
		}else if(cmd == "oil_lamp"){
			oil_lamp = true;
		}else if(cmd == "mine_cart"){
			mine_cart = true;
		}
	}
	public String toString(){
		String toReturn = new String("pick:");
		toReturn = toReturn.concat(pick + "\n");
		toReturn = toReturn.concat("oil_lamp:");
		toReturn = toReturn.concat(oil_lamp + "\n");
		toReturn = toReturn.concat("mine_cart:");
		toReturn = toReturn.concat(mine_cart + "\n");
		return new String(toReturn);
	}

}
