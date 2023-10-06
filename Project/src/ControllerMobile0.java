import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;

public class ControllerMobile0 implements Initializable { // implements Initializable

    @FXML
    private ListView<String> listaMenu0;

    @FXML
    private Label myLabel;

    String[] opcions = { "Personatges", "Jocs", "Consoles" };

    String current;

    @Override
    public void initialize(URL location, ResourceBundle resources) { // throws Exception
        listaMenu0.getItems().addAll(opcions);

        listaMenu0.setOnMouseClicked(event -> {
            String selectedItem = listaMenu0.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                switch (selectedItem) {
                    case "Personajes":
                        // Cargar la pestaña de Personajes
                        //loadTab("Personajes.fxml");
                        break;
                    case "Juegos":
                        // Cargar la pestaña de Juegos
                        //loadTab("Juegos.fxml");
                        break;
                    case "Consoles":
                        // Cargar la pestaña de Consoles
                        //loadTab("Consoles.fxml");
                        break;
                    default:
                        break;
                }
            }
        });

    }

    public void showList(String choiceBox) throws Exception {
        // Si s'ha carregat una altra opció, no cal fer res
        // (perquè el callback pot arribar després de que l'usuari hagi canviat d'opció)
        String opcioSeleccionada = choiceBox;

        // Obtenir una referència a l'ojecte AppData que gestiona les dades
        AppData appData = AppData.getInstance();

        // Obtenir les dades de l'opció seleccionada
        JSONArray dades = appData.getData(opcioSeleccionada);

        // Carregar la plantilla
        URL resource = this.getClass().getResource("assets/template_list_item.fxml");

        // Esborrar la llista actual
        yPane.getChildren().clear();

        // Carregar la llista amb les dades
        for (int i = 0; i < dades.length(); i++) {
            JSONObject consoleObject = dades.getJSONObject(i);
            if (consoleObject.has("nom")) {
                String nom = consoleObject.getString("nom");
                String imatge = "assets/images/" + consoleObject.getString("imatge");
                FXMLLoader loader = new FXMLLoader(resource);
                Parent itemTemplate = loader.load();
                ControllerListItem itemController = loader.getController();

                itemController.setText(nom);
                itemController.setImage(imatge);

                // Defineix el callback que s'executarà quan l'usuari seleccioni un element
                // (cal passar final perquè es pugui accedir des del callback)
                final String type = opcioSeleccionada;
                final int index = i;
                itemTemplate.setOnMouseClicked(event -> {
                    showInfo(type, index);
                });
                yPane.getChildren().add(itemTemplate);
            }
        }

    }

}