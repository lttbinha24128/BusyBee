package busybee.auth;

import busybee.app.MainApp;
import busybee.model.UserAccount;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtPasswordVisible;

    @FXML
    private Button btnTogglePassword;

    @FXML
    private Label lblError;

    // Service xử lý đăng nhập
    private AuthService authService = new AuthService();

    private boolean isPasswordVisible = false;

    @FXML
    private void initialize() {
        // Đồng bộ text giữa PasswordField và TextField
        txtPasswordVisible.textProperty().bindBidirectional(txtPassword.textProperty());

        // Ẩn TextField ban đầu
        txtPasswordVisible.setVisible(false);
        txtPasswordVisible.setManaged(false);
    }

    // Hàm toggle hiện/ẩn mật khẩu
    @FXML
    private void handleTogglePassword() {
        isPasswordVisible = !isPasswordVisible;

        if (isPasswordVisible) {
            txtPasswordVisible.setVisible(true);
            txtPasswordVisible.setManaged(true);
            txtPassword.setVisible(false);
            txtPassword.setManaged(false);
            btnTogglePassword.setText("🙈");
        } else {
            txtPassword.setVisible(true);
            txtPassword.setManaged(true);
            txtPasswordVisible.setVisible(false);
            txtPasswordVisible.setManaged(false);
            btnTogglePassword.setText("👁");
        }
    }

    // Hàm được gọi khi người dùng bấm nút Login
    @FXML
    public void handleLogin() {
        String username = txtUsername.getText();
        String password = isPasswordVisible ? txtPasswordVisible.getText() : txtPassword.getText();

        UserAccount user = authService.login(username, password);

        if (user == null) {
            lblError.setText("Incorrect username or password");
            return;
        }

        // Lưu session
        Session.setCurrentUser(user);

        // Chuyển sang main layout
        MainApp.changeScene("/busybee/layout/main_layout.xml");
        MainApp.getPrimaryStage().setMaximized(true);
    }
}
