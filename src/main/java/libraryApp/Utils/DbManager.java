package libraryApp.Utils;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import org.apache.log4j.PropertyConfigurator;
import java.io.IOException;
import java.util.Optional;

public class DbManager {

    public static final Logger LOGGER = LoggerFactory.getLogger(DbManager.class);
    private static final String JDBC_CONNECTION_H2 = "jdbc:h2:./library";
    private static ConnectionSource connectionSource;
    private static final String log4jConfPath = "src/main/java/log4j.properties";

    public static void createConnectionSource() {
        PropertyConfigurator.configure(log4jConfPath);
        try {
            connectionSource = new JdbcConnectionSource(JDBC_CONNECTION_H2);
        } catch (Exception e) {
            Optional<ButtonType> result = DialogUtils.errorDuringCreateConnectionSource(e.getMessage());
            if(result.get() == ButtonType.OK || result.get() == ButtonType.CANCEL){
                Platform.exit();
                System.exit(0);
            }
        }
    }

    public static ConnectionSource getConnectionSource() {
        if(connectionSource == null)
                createConnectionSource();
        return connectionSource;
    }

    public static void closeConnectionSource(){
        if(connectionSource != null){
            try {
                connectionSource.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
