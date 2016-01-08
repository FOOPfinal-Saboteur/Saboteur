import java.lang.*;
import java.util.*;
import java.io.*;

class ActiveStatus{
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
	public void break(String cmd){
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

	/* Method */
}

public class Player{

	/* Member variable */
	private ArrayList<Card> hand;
	private int card_num;
	private String name;
	private int role; // 0:Saboteur, 1:Miner  
	private ActiveStatus status = new ActiveStatus();

	/* Constructor */
	public Player(String _name, int _role, int _num){
		name = _name;
		role = _role;
		card_num = _num;
	}
	public Player(String _name, String _role, int _num){
		name = _name;
		if(_role == "Saboteur"){
			role = 0;
		}else if(_role == "Miner"){
			role = 1;
		}
		card_num = _num;
	}

	/* Accessor */
	public Card getCard(int index){
		if(index < 0 || index >= card_num){
			System.out.println("[ERROR] Get card error: index out of bound");
			return null; // exception
		}else{
			return hand.get(index);
		}
	}

	/* Mutator */
		/* About Hand */
	public boolean setHand(ArrayList<Card> cards){
		if(cards.size() == card_num){
			hand = cards;
			return true;
		}else{
			System.out.println("[ERROR] Init hand error: size doesn't match");
			return false; // exception
		}
	}

	public boolean removeCard(int index){
		if(index < 0 || index >= card_num){
			System.out.println("[ERROR] Remove card error: index out of bound");
			return false; // exception
		}else{
			hand.remove(index);
			return true;
		}
	}

	public boolean assignCard(Card _card){
		if(hand.size() >= card_num){
			System.out.println("[ERROR] Assign card error: too much on hand");
			return false; // exception
		}else{
			hand.add(_card);
			return true;
		}
	}
		/* Active Status */
	public boolean break(String cmd){
		if(cmd == "pick"){
			if(status.pickOK()){
				status.break(cmd);
				return true;
			}
		}else if(cmd == "oil_lamp"){
			if(status.oil_lampOK()){
				status.break(cmd);
				return true;
			}
		}else if(cmd == "mine_cart"){
			if(status.mine_cartOK()){
				status.break(cmd);
				return true;
			}
		}else{
			System.out.println("[ERROR] Break activeness error: command undefined");
			return false;
		}
	}

	public boolean fix(String cmd){
		if(cmd == "pick"){
			if(!status.pickOK()){
				status.fix(cmd);
				return true;
			}
		}else if(cmd == "oil_lamp"){
			if(!status.oil_lampOK()){
				status.fix(cmd);
				return true;
			}
		}else if(cmd == "mine_cart"){
			if(!status.mine_cartOK()){
				status.fix(cmd);
				return true;
			}
		}else{
			System.out.println("[ERROR] Fix activeness error: command undefined");
			return false;
		}
	}

	/* Method */
	public String toString(){
		return name;
	} 
	
}