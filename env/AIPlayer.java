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
	public int HowmanyFarther(){
		int num = 0;
		if(TopChange < 0) num ++;
		if(MidChange < 0) num ++;
		if(BtmChange < 0) num ++;
		return num;
	}
	public int HowmanyCloser(){
		int num = 0;
		if(TopChange > 0) num ++;
		if(MidChange > 0) num ++;
		if(BtmChange > 0) num ++;
		return num;
	}
	public boolean closerToTop(){ return TopChange > 0;}
	public boolean closerToMid(){ return MidChange > 0;}
	public boolean closerToBtm(){ return BtmChange > 0;}
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
		if( (card.getBind(1) && Distance[EdgeNum(x,y,Vertical)] >= 100) || (!card.getBind(1) && Distance[EdgeNum(x,y,Vertical)] == 0))
			return false;	
		if( (card.getBind(3) && Distance[EdgeNum(x + 1,y,Vertical)] >= 100) || (!card.getBind(3) && Distance[EdgeNum(x + 1,y,Vertical)]== 0))
			return false;	
		if( (card.getBind(2) && Distance[EdgeNum(x,y,Horziontal)] >= 100) || (!card.getBind(2) && Distance[EdgeNum(x,y,Horziontal)] == 0))
				return false;		
		if( (card.getBind(0) && Distance[EdgeNum(x,y + 1,Horziontal)] >= 100) || (!card.getBind(0) && Distance[EdgeNum(x,y + 1,Horziontal)] == 0))
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
		RoadCard origin;
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
		minT = minDist(9,4);
		minM = minDist(9,2);
		minB = minDist(9,0);
		WhatHappen toReturn = new WhatHappen(minT - originT, minM - originM, minB - originB);
		if(card.IsFunction()){
			assignCard(x,y,origin);
			Dijkstra();
			minT = minDist(9,4);
			minM = minDist(9,2);
			minB = minDist(9,0);
		}
		else if(card.IsRoad()){
			breakRoad(x,y);
			Dijkstra();
			minT = minDist(9,4);
			minM = minDist(9,2);
			minB = minDist(9,0);
		}
		return toReturn;
	//	if()
	}
	public boolean flipOnTop(){return minT == 0;}
	public boolean flipOnMid(){return minM == 0;}
	public boolean flipOnBtm(){return minB == 0;}
	public RoadCard OriginCard(int x,int y){
		boolean[] bind = new boolean[4];
		for(int i = 0; i < 4; i++)
			bind[i] = false;
		boolean[] connect =  new boolean[6];
		for(int i = 0; i < 6; i ++)
			connect[i] = false;
		if(Distance[EdgeNum(x,y + 1,Horizontal)] < 100)
			connect[0] = true;
		if(Distance[EdgeNum(x,y,Vertical)] < 100)
			connect[1] = true;
		if(Distance[EdgeNum(x,y,Horizontal)] < 100)
			connect[2] = true;
		if(Distance[EdgeNum(x + 1,y,Vertical)] < 100)
			connect[3] = true;
		if(EdgeWeight(EdgeNum(x,y+1,Horizontal),EdgeNum(x,y,Vertical)) < 100)
			bind[0] =true;
		if(EdgeWeight(EdgeNum(x,y,Horizontal),EdgeNum(x,y,Vertical)) < 100)
			bind[1] =true;
		if(EdgeWeight(EdgeNum(x,y,Horizontal),EdgeNum(x+1,y,Vertical)) < 100)
			bind[2] =true;
		if(EdgeWeight(EdgeNum(x,y+1,Horizontal),EdgeNum(x+1,y,Vertical)) < 100)
			bind[3] =true;
		if(EdgeWeight(EdgeNum(x,y+1,Horizontal),EdgeNum(x,y,Horizantal)) < 100)
			bind[4] =true;
		if(EdgeWeight(EdgeNum(x+1,y,Vertical),EdgeNum(x,y,Vertical)) < 100)
			bind[6] =true;
		return new RoadCard(bind,connect,false);

	}
	public void assignCard(int x, int y,RoadCard card){
		if(!card.getBind(0)) isolate(EdgeNum(x,y+1,Horizontal));
		if(!card.getBind(1)) isolate(EdgeNum(x,y,Vertical));
		if(!card.getBind(2)) isolate(EdgeNum(x,y,Horizontal));
		if(!card.getBind(3)) isolate(EdgeNum(x + 1,y,Vertical));
		(card.getConnect(0)) ? changeEdge(EdgeNum(x,y+1,Horizontal),EdgeNum(x,y,Vertical),0):changeEdge(EdgeNum(x,y+1,Horizontal),EdgeNum(x,y,Vertical),100);
		(card.getConnect(1)) ? changeEdge(EdgeNum(x,y,Horizontal),EdgeNum(x,y,Vertical),0):changeEdge(EdgeNum(x,y,Horizontal),EdgeNum(x,y,Vertical),100);
		(card.getConnect(2)) ? changeEdge(EdgeNum(x,y,Horizontal),EdgeNum(x + 1,y,Vertical),0):changeEdge(EdgeNum(x,y,Horizontal),EdgeNum(x + 1,y,Vertical),100);
		(card.getConnect(3)) ? changeEdge(EdgeNum(x,y+1,Horizontal),EdgeNum(x+1,y,Vertical),0):changeEdge(EdgeNum(x,y+1,Horizontal),EdgeNum(x+1,y,Vertical),100);
		(card.getConnect(4)) ? changeEdge(EdgeNum(x,y+1,Horizontal),EdgeNum(x,y,Horizontal),0):changeEdge(EdgeNum(x,y+1,Horizontal),EdgeNum(x,y,Horizontal),100);
		(card.getConnect(5)) ? changeEdge(EdgeNum(x+1,y,Vertical),EdgeNum(x,y,Vertical),0):changeEdge(EdgeNum(x+1,y,Vertical),EdgeNum(x,y,Vertical),100);
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
	private int EdgeWeight(int p_st,int p_nd){
		for(Edge e:AdjList[p_st]){
			if(e.toWhere == p_nd)
				return e.weight;
		}
		return 1010;
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
	boolean maybe_where;//0  for top, 2 for mid, 3 for btm
	InnerMap myMap;
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
		myMap = new InnerMap(10,5);
		maybe_where = new boolean[3];
		for(int i = 0; i < 3; i ++)
			maybe_where[i] = true;
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
		myMap = new InnerMap(10,5);
		maybe_where = new boolean[3];
		for(int i = 0; i < 3; i ++)
			maybe_where[i] = true;
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
		WhatHappen what;
		what = myMap.receiveCard(act.getToWhere().X(),act.getToWhere.Y(),act.getCard());
		 if(what.HowmanyCloser() == 0 && what.HowmanyFarther() > 0){
		 	gamer[who].definitely_sab();
		 }
		 else if(what.HowmanyCloser() == 3){
		 	gamer[who].maybe_miner();
		 }
		 else if(gamer[who].knowHowMant()== 0){
		 	if(what.HowmanyCloser() >= 2 || (what.HowmanyCloser() >= 1 && what.HowmanyFather == 0))
				gamer[who].maybe_miner();
			else
				gamer[who].maybe_sab();
		 }
		 else if(gamer[who].knowHowMany() == 1){
		 	if(gamer[who].isDefinitelyMiner()){
				if(!what.closerToTop())
					maybe_where[0] = false;
				if(!what.closerToMid())
					maybe_where[1] = false;
				if(!what.closerToBtm())
					maybe_where[2] = false;
			}
		 }
		 else if(gamer[who].knowHowMany() >= 2){
		 	if(gamer[who].isDefinitelyMiner()){
				if(!what.closerToTop())
					maybe_where[0] = false;
				if(!what.closerToMid())
					maybe_where[1] = false;
				if(!what.closerToBtm())
					maybe_where[2] = false;
			}
			else if(maybe_where[0] && what.closerToTop()){
				gamer[who].maybe_miner();
			}
			else if(maybe_where[1] && what.closerToMid()){
				gamer[who].maybe_miner();
			}
			else if(maybe_where[2] && what.closerToBtm()){
				gamer[who].maybe_miner();
			}
			else
				gamer[who].definitely_sab();
		 }
		 if(map.flipOnTop()){
			 maybe_where[0] = false;
			 for(int i = 0; i < player_num;i ++)
				 gamer[i].watchMap(0);
		 }
		 if(map.flipOnMid()){
			 maybe_where[1] = false;
			 for(int i = 0; i < player_num;i ++)
				 gamer[i].watchMap(1);
		 }
		 if(map.flipOnBtm()){
			 maybe_where[2] = false;
			 for(int i = 0; i < player_num;i ++)
				 gamer[i].watchMap(2);
		 }
		 //if(map.flipOn())
		//update the map
		//map.update();
	}
	private void OtherAction(Action act){
		FunctionCard func = act.getFunctionCard;
		int who = act.getFromWho();
		//if action id watch
		if(func.isMap()){
			if(act.getToWhere.Y() == 4)
				gamer[who].watchMap(0);
			if(act.getToWhere.Y() == 2)
				gamer[who].watchMap(1);
			if(act.getToWhere.Y() == 0)
				gamer[who].watchMap(2);
		}
		//if the act is attack
		int whom = act.getToWhom();
		if(func.isBreak()){
			gamer[whom].destory(func.kindStr());
			if(gamer[whom].isPossibleMiner())
				gamer[who].maybe_sab();
			else
				gamer[who].maybe_miner();
		}
		if(func.isFix()){
			gamer[whom].fix(func.kindStr());
			if(gamer[whom].isPossibleMiner())
				gamer[who].maybe_miner();
			else
				gamer[who].maybe_sab();
		}
		//if the act is save
	}
}
