package libraryApp.Controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import libraryApp.Models.Author;
import libraryApp.Models.Book;
import libraryApp.Models.Category;
import libraryApp.Utils.DbManager;
import libraryApp.Utils.DialogUtils;
import libraryApp.Utils.FxmlUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class BookController {
    @FXML
    public TextField textFieldCategory;
    @FXML
    public ComboBox categoryComboBox;
    @FXML
    public Button addBookButton;
    @FXML
    public ComboBox authorComboBox;
    @FXML
    public TextArea textAreaDescription;
    @FXML
    public Slider rateSlider;
    @FXML
    public TextField textareaISBN;

    private ObservableList<Category> categoyList = FXCollections.observableArrayList();
    private ObservableList<Author> authorList = FXCollections.observableArrayList();
    private List<Book> bookList = FXCollections.observableArrayList();

    static ResourceBundle bundle = FxmlUtils.getResource();


    @FXML
    public void initialize()
    {

        loadCategoryList();
        loadAuthorList();

        categoryComboBox.setItems(categoyList);
        authorComboBox.setItems(authorList);
    }

    @FXML
    public void addBook() throws SQLException{

        DbManager.createConnectionSource();

        TableUtils.createTableIfNotExists(DbManager.getConnectionSource(), Book.class);
        Book book = new Book();
        book.setTitle(textFieldCategory.getText());
        book.setDescription(textAreaDescription.getText());
        book.setISBN(textareaISBN.getText());
        book.setRate(Integer.valueOf((int) rateSlider.getValue()));
        book.setAuthor((Author) authorComboBox.getSelectionModel().getSelectedItem());
        Dao<Book, Integer> daoBook = DaoManager.createDao(DbManager.getConnectionSource(), Book.class);
        book.setCategory((Category) categoryComboBox.getSelectionModel().getSelectedItem());
        bookList = daoBook.query(daoBook.queryBuilder().where().eq("TITLE", textFieldCategory.getText()).prepare());
        if(bookList.size() == 0)
            daoBook.create(book);
        else{
            DialogUtils.bookExsistInDataBase();
        }
        textAreaDescription.clear();
        textareaISBN.clear();
        textFieldCategory.clear();
        categoryComboBox.getSelectionModel().select(-1);
        authorComboBox.getSelectionModel().select(-1);
        rateSlider.setValue(1);
    }

    public void loadCategoryList() {
        categoyList.clear();
        try {
            Dao<Category, Integer> dao = DaoManager.createDao(DbManager.getConnectionSource(), Category.class);
            List<Category> result = dao.queryBuilder().query();
            result.forEach(e->{
                categoyList.add(e);
            });
        } catch (SQLException e) {
            System.out.println(e.getCause().getMessage());
        }
    }

    public void loadAuthorList(){
        authorList.clear();
        try{
            Dao<Author, Integer> dao  = DaoManager.createDao(DbManager.getConnectionSource(), Author.class);
            List<Author> result = dao.queryBuilder().query();
            result.forEach(e->{
                authorList.add(e);
            });
        }catch (SQLException e) {
            System.out.println(e.getCause().getMessage());
        }
    }
}
