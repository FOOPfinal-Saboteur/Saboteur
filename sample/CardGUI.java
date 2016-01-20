package sample;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
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
import javafx.stage.StageStyle;
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

	public static final String roadBackURL = "./img/game/Road_Back.png";
	public static final String destBackURL = "./img/game/Destination_Back.png";

	public/* static*/ Stage win;

	public static final boolean routeProperty[][] = 
	{{true, false, true, false, false},
	{true, false, false, true, false},
	{true, true, false, false, false},
	{false, false, true, false, false},
	{true, false, false, false, false},
	{true, true, true, false, false},
	{true, true, true, true, false},
	{false, false, true, true, false},
	{true, false, true, true, false},
	{false, false, true, true, true},
	{true, false, true, true, true},
	{true, false, true, false, true},
	{true, false, false, true, true},
	{true, true, false, false, true},
	{true, true, true, false, true},
	{true, true, true, true, true}}; 
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int CENTER = 4;

	public static final int NON_FUNC = 0;
	public static final int BREAK_CART = 16;
	public static final int BREAK_LAMP = 17;
	public static final int BREAK_PICK = 18;
	public static final int FIX_CART = 19;
	public static final int FIX_LAMP = 20;
	public static final int FIX_PICK = 21;
	public static final int FIX_CART_PICK = 22;
	public static final int FIX_LAMP_CART = 23;
	public static final int FIX_PICK_LAMP = 24;
	public static final int COLLAPSE = 25;
	public static final int MAP = 26;

	private Image image;

	private ImageView iv;

	static boolean isFading = false;
	

	private double initX;
	private double initY;
	private double dragTempX;
	private double dragTempY;
	private Point2D dragAnchor = new Point2D(0,0);

	private FadeTransition fadeTransition = new FadeTransition();
	private TranslateTransition translateTransition;
	private RotateTransition rotateTransition;
	private ScaleTransition scaleTransition;
	private ParallelTransition parallelTransition;

	private DropShadow dropShadow;

	private int handIndex;

	private int cardID;

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

	public CardGUI(Image newImage, int id) {
		super(newImage);
		image = newImage;	
		isFixed = false;
		upSideDown = false;
		if (id <= 15)
			isFunc = false;
		else
			isFunc = true;
		cardID = id;
	}

	public CardGUI(int id, String url) {
		super();
		image = new Image(url);
		this.setImage(image);
		isFixed = false;
		upSideDown = false;
		for (int i = 0; i < 27; i++)
			if (url.equals(Shuffler.cardInfo[i][1])) {
				if (i <= 15)
					isFunc = false;
				else
					isFunc = true;
				cardID = i;
			}
		handIndex = id;
	}

	public CardGUI(String url) {
		super();
		image = new Image(url);
		this.setImage(image);
		isFixed = false;
		upSideDown = false;
		for (int i = 0; i < 27; i++)
			if (url.equals(Shuffler.cardInfo[i][1])) {
				if (i <= 15)
					isFunc = false;
				else
					isFunc = true;
				cardID = i;
			}

		
	}
	public CardGUI(String url, boolean func) {
		super();
		image = new Image(url);
		this.setImage(image);
		isFixed = false;
		isFunc = func;
		upSideDown = false;
		for (int i = 0; i < 27; i++)
			if (url.equals(Shuffler.cardInfo[i][1])) {
				cardID = i;
			}
	}
	public CardGUI(String url, int id) {
		super();
		image = new Image(url);
		this.setImage(image);
		isFixed = false;
		upSideDown = false;
		if (id <= 15)
			isFunc = false;
		else
			isFunc = true;
		cardID = id;
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
	public int getCardID() {
		return cardID;
	}
	public static void initCard(CardGUI iv) {
		iv.setFitHeight(105);
		iv.setPreserveRatio(true);
		iv.setSmooth(true);
		iv.setCache(true);
	}
	public int funcCardType(CardGUI card) {
		if (cardID>= 16)
			return cardID;
		return 0;
	}

	public void setUpSideDown(boolean value) {
		upSideDown = value;
	}

	public void setHandler(MapGUI map, ArrayList<CardGUI> gui, Shuffler shuffler, Pane pane) {
		dropShadow = new DropShadow();
        CardGUI.this.setEffect(dropShadow);
        colorChangeFlag = false;
        isUsed = false;
        deleted = false;

		this.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				if (isFixed || isFading)
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
				Main.cardid = handIndex;
				if (isFixed || (parallelTransition != null && parallelTransition.getStatus() == Status.RUNNING) || isFading)
					return;
				CardGUI.this.toFront();
				CardGUI.this.setCursor(Cursor.CLOSED_HAND);
				if (translateTransition == null){
					initX = CardGUI.this.getTranslateX();
					initY = CardGUI.this.getTranslateY();
				}
				dragTempX = initX;
				dragTempY = initY;
				dragAnchor = new Point2D(me.getSceneX(), me.getSceneY());
				System.out.printf("Press detected!! (initX, initY, mouseX, mouseY) = (%.0f, %.0f, %.0f, %.0f)\n", initX, initY, me.getSceneX(), me.getSceneY());
				System.out.printf("CardID = %d (%s), ", CardGUI.this.getCardID(), Shuffler.cardInfo[CardGUI.this.getCardID()][0]);
				System.out.println("isUpSideDown = " + upSideDown);
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
					Main.is_rotate = !Main.is_rotate;
					Main.cardid = handIndex;
					Main.window2.close();

				}
			}
		});
		this.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				if (isFixed || isFading)
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
					Main.gx = i; Main.gy = j;
					if ( (!map.isOccupied(i, j) && Main.sts[Main.nowPlayer].isActive() && !CardGUI.this.isFunc
							&& map.canLegallyPlace(CardGUI.this, i, j)) ||
						/*map*/(CardGUI.this.cardID == MAP && i == 8 && (j == 0 || j == 2 || j == 4)) ) 
						map.setLightGreen(i, j, true);
					else if (/*collapse*/(CardGUI.this.cardID == COLLAPSE && map.isOccupied(i, j) && (!(i == 0 && j == 2)&& !(i == 8 && (j == 0 || j == 2 || j == 4))) )) {
						map.cardToBack(i, j);
						map.setLightRed(i, j, true);
						map.collapseGreen(i, j, true);
					}
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
				//System.out.printf("(%.1f, %.1f)\n", me.getSceneX(), me.getSceneY());
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
                //System.out.printf("Drag detected!! (dragX, dragY) = (%.0f, %.0f)\n", dragX, dragY);
                dragTempX = newXPosition;
               	dragTempY = newYPosition;

				/* Function on Player */
				for(int i = 0; i < Main.playerNumber; i++) {
					if (me.getSceneX() >= 680 && me.getSceneX() < 960 &&
							me.getSceneY() > 0+i*63 && me.getSceneY() <= 63+i*63) {
							switch (CardGUI.this.getCardID()){
								case 16:
									if(Main.sts[i].mine_cartOK())
										Main.ctrl.setStatusGreen(true, i);
									else Main.ctrl.setStatusRed(true, i);
									break;
								case 17:
									if(Main.sts[i].oil_lampOK())
										Main.ctrl.setStatusGreen(true, i);
									else Main.ctrl.setStatusRed(true, i);
									break;
								case 18:
									if(Main.sts[i].pickOK())
										Main.ctrl.setStatusGreen(true, i);
									else Main.ctrl.setStatusRed(true, i);
									break;
								case 19:
									if(!Main.sts[i].mine_cartOK())
										Main.ctrl.setStatusGreen(true, i);
									else Main.ctrl.setStatusRed(true, i);
									break;
								case 20:
									if(!Main.sts[i].oil_lampOK())
										Main.ctrl.setStatusGreen(true, i);
									else Main.ctrl.setStatusRed(true, i);
									break;
								case 21:
									if(!Main.sts[i].pickOK())
										Main.ctrl.setStatusGreen(true, i);
									else Main.ctrl.setStatusRed(true, i);
									break;
								case 22:
									if(!Main.sts[i].mine_cartOK() || !Main.sts[i].pickOK())
										Main.ctrl.setStatusGreen(true, i);
									else Main.ctrl.setStatusRed(true, i);
									break;
								case 23:
									if(!Main.sts[i].oil_lampOK() || !Main.sts[i].mine_cartOK())
										Main.ctrl.setStatusGreen(true, i);
									else Main.ctrl.setStatusRed(true, i);
									break;
								case 24:
									if(!Main.sts[i].pickOK() || !Main.sts[i].oil_lampOK())
										Main.ctrl.setStatusGreen(true, i);
									else Main.ctrl.setStatusRed(true, i);
									break;
								default:
									break;
							}
					} else {
						Main.ctrl.setStatusGreen(false, i);
					}
				}
			}
		});
		this.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				if (isFixed || isFading)
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
                //System.out.println("map.canLegallyPlace: "+map.canLegallyPlace(CardGUI.this, i, j));
                if ((!map.isOccupied(i, j) && map.isGreen(i, j) && map.canLegallyPlace(CardGUI.this, i, j) && Main.sts[Main.nowPlayer].isActive())

                /*me.getSceneX() >= targetX && me.getSceneX() <= targetX + (MapGUI.coordEndX-MapGUI.coordStartX) * map.getRatio() && 
                	me.getSceneY() >= targetY && me.getSceneY() <= targetY + (MapGUI.coordEndY-MapGUI.coordStartY) * map.getRatio()*/) {
					Main.cardid = handIndex;
					Main.window2.close();
					/*win = new Stage();
					win.initStyle(StageStyle.TRANSPARENT);
					win.initOwner(Main.window);
					win.showAndWait();*/
//					if(Main.can_place) {
						Main.ctrl.onMap.add(Main.ctrl.gui.get(handIndex));
						Main.ctrl.gui.remove(handIndex);
						//Main.msx = me.getSceneX();
						//Main.msy = me.getSceneY();

						map.setOccupied(i, j, true);
						map.setLightGreen(i, j, false);
						colorChangeFlag = false;
						map.setOccupyCard(i, j, CardGUI.this);
						CardGUI.this.setCursor(Cursor.DEFAULT);
						System.out.printf("(%d, %d) got a card!\n", i, j);
						translateTransition = new TranslateTransition(Duration.seconds(0.25), CardGUI.this);
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
						scaleTransition = new ScaleTransition(Duration.seconds(0.25), CardGUI.this);
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
						/*For finally, to reveal the answer, pending*/
						/*if ((i == 7 && (j == 0 || j == 2 || j == 4)) || (i == 8 && (j == 1 || j == 3)) ) {
							CardGUI temp = new CardGUI(Shuffler.cardInfo[15][1], 15);
							if ((i == 7 && j == 0)|| (i == 8 && j == 1) ) {
								if (map.canLegallyPlace(temp, 8, 0))
									map.revealDest(0);
							}
							if ((i == 7 && j == 2)|| (i == 8 && j == 1)|| (i == 8 && j == 3)) {
								if (map.canLegallyPlace(temp, 8, 2))
									map.revealDest(1);
							}
							if ((i == 7 && j == 4)|| (i == 8 && j == 3)) {
								if (map.canLegallyPlace(temp, 8, 4))
									map.revealDest(2);
							}
						}*/
                  /*For finally, to reveal the answer, pending*/
//					}

               }
               else if (/*map*/(CardGUI.this.cardID == MAP && i == 8 && (j == 0 || j == 2 || j == 4)) && map.isGreen(i, j)) {
					Main.cardid = handIndex;
					Main.window2.close();
					//if(Main.can_see) {
						map.setLightGreen(i, j, false);
						translateTransition = new TranslateTransition(Duration.seconds(0.25), CardGUI.this);
						translateTransition.setFromX(CardGUI.this.getTranslateX());
						translateTransition.setToX(targetX + 15);
						translateTransition.setFromY(CardGUI.this.getTranslateY());
						translateTransition.setToY(targetY + 33);
						translateTransition.setCycleCount(1);
						translateTransition.setAutoReverse(false);
						scaleTransition = new ScaleTransition(Duration.seconds(0.25), CardGUI.this);
						scaleTransition.setFromX(1);
						scaleTransition.setToX(0);
						scaleTransition.setFromY(1);
						scaleTransition.setToY(0);
						scaleTransition.setCycleCount(1);
						scaleTransition.setAutoReverse(false);
						parallelTransition = new ParallelTransition(CardGUI.this, translateTransition, scaleTransition);
						fadeTransition = new FadeTransition(Duration.seconds(3), map.getDest(j));
						fadeTransition.setFromValue(0);
						fadeTransition.setToValue(1);
						fadeTransition.setCycleCount(2);
						fadeTransition.setAutoReverse(true);
						parallelTransition.play();
						isFading = true;
						//FadeTransition ft = new FadeTransition(Duration.millis(1000), null);
						//Main.ctrl.pane.setDisable(true);
						fadeTransition.onFinishedProperty().set(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent actionEvent) {
								isFading = false;
								Main.window3.close();
							}
						});
						fadeTransition.play();

					//SequentialTransition seq = new SequentialTransition(fadeTransition,ft);
						isUsed = true;
						deleted = true;
						flag = true;


					//}
               }
				else if (/*collapse*/(CardGUI.this.cardID == COLLAPSE && map.isOccupied(i, j) && (!(i == 0 && j == 2)&& !(i == 8 && (j == 0 || j == 2 || j == 4))) && map.isGreen(i, j))) {
					Main.cardid = handIndex;
					Main.window2.close();
					Main.ctrl.onMap.remove(map.getObjectCard(i,j));

					map.toBack();
					map.cardToFront(i, j);
					CardGUI.this.toFront();
					map.setLightRed(i, j, false);
					translateTransition = new TranslateTransition(Duration.seconds(0.5),CardGUI.this);
					translateTransition.setFromX(CardGUI.this.getTranslateX());
					translateTransition.setToX(targetX+15);
					translateTransition.setFromY(CardGUI.this.getTranslateY());
					translateTransition.setToY(targetY+33);
					translateTransition.setCycleCount(1);
					translateTransition.setAutoReverse(false);
					scaleTransition = new ScaleTransition(Duration.seconds(0.5),CardGUI.this);
					scaleTransition.setFromX(1);
					scaleTransition.setToX(0);
					scaleTransition.setFromY(1);
					scaleTransition.setToY(0);
					scaleTransition.setCycleCount(1);
					scaleTransition.setAutoReverse(false);
					parallelTransition = new ParallelTransition(CardGUI.this, translateTransition, scaleTransition);
					parallelTransition.play();

					ScaleTransition temp = new ScaleTransition(Duration.seconds(0.5), map.getObjectCard(i, j));
					temp.setFromX(1);
					temp.setToX(0);
					temp.setFromY(1);
					temp.setToY(0);
					temp.setCycleCount(1);
					temp.setAutoReverse(false);
					temp.play();
					map.deleteCard(i, j);

					isUsed = true;
					deleted = true;
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
					Main.cardid = handIndex;
					Main.discard = true;
					Main.window2.close();
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
           		/*for (int i = 0; i < DoubleLayout.hand_num; i++) {
						if (gui.size() >= i+1 && gui.get(i).isUsed()) {
							*//*if (DoubleLayout.gui[i].isDeleted()) {
								DoubleLayout.pane.getChildren().remove(DoubleLayout.gui[i]);
							}	*//*
							gui.remove(i);
							for (int j = i; j < gui.size(); j++) {
								gui.get(j).setTranslateX(10 + 80 * j);
								gui.get(j).changeInit(10 + 80 * j, 583);
							}
							CardGUI temp = shuffler.takeCard();
							if (temp == null)
								continue;
							temp.setHandler(map, gui, shuffler, pane);
							gui.add(temp);
							initCard(gui.get(gui.size() - 1));
							pane.getChildren().add(gui.get(gui.size() - 1));
							gui.get(gui.size() - 1).setTranslateX(10 + 80 * (gui.size() - 1));
							gui.get(gui.size() - 1).setTranslateY(583);
							*//*if (DoubleLayout.gui[i] != null) {
								DoubleLayout.initCard(DoubleLayout.gui[i]);
								DoubleLayout.pane.getChildren().add(DoubleLayout.gui[i]);
								DoubleLayout.gui[i].setTranslateX(10 + 80 * i);
								DoubleLayout.gui[i].setTranslateY(583);
							}*//*
						}
					}*/
				/* Function on Player */
				for(int i = 0; i < Main.playerNumber; i++) {
					if (me.getSceneX() >= 680 && me.getSceneX() < 960 &&
							me.getSceneY() > 0+i*63 && me.getSceneY() <= 63+i*63) {

						Main.cardid = handIndex;
						Main.whomid = i;
						if(CardGUI.this.getCardID()<=24 && CardGUI.this.getCardID()>=16)
							Main.window2.close();

						switch (CardGUI.this.getCardID()){
							case 16:
								if(Main.sts[i].mine_cartOK()) {
									//Main.ctrl.attack(i, 2);
									System.out.println("Player"+i+" is attacked!");
								}
								break;
							case 17:
								if(Main.sts[i].oil_lampOK()) {
									//Main.ctrl.attack(i, 1);
									System.out.println("Player"+i+" is attacked!");
								}
								break;
							case 18:
								if(Main.sts[i].pickOK()) {
									//Main.ctrl.attack(i, 0);
									System.out.println("Player"+i+" is attacked!");
								}
								break;
							case 19:
								if(!Main.sts[i].mine_cartOK()) {
									Main.ctrl.rescue(i, 2);
								}
								break;
							case 20:
								if(!Main.sts[i].oil_lampOK()) {
									Main.ctrl.rescue(i, 1);
								}
								break;
							case 21:
								if(!Main.sts[i].pickOK()) {
									Main.ctrl.rescue(i, 0);
								}
								break;
							case 22:
								if(!Main.sts[i].mine_cartOK() && !Main.sts[i].pickOK()) {
									ChooseBox.display("Fix item", "Choose one to fix", "mine_cart", "pick");
									Main.window3.close();
								}else if(!Main.sts[i].mine_cartOK()){
									Main.ctrl.rescue(i, 2);
								}else if(!Main.sts[i].pickOK()){
									Main.ctrl.rescue(i, 0);
								}
								break;
							case 23:
								if(!Main.sts[i].oil_lampOK() && !Main.sts[i].mine_cartOK()){
									ChooseBox.display("Fix item", "Choose one to fix", "lamp", "cart");
									Main.window3.close();
								}else if(!Main.sts[i].oil_lampOK()){
									Main.ctrl.rescue(i, 1);
								}else if(!Main.sts[i].mine_cartOK()){
									Main.ctrl.rescue(i, 2);
								}
								break;
							case 24:
								if(!Main.sts[i].pickOK() && !Main.sts[i].oil_lampOK()){
									ChooseBox.display("Fix item", "Choose one to fix", "pick", "lamp");
									Main.window3.close();
								}else if(!Main.sts[i].pickOK()){
									Main.ctrl.rescue(i, 0);
								}else if(!Main.sts[i].oil_lampOK()){
									Main.ctrl.rescue(i, 1);
								}
								break;
							default:
								break;
						}
					} else {
						Main.ctrl.setStatusGreen(false, i);
					}
				}
			}
		});
		this.setOnMouseExited(new EventHandler<MouseEvent>() {   
            public void handle(MouseEvent me) {
            	if (isFixed || isFading )
            		return;
            	CardGUI.this.setCursor(Cursor.DEFAULT);
               dropShadow = new DropShadow();
               CardGUI.this.setEffect(dropShadow);

            }
         });
	}
}