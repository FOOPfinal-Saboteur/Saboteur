import java.lang.*;
import java.util.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/*  Pseudo action rule */
	/* Use //= to begin in the code */

// #: frequent used action type
// 	#input: read in the arguments 
//	#ouput: print out some info
// 	#err: print our error msg/handle exception (for debugging)
//	#construct: only once since process begin
//	#init: initialization, maybe at the begin of a new round 
//	#click: click something clickable

// []: for some condition
//  [more]: about variant
//	[rand]: need of random factor

// @xxx: show relation
//	@@: parent, @child
//	example: line37 - #init xxx @@a, line52 - #_action @a 
// 	usage: you can use ctrl+F to find the key word

public class Saboteur{
	public static void main(String[] argv){
		/* Startup when the process begin */

		//= [more] #input: choose variant @@1
		//=  #imput: PLAYER number
		//= [more] #input: AI(BOT) num @@2  
		//=  #imput: PLAYER name
		//= #construct PLAYERs(include AI) @2
		//= #construct ROLE_LIST @@3
		//= #construct: DECK @1

		/* Startup when the round begin */

		//= FOR: round = 1~3
			//= #init DECK
			//= [rand] #init PLAYERs' roles @3
			//= [rand] #init: PLAYER HAND
			//= #output: show map, hand, role, status, msg_bar...

		/* Start to play */

			//= WHILE: there are CARDs on hand/deck
			//= or FOR: action = 1~deck_size

				/* Take action */

				//= WHILE: no valid action performed
					//= #click: select CARD or rotate_roadCARD
					//= IF: one CARD selected: 0:discard, 1:use
						//= 0: reassure, 1: check feasibility
						//=               .
						//=               .
						//=               .
						//= something important happen
						//=               .
						//=               .
						//=               .
				//= end WHILE

				/* After action */ 

				//= [rand] assign card
				//= #output: show change(map, hand, player_status)
				//= [more] #output: show action on msg_bar
				//= switch to next player

			//= end WHILE

		//= end FOR
	}
}