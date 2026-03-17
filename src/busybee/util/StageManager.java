package busybee.util;

import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class StageManager {

    private static StageManager instance;
    private List<Stage> managedStages;

    private StageManager() {
        managedStages = new ArrayList<>();
    }

    public static StageManager getInstance() {
        if (instance == null) {
            instance = new StageManager();
        }
        return instance;
    }

    /**
     * Adds a stage to be managed by the StageManager.
     * This allows for centralized stage management and cleanup.
     */
    public void addStage(Stage stage) {
        if (stage != null && !managedStages.contains(stage)) {
            managedStages.add(stage);

            // Remove from list when stage is closed
            stage.setOnHidden(event -> managedStages.remove(stage));
        }
    }

    /**
     * Removes a stage from management.
     */
    public void removeStage(Stage stage) {
        managedStages.remove(stage);
    }

    /**
     * Closes all managed stages.
     */
    public void closeAllStages() {
        for (Stage stage : new ArrayList<>(managedStages)) {
            if (stage.isShowing()) {
                stage.close();
            }
        }
        managedStages.clear();
    }

    /**
     * Gets the number of currently managed stages.
     */
    public int getStageCount() {
        return managedStages.size();
    }

    /**
     * Gets a list of all managed stages.
     */
    public List<Stage> getManagedStages() {
        return new ArrayList<>(managedStages);
    }

    /**
     * Brings all managed stages to front.
     */
    public void bringAllToFront() {
        for (Stage stage : managedStages) {
            if (stage.isShowing()) {
                stage.toFront();
            }
        }
    }
}