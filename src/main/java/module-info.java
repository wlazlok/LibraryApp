module LibraryApp {

    requires javafx.controls;
    requires javafx.fxml;
    requires ormlitebuild;
    requires log4j;
    requires java.sql;

    opens libraryApp;
    opens libraryApp.Controllers;
    opens libraryApp.Utils;
    opens libraryApp.Models;
}