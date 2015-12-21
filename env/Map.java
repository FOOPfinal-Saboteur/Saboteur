import java.lang.*;
import java.util.*;
import java.io.*;

public class Map{

	/* Member variable */
		/* [x][y], x:horizontal, y:vertical */
		/* Bind- 0:false 1:true 2:undefined */
		/* CTS: connected to source */
	private boolean[][] haveCard[10][5];
	private boolean[][] candidate[10][5];

	private int[][] vSideBind[11][5]; // vertical side 
	private boolean[][] vSideCTS[11][5]; // vertical side 

	private int[][] hSideBind[10][6]; // horizontal side
	private boolean[][] hSideCTS[10][6]; // horizontal side
	

	/* Constructor */
	public Map(){
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 5; j++){
				haveCard[i][j] = false;
				candidate[i][j] = false;
			}
		}
		/* set source */
		haveCard[1][2] = true;
		vSideCTS[1][2] = true; // Left 
		vSideCTS[2][2] = true; // Right
		hSideCTS[1][2] = true; // Bottom
		hSideCTS[1][3] = true; // Top
		/* set first candidates */
		candidate[1][1] = true;
		candidate[1][3] = true;
		candidate[0][2] = true;
		candidate[2][2] = true;
		/* set destinations */
		haveCard[9][0] = true;
		haveCard[9][2] = true;
		haveCard[9][4] = true;
	}

	/* Method */ 
	public void placeRoad(RoadCard c){
		
	}
} 