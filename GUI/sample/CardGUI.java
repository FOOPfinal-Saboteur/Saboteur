package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

//event handler
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
//image
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//Color
import javafx.scene.paint.Color;

import javafx.geometry.Point2D;

//for transition
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.ParallelTransition;
import javafx.util.Duration;
import javafx.animation.Animation.Status;

//for effect
import javafx.scene.effect.Effect;
import javafx.scene.effect.DropShadow;

//cursor
import javafx.scene.Cursor;
//sleep
import java.util.concurrent.TimeUnit;
//arraylist
import java.util.ArrayList;
//Pane
import javafx.scene.layout.Pane;

public class CardGUI extends javafx.scene.image.ImageView {

	public static final String backURL = "./img/game/Road_Back.png";

	private Image image;

	private ImageView iv;


	

	private double initX;
	private double initY;
	private double dragTempX;
	private double dragTempY;
	private Point2D dragAnchor;


	private TranslateTransition translateTransition;
	private RotateTransition rotateTransition;
	private ScaleTransition scaleTransition;
	private ParallelTransition parallelTransition;

	private DropShadow dropShadow;

	private boolean isFixed;

	private boolean isFunc;

	private boolean upSideDown;

	private boolean colorChangeFlag;

	private boolean isUsed;

	private boolean deleted;


	public CardGUI() {
		super();
		isFixed = false;
		upSideDown = false;
		
	}

	public CardGUI(Image newImage) {
		super(newImage);
		image = newImage;	
		isFixed = false;
		isFunc = false;
		upSideDown = false;
		
	}
	public CardGUI(Image newImage, boolean func) {
		super(newImage);
		image = newImage;	
		isFixed = false;
		isFunc = func;
		upSideDown = false;
		
	}

	public CardGUI(String url) {
		super();
		image = new Image(url);
		this.setImage(image);
		isFixed = false;
		isFunc = false;
		upSideDown = false;
		
	}
	public CardGUI(String url, boolean func) {
		super();
		image = new Image(url);
		this.setImage(image);
		isFixed = false;
		isFunc = func;
		upSideDown = false;
		
	}

	public boolean isFunc() {
		return isFunc;
	}
	public boolean isUpSideDown() {
		return upSideDown;
	}
	public boolean isUsed() {
		return isUsed;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void changeInit(double newInitX, double newInitY) {
		initX = newInitX;
		initY = newInitY;
	}
	public static void initCard(CardGUI iv) {
		iv.setFitHeight(105);
		iv.setPreserveRatio(true);
		iv.setSmooth(true);
		iv.setCache(true);
	}
	
	public void setHandler(MapGUI map, ArrayList<CardGUI> gui, Shuffler shuffler, Pane pane) {
		dropShadow = new DropShadow();
        CardGUI.this.setEffect(dropShadow);
        colorChangeFlag = false;
        isUsed = false;
        deleted = false;

		this.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				if (isFixed)
					return;
				CardGUI.this.setCursor(Cursor.OPEN_HAND);
				dropShadow = new DropShadow();
				dropShadow.setRadius(15.0);
				dropShadow.setOffsetX(0.0);
				dropShadow.setOffsetY(0.0);
				dropShadow.setColor(Color.YELLOW);
				CardGUI.this.setEffect(dropShadow);
			}
		});

		this.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				if (isFixed || (parallelTransition != null && parallelTransition.getStatus() == Status.RUNNING) )
					return;
				CardGUI.this.toFront();
				CardGUI.this.setCursor(Cursor.CLOSED_HAND);
				if (translateTransition == null){
					initX = CardGUI.this.getTranslateX();
					initY = CardGUI.this.getTranslateY();
				}
				System.out.printf("Mouse pressed!! (initX, initY) = (%.0f, %.0f)\n", initX, initY);
				dragTempX = initX;
				dragTempY = initY;
				dragAnchor = new Point2D(me.getSceneX(), me.getSceneY());
				System.out.printf("Press detected!! (mouseX, mouseY) = (%.0f, %.0f)\n", me.getSceneX(), me.getSceneY());
				if (translateTransition != null)
                  translateTransition.stop();
              	if (!isFunc && me.getClickCount() > 0 && me.getClickCount() % 2 == 0 && (rotateTransition == null||(rotateTransition != null && rotateTransition.getStatus() == Status.STOPPED)) ) {
                  if (upSideDown) upSideDown = false;
                  else upSideDown = true;
                  System.out.println("upSideDown = " + upSideDown);
                  rotateTransition = new RotateTransition(Duration.seconds(0.5), CardGUI.this);
                  rotateTransition.setFromAngle(CardGUI.this.getRotate());
                  rotateTransition.setToAngle(CardGUI.this.getRotate() + 180);
                  rotateTransition.setCycleCount(1);
                  rotateTransition.setAutoReverse(false);
                  rotateTransition.play();
               }
			}
		});
		this.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				if (isFixed)
					return;
				double dragX = me.getSceneX() - dragAnchor.getX();
				double dragY = me.getSceneY() - dragAnchor.getY();
				double newXPosition = initX + dragX;
				double newYPosition = initY + dragY;
				//special effect on target slots on the map
				/*System.out.printf("(%.1f, %.1f, %.1f), (%.1f, %.1f, %.1f)\n", map.getLayoutX() + MapGUI.coordStartX * map.getRatio(),
					me.getSceneX(), map.getLayoutX() + (MapGUI.coordEndX - MapGUI.coordStartX) * map.getRatio(),
					map.getLayoutY() + MapGUI.coordStartY * map.getRatio(), me.getSceneY(), 
					map.getLayoutY() + (MapGUI.coordEndY - MapGUI.coordStartY) * map.getRatio());*/
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 5; j++) {

				if (me.getSceneX() >= map.getLayoutX() + (MapGUI.coordStartX + MapGUI.coordDistanceX * i) * map.getRatio() &&
					me.getSceneX() <= map.getLayoutX() + (MapGUI.coordEndX + MapGUI.coordDistanceX * i) * map.getRatio() && 
					me.getSceneY() >= map.getLayoutY() + (MapGUI.coordStartY + MapGUI.coordDistanceY * j) * map.getRatio() &&
					me.getSceneY() <= map.getLayoutY() + (MapGUI.coordEndY + MapGUI.coordDistanceY * j) * map.getRatio()) {
					if (!map.isOccupied(i, j) && !CardGUI.this.isFunc) map.setLightGreen(i, j, true);
					else {
						if ((i != 0 && j != 2)||(i != 8 && (j != 0 || j != 2 || j != 4))) {
							map.cardToBack(i, j);
						}
						map.setLightRed(i, j, true);
					}
					colorChangeFlag = true;
				}
				else {
					if (colorChangeFlag == false)
						continue;
						//close color change
					if (!map.isOccupied(i, j) && !CardGUI.this.isFunc) map.setLightGreen(i, j, false);
					else {
						if ((i != 0 && j != 2)||(i != 8 && (j != 0 || j != 2 || j != 4))) {
							map.toBack();
							map.cardToFront(i, j);
							CardGUI.this.toFront();
						}
						map.setLightRed(i, j, false);
					}
				}

				}
				}//end of loop 
				System.out.printf("(%.1f, %.1f)\n", me.getSceneX(), me.getSceneY());
				if (me.getSceneX() >= 495 && me.getSceneX() <= 565 && 
					me.getSceneY() >= 582 && me.getSceneY() <= 667) {
					map.setBinGreen(true);
				}
				else map.setBinGreen(false);

				//System.out.printf("(newXPosition, newYPosition) = (%.0f, %.0f)\n", newXPosition, newYPosition);
				if(newXPosition>=0 && newXPosition<960-CardGUI.this.getFitWidth())
                  CardGUI.this.setTranslateX(newXPosition);
             	if(newYPosition>=0 && newYPosition<720-(CardGUI.this.getFitWidth() * image.getHeight() / image.getWidth()) )
                  CardGUI.this.setTranslateY(newYPosition);
                System.out.printf("Drag detected!! (dragX, dragY) = (%.0f, %.0f)\n", dragX, dragY);
                dragTempX = newXPosition;
               	dragTempY = newYPosition;
			}
		});
		this.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				if (isFixed)
					return;
				System.out.printf("Mouse released!! (initX, initY) = (%.0f, %.0f)\n", initX, initY);
				double targetX;
                double targetY;

                boolean flag = false;

                for (int i = 0; i < 9 && flag != true; i++) {
                	//if (isFunc) break;
                	for (int j = 0; j < 5 && flag != true; j++) {
                
                targetX = 13+(-12+MapGUI.coordStartX + i * MapGUI.coordDistanceX) * map.getRatio();
                targetY = (52+MapGUI.coordStartY + j * MapGUI.coordDistanceY) * map.getRatio();

                if (!map.isOccupied(i, j) && map.isGreen(i, j)/*me.getSceneX() >= targetX && me.getSceneX() <= targetX + (MapGUI.coordEndX-MapGUI.coordStartX) * map.getRatio() && 
                	me.getSceneY() >= targetY && me.getSceneY() <= targetY + (MapGUI.coordEndY-MapGUI.coordStartY) * map.getRatio()*/) {
                  map.setOccupied(i, j, true);
                  map.setLightGreen(i, j, false);
                  colorChangeFlag = false;
                  map.setOccupyCard(i, j, CardGUI.this);
              		CardGUI.this.setCursor(Cursor.DEFAULT);
              		System.out.printf("(%d, %d) got a card!\n", i, j);
                  translateTransition = new TranslateTransition(Duration.seconds(0.25),CardGUI.this);
                  translateTransition.setFromX(CardGUI.this.getTranslateX());
                  translateTransition.setToX(targetX);
                  translateTransition.setFromY(CardGUI.this.getTranslateY());
                  translateTransition.setToY(targetY);
                  translateTransition.setCycleCount(1);
                  translateTransition.setAutoReverse(false);
                  /*translateTransition = TranslateTransitionBuilder.create()
                           .duration(Duration.seconds(2))
                           .node(iv2)
                           .fromX(initX)
                           .toX(targetX)
                           .fromY(initY)
                           .toY(targetY)
                           .cycleCount(1)
                           .autoReverse(false)
                           .build();*/
                  scaleTransition = new ScaleTransition(Duration.seconds(0.25),CardGUI.this);
                  scaleTransition.setFromX(1);
                  scaleTransition.setToX(0.85);
                  scaleTransition.setFromY(1);
                  scaleTransition.setToY(0.85);
                  //scaleTransition.setByX(0.05/*(MapGUI.coordEndX-MapGUI.coordStartX) * map.getRatio() / CardGUI.this.getFitWidth()*/);
                  //scaleTransition.setByY(0.05/*(MapGUI.coordEndX-MapGUI.coordStartX) * map.getRatio() / (CardGUI.this.image.getHeight() * CardGUI.this.getFitWidth() / CardGUI.this.image.getWidth())*/);
                  scaleTransition.setCycleCount(1);
                  scaleTransition.setAutoReverse(false);
                  //translateTransition.play();
                  parallelTransition = new ParallelTransition(CardGUI.this, translateTransition, scaleTransition);

                  parallelTransition.play();
                  isUsed = true;

                  dropShadow = new DropShadow();
               	  CardGUI.this.setEffect(dropShadow);
                  isFixed = true;
                  flag = true;
               }
               else if (map.isRed(i, j)) {
               	  map.setLightRed(i, j, false);
               	  if ((i != 0 && j != 2)||(i != 8 && (j != 0 || j != 2 || j != 4))) {
						map.toBack();
						map.cardToFront(i, j);
						CardGUI.this.toFront();
					}
                  colorChangeFlag = false;
               }
               /*else {
               	  CardGUI.this.setCursor(Cursor.OPEN_HAND);
                  translateTransition = new TranslateTransition(Duration.seconds(0.25),CardGUI.this);
                  translateTransition.setFromX(CardGUI.this.getTranslateX());
                  translateTransition.setToX(initX);
                  translateTransition.setFromY(CardGUI.this.getTranslateY());
                  translateTransition.setToY(initY);
                  translateTransition.setCycleCount(1);
                  translateTransition.setAutoReverse(false);
                  translateTransition.play();
               }*/
               	}
           		}//end of loop
           		if (!flag && !map.isBinGreen()) {
           		  CardGUI.this.setCursor(Cursor.OPEN_HAND);
                  translateTransition = new TranslateTransition(Duration.seconds(0.25),CardGUI.this);
                  translateTransition.setFromX(CardGUI.this.getTranslateX());
                  translateTransition.setToX(initX);
                  translateTransition.setFromY(CardGUI.this.getTranslateY());
                  translateTransition.setToY(initY);
                  translateTransition.setCycleCount(1);
                  translateTransition.setAutoReverse(false);
                  translateTransition.play();
           		}
           		if (map.isBinGreen()) {
					CardGUI.this.setCursor(Cursor.OPEN_HAND);
					translateTransition = new TranslateTransition(Duration.seconds(0.5),CardGUI.this);
					translateTransition.setFromX(CardGUI.this.getTranslateX());
					translateTransition.setToX(490);
					translateTransition.setFromY(CardGUI.this.getTranslateY());
					translateTransition.setToY(580);
					translateTransition.setCycleCount(1);
                  	translateTransition.setAutoReverse(false);
                  	scaleTransition = new ScaleTransition(Duration.seconds(0.5),CardGUI.this);
                  	scaleTransition.setFromX(1);
                  	scaleTransition.setToX(0.0);
                  	scaleTransition.setFromY(1);
                  	scaleTransition.setToY(0.0);
                  	scaleTransition.setCycleCount(1);
                  	scaleTransition.setAutoReverse(false);
                  	rotateTransition = new RotateTransition(Duration.seconds(0.5), CardGUI.this);
                  	rotateTransition.setFromAngle(CardGUI.this.getRotate());
                  	rotateTransition.setToAngle(CardGUI.this.getRotate() + 540);
                  	rotateTransition.setCycleCount(1);
                  	rotateTransition.setAutoReverse(false);
                  	parallelTransition = new ParallelTransition(CardGUI.this, translateTransition, scaleTransition, rotateTransition);
                  	parallelTransition.play();
                  	System.out.printf("Card deleted!!\n");
                  	isUsed = true;
                  	deleted = true;
                  	map.toFullBin();
           		}
           		map.setBinGreen(false);

           		//while(parallelTransition.getStatus() != Status.STOPPED);
           		
           		//try {TimeUnit.MILLISECONDS.sleep(500);}catch (InterruptedException e) {}

           		/*referenced from DoubleLayout*/
           		for (int i = 0; i < 6; i++) {
						if (gui.size() >= i+1 && gui.get(i).isUsed()) {
							/*if (DoubleLayout.gui[i].isDeleted()) {
								DoubleLayout.pane.getChildren().remove(DoubleLayout.gui[i]);
							}	*/
							gui.remove(i);
							for (int j = i; j < gui.size(); j++) {
								gui.get(j).setTranslateX(20 + 80 * j);
								gui.get(j).changeInit(20 + 80 * j, 590);
							}
							CardGUI temp = shuffler.takeCard();
							if (temp == null)
								continue;
							temp.setHandler(map, gui, shuffler, pane);
							gui.add(temp);
							initCard(gui.get(gui.size() - 1));
							pane.getChildren().add(gui.get(gui.size() - 1));
							gui.get(gui.size() - 1).setTranslateX(20 + 80 * (gui.size() - 1));
							gui.get(gui.size() - 1).setTranslateY(590);
							/*if (DoubleLayout.gui[i] != null) {
								DoubleLayout.initCard(DoubleLayout.gui[i]);
								DoubleLayout.pane.getChildren().add(DoubleLayout.gui[i]);
								DoubleLayout.gui[i].setTranslateX(20 + 80 * i);
								DoubleLayout.gui[i].setTranslateY(590);
							}*/
						}
					}
			}
		});
		this.setOnMouseExited(new EventHandler<MouseEvent>() {   
            public void handle(MouseEvent me) {
            	if (isFixed)
            		return;
            	CardGUI.this.setCursor(Cursor.DEFAULT);
               dropShadow = new DropShadow();
               CardGUI.this.setEffect(dropShadow);

            }
         });
	}
}