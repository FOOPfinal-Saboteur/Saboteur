import java.lang.*;
import java.util.*;
import java.io.*;

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
