package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    /* Big Layout */
    @FXML public ArrayList<CardGUI> onMap = new ArrayList<CardGUI>();
    @FXML public ArrayList<CardGUI> gui;
    @FXML public MapGUI mapView;
    @FXML public Shuffler shuffler;
    @FXML public Pane pane;
    @FXML public Pane mapPane;

    public int hand_num;

    /* Topmenu */
    @FXML public Label mode;
    @FXML public Label player_num;
    @FXML public Label role;
    @FXML public Label round;

    /* right col */
    @FXML public VBox rightcol;

    @FXML public Label p1_id; @FXML public Label p2_id; @FXML public Label p3_id; @FXML public Label p4_id;
    @FXML public Label p5_id; @FXML public Label p6_id; @FXML public Label p7_id; @FXML public Label p8_id;
    @FXML public Label p9_id; @FXML public Label p10_id;

    @FXML public HBox s1_id; @FXML public HBox s2_id; @FXML public HBox s3_id; @FXML public HBox s4_id;
    @FXML public HBox s5_id; @FXML public HBox s6_id; @FXML public HBox s7_id; @FXML public HBox s8_id;
    @FXML public HBox s9_id; @FXML public HBox s10_id;

    @FXML public Label llb1; @FXML public Label llb2; @FXML public Label llb3; @FXML public Label llb4;
    @FXML public Label llb5; @FXML public Label llb6; @FXML public Label llb7; @FXML public Label llb8;
    @FXML public Label llb9; @FXML public Label llb10;

    @FXML public ImageView item1_1; @FXML public ImageView item1_2; @FXML public ImageView item1_3;
    @FXML public ImageView item2_1; @FXML public ImageView item2_2; @FXML public ImageView item2_3;
    @FXML public ImageView item3_1; @FXML public ImageView item3_2; @FXML public ImageView item3_3;
    @FXML public ImageView item4_1; @FXML public ImageView item4_2; @FXML public ImageView item4_3;
    @FXML public ImageView item5_1; @FXML public ImageView item5_2; @FXML public ImageView item5_3;
    @FXML public ImageView item6_1; @FXML public ImageView item6_2; @FXML public ImageView item6_3;
    @FXML public ImageView item7_1; @FXML public ImageView item7_2; @FXML public ImageView item7_3;
    @FXML public ImageView item8_1; @FXML public ImageView item8_2; @FXML public ImageView item8_3;
    @FXML public ImageView item9_1; @FXML public ImageView item9_2; @FXML public ImageView item9_3;
    @FXML public ImageView item10_1; @FXML public ImageView item10_2; @FXML public ImageView item10_3;

    @FXML public Rectangle rct1; @FXML public Rectangle rct2; @FXML public Rectangle rct3;
    @FXML public Rectangle rct4; @FXML public Rectangle rct5; @FXML public Rectangle rct6;
    @FXML public Rectangle rct7; @FXML public Rectangle rct8; @FXML public Rectangle rct9;
    @FXML public Rectangle rct10;

    /* rightcol2 - chatroom */

    @FXML public VBox chatroom;
    @FXML public Label chatroomtop;
    @FXML public TextArea chatoutput;
    @FXML public TextField chatinput;
    @FXML public Button chatsend;

    /* MainArea */

    @FXML public Text card_cnt;

    /* Function */


    public static void initCard(ImageView iv) {
        iv.setFitHeight(105);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setCache(true);
    }

    @FXML
    public void backEndSetUp(){

        mapView = new MapGUI(538);
        /*shuffler = new Shuffler();
        shuffler.shuffle();*/

        gui = new ArrayList<CardGUI>();
        /*for (int i = 0; i < hand_num; i++) {
            initCard(gui.get(i));
        }*/

        mapPane = mapView.initPane();
        pane.getChildren().add(mapPane);
        /*for (int i = 0; i < hand_num; i++) {
            pane.getChildren().add(gui.get(i));
            gui.get(i).setTranslateX(20 + 80 * i);
            gui.get(i).setTranslateY(590);
        }
        for (int i = 0; i < hand_num; i++)
            gui.get(i).setHandler(mapView, gui, shuffler, pane);
*/
        mapView.setLayoutX(13);
        mapView.setLayoutY(40);
        mapPane.toBack();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
    }

    public MainController() {
    }

    /* init gaevent */

    @FXML
    public void setStatus(int pnum, ArrayList<String> naevents){
        Label[] p_id = new Label[]{p1_id, p2_id, p3_id, p4_id, p5_id, p6_id, p7_id, p8_id, p9_id, p10_id};
        for(int i = 0; i < pnum; i++) {
            p_id[i].setText(naevents.get(i));
        }
        HBox[] s_id = new HBox[]{s1_id, s2_id, s3_id, s4_id, s5_id, s6_id, s7_id, s8_id, s9_id, s10_id};
        for(int i = pnum; i < 10; i++) {
            s_id[i].setVisible(false);
        }
        Rectangle[] rct = new Rectangle[]{rct1,rct2,rct3,rct4,rct5,rct6,rct7,rct8,rct9,rct10};
        for(int i = pnum; i < 10; i++){
            rct[i].setDisable(true);
        }
    }

    @FXML
    public void setMenuBar(String mode, int pnum, String role){
        this.mode.setText(mode);
        this.player_num.setText(pnum+"");
        this.role.setText(role);
    }

    @FXML
    public void setRound(int rnd){
        /* Init */
        this.round.setText(rnd+"/3");
    }

    @FXML
    public void setNowPlayer(int n){
        Label[] llb = new Label[]{llb1, llb2, llb3, llb4, llb5, llb6, llb7, llb8, llb9, llb10};
        for(int i = 0; i < 10; i++) {
            if(i == n){
                llb[i].setVisible(true);
            }else{
                llb[i].setVisible(false);
            }
        }
    }

    /* chatroom */

    @FXML
    public boolean chatIsOpen(){
        return chatroom.isVisible();
    }

    @FXML
    public void chatOpen(){
        this.chatroom.setDisable(false);
        this.chatroomtop.setDisable(false);
        this.chatroom.setVisible(true);
        Rectangle[] rct = new Rectangle[]{rct1, rct2, rct3, rct4, rct5, rct6, rct7, rct8, rct9, rct10};
        for(int i = 0; i < 10; i++) {
            rct[i].setVisible(false);
        }
    }

    @FXML
    public void chatClose(){
        this.chatroom.setDisable(true);
        this.chatroomtop.setDisable(true);
        this.chatroom.setVisible(false);
        Rectangle[] rct = new Rectangle[]{rct1, rct2, rct3, rct4, rct5, rct6, rct7, rct8, rct9, rct10};
        for(int i = 0; i < 10; i++) {
            rct[i].setVisible(true);
        }
    }

    @FXML
    public void chatOpenClk(MouseEvent event){ chatOpen(); }
    @FXML
    public void chatCloseClk(MouseEvent event){ chatClose(); }
    @FXML
    public void chatClosePrs(KeyEvent event){ if(event.getCode() == KeyCode.TAB) chatClose(); }

    @FXML
    public void chatMsgSend(ActionEvent event){
        if(!this.chatinput.getCharacters().toString().trim().equals("")) {
            this.chatoutput.setText(this.chatoutput.getText()+this.chatinput.getCharacters()+"\n");
            this.chatinput.clear();
        }
    }

    @FXML
    public void chatMsgSend2(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER) {
            if (!this.chatinput.getCharacters().toString().trim().equals("")) {
                this.chatoutput.setText(this.chatoutput.getText() + this.chatinput.getCharacters() + "\n");
                this.chatinput.clear();
            }
        }else if(event.getCode() == KeyCode.TAB) {
            chatClose();
        }
    }

    @FXML
    public void setCardCnt(int n){
        this.card_cnt.setText(n+"");
    }

    /* Affect player's status */
    @FXML
    public void attack(int playerid, int item){
        ImageView[] st_id1 = new ImageView[]{item1_1, item1_2, item1_3};
        ImageView[] st_id2 = new ImageView[]{item2_1, item2_2, item2_3};
        ImageView[] st_id3 = new ImageView[]{item3_1, item3_2, item3_3};
        ImageView[] st_id4 = new ImageView[]{item4_1, item4_2, item4_3};
        ImageView[] st_id5 = new ImageView[]{item5_1, item5_2, item5_3};
        ImageView[] st_id6 = new ImageView[]{item6_1, item6_2, item6_3};
        ImageView[] st_id7 = new ImageView[]{item7_1, item7_2, item7_3};
        ImageView[] st_id8 = new ImageView[]{item8_1, item8_2, item8_3};
        ImageView[] st_id9 = new ImageView[]{item9_1, item9_2, item9_3};
        ImageView[] st_id10 = new ImageView[]{item10_1, item10_2, item10_3};
        ImageView[][] st_id = new ImageView[][]{st_id1, st_id2, st_id3, st_id4, st_id5, st_id6, st_id7, st_id8, st_id9, st_id10};

        st_id[playerid][item].setVisible(true);
        String[] tmp = new String[]{"pick", "oil_lamp", "mine_cart"};
        System.out.println(Main.playerName.get(playerid)+"'s "+tmp[item]+" is DESTROYED!");
    }

    @FXML
    public void rescue(int playerid, int item){
        ImageView[] st_id1 = new ImageView[]{item1_1, item1_2, item1_3};
        ImageView[] st_id2 = new ImageView[]{item2_1, item2_2, item2_3};
        ImageView[] st_id3 = new ImageView[]{item3_1, item3_2, item3_3};
        ImageView[] st_id4 = new ImageView[]{item4_1, item4_2, item4_3};
        ImageView[] st_id5 = new ImageView[]{item5_1, item5_2, item5_3};
        ImageView[] st_id6 = new ImageView[]{item6_1, item6_2, item6_3};
        ImageView[] st_id7 = new ImageView[]{item7_1, item7_2, item7_3};
        ImageView[] st_id8 = new ImageView[]{item8_1, item8_2, item8_3};
        ImageView[] st_id9 = new ImageView[]{item9_1, item9_2, item9_3};
        ImageView[] st_id10 = new ImageView[]{item10_1, item10_2, item10_3};
        ImageView[][] st_id = new ImageView[][]{st_id1, st_id2, st_id3, st_id4, st_id5, st_id6, st_id7, st_id8, st_id9, st_id10};

        st_id[playerid][item].setVisible(false);
    }

   /* @FXML
   public void statusMark(MouseEvent event){
        if(event.isDragDetect()){
            Rectangle tmp = (Rectangle) event.getTarget();
            tmp.setStroke(Color.BLACK);
        }
    }

    @FXML
    public void statusUnmark(MouseEvent event){
        if(event.isDragDetect()) {
            Rectangle tmp = (Rectangle) event.getTarget();
            tmp.setStroke(Color.TRANSPARENT);
        }
    }*/

    @FXML
    public void setStatusGreen(boolean b, int id){
        Rectangle[] rct = new Rectangle[]{rct1,rct2,rct3,rct4,rct5,rct6,rct7,rct8,rct9,rct10};
        if(b)
            rct[id].setStroke(Color.GREEN);
        else
            rct[id].setStroke(Color.TRANSPARENT);
    }

    @FXML
    public void setStatusRed(boolean b, int id){
        Rectangle[] rct = new Rectangle[]{rct1,rct2,rct3,rct4,rct5,rct6,rct7,rct8,rct9,rct10};
        if(b)
            rct[id].setStroke(Color.RED);
        else
            rct[id].setStroke(Color.TRANSPARENT);    }

    @FXML
    public void showHand(ArrayList<Card> hand){

        if(gui != null){
            for(int i = 0; i < gui.size(); i++){
                pane.getChildren().remove(gui.get(i));
            }
        }

        gui = new ArrayList<CardGUI>();

        for(int i = 0; i < hand.size(); i++){
            String url = new String();
            if(hand.get(i).IsRoad()){
                url += "Road_";
                if(hand.get(i).getType().equals("intersection")){
                    url += "Intersection";
                }else if(hand.get(i).getType().equals("longT")){
                    url += "LongT";
                }else if(hand.get(i).getType().equals("shortT")){
                    url += "ShortT";
                }else if(hand.get(i).getType().equals("longI")){
                    url += "LongI";
                }else if(hand.get(i).getType().equals("shortI")){
                    url += "ShortI";
                }else if(hand.get(i).getType().equals("LeftTop")){
                    url += "LeftTop";
                }else if(hand.get(i).getType().equals("RightTop")){
                    url += "RightTop";
                }else if(hand.get(i).getType().equals("fullblock")){
                    url += "FullBlock";
                }else if(hand.get(i).getType().equals("longTblock")){
                    url += "LongTBlock";
                }else if(hand.get(i).getType().equals("shortTblock")){
                    url += "ShortTBlock";
                }else if(hand.get(i).getType().equals("LeftTopblock")){
                    url += "LeftTopBlock";
                }else if(hand.get(i).getType().equals("RightTopblock")){
                    url += "RightTopBlock";
                }else if(hand.get(i).getType().equals("longIblock")){
                    url += "LongIBlock";
                }else if(hand.get(i).getType().equals("shortIblock")){
                    url += "ShortIBlock";
                }else if(hand.get(i).getType().equals("longDeadEnd")){
                    url += "LongDeadEnd";
                }else if(hand.get(i).getType().equals("shortDeadEnd")){
                    url += "ShortDeadEnd";
                }
            }else if(hand.get(i).IsFunction()){
                url += "Func_";
                if(hand.get(i).getType().equals("fix")) {
                    url += "Fix";
                }else if(hand.get(i).getType().equals("break")){
                    url += "Break";
                }else if(hand.get(i).getType().equals("map")){
                    url += "Map";
                }else if(hand.get(i).getType().equals("collapse")){
                    url += "Collapse";
                }
                if(hand.get(i).getItem().equals("pick")){
                    url += "_pick";
                }else if(hand.get(i).getItem().equals("oil_lamp")){
                    url += "_lamp";
                }else if(hand.get(i).getItem().equals("mine_cart")){
                    url += "_cart";
                }else if(hand.get(i).getItem().equals("pick & oil_lamp")){
                    url += "_pick&lamp";
                }else if(hand.get(i).getItem().equals("oil_lamp & mine_cart")){
                    url += "_lamp&cart";
                }else if(hand.get(i).getItem().equals("mine_cart & pick")){
                    url += "_cart&pick";
                }
            }
            url = "file:"+System.getProperty("user.dir")+"/src/sample/img/game/"+url+".png";

            gui.add(new CardGUI(i, url));
        }

        for (int i = 0; i < gui.size(); i++) {
            initCard(gui.get(i));
            pane.getChildren().add(gui.get(i));
            gui.get(i).setTranslateX(20 + 80 * i);
            gui.get(i).setTranslateY(590);
            gui.get(i).setHandler(mapView, gui, shuffler, pane);
        }

    }

    @FXML
    public void askWhich(int item){
        switch (item){
            case 3:
                ChooseBox.display("Fix item", "Choose one to fix", "pick", "lamp");
            case 4:
                ChooseBox.display("Fix item", "Choose one to fix", "lamp", "cart");
            case 5:
                ChooseBox.display("Fix item", "Choose one to fix", "cart", "pick");
        }
    }

    @FXML
    public void clearTable(){
        for(int i = 0; i < gui.size(); i++){
            pane.getChildren().remove(gui.get(i));
        }
        for(int i = 0; i < onMap.size(); i++){
            pane.getChildren().remove(onMap.get(i));
        }
        gui.clear();
        onMap.clear();
        pane.getChildren().remove(mapPane);
        mapPane.getChildren().clear();
    }
}
