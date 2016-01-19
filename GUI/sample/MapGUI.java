package sample;
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


public class MapGUI extends javafx.scene.image.ImageView {
	public static final double coordStartX = 56;
	public static final double coordStartY = 59;
	public static final double coordEndX = 152;
	public static final double coordEndY = 207;
	public static final double coordDistanceX = 107;
	public static final double coordDistanceY = 159;

	public static final String binURL[] = {"file:"+System.getProperty("user.dir")+"/src/sample"+"/img/Recycle_Bin_Empty.png",
			"file:"+System.getProperty("user.dir")+"/src/sample"+"/img/Recycle_Bin_Full.png"};

	private Image binImage;
	private ImageView binImageView;
	private Image mapImage;
	private Rectangle binRec;

	public double fitHeight;
	public double fitWidth;

	private double ratio;

	private CoordProperty[][] property;

	class CoordProperty {
		public boolean isOccupied;
		public Rectangle rectangle;
		public CardGUI card;
		public CardGUI cardAddTemp;

		public CoordProperty() {
			isOccupied = false;
		}
	}

	public MapGUI() {
		super();
		mapImage = new Image("./img/game/Z_Map.jpg");
		this.setImage(mapImage);
		fitHeight = mapImage.getHeight();
		fitWidth = mapImage.getWidth();
		ratio = 1;
		initMapView();
		initProperty();
	}
	public MapGUI(double value) {
		super();
		mapImage = new Image("file:"+System.getProperty("user.dir")+"/src/sample"+"/img/game/Z_Map.jpg");
		this.setImage(mapImage);
		fitHeight = mapImage.getHeight();
		fitWidth = mapImage.getWidth();
		ratio = 1;
		initMapView();
		initProperty();

		resizeHeight(value);
	}
	private void initProperty() {
		property = new CoordProperty[9][5];
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 5; j++) {
				property[i][j] = new CoordProperty();
				if (i == 0 && j == 2)
					property[i][j].isOccupied = true;
				else if (i == 8 && (j == 0 || j == 2 || j == 4))
					property[i][j].isOccupied = true;
			}
	}
	private void initMapView() {
		this.setPreserveRatio(true);
		this.setSmooth(true);
		this.setCache(true);
	}
	public Pane initPane() {
		Pane pane = new Pane();
		pane.getChildren().add(this);
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 5; j++) {
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
			}

		binImage = new Image(binURL[0]);
		binImageView = new ImageView(binImage);
		binImageView.setFitWidth(80);
		binImageView.setPreserveRatio(true);
		binImageView.setSmooth(true);
		binImageView.setCache(true);
		pane.getChildren().add(binImageView);
		binImageView.setTranslateX(510);
		binImageView.setTranslateY(610);
		binRec = new Rectangle();
		binRec.setWidth(70);
		binRec.setHeight(85);
		binRec.setFill(Color.GREEN);
		binRec.setOpacity(0.0);
		pane.getChildren().add(binRec);
		binRec.setTranslateX(515);
		binRec.setTranslateY(610);
		
		return pane;
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
	public void setLightRed(int x, int y, boolean on) {
		if (on) {
			if (property[x][y].card != null && property[x][y].cardAddTemp == null) {
				//construct a temp card and add into the same hierarchy as the Map/rectangle in order to realize quasi-transparent rectangles
				property[x][y].cardAddTemp = new CardGUI(property[x][y].card.getImage(), property[x][y].card.isFunc());
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

}