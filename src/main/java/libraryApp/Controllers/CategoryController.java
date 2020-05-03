package libraryApp.Controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import libraryApp.Models.Category;
import libraryApp.Utils.DbManager;
import libraryApp.Utils.DialogUtils;
import libraryApp.Utils.FxmlUtils;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CategoryController {

    @FXML
    public TextField textFieldCategory;
    @FXML
    public ListView categoryListView;
    @FXML
    public Button addCategoryButton;
    @FXML
    public ComboBox categoryComboBox;
    @FXML
    public Button deleteCategoryButton;

    static ResourceBundle bundle = FxmlUtils.getResource();

    final ObservableList<String> categoryList = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        this.addCategoryButton.disableProperty().bind(textFieldCategory.textProperty().isEmpty());
        loadCategoryList();
        categoryComboBox.setItems(categoryList);
    }

    @FXML
    public void addCategory() throws SQLException{

        DbManager.createConnectionSource();

        //TableUtils.dropTable(DbManager.getConnectionSource(), Category.class, true);
        TableUtils.createTableIfNotExists(DbManager.getConnectionSource(), Category.class);

        Category category = new Category();
        category.setCategoryName(textFieldCategory.getText());

        Dao<Category, Integer> dao = DaoManager.createDao(DbManager.getConnectionSource(), Category.class);
        dao.create(category);
        textFieldCategory.clear();

        DbManager.closeConnectionSource();

        loadCategoryList();
    }

    public void loadCategoryList(){
        categoryListView.setItems(categoryList);
        categoryList.clear();
        try {
            Dao<Category, Integer> dao = DaoManager.createDao(DbManager.getConnectionSource(), Category.class);
            List<Category> result = dao.queryBuilder().query();
            result.forEach(e->{
                categoryList.add(e.getCategoryName());
            });

        } catch (SQLException e) {
            System.out.println(bundle.getString("error.during.loading.list") + e.getMessage());
        }
    }
    @FXML
    public void deleteCategory() {

        try {
            Dao<Category, Integer> dao = DaoManager.createDao(DbManager.getConnectionSource(), Category.class);
            if(categoryComboBox.getSelectionModel().getSelectedItem()== null) {
                DialogUtils.deleteCategory();
                initialize();
            }
            else {
                List<Category> categoryList = dao.query(dao.queryBuilder().where().eq("CATEGORY_NAME",
                        categoryComboBox.getSelectionModel().getSelectedItem().toString()).prepare());
                categoryList.forEach(e -> {
                    try {
                        dao.delete(e);
                    } catch (SQLException e1) {
                        System.out.println(bundle.getString("error.during.deleting.category") + e1.getMessage());
                    }
                });
            }
        } catch (SQLException e) {
            System.out.println(bundle.getString("error.during.deleting.category") + e.getMessage());
        }
        loadCategoryList();
    }
}
