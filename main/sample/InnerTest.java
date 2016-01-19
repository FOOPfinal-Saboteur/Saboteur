package sample;

import java.util.*;

public class InnerTest{
	public static void main(String[] args){
		InnerMap inner = new InnerMap(10,5);
		inner.printMap();
		Scanner scanner = new Scanner(System.in);
		int x,y;
		String cmd = new String();
		while(true){
			System.out.println("Action:(place/try/break/map/edge/bye)");
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
				boolean rot = false;
				while(ro.charAt(0) == 'y'){
					rot = !rot;
					card.rotate();
					System.out.println(card);
					System.out.println("Still wanna rotate?(y/n)");
					ro = scanner.next();
				}
				System.out.println("Where to place:");
				x = scanner.nextInt();
				y = scanner.nextInt();
				inner.receiveCard(x,y,new Card(kind,rot));
				inner.printMap();

			}
			else if(cmd.equals("try")){
				System.out.println("What road you want:");
				System.out.println("break/intersection/longT/shortT/longI/shortI");
				System.out.println("LeftTop/RightTop/fullblock/longTblock/shortTblock");
				System.out.println("LeftTopblock/RightTopblock/longDeadEnd/shortDeadEnd");
				String kind = scanner.next();
				if(!kind.equals("break")){
					RoadCard card = new RoadCard(kind);
					if(!card.getSuccess()){
						System.out.println("no what you want");
						continue;
					}
					System.out.println(card);
					System.out.println("Wanna rotate?(y/n)");
					String ro = scanner.next();
					boolean rot = false;
					while(ro.charAt(0) == 'y'){
						rot = !rot;
						card.rotate();
						System.out.println(card);
						System.out.println("Still wanna rotate?(y/n)");
						ro = scanner.next();
					}
					System.out.println("Where to place:");
					x = scanner.nextInt();
					y = scanner.nextInt();
					inner.tryCard(x,y,new Card(kind,rot));
				}
				else{
					System.out.println("Where to break:");
					x = scanner.nextInt();
					y = scanner.nextInt();
					inner.tryCard(x,y,new Card("collapse","lalal"));
				
				}
				inner.printMap();

			}
			else if (cmd.equals("break")){
				System.out.println("Where to break:");
				x = scanner.nextInt();
				y = scanner.nextInt();
				inner.receiveCard(x,y,new Card("collapse","lalala"));
				inner.printMap();
			}
			else if(cmd.equals("map"))
				inner.printMap();
			else if(cmd.equals("edge"))
				System.out.println(inner);
			else if(cmd.equals("bye"))
				break;
			else
				continue;
		}
		

	}
}
