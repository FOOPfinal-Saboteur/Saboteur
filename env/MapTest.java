import java.util.Scanner;

public class  MapTest{
	public static void main(String[] args){
		Map map = new Map();
		String cmd;
		Scanner scanner = new Scanner(System.in);
		int x,y;
		while(true){
			System.out.println("Action:(place//break/map)");
			cmd = scanner.next();
			if(cmd.equals("place")){
				System.out.println("What road you want:");
				String kind = scanner.next();
				RoadCard card = new RoadCard(kind);
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
			else
				break;

		}

	}
} 
