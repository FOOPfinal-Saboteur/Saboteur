import java.lang.*;
import java.util.*;
import java.io.*;

public class AIPlayer extends Player{
	//0:Saboteur , 1:Miner
	int[] otherRole;
	public AIPlayer(String _name,int _role,int _num,int p_num){
		super(_name,_role,_num);
		isAI = true;
		otherRole = new int[p_num];
		for(int i = 0; i < p_num; i++)
			otherRole[i] = 1;
	}
	public AIPlayer(String _name,String _role,int _num,int p_num){
		super(_name,_role,_num);
		isAI = true;
		otherRole = new int[p_num];
		for(int i = 0; i < p_num; i++)
			otherRole[i] = 1;
	}
	//watch what happen
	//decision making
	//
}
