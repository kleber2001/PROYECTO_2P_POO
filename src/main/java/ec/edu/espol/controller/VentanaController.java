/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.controller;

import ec.edu.espol.proyecto2p.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class VentanaController implements Initializable {

    @FXML
    private VBox vpane;
    @FXML
    private Button btnvendedor;
    @FXML
    private Button btncomprador;
    @FXML
    private Button btnsalir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image i = new Image("img/auto.jpg");
        ImageView imv = new ImageView(i);
        imv.setFitHeight(600);
        imv.setFitWidth(300);
        vpane.getChildren().add(imv);

    }    

    @FXML
    private void cerrarventana(MouseEvent event) {
        Stage stage = (Stage) this.btnsalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void sceneVendedor(MouseEvent event) {
        try {
            FXMLLoader fxmlloader = App.loadFXMLoader("vendedorLogin");
            App.setRoot(fxmlloader);
            VendedorLoginController vlc = fxmlloader.getController();
            

        } catch (IOException ex) {
            Alert a = new Alert(AlertType.ERROR,"No se pudo acceder");
            a.show();
        }
    }
    
    

}
