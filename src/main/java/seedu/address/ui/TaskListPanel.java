package seedu.address.ui;

import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.task.Task;
import seedu.address.model.task.filters.TaskFilters.TaskFilter;
import seedu.address.ui.exceptions.GuiException;

public class TaskListPanel extends UiPart<Region> {

    @FXML
    private ComboBox<TaskFilter> filterComboBox;

    @FXML
    private ListView<Task> taskListView;

    @FXML
    private FlowPane filterFlowPane;

    private final ObservableList<TaskFilter> filterOptions;
    private final ObservableList<TaskFilter> selectedFilters;

    /**
     * Creates a TaskListPanel displaying a given list of tasks.
     * @param taskList the list of tasks to display
     */
    public TaskListPanel(
            ObservableList<Task> taskList,
            ObservableList<TaskFilter> filterOptions,
            ObservableList<TaskFilter> selectedFilters,
            Consumer<TaskFilter> addTaskFilter,
            Consumer<TaskFilter> removeTaskFilter,
            TaskEditor taskEditor) {
        super("TaskListPanel.fxml");
        this.filterOptions = filterOptions;
        this.selectedFilters = selectedFilters;

        taskListView.setItems(taskList);
        taskListView.setCellFactory(tasks -> new TaskListViewCell(taskEditor));

        filterComboBox.setItems(filterOptions);
        filterComboBox.setCellFactory(taskFilters -> new TaskFilterCell());
        filterComboBox.setPromptText(null);
        filterComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                addTaskFilter.accept(newValue);

                Platform.runLater(filterComboBox.getSelectionModel()::clearSelection);
            }
        });

        // Update FlowPane containing a node for each selected filter
        selectedFilters.addListener((Observable observable) -> {
            filterFlowPane.getChildren().clear();
            selectedFilters.stream()
                    .map(taskFilter -> new TaskFilterChip(taskFilter, removeTaskFilter).getRoot())
                    .forEach(filterFlowPane.getChildren()::add);
        });
    }

    @FunctionalInterface
    interface TaskEditor {
        void updateTask(Task oldTask, Task newTask) throws GuiException;
    }
}
