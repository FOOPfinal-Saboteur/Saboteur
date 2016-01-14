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
				System.out.println("Where to place:");
				x = scanner.nextInt();
				y = scanner.nextInt();
				if(!map.placeRoad(card,x,y)){
					System.out.println("error");
					continue;
				}
				map.printMap();

			}
			else if (cmd.equals("break")){
				System.out.println("Where to break:");
				x = scanner.nextInt();
				y = scanner.nextInt();
				if(!map.breakRoad(x,y))
					System.out.println("error");
				map.printMap();
			}
			else if(cmd.equals("map"))
				map.printMap();
			else if(cmd.equals("bye"))
				break;
			else
				continue;
		}

	}
} 
