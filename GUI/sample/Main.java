//package sample;

import javafx.application.Application;
import javafx.beans.binding.StringBinding;
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
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

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

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;

        setUserAgentStylesheet(STYLESHEET_MODENA);

        /* Scene1 */

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(50));

        Text scenetitle = new Text("Press anywhere to start");
        //scenetitle.setId("dark-blue");
        //scenetitle.setFont(Font.font("Helvetica", FontWeight.BOLD, 36));
        //scenetitle.setFill(Color.BROWN);
        grid.add(scenetitle,0,10);

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
        scene1 = new Scene(grid, 940, 705);
        scene1.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        /* Scene2 */

        VBox vbox2 = new VBox();
        vbox2.setAlignment(Pos.CENTER);
        vbox2.setPadding(new Insets(400,300,300,300));
        vbox2.setSpacing(50);

        Text pnumtext = new Text("Please select number of players");
        pnumtext.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        pnumtext.setFill(Color.BLACK);

        Text ainumtext = new Text("Please select number of AI players");
        ainumtext.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        ainumtext.setFill(Color.BLACK);

        String[] nnn = {"0","1","2","3","4","5","6","7","8","9"};
        ChoiceBox cb = new ChoiceBox();
        ChoiceBox cb2 = new ChoiceBox();
        cb.setItems(FXCollections.observableArrayList("3","4","5","6","7","8","9","10"));
        cb.setId("round-red");
        cb2.setId("round-red");

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

        HBox hbox2_1 = new HBox();
        hbox2_1.setSpacing(10);
        HBox hbox2_2 = new HBox();
        hbox2_2.setSpacing(10);

        hbox2_1.getChildren().add(pnumtext);
        hbox2_1.getChildren().add(cb);
        hbox2_2.getChildren().add(ainumtext);
        hbox2_2.getChildren().add(cb2);
        vbox2.getChildren().add(hbox2_1);
        vbox2.getChildren().add(hbox2_2);

        /* Things in Scene4 */
        Text mode = new Text("Mode: "+"Normal");
        Text shownum = new Text();
        Text showrole = new Text();
        Text showname = new Text();
        Label[] status = new Label[playerNumberMAX];

        VBox rightcol = new VBox(20);
        rightcol.setMinWidth(180);
        rightcol.setPadding(new Insets(20));

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameLayout.fxml"));
        Parent root = (Parent)fxmlLoader.load();

        MainController ctrl = fxmlLoader.<MainController>getController();
        /* */

        Button btn2 = new Button("OK");
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(cb.getValue() == null || cb2.getValue() == null){
                    AlertBox.display("Attetion", "You haven't set the numbers yet!");
                }else {
                    playerNumber = Integer.parseInt(cb.getValue().toString());
                    aiNumber = Integer.parseInt(cb2.getValue().toString());
                    shownum.setText("Player's number: " + playerNumber);
                    randomSetRoles(playerNumber, roles);
                    showrole.setText("Role: " + rolename[roles.get(nowPlayer)]);
                    for (int i = 0; i < playerNumber; i++) {
                        status[i] = new Label();
                        status[i].setFont(Font.font("Helvetica", FontWeight.NORMAL, 28));
                        status[i].setTextFill(Color.WHITE);
                    }
                    window.setScene(scene3);
                }
            }
        });
        btn2.setAlignment(Pos.CENTER);
        btn2.setId("record-sales");

        vbox2.getChildren().add(btn2);

        vbox2.setId("pane2");
        scene2 = new Scene(vbox2, 940, 705);
        scene2.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        /* Scene3 */

        VBox vbox3 = new VBox();
        vbox3.setAlignment(Pos.CENTER);
        vbox3.setSpacing(10);
        vbox3.setPadding(new Insets(400,300,300,300));

        HBox hbox3_1 = new HBox();
        hbox3_1.setAlignment(Pos.CENTER);
        hbox3_1.setSpacing(10);

        Text nametext = new Text("Please enter the name of players");
        nametext.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        nametext.setFill(Color.BLACK);
        vbox3.getChildren().add(nametext);

        TextField typename = new TextField();
        typename.setPrefWidth(200);
        addTextLimiter(typename, nameCharMax);
        typename.setStyle("-fx-control-inner-background: BROWN");
        typename.setFont(Font.font("Serif", FontWeight.NORMAL, 18));
        hbox3_1.getChildren().add(typename);

        TextArea namearea = new TextArea();
        namearea.setStyle("-fx-control-inner-background: BROWN");
        namearea.setFont(Font.font("Serif", FontWeight.NORMAL, 18));
        namearea.setEditable(false);
        namearea.setMinHeight(200);
        namearea.setMaxWidth(250);

        Button btn3 = new Button("Submit");
        btn3.setId("record-sales");
        btn3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean nameused = false;
                for(int i = 0; i < playerName.size(); i++){
                    if(playerName.get(i).equals(typename.getCharacters().toString().trim())){
                        AlertBox.display("Attention!", "This name has been used!");
                        nameused = true;
                        break;
                    }
                }
                System.out.println(typename.getCharacters().toString());
                if(typename.getCharacters().toString().trim().equals("")){
                    AlertBox.display("Attention!", "Name can't be blank!");
                    nameused = true;
                }
                if(!nameused) {
                    playerName.add(typename.getCharacters().toString().trim());
                    System.out.println(playerName.get(playerName.size() - 1));
                    if(namearea.getText().equals("")) {
                        namearea.setText(typename.getCharacters().toString());
                    }else{
                        namearea.setText(namearea.getText() + "\n" + typename.getCharacters());
                    }
                    if(playerName.size() == playerNumber-aiNumber){
                        randomAIName(aiNumber, playerName);
                        AlertBox.display("", "OK!, Let's Start the game!");
                        showname.setText("Name: "+playerName.get(nowPlayer));
                        for(int i = 0; i < playerNumber; i++) {
                            status[i].setText(playerName.get(i));
                            rightcol.getChildren().add(status[i]);
                        }
                        ctrl.setStatus(playerNumber, playerName);
                        ctrl.setMenuBar("Normal", playerNumber, rolename[roles.get(nowPlayer)]);
                        window.setScene(scene4);
                        window.centerOnScreen();
                    }
                }
                typename.clear();
            }
        });
        hbox3_1.getChildren().add(btn3);
        vbox3.getChildren().add(hbox3_1);

        vbox3.getChildren().add(namearea);

        vbox3.setId("pane3");
        scene3 = new Scene(vbox3, 940, 705);
        scene3.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        /* Scene4(Game) */

        /*BorderPane pane = new BorderPane();
        GridPane lefttop = new GridPane();
        lefttop.setAlignment(Pos.TOP_LEFT);
        lefttop.setHgap(10);
        lefttop.setVgap(10);
        lefttop.setPadding(new Insets(20));

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
        pane.setId("pane4");*/

        // scene4 = new Scene(pane, 1080, 720);
        // scene4.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        scene4 = new Scene(root, 960, 720);

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
