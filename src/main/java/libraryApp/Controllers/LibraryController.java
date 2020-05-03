package libraryApp.Controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import libraryApp.Models.Book;
import libraryApp.Models.Category;
import libraryApp.Utils.DbManager;
import libraryApp.Utils.FxmlUtils;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class LibraryController {
    @FXML
    public TreeView treeViewListOfBooks;

    private TreeItem<String> root = new TreeItem<>();

    static ResourceBundle bundle = FxmlUtils.getResource();

    @FXML
    public void initialize() {
        laodBooks();
        treeViewListOfBooks.setRoot(root);
    }

    public void laodBooks() {
        Dao<Category, Integer> dao = null;
        try {
            dao = DaoManager.createDao(DbManager.getConnectionSource(), Category.class);
            List<Category> result = dao.queryBuilder().query();
            result.forEach(e -> {
                TreeItem<String> categoryItem = new TreeItem<>(e.getCategoryName());
                try {
                    Dao<Book, ImageCursor> daoBook = DaoManager.createDao(DbManager.getConnectionSource(), Book.class);
                    List<Book> resultBook = daoBook.query(daoBook.queryBuilder().where().eq("CATEGORY_ID", e).prepare());
                    resultBook.forEach(e1->{
                        TreeItem<String> categoryItemBook = new TreeItem<>(e1.getTitle());
                        TreeItem<String> descBook = new TreeItem<>(e1.getDesc());
                        categoryItemBook.getChildren().add(descBook);
                        categoryItem.getChildren().add(categoryItemBook);
                    });
                } catch (SQLException error) {
                    System.out.println(bundle.getString("error.during.loading.list") + error.getMessage());
                }

                root.getChildren().add(categoryItem);
            });
        } catch (SQLException e) {
            System.out.println(bundle.getString("error.during.loading.list") + e.getMessage());
        }
    }
}
