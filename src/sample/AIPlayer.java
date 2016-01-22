package sample;
import java.lang.*;
import java.util.*;
import java.io.*;

public class AIPlayer extends Player{
	//0:Saboteur , 1:Miner
	int[] otherRole;
	GamerStatus[] gamer;
	int player_num;//how many player
	boolean[] maybe_where;//0  for top, 2 for mid, 3 for btm
	boolean[] definitely_where;//0 top...
	static InnerMap myMap;
	public AIPlayer(String _name,int _role,int _num,int p_num,int me){
		super(_name,_role,_num,me);
		isAI = true;
		player_num = p_num;
		gamer = new GamerStatus[p_num];
		for(int i = 0; i < p_num; i ++)
			gamer[i] = new GamerStatus();
		myMap = new InnerMap(9,5);
		maybe_where = new boolean[3];
		for(int i = 0; i < 3; i ++)
			maybe_where[i] = true;
		definitely_where = new boolean[3];
		for(int i = 0; i < 3; i ++)
			definitely_where[i] = false;
	}
	public AIPlayer(String _name,String _role,int _num,int p_total,int me){
		super(_name,_role,_num,me);
		isAI = true;
		player_num = p_total;
		gamer = new GamerStatus[p_total];
		for(int i = 0; i < p_total; i ++)
			gamer[i] = new GamerStatus();
		myMap = new InnerMap(9,5);
		maybe_where = new boolean[3];
		for(int i = 0; i < 3; i ++)
			maybe_where[i] = true;
		definitely_where = new boolean[3];
		for(int i = 0; i < 3; i ++)
			definitely_where[i] = false;
	}
	public void updateSituation(Action act){
		if(act.getIsOnMap())
			MapAction(act);	
		else if(act.getIsFunction())
			OtherAction(act);
		else
			System.out.println("Discard card");
	}
	public boolean watchMap(DestinyStatus destiny){
		if(destiny.isGold)
			definitely_where[destiny.where] = true;
		else
			maybe_where[destiny.where] = false;
		return destiny.isGold;
	}
	private void MapAction(Action act){
		//if already know the identity of the actioner update map
		int who = act.getFromWho();
		//else, make the guessing
		WhatHappen what;
		what = myMap.receiveCard(act.getToWhere().X(),act.getToWhere().Y(),act.getCard());
		 if(what.HowmanyCloser() == 0 && what.HowmanyFarther() > 0){
		 	gamer[who].definitely_sab();
		 }
		 else if(what.HowmanyCloser() == 3){
		 	gamer[who].maybe_miner();
		 }
		 else if(gamer[who].knowHowMany()== 0){
		 	if(what.HowmanyCloser() >= 2 || (what.HowmanyCloser() >= 1 && what.HowmanyFarther() == 0))
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
		 if(myMap.flipOnTop()){
			 maybe_where[0] = false;
			 for(int i = 0; i < player_num;i ++)
				 gamer[i].watchMap(0);
		 }
		 if(myMap.flipOnMid()){
			 maybe_where[1] = false;
			 for(int i = 0; i < player_num;i ++)
				 gamer[i].watchMap(1);
		 }
		 if(myMap.flipOnBtm()){
			 maybe_where[2] = false;
			 for(int i = 0; i < player_num;i ++)
				 gamer[i].watchMap(2);
		 }
		 //if(map.flipOn())
		//update the map
		//map.update();
	}
	private void OtherAction(Action act){
		FunctionCard func = act.getFunctionCard();
		int who = act.getFromWho();
		//if action id watch
		if(func.isMap()){
			if(act.getToWhere().Y() == 4)
				gamer[who].watchMap(0);
			if(act.getToWhere().Y() == 2)
				gamer[who].watchMap(1);
			if(act.getToWhere().Y() == 0)
				gamer[who].watchMap(2);
		}
		//if the act is attack
		int whom = act.getToWhom();
		if(func.isBreak()){
			gamer[whom].destroy(func.kindStr());
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
	public Action makeDecision(){
		if(role == 0)//sab
			return SabAction();
		if(role == 1)
			return MinerAction();
		return null;
	}
	private Action MinerAction(){
		boolean removable[] = new boolean[hand.size()];
		for(int i = 0; i < hand.size(); i ++)
			removable[i] = false;
		int k = 0;
		for(Card c:hand){
//								if(myMap.minDist(8,4) == 500)
//									System.out.println("wrong!!");
			if(c.IsFunction() && c.Function().isMap()){
				int howManyIknow = 3;
				for(int i = 0;i < 3; i ++){
					if(definitely_where[i]){
						removable[k] = true;
						break;
					}
					if(maybe_where[i])
						howManyIknow --;
				}
				if(howManyIknow < 2){
					if(maybe_where[0]){
						hand.remove(k);
						return new Action(new Card("map","la"),8,4,my_num,0);
					}
					if(maybe_where[2]){
						hand.remove(k);
						return new Action(new Card("map","la"),8,0,my_num,0);
					}
					if(maybe_where[1]){
						hand.remove(k);
						return new Action(new Card("map","la"),8,2,my_num,0);
					}
				}
				removable[k] = true;
			}
			if(status.pickOK() && status.oil_lampOK() && status.mine_cartOK()){
				boolean look = true;
				if(!c.IsRoad())
					look = false;
				if(look && knowGold()){
					for(int x = 0; x < 9; x ++){
						for(int y = 0;y < 5; y ++){
							if(!myMap.canPut(x,y,c.Road()))
								c.rotateCard();
							if(myMap.canPut(x,y,c.Road())){
								WhatHappen wtf = myMap.tryCard(x,y,c);
								if(definitely_where[0] && wtf.closerToTop()){
									Card toRet = new Card(c);
									hand.remove(k);
									return new Action(new Card(toRet),x,y,my_num,0);
								}
								if(definitely_where[2] && wtf.closerToBtm()){
									Card toRet = new Card(c);
									hand.remove(k);
									return new Action(new Card(toRet),x,y,my_num,0);
								}
								if(definitely_where[1] && wtf.closerToMid()){
									Card toRet = new Card(c);
									hand.remove(k);
									return new Action(new Card(toRet),x,y,my_num,0);
								}
								c.rotateCard();
								if(myMap.canPut(x,y,c.Road())){
									wtf = myMap.tryCard(x,y,c);
									if(definitely_where[0] && wtf.closerToTop()){
										Card toRet = new Card(c);
										hand.remove(k);
										return new Action(new Card(toRet),x,y,my_num,0);
									}
									if(definitely_where[2] && wtf.closerToBtm()){
										Card toRet = new Card(c);
										hand.remove(k);
										return new Action(new Card(toRet),x,y,my_num,0);
									}
									if(definitely_where[1] && wtf.closerToMid()){
										Card toRet = new Card(c);
										hand.remove(k);
										return new Action(new Card(toRet),x,y,my_num,0);
									}
									c.rotateCard();

								}
								removable[k] = true;
							}
						}
					}
				}
				else if (look){
					boolean shouldR = false;
					int max = 0;
					int mX = 0,mY = 0;
					for(int x = 0; x < 9; x ++){
						for(int y = 0;y < 5; y ++){
							boolean rota = false;
							Card tmp = new Card(c);
							if(!myMap.canPut(x,y,tmp.Road())){
								rota = !rota;
								tmp.rotateCard();
							}
							if(myMap.canPut(x,y,tmp.Road())){
								int ret_rate = 3;
								WhatHappen wtf = myMap.tryCard(x,y,tmp);
//								if(myMap.minDist(8,4) == 500)
//									System.out.println("wrong!!");
								//System.out.println(x +","+ y+":" + wtf.HowmanyCloser());
								if(wtf.HowmanyCloser() == 3){
									Card toRet = new Card(tmp);
									hand.remove(tmp);
									return new Action(new Card(toRet),x,y,my_num,0);
								}
								ret_rate -= wtf.HowmanyFarther();
									
								if(maybe_where[0] && !wtf.closerToTop()){
									ret_rate --;
								}
								if(maybe_where[2] && !wtf.closerToBtm()){
									ret_rate --;
								}
								if(maybe_where[1] && !wtf.closerToMid()){
									ret_rate --;
								}
								if(ret_rate >= max){
									mX = x;
									mY = y;
									max = ret_rate;
									shouldR = rota;
								}
								tmp.rotateCard();
								rota = !rota;
								if(myMap.canPut(x,y,tmp.Road())){
									ret_rate = 3;
									wtf = myMap.tryCard(x,y,tmp);
									//System.out.println(x +","+ y+":" + wtf.HowmanyCloser());
									if(wtf.HowmanyCloser() == 3){
										Card toRet = new Card(tmp);
										hand.remove(k);
										return new Action(new Card(toRet),x,y,my_num,0);
									}
									ret_rate -= wtf.HowmanyFarther();

									if(maybe_where[0] && !wtf.closerToTop()){
										ret_rate --;
									}
									if(maybe_where[2] && !wtf.closerToBtm()){
										ret_rate --;
									}
									if(maybe_where[1] && !wtf.closerToMid()){
										ret_rate --;
									}
									if(ret_rate >= max){
										mX = x;
										mY = y;
										max = ret_rate;
										shouldR = rota;
									
									}
								}
							}
						}
					}
					if(max >= 1){
						Card toRet = new Card(c.getType(),shouldR);
						hand.remove(k);
						return new Action(new Card(toRet),mX,mY,my_num,0);
					}
					removable[k] = true;

				}
			}
			FunctionCard func;
			if(c.IsRoad())
				removable[k] = true;
			else if(c.IsFunction() && c.Function().isCollapse()){
				if(knowGold()){
					for(int x = 0; x < 9; x ++){
						for(int y = 0;y < 5; y ++){
							if(myMap.shouldPut(x,y)){
								WhatHappen wtf = myMap.tryCard(x,y,c);
								if(definitely_where[0] && wtf.closerToTop()){
									Card toRet = new Card(c);
									hand.remove(k);
									return new Action(new Card(toRet),x,y,my_num,0);
								}
								if(definitely_where[2] && wtf.closerToBtm()){
									Card toRet = new Card(c);
									hand.remove(k);
									return new Action(new Card(toRet),x,y,my_num,0);
								}
								if(definitely_where[1] && wtf.closerToMid()){
									Card toRet = new Card(c);
									hand.remove(k);
									return new Action(new Card(toRet),x,y,my_num,0);
								}
								removable[k] = true;
							}
						}
					}
				}
				else{
				//	boolean shouldR = false;
					int max = 0;
					int mX = 0,mY = 0;
					for(int x = 0; x < 9; x ++){
						for(int y = 0;y < 5; y ++){
				//			boolean rota = false;
							if(myMap.shouldPut(x,y)){
								int ret_rate = 3;
								WhatHappen wtf = myMap.tryCard(x,y,c);
								//System.out.println(x +";"+ y+":" + wtf.HowmanyCloser());
								if(wtf.HowmanyCloser() == 3){
									Card toRet = new Card(c);
									hand.remove(k);
									return new Action(new Card(toRet),x,y,my_num,0);
								}
								if(maybe_where[0] && !wtf.closerToTop()){
									ret_rate --;
								}
								if(maybe_where[2] && !wtf.closerToBtm()){
									ret_rate --;
								}
								if(maybe_where[1] && !wtf.closerToMid()){
									ret_rate --;
								}
								if(ret_rate >= max){
									mX = x;
									mY = y;
									max = ret_rate;
//									shouldR = rota;
								}
							}
						}
					}
					if(max > 1){
						Card toRet = new Card(c.getType());
						hand.remove(k);
						return new Action(new Card(toRet),mX,mY,my_num,0);
					}
					removable[k] = true;

				}

			}
			else if(c.Function().isFix()){
				int kind = c.Function().itemKind();
				if((kind == 0 || kind == 3 || kind == 5) && !status.pickOK()){
					Card toRet = new Card("fix","pick");
					hand.remove(k);
					return new Action(new Card(toRet),-1,-1,my_num,my_num);
				}
				if((kind == 1 || kind == 4 || kind == 3) && !status.oil_lampOK()){
					Card toRet = new Card("fix","oil_lamp");
					hand.remove(k);
					return new Action(new Card(toRet),-1,-1,my_num,my_num);
				}
				if((kind == 2 || kind == 5 || kind == 4) && !status.mine_cartOK()){
					Card toRet = new Card("fix","mine_cart");
					hand.remove(k);
					return new Action(new Card(toRet),-1,-1,my_num,my_num);
				}
				if((kind == 0 || kind == 3 || kind == 5)){
					for(int i = 0; i < player_num; i ++){
						if(gamer[i].isPossibleMiner() && !gamer[i].pickOK()){
							Card toRet = new Card("fix","pick");
							hand.remove(k);
							return new Action(new Card(toRet),-1,-1,my_num,i);
						}
					}
				}
				if((kind == 1 || kind == 3 || kind == 4)){
					for(int i = 0; i < player_num; i ++){
						if(gamer[i].isPossibleMiner() && !gamer[i].oil_lampOK()){
							Card toRet = new Card("fix","oil_lamp");
							hand.remove(k);
							return new Action(new Card(toRet),-1,-1,my_num,i);
						}
					}
				}
				if((kind == 2 || kind == 5 || kind == 4)){
					for(int i = 0; i < player_num; i ++){
						if(gamer[i].isPossibleMiner() && !gamer[i].mine_cartOK()){
							Card toRet = new Card("fix","mine_cart");
							hand.remove(k);
							return new Action(new Card(toRet),-1,-1,my_num,i);
						}
					}
				}
				removable[k] = true;
			}
			else if(c.Function().isBreak()){
				int kind = c.Function().itemKind();
				for(int i = 0; i < player_num; i ++){
					if(kind == 0){
						if(!gamer[i].isPossibleMiner() && !gamer[i].pickOK()){
							Card toRet = new Card(c);
							hand.remove(k);
							return new Action(new Card(toRet),-1,-1,my_num,i);
						}
					}
					if(kind == 1){
						if(!gamer[i].isPossibleMiner() && !gamer[i].oil_lampOK()){
							Card toRet = new Card(c);
							hand.remove(k);
							return new Action(new Card(toRet),-1,-1,my_num,i);
						}
					}
					if(kind == 2){
						if(!gamer[i].isPossibleMiner() && !gamer[i].mine_cartOK()){
							Card toRet = new Card(c);
							hand.remove(k);
							return new Action(new Card(toRet),-1,-1,my_num,i);
						}
					}
				removable[k] = true;
				}
			}
			k ++;
		}
		for(int i = 0; i < hand.size(); i ++){
			if(removable[i]){
				hand.remove(i);
				return new Action(my_num,true);
			}
		}
		hand.remove(0);
			return new Action(my_num,true);
	}
	private boolean knowGold(){
		for(int i = 0; i < 3; i ++)
			if(definitely_where[i])
				return true;
		return false;
	}
	private Action SabAction(){
		boolean removable[] = new boolean[hand.size()];
		for(int i = 0; i < hand.size(); i ++)
			removable[i] = false;
		int k = 0;
		for(Card c:hand){
			if(c.IsFunction() && c.Function().isMap()){
				int howManyIknow = 3;
				for(int i = 0;i < 3; i ++){
					if(definitely_where[i]){
						removable[k] = true;
						break;
					}
					if(maybe_where[i])
						howManyIknow --;
				}
				if(howManyIknow < 1){
					if(maybe_where[0]){
						hand.remove(k);
						return new Action(new Card("map","la"),8,4,my_num,0);
					}
					if(maybe_where[2]){
						hand.remove(k);
						return new Action(new Card("map","la"),8,0,my_num,0);
					}
					if(maybe_where[1]){
						hand.remove(k);
						return new Action(new Card("map","la"),8,2,my_num,0);
					}
				}
				removable[k] = true;
			}
			if(status.pickOK() && status.oil_lampOK() && status.mine_cartOK()){
				boolean look = true;
				if(!c.IsRoad())
					look = false;
				if(look && knowGold()){
					for(int x = 0; x < 9; x ++){
						for(int y = 0;y < 5; y ++){
							if(!myMap.canPut(x,y,c.Road()))
								c.rotateCard();
							if(myMap.canPut(x,y,c.Road())){
								WhatHappen wtf = myMap.tryCard(x,y,c);
							//	System.out.println(x +"+"+ y+":" + wtf.HowmanyFarther());
								if(definitely_where[0] && wtf.fartherToTop()){
									Card toRet = new Card(c);
									hand.remove(k);
									return new Action(new Card(toRet),x,y,my_num,0);
								}
								if(definitely_where[2] && !wtf.fartherToBtm()){
									Card toRet = new Card(c);
									hand.remove(k);
									return new Action(new Card(toRet),x,y,my_num,0);
								}
								if(definitely_where[1] && !wtf.fartherToMid()){
									Card toRet = new Card(c);
									hand.remove(k);
									return new Action(new Card(toRet),x,y,my_num,0);
								}
								removable[k] = true;
							}
						}
					}
				}
				else if (look){
					for(int x = 0; x < 9; x ++){
						for(int y = 0;y < 5; y ++){
							if(!myMap.canPut(x,y,c.Road()))
								c.rotateCard();
							if(myMap.canPut(x,y,c.Road())){
								int ret_rate = 3;
								WhatHappen wtf = myMap.tryCard(x,y,c);
								if(maybe_where[0] && !wtf.fartherToTop()){
									ret_rate --;
								}
								if(maybe_where[2] && !wtf.fartherToBtm()){
									ret_rate --;
								}
								if(maybe_where[1] && !wtf.fartherToMid()){
									ret_rate --;
								}
								if(ret_rate >= 2){
									Card toRet = new Card(c);
									hand.remove(k);
									return new Action(new Card(toRet),x,y,my_num,0);
								}
								removable[k] = true;
							}
						}
					}

				}
			}
			FunctionCard func;
			if(c.IsRoad())
				removable[k] = true;
			else if(c.IsFunction() && c.Function().isCollapse()){
				if(knowGold()){
					for(int x = 0; x < 9; x ++){
						for(int y = 0;y < 5; y ++){
							if(myMap.shouldPut(x,y)){
								WhatHappen wtf = myMap.tryCard(x,y,c);
								if(definitely_where[0] && !wtf.closerToTop()){
									Card toRet = new Card(c);
									hand.remove(k);
									return new Action(new Card(toRet),x,y,my_num,0);
								}
								if(definitely_where[2] && !wtf.closerToBtm()){
									Card toRet = new Card(c);
									hand.remove(k);
									return new Action(new Card(toRet),x,y,my_num,0);
								}
								if(definitely_where[1] && !wtf.closerToMid()){
									Card toRet = new Card(c);
									hand.remove(k);
									return new Action(new Card(toRet),x,y,my_num,0);
								}
								removable[k] = true;
							}
						}
					}
				}
				else{
					for(int x = 0; x < 9; x ++){
						for(int y = 0;y < 5; y ++){
							if(myMap.shouldPut(x,y)){
								int ret_rate = 3;
								WhatHappen wtf = myMap.tryCard(x,y,c);
								if(maybe_where[0] && !wtf.closerToTop()){
									ret_rate --;
								}
								if(maybe_where[2] && !wtf.closerToBtm()){
									ret_rate --;
								}
								if(maybe_where[1] && !wtf.closerToMid()){
									ret_rate --;
								}
								if(ret_rate > 1){
									Card toRet = new Card(c);
									hand.remove(k);
									return new Action(new Card(toRet),x,y,my_num,0);
								}
								removable[k] = true;
							}
						}
					}
				}

			}
			else if(c.Function().isBreak()){
				int kind = c.Function().itemKind();
				for(int i = 0; i < player_num; i ++){
					if(kind == 0){
						if(gamer[i].isPossibleMiner() && gamer[i].pickOK() && i != my_num){
							Card toRet = new Card(c);
							hand.remove(k);
							return new Action(new Card(toRet),-1,-1,my_num,i);
						}
					}
					if(kind == 1){
						if(gamer[i].isPossibleMiner() && gamer[i].oil_lampOK() && i!= my_num){
							Card toRet = new Card(c);
							hand.remove(k);
							return new Action(new Card(toRet),-1,-1,my_num,i);
						}
					}
					if(kind == 2){
						if(gamer[i].isPossibleMiner() && gamer[i].mine_cartOK() && i!= my_num){
							Card toRet = new Card(c);
							hand.remove(k);
							return new Action(new Card(toRet),-1,-1,my_num,i);
						}
					}
				}
				removable[k] = true;
			}
			else if(c.Function().isFix()){
				int kind = c.Function().itemKind();
				if((kind == 0 || kind == 3 || kind == 5) && !status.pickOK()){
					Card toRet = new Card("fix","pick");
					hand.remove(k);
					return new Action(new Card(toRet),-1,-1,my_num,my_num);
				}
				if((kind == 1 || kind == 4 || kind == 3) && !status.oil_lampOK()){
					Card toRet = new Card("fix","oil_lamp");
					hand.remove(k);
					return new Action(new Card(toRet),-1,-1,my_num,my_num);
				}
				if((kind == 2 || kind == 5 || kind == 4) && !status.mine_cartOK()){
					Card toRet = new Card("fix","mine_cart");
					hand.remove(k);
					return new Action(new Card(toRet),-1,-1,my_num,my_num);
				}
				if((kind == 0 || kind == 3 || kind == 5)){
					for(int i = 0; i < player_num; i ++){
						if(!gamer[i].isPossibleMiner() && !gamer[i].pickOK()){
							Card toRet = new Card("fix","pick");
							hand.remove(k);
							return new Action(new Card(toRet),-1,-1,my_num,i);
						}
					}
				}
				if((kind == 1 || kind == 4 || kind == 3)){
					for(int i = 0; i < player_num; i ++){
						if(!gamer[i].isPossibleMiner() && !gamer[i].oil_lampOK()){
							Card toRet = new Card("fix","oil_lamp");
							hand.remove(k);
							return new Action(new Card(toRet),-1,-1,my_num,i);
						}
					}
				}
				if((kind == 2 || kind == 5 || kind == 4)){
					for(int i = 0; i < player_num; i ++){
						if(!gamer[i].isPossibleMiner() && !gamer[i].mine_cartOK()){
							Card toRet = new Card("fix","mine_cart");
							hand.remove(k);
							return new Action(new Card(toRet),-1,-1,my_num,i);
						}
					}
				}
				removable[k] = true;
			}
			k ++;
		}
		for(int i = 0; i < hand.size(); i ++)
			if(removable[i]){
				hand.remove(i);
				return new Action(my_num,true);
			}
		hand.remove(0);
		return new Action(my_num,true);
	}
}
