package sample;

import java.io.File;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by leinadshih on 1/19/16.
 */

public class Main {
    static int playerNumber, aiNumber, cardNumber;
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

    public static void printStatus(int id, String handstr){
            System.out.println("Player " + id + ": " + playerName.get(id));
            System.out.println("Role: "+rolename[roles.get(id)]);
            System.out.println(handstr);
    }

    public static boolean isValid(Action now, Map map, Player[] players, ActiveStatus[] sts){
        if(now.getIsOnMap()){
           if(now.getCard().IsFunction()
               && now.getCard().Function().isCollapse()){
               if(map.breakRoad(now.getToWhere().X(), now.getToWhere().Y())) {
                   return true;
               }
           }else if(sts[now.getFromWho()].isActive()
                   && map.placeRoad(now.getCard().Road(), now.getToWhere().X(), now.getToWhere().Y())){
               return true;
           }
        }else if(now.getIsFunction()){
            if(now.getCard().Function().isMap()){
                boolean b = map.haveGold(now.getToWhere().Y());
                DestinyStatus ds = new DestinyStatus(2 - now.getToWhere().Y()/2, b);
                players[now.getFromWho()].watchMap(ds);
                return true;
            }else if(now.getCard().Function().isBreak()){
                if(now.getCard().Function().itemKind() == 0 && sts[now.getToWhom()].pickOK()
                    || now.getCard().Function().itemKind() == 1 && sts[now.getToWhom()].oil_lampOK()
                    || now.getCard().Function().itemKind() == 2 && sts[now.getToWhom()].mine_cartOK()) {
                    System.out.println("Destroy!!!");
                    sts[now.getToWhom()].destroy(now.getCard().Function().kindStr());
                    System.out.println(sts[now.getToWhom()]);
                    return players[now.getToWhom()].destroy(now.getCard().Function().kindStr()); // index
                }
            }else if(now.getCard().Function().isFix()){
                if(now.getCard().Function().itemKind() == 0 && !sts[now.getToWhom()].pickOK()
                        || now.getCard().Function().itemKind() == 1 && !sts[now.getToWhom()].oil_lampOK()
                        || now.getCard().Function().itemKind() == 2 && !sts[now.getToWhom()].mine_cartOK()) {
                    System.out.println("Fix!!!");
                    sts[now.getToWhom()].fix(now.getCard().Function().kindStr());
                    return players[now.getToWhom()].fix(now.getCard().Function().kindStr()); // index
                }
            }
        }else {
            return true;
        }
        return false;
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Player #: ");
        playerNumber = scanner.nextInt();
        System.out.print("AI #: ");
        aiNumber = scanner.nextInt();
        if(playerNumber < 6){
            cardNumber = 6;
        }else if(playerNumber < 8){
            cardNumber = 5;
        }else{
            cardNumber = 4;
        }
        for(int i = 0; i < playerNumber-aiNumber; i++){
            System.out.print("Please enter name: ");
            playerName.add(scanner.next());
        }
        randomAIName(aiNumber, playerName);

        for(int rnd = 1; rnd <= 3; rnd++){

            randomSetRoles(playerNumber, roles);
            /* init Player */
            Player[] player = new Player[playerNumber];
            for(int i = 0; i < playerNumber-aiNumber; i++){
                player[i] = new Player(playerName.get(i), roles.get(i), cardNumber);
            }
            /* init AI player */
            for(int i = playerNumber-aiNumber; i < playerNumber; i++){
                player[i] = new AIPlayer(playerName.get(i), roles.get(i), cardNumber, playerNumber, i);
            }

            System.out.println("***********Round "+rnd+" *****************");

            Deck deck = new Deck();
            Map map = new Map();
            ActiveStatus[] sts = new ActiveStatus[playerNumber];
            for(int i = 0; i < playerNumber; i++) {
                sts[i] = new ActiveStatus();
            }
            /* int player hand */
            for(int i = 0; i < playerNumber; i++){
                ArrayList<Card> tmp = new ArrayList<Card>();
                for(int j = 0; j < cardNumber; j++){
                    tmp.add(deck.giveACard());
                }
                player[i].setHand(tmp);
            }
            /* print out init status */
            for(int i = 0; i < playerNumber; i++) {
                printStatus(i, player[i].showHand());
            }
            System.out.println("***********Start*****************");
            /* take action */
            int baredHand = 0;
            int nowPlayer = -1;
            Random ran = new Random();
            int winner = 0;
            boolean end = false;

            while((deck.theDeck.size() != 0 || baredHand < playerNumber) && !end){
                boolean valid = false;
                nowPlayer = (nowPlayer + 1) % playerNumber;
                System.out.println("*********Switch Player**********");
                System.out.println("It's "+playerName.get(nowPlayer)+"'s time!");
                Action nowAction = new Action();
                //System.out.println(player[nowPlayer].showHand());

                while(!valid){
                    // if now player have to action
                    if(!player[nowPlayer].stillHaveCard()){
                        break;
                    }
                    // player acting
                    int cardid = -1;
                    if(player[nowPlayer].isAI()){
                        AIPlayer tmp = (AIPlayer) player[nowPlayer];
                        nowAction = tmp.makeDecision();
                    }else{
                        printStatus(nowPlayer, player[nowPlayer].showHand());
                        System.out.print("Which card: ");
                        cardid = scanner.nextInt();
                        Card ctmp = player[nowPlayer].getCard(cardid);
                        if(ctmp.IsRoad()) {
                            System.out.print("Rotate: ");
                            if(scanner.next().equals("y")){
                                System.out.println("Rotate!!!");
                                ctmp.rotateCard();
                                player[nowPlayer].rotateCard(cardid);
                                System.out.println(player[nowPlayer].getCard(cardid));
                            }
                            System.out.print("Where/Discard : ");
                            int x = scanner.nextInt();
                            int y = -1;
                            if(x < 0){
                                player[nowPlayer].removeCard(cardid);
                                nowAction = new Action(nowPlayer, true);
                            }else {
                                y = scanner.nextInt();
                                nowAction = new Action(ctmp, x, y, nowPlayer, -1);
                            }
                        }else if(ctmp.IsFunction()) {
                            if(ctmp.Function().isMap()){
                                System.out.print("Which(0/1/2)/Discard : ");
                                int pos = scanner.nextInt();
                                if(pos < 0){
                                    player[nowPlayer].removeCard(cardid);
                                    nowAction = new Action(nowPlayer, true);
                                }else{
                                    String[] tmp = new String[]{"Top", "Mid", "Bot"};
                                    System.out.println(tmp[pos]+" is "+(map.haveGold(4 - pos*2)?"Gold":"Stone"));
                                    player[nowPlayer].removeCard(cardid);
                                }
                            }else if(ctmp.Function().isCollapse()){
                                System.out.print("Where/Discard : ");
                                int x, y;
                                x = scanner.nextInt();
                                if(x < 0){
                                    player[nowPlayer].removeCard(cardid);
                                    nowAction = new Action(nowPlayer, true);
                                }else{
                                    y = scanner.nextInt();
                                    nowAction = new Action(ctmp, x, y, nowPlayer, -1);
                                    player[nowPlayer].removeCard(cardid);
                                }
                            }else {
                                System.out.print("Whom/Discard: ");
                                int pid = scanner.nextInt();
                                if (pid < 0) {
                                    player[nowPlayer].removeCard(cardid);
                                    nowAction = new Action(nowPlayer, true);
                                } else {
                                    Card chg;
                                    if(ctmp.Function().isFix() && ctmp.Function().itemKind() > 2){
                                        System.out.println("Choose which to fix(0/1/2): ");
                                        int item = scanner.nextInt();
                                        String[] tmp = new String[]{"pick", "oil_lamp", "mine_cart"};
                                        chg = new Card("fix", tmp[item]);
                                        nowAction = new Action(chg, -1, -1, nowPlayer, pid);
                                        player[nowPlayer].removeCard(cardid);
                                    }else {
                                        nowAction = new Action(ctmp, -1, -1, nowPlayer, pid);
                                        player[nowPlayer].removeCard(cardid);
                                    }
                                }
                            }
                        }
                        nowAction.toString();
                    }

                    /* server handle */
                    // boolean[] before = new boolean[]{map.isFlipped(4), map.isFlipped(2), map.isFlipped(0)};
                    if(valid = isValid(nowAction, map, player, sts)){
                        if(nowAction.getIsOnMap() && nowAction.getCard().IsRoad()){
                            player[nowPlayer].removeCard(cardid);
                            if(map.isFlipped(4)){
                                if(map.haveGold(4)){
                                    end = true;
                                    winner = 1;
                                }
                            }
                            if(map.isFlipped(2)){
                                if(map.haveGold(2)){
                                    end = true;
                                    winner = 1;
                                }
                            }
                            if(map.isFlipped(0)){
                                if(map.haveGold(0)){
                                    end = true;
                                    winner = 1;
                                }
                            }
                        }
                        if(deck.theDeck.size() > 0) { // assign
                            player[nowPlayer].assignCard(deck.giveACard());
                            System.out.println("ASSIGN!!!");
                        }else{  // count barehand
                            baredHand = 0;
                            for(int i = 0; i < playerNumber; i++){
                                if(!player[i].stillHaveCard()){
                                    baredHand++;
                                }
                            }
                        }
                        /* Broadcast */
                        for(int i = 0; i < playerNumber; i++){
                            if(player[i].isAI()){
                                AIPlayer aitmp = (AIPlayer) player[i];
                                aitmp.updateSituation(nowAction);
                                break;
                            }
                        }
                        /* Show Map */
                        System.out.println(map);
                    }
                }
            }
            /* Count reward */
            if(winner == 0){
                System.out.println("Saboteurs win!!!");
            } else if(winner == 1){
                System.out.println("Miners win!!!");
            }

        }
    }
}
