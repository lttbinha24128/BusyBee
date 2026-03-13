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

        // mở login
        changeScene("/busybee/auth/login.xml", "login.css");

        stage.setTitle("BusyBee Management System");
        stage.show();
    }

    //Đổi scene và load CSS
     
    public static void changeScene(String fxmlPath, String... cssFiles) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    MainApp.class.getResource(fxmlPath)
            );

            Parent root = loader.load();

            Scene scene = new Scene(root);

            /* ===== CSS GLOBAL ===== */

            scene.getStylesheets().add(
                    MainApp.class
                            .getResource("/busybee/style/busybee.css")
                            .toExternalForm()
            );

            /* ===== CSS SCENE ===== */

            if (cssFiles != null) {

                for (String css : cssFiles) {

                    scene.getStylesheets().add(
                            MainApp.class
                                    .getResource("/busybee/style/" + css)
                                    .toExternalForm()
                    );

                }

            }

            primaryStage.setScene(scene);

            // căn giữa lại
            primaryStage.centerOnScreen();

        } catch (Exception e) {

            System.err.println("Error loading scene: " + fxmlPath);
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