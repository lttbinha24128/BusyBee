package busybee.layout.sidebar;

import busybee.layout.MainLayoutController;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

public class SidebarDirectorController {

    @FXML
    private TreeView<String> sidebarTree;

    @FXML
    private void initialize() {

        // Root invisible
        TreeItem<String> root = new TreeItem<>("Root");
        root.setExpanded(true);
        sidebarTree.setRoot(root);
        sidebarTree.setShowRoot(false);

        // Dashboard
        TreeItem<String> dashboard = new TreeItem<>("🏠 Dashboard");
        root.getChildren().add(dashboard);

        // System Management
        TreeItem<String> sysMgmt = new TreeItem<>("⚙ System Management");
        sysMgmt.getChildren().add(new TreeItem<>("Users & Roles"));
        root.getChildren().add(sysMgmt);

        // Human Resources
        TreeItem<String> hr = new TreeItem<>("👥 Human Resources");
        hr.getChildren().add(new TreeItem<>("Office Staff"));
        hr.getChildren().add(new TreeItem<>("Staff Schedule"));
        hr.getChildren().add(new TreeItem<>("Recruitment"));
        hr.getChildren().add(new TreeItem<>("Benefits"));
        root.getChildren().add(hr);

        // Collaborator Management
        TreeItem<String> helper = new TreeItem<>("🤝 Collaborator Management");
        helper.getChildren().add(new TreeItem<>("Profile"));
        helper.getChildren().add(new TreeItem<>("Schedule"));
        helper.getChildren().add(new TreeItem<>("Assignment"));
        root.getChildren().add(helper);

        // =========================
        // CUSTOMER MANAGEMENT
        // =========================
        TreeItem<String> customer = new TreeItem<>("👤 Customer Management");
        customer.getChildren().add(new TreeItem<>("Customer List"));
        customer.getChildren().add(new TreeItem<>("Customer Reports"));
        root.getChildren().add(customer);

        // =========================
        // SERVICE REQUESTS
        // =========================
        TreeItem<String> requests = new TreeItem<>("📋 Service Requests");
        requests.getChildren().add(new TreeItem<>("Request List"));
        requests.getChildren().add(new TreeItem<>("Request Reports"));
        root.getChildren().add(requests);

        // =========================
        // BILLING
        // =========================
        TreeItem<String> billing = new TreeItem<>("💳 Billing");
        billing.getChildren().add(new TreeItem<>("Billing List"));
        billing.getChildren().add(new TreeItem<>("Billing Reports"));
        root.getChildren().add(billing);

        // Inventory
        TreeItem<String> inventory = new TreeItem<>("📦 Inventory");
        root.getChildren().add(inventory);

        // Reports
        TreeItem<String> reports = new TreeItem<>("💰 Reports");
        reports.getChildren().add(new TreeItem<>("Salary"));
        reports.getChildren().add(new TreeItem<>("Feedback"));
        root.getChildren().add(reports);

        // Promotion
        TreeItem<String> promotion = new TreeItem<>("🎉 Promotion");
        promotion.getChildren().add(new TreeItem<>("All Promotions"));
        promotion.getChildren().add(new TreeItem<>("Promotion Reports"));
        root.getChildren().add(promotion);

        // CLICK 1 LẦN ĐỂ DROPDOWN
        sidebarTree.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            TreeItem<String> selected = sidebarTree.getSelectionModel().getSelectedItem();

            if (selected != null && !selected.isLeaf()) {
                selected.setExpanded(!selected.isExpanded());
            }

        });

        // =========================
        // NAVIGATION
        // =========================
        sidebarTree.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {

            if (newVal == null) {
                return;
            }

            String value = newVal.getValue();

            switch (value) {

                case "🏠 Dashboard" ->
                    MainLayoutController.getInstance().showContent("Dashboard VIEW HERE");

                case "Users & Roles" ->
                    MainLayoutController.getInstance().showContent("USERS & ROLES VIEW HERE");

                case "Office Staff" ->
                    MainLayoutController.getInstance().showContent("OFFICE STAFF VIEW HERE");

                case "Staff Schedule" ->
                    MainLayoutController.getInstance().showContent("STAFF SCHEDULE VIEW HERE");

                case "Recruitment" ->
                    MainLayoutController.getInstance().showContent("RECRUITMENT VIEW HERE");

                case "Benefits" ->
                    MainLayoutController.getInstance().showContent("BENEFITS VIEW HERE");

                case "Profile" ->
                    MainLayoutController.getInstance().showContent("HELPER PROFILE VIEW HERE");

                case "Schedule" ->
                    MainLayoutController.getInstance().showContent("HELPER SCHEDULE VIEW HERE");

                case "Assignment" ->
                    MainLayoutController.getInstance().showContent("ASSIGNMENT VIEW HERE");

                // CUSTOMER
                case "Customer List" ->
                    MainLayoutController.getInstance()
                            .loadContent("/busybee/customer/view/customer_list.xml");

                case "Customer Reports" ->
                    MainLayoutController.getInstance()
                            .loadContent("/busybee/customer/view/customer_report.xml");

                // REQUEST
                case "Request List" ->
                    MainLayoutController.getInstance()
                            .loadContent("/busybee/request/view/request_list.xml");

                case "Request Reports" ->
                    MainLayoutController.getInstance()
                            .loadContent("/busybee/request/view/request_report.xml");

                // BILLING
                case "Billing List" ->
                    MainLayoutController.getInstance()
                            .loadContent("/busybee/billing/view/billing_list.xml");

                case "Billing Reports" ->
                    MainLayoutController.getInstance()
                            .loadContent("/busybee/billing/view/billing_report.xml");

                case "📦 Inventory" ->
                    MainLayoutController.getInstance().showContent("INVENTORY VIEW HERE");

                case "Salary" ->
                    MainLayoutController.getInstance().showContent("SALARY VIEW HERE");

                case "Feedback" ->
                    MainLayoutController.getInstance().showContent("REPORTS & FEEDBACK VIEW HERE");

                // PROMOTION
                case "All Promotions" ->
                    MainLayoutController.getInstance()
                            .loadContent("/busybee/promotion/view/promotion_list.xml");

                case "Promotion Reports" ->
                    MainLayoutController.getInstance()
                            .loadContent("/busybee/promotion/view/promotion_reports.xml");
            }

        });

    }
}
