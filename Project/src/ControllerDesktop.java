import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.Initializable;

public class ControllerDesktop implements Initializable {
    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private VBox yPane;

    @FXML
    private AnchorPane info;

    String opcions[] = { "Personatges", "Jocs", "Consoles" };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Afegeix les opcions al ChoiceBox
        choiceBox.getItems().addAll(opcions);
        // Selecciona la primera opció
        choiceBox.setValue(opcions[0]);
        // Callback que s’executa quan l’usuari escull una opció
        choiceBox.setOnAction((event) -> {
            loadList();
        });
        // Carregar automàticament les dades de ‘Personatges’
        loadList();
    }

    public void loadList() {
        // Obtenir l'opció seleccionada
        String opcio = choiceBox.getValue();
        // Obtenir una referència a AppData que gestiona les dades
        AppData appData = AppData.getInstance();
        // Mostrar el missatge de càrrega
        showLoading();
        // Demanar les dades
        appData.load(opcio, (result) -> {
            if (result == null) {
                System.out.println("ControllerDesktop: Error loading data.");
            } else {
                try {
                    showList();
                } catch (Exception e) {
                    System.out.println("ControllerDesktop: Error showing list.");
                }

            }
        });
    }

    public void showList() throws Exception {
        // Si s'ha carregat una altra opció, no cal fer res
        // (perquè el callback pot arribar després de que l'usuari hagi canviat d'opció)
        String opcioSeleccionada = choiceBox.getValue();

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

    public void showLoading() {
        // Esborrar la llista actual
        yPane.getChildren().clear();

        // Afegeix un indicador de progrés com a primer element de la llista
        ProgressIndicator progressIndicator = new ProgressIndicator();
        yPane.getChildren().add(progressIndicator);
    }

    void showInfo(String type, int index) {
        // Obtenir una referència a l'ojecte AppData que gestiona les dades
        AppData appData = AppData.getInstance();

        // Obtenir les dades de l'opció seleccionada
        JSONObject dades = appData.getItemData(type, index);

        // Carregar la plantilla
        // URL resource = this.getClass().getResource("assets/template_info_item.fxml");

        // Esborrar la informació actual
        info.getChildren().clear();

        //
        URL resource;
        FXMLLoader loader;
        Parent itemTemplate;
        ControllerInfoItem itemController;
        

        if (type=="Consoles") {
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
                itemController.setRectangle(dades.getString("color"));
                


                // Afegeix la informació a la vista
                info.getChildren().add(itemTemplate);

                // Estableix que la mida de itemTemplaate s'ajusti a la mida de info
                AnchorPane.setTopAnchor(itemTemplate, 0.0);
                AnchorPane.setRightAnchor(itemTemplate, 0.0);
                AnchorPane.setBottomAnchor(itemTemplate, 0.0);
                AnchorPane.setLeftAnchor(itemTemplate, 0.0);

            } catch (Exception e) {
                System.out.println(e);
            }

        } else if (type == "Jocs") {
            resource = this.getClass().getResource("assets/template_info_jocs.fxml");
            try {
                loader = new FXMLLoader(resource);
                itemTemplate = loader.load();
                itemController = loader.getController();
                itemController.setImage("assets/images/" + dades.getString("imatge"));
                itemController.setAny(dades.getInt("any"));
                itemController.setTitle(dades.getString("tipus"));
                itemController.setDescripcio(dades.getString("descripcio"));
                itemController.setG();

                // Afegeix la informació a la vista
                info.getChildren().add(itemTemplate);

                // Estableix que la mida de itemTemplaate s'ajusti a la mida de info
                AnchorPane.setTopAnchor(itemTemplate, 0.0);
                AnchorPane.setRightAnchor(itemTemplate, 0.0);
                AnchorPane.setBottomAnchor(itemTemplate, 0.0);
                AnchorPane.setLeftAnchor(itemTemplate, 0.0);

            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (type=="Personatges") {
            resource = this.getClass().getResource("assets/template_info_personatge.fxml");
            try {
                loader = new FXMLLoader(resource);
                itemTemplate = loader.load();
                itemController = loader.getController();
                itemController.setImage("assets/images/" + dades.getString("imatge"));
                itemController.setTitle(dades.getString("nom"));
                itemController.setText(dades.getString("nom_del_videojoc"));
                itemController.setRectangle(dades.getString("color"));

                // Afegeix la informació a la vista
                info.getChildren().add(itemTemplate);

                // Estableix que la mida de itemTemplaate s'ajusti a la mida de info
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