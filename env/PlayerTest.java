import java.util.Scanner;


public class PlayerTest{
	public static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args){
		Deck deck = new Deck();
		Player player = new Player("john","Saboteur",5);
		Map map = new Map();
		for(int i = 0; i < 5; i ++){
//			Card card = deck.giveACard();
//			System.out.println(card);
			player.assignCard(deck.giveACard());
		}
		System.out.println(player);
		String cmd ="";
		while(true){
			System.out.println("Action?(road/bye)");
			cmd = scanner.next();
			if(cmd.equals("bye"))
				break;
			else if(cmd.equals("road")){
				do_road(deck,player,map);
			}
		}
	}
	public static void do_road(Deck deck,Player player,Map map){
		int c_num;
		String cmd;
		System.out.println(map);
		System.out.println(player);
		do{
			System.out.println("Rotate Card?(9 for quit)");
			c_num = scanner.nextInt();
			if(c_num == 9)
				break;
			player.rotateCard(c_num);
			System.out.println(player);
		}while(true);
		System.out.println(player);
		System.out.println(map);
		System.out.println("Where to put?");
		int x = scanner.nextInt();
		int y = scanner.nextInt();

		System.out.println("Which card?");
		c_num = scanner.nextInt();
		Card c_out = player.getCard(c_num);
		while(!c_out.IsRoad()){
			System.out.println("not a road");
			c_num = scanner.nextInt();
			c_out = player.getCard(c_num);
		}
		System.out.println(c_out);
		if(!map.placeRoad(c_out.Road(),x,y)){
			System.out.println("cannot put");
			return;
		}
		player.removeCard(c_num);
		player.assignCard(deck.giveACard());
		System.out.println(map);
	}
}
