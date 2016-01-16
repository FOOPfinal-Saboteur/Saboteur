import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class xx extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) {
    stage.setTitle("ComboBoxSample");
    Scene scene = new Scene(new Group(), 450, 250);

    ComboBox emailComboBox = new ComboBox();
    emailComboBox.getItems().addAll("A","B","C","D","E");
    emailComboBox.setPromptText("Email address");
    emailComboBox.setEditable(true);        
    emailComboBox.valueProperty().addListener(new ChangeListener<String>() {
        @Override public void changed(ObservableValue ov, String t, String t1) {
          System.out.println(ov);
            System.out.println(t);
            System.out.println(t1);
        }    
    });
    
    GridPane grid = new GridPane();
    grid.setVgap(4);
    grid.setHgap(10);
    grid.setPadding(new Insets(5, 5, 5, 5));
    grid.add(new Label("To: "), 0, 0);
    grid.add(emailComboBox, 1, 0);
    
    
    Group root = (Group) scene.getRoot();
    root.getChildren().add(grid);
    stage.setScene(scene);
    stage.show();

  }
}