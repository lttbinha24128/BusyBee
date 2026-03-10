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

        // Mở màn hình login khi khởi động, kèm login.css
        changeScene("/busybee/auth/login.xml", "login.css");

        stage.setTitle("BusyBee Management System");
        stage.show();
    }

    /**
     * Đổi scene và load CSS
     * @param xmlPath đường dẫn FXML
     * @param cssFiles danh sách file CSS cần load thêm (ngoài busybee.css)
     */
    public static void changeScene(String xmlPath, String... cssFiles) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(xmlPath));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            // Luôn load CSS chung
            scene.getStylesheets().add(
                MainApp.class.getResource("/busybee/style/busybee.css").toExternalForm()
            );

            // Load thêm các CSS khác nếu có
            for (String cssFile : cssFiles) {
                scene.getStylesheets().add(
                    MainApp.class.getResource("/busybee/style/" + cssFile).toExternalForm()
                );
            }

            primaryStage.setScene(scene);

            // Căn giữa lại mỗi lần đổi scene
            primaryStage.centerOnScreen();

        } catch (Exception e) {
            System.err.println("Error loading scene: " + xmlPath);
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
