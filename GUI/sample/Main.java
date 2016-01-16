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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Main extends Application{

    Stage window;
    Scene scene1, scene2, scene3;
    int playerNumber, aiNumber;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));

        Text scenetitle = new Text("Welcome to Saboteur");
        scenetitle.setFont(Font.font("Helvetica", FontWeight.BOLD, 36));
        scenetitle.setFill(Color.BROWN);
        grid.add(scenetitle,0,0);

        Button btn1 = new Button("Press here to enter game");
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.setScene(scene2);
            }
        });
        btn1.setAlignment(Pos.BASELINE_CENTER);
        grid.add(btn1,0,25);

        grid.setId("pane1");
        scene1 = new Scene(grid, 550, 398);
        scene1.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());


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
                System.out.println("Player# : "+t1);
                playerNumber = Integer.parseInt(t1);
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

        Button btn2 = new Button("OK");
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.setScene(scene3);
            }
        });
        btn2.setAlignment(Pos.BASELINE_CENTER);
        grid2.add(btn2,0,12);

        grid2.setId("pane2");
        scene2 = new Scene(grid2, 550, 398);
        scene2.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        window.setScene(scene1);
        window.setTitle("GUI Test");
        window.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
