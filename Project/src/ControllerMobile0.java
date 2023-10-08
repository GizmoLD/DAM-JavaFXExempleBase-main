import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.Parent;

public class ControllerMobile0 implements Initializable { // implements Initializable

    @FXML
    private ListView<String> listaMenu0;

    @FXML
    private ListView<String> listaMenu1;

    @FXML
    private Label myLabel;

    @FXML
    private Button retrocederMenu0;

    @FXML
    private AnchorPane contenedor;

    @FXML
    private ImageView img;

    @FXML
    private Label title;

    @FXML
    private Rectangle color;

    @FXML
    private Label data;

    @FXML
    private Label procesador;

    @FXML
    private Label venudes;

    private List<String> opcions = new ArrayList<>();

    private List<String> opcionsMenu1 = new ArrayList<>();

    String current;

    private boolean retrocederM1;

    private boolean retrocederM2;

    private boolean retrocederPresionado = false;


    String selectedItem1;

    int index;

    @Override
    public void initialize(URL location, ResourceBundle resources) { // throws Exception
        CompletableFuture<Void> consolesFuture = CompletableFuture
                .runAsync(() -> AppData.getInstance().load("Consoles", null));
        CompletableFuture<Void> jocsFuture = CompletableFuture.runAsync(() -> AppData.getInstance().load("Jocs", null));
        CompletableFuture<Void> personatgesFuture = CompletableFuture
                .runAsync(() -> AppData.getInstance().load("Personatges", null));

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(consolesFuture, jocsFuture, personatgesFuture);

        try {
            allFutures.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        retrocederM1 = false;
        retrocederM2 = false;

        retrocederMenu0.setVisible(false);

        listaMenu0.getItems().clear();

        opcions.add("Personatges");
        opcions.add("Jocs");
        opcions.add("Consoles");

        listaMenu0.getItems().addAll(opcions);

        listaMenu0.setOnMouseClicked(event -> {

            String selectedItem = listaMenu0.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                switch (selectedItem) {
                    case "Personatges":
                        listaMenu0.getItems().clear();
                        showList("Personatges");
                        retrocederMenu0.setVisible(true);
                        listaMenu0.setOnMouseClicked(innerEvent -> {

                            if (!retrocederM1){
                                selectedItem1 = listaMenu0.getSelectionModel().getSelectedItem();
                                index = opcionsMenu1.indexOf(selectedItem1);
                            }
                        });

                        break;
                    case "Jocs":
                        listaMenu0.getItems().clear();
                        showList("Jocs");
                        retrocederMenu0.setVisible(true);
                        listaMenu0.setOnMouseClicked(innerEvent -> {
                            selectedItem1 = listaMenu0.getSelectionModel().getSelectedItem();
                            index = opcionsMenu1.indexOf(selectedItem1);
                        });
                        break;
                    case "Consoles":
                        listaMenu0.getItems().clear();
                        showList("Consoles");
                        retrocederMenu0.setVisible(true);
                        listaMenu0.setOnMouseClicked(innerEvent -> {
                            selectedItem1 = listaMenu0.getSelectionModel().getSelectedItem();
                            index = opcionsMenu1.indexOf(selectedItem1);
                        });
                        break;
                    default:
                        break;
                }
            }
        });
        retrocederMenu0.setOnAction(e -> {
            retrocederM1 = true;
            showMenu0();
        });

    }

    public void showMenu0() {
        retrocederMenu0.setVisible(false);
        listaMenu0.getItems().clear();
        listaMenu0.getItems().addAll(opcions);
    }

    public void showList(String choiceBox) {
        AppData appData = AppData.getInstance();
        JSONArray dades = appData.getData(choiceBox);
        for (int i = 0; i < dades.length(); i++) {
            JSONObject consoleObject = dades.getJSONObject(i);

            if (consoleObject.has("nom")) {
                String nom = consoleObject.getString("nom");
                opcionsMenu1.add(nom);
            }
        }
        listaMenu0.getItems().addAll(opcionsMenu1);
    }

    void showInfo(String type, int index) {
        // Obtenir una referència a l'ojecte AppData que gestiona les dades
        AppData appData = AppData.getInstance();

        // Obtenir les dades de l'opció seleccionada
        JSONObject dades = appData.getItemData(type, index);

        URL resource;
        FXMLLoader loader;
        Parent itemTemplate;
        ControllerInfoItem itemController;

        if (type == "Consoles") {
            // Carregar la plantilla
            resource = this.getClass().getResource("assets/template_info_consoles.fxml");
            try {
                loader = new FXMLLoader(resource);
                itemTemplate = loader.load();
                itemController = loader.getController();
                itemController.setImage("assets/images/" + dades.getString("imatge"));
                itemController.setTitle(dades.getString("nom"));
                itemController.setData(dades.getString("data"));
                itemController.setProcesador(dades.getString("procesador"));
                itemController.setVenudes(dades.getInt("venudes"));
                AnchorPane.setTopAnchor(itemTemplate, 0.0);
                AnchorPane.setRightAnchor(itemTemplate, 0.0);
                AnchorPane.setBottomAnchor(itemTemplate, 0.0);
                AnchorPane.setLeftAnchor(itemTemplate, 0.0);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}