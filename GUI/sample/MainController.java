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

    @FXML private Label p1_id;
    @FXML private Label p2_id;
    @FXML private Label p3_id;
    @FXML private Label p4_id;
    @FXML private Label p5_id;
    @FXML private Label p6_id;
    @FXML private Label p7_id;
    @FXML private Label p8_id;
    @FXML private Label p9_id;
    @FXML private Label p10_id;

    @FXML private HBox s1_id;
    @FXML private HBox s2_id;
    @FXML private HBox s3_id;
    @FXML private HBox s4_id;
    @FXML private HBox s5_id;
    @FXML private HBox s6_id;
    @FXML private HBox s7_id;
    @FXML private HBox s8_id;
    @FXML private HBox s9_id;
    @FXML private HBox s10_id;

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

    @FXML
    public void chatOpen(MouseEvent event){
        this.chatroom.setDisable(false);
        this.chatroomtop.setDisable(false);
        this.chatroom.setVisible(true);
    }

    @FXML
    public void chatClose(MouseEvent event){
        this.chatroom.setDisable(true);
        this.chatroomtop.setDisable(true);
        this.chatroom.setVisible(false);
    }

    @FXML
    public void chatMsgSend(ActionEvent event){
        if(!this.chatinput.getCharacters().toString().trim().equals("")) {
            this.chatoutput.setText(this.chatoutput.getText()+this.chatinput.getCharacters()+"\n");
            this.chatinput.clear();
        }
    }

}
