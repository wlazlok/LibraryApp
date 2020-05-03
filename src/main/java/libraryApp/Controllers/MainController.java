package libraryApp.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import libraryApp.Utils.DialogUtils;
import libraryApp.Utils.FxmlUtils;

import java.io.IOException;
import java.util.Optional;

public class MainController {

    @FXML
    public BorderPane borderPane;
    @FXML
    topMenuButtonsController topMenuButtonsController;

    @FXML
    public void initialize(){
        topMenuButtonsController.setMainController(this);
    }
    @FXML
    public void closeApplication() {
        Optional<ButtonType> result = DialogUtils.confirmationDialog();
        if(result.get() == ButtonType.OK){
            Platform.exit();
            System.exit(0);
        }
    }

    @FXML
    public void aboutApp() {
        DialogUtils.aboutAppDialog();
    }

    public void setCenter(String fxmlPath){

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fxmlPath));
        loader.setResources(FxmlUtils.getResource());
        try {
            borderPane.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
