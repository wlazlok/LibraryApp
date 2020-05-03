package libraryApp.Controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import libraryApp.Models.Author;
import libraryApp.Utils.DbManager;
import libraryApp.Utils.FxmlUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AuthorController {
    @FXML
    public Button addAuthorButton;
    @FXML
    public TextField authorNameTextField;
    @FXML
    public TextField authorSurnameTextField;
    @FXML
    public ComboBox authorComboBox;
    @FXML
    public Button deleteAuthorButton;
    @FXML
    public ListView authorListView;

    private ObservableList<Author> authorList = FXCollections.observableArrayList();

    static ResourceBundle bundle = FxmlUtils.getResource();

    @FXML
    public void initialize(){
        loadAuthorList();

        addAuthorButton.disableProperty().bind(authorNameTextField.textProperty().isEmpty());
        addAuthorButton.disableProperty().bind(authorSurnameTextField.textProperty().isEmpty());

        authorComboBox.setItems(authorList);
        authorListView.setItems(authorList);
    }

    @FXML
    public void addAuthor() throws SQLException {

        DbManager.createConnectionSource();

        TableUtils.createTableIfNotExists(DbManager.getConnectionSource(), Author.class);
        Dao<Author, Integer> dao = DaoManager.createDao(DbManager.getConnectionSource(), Author.class);
        Author author = new Author();
        author.setName(authorNameTextField.getText());
        author.setSurname(authorSurnameTextField.getText());
        dao.create(author);
        DbManager.closeConnectionSource();
        authorNameTextField.clear();
        authorSurnameTextField.clear();
        loadAuthorList();
    }

    @FXML
    public void deleteAuthor() {
        try {
            Dao<Author, Integer> dao = DaoManager.createDao(DbManager.getConnectionSource(), Author.class);
            dao.delete((Author) authorComboBox.getSelectionModel().getSelectedItem());
        } catch (SQLException e) {
            System.out.println(e.getCause().getMessage());
        }
        loadAuthorList();
    }

    public void loadAuthorList(){
        authorList.clear();
        try{
            Dao<Author, Integer> dao = DaoManager.createDao(DbManager.getConnectionSource(), Author.class);
            List<Author> result = dao.queryBuilder().query();
            result.forEach(e->{
                authorList.add(e);
            });
        }catch (SQLException e){
            System.out.println(bundle.getString("error.during.loading.list") + e.getMessage());
        }
    }
}
