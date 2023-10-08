import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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



    private List<String> opcions = new ArrayList<>();

    private List<String> opcionsMenu1 = new ArrayList<>();

    String current;

    private boolean retrocederM1;
    private boolean retrocederM2;

    @Override
    public void initialize(URL location, ResourceBundle resources) { // throws Exception
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
                        // Cargar la pestaña de Personajes
                        // loadTab("Personajes.fxml");
                        listaMenu0.getItems().clear();
                        showList("Personatges");
                        retrocederMenu0.setVisible(true);

                        listaMenu0.setOnMouseClicked( innerEvent -> {
                            String selectedItem2 = listaMenu0.getSelectionModel().getSelectedItem();
                            listaMenu0.setVisible(false);
                            
                        });

                        break;
                    case "Juegos":
                        // Cargar la pestaña de Juegos
                        // loadTab("Juegos.fxml");
                        break;
                    case "Consoles":
                        // Cargar la pestaña de Consoles
                        // loadTab("Consoles.fxml");
                        break;
                    default:
                        break;
                }
            }

        });

        retrocederMenu0.setOnAction(e -> {
            showMenu0();
        });

    }

    public void showMenu0() {
        retrocederMenu0.setVisible(false);
        listaMenu0.getItems().clear();
        listaMenu0.getItems().addAll(opcions);
    }

    public void showList(String choiceBox) {
        // Si s'ha carregat una altra opció, no cal fer res
        // (perquè el callback pot arribar després de que l'usuari hagi canviat d'opció)
        List<String> lista = new ArrayList<>();

        String opcioSeleccionada = choiceBox;

        // Obtenir una referència a l'ojecte AppData que gestiona les dades
        AppData appData = AppData.getInstance();

        // Obtenir les dades de l'opció seleccionada
        JSONArray dades = appData.getData(opcioSeleccionada);

        // Carregar la plantilla
        URL resource = this.getClass().getResource("assets/layout_mobile_11.fxml");

        // Esborrar la llista actual
        // yPane.getChildren().clear();

        // Carregar la llista amb les dades
        System.out.println("bandera 1");
        for (int i = 0; i < dades.length(); i++) {
            System.out.println("bandera 2");
            JSONObject consoleObject = dades.getJSONObject(i);

            if (consoleObject.has("nom")) {
                System.out.println("bandera 3");
                String nom = consoleObject.getString("nom");
                FXMLLoader loader = new FXMLLoader(resource);
                // Parent itemTemplate = loader.load();
                // ControllerListItem itemController = loader.getController();

                // itemController.setText(nom);

                // Defineix el callback que s'executarà quan l'usuari seleccioni un element
                // (cal passar final perquè es pugui accedir des del callback)
                final String type = opcioSeleccionada;
                final int index = i;
                /*
                 * itemTemplate.setOnMouseClicked(event -> {
                 * showInfo(type, index);
                 * });
                 */
                // yPane.getChildren().add(itemTemplate);
                System.out.println("bandera 4");
                opcionsMenu1.add(nom);
                // listaMenu1.getItems().add(nom);
            }
        }

        System.out.println("bandera5");
        // listaMenu0.getItems().addAll(opcions);

        listaMenu0.getItems().addAll(opcionsMenu1);
        // String[] os = { "Personatges", "Jocs", "Consoles" };
        // listaMenu1.getItems().addAll(os);
        System.out.println(lista);
        System.out.println("bandera6");
    }

    void showInfo(String type, int index) {
        // Obtenir una referència a l'ojecte AppData que gestiona les dades
        AppData appData = AppData.getInstance();

        // Obtenir les dades de l'opció seleccionada
        JSONObject dades = appData.getItemData(type, index);

        // Carregar la plantilla
        // URL resource = this.getClass().getResource("assets/template_info_item.fxml");

        // Esborrar la informació actual
        //
        //
        //info.getChildren().clear();
        //
        //

        //
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

                // Afegeix la informació a la vista
                //
                //
                //info.getChildren().add(itemTemplate);
                //
                //

                // Estableix que la mida de itemTemplaate s'ajusti a la mida de info
                AnchorPane.setTopAnchor(itemTemplate, 0.0);
                AnchorPane.setRightAnchor(itemTemplate, 0.0);
                AnchorPane.setBottomAnchor(itemTemplate, 0.0);
                AnchorPane.setLeftAnchor(itemTemplate, 0.0);

            } catch (Exception e) {
                System.out.println(e);
            }

        }
        /*
        else if (type == "Jocs") {
            resource = this.getClass().getResource("assets/template_info_jocs.fxml");
            try {
                loader = new FXMLLoader(resource);
                itemTemplate = loader.load();
                itemController = loader.getController();
                itemController.setImage("assets/images/" + dades.getString("imatge"));
                itemController.setAny(dades.getInt("any"));
                itemController.setTitle(dades.getString("tipus"));
                itemController.setDescripcio(dades.getString("descripcio"));

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
        } else if (type == "Personatges") {
            resource = this.getClass().getResource("assets/template_info_personatge.fxml");
            try {
                loader = new FXMLLoader(resource);
                itemTemplate = loader.load();
                itemController = loader.getController();
                itemController.setImage("assets/images/" + dades.getString("imatge"));
                itemController.setTitle(dades.getString("nom"));
                itemController.setText(dades.getString("nom_del_videojoc"));

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
        */
    }
}