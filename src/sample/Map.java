package sample;
import java.lang.*;
import java.util.*;
import java.io.*;

class Position{

	/* Member variable */
	private boolean haveCard;
	private boolean isCandidate;
	/* for destination */
		private boolean isDestination;
		private boolean isGold;
		private boolean flipped;
	private RoadCard card;

	/* Constructor */
	public Position(){
		haveCard = false;
		isCandidate = false;
		isDestination = false;
		isGold = false;
		card = null;
	}

	/* Accessor */
	public boolean getHaveCard(){ return haveCard; }
	public boolean getIsCandidate(){ return isCandidate; }
	public boolean getIsDestination(){return isDestination;}
	public boolean getIsGold(){return isGold;}
	public boolean getBind(int n){ return card.getBind(n); }
	public boolean getBindToAny(){
		for(int i = 0; i < 4; i ++)
			if(card.getBind(i))
				return true;
		return false;
	}
	public boolean getConnect(int n){ return card.getConnect(n);}
	public boolean getIsBlock(){ return card.isBlock();}
	public boolean getIsFlipped(){ return flipped; }

	/* Mutator */
	public boolean setCard(RoadCard c){
		if(haveCard){
			System.out.println("Occupied");
			return false;
		}
		card = c;
		haveCard = true;
		isCandidate = false;
		return true;
	}

	public boolean removeCard(){
		if(!haveCard)
			return false;
		card = null;
		haveCard = false;
		isCandidate = true;
		return true;
	}

	public void setHaveCard(boolean b){ haveCard = b; }
	public void setDestination(boolean d,boolean g,boolean f){isDestination = d; isGold = g; flipped = f;}
	public void setFlipped(boolean f){flipped = f;}
	public void setCandidate(boolean b){ isCandidate = b;/* System.out.println("YEAHYEAHYEAH // "+b);*/}
}

public class Map{

	/* Member variable */
		/* [x][y], x:horizontal, y:vertical */
		/* CTS: connected to source */
	private Position[][] pos = new Position[9][5];

	private boolean[][] vSideCTS = new boolean[10][5]; // vertical side 
	private boolean[][] hSideCTS = new boolean[9][6]; // horizontal side
	
	private boolean[][] traced = new boolean[9][5]; // for spread


	/* Constructor */
	public Map(){
		for(int i = 0; i < 9; i++)
			for(int j = 0; j < 5; j++)
				pos[i][j] = new Position();
		for(int i = 0; i < 10; i++)		
			for(int j = 0; j < 5; j++)
				vSideCTS[i][j] = false;
		for(int i = 0; i < 9; i++)		
			for(int j = 0; j < 6; j++)
				hSideCTS[i][j] = false;
		/* set source */
		RoadCard source = new RoadCard("intersection");
		pos[0][2].setCard(source);
		vSideCTS[0][2] = true; // Left
		vSideCTS[1][2] = true; // Right
		hSideCTS[0][2] = true; // Bottom
		hSideCTS[0][3] = true; // Top
		/* set first candidates */
		pos[0][1].setCandidate(true); // Bottom
		pos[0][3].setCandidate(true); // Top
		//pos[0][2].setCandidate(true); // Left
		pos[1][2].setCandidate(true); // Right
		/* set destinations*/
		pos[8][0].setDestination(true,false,false); // Bottom
		pos[8][2].setDestination(true,false,false); // Middle
		pos[8][4].setDestination(true,false,false); // Top
		// set destination have card
		pos[8][0].setHaveCard(true); // Bottom
		pos[8][2].setHaveCard(true); // Middle
		pos[8][4].setHaveCard(true); // Top
		Random ran = new Random();
		//pos[8][ran.nextInt(3)*2].setDestination(true, true,false); // TMB choose one
	}

	public void setDest(int where){
		pos[8][4 - 2*where].setDestination(true,true,false);
	}

	/* Accessor */
	public boolean haveGold(int y){
		return pos[8][y].getIsGold();
	}

	public boolean isFlipped(int y){
		return pos[8][y].getIsFlipped();
	}

	/* Method */ 
	public boolean receiveCard(Card card,int x,int y){
		if(card.IsFunction()){
			if(card.Function().isCollapse())
				return breakRoad(x,y);
		}
		else if(card.IsRoad()){
			return placeRoad(card.Road(),x,y);
		}
		else{
			System.out.println("Invalid Card");
			return false;
		}
		return false;
	}
	public boolean placeRoad(RoadCard c, int x, int y){
		/* check position index */
		if(x >= 9 || x < 0 || y >= 5 || y < 0){
			System.out.println("No such position");
			return false;
		}
		/* check candidate */
		if(!pos[x][y].getIsCandidate()){
			System.out.println("Not candidate");
			return false;
		}
		/* check bind */
		if(x > 0 && pos[x-1][y].getHaveCard()){ // Left
			if(pos[x-1][y].getBind(3) != c.getBind(1)){
				System.out.println("Not matched");
				return false;
			}
		}else if(x < 8 && pos[x+1][y].getHaveCard()){ // Right
			if(x == 7 && (y == 0 || y == 2 || y == 4)){
			}else if(pos[x+1][y].getBind(1) != c.getBind(3)){
				System.out.println("Not matched");
				return false;
			}
		}else if(y > 0 && pos[x][y-1].getHaveCard()){ // Bottom
			if(x == 8){
			}else if(pos[x][y-1].getBind(0) != c.getBind(2)){
				System.out.println("Not matched");
				return false;
			}
		}else if(y < 4 && pos[x][y+1].getHaveCard()){ // Top
			if(x == 8){
			}else if(pos[x][y+1].getBind(2) != c.getBind(0)){
				System.out.println("Not matched");
				return false;
			}
		}
		pos[x][y].setCard(c);
		/* update CTS & candidates*/
		traceInit();
		spread(x, y);

		if(x == 7 && y == 4){
			System.out.println("Very Close!");
			if(c.getBind(3) && vSideCTS[8][4]) {
				pos[8][4].setFlipped(true);
			}
		}else if(x == 7 && y == 2){
			System.out.println("Very Close!");
			if(c.getBind(3) && vSideCTS[8][2]) {
				pos[8][2].setFlipped(true);
			}
		}else if(x == 7 && y == 0){
			System.out.println("Very Close!");
			if(c.getBind(3) && vSideCTS[8][0]) {
				pos[8][0].setFlipped(true);
			}
		}else if(x == 8 && y == 3){
			System.out.println("Very Close!");
			if(c.getBind(0) && hSideCTS[8][4]) {
				pos[8][4].setFlipped(true);
			}
			if(c.getBind(2) && hSideCTS[8][3]) {
				pos[8][2].setFlipped(true);
			}
		}else if(x == 8 && y == 1){
			System.out.println("Very Close!");
			if(c.getBind(0) && vSideCTS[8][2]) {
				pos[8][2].setFlipped(true);
			}
			if(c.getBind(2) && vSideCTS[8][1]) {
				pos[8][0].setFlipped(true);
			}
		}
		
		return true;
	}

	protected void traceInit(){
		for(int i = 0; i < 9; i++)
			for(int j = 0; j < 5; j++)
				traced[i][j] = false;
	}

	protected void spread(int x, int y){
		if(traced[x][y])
			return;		
		if(pos[x][y].getConnect(0)){
			if(vSideCTS[x][y])
				hSideCTS[x][y+1] = true;
			if(hSideCTS[x][y+1])
				vSideCTS[x][y] = true;
		}
		if(pos[x][y].getConnect(1)){
			if(vSideCTS[x][y])
				hSideCTS[x][y] = true;
			if(hSideCTS[x][y])
				vSideCTS[x][y] = true;
		}
		if(pos[x][y].getConnect(2)){
			if(vSideCTS[x+1][y])
				hSideCTS[x][y] = true;
			if(hSideCTS[x][y])
				vSideCTS[x+1][y] = true;
		}
		if(pos[x][y].getConnect(3)){
			if(vSideCTS[x+1][y])
				hSideCTS[x][y+1] = true;
			if(hSideCTS[x][y+1])
				vSideCTS[x+1][y] = true;
		}
		if(pos[x][y].getConnect(4)){
			if(hSideCTS[x][y])
				hSideCTS[x][y+1] = true;
			if(hSideCTS[x][y+1])
				hSideCTS[x][y] = true;
		}
		if(pos[x][y].getConnect(5)){
			if(vSideCTS[x][y])
				vSideCTS[x+1][y] = true;
			if(vSideCTS[x+1][y])
				vSideCTS[x][y] = true;
		}

		traced[x][y] = true;
//
		if(x > 0 && !pos[x-1][y].getHaveCard()){ // Left
			if(vSideCTS[x][y])
				pos[x-1][y].setCandidate(true);
		}
		if(x < 7 && !pos[x+1][y].getHaveCard()){ // Right
			if(vSideCTS[x+1][y])
				pos[x+1][y].setCandidate(true);
		}
		if(x == 7 && (y == 1 || y == 3)
				&& !pos[x+1][y].getHaveCard()){ // Right
			if(vSideCTS[x+1][y])
				pos[x+1][y].setCandidate(true);
		}
		if(y > 0 && !pos[x][y-1].getHaveCard()){ // Bottom
			if(hSideCTS[x][y])
				pos[x][y-1].setCandidate(true);
		}
		if(y < 4 && !pos[x][y+1].getHaveCard()){ // Top
			if(hSideCTS[x][y+1])
				pos[x][y+1].setCandidate(true);
		}

		if(x > 0 && pos[x-1][y].getHaveCard()) 
			spread(x-1, y);
		if(x < 7 && pos[x+1][y].getHaveCard()) 
			spread(x+1, y);
		if(x == 7 && (y == 1 || y == 3)
			&& pos[x+1][y].getHaveCard())
			spread(x+1, y);
		if(x < 8 && y > 0 && pos[x][y-1].getHaveCard())
			spread(x, y-1);
		if(x < 8 && y < 4 && pos[x][y+1].getHaveCard())
			spread(x, y+1);
	}

	public boolean breakRoad(int x, int y){
		/* check position index */
		if(x >= 9 || x < 0 || y >= 5 || y < 0){
			System.out.println("No such position");
			return false;
		}
		/* check haveCard */
		if(!pos[x][y].getHaveCard()){
			System.out.println("No road");
			return false;
		}
		pos[x][y].removeCard();
		/* reset CTS */
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 5; j++){
				if(pos[i][j].getHaveCard()){
					vSideCTS[i][j] = false;
					vSideCTS[i+1][j] = false;
					hSideCTS[i][j] = false;
					hSideCTS[i][j+1] = false;
				}
			}
		}
		vSideCTS[0][2] = true; // Left
		vSideCTS[1][2] = true; // Right
		hSideCTS[0][2] = true; // Bottom
		hSideCTS[0][3] = true; // Top
		/* reset candidates */
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 5; j++){
				pos[i][j].setCandidate(false);
			}
		}
		traceInit();
		spread(0, 2); // spread from source

		return true;
	}
	public char[][] MapStatus(){
		char[][] simpleMap = new char[37][21];
		for(int i = 0; i < 37; i ++)
			for(int j = 0; j < 21; j ++)
				simpleMap[i][j] = (i % 2 == 0 && (j % 2 == 0) && !((i + j) % 4 == 0)) ? '.':' ';
			
		simpleMap[4 * 0 + 2][4 * 2 + 2] = 's';
		simpleMap[4 * 8 + 2][4 * 0 + 2] = 'b';
		simpleMap[4 * 8 + 2][4 * 2 + 2] = 'm';
		simpleMap[4 * 8 + 2][4 * 4 + 2] = 't';

		for(int i = 0; i < 9; i ++)
			for(int j = 0; j < 5; j ++){
				if(!pos[i][j].getHaveCard())
					continue;
				if(pos[i][j].getIsDestination())
					continue;
				if(!pos[i][j].getBindToAny())
					continue;
				System.out.println(i +" " + j);
				int centerx = i * 4 + 2;
				int centery = j * 4 + 2;
				if(pos[i][j].getConnect(0)){
					simpleMap[centerx - 1][centery + 1] = '/';
				}
				if(pos[i][j].getConnect(1)){
					simpleMap[centerx - 1][centery - 1] = '\\';
				}
				if(pos[i][j].getConnect(2)){
					simpleMap[centerx + 1][centery - 1] = '/';
				}
				if(pos[i][j].getConnect(3)){
					simpleMap[centerx + 1][centery + 1] = '\\';
				}
				if(pos[i][j].getConnect(4)){
					simpleMap[centerx][centery + 1] = '|';
					simpleMap[centerx][centery - 1] = '|';
				}
				if(pos[i][j].getConnect(5)){
					simpleMap[centerx - 1][centery] = '-';
					simpleMap[centerx + 1][centery] = '-';
				}
				if(!pos[i][j].getBind(0))
					simpleMap[centerx][centery + 2] = 'x';
				if(!pos[i][j].getBind(1))
					simpleMap[centerx - 2][centery] = 'x';
				if(!pos[i][j].getBind(2))
					simpleMap[centerx][centery - 2] = 'x';
				if(!pos[i][j].getBind(3))
					simpleMap[centerx + 2][centery] = 'x';
				if(pos[i][j].getIsBlock())
					simpleMap[centerx][centery] = 'b';

		}
		return simpleMap;
	}
	public String toString(){
		char[][] charMap = MapStatus();
		String toReturn = new String();
		for(int j = 20; j >= 0; j--){
			toReturn += (j % 4 == 2) ? Integer.toString(j/4):" ";
			for(int i = 0; i < 37; i ++){
				toReturn += charMap[i][j];
			}
			toReturn += "\n";
		}
		for(int i = 0; i < 38; i ++){
			toReturn += (i % 4 == 3)? Integer.toString(i/4):" ";
		}
		return toReturn;
	}
} 
