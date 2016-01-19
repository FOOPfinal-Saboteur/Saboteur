import java.lang.*;
import java.util.*;
import java.io.*;
public class ToWhere{
	private int x;
	private int y;
	ToWhere(int _x,int _y){
		x = _x;
		y = _y;
	}
	public int X(){return x;}
	public int Y(){return y;}
	public String toString(){
		return new String("(" +x+ ","+y+")");
	}
}
