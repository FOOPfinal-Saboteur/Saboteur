package sample;
import java.lang.*;
import java.util.*;
import java.io.*;

public class RoadCard{

	/* Member variable */
	private boolean[] bind = new boolean[4];
		/* 0:Top, 1:Left, 2:Bottom, 3:Right */
	private boolean[] connect = new boolean[6];
		/* 0:LeftTop, 1:LeftBottom, 3:RightBottom
		   4:RightTop, 5:Vertical, 6:Horizontal */
	/* Constructor */
	private boolean isRotate;
	public RoadCard(boolean[] b, boolean[] c,boolean rotate){
		for(int i = 0; i < 4; i++)
			bind[i] = b[i];
		for(int i = 0; i < 6; i++)
			connect[i] = c[i];
		isRotate = false;
		if(rotate){
			isRotate = true;
			rotate();
		}
		
	}
	public RoadCard(RoadCard old){
		for(int i = 0; i < 4; i ++)
			bind[i] = old.getBind(i);
		for(int i = 0; i < 6; i ++)
			connect[i] = old.getConnect(i);
		isRotate = old.getRotate();
	}
	public RoadCard(String cmd,boolean rotate){
		RoadCard tmp = new RoadCard(cmd);
		for(int i = 0; i < 4; i ++)
			bind[i] = tmp.getBind(i);
		for(int i = 0; i < 6; i ++)
			connect[i] = tmp.getConnect(i);
		isRotate = rotate;
		if(isRotate)
			rotate();
	}
	public RoadCard(String cmd){
		boolean _O = true;
		boolean _X = false;
		boolean[][] _bind = new boolean[16][];
		boolean[][] _connect = new boolean[16][];
		int _case = -1;

			/* Connected */
			/* 4-Bound */
			if(cmd.equals("intersection")){
				_bind[0] = new boolean[]{_O, _O, _O, _O};
				_connect[0] = new boolean[]{_O, _O, _O, _O, _O, _O};
				_case = 0;
			}
			/* 3-Bound */
			else if(cmd.equals("longT")){
				_bind[1] = new boolean[]{_O, _O, _O, _X};
				_connect[1] = new boolean[]{_O, _O, _X, _X, _O, _X};
				_case = 1;
			}
			else if(cmd.equals("shortT")){
				_bind[2] = new boolean[]{_O, _O, _X, _O};
				_connect[2] = new boolean[]{_O, _X, _X, _O, _X, _O};
				_case = 2;
			}
			/* 2-Bound */
			else if(cmd.equals("longI")){
				_bind[3] = new boolean[]{_O, _X, _O, _X};
				_connect[3] = new boolean[]{_X, _X, _X, _X, _O, _X};
				_case = 3;
			}
			else if(cmd.equals("shortI")){
				_bind[4] = new boolean[]{_X, _O, _X, _O};
				_connect[4] = new boolean[]{_X, _X, _X, _X, _X, _O};
				_case = 4;
			}
			else if(cmd.equals("LeftTop")){
				_bind[5] = new boolean[]{_O, _O, _X, _X};
				_connect[5] = new boolean[]{_O, _X, _X, _X, _X, _X};
				_case = 5;
			}
			else if(cmd.equals("RightTop")){
				_bind[6] = new boolean[]{_O, _X, _X, _O};
				_connect[6] = new boolean[]{_X, _X, _X, _O, _X, _X};
				_case = 6;
			}
			/* Blocked */
			/* 4-Bound */
			else if(cmd.equals("fullblock")){
				_bind[7] = new boolean[]{_O, _O, _O, _O};
				_connect[7] = new boolean[]{_X, _X, _X, _X, _X, _X};
				_case = 7;
			}
			/* 3-Bound */
			else if(cmd.equals("longTblock")){
				_bind[8] = new boolean[]{_O, _O, _O, _X};
				_connect[8] = new boolean[]{_X, _X, _X, _X, _X, _X};
				_case = 8;
			}
			else if(cmd.equals("shortTblock")){
				_bind[9] = new boolean[]{_O, _O, _X, _O};
				_connect[9] = new boolean[]{_X, _X, _X, _X, _X, _X};
				_case = 9;
			}
			/* 2-Bound */
			else if(cmd.equals("longIblock")){
				_bind[10] = new boolean[] {_O, _X, _O, _X};
				_connect[10] = new boolean[] {_X, _X, _X, _X, _X, _X};
				_case = 10;
			}
			else if(cmd.equals("shortIblock")){
				_bind[11] = new boolean[] {_X, _O, _X, _O};
				_connect[11] = new boolean[] {_X, _X, _X, _X, _X, _X};
				_case = 11;
			}
			else if(cmd.equals("LeftTopblock")){
				_bind[12] = new boolean[] {_O, _O, _X, _X};
				_connect[12] = new boolean[] {_X, _X, _X, _X, _X, _X};
				_case = 12;
			}
			else if(cmd.equals("RightTopblock")){
				_bind[13] = new boolean[] {_O, _X, _X, _O};
				_connect[13] = new boolean[] {_X, _X, _X, _X, _X, _X};
				_case = 13;
			}
			/* 1-Bound */
			else if(cmd.equals("longDeadEnd")){
				_bind[14] = new boolean[] {_O, _X, _X, _X};
				_connect[14] = new boolean[] {_X, _X, _X, _X, _X, _X};
				_case = 14;
			}
			else if(cmd.equals("shortDeadEnd")){
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
			isRotate = false;
	}

	/* Accessor */
	public boolean getSuccess(){
		for(int i = 0; i < 4; i ++)
			if(getBind(i))
				return true;
		return false;
	}
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
	public boolean getRotate(){return isRotate;}
	public boolean isBlock(){
		for(int i = 0; i < 6; i ++)
			if(connect[i])
				return false;
		return true;
	}
	/* Method */ 
	public void rotate(){
		boolean tmp;
		tmp = bind[0]; bind[0] = bind[2]; bind[2] = tmp;
		tmp = bind[1]; bind[1] = bind[3]; bind[3] = tmp;
		tmp = connect[0]; connect[0] = connect[2]; connect[2] = tmp;
		tmp = connect[1]; connect[1] = connect[3]; connect[3] = tmp;
		isRotate = !isRotate;
	}
	public String toString(){
		char[][] table = new char[3][3];
		for(int i = 0; i < 3; i ++)
			for(int j = 0; j < 3; j ++)
				table[i][j] = ' ';
		if(getConnect(0)) table[0][0] = '/';
		if(getConnect(1)) table[2][0] = '\\';
		if(getConnect(2)) table[2][2] = '/';
		if(getConnect(3)) table[0][2] = '\\';
		if(getConnect(4)){
			table[0][1] = '|';
			table[2][1] = '|';
		}
		if(getConnect(5)){
			table[1][0] = '-';
			table[1][2] = '-';
		}
		if(!getBind(0)) table[0][1] = 'x';
		if(!getBind(1)) table[1][0] = 'x';
		if(!getBind(2)) table[2][1] = 'x';
		if(!getBind(3)) table[1][2] = 'x';
		if(isBlock())
			table[1][1] = 'b';

		String toReturn = "";
		for(int i = 0; i <3; i++){
			String str = new String(table[i]);
			toReturn = toReturn.concat(str + "\n");
		}
		return toReturn;
	}
}
