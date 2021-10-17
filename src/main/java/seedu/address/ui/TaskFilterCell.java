package seedu.address.ui;

import javafx.scene.control.cell.ComboBoxListCell;
import seedu.address.model.task.filters.TaskFilters;

public class TaskFilterCell extends ComboBoxListCell<TaskFilters.TaskFilter> {

    @Override
    public void updateItem(TaskFilters.TaskFilter taskFilter, boolean empty) {
        super.updateItem(taskFilter, empty);

        if (empty || taskFilter == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(taskFilter.toString());
        }
    }
}
