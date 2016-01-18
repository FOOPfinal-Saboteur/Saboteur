import java.lang.*;
import java.util.*;
import java.io.*;

public class GamerStatus extends ActiveStatus{
	private boolean watch_top; //0
	private boolean watch_mid; //1
	private boolean watch_btm; //2
	private int g_knowing;//watch how many card
	private int g_miner;//sab:-100 1 2 /miner:3\ 4 5 100
	GamerStatus(){
		super();
		watch_top = false;
		watch_mid = false;
		watch_btm = false;
		g_knowing = 0;
		g_miner = 3;
	}
	public boolean isPossibleMiner(){return g_miner >= 3;}
	public boolean isPossibleKnowing(){return g_knowing >= 2;}
	public boolean knowTop(){return watch_top;}
	public boolean knowMid(){return watch_mid;}
	public boolean knowBtm(){return watch_btm;}
	public int knowHowMany(){return g_knowing;}
	public boolean isDefinitelyMiner(){return g_miner >= 100;}
	public boolean isDefinitelySab(){return g_miner <= -100;}

	public void destory_other(Card card,GamerStatus other){}
	public void fix_other(Card card,GamerStatus other){}
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
	public void definitely_sab(){g_miner = -100;}
	public void definitely_miner(){g_miner = 100;}
	public void rejudge(){g_miner = 3;}
}
