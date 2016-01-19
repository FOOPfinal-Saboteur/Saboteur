import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by leinadshih on 1/19/16.
 */
public class Main {
    static int playerNumber, aiNumber;
    static ArrayList<String> playerName = new ArrayList<String>();
    static ArrayList<Integer> roles = new ArrayList<Integer>();
    static String[] rolename = {"Saboteur", "Miner"};

    public static void randomAIName(int ainum, ArrayList<String> names){
        /*ArrayList<String> AInames = new ArrayList<String>(Arrays.asList("Peter", "Andrew", "James", "John",
                "Philip", "Nathanael", "Matthew", "Thomas", "Simon", "Thaddaeus"));*/


        ArrayList<String> AInamelist = new ArrayList<String>();

        try {
            File file = new File(System.getProperty("user.dir")+"/src/sample/name.txt");
            Scanner input = new Scanner(file);

            while (input.hasNextLine()) {
                String line = input.nextLine();
                AInamelist.add(line);
            }
            input.close();

            Random ran = new Random();
            int index;
            for (int i = 0; i < ainum; i++) {
                index = ran.nextInt(AInamelist.size());
                names.add("[AI]" + AInamelist.get(index));
                AInamelist.remove(index);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void randomSetRoles(int plnum, ArrayList<Integer> roleset){
        int saboteur = 0, miner = 0;
        switch (plnum){
            case 3: saboteur = 1; miner = 3; break;
            case 4: saboteur = 1; miner = 4; break;
            case 5: saboteur = 2; miner = 4; break;
            case 6: saboteur = 2; miner = 5; break;
            case 7: saboteur = 3; miner = 5; break;
            case 8: saboteur = 3; miner = 6; break;
            case 9: saboteur = 3; miner = 7; break;
            case 10: saboteur = 4; miner = 7; break;
            default: System.out.println("Invalid player number"); break;
        }

        ArrayList<Integer> rolecardlist = new ArrayList<Integer>();
        for(int i = 0; i < saboteur; i++) rolecardlist.add(0);
        for(int i = 0; i < miner; i++) rolecardlist.add(1);

        Random ran = new Random();
        int index;
        for(int i = 0; i < plnum; i++){
            index = ran.nextInt(rolecardlist.size());
            roleset.add(rolecardlist.get(index));
            rolecardlist.remove(index);
        }
    }

    public static boolean isValid(Action now, Map map, Player[] players){
	System.out.println(now);
	if(!now.getIsOnMap() && !now.getIsFunction())
		return true;
        if(now.getIsOnMap()){
           if(now.getIsFunction() && now.getCard().Function().isCollapse()
               && map.breakRoad(now.getToWhere().X(), now.getToWhere().Y())){
               return true;
           }else if(map.placeRoad(now.getCard().Road(), now.getToWhere().X(), now.getToWhere().Y())){
               return true;
           }
        }else if(now.getIsFunction()){
            if(now.getCard().Function().isMap()){
                boolean b = map.haveGold(now.getToWhere().Y());
                DestinyStatus ds = new DestinyStatus(2 - now.getToWhere().Y()/2, b);
                players[now.getFromWho()].watchMap(ds);
                return true;
            }else if(now.getCard().Function().isBreak()){
                return players[now.getToWhom()].destroy(now.getCard().Function().kindStr()); // index
            }else if(now.getCard().Function().isFix()){
                return players[now.getToWhom()].fix(now.getCard().Function().kindStr()); // index
            }
        }
        return false;
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        playerNumber = 3;
        aiNumber = 3;
        for(int i = 0; i < playerNumber-aiNumber; i++){
            playerName.add(scanner.next());
        }
        randomAIName(aiNumber, playerName);
        randomSetRoles(playerNumber, roles);
        /* init Player */
        Player[] player = new Player[playerNumber];
        for(int i = 0; i < playerNumber-aiNumber; i++){
            player[i] = new Player(playerName.get(i), roles.get(i), 6);
        }
        /* init AI player */
        for(int i = playerNumber-aiNumber; i < playerNumber; i++){
            player[i] = new AIPlayer(playerName.get(i), roles.get(i), 6, playerNumber, i);
        }

        for(int rnd = 1; rnd <= 3; rnd++){
            System.out.println("***********Round "+rnd+" *****************");

            Deck deck = new Deck();
            Map map = new Map();
            /* int player hand */
            for(int i = 0; i < playerNumber-aiNumber; i++){
                ArrayList<Card> tmp = new ArrayList<Card>();
                for(int j = 0; j < 6; j++){
                    tmp.add(deck.giveACard());
                }
                player[i].setHand(tmp);
            }
            /* init AI player hand */
            for(int i = playerNumber-aiNumber; i < playerNumber; i++){
                ArrayList<Card> tmp = new ArrayList<Card>();
                for(int j = 0; j < 6; j++){
                    tmp.add(new Card("shortT"));
                }
                player[i].setHand(tmp);
            }
            /* print out init status */
            for(int i = 0; i < playerNumber; i++) {
                System.out.println("Player " + i + ": " + playerName.get(i));
                System.out.println("Role: "+rolename[roles.get(i)]);
                for(int j = 0; j < 6; j++){
                    System.out.println(player[i].getCard(j).toString());
                }
            }
            System.out.println("***********Start*****************");
            /* take action */
            int baredHand = 0;
            int nowPlayer = 0;
            while(deck.theDeck.size() != 0 || baredHand < playerNumber){
                boolean valid = false;
                nowPlayer = (nowPlayer + 1) % playerNumber;
                Action nowAction = new Action();

                while(!valid){
                    // player's action
                    if(player[nowPlayer].isAI()){
                        AIPlayer tmp = (AIPlayer) player[nowPlayer];
                        nowAction = tmp.makeDecision();
                    }else{
                        /*  */
                    }
                    /* server action */
                    valid = isValid(nowAction, map, player);
		    System.out.println(map);
		    System.out.println(player[nowPlayer]);
                }
            }

        }
    }
}
