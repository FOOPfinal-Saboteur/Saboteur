package sample;
import java.lang.*;
import java.util.*;
import java.io.*;

public class Deck{
	public static String[] func = {
		"fix","break","map","collapse","pick",
		"oil_lamp","mine_cart","pick & oil_lamp","oil_lamp & mine_cart","mine_cart & pick",
		"whatever"};
	public static String[] road = {
		"intersection","longT","shortT","longI","shortI",
		"LeftTop","RightTop",  "fullblock","longTblock","shortTblock",
		"LeftTopblock","RightTopblock",	"longIblock","shortIblock","longDeadEnd",
		"shortDeadEnd"};
	public ArrayList<Card> theDeck;
	//constructor
	public Deck(){
		theDeck = new ArrayList<Card>();
		Card card;
		//collasp
		for(int i = 0; i < 3; i ++){
			card = new Card(func[3],func[10]);
			theDeck.add(card);
		}
		//map
		for(int i = 0; i < 3; i ++){
			card = new Card(func[2],func[10]);
			theDeck.add(card);
		}
		//break
		for(int i = 0; i < 3; i ++){	
			card = new Card(func[1],func[4]);
			theDeck.add(card);
			card = new Card(func[1],func[5]);
			theDeck.add(card);
			card = new Card(func[1],func[6]);
			theDeck.add(card);
		}
		//single fix
		for(int i = 0; i < 2; i ++){	
			card = new Card(func[0],func[4]);
			theDeck.add(card);
			card = new Card(func[0],func[5]);
			theDeck.add(card);
			card = new Card(func[0],func[6]);
			theDeck.add(card);
		}
		//double fix
		card = new Card(func[0],func[7]);
		theDeck.add(card);
		card = new Card(func[0],func[8]);
		theDeck.add(card);
		card = new Card(func[0],func[9]);
		theDeck.add(card);
		//road Card
		//dead end
		for(int i = 7; i < 16; i ++){
			card = new Card(road[i]);
			theDeck.add(card);		
		}
		card = new Card(road[7]);
		theDeck.add(card);		
		for(int i = 0; i < 5; i ++){
			for(int j = 0; j < 7; j ++){
				card = new Card(road[j]);
				theDeck.add(card);		
			}		
		}
	}
	Card giveACard(){
		Random rand = new Random();
		int r = rand.nextInt(theDeck.size());
		Card toReturn = new Card(theDeck.get(r));
		theDeck.remove(r);
		return toReturn;
	}
}
