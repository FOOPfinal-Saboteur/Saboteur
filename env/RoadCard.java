import java.lang.*;
import java.util.*;
import java.io.*;

public class RoadCard{

	/* Member variable */
	protected boolean[] bind[4];
		/* 0:Top, 1:Left, 2:Bottom, 3:Right */
	protected boolean[] connect[6];
		/* 0:LeftTop, 1:LeftBottom, 3:RightBottom
		   4:RightTop, 5:Vertical, 6:Horizontal */

	/* Constructor */
	public RoadCard(int[] b[4], boolean[] c[6]){
		for(int i = 0; i < 4; i++)
			bind[i] = b[i];
		for(int i = 0; i < 6; i++)
			connect[i] = c[i];
	}

	public RoadCard(String cmd){
		private static final boolean _T = true;
		private static final boolean _F = false;
		switch(cmd){
			/* Connected */
			/* 4-Bound */
			case "intersection":
				bind = {_T, _T, _T, _T};
				connect = {_T, _T, _T, _T, _T, _T};
				break;
			/* 3-Bound */
			case "longT":
				bind = {_T, _T, _T, _F};
				connect = {_T, _T, _F, _F, _T, _F};
				break;
			case "shortT":
				bind = {_T, _T, _F, _T};
				connect = {_T, _F, _F, _T, _F, _T};
				break;
			/* 2-Bound */
			case "longI":
				bind = {_T, _F, _T, _F};
				connect = {_F, _F, _F, _F, _T, _F};
				break;
			case "shortI":
				bind = {_F, _T, _F, _T};
				connect = {_F, _F, _F, _F, _F, _T};
				break;
			case "LeftTop":
				bind = {_T, _T, _F, _F};
				connect = {_T, _F, _F, _F, _F, _F};
				break;
			case "RightTop":
				bind = {_T, _F, _F, _T};
				connect = {_F, _F, _F, _T, _F, _F};
				break;
			/* Blocked */
			/* 4-Bound */
			case "fullblock":
				bind = {_T, _T, _T, _T};
				connect = {_F, _F, _F, _F, _F, _F};
				break;
			/* 3-Bound */
			case "longTblock":
				bind = {_T, _T, _T, _F};
				connect = {_F, _F, _F, _F, _F, _F};
				break;
			case "shortTblock":
				bind = {_T, _T, _F, _T};
				connect = {_F, _F, _F, _F, _F, _F};
				break;
			/* 2-Bound */
			case "longIblock":
				bind = {_T, _F, _T, _F};
				connect = {_F, _F, _F, _F, _F, _F};
				break;
			case "shortIblock":
				bind = {_F, _T, _F, _T};
				connect = {_F, _F, _F, _F, _F, _F};
				break;
			case "LeftTopblock":
				bind = {_T, _T, _F, _F};
				connect = {_F, _F, _F, _F, _F, _F};
				break;
			case "RightTopblock":
				bind = {_T, _F, _F, _T};
				connect = {_F, _F, _F, _F, _F, _F};
				break;
			/* 1-Bound */
			case "longDeadEnd":
				bind = {_T, _F, _F, _F};
				connect = {_F, _F, _F, _F, _F, _F};
				break;
			case "shortDeadEnd":
				bind = {_F, _T, _F, _F};
				connect = {_F, _F, _F, _F, _F, _F};
				break;
			default:
				System.out.println("No such kind of RoadCard");
				break;
		}	
	}

	/* Accessor */
	public boolean getBind(int n){
		if(n >= 4 || n < 0){
			System.out.println("No such bind");
			return false;
		}else
			return bind[n];
	}

	public boolean getConnect(int n){
		if(n >= 6 || n < 0){
			System.out.println("No such connect");
			return false;
		}else
			return connect[n];
	}

	/* Method */ 
	public private swap(boolean a, boolean b){
		boolean tmp;
		tmp = a; a = b; b = tmp;
	}
	public void rotate(){
		swap(bind[0], bind[2]);
		swap(bind[1], bind[3]);
		swap(connect[0], connect[2]);
		swap(connect[1], connect[3]);
	}
}