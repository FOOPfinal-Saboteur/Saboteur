import java.lang.*;
import java.util.*;
import java.io.*;

public class GamerStatus extends ActiveStatus{
	private boolean watch_top; //0
	private boolean watch_mid; //1
	private boolean watch_btm; //2
	private int g_knowing;//watch how many card
	private int g_miner;//sab:0 1 2 /miner:3\ 4 5 6
	GamerStatus(){
		super();
		watch_top = false;
		watch_mid = false;
		watch_btm = false;
		g_knowing = 0;
		g_miner = 3;
	}
	public boolean isMiner(){return g_miner >= 3;}
	public boolean isKnowing(){return knowing >= 2;}
	public void destory_other(Card card,GameStatus other){}
	public void fix_other(Card card,GameStatus other){}
	public void watchMap(int watchWhich){
		if(watchWhich == 0){
			if(!watch_top){
				watch_top = true;
				g_knowing ++;
			}
		}
		else if(watchWhich == 1){
			if(!watch_mid){
				watch_mid = true;
				g_knowing ++;
			}
		}
		else if(watchWhich == 2){
			if(!watch_btm){
				watch_btm = true;
				g_knowing ++;
			}
		}
	}
	public void maybe_sab(){g_miner --;}
	public void maybe_miner(){g_miner ++;}
	public void definitely_sab(){g_miner = 0;}
	public void definitely_miner(g_miner = 6;)

}
