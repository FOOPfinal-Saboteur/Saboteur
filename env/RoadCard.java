import java.lang.*;
import java.util.*;
import java.io.*;

public class RoadCard extends Card{

	/* Member variable */
	private boolean[] bind = new boolean[4];
		/* 0:Top, 1:Left, 2:Bottom, 3:Right */
	private boolean[] connect = new boolean[6];
		/* 0:LeftTop, 1:LeftBottom, 3:RightBottom
		   4:RightTop, 5:Vertical, 6:Horizontal */

	/* Constructor */
	public RoadCard(boolean[] b, boolean[] c){
		for(int i = 0; i < 4; i++)
			bind[i] = b[i];
		for(int i = 0; i < 6; i++)
			connect[i] = c[i];
	}

	public RoadCard(String cmd){
		boolean _O = true;
		boolean _X = false;
		boolean[][] _bind = new boolean[16][];
		boolean[][] _connect = new boolean[16][];
		int _case = -1;

			/* Connected */
			/* 4-Bound */
			if(cmd == "intersection"){
				_bind[0] = new boolean[]{_O, _O, _O, _O};
				_connect[0] = new boolean[]{_O, _O, _O, _O, _O, _O};
				_case = 0;
			}
			/* 3-Bound */
			else if(cmd == "longT"){
				_bind[1] = new boolean[]{_O, _O, _O, _X};
				_connect[1] = new boolean[]{_O, _O, _X, _X, _O, _X};
				_case = 1;
			}
			else if(cmd == "shortT"){
				_bind[2] = new boolean[]{_O, _O, _X, _O};
				_connect[2] = new boolean[]{_O, _X, _X, _O, _X, _O};
				_case = 2;
			}
			/* 2-Bound */
			else if(cmd == "longI"){
				_bind[3] = new boolean[]{_O, _X, _O, _X};
				_connect[3] = new boolean[]{_X, _X, _X, _X, _O, _X};
				_case = 3;
			}
			else if(cmd == "shortI"){
				_bind[4] = new boolean[]{_X, _O, _X, _O};
				_connect[4] = new boolean[]{_X, _X, _X, _X, _X, _O};
				_case = 4;
			}
			else if(cmd == "LeftTop"){
				_bind[5] = new boolean[]{_O, _O, _X, _X};
				_connect[5] = new boolean[]{_O, _X, _X, _X, _X, _X};
				_case = 5;
			}
			else if(cmd == "RightTop"){
				_bind[6] = new boolean[]{_O, _X, _X, _O};
				_connect[6] = new boolean[]{_X, _X, _X, _O, _X, _X};
				_case = 6;
			}
			/* Blocked */
			/* 4-Bound */
			else if(cmd == "fullblock"){
				_bind[7] = new boolean[]{_O, _O, _O, _O};
				_connect[7] = new boolean[]{_X, _X, _X, _X, _X, _X};
				_case = 7;
			}
			/* 3-Bound */
			else if(cmd == "longTblock"){
				_bind[8] = new boolean[]{_O, _O, _O, _X};
				_connect[8] = new boolean[]{_X, _X, _X, _X, _X, _X};
				_case = 8;
			}
			else if(cmd == "shortTblock"){
				_bind[9] = new boolean[]{_O, _O, _X, _O};
				_connect[9] = new boolean[]{_X, _X, _X, _X, _X, _X};
				_case = 9;
			}
			/* 2-Bound */
			else if(cmd == "longIblock"){
				_bind[10] = new boolean[] {_O, _X, _O, _X};
				_connect[10] = new boolean[] {_X, _X, _X, _X, _X, _X};
				_case = 10;
			}
			else if(cmd == "shortIblock"){
				_bind[11] = new boolean[] {_X, _O, _X, _O};
				_connect[11] = new boolean[] {_X, _X, _X, _X, _X, _X};
				_case = 11;
			}
			else if(cmd == "LeftTopblock"){
				_bind[12] = new boolean[] {_O, _O, _X, _X};
				_connect[12] = new boolean[] {_X, _X, _X, _X, _X, _X};
				_case = 13;
			}
			else if(cmd == "RightTopblock"){
				_bind[13] = new boolean[] {_O, _X, _X, _O};
				_connect[13] = new boolean[] {_X, _X, _X, _X, _X, _X};
				_case = 13;
			}
			/* 1-Bound */
			else if(cmd == "longDeadEnd"){
				_bind[14] = new boolean[] {_O, _X, _X, _X};
				_connect[14] = new boolean[] {_X, _X, _X, _X, _X, _X};
				_case = 14;
			}
			else if(cmd == "shortDeadEnd"){
				_bind[15] = new boolean[] {_X, _O, _X, _X};
				_connect[15] = new boolean[] {_X, _X, _X, _X, _X, _X};
				_case = 15;
			}
			else {
				System.out.println("No such kind of RoadCard");
			}
			if(_case >= 0){
				for(int i = 0; i < 4; i++)
					bind[i] = _bind[_case][i];
				for(int i = 0; i < 6; i++)
					connect[i] = _connect[_case][i];
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
	public void swap(boolean a, boolean b){
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