package sample;

import javafx.application.Application;
import javafx.scene.Scene;
//images
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//layout
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
//set color
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.stage.Stage;
//position
import javafx.geometry.Pos;

public class DoubleLayout extends Application {

	Scene scene;

	public static final double WIDTH = 960;
	public static final double HEIGHT = 720;


	public void initCard(ImageView iv) {
		iv.setFitHeight(105);
		iv.setPreserveRatio(true);
		iv.setSmooth(true);
		iv.setCache(true);
	}


	@Override public void start(Stage stage) {
		


		MapGUI mapView = new MapGUI(538);

		Shuffler shuffler = new Shuffler(mapView);
		shuffler.shuffle();

		CardGUI[] gui = new CardGUI[10];
		for (int i = 0; i < 10; i++) {
			gui[i] = shuffler.takeCard();
			initCard(gui[i]);
		}
		

		Pane pane = new Pane();
		Pane mapPane = new Pane();
		HBox hBox = new HBox();
		pane.getChildren().add(mapPane);
		pane.getChildren().add(hBox);
		for (int i = 0; i < 10; i++) {
			pane.getChildren().add(gui[i]);
			gui[i].setTranslateX(10 + 80 * i);
			gui[i].setTranslateY(583);
		}
		scene = new Scene(pane);
		mapPane.getChildren().add(mapView);
		//hBox.getChildren().add(gui1);
		//hBox.getChildren().add(gui2);
		mapView.setLayoutX(0);
		mapView.setLayoutY(40);
		hBox.setLayoutX(0);
		hBox.setLayoutY(0);
		hBox.setTranslateX(0);
		hBox.setTranslateY(600);
		//stackPane.setAlignment(Pos.TOP_LEFT);
		scene.setFill(Color.BLACK);
		mapPane.toBack();


		//stage
		stage.setTitle("Test");
		stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.setResizable(false);
        stage.setScene(scene);

        stage.show();  



	}





	public static void main(String[] args) {
         Application.launch(args);
     }
}