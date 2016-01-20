//image
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//shape:rectangle
import javafx.scene.shape.*;
//layout
import javafx.scene.layout.Pane;
import javafx.scene.Parent;
//Color
import javafx.scene.paint.Color;
//for effect
import javafx.scene.effect.Effect;
import javafx.scene.effect.DropShadow;
//for transition
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.util.Duration;
import javafx.animation.Animation.Status;
//random
import java.util.*;


public class MapGUI extends javafx.scene.image.ImageView {
	public static final double coordStartX = 56;
	public static final double coordStartY = 59;
	public static final double coordEndX = 152;
	public static final double coordEndY = 207;
	public static final double coordDistanceX = 107;
	public static final double coordDistanceY = 159;

	public static final String binURL[] = {"file:"+System.getProperty("user.dir")+"/src/sample/img/Recycle_Bin_Empty.png", "file:"+System.getProperty("user.dir")+"/src/sample/img/Recycle_Bin_Full.png"};

	public static final String destURL[] = {"file:"+System.getProperty("user.dir")+"/src/sample/img/game/Destination_Gold.png",
											"file:"+System.getProperty("user.dir")+"/src/sample/img/game/Destination_stoneJ.png",
											"file:"+System.getProperty("user.dir")+"/src/sample/img/game/Destination_stoneL.png"};

	private Image binImage;
	private ImageView binImageView;
	private Image mapImage;
	private Rectangle binRec;

	public double fitHeight;
	public double fitWidth;

	private int result[];

	private double ratio;

	private CoordProperty[][] property;

	private boolean[][] traced;

	private FadeTransition fadeTransition;
	private RotateTransition rotateTransition;

	class CoordProperty {
		public boolean isOccupied;
		public Rectangle rectangle;
		public CardGUI card;
		public CardGUI cardAddTemp;
		public boolean[] openRoute;

		public CoordProperty() {
			isOccupied = false;
		}
	}

	public MapGUI() {
		super();
		mapImage = new Image("file:"+System.getProperty("user.dir")+"/src/sample/img/game/Z_Map.jpg");
		this.setImage(mapImage);
		fitHeight = mapImage.getHeight();
		fitWidth = mapImage.getWidth();
		ratio = 1;
		initMapView();
		initProperty();
		initResult();
	}
	public MapGUI(double value) {
		super();
		mapImage = new Image("file:"+System.getProperty("user.dir")+"/src/sample/img/game/Z_Map.jpg");
		this.setImage(mapImage);
		fitHeight = mapImage.getHeight();
		fitWidth = mapImage.getWidth();
		ratio = 1;
		initMapView();
		initProperty();
		initResult();

		resizeHeight(value);
	}
	private void initProperty() {
		property = new CoordProperty[9][5];
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 5; j++) {
				property[i][j] = new CoordProperty();
				if (i == 0 && j == 2)  {
					property[i][j].isOccupied = true;
					property[i][j].openRoute = new boolean[5];
					property[i][j].openRoute[0] = true;
					property[i][j].openRoute[1] = true;
					property[i][j].openRoute[2] = true;
					property[i][j].openRoute[3] = true;
					property[i][j].openRoute[4] = true;
				}
				else if (i == 8 && (j == 0 || j == 2 || j == 4)) {
					property[i][j].isOccupied = true;
					property[i][j].openRoute = new boolean[5];
					property[i][j].openRoute[0] = true;
					property[i][j].openRoute[1] = true;
					property[i][j].openRoute[2] = true;
					property[i][j].openRoute[3] = true;
					property[i][j].openRoute[4] = true;
				}
			}
	}
	private void initMapView() {
		this.setPreserveRatio(true);
		this.setSmooth(true);
		this.setCache(true);
	}
	public void initResult() {
		result = new int[3];
		for (int i = 0; i < 3; i++)
			result[i] = i;
		Random randomGenerator = new Random();
		for (int i = 3; i > 1; i--) {
			int r = randomGenerator.nextInt(i);
			int temp = result[i - 1];
			result[i - 1] = result[r];
			result[r] = temp;
		}

	}
	public CardGUI getDest(int i) {
		return property[8][i].card;
	}
	public CardGUI getObjectCard(int i, int j) {
		return property[i][j].card;
	}
	public void deleteCard(int i, int j) {
		property[i][j].card = null;
		property[i][j].cardAddTemp = null;
		property[i][j].isOccupied = false;
	}
	public Pane initPane() {
		Pane pane = new Pane();
		pane.getChildren().add(this);
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 5; j++) {
				if (i == 8 && (j == 0 || j == 2 || j == 4)) {
						property[i][j].card = new CardGUI(destURL[result[j / 2]], 100 + result[j / 2]);
						property[i][j].card.setFitWidth((coordEndX - coordStartX) * (ratio+0.04));
						property[i][j].card.setPreserveRatio(true);
						property[i][j].card.setSmooth(true);
						property[i][j].card.setCache(true);
						pane.getChildren().add(property[i][j].card);
						property[i][j].card.setTranslateX(-16*ratio+coordStartX + i * coordDistanceX * ratio);
						property[i][j].card.setTranslateY(28*ratio+coordStartY + j * coordDistanceY * ratio);
						property[i][j].card.setOpacity(0.0);
				}
				property[i][j].rectangle = new Rectangle();
				property[i][j].rectangle.setWidth((coordEndX - coordStartX) * ratio);
				property[i][j].rectangle.setHeight((coordEndY - coordStartY) * ratio);
				property[i][j].rectangle.setArcWidth(5);
				property[i][j].rectangle.setArcHeight(5);
				property[i][j].rectangle.setFill(Color.GREEN);
				property[i][j].rectangle.setOpacity(0.0);

				pane.getChildren().add(property[i][j].rectangle);
				property[i][j].rectangle.setTranslateX(-16*ratio+coordStartX + i * coordDistanceX * ratio);
				property[i][j].rectangle.setTranslateY(28*ratio+coordStartY + j * coordDistanceY * ratio);
				/*System.out.printf("rectangle (%d, %d) = (%.1f, %.1f)\n", i, j, -16*ratio+coordStartX + i * coordDistanceX * ratio,
					28*ratio+coordStartY + j * coordDistanceY * ratio);*/
			}

		binImage = new Image(binURL[0]);
		binImageView = new ImageView(binImage);
		binImageView.setFitWidth(80);
		binImageView.setPreserveRatio(true);
		binImageView.setSmooth(true);
		binImageView.setCache(true);
		pane.getChildren().add(binImageView);
		binImageView.setTranslateX(485);
		binImageView.setTranslateY(585);
		binRec = new Rectangle();
		binRec.setWidth(70);
		binRec.setHeight(85);
		binRec.setFill(Color.GREEN);
		binRec.setOpacity(0.0);
		pane.getChildren().add(binRec);
		binRec.setTranslateX(490);
		binRec.setTranslateY(585);
		
		return pane;
	}
	private boolean canLegallyPlaceInner(CardGUI card, int x, int y, int a, int b) {
		if (a < 0 || b < 0 || a >= 9 || b >= 5)
			return false;
		else if (traced[a][b])
			return false;
		else if (x == a && y == b)
			return false;
		else if (property[a][b].openRoute[4] == false)
			return false;
		traced[a][b] = true;
		boolean up, down, left, right;
		int id = card.getCardID();
		if (id == 100)
			id = 15;
		else if (id == 101)
			id = 11;
		else if (id == 102)
			id = 12;
		boolean usd = card.isUpSideDown();
		if (card.getCardID() == 102)
			usd = !card.isUpSideDown();
		
		if (b - 1 < 0 || (property[a][b-1].card == null && (a != x || b-1 != y) ) || (property[a][b-1].card != null && property[a][b-1].openRoute[1] == false) || property[a][b].openRoute[0] == false )
			up = false;
		else if (a == x && b-1 == y && ((usd && CardGUI.routeProperty[id][CardGUI.UP])||(!usd && CardGUI.routeProperty[id][CardGUI.DOWN])))
			return true;
		else
			up = canLegallyPlaceInner(card, x, y, a, b - 1);
		if (b + 1 >= 5 || (property[a][b+1].card == null && (a != x || b+1 != y) ) || (property[a][b+1].card != null && property[a][b+1].openRoute[0] == false) || property[a][b].openRoute[1] == false)
			down = false;
		else if (a == x && b+1 == y && ((usd && CardGUI.routeProperty[id][CardGUI.DOWN])||(!usd && CardGUI.routeProperty[id][CardGUI.UP])))
			return true;
		else
			down = canLegallyPlaceInner(card, x, y, a, b + 1);
		if (a - 1 < 0 || (property[a-1][b].card == null && (a-1 != x || b != y) ) || (property[a-1][b].card != null && property[a-1][b].openRoute[3] == false) || property[a][b].openRoute[2] == false)
			left = false;
		else if (a-1 == x && b == y && ((usd && CardGUI.routeProperty[id][CardGUI.LEFT])||(!usd && CardGUI.routeProperty[id][CardGUI.RIGHT])))
			return true;
		else
			left = canLegallyPlaceInner(card, x, y, a - 1, b);
		if (a + 1 >= 9 || (property[a+1][b].card == null && (a+1 != x || b != y) ) || (property[a+1][b].card != null && property[a+1][b].openRoute[2] == false) || property[a][b].openRoute[3] == false)
			right = false;
		else if (a+1 == x && b == y && ((usd && CardGUI.routeProperty[id][CardGUI.RIGHT])||(!usd && CardGUI.routeProperty[id][CardGUI.LEFT])) )
			return true;
		else
			right = canLegallyPlaceInner(card, x, y, a+1, b);
		return up || down || left || right;
	}
	
	public boolean canBind(CardGUI card, int x, int y) {

		int id = card.getCardID();
		if (id == 100)
			id = 15;
		else if (id == 101)
			id = 11;
		else if (id == 102)
			id = 12;
		boolean usd = card.isUpSideDown();
		if (card.getCardID() == 102)
			usd = !card.isUpSideDown();
		if (y - 1 >= 0 && property[x][y-1].card != null && ((!usd && property[x][y-1].openRoute[1] != CardGUI.routeProperty[id][CardGUI.UP]) || (usd && property[x][y-1].openRoute[1] != CardGUI.routeProperty[id][CardGUI.DOWN])))
			if (x != 8 || (x == 8 && property[x][y-1].card.getOpacity() > 0))
				return false;
		if (y + 1 < 5 && property[x][y+1].card != null && ((!usd && property[x][y+1].openRoute[0] != CardGUI.routeProperty[id][CardGUI.DOWN]) || (usd && property[x][y+1].openRoute[0] != CardGUI.routeProperty[id][CardGUI.UP])))
			if (x != 8 || (x == 8 && property[x][y+1].card.getOpacity() > 0))
				return false;
		if (x - 1 >= 0 && property[x-1][y].card != null && ((!usd && property[x-1][y].openRoute[3] != CardGUI.routeProperty[id][CardGUI.LEFT])||(usd && property[x-1][y].openRoute[3] != CardGUI.routeProperty[id][CardGUI.RIGHT])))
			return false;
		if (x + 1 < 9 && property[x+1][y].card != null && ((!usd && property[x+1][y].openRoute[2] != CardGUI.routeProperty[id][CardGUI.RIGHT])||(usd && property[x+1][y].openRoute[2] != CardGUI.routeProperty[id][CardGUI.LEFT])))
			if (x != 7 || (x == 7 && (y == 0 || y == 2 || y == 4) && property[x+1][y].card.getOpacity() > 0))
				return false;
		return true;
	}
	public boolean canLegallyPlace(CardGUI card, int x, int y) {
		if (card.isFunc() && card.getCardID() < 30) return false;
		traced = new boolean[9][5];
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 5; j++)
				traced[i][j] = false;
		if (x == 8 && (y == 0 || y == 2 || y == 4))
			return canLegallyPlaceInner(card, x, y, 0, 2);
		return canLegallyPlaceInner(card, x, y, 0, 2) && canBind(card, x, y);

	}
	public boolean isBinGreen() {
		return binRec.getOpacity() > 0;
	}
	public void setBinGreen(boolean on) {
		if (on) {
			binRec.setOpacity(0.5);
		}
		else
			binRec.setOpacity(0.0);
	}
	public void setLightGreen(int x, int y, boolean on) {
		if (on) {
			property[x][y].rectangle.toFront();
			property[x][y].rectangle.setFill(Color.GREEN);
			property[x][y].rectangle.setOpacity(0.5);
		}
		else {
			property[x][y].rectangle.setOpacity(0.0);
			property[x][y].rectangle.toBack();
			this.toBack();	
		}
	}
	public void collapseGreen(int x, int y, boolean on){
		if(on) {
			property[x][y].rectangle.setFill(Color.GREEN);
		}
		else
			property[x][y].rectangle.setFill(Color.RED);
	}
	public void setLightRed(int x, int y, boolean on) {
		if (on) {
			if (property[x][y].card != null && property[x][y].cardAddTemp == null && !(x == 8 && (y == 0 || y == 2 || y == 4))) {
				//construct a temp card and add into the same hierarchy as the Map/rectangle in order to realize quasi-transparent rectangles
				property[x][y].cardAddTemp = new CardGUI(property[x][y].card.getImage(), property[x][y].card.getCardID());
				Parent pane = this.getParent();
				((Pane) pane).getChildren().add(property[x][y].cardAddTemp);
				property[x][y].cardAddTemp.setFitWidth((coordEndX - coordStartX) * ratio);
				property[x][y].cardAddTemp.setPreserveRatio(true);
				property[x][y].cardAddTemp.setCache(true);
				property[x][y].cardAddTemp.setSmooth(true);
				property[x][y].cardAddTemp.setTranslateX(-16*ratio+coordStartX + x * coordDistanceX * ratio);
				property[x][y].cardAddTemp.setTranslateY(28*ratio+coordStartY + y * coordDistanceY * ratio);
				if (property[x][y].card.isUpSideDown()) {
					property[x][y].cardAddTemp.setRotate(180);
				}
			}
			property[x][y].rectangle.toFront();
			property[x][y].rectangle.setFill(Color.RED);
			property[x][y].rectangle.setOpacity(0.5);
		}
		else {
			Parent pane = this.getParent();
			((Pane) pane).getChildren().remove(property[x][y].cardAddTemp);
			property[x][y].cardAddTemp = null;

			property[x][y].rectangle.setOpacity(0.0);
			property[x][y].rectangle.toBack();
			this.toBack();	
		}
	}
	public void toFullBin() {
		binImage = new Image(binURL[1]);
		binImageView.setImage(binImage);
	}
	public boolean isGreen(int x, int y) {
		if (property[x][y].rectangle.getOpacity() != 0.0 && property[x][y].rectangle.getFill() == Color.GREEN)
			return true;
		return false;
	}
	public boolean isRed(int x, int y) {
		if (property[x][y].rectangle.getOpacity() != 0.0 && property[x][y].rectangle.getFill() == Color.RED)
			return true;
		return false;
	}
	public void setOccupyCard(int x, int y, CardGUI card) {
		property[x][y].card = card;
		property[x][y].openRoute = new boolean[5];
		if (card.isUpSideDown()) {
			property[x][y].openRoute[0] = CardGUI.routeProperty[card.getCardID()][1];
			property[x][y].openRoute[1] = CardGUI.routeProperty[card.getCardID()][0];
			property[x][y].openRoute[2] = CardGUI.routeProperty[card.getCardID()][3];
			property[x][y].openRoute[3] = CardGUI.routeProperty[card.getCardID()][2];
			property[x][y].openRoute[4] = CardGUI.routeProperty[card.getCardID()][4];
		}
		else {
			property[x][y].openRoute[0] = CardGUI.routeProperty[card.getCardID()][0];
			property[x][y].openRoute[1] = CardGUI.routeProperty[card.getCardID()][1];
			property[x][y].openRoute[2] = CardGUI.routeProperty[card.getCardID()][2];
			property[x][y].openRoute[3] = CardGUI.routeProperty[card.getCardID()][3];
			property[x][y].openRoute[4] = CardGUI.routeProperty[card.getCardID()][4];
		}
	}
	public void cardToBack(int x, int y) {
		if (property[x][y].card != null)
		property[x][y].card.toBack();
	}
	public void cardToFront(int x, int y) {
		if (property[x][y].card != null)
			property[x][y].card.toFront();
	}
	/*extend setFitWidth*/
	public void resizeWidth(double value) {
		super.setFitWidth(value);
		fitHeight = this.getFitWidth() * mapImage.getHeight() / mapImage.getWidth();
		fitWidth = super.getFitWidth();
		ratio = this.getFitWidth() / mapImage.getWidth();
	}
	public void resizeHeight(double value) {
		super.setFitHeight(value);
		fitWidth = this.getFitHeight() * mapImage.getWidth() / mapImage.getHeight();
		fitHeight = super.getFitHeight();
		ratio = this.getFitHeight() / mapImage.getHeight();
	}

	public double getRatio() {
		return ratio;
	}
	public boolean isOccupied(int i, int j) {
		return property[i][j].isOccupied;
	}
	public void setOccupied(int i, int j, boolean value) {
		property[i][j].isOccupied = value;
	}
	public boolean isRevealed(int k) {
		return (property[8][k * 2].card.getOpacity() > 0);
	}
	public void revealDest(int k) {
		if (property[8][k * 2].card.getOpacity() > 0)
			return;
		fadeTransition = new FadeTransition(Duration.seconds(4), property[8][k * 2].card);
		fadeTransition.setFromValue(0);
		fadeTransition.setToValue(1);
		fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);
        fadeTransition.play();

        if (result[k] == 1 || result[k] == 2) {
        	if (result[k] == 1) {
        		property[8][k * 2].openRoute[0] = false;
        		property[8][k * 2].openRoute[2] = false;
        		property[8][k * 2].card.setUpSideDown(false);
        	}
        	else {
        		property[8][k * 2].openRoute[0] = false;
        		property[8][k * 2].openRoute[3] = false;
        		property[8][k * 2].card.setUpSideDown(false);
        	}
        	//System.out.println("8, "+ k*2 +" "+property[8][k * 2].openRoute[0]+" "+property[8][k * 2].openRoute[1]
        	//	+" "+property[8][k * 2].openRoute[2]+" "+property[8][k * 2].openRoute[3]);
        	if (!canLegallyPlace(property[8][k * 2].card, 8, k * 2)) {
        		rotateTransition = new RotateTransition(Duration.seconds(0.5), property[8][k * 2].card);
        		rotateTransition.setFromAngle(property[8][k * 2].card.getRotate());
                rotateTransition.setToAngle(property[8][k * 2].card.getRotate() + 180);
                rotateTransition.setCycleCount(1);
                rotateTransition.setAutoReverse(false);
                rotateTransition.play();
                property[8][k * 2].card.setUpSideDown(true);
                if (result[k] == 1) {
                	property[8][k * 2].openRoute[0] = true;
        			property[8][k * 2].openRoute[1] = false;
        			property[8][k * 2].openRoute[2] = true;
        			property[8][k * 2].openRoute[3] = false;
                }
                else {
                	property[8][k * 2].openRoute[0] = true;
        			property[8][k * 2].openRoute[1] = false;
        			property[8][k * 2].openRoute[2] = false;
        			property[8][k * 2].openRoute[3] = true;
                }
        	}
        }
	}

}