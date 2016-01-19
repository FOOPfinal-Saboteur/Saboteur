import java.lang.*;
import java.util.*;
import java.io.*;
/*
class ActiveStatus{
	// Three status 
	private boolean pick; // 十字鎬
	private boolean oil_lamp; // 油燈
	private boolean mine_cart; // 礦車

	// Constructor 
	public ActiveStatus(){
		pick = true;
		oil_lamp = true;
		mine_cart = true;
	}

	// Accessor
	public boolean pickOK(){ return pick; }
	public boolean oil_lampOK(){ return oil_lamp; }
	public boolean mine_cartOK(){ return mine_cart; }
	public boolean isActive(){
		return pick && oil_lamp && mine_cart;
	}
	// Mutator
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

	// Method 
}
*/

public class Player{

	/* Member variable */
	protected ArrayList<Card> hand;
	protected int card_num;
	protected String name;
	protected int role; // 0:Saboteur, 1:Miner  
	protected ActiveStatus status = new ActiveStatus();
	protected boolean isAI;

	/* Constructor */
	public Player(String _name, int _role, int _num){
		hand = new ArrayList<Card>();
		name = new String(_name);
		role = _role;
		card_num = _num;
		isAI = false;
	}
	public Player(String _name, String _role, int _num){
		hand = new ArrayList<Card>();
		name = new String(_name);
		if(_role == "Saboteur"){
			role = 0;
		}else if(_role == "Miner"){
			role = 1;
		}
		card_num = _num;
		isAI = false;
	}

	/* Accessor */
	public Card getCard(int index){
		if(index < 0 || index >= card_num){
			System.out.println("[ERROR] Get card error: index out of bound");
			return null; // exception
		}else{
			Card toReturn = new Card(hand.get(index));
	//		System.out.println("what i get\n" + toReturn);
			return toReturn;
		}
	}
	public String showHand(){
		String toReturn = new String();
		int i = 0;
		for(Card card:hand){
			toReturn = toReturn.concat(i +"\n" + card.toString() + "\n");
			i ++;
		}
//		System.out.println(toReturn);
		return toReturn;
		
	}
	public String showStatus(){
		String toReturn = status.toString();
		toReturn += showHand();
//		System.out.println(toReturn);
		return toReturn;		
	}
	public boolean isAI(){return isAI;}
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
	public boolean watchMap(DestinyStatus destiny){
		return (destiny.isGold);
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
//			System.out.println(_card);
			Card put_in = new Card(_card);
//			System.out.println(put_in.toString());
			hand.add(put_in);
			return true;
		}
	}
	public boolean rotateCard(int num){
		if(num >= hand.size() || num < 0){
			System.out.println("[Error] No such card");
			return false;
		}
		//experiment
		if(hand.get(num).IsFunction()){
			System.out.println("Don't rotate function card");
			return false;
		}
		return hand.get(num).rotateCard();
	}
		/* Active Status */
	public boolean destroy(String cmd){
		if(cmd == "pick"){
			if(status.pickOK()){
				status.destroy(cmd);
				return true;
			}
			return false;
		}else if(cmd == "oil_lamp"){
			if(status.oil_lampOK()){
				status.destroy(cmd);
				return true;
			}
			return false;
		}else if(cmd == "mine_cart"){
			if(status.mine_cartOK()){
				status.destroy(cmd);
				return true;
			}
			return false;
		}
		System.out.println("[ERROR] destroy activeness error: command undefined");
		return false;
	}

	public boolean fix(String cmd){
		if(cmd == "pick"){
			if(!status.pickOK()){
				status.fix(cmd);
				return true;
			}
			return false;
		}else if(cmd == "oil_lamp"){
			if(!status.oil_lampOK()){
				status.fix(cmd);
				return true;
			}
			return false;
		}else if(cmd == "mine_cart"){
			if(!status.mine_cartOK()){
				status.fix(cmd);
				return true;
			}
			return false;
		}
		System.out.println("[ERROR] Fix activeness error: command undefined");
		return false;
	}
	/* Method */
	public String toString(){
		String _role = new String();
		_role += (role == 0)? "Saboteur":"Miner";
//		System.out.println(showStatus());
		return new String("name:" + name +"\n" +"role:" + _role +"\n"+ showStatus());
	} 
	
}
