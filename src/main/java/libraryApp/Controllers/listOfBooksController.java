package libraryApp.Controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import libraryApp.Models.Book;
import libraryApp.Utils.DbManager;
import libraryApp.Utils.DialogUtils;
import libraryApp.Utils.FxmlUtils;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class listOfBooksController {
    @FXML
    public TableView listOfBooks;
    @FXML
    public TableColumn titleColumn;
    @FXML
    public TableColumn authorColumn;
    @FXML
    public TableColumn categoryColumn;
    @FXML
    public TableColumn descColumn;
    @FXML
    public TableColumn rateColumn;
    @FXML
    public TableColumn isbnColumn;

    ObservableList<Book> books = FXCollections.observableArrayList();

    static ResourceBundle bundle = FxmlUtils.getResource();

    @FXML
    public void initialize(){

        initList();
        titleColumn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("author"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("category"));
        descColumn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("description"));
        rateColumn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("rate"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("ISBN"));
        listOfBooks.setItems(books);

    }

    public void initList() {
        books.clear();
        try {
            Dao<Book, Integer> dao = DaoManager.createDao(DbManager.getConnectionSource(), Book.class);
            List<Book> result = dao.queryForAll();
            result.forEach(e -> {
                books.add(e);
            });
        } catch (SQLException  e) {
            System.out.println(bundle.getString("error.during.loading.list") + e.getMessage());
        }
    }

    public void deleteBook() throws SQLException {
        if(listOfBooks.getSelectionModel().getSelectedItem() == null){
            DialogUtils.bootToDeleteNotsSelected();
        }else {
            System.out.println(listOfBooks.getSelectionModel().getSelectedItem());
            Book result = (Book) listOfBooks.getSelectionModel().getSelectedItem();
            Dao<Book, Integer> dao = DaoManager.createDao(DbManager.getConnectionSource(), Book.class);
            Optional<ButtonType> result1 = DialogUtils.deleteConfirmationDialog();
            if(result1.get() == ButtonType.OK)
                dao.delete(result);
            initList();
        }
    }

    public void editBook(){
        try{
            Book result = (Book) listOfBooks.getSelectionModel().getSelectedItem();
            DialogUtils.editBookDialog(result);
            initList();
        }catch(Exception e){
            DialogUtils.bootToEditNotsSelected();
        }

    }
}
