package busybee.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        // Mở màn hình login khi khởi động
        changeScene("/busybee/auth/login.xml");

        stage.setTitle("BusyBee Management System");
        stage.show();
    }

    public static void changeScene(String xmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(xmlPath));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            // Load CSS chung cho toàn app
            scene.getStylesheets().add(
                MainApp.class.getResource("/busybee/style/busybee.css").toExternalForm()
            );

            primaryStage.setScene(scene);

            // Căn giữa lại mỗi lần đổi scene
            primaryStage.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
