package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapGUI extends javafx.scene.image.ImageView {
	public static final double coordStartX = 56;
	public static final double coordStartY = 59;
	public static final double coordEndX = 152;
	public static final double coordEndY = 207;
	public static final double coordDistanceX = 107;
	public static final double coordDistanceY = 159;

	private Image mapImage;

	public double fitHeight;
	public double fitWidth;

	private double ratio;

	private CoordProperty[][] property;

	class CoordProperty {
		public boolean isOccupied;

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
	/*override*/
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