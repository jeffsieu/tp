package seedu.address.ui;

import javafx.scene.control.ListCell;
import seedu.address.model.task.filters.TaskFilters;

public class TaskFilterChipCell extends ListCell<TaskFilters.TaskFilter> {
    @Override
    protected void updateItem(TaskFilters.TaskFilter taskFilter, boolean empty) {
        super.updateItem(taskFilter, empty);

        setText(null);
        if (empty || taskFilter == null) {
            setGraphic(null);
        } else {
            setGraphic(new DeletableChip(taskFilter.toString(), () ->
                 getListView().getItems().remove(taskFilter)).getRoot());
        }
    }
}
