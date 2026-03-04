package busybee.layout.sidebar;

import busybee.layout.MainLayoutController;
import javafx.fxml.FXML;

public class SidebarWarehouseController {

    @FXML private void showDashboard() {
        MainLayoutController.getInstance().showContent("Warehouse Dashboard VIEW HERE");
    }

    // Tools & Supplies
    @FXML private void showItemList() {
        MainLayoutController.getInstance().showContent("ITEM LIST VIEW HERE");
    }
    @FXML private void showUpdateItem() {
        MainLayoutController.getInstance().showContent("ADD/UPDATE ITEM VIEW HERE");
    }

    // Stock Management
    @FXML private void showStockIn() {
        MainLayoutController.getInstance().showContent("STOCK IN VIEW HERE");
    }
    @FXML private void showStockOut() {
        MainLayoutController.getInstance().showContent("STOCK OUT VIEW HERE");
    }
    @FXML private void showInventoryCheck() {
        MainLayoutController.getInstance().showContent("INVENTORY CHECK VIEW HERE");
    }

    // Damage & Expiry
    @FXML private void showReportDamage() {
        MainLayoutController.getInstance().showContent("REPORT DAMAGE VIEW HERE");
    }
    @FXML private void showReportExpiry() {
        MainLayoutController.getInstance().showContent("REPORT EXPIRY VIEW HERE");
    }
    @FXML private void showDamageExpiryList() {
        MainLayoutController.getInstance().showContent("DAMAGE/EXPIRY LIST VIEW HERE");
    }
    @FXML private void showDamageAction() {
        MainLayoutController.getInstance().showContent("ACTION TAKEN VIEW HERE");
    }

    // Distribution
    @FXML private void showDistributionRequest() {
        MainLayoutController.getInstance().showContent("REQUEST DISTRIBUTION VIEW HERE");
    }
    @FXML private void showPendingDistribution() {
        MainLayoutController.getInstance().showContent("PENDING DISTRIBUTION VIEW HERE");
    }
    @FXML private void showCompletedDistribution() {
        MainLayoutController.getInstance().showContent("COMPLETED DISTRIBUTION VIEW HERE");
    }
    @FXML private void showReturnHandling() {
        MainLayoutController.getInstance().showContent("RETURN HANDLING VIEW HERE");
    }

    // Reports
    @FXML private void showStockReport() {
        MainLayoutController.getInstance().showContent("STOCK REPORT VIEW HERE");
    }
    @FXML private void showUsageReport() {
        MainLayoutController.getInstance().showContent("USAGE REPORT VIEW HERE");
    }
    @FXML private void showDistributionReport() {
        MainLayoutController.getInstance().showContent("DISTRIBUTION REPORT VIEW HERE");
    }
    @FXML private void showDamageExpiryReport() {
        MainLayoutController.getInstance().showContent("DAMAGE/EXPIRY REPORT VIEW HERE");
    }
}
