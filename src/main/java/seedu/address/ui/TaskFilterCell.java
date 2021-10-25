package seedu.address.ui;

import javafx.scene.control.cell.ComboBoxListCell;
import seedu.address.model.task.filters.TaskFilter;

public class TaskFilterCell extends ComboBoxListCell<TaskFilter> {

    @Override
    public void updateItem(TaskFilter taskFilter, boolean empty) {
        super.updateItem(taskFilter, empty);

        setGraphic(null);
        if (empty || taskFilter == null) {
            setText(null);
        } else {
            setText(taskFilter.toDisplayString());
        }
    }
}
