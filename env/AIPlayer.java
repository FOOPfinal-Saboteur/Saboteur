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
class Node{
	int place;
	int weight;
	Node(int p,int w){
		place = p;
		weight = w;
	}
}
public class NodeComparator implements Comparator<Node>{
	@Override
	public int compare(Node x,Node y){
		if(x.weight > y.weight)
			return -1;
		if(x.weight  < y.weight)
			return 1;
		return 0;
	}
}
class WhatHappen{
	int TopChange;
	int MidChange;
	int BtmChange;
	WhatHappen(int t,int m, int b){
		TopChange = t; MidChange = m; BtmChange = b;
	}
	public int HowmanyCloser(){
		int num = 0;
		if(TopChange > 0) num ++;
		if(MidChange > 0) num ++;
		if(BtmChange > 0) num ++;
		return num;
	}
}
class InnerMap{
	static boolean Vertical = true;
	static boolean Horizontal = false;
	ArrayList<Edge>[] AdjList;
	int Row;
	int Column;
	int[] Distance;
	int source;
	int minT,minM,minB;
	InnerMap(int column,int row){//row is y, column is x
		Row = row;
		Column = column;
		Distance = new int[column *(row + 1) + (column + 1) * row];
		for(int i = 0; i < Distance.lenth(); i ++)
			Destance[i] = 100;
		int horizon_num = column * (row + 1);
		for(int i = 0; i < (column * (row + 1) + (column + 1) * row); i ++)
			AdjList[i] = new ArrayList<Edge>();
		for(int i = 0; i < (column * (row + 1) + (column + 1) * row) i ++){
			int x, y;
			if(i >= horizon_num){// is a vertical edge
				int tmp_num = i - horizon_num;
				x = i % (column + 1);
				y = i / (column + 1);
				if(validEdge(x - 1,y,Vertical))	AdjList[i].add(new Edge(EdgeNum(x - 1,y,Vertical),1,horizon_num));
				if(validEdge(x + 1,y,Vertical)) AdjList[i].add(new Edge(EdgeNum(x + 1,y,Vertical),1,horizon_num));
				if(validEdge(x,y,Horizontal)) AdjList[i].add(new Edge(EdgeNum(x ,y,Horizontal),1,horizon_num));
				if(validEdge(x,y + 1,Horizontal)) AdjList[i].add(new Edge(EdgeNum(x ,y + 1,Horizontal),1,horizon_num));
				if(validEdge(x - 1,y,Horizontal)) AdjList[i].add(new Edge(EdgeNum(x - 1,y,Horizontal),1,horizon_num));
				if(validEdge(x - 1,y + 1,Horizontal)) AdjList[i].add(new Edge(EdgeNum(x - 1,y + 1,Horizontal),1,horizon_num));
			}
			if(i < horizon_num){
				x = i % column;
				y = i / column;
				if(validEdge(x,y +1,Horziontal)) AdjList[i].add(new Edge(EdgeNum(x ,y + 1,Horizontal),1,horizon_num));
				if(validEdge(x,y -1,Horziontal))AdjList[i].add(new Edge(EdgeNum(x ,y - 1,Horizontal),1,horizon_num));
				if(validEdge(x,y,Vertical))AdjList[i].add(new Edge(EdgeNum(x ,y,Vertical),1,horizon_num));
				if(validEdge(x + 1,y,Vertical))AdjList[i].add(new Edge(EdgeNum(x + 1,y,Vertical),1,horizon_num));
				if(validEdge(x,y - 1,Vertical))AdjList[i].add(new Edge(EdgeNum(x ,y - 1,Vertical),1,horizon_num));
				if(validEdge(x + 1,y - 1,Vertical))AdjList[i].add(new Edge(EdgeNum(x + 1,y - 1,Vertical),1,horizon_num));
			}
		}
		source = EdgeNum(1,2,Vertical);
		assignCard(1,2,new RoadCard("intersection"));
		Distance[source] = 0;
		Dijkstra();
		minT = minDist(9,4);
		minM = minDist(9,2);
		minB = minDist(9,0);
	}
	public boolean canPut(int x,int y,RoadCard card){
		if(!(Distance[EdgeNum(x,y,Vertical)] == 0 || Distance[EdgeNum(x + 1,y,Vertical)] == 0 || Distance[EdgeNum(x,y,Horizontal)] == 0|| Distance[EdgeNum(x,y,Vertical)] == 0) )
			return false;
		if( (card.getConnect(1) && Distance[EdgeNum(x,y,Vertical)] >= 100) || (!card.getConnect(1) && Distance[EdgeNum(x,y,Vertical)] == 0))
			return false;	
		if( (card.getConnect(3) && Distance[EdgeNum(x + 1,y,Vertical)] >= 100) || (!card.getConnect(3) && Distance[EdgeNum(x + 1,y,Vertical)]== 0))
			return false;	
		if( (card.getConnect(2) && Distance[EdgeNum(x,y,Horziontal)] >= 100) || (!card.getConnect(2) && Distance[EdgeNum(x,y,Horziontal)] == 0))
				return false;		
		if( (card.getConnect(0) && Distance[EdgeNum(x,y + 1,Horziontal)] >= 100) || (!card.getConnect(0) && Distance[EdgeNum(x,y + 1,Horziontal)] == 0))
				return false;
		return true;
	}
	public WhatHappen receiveCard(int x,int y,Card card){
		int originT = minT;
		int originM = minM;
		int originB = minB;
		if(card.IsFunction()){
			FunctionCard func = card.Function();
			if(!func.isCollapse()){
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
		minT = minDist(9,4);
		minM = minDist(9,2);
		minB = minDist(9,0);
		return new WhatHappen(minT - originT, minM - originM, minB - originB);
	}
	public WhatHappen tryCard(int x,int y,int Card card){
		int originT = minT;
		int originM = minM;
		int originB = minB;
		if(card.IsFunction()){

			FunctionCard func = card.Function();
			if(!func.isCollapse()){
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
		minT = minDist(9,4);
		minM = minDist(9,2);
		minB = minDist(9,0);
		WhatHappen toReturn = new WhatHappen(minT - originT, minM - originM, minB - originB);
		if()
	}
	public void assignCard(int x, int y,RoadCard card){
		if(!card.getConnect(0)) isolate(EdgeNum(x,y+1,Horizontal));
		if(!card.getConnect(1)) isolate(EdgeNum(x,y,Vertical));
		if(!card.getConnect(2)) isolate(EdgeNum(x,y,Horizontal));
		if(!card.getConnect(3)) isolate(EdgeNum(x + 1,y,Vertical));
		(card.getBind(0)) ? changeEdge(EdgeNum(x,y+1,Horizontal),EdgeNum(x,y,Vertical),0):changeEdge(EdgeNum(x,y+1,Horizontal),EdgeNum(x,y,Vertical),100);
		(card.getBind(1)) ? changeEdge(EdgeNum(x,y,Horizontal),EdgeNum(x,y,Vertical),0):changeEdge(EdgeNum(x,y,Horizontal),EdgeNum(x,y,Vertical),100);
		(card.getBind(2)) ? changeEdge(EdgeNum(x,y,Horizontal),EdgeNum(x + 1,y,Vertical),0):changeEdge(EdgeNum(x,y,Horizontal),EdgeNum(x + 1,y,Vertical),100);
		(card.getBind(3)) ? changeEdge(EdgeNum(x,y+1,Horizontal),EdgeNum(x+1,y,Vertical),0):changeEdge(EdgeNum(x,y+1,Horizontal),EdgeNum(x+1,y,Vertical),100);
		(card.getBind(4)) ? changeEdge(EdgeNum(x,y+1,Horizontal),EdgeNum(x,y,Horizontal),0):changeEdge(EdgeNum(x,y+1,Horizontal),EdgeNum(x,y,Horizontal),100);
		(card.getBind(5)) ? changeEdge(EdgeNum(x+1,y,Vertical),EdgeNum(x,y,Vertical),0):changeEdge(EdgeNum(x+1,y,Vertical),EdgeNum(x,y,Vertical),100);
	}

	public void breakRoad(int x, int y){
		changeEdge(EdgeNum(x,y+1,Horizontal),EdgeNum(x,y,Vertical),1);
		changeEdge(EdgeNum(x,y,Horizontal),EdgeNum(x,y,Vertical),1);
		changeEdge(EdgeNum(x,y,Horizontal),EdgeNum(x + 1,y,Vertical),1);
		changeEdge(EdgeNum(x,y+1,Horizontal),EdgeNum(x + 1,y,Vertical),1);
		changeEdge(EdgeNum(x,y+1,Horizontal),EdgeNum(x,y,Horziontal),1);
		changeEdge(EdgeNum(x+1,y,Vertical),EdgeNum(x,y,Vertical),1);
	}
	private void isolate(int place){
		Distance[place] = 500;
		for(Edge e:AdjList[place]){
			changeEdge(place,e.toWhere,100);
		}
	}
	public void changeEdge(int p_st,int p_nd,int Weight){
		for(Edge e:AdjList[p_st])
			if(e.toWhere == p_nd)
				e.weight = Weight;
		for(Edge e:AdjList[p_nd])
			if(e.toWhere == p_st)
				e.weight = Weight;
	}
	public void Dijkstra(){
		PriorityQueue<Node> NodeQ = new PriorityQueue<Node>(10,NodeComparator);
		NodeQ.add(new Node(source,0));
		while(NodeQ.size() > 0){
			Node head = NodeQ.poll();
			for(Edge e:AdjList[head.place]){
				if(e.weight + head.distance < Distance[e.toWhere]){
					Distance[e.toWhere] = e.weight + Distance[head.place]
					NodeQ.add(new Node(e.toWhere,Distance[e.toWhere]));
				}
			}
		}
	}
	private int minDist(int x, int y){
		int min = 10000;
		if(min > Distance[EdgeNum(x,y,Vertical)]) min = Distance[EdgeNum(x,y,Vertical)];
		if(min > Distance[EdgeNum(x,y + 1,Vertical)]) min = Distance[EdgeNum(x,y + 1,Vertical)];
		if(min > Distance[EdgeNum(x,y,Horizontal)]) min = Distance[EdgeNum(x,y,Horizontal)];
		if(min > Distance[EdgeNum(x + 1,y,Horizontal)]) min = Distance[EdgeNum(x + 1,y,Horizontal)];
		return min;	
	}
	private boolean validEdge(int _x,int _y,boolean isVertical){
		return ((isVertical &&( _x < 0 || _x > Row || _y < 0 || _y > Column + 1))|| ((!isVertical) && (_x < 0 || _x > (Row + 1) || _y < 0 || _y > Column)));
	}

	private int EdgeNum(int _x,int _y, boolean isVertical){
		int toReturn;
		if(isVertical == Vertical)
			toReturn = (_x + (_y * Column + 1) + Column * (Row + 1);
		if(isVertical == Horizontal)
			toReturn = (_x + (_y * Column ));
		return toReturn;
	}
}

public class AIPlayer extends Player{
	//0:Saboteur , 1:Miner
	int[] otherRole;
	GamerStatus[] gamer;
	int player_num;//how many player
	public AIPlayer(String _name,int _role,int _num,int p_num){
		super(_name,_role,_num);
		isAI = true;
		player_num = p_num;
		otherRole = new int[p_num];
		for(int i = 0; i < p_num; i++)
			otherRole[i] = 1;
		gamer = new GamerStatus[p_num];
		for(int i = 0; i < p_num; i ++)
			gamer[i] = new GamerStatus();
	}
	public AIPlayer(String _name,String _role,int _num,int p_total){
		super(_name,_role,_num);
		isAI = true;
		player_num = p_total;
		otherRole = new int[p_total];
		for(int i = 0; i < p_total; i++)
			otherRole[i] = 1;
		gamer = new GamerStatus[p_total];
		for(int i = 0; i < p_total; i ++)
			gamer[i] = new GamerStatus();
	}
	public void updateSituation(Action act){
		if(act.getIsOnMap())
			MapAction(act);
		else if(act.getIsFunction())
			OtherAction(act);
	}
	private void MapAction(Action act){
		//if already know the identity of the actioner update map
		int who = act.getFromWho();
		//else, make the guessing
		/*
		 if(!map.closeToAny()){
		 	gamer[who].definitely_sab();
		 }
		 else if(map.closeToAll()){
		 	gamer[who].maybe_miner();
		 }
		 else if(gamer[who].knowing == 0){
		 	if(map.closeToTwo())
				gamer[who].maybe_miner();
			else
				gamer[who].maybe_sab();
		 }
		 else if(gamer[who].isKnowing()){
		 	if(gamer[who].isPossibleMiner())
				maybeWhereIsGold = map.toWhichDestination();
		 }
		 if(map.flipOn()  > 0)
		 	FlushEveryOne's knowing
		map.update();
		 */

		//update the map
		//map.update();
	}
	private void OtherAction(Action act){

		//if action id watch
		//if the act is attack
		//if the act is save
	}
}
