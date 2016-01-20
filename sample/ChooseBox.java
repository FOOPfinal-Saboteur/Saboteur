package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by leinadshih on 1/14/16.
 */
public class ChooseBox {

    public static boolean display(String title,String msg, String item1, String item2){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);


        Button b1 = new Button(item1);
        Button b2 = new Button(item2);

        b1.setOnAction(e -> {
            if(item1.equals("pick"))
                Main.targetitem = 0;
            if(item1.equals("lamp"))
                Main.targetitem = 1;
            if(item1.equals("cart"))
                Main.targetitem = 2;
            window.close();
        });
        b2.setOnAction(e -> {
            if(item2.equals("pick"))
                Main.targetitem = 0;
            if(item2.equals("lamp"))
                Main.targetitem = 1;
            if(item2.equals("cart"))
                Main.targetitem = 2;
            window.close();
        });

        Label air = new Label();

        VBox layout = new VBox(10);
        layout.getChildren().addAll(b1, b2, air);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return true;
    }
}
