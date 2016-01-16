//package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Main extends Application{

    Stage window;
    Scene scene1, scene2, scene3, scene4;
    int playerNumber, aiNumber;
    int nameCharMax = 12, playerNumberMAX = 10;
    ArrayList<String> playerName = new ArrayList<String>();
    int nowPlayer = 0;
    ArrayList<Integer> roles = new ArrayList<Integer>();
    String[] rolename = {"Saboteur", "Miner"};

    public static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
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

        for(int i = 0; i < plnum; i++){
            Random ran = new Random();
            int index;
            index = ran.nextInt(rolecardlist.size());
            roleset.add(rolecardlist.get(index));
            rolecardlist.remove(index);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;

        /* Scene1 */

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));

        Text scenetitle = new Text("Welcome to Saboteur");
        scenetitle.setFont(Font.font("Helvetica", FontWeight.BOLD, 36));
        scenetitle.setFill(Color.BROWN);
        grid.add(scenetitle,0,0);

       /* Button btn1 = new Button("Press here to enter game");
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.setScene(scene2);
            }
        });
        btn1.setAlignment(Pos.BASELINE_CENTER);
        grid.add(btn1,0,25);*/
        grid.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                window.setScene(scene2);
            }
        });

        grid.setId("pane1");
        scene1 = new Scene(grid, 550, 398);
        scene1.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        /* Scene2 */

        GridPane grid2 = new GridPane();
        grid2.setAlignment(Pos.CENTER);
        grid2.setVgap(10);
        grid2.setHgap(10);
        grid2.setPadding(new Insets(25,25,25,25));

        Text pnumtext = new Text("Please select number of players");
        pnumtext.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        pnumtext.setFill(Color.BROWN);
        grid2.add(pnumtext,0,0);

        Text ainumtext = new Text("Please select number of AI players");
        ainumtext.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        ainumtext.setFill(Color.BROWN);
        grid2.add(ainumtext,0,8);

        String[] nnn = {"0","1","2","3","4","5","6","7","8","9"};
        ChoiceBox cb = new ChoiceBox();
        ChoiceBox cb2 = new ChoiceBox();
        cb.setItems(FXCollections.observableArrayList("3","4","5","6","7","8","9","10"));


        cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                playerNumber = Integer.parseInt(t1);
                System.out.println("Player# : "+playerNumber);
                cb2.getItems().clear();
                for(int i = 0; i < playerNumber; i++) {
                    cb2.getItems().add(nnn[i]);
                }
            }
        });
        cb2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                if(t1 == null) {
                    System.out.println("reset list");
                    aiNumber = 0;
                } else {
                    System.out.println("AI# : "+t1);
                    aiNumber = Integer.parseInt(t1);
                }

            }
        });

        grid2.add(cb,5,0);
        grid2.add(cb2,5,8);

        /* Things in Scene4 */
        Text mode = new Text("Mode: "+"Normal");
        Text shownum = new Text();
        Text showrole = new Text();
        Text showname = new Text();
        Label[] status = new Label[playerNumberMAX];

        VBox rightcol = new VBox(20);
        rightcol.setMinWidth(180);
        /* */

        Button btn2 = new Button("OK");
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                playerNumber = Integer.parseInt(cb.getValue().toString());
                aiNumber = Integer.parseInt(cb2.getValue().toString());
                shownum.setText("Player's number: "+playerNumber);
                randomSetRoles(playerNumber, roles);
                showrole.setText("Role: "+rolename[roles.get(nowPlayer)]);
                for(int i = 0; i < playerNumber; i++) {
                    status[i] = new Label();
                    status[i].setFont(Font.font("Helvetica", FontWeight.NORMAL, 28));
                    status[i].setTextFill(Color.BLACK);
                }
                window.setScene(scene3);
            }
        });
        btn2.setAlignment(Pos.BASELINE_CENTER);
        grid2.add(btn2,0,12);

        grid2.setId("pane2");
        scene2 = new Scene(grid2, 550, 398);
        scene2.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        /* Scene3 */

        GridPane grid3 = new GridPane();
        grid3.setAlignment(Pos.CENTER);
        grid3.setVgap(10);
        grid3.setHgap(10);
        grid3.setPadding(new Insets(15,15,15,15));

        Text nametext = new Text("Please enter the name of players");
        nametext.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        nametext.setFill(Color.BROWN);
        grid3.add(nametext,0,0);

        TextField typename = new TextField();
        addTextLimiter(typename, nameCharMax);
        grid3.add(typename,0,2);

        Text namelist = new Text();
        grid3.add(namelist,0,4);

        Button btn3 = new Button("Submit");
        btn3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean nameused = false;
                for(int i = 0; i < playerName.size(); i++){
                    if(playerName.get(i).equals(typename.getCharacters().toString().trim())){
                        AlertBox.display("Attention!", "This name has been used!");
                        nameused = true;
                    }
                }
                if(!nameused) {
                    playerName.add(typename.getCharacters().toString().trim());
                    System.out.println(playerName.get(playerName.size() - 1));
                    namelist.setText(namelist.getText() + "\n" + typename.getCharacters());
                    if(playerName.size() == playerNumber-aiNumber){
                        AlertBox.display("", "OK!, Let's Start the game!");
                        showname.setText("Name: "+playerName.get(nowPlayer));
                        for(int i = 0; i < playerNumber-aiNumber; i++) {
                            status[i].setText(playerName.get(i));
                            rightcol.getChildren().add(status[i]);
                        }
                        window.setScene(scene4);
                        window.centerOnScreen();
                    }
                }
                typename.clear();
            }
        });
        grid3.add(btn3,1,2);

        grid3.setId("pane3");
        scene3 = new Scene(grid3, 550, 398);
        scene3.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        /* Scene4(Game) */

        BorderPane pane = new BorderPane();
        GridPane lefttop = new GridPane();
        lefttop.setAlignment(Pos.TOP_LEFT);
        lefttop.setHgap(10);
        lefttop.setVgap(10);
        lefttop.setPadding(new Insets(20,20,20,20));

        mode.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        mode.setFill(Color.ORANGE);
        shownum.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        shownum.setFill(Color.ORANGE);
        showrole.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        showrole.setFill(Color.ORANGE);
        showname.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        showname.setFill(Color.ORANGE);

        lefttop.add(mode,0,0);
        lefttop.add(shownum,1,0);
        lefttop.add(showrole,2,0);
        lefttop.add(showname,3,0);

        pane.setRight(rightcol);
        pane.setTop(lefttop);

        pane.setId("pane4");
        scene4 = new Scene(pane, 1080, 720);
        scene4.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        /* Start */
        window.setScene(scene1);
        window.setTitle("GUI Test");
        window.centerOnScreen();
        window.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}