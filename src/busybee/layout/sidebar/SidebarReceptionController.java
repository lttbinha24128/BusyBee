package busybee.layout.sidebar;

import busybee.layout.MainLayoutController;
import javafx.fxml.FXML;

public class SidebarReceptionController {

    @FXML private void showDashboard() {
        MainLayoutController.getInstance().showContent("Receptionist Dashboard VIEW HERE");
    }

    @FXML private void showCustomer() {
        MainLayoutController.getInstance().showContent("CUSTOMER MANAGEMENT VIEW HERE");
    }

    @FXML private void showRequest() {
        MainLayoutController.getInstance().showContent("SERVICE REQUESTS VIEW HERE");
    }

    @FXML private void showStatus() {
        MainLayoutController.getInstance().showContent("ONGOING STATUS VIEW HERE");
    }

    @FXML private void showBilling() {
        MainLayoutController.getInstance().showContent("BILLING / PAYMENT VIEW HERE");
    }
}
