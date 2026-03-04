package busybee.layout.sidebar;

import busybee.layout.MainLayoutController;
import javafx.fxml.FXML;

public class SidebarDirectorController {

    // Dashboard
    @FXML private void showDashboard() {
        MainLayoutController.getInstance().showContent("Dashboard VIEW HERE");
    }

    // System Management
    @FXML private void showUsersRoles() {
        MainLayoutController.getInstance().showContent("USERS & ROLES VIEW HERE");
    }

    // Human Resources
    @FXML private void showOfficeStaff() {
        MainLayoutController.getInstance().showContent("OFFICE STAFF VIEW HERE");
    }
    @FXML private void showStaffSchedule() {
        MainLayoutController.getInstance().showContent("STAFF SCHEDULE VIEW HERE");
    }
    @FXML private void showRecruitment() {
        MainLayoutController.getInstance().showContent("RECRUITMENT VIEW HERE");
    }
    @FXML private void showBenefits() {
        MainLayoutController.getInstance().showContent("BENEFITS VIEW HERE");
    }

    // Helper Management
    @FXML private void showHelperProfile() {
        MainLayoutController.getInstance().showContent("HELPER PROFILE VIEW HERE");
    }
    @FXML private void showHelperSchedule() {
        MainLayoutController.getInstance().showContent("HELPER SCHEDULE VIEW HERE");
    }
    @FXML private void showAssignment() {
        MainLayoutController.getInstance().showContent("ASSIGNMENT VIEW HERE");
    }

    // Service & Customer
    @FXML private void showServiceMgmt() {
        MainLayoutController.getInstance().showContent("SERVICE MANAGEMENT VIEW HERE");
    }
    @FXML private void showCustomer() {
        MainLayoutController.getInstance().showContent("CUSTOMER MANAGEMENT VIEW HERE");
    }
    @FXML private void showRequest() {
        MainLayoutController.getInstance().showContent("SERVICE REQUESTS VIEW HERE");
    }

    // Inventory
    @FXML private void showInventory() {
        MainLayoutController.getInstance().showContent("INVENTORY VIEW HERE");
    }

    // Finance & Reports
    @FXML private void showBilling() {
        MainLayoutController.getInstance().showContent("BILLING VIEW HERE");
    }
    @FXML private void showSalary() {
        MainLayoutController.getInstance().showContent("SALARY VIEW HERE");
    }
    @FXML private void showReports() {
        MainLayoutController.getInstance().showContent("REPORTS & FEEDBACK VIEW HERE");
    }

    // Promotion submenu
    @FXML private void showPromotionList() {
        MainLayoutController.getInstance().showContent("PROMOTION LIST VIEW HERE");
    }
    @FXML private void showCreatePromotion() {
        MainLayoutController.getInstance().showContent("CREATE PROMOTION VIEW HERE");
    }
    @FXML private void showActivePromotions() {
        MainLayoutController.getInstance().showContent("ACTIVE PROMOTIONS VIEW HERE");
    }
    @FXML private void showExpiredPromotions() {
        MainLayoutController.getInstance().showContent("EXPIRED PROMOTIONS VIEW HERE");
    }
}
