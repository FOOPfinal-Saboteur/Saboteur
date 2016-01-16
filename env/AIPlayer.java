import java.lang.*;
import java.util.*;
import java.io.*;

class MyMap{

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
