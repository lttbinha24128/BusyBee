package busybee.layout.sidebar;

import busybee.layout.MainLayoutController;
import javafx.fxml.FXML;

public class SidebarOfficeController {

    @FXML private void showDashboard() {
        MainLayoutController.getInstance().showContent("Office Staff Dashboard VIEW HERE");
    }

    // Service Requests
    @FXML private void showNewRequests() {
        MainLayoutController.getInstance().showContent("NEW REQUESTS VIEW HERE");
    }
    @FXML private void showProcessingRequests() {
        MainLayoutController.getInstance().showContent("PROCESSING REQUESTS VIEW HERE");
    }
    @FXML private void showCompletedRequests() {
        MainLayoutController.getInstance().showContent("COMPLETED REQUESTS VIEW HERE");
    }

    // Helper Management
    @FXML private void showHelperList() {
        MainLayoutController.getInstance().showContent("HELPER LIST VIEW HERE");
    }
    @FXML private void showHelperAvailability() {
        MainLayoutController.getInstance().showContent("HELPER AVAILABILITY VIEW HERE");
    }
    @FXML private void showHelperPerformance() {
        MainLayoutController.getInstance().showContent("HELPER PERFORMANCE VIEW HERE");
    }

    // Assignment
    @FXML private void showAssignmentRequests() {
        MainLayoutController.getInstance().showContent("ASSIGNMENT REQUESTS VIEW HERE");
    }
    @FXML private void showAssignmentStatus() {
        MainLayoutController.getInstance().showContent("ASSIGNMENT STATUS VIEW HERE");
    }
    @FXML private void showReassignment() {
        MainLayoutController.getInstance().showContent("REASSIGNMENT VIEW HERE");
    }

    // Inventory Request
    @FXML private void showInventoryRequest() {
        MainLayoutController.getInstance().showContent("INVENTORY REQUEST VIEW HERE");
    }
}
