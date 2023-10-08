import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextFlow;

public class ControllerInfoItem {
    @FXML
    private ImageView img;

    @FXML
    private Label title = new Label();

    @FXML
    private Label text = new Label();

    @FXML
    private Rectangle color = new Rectangle();

    public void setRectangle(String color) {
        this.color.setStyle("-fx-fill: "+color+";");
    }

    public void setG(){
        TextFlow textFlow = new TextFlow();
        this.descripcio.setGraphic(textFlow);
    }

    //
    //
    @FXML
    private Label procesador = new Label();

    @FXML
    private Label data = new Label();

    @FXML
    private Label venudes = new Label();

    @FXML
    private Label any = new Label();

    @FXML
    private Label tipus = new Label();

    @FXML 
    private Label descripcio = new Label();
    //
    //

    public void setImage(String resourceName) {

        // Obté una referència al recurs dins del .jar
        ClassLoader classLoader = getClass().getClassLoader();
        Image image = new Image(classLoader.getResourceAsStream(resourceName));

        // Estableix la imatge a l'ImageView
        img.setImage(image);
    }

    public void setTitle(String text) {

        // Estableix el contingut del Label
        this.title.setText(text);
    }

    public void setText(String text) {

        // Estableix el contingut del Label
        this.text.setText(text);
    }

    //
    //
    //
    public void setData(String data){
        this.data.setText(data);
    }

    public void setProcesador(String procesador){
        this.procesador.setText(""+procesador);
    }

    public void setVenudes(int venudes){
        this.venudes.setText(""+venudes);
    }

    public void setAny(int any){
        this.any.setText(""+any);
    }

    public void setDescripcio(String descripcion){
        this.descripcio.setText(descripcion);
    }
    //
    //
    //
}
