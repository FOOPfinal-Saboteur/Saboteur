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