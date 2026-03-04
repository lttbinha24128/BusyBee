/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package busybee.auth;

/**
 *
 * @author 
 */
import busybee.app.MainApp;
import busybee.model.UserAccount;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    
    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblError;

    // Service xử lý đăng nhập 
    private AuthService authService = new AuthService();

    //Hàm được gọi khi người dùng bấm nút Login
     
@FXML
public void handleLogin() {

    String username = txtUsername.getText();
    String password = txtPassword.getText();

    UserAccount user = authService.login(username, password);

    if (user == null) {
        lblError.setText("Incorrect username or password");
        return;
    }

    // Lưu session
    Session.setCurrentUser(user);

    // CHUYỂN SANG MAIN LAYOUT
    MainApp.changeScene("/busybee/layout/main_layout.xml");
    MainApp.getPrimaryStage().setMaximized(true);
}
}