package libraryApp.Utils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import libraryApp.Models.Author;
import libraryApp.Models.Book;
import libraryApp.Models.Category;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class DialogUtils {

    static ResourceBundle bundle = FxmlUtils.getResource();
    private static String imagePath = "DialogUtils.bootToDeleteNotsSelected.content.text";

    public static Optional<ButtonType> confirmationDialog(){
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle(bundle.getString("DialogUtils.confirmationDialog.title"));
        confirmationAlert.setHeaderText(bundle.getString("DialogUtils.confirmationDialog.header.text"));
        confirmationAlert.setContentText(bundle.getString("DialogUtils.confirmationDialog.content.text"));
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        return result;
    }

    public static void aboutAppDialog(){
        Alert aboutAppAlert = new Alert(Alert.AlertType.INFORMATION);
        aboutAppAlert.setTitle(bundle.getString("DialogUtils.aboutAppDialog.title"));
        aboutAppAlert.setHeaderText(bundle.getString("DialogUtils.aboutAppDialog.header.text"));
        aboutAppAlert.setContentText(bundle.getString("DialogUtils.aboutAppDialog.content.text"));
        aboutAppAlert.showAndWait();
    }

    public static Optional<ButtonType> deleteConfirmationDialog(){
        Alert deleteConfirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        deleteConfirmationDialog.setTitle(bundle.getString("DialogUtils.deleteConfirmationDialog.title"));
        deleteConfirmationDialog.setHeaderText(bundle.getString("DialogUtils.deleteConfirmationDialog.header.text"));
        deleteConfirmationDialog.setContentText(bundle.getString("DialogUtils.deleteConfirmationDialog.content.text"));
        Optional<ButtonType> result = deleteConfirmationDialog.showAndWait();
        return result;
    }

    public static void bookExsistInDataBase(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(bundle.getString("alert.title"));
        alert.setHeaderText(bundle.getString("alert.header.text"));
        alert.setContentText(bundle.getString("alert.contetnt.test"));
        alert.showAndWait();
    }

    public static void deleteCategory(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(bundle.getString("DialogUtils.deleteCategory.title"));
        alert.setHeaderText(bundle.getString("DialogUtilsdeleteCategory.header.text"));
        alert.showAndWait();
    }

    public static Optional<ButtonType> errorDuringCreateConnectionSource(String string){
        Alert errorConnectionSource = new Alert(Alert.AlertType.INFORMATION);
        errorConnectionSource.setTitle(bundle.getString("DialogUtils.errorDuringCreateConnectionSource.title"));
        errorConnectionSource.setHeaderText(bundle.getString("DialogUtils.errorDuringCreateConnectionSource.header.text"));
        errorConnectionSource.setContentText(string);
        Optional<ButtonType> result = errorConnectionSource.showAndWait();
        return result;
    }

    public static void editBookDialog(Book book){

        // reading all author from base and adding them to the list
        ObservableList<Author> authorList = FXCollections.observableArrayList();
        try{
            Dao<Author, Integer> dao  = DaoManager.createDao(DbManager.getConnectionSource(), Author.class);
            List<Author> result = dao.queryBuilder().query();
            result.forEach(e->{
                authorList.add(e);
            });
        }catch (SQLException e) {
            System.out.println(e.getCause().getMessage());
        }
        //

        // reading all category's from base and adding them to the list
        ObservableList<Category> categoryList = FXCollections.observableArrayList();
        try {
            Dao<Category, Integer> daoCategory  = DaoManager.createDao(DbManager.getConnectionSource(), Category.class);
            List<Category> resultCategory = daoCategory.queryBuilder().query();
            resultCategory.forEach(e->{
                categoryList.add(e);
            });
        }
        catch (SQLException e1) {
                System.out.println(e1.getCause().getMessage());
            }
        //
        Dialog dialog = new Dialog();
        dialog.setTitle(bundle.getString("DialogUtils.editBookDialog.title"));
        ButtonType changeButtonType = new ButtonType(bundle.getString("DialogUtils.editBookDialog.buttonType.text"), ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(changeButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField titletextField = new TextField();
        ComboBox<Author> authorComboBox = new ComboBox();
        authorComboBox.setItems(authorList);
        ComboBox<Category> categoryComboBox = new ComboBox();
        categoryComboBox.setItems(categoryList);
        TextArea descTextArea = new TextArea();
        Slider rateSlider = new Slider();
        rateSlider.setValue(book.getRate());
        rateSlider.setMin(1);
        rateSlider.setMax(5);
        rateSlider.setBlockIncrement(1);
        rateSlider.setShowTickMarks(true);
        rateSlider.setShowTickLabels(true);
        rateSlider.setSnapToTicks(true);
        rateSlider.setMajorTickUnit(1);
        rateSlider.setMinorTickCount(0);
        TextField textareaISBN = new TextField();

        grid.add(new Label(bundle.getString("book.title")),0, 0);
        grid.add(titletextField, 1, 0);
        grid.add(new Label(bundle.getString("book.author")),0, 1);
        grid.add(authorComboBox, 1, 1);
        grid.add(new Label(bundle.getString("book.category")),0, 2);
        grid.add(categoryComboBox, 1, 2);
        grid.add(new Label(bundle.getString("book.description")),0, 3);
        grid.add(descTextArea, 1, 3);
        grid.add(new Label(bundle.getString("book.rate")),0, 4);
        grid.add(rateSlider, 1, 4);
        grid.add(new Label(bundle.getString("book.isbn")),0, 5);;
        grid.add(textareaISBN, 1, 5);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogBtton ->{
            if(dialogBtton == changeButtonType){
                if(titletextField.getLength() == 0 && authorComboBox.getSelectionModel().getSelectedItem() == null
                        && categoryComboBox.getSelectionModel().getSelectedItem() == null && descTextArea.getLength() == 0
                        && rateSlider.getValue() == book.getRate() && textareaISBN.getLength() == 0)
                    noChangesInEdit();
                else{
                    try {
                        Dao<Book, Integer> dao = DaoManager.createDao(DbManager.getConnectionSource(), Book.class);
                        if(titletextField.getLength() != 0)
                            book.setTitle(titletextField.getText());
                        if(authorComboBox.getSelectionModel().getSelectedItem() != null)
                            book.setAuthor(authorComboBox.getSelectionModel().getSelectedItem());
                        if(categoryComboBox.getSelectionModel().getSelectedItem() != null)
                            book.setCategory(categoryComboBox.getSelectionModel().getSelectedItem());
                        if(descTextArea.getLength() != 0)
                            book.setDescription(descTextArea.getText());
                        if(rateSlider.getValue() != book.getRate())
                            book.setRate(Integer.valueOf((int)rateSlider.getValue()));
                        if(textareaISBN.getLength() != 0)
                            book.setISBN(textareaISBN.getText());
                        dao.update(book);
                    } catch (SQLException e) {
                        System.out.println(bundle.getString("error.during.updating.book") + e.getMessage());
                    }
                }
            }
            return null;
        });
        dialog.showAndWait();
    }

    public static void noChangesInEdit(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(bundle.getString("DialogUtils.noChangesInEdit.title"));
        alert.setHeaderText(bundle.getString("DialogUtils.noChangesInEdit.headerText"));

        alert.showAndWait();
    }

    public static void bootToEditNotsSelected(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(bundle.getString("DialogUtils.bootToEditNotsSelected.title"));
        alert.setHeaderText(bundle.getString("DialogUtils.bootToEditNotsSelected.headertext"));
        alert.setContentText(bundle.getString("DialogUtils.bootToEditNotsSelected.content.text"));

        alert.showAndWait();
    }

    public static void bootToDeleteNotsSelected(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(bundle.getString("DialogUtils.bootToDeleteNotsSelected.title"));
        alert.setHeaderText(bundle.getString("DialogUtils.bootToDeleteNotsSelected.headertext"));
        alert.setContentText(bundle.getString(imagePath));
        alert.showAndWait();
    }
}
