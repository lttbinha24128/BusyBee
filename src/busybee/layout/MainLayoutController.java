package busybee.layout;

import busybee.app.MainApp;
import busybee.auth.Role;
import busybee.auth.Session;
import busybee.model.UserAccount;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainLayoutController {

    private static MainLayoutController instance;

    public MainLayoutController() {
        instance = this;
    }

    public static MainLayoutController getInstance() {
        return instance;
    }

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
    private void handleNotification() {
        System.out.println("Notification button clicked!");
        // TODO: mở popup hoặc danh sách thông báo
    }

    @FXML
    private void handleLogout() {
        Session.clear();
        MainApp.changeScene("/busybee/auth/login.xml");
    }

    @FXML
    public void initialize() {
        UserAccount user = Session.getCurrentUser();
        if (user == null) return;

        // Hiển thị tên và role
        lblName.setText(user.getUsername());
        lblRole.setText(user.getRole().toString());

 

        // Avatar: chưa có dữ liệu, có thể để trống hoặc gắn ảnh mặc định sau này

        // Nạp sidebar theo role
        loadSidebarByRole(user.getRole());
    }

    private void loadSidebarByRole(Role role) {
        String fxmlFile = null;
        switch (role) {
            case DIRECTOR: fxmlFile = "/busybee/layout/sidebar/sidebar_director.xml"; break;
            case RECEPTIONIST: fxmlFile = "/busybee/layout/sidebar/sidebar_reception.xml"; break;
            case OFFICE_STAFF: fxmlFile = "/busybee/layout/sidebar/sidebar_office.xml"; break;
            case WAREHOUSE: fxmlFile = "/busybee/layout/sidebar/sidebar_warehouse.xml"; break;
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

    public void showContent(String title) {
        mainContent.getChildren().clear();
        Label label = new Label(title);
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        mainContent.getChildren().add(label);
    }
}
