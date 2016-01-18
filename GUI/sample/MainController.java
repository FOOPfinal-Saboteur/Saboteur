package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    /* TopMenu */
    @FXML private Label mode;
    @FXML private Label player_num;
    @FXML private Label role;
    @FXML private Label round;

    /* right col */
    @FXML private VBox rightcol;

    @FXML private Label p1_id; @FXML private Label p2_id; @FXML private Label p3_id; @FXML private Label p4_id;
    @FXML private Label p5_id; @FXML private Label p6_id; @FXML private Label p7_id; @FXML private Label p8_id;
    @FXML private Label p9_id; @FXML private Label p10_id;

    @FXML private HBox s1_id; @FXML private HBox s2_id; @FXML private HBox s3_id; @FXML private HBox s4_id;
    @FXML private HBox s5_id; @FXML private HBox s6_id; @FXML private HBox s7_id; @FXML private HBox s8_id;
    @FXML private HBox s9_id; @FXML private HBox s10_id;

    @FXML private ImageView item1_1; @FXML private ImageView item1_2; @FXML private ImageView item1_3;
    @FXML private ImageView item2_1; @FXML private ImageView item2_2; @FXML private ImageView item2_3;
    @FXML private ImageView item3_1; @FXML private ImageView item3_2; @FXML private ImageView item3_3;
    @FXML private ImageView item4_1; @FXML private ImageView item4_2; @FXML private ImageView item4_3;
    @FXML private ImageView item5_1; @FXML private ImageView item5_2; @FXML private ImageView item5_3;
    @FXML private ImageView item6_1; @FXML private ImageView item6_2; @FXML private ImageView item6_3;
    @FXML private ImageView item7_1; @FXML private ImageView item7_2; @FXML private ImageView item7_3;
    @FXML private ImageView item8_1; @FXML private ImageView item8_2; @FXML private ImageView item8_3;
    @FXML private ImageView item9_1; @FXML private ImageView item9_2; @FXML private ImageView item9_3;
    @FXML private ImageView item10_1; @FXML private ImageView item10_2; @FXML private ImageView item10_3;

    @FXML private Label chat;

    /* rightcol2 - chatroom */

    @FXML private VBox chatroom;
    @FXML private Label chatroomtop;
    @FXML private TextArea chatoutput;
    @FXML private TextField chatinput;
    @FXML private Button chatsend;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
    }
    public MainController() {
    }

    /* init game */

    @FXML
    protected void setStatus(int pnum, ArrayList<String> names){
        Label[] p_id = new Label[]{p1_id, p2_id, p3_id, p4_id, p5_id, p6_id, p7_id, p8_id, p9_id, p10_id};
        for(int i = 0; i < pnum; i++) {
            p_id[i].setText(names.get(i));
        }
        HBox[] s_id = new HBox[]{s1_id, s2_id, s3_id, s4_id, s5_id, s6_id, s7_id, s8_id, s9_id, s10_id};
        for(int i = pnum; i < 10; i++) {
            s_id[i].setVisible(false);
        }
    }

    @FXML
    protected void setMenuBar(String mode, int pnum, String role){
        this.mode.setText(mode);
        this.player_num.setText(pnum+"");
        this.role.setText(role);
    }

    @FXML
    protected void setRound(int rnd){
        /* Init */
        this.round.setText(rnd+"/3");
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
    }

    @FXML
    public void chatClose(){
        this.chatroom.setDisable(true);
        this.chatroomtop.setDisable(true);
        this.chatroom.setVisible(false);
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

        st_id[playerid-1][item-1].setVisible(true);
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

        st_id[playerid-1][item-1].setVisible(false);
    }

}
