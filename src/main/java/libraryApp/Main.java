package libraryApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import libraryApp.Utils.FxmlUtils;

import java.util.Locale;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        //Locale.setDefault(new Locale("en"));

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/mainBorderPane.fxml"));
        loader.setResources(FxmlUtils.getResource());
        BorderPane mainPane = loader.load();
        Scene mainScene = new Scene(mainPane);
        primaryStage.setTitle(FxmlUtils.getResource().getString("title.application"));
        primaryStage.setScene(mainScene);
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/icons/app_icon.png")));
        primaryStage.show();
    }
}
