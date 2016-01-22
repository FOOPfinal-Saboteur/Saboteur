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

import java.util.concurrent.TimeUnit;
import javafx.concurrent.*;

//arraylist
import java.util.ArrayList;




public class DoubleLayout extends Application {

	Scene scene;

	public static final double WIDTH = 960;
	public static final double HEIGHT = 720;

	public static int hand_num = 4;

	public MapGUI mapView;

	public ArrayList<CardGUI> gui;

	//public static CardGUI[] gui;
	public Shuffler shuffler;

	public Pane pane;
	


	/*public void run() {
		return;
		System.out.printf("new thread!!!\n");
		//try{TimeUnit.SECONDS.sleep(5);}catch(InterruptedException e){}

			while(true) {
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch(InterruptedException e){}
				System.out.printf("now now\n");*/
				/*if (shuffler == null) {
					System.out.printf("Null!!(thread)\n");
					return;
				}*/
				
				/*for (int i = 0; i < 6; i++) {
					if (gui[i] != null && gui[i].isUsed()) {
						if (gui[i].isDeleted()) {
							pane.getChildren().remove(gui[i]);
						}
						gui[i] = shuffler.takeCard();
						initCard(gui[i]);
						pane.getChildren().add(gui[i]);
						gui[i].setTranslateX(10 + 80 * i);
						gui[i].setTranslateY(583);
					}
				}
			}

	}*/

	public static void initCard(ImageView iv) {
		iv.setFitHeight(105);
		iv.setPreserveRatio(true);
		iv.setSmooth(true);
		iv.setCache(true);
	}


	@Override public void start(Stage stage) {

		mapView = new MapGUI(538);



		

		shuffler = new Shuffler();
		shuffler.shuffle();

		/*gui = new CardGUI[6];
		for (int i = 0; i < 6; i++) {
			gui[i] = shuffler.takeCard();
			initCard(gui[i]);
		}*/
		gui = new ArrayList<CardGUI>();
		for (int i = 0; i < hand_num; i++) {
			gui.add(shuffler.takeCard());
			initCard(gui.get(i));
		}

		pane = new Pane();
		//Pane mapPane = new Pane();
		Pane mapPane = mapView.initPane();
		HBox hBox = new HBox();
		pane.getChildren().add(mapPane);
		pane.getChildren().add(hBox);
		for (int i = 0; i < hand_num; i++) {
			pane.getChildren().add(gui.get(i));
			gui.get(i).setTranslateX(10 + 80 * i);
			gui.get(i).setTranslateY(583);
		}
		for (int i = 0; i < hand_num; i++)
			gui.get(i).setHandler(mapView, gui, shuffler, pane);
		scene = new Scene(pane);
		//mapPane.getChildren().add(mapView);
		//hBox.getChildren().add(gui1);
		//hBox.getChildren().add(gui2);
		mapView.setLayoutX(13);
		mapView.setLayoutY(40);
		hBox.setLayoutX(0);
		hBox.setLayoutY(0);
		hBox.setTranslateX(0);
		hBox.setTranslateY(600);
		//stackPane.setAlignment(Pos.TOP_LEFT);
		scene.setFill(Color.BLACK);
		mapPane.toBack();

		/*DoubleLayout obj = new DoubleLayout();
		Thread thr = new Thread(obj);
		thr.start();*/

		//stage
		stage.setTitle("Test");
		stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.setResizable(false);
        stage.setScene(scene);

        stage.show();  
        

        /*Task task = new Task<Void>() {
        	 protected Void call() throws Exception {
        	 	while(true) {
    				try {
						TimeUnit.SECONDS.sleep(1);
					} catch(InterruptedException e){}
					for (int i = 0; i < 6; i++) {
						if (gui[i] != null && gui[i].isUsed()) {
							if (gui[i].isDeleted()) {
								System.out.printf("uw4gf\n");
								((Pane)(gui[i].getParent())).getChildren().remove(gui[i]);
								//pane.getChildren().remove(gui[i]);
								System.out.printf("vw4gf\n");
							}	
							gui[i] = shuffler.takeCard();
							initCard(gui[i]);
							pane.getChildren().add(gui[i]);
							gui[i].setTranslateX(10 + 80 * i);
							gui[i].setTranslateY(583);
						}
					}
    			}
    			
    			
    		}
        };
        new Thread(task).start();*/
    	
        
	}





	public static void main(String[] args) {
		

         Application.launch(args);
     }
}