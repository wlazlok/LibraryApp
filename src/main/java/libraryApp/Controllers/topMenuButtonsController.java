package libraryApp.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class topMenuButtonsController {

    MainController mainController;
    @FXML
    public Button booksButton;
    @FXML
    public ToggleButton showBooks;
    @FXML
    public ToggleButton addAuthorButton;
    @FXML
    public ToggleButton addBookButton;
    @FXML
    public ToggleButton addCategoryButton;
    @FXML
    public ToggleGroup topMenuButtonGroup;

    @FXML
    public void addCategory() {
        if(topMenuButtonGroup.getSelectedToggle()!=null)
            topMenuButtonGroup.getSelectedToggle().setSelected(false);
        mainController.setCenter("/fxml/addCategory.fxml");
    }

    @FXML
    public void addBook() {
        if(topMenuButtonGroup.getSelectedToggle()!=null)
            topMenuButtonGroup.getSelectedToggle().setSelected(false);
        mainController.setCenter("/fxml/addBook.fxml");
    }

    @FXML
    public void addAuthor() {
        if(topMenuButtonGroup.getSelectedToggle()!=null)
            topMenuButtonGroup.getSelectedToggle().setSelected(false);
        mainController.setCenter("/fxml/addAuthor.fxml");
    }
    @FXML
    public void openLibrary() {
        if(topMenuButtonGroup.getSelectedToggle()!=null)
            topMenuButtonGroup.getSelectedToggle().setSelected(false);
        mainController.setCenter("/fxml/Library.fxml");
    }
    @FXML
    public void showAllBooks() {
        if(topMenuButtonGroup.getSelectedToggle()!=null)
            topMenuButtonGroup.getSelectedToggle().setSelected(false);
        mainController.setCenter("/fxml/listOfBooks.fxml");
    }

    public void setMainController(MainController mainController) {

        this.mainController = mainController;
    }
}
