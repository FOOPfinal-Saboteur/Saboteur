import java.lang.*;
import java.util.*;
import java.io.*;

class Edge{
	static boolean Vertical = true;
	static boolean Horizonal = false;
	static int horizon_group;
	boolean vertical;// thiskind:|
	int weight;
	int toWhere;

	Edge(int Place, int Weight,int horizon){ //for horizon;
		 toWhere = Place; weight = Weight; horizon_group = horizon;
	}
}
class Node implements Comparable<Node>{
	int place;
	int weight;
	Node(int p,int w){
		place = p;
		weight = w;
	}
	@Override
	public int compareTo(Node other){
		return weight - other.weight;
	}

}
class InnerMap{
	static boolean Vertical = true;
	static boolean Horizontal = false;
	List<List<Edge>> AdjList;
	int Row;
	int Column;
	int[] Distance;
	int source;
	int minT,minM,minB;
	int horizon_num;
	int all_num;
	InnerMap(int column,int row){//row is y, column is x
		Row = row;
		Column = column;
		all_num = column *(row + 1) + (column + 1) * row;
		Distance = new int[column *(row + 1) + (column + 1) * row];
		for(int i = 0; i < column *(row + 1) + (column + 1) * row; i ++)
			Distance[i] = 500;
		horizon_num = column * (row + 1);
		AdjList = new ArrayList<List<Edge>>(9);
		for(int i = 0; i < (column * (row + 1) + (column + 1) * row); i ++)
			AdjList.add(i, new ArrayList<Edge>(2));
		for(int i = 0; i < (column * (row + 1) + (column + 1) * row); i ++){
			int x, y;
			if(i >= horizon_num){// is a vertical edge
				int tmp_num = i - horizon_num;
				x = tmp_num % (column + 1);
				y = tmp_num / (column + 1);
				if(validEdge(x - 1,y,Vertical))	AdjList.get(i).add(new Edge(EdgeNum(x - 1,y,Vertical),1,horizon_num));
				if(validEdge(x + 1,y,Vertical)) AdjList.get(i).add(new Edge(EdgeNum(x + 1,y,Vertical),1,horizon_num));
				if(validEdge(x,y,Horizontal)) AdjList.get(i).add(new Edge(EdgeNum(x ,y,Horizontal),1,horizon_num));
				if(validEdge(x,y + 1,Horizontal)) AdjList.get(i).add(new Edge(EdgeNum(x ,y + 1,Horizontal),1,horizon_num));
				if(validEdge(x - 1,y,Horizontal)) AdjList.get(i).add(new Edge(EdgeNum(x - 1,y,Horizontal),1,horizon_num));
				if(validEdge(x - 1,y + 1,Horizontal)) AdjList.get(i).add(new Edge(EdgeNum(x - 1,y + 1,Horizontal),1,horizon_num));
			}
			if(i < horizon_num){
				x = i % column;
				y = i / column;
				if(validEdge(x,y +1,Horizontal)) AdjList.get(i).add(new Edge(EdgeNum(x ,y + 1,Horizontal),1,horizon_num));
				if(validEdge(x,y -1,Horizontal))AdjList.get(i).add(new Edge(EdgeNum(x ,y - 1,Horizontal),1,horizon_num));
				if(validEdge(x,y,Vertical))AdjList.get(i).add(new Edge(EdgeNum(x ,y,Vertical),1,horizon_num));
				if(validEdge(x + 1,y,Vertical))AdjList.get(i).add(new Edge(EdgeNum(x + 1,y,Vertical),1,horizon_num));
				if(validEdge(x,y - 1,Vertical))AdjList.get(i).add(new Edge(EdgeNum(x ,y - 1,Vertical),1,horizon_num));
				if(validEdge(x + 1,y - 1,Vertical))AdjList.get(i).add(new Edge(EdgeNum(x + 1,y - 1,Vertical),1,horizon_num));
			}
		//	System.out.println(pObvious(i) + ":" + i);
		//	for(Edge e:AdjList[i]){
		//		System.out.print(pObvious(e.toWhere)+ " ");
		//	}
		//	System.out.println();
		}
		source = EdgeNum(0,2,Vertical);
		assignCard(0,2,new RoadCard("intersection"));
		Distance[source] = 0;
		Dijkstra();
		minT = minDist(8,4);
		minM = minDist(8,2);
		minB = minDist(8,0);
	}
	public boolean canPut(int x,int y,RoadCard card){
//		System.out.println(x +" "+y);
		if(!noRoad(x,y))
			return false;
		if(!(Distance[EdgeNum(x,y,Vertical)] == 0 || Distance[EdgeNum(x + 1,y,Vertical)] == 0 || Distance[EdgeNum(x,y,Horizontal)] == 0|| Distance[EdgeNum(x,y + 1,Horizontal)] == 0) )
			return false;
		if( (card.getBind(1) && Distance[EdgeNum(x,y,Vertical)] >= 100) || (!card.getBind(1) && Distance[EdgeNum(x,y,Vertical)] == 0))
			return false;	
		if( (card.getBind(3) && Distance[EdgeNum(x + 1,y,Vertical)] >= 100) || (!card.getBind(3) && Distance[EdgeNum(x + 1,y,Vertical)]== 0))
			return false;	
		if( (card.getBind(2) && Distance[EdgeNum(x,y,Horizontal)] >= 100) || (!card.getBind(2) && Distance[EdgeNum(x,y,Horizontal)] == 0))
				return false;		
		if( (card.getBind(0) && Distance[EdgeNum(x,y + 1,Horizontal)] >= 100) || (!card.getBind(0) && Distance[EdgeNum(x,y + 1,Horizontal)] == 0))
				return false;
		return true;
	}
	public boolean shouldPut(int x,int y){
		if(x == 0 && y == 2)
			return false;
		if(noRoad(x,y))
			return false;
		if(Distance[EdgeNum(x,y,Vertical)] == 0)
			return true;
		if(Distance[EdgeNum(x + 1,y,Vertical)] == 0)
			return true;
		if(Distance[EdgeNum(x,y,Horizontal)] == 0)
			return true;
		if(Distance[EdgeNum(x,y + 1,Horizontal)] == 0)
			return true;
		return true;
	}
	private boolean noRoad(int x, int y){
		if(EdgeWeight(EdgeNum(x,y+1,Horizontal),EdgeNum(x,y,Vertical)) == 1 ||EdgeWeight(EdgeNum(x,y+1,Horizontal),EdgeNum(x,y,Vertical)) == 600)
			return true;
		if(EdgeWeight(EdgeNum(x,y,Horizontal),EdgeNum(x,y,Vertical)) == 1 ||EdgeWeight(EdgeNum(x,y,Horizontal),EdgeNum(x,y,Vertical)) == 600)
			return true;
		if(EdgeWeight(EdgeNum(x,y,Horizontal),EdgeNum(x+1,y,Vertical)) == 1 ||EdgeWeight(EdgeNum(x,y,Horizontal),EdgeNum(x+1,y,Vertical)) == 600)
			return true;
		if(EdgeWeight(EdgeNum(x,y+1,Horizontal),EdgeNum(x+1,y,Vertical)) == 1 ||EdgeWeight(EdgeNum(x,y+1,Horizontal),EdgeNum(x+1,y,Vertical)) == 600)
			return true;
		if(EdgeWeight(EdgeNum(x,y+1,Horizontal),EdgeNum(x,y,Horizontal)) == 1||EdgeWeight(EdgeNum(x,y+1,Horizontal),EdgeNum(x,y,Horizontal)) == 600)
			return true;
		if(EdgeWeight(EdgeNum(x+1,y,Vertical),EdgeNum(x,y,Vertical)) == 1 ||EdgeWeight(EdgeNum(x+1,y,Vertical),EdgeNum(x,y,Vertical)) == 600)
			return true;
		return false;
	}
	public WhatHappen receiveCard(int x,int y,Card card){
		int originT = minT;
		int originM = minM;
		int originB = minB;
		if(card.IsFunction()){
			FunctionCard func = card.Function();
			if(!func.isCollapse() || !shouldPut(x,y)){
				System.out.println("Wrong Card");
				return null;
			}
			breakRoad(x,y);
		}
		else if(card.IsRoad()){
			RoadCard road = card.Road();
			if(!canPut(x,y,road)){
				System.out.println("Cannot put here");
				return null;
			}
			assignCard(x,y,road);
		}
		Dijkstra();
		minT = minDist(8,4);
		minM = minDist(8,2);
		minB = minDist(8,0);
		return new WhatHappen(originT - minT, originM - minM,originB -minB);
	}
	public WhatHappen tryCard(int x,int y,Card card){
		int originT = minT;
		int originM = minM;
		int originB = minB;
		RoadCard origin = null;
		if(card.IsFunction()){
			FunctionCard func = card.Function();
			if(!func.isCollapse()){
				System.out.println("Wrong Card");
				return null;
			}
			origin = OriginCard(x,y);
			breakRoad(x,y);
		}
		else if(card.IsRoad()){
			RoadCard road = card.Road();
			if(!canPut(x,y,road)){
				System.out.println("Cannot put here");
				return null;
			}
			assignCard(x,y,road);
		}
		Dijkstra();
		minT = minDist(8,4);
		minM = minDist(8,2);
		minB = minDist(8,0);
		WhatHappen toReturn = new WhatHappen(originT -minT,originM -minM, originB -minB);
//		printMap();
		if(card.IsFunction()){
			assignCard(x,y,origin);
			Dijkstra();
			minT = minDist(8,4);
			minM = minDist(8,2);
			minB = minDist(8,0);
		}
		else if(card.IsRoad()){
			breakRoad(x,y);
			Dijkstra();
			minT = minDist(8,4);
			minM = minDist(8,2);
			minB = minDist(8,0);
			if(minT == 500)
				System.out.println("something wrong\n" + card);
		}
		return toReturn;
	//	if()
	}
	public boolean flipOnTop(){return minT == 0;}
	public boolean flipOnMid(){return minM == 0;}
	public boolean flipOnBtm(){return minB == 0;}
	public String pObvious(int place){
		String toReturn = new String();
		int x,y;
		if(place >= horizon_num){
			int k = place - horizon_num; 
			x = k % (Column + 1);
			y = k / (Column + 1);
			toReturn += ("(" + x +"," + y + "," + "|)");
		}
		else{
			int k = place;
			x = k % (Column);
			y = k / (Column);
			toReturn += ("(" + x +"," + y + "," + "-)");
		
		}
		return toReturn;
	}
	public RoadCard OriginCard(int x,int y){
		boolean[] bind = new boolean[4];
		for(int i = 0; i < 4; i++)
			bind[i] = false;
		boolean[] connect =  new boolean[6];
		for(int i = 0; i < 6; i ++)
			connect[i] = false;
		if(Distance[EdgeNum(x,y + 1,Horizontal)] < 100)
			bind[0] = true;
		if(Distance[EdgeNum(x,y,Vertical)] < 100)
			bind[1] = true;
		if(Distance[EdgeNum(x,y,Horizontal)] < 100)
			bind[2] = true;
		if(Distance[EdgeNum(x + 1,y,Vertical)] < 100)
			bind[3] = true;
		if(EdgeWeight(EdgeNum(x,y+1,Horizontal),EdgeNum(x,y,Vertical)) < 100)
			connect[0] =true;
		if(EdgeWeight(EdgeNum(x,y,Horizontal),EdgeNum(x,y,Vertical)) < 100)
			connect[1] =true;
		if(EdgeWeight(EdgeNum(x,y,Horizontal),EdgeNum(x+1,y,Vertical)) < 100)
			connect[2] =true;
		if(EdgeWeight(EdgeNum(x,y+1,Horizontal),EdgeNum(x+1,y,Vertical)) < 100)
			connect[3] =true;
		if(EdgeWeight(EdgeNum(x,y+1,Horizontal),EdgeNum(x,y,Horizontal)) < 100)
			connect[4] =true;
		if(EdgeWeight(EdgeNum(x+1,y,Vertical),EdgeNum(x,y,Vertical)) < 100)
			connect[5] =true;
		return new RoadCard(bind,connect,false);

	}
	public void assignCard(int x, int y,RoadCard card){
		if(!card.getBind(0)) isolate(EdgeNum(x,y+1,Horizontal));
		if(!card.getBind(1)) isolate(EdgeNum(x,y,Vertical));
		if(!card.getBind(2)) isolate(EdgeNum(x,y,Horizontal));
		if(!card.getBind(3)) isolate(EdgeNum(x + 1,y,Vertical));
		if(card.getConnect(0))
			changeEdge(EdgeNum(x,y+1,Horizontal),EdgeNum(x,y,Vertical),0);
		else
			changeEdge(EdgeNum(x,y+1,Horizontal),EdgeNum(x,y,Vertical),700);	
		if(card.getConnect(1))
			changeEdge(EdgeNum(x,y,Horizontal),EdgeNum(x,y,Vertical),0);
		else 
			changeEdge(EdgeNum(x,y,Horizontal),EdgeNum(x,y,Vertical),700);
		if(card.getConnect(2)) 
			changeEdge(EdgeNum(x,y,Horizontal),EdgeNum(x + 1,y,Vertical),0);
		else 
			changeEdge(EdgeNum(x,y,Horizontal),EdgeNum(x + 1,y,Vertical),700);
		if(card.getConnect(3)) 
			changeEdge(EdgeNum(x,y+1,Horizontal),EdgeNum(x+1,y,Vertical),0);
		else 
			changeEdge(EdgeNum(x,y+1,Horizontal),EdgeNum(x+1,y,Vertical),700);
		if(card.getConnect(4)) 
			changeEdge(EdgeNum(x,y+1,Horizontal),EdgeNum(x,y,Horizontal),0);
		else
			changeEdge(EdgeNum(x,y+1,Horizontal),EdgeNum(x,y,Horizontal),700);
		if(card.getConnect(5))
			changeEdge(EdgeNum(x+1,y,Vertical),EdgeNum(x,y,Vertical),0);
		else
			changeEdge(EdgeNum(x+1,y,Vertical),EdgeNum(x,y,Vertical),700);
	}

	public void breakRoad(int x, int y){
		changeEdge(EdgeNum(x,y+1,Horizontal),EdgeNum(x,y,Vertical),1);
		changeEdge(EdgeNum(x,y,Horizontal),EdgeNum(x,y,Vertical),1);
		changeEdge(EdgeNum(x,y,Horizontal),EdgeNum(x + 1,y,Vertical),1);
		changeEdge(EdgeNum(x,y+1,Horizontal),EdgeNum(x + 1,y,Vertical),1);
		changeEdge(EdgeNum(x,y+1,Horizontal),EdgeNum(x,y,Horizontal),1);
		changeEdge(EdgeNum(x+1,y,Vertical),EdgeNum(x,y,Vertical),1);
		for(Edge e:AdjList.get(EdgeNum(x,y,Horizontal))){
			if(used_to_be_dead(EdgeNum(x,y,Horizontal))){ //unable because of edge
				if(e.weight == 1)
					changeEdge(EdgeNum(x,y,Horizontal), e.toWhere, 600);
			}
			else
				if(e.weight == 600&& !used_to_be_dead(e.toWhere))
					changeEdge(EdgeNum(x,y,Horizontal), e.toWhere, 1);

		}
		for(Edge e:AdjList.get(EdgeNum(x,y + 1,Horizontal))){
			if(used_to_be_dead(EdgeNum(x,y + 1,Horizontal))){ //unable because of edge
				if(e.weight == 1)
					changeEdge(EdgeNum(x,y + 1,Horizontal), e.toWhere, 600);
			}
			else
				if(e.weight == 600&& !used_to_be_dead(e.toWhere))
					changeEdge(EdgeNum(x,y + 1,Horizontal), e.toWhere, 1);
		}
		for(Edge e:AdjList.get(EdgeNum(x,y,Vertical))){
			if(used_to_be_dead(EdgeNum(x,y,Vertical))){ //unable because of edge
				if(e.weight == 1)
					changeEdge(EdgeNum(x,y,Vertical), e.toWhere, 600);
			}
			else
				if(e.weight == 600&& !used_to_be_dead(e.toWhere))
					changeEdge(EdgeNum(x,y,Vertical), e.toWhere, 1);
		}
		for(Edge e:AdjList.get(EdgeNum(x + 1,y,Vertical))){
			if(used_to_be_dead(EdgeNum(x,y,Vertical))){ //unable because of edge
				if(e.weight == 1)
					changeEdge(EdgeNum(x + 1,y,Vertical), e.toWhere, 600);
			}
			else
				if(e.weight == 600&& !used_to_be_dead(e.toWhere))
					changeEdge(EdgeNum(x + 1,y,Vertical), e.toWhere, 1);
		}
	}
	private boolean used_to_be_dead(int place){
		for(Edge e:AdjList.get(place))
			if(e.weight == 700)
				return true;
		return false;
	}
	private void isolate(int place){
		Distance[place] = 500;
		for(Edge e:AdjList.get(place)){
			changeEdge(place,e.toWhere,600);
		}
	}
	public void changeEdge(int p_st,int p_nd,int Weight){
		for(Edge e:AdjList.get(p_st))
			if(e.toWhere == p_nd)
				e.weight = Weight;
		for(Edge e:AdjList.get(p_nd))
			if(e.toWhere == p_st)
				e.weight = Weight;
	}
	public void Dijkstra(){
		for(int i = 0; i < all_num; i ++)
			Distance[i] = 500;
		Distance[source] = 0;
		PriorityQueue<Node> NodeQ = new PriorityQueue<Node>(200);
		NodeQ.add(new Node(source,0));
		while(NodeQ.size() > 0){
			Node head = NodeQ.poll();
	//		System.out.println(pObvious(head.place) + ":");
			for(Edge e:AdjList.get(head.place)){
	//			System.out.print( pObvious(e.toWhere)+" ");
				if(e.weight + head.weight < Distance[e.toWhere]){
					Distance[e.toWhere] = e.weight + Distance[head.place];
					NodeQ.add(new Node(e.toWhere,Distance[e.toWhere]));
				}
			}
	//		System.out.println();
		}
	}
	public int minDist(int x, int y){
		int min = 10000;
		if(min > Distance[EdgeNum(x,y,Vertical)]) min = Distance[EdgeNum(x,y,Vertical)];
		if(min > Distance[EdgeNum(x + 1,y ,Vertical)]) min = Distance[EdgeNum(x,y + 1,Vertical)];
		if(min > Distance[EdgeNum(x,y,Horizontal)]) min = Distance[EdgeNum(x,y,Horizontal)];
		if(min > Distance[EdgeNum(x,y + 1,Horizontal)]) min = Distance[EdgeNum(x + 1,y,Horizontal)];
		return min;	
	}
	private boolean validEdge(int _x,int _y,boolean isVertical){

		return ((isVertical &&!( _x < 0 || _x > Column || _y < 0 || _y >= Row))|| ((!isVertical) && !(_x < 0 || _x >= (Column ) || _y < 0 || _y > Row)));
	}
	private int EdgeWeight(int p_st,int p_nd){
		for(Edge e:AdjList.get(p_st)){
			if(e.toWhere == p_nd)
				return e.weight;
		}
		return 1010;
	}
	private int EdgeNum(int _x,int _y, boolean isVertical){
		int toReturn = 0;
		if(isVertical == Vertical)
			toReturn = (_x + (_y * (Column + 1)) + Column * (Row + 1));
		if(isVertical == Horizontal)
			toReturn = (_x + (_y * Column ));
		return toReturn;
	}
	public void printMap(){
		int[][] simpleMap = new int[2 * Column + 1][2 * Row + 1];
		for(int i = 0; i < 2 * Column + 1; i ++)
			for(int j = 0; j < 2 *Row + 1; j ++)
				simpleMap[i][j] = -1;
		for(int x = 0; x < Column; x ++){
			for(int y = 0; y < Row; y ++){
				simpleMap[2 * x][2 * y + 1] = Distance[EdgeNum(x,y,Vertical)];
				simpleMap[2 * x + 2][2 * y + 1] = Distance[EdgeNum(x + 1,y,Vertical)];
				simpleMap[2 * x + 1][2 * y] = Distance[EdgeNum(x,y,Horizontal)];
				simpleMap[2 * x + 1][2 * y + 2] = Distance[EdgeNum(x,y + 1,Horizontal)];
			}
		}
		for(int j = 2 * Row;j >= 0; j --){
			if(j % 2 == 0)
				System.out.print("   ");
			else
				System.out.print(j/2+"  ");
			for(int i = 0; i < 2 * Column + 1; i ++){
				String toPrint = new String();
				toPrint += (simpleMap[i][j] == - 1) ? "   " : String.format("%03d",simpleMap[i][j]);
				System.out.print(toPrint);
			}
			System.out.println();
		}
		System.out.print("   ");
		for(int i = 0; i < Column; i++)
			System.out.print("     " +i);
		System.out.println();
	}
	public String toString(){
		String toReturn = new String();
		for(int i = 0; i < all_num; i ++){
			String type;
			toReturn += ("Vertice "+pObvious(i)+":\n");
			for(Edge e:AdjList.get(i)){
				toReturn += (" to"+ pObvious(e.toWhere)+",weight " + e.weight);
			}
			toReturn += "\n";
		}
		return toReturn;
	}
}

