package busybee.layout;

import busybee.app.MainApp;
import busybee.auth.Role;
import busybee.auth.Session;
import busybee.auth.UserAccount;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainLayoutController {

    private static MainLayoutController instance;

    public static MainLayoutController getInstance() {
        return instance;
    }

    // VIEW CACHE (không load lại FXML)
    private Map<String, Node> viewCache = new HashMap<>();

    // TOP HEADER
    @FXML private Label lblName;
    @FXML private Label lblRole;
    @FXML private ImageView imgAvatar;
    @FXML private Label lblBreadcrumbRole;

    // SIDEBAR
    @FXML private VBox sidebarContainer;

    // MAIN CONTENT
    @FXML private StackPane mainContent;

    @FXML
    public void initialize() {

        // set singleton instance
        instance = this;

        UserAccount user = Session.getCurrentUser();
        if (user == null) return;

        // Hiển thị tên và role
        lblName.setText(user.getUsername());
        lblRole.setText(user.getRole().toString());

        // Load sidebar theo role
        loadSidebarByRole(user.getRole());
    }

    @FXML
    private void handleNotification() {
        System.out.println("Notification button clicked!");
    }

    @FXML
    private void handleLogout() {
        Session.clear();
        MainApp.changeScene("/busybee/auth/login.xml");
    }

    private void loadSidebarByRole(Role role) {

        String fxmlFile = null;

        switch (role) {

            case DIRECTOR:
                fxmlFile = "/busybee/layout/sidebar/sidebar_director.xml";
                break;

            case RECEPTIONIST:
                fxmlFile = "/busybee/layout/sidebar/sidebar_reception.xml";
                break;

            case OFFICE_STAFF:
                fxmlFile = "/busybee/layout/sidebar/sidebar_office.xml";
                break;

            case WAREHOUSE:
                fxmlFile = "/busybee/layout/sidebar/sidebar_warehouse.xml";
                break;

            default:
                System.out.println("Unrecognized role: " + role);
                return;
        }

        try {

            Node sidebar = FXMLLoader.load(getClass().getResource(fxmlFile));

            sidebarContainer.getChildren().setAll(sidebar);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // DEMO TEXT CONTENT
    public void showContent(String title) {

        mainContent.getChildren().clear();

        Label label = new Label(title);
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        mainContent.getChildren().add(label);
    }

    // LOAD FXML VÀ CACHE
    public void loadContent(String fxmlPath) {

        try {

            Node view;

            if (viewCache.containsKey(fxmlPath)) {

                // dùng view đã load
                view = viewCache.get(fxmlPath);

            } else {

                // load lần đầu
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource(fxmlPath)
                );

                view = loader.load();

                // lưu vào cache
                viewCache.put(fxmlPath, view);
            }

            // hiển thị
            mainContent.getChildren().setAll(view);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}