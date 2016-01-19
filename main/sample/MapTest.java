package sample;
import java.util.Scanner;

public class  MapTest{
	public static void main(String[] args){
		Map map = new Map();
		String cmd;
		Scanner scanner = new Scanner(System.in);
		int x,y;
		while(true){
			System.out.println("Action:(place/break/map/bye)");
			cmd = scanner.next();
			if(cmd.equals("place")){
				System.out.println("What road you want:");
				System.out.println("intersection/longT/shortT/longI/shortI");
				System.out.println("LeftTop/RightTop/fullblock/longTblock/shortTblock");
				System.out.println("LeftTopblock/RightTopblock/longDeadEnd/shortDeadEnd");
				String kind = scanner.next();
				RoadCard card = new RoadCard(kind);
				if(!card.getSuccess()){
					System.out.println("no what you want");
					continue;
				}
				System.out.println(card);
				System.out.println("Wanna rotate?(y/n)");
				String ro = scanner.next();
				while(ro.charAt(0) == 'y'){
					card.rotate();
					System.out.println(card);
					System.out.println("Still wanna rotate?(y/n)");
					ro = scanner.next();
				}
				System.out.println("Where to place:");
				x = scanner.nextInt();
				y = scanner.nextInt();
				if(!map.placeRoad(card,x,y)){
					System.out.println("error");
					continue;
				}
				System.out.println(map);

			}
			else if (cmd.equals("break")){
				System.out.println("Where to break:");
				x = scanner.nextInt();
				y = scanner.nextInt();
				if(!map.breakRoad(x,y))
					System.out.println("error");
				System.out.println(map);
			}
			else if(cmd.equals("map"))
				System.out.println(map);
			else if(cmd.equals("bye"))
				break;
			else
				continue;
		}

	}
} 
