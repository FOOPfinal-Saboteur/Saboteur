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


	public CardGUI() {
		super();
		isFixed = false;
		upSideDown = false;
		setHandler(null);
	}

	public CardGUI(Image newImage, MapGUI map) {
		super(newImage);
		image = newImage;	
		isFixed = false;
		isFunc = false;
		upSideDown = false;
		setHandler(map);
	}
	public CardGUI(Image newImage, MapGUI map, boolean func) {
		super(newImage);
		image = newImage;	
		isFixed = false;
		isFunc = func;
		upSideDown = false;
		setHandler(map);
	}

	public CardGUI(String url, MapGUI map) {
		super();
		image = new Image(url);
		this.setImage(image);
		isFixed = false;
		isFunc = false;
		upSideDown = false;
		setHandler(map);
	}

	public CardGUI(String url, MapGUI map, boolean func) {
		super();
		image = new Image(url);
		this.setImage(image);
		isFixed = false;
		isFunc = func;
		upSideDown = false;
		setHandler(map);
	}

	public boolean isFunc() {
		return isFunc;
	}

	
	private void setHandler(MapGUI map) {

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
				if (isFixed )
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
                	if (isFunc) break;
                	for (int j = 0; j < 5 && flag != true; j++) {
                
                targetX = 13+(-12+MapGUI.coordStartX + i * MapGUI.coordDistanceX) * map.getRatio();
                targetY = (52+MapGUI.coordStartY + j * MapGUI.coordDistanceY) * map.getRatio();

                if (!map.isOccupied(i, j) && me.getSceneX() >= targetX && me.getSceneX() <= targetX + (MapGUI.coordEndX-MapGUI.coordStartX) * map.getRatio() && 
                	me.getSceneY() >= targetY && me.getSceneY() <= targetY + (MapGUI.coordEndY-MapGUI.coordStartY) * map.getRatio()) {
                  map.setOccupied(i, j, true);
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

                  dropShadow = new DropShadow();
               	  CardGUI.this.setEffect(dropShadow);
                  isFixed = true;
                  flag = true;
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
           		if (!flag) {
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