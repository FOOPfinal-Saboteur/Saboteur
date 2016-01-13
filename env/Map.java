import java.lang.*;
import java.util.*;
import java.io.*;

class Position{

	/* Member variable */
	private boolean haveCard;
	private boolean isCandidate;
	private RoadCard card;

	/* Constructor */
	public Position(){
		haveCard = false;
		isCandidate = false;
		card = null;
	}

	/* Accessor */
	public boolean getHaveCard(){ return haveCard; }
	public boolean getIsCandidate(){ return isCandidate; }
	public boolean getBind(int n){ return card.getBind(n); }
	public boolean getConnect(int n){ return card.getConnect(n); }

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
	public void setCandidate(boolean b){ isCandidate = b; }
}

public class Map{

	/* Member variable */
		/* [x][y], x:horizontal, y:vertical */
		/* CTS: connected to source */
	private Position[][] pos = new Position[10][5];

	private boolean[][] vSideCTS = new boolean[11][5]; // vertical side 
	private boolean[][] hSideCTS = new boolean[10][6]; // horizontal side
	
	private boolean[][] traced = new boolean[10][5]; // for spread


	/* Constructor */
	public Map(){
		for(int i = 0; i < 10; i++)
			for(int j = 0; j < 5; j++)
				pos[i][j] = new Position();
		for(int i = 0; i < 11; i++)		
			for(int j = 0; j < 5; j++)
				vSideCTS[i][j] = false;
		for(int i = 0; i < 10; i++)		
			for(int j = 0; j < 6; j++)
				hSideCTS[i][j] = false;
		/* set source */
		RoadCard source = new RoadCard("intersection");
		pos[1][2].setCard(source);
		vSideCTS[1][2] = true; // Left 
		vSideCTS[2][2] = true; // Right
		hSideCTS[1][2] = true; // Bottom
		hSideCTS[1][3] = true; // Top
		/* set first candidates */
		pos[1][1].setCandidate(true); // Bottom
		pos[1][3].setCandidate(true); // Top
		pos[0][2].setCandidate(true); // Left
		pos[2][2].setCandidate(true); // Right
		/* set destinations */
		pos[9][0].setHaveCard(true); // Bottom
		pos[9][2].setHaveCard(true); // Middle
		pos[9][4].setHaveCard(true); // Top
	}

	/* Method */ 
	public boolean placeRoad(RoadCard c, int x, int y){
		/* check position index */
		if(x >= 10 || x < 0 || y >= 5 || y < 0){
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
		}else if(x < 9 && pos[x+1][y].getHaveCard()){ // Right
			if(x == 8 && (y == 0 || y == 2 || y == 4)){
			}else if(pos[x+1][y].getBind(1) != c.getBind(3)){
				System.out.println("Not matched");
				return false;
			}
		}else if(y > 0 && pos[x][y-1].getHaveCard()){ // Bottom
			if(x == 9){
			}else if(pos[x][y-1].getBind(0) != c.getBind(2)){
				System.out.println("Not matched");
				return false;
			}
		}else if(y < 4 && pos[x][y+1].getHaveCard()){ // Top
			if(x == 9){
			}else if(pos[x][y+1].getBind(2) != c.getBind(0)){
				System.out.println("Not matched");
				return false;
			}
		}
		pos[x][y].setCard(c);
		/* update CTS & candidates*/
		traceInit();
		spread(x, y);
		
		return true;
	}

	protected void traceInit(){
		for(int i = 0; i < 10; i++)
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
		if(pos[x][y].getConnect(5)){
			if(hSideCTS[x][y])
				hSideCTS[x][y+1] = true;
			if(hSideCTS[x][y+1])
				hSideCTS[x][y] = true;
		}
		if(pos[x][y].getConnect(6)){
			if(vSideCTS[x][y])
				vSideCTS[x+1][y] = true;
			if(vSideCTS[x+1][y])
				vSideCTS[x][y] = true;
		}

		traced[x][y] = true;

		if(x > 0 && !pos[x-1][y].getHaveCard()){ // Left
			if(vSideCTS[x][y])
				pos[x-1][y].setCandidate(true);
		}else if(x < 8 && !pos[x+1][y].getHaveCard()){ // Right
			if(vSideCTS[x+1][y])
				pos[x+1][y].setCandidate(true);
		}else if(x == 8 && (y == 1 | y == 3)
				&& !pos[x+1][y].getHaveCard()){ // Right
			if(vSideCTS[x+1][y])
				pos[x+1][y].setCandidate(true);
		}else if(y > 0 && !pos[x][y-1].getHaveCard()){ // Bottom
			if(hSideCTS[x][y])
				pos[x][y-1].setCandidate(true);
		}else if(y < 4 && !pos[x][y+1].getHaveCard()){ // Top
			if(hSideCTS[x][y+1])
				pos[x][y+1].setCandidate(true);
		}

		if(x > 0 && pos[x-1][y].getHaveCard()) 
			spread(x-1, y);
		if(x < 8 && pos[x+1][y].getHaveCard()) 
			spread(x+1, y);
		if(x == 8 && (y == 1 || y == 3)
			&& pos[x+1][y].getHaveCard())
			spread(x+1, y);
		if(x < 9 && y > 0 && pos[x][y-1].getHaveCard())
			spread(x, y-1);
		if(x < 9 && y < 5 && pos[x][y+1].getHaveCard())
			spread(x, y+1);
	}

	public boolean breakRoad(int x, int y){
		/* check position index */
		if(x >= 10 || x < 0 || y >= 5 || y < 0){
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
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 5; j++){
				if(pos[i][j].getHaveCard()){
					vSideCTS[i][j] = false;
					vSideCTS[i+1][j] = false;
					hSideCTS[i][j] = false;
					hSideCTS[i][j+1] = false;
				}
			}
		}
		vSideCTS[1][2] = true; // Left 
		vSideCTS[2][2] = true; // Right
		hSideCTS[1][2] = true; // Bottom
		hSideCTS[1][3] = true; // Top
		/* reset candidates */
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 5; j++){
				pos[i][j].setCandidate(false);
			}
		}
		traceInit();
		spread(1, 2); // spread from source

		return true;
	}	
} 
