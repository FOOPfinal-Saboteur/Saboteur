package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

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

    @FXML private Label mode;
    @FXML private Label player_num;
    @FXML private Label role;
    @FXML private Label round;

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


}
