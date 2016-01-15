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

	}
}
