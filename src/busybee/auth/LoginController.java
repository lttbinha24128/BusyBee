package busybee.auth;

import busybee.app.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

    private final AuthService authService = new AuthService();

    private boolean isPasswordVisible = false;

    // =========================
    // INIT
    // =========================
    @FXML
    private void initialize() {

        // bind 2 field password
        txtPasswordVisible.textProperty().bindBidirectional(txtPassword.textProperty());

        // ẩn field visible ban đầu
        txtPasswordVisible.setVisible(false);
        txtPasswordVisible.setManaged(false);

        lblError.setText("");
    }

    // =========================
    // TOGGLE PASSWORD
    // =========================
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

    // =========================
    // LOGIN
    // =========================
    @FXML
    private void handleLogin() {

        String username = txtUsername.getText().trim();
        String password = isPasswordVisible
                ? txtPasswordVisible.getText().trim()
                : txtPassword.getText().trim();

        // validate input
        if (username.isEmpty() || password.isEmpty()) {
            lblError.setText("Please enter username and password");
            return;
        }

        // gọi service
        UserAccount user = authService.login(username, password);

        if (user == null) {
            lblError.setText("Incorrect username or password");
            return;
        }

        // 🔥 lưu session
        Session.setCurrentUser(user);

        // debug (có thể xoá sau)
        System.out.println("Login success - Role: " + user.getRole());

        // chuyển màn hình chính
        MainApp.changeScene("/busybee/layout/main_layout.xml");
        MainApp.getPrimaryStage().setMaximized(true);
    }
}