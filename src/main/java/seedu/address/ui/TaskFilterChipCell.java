package seedu.address.ui;

import javafx.scene.control.ListCell;
import seedu.address.model.task.filters.TaskFilter;

public class TaskFilterChipCell extends ListCell<TaskFilter> {
    @Override
    protected void updateItem(TaskFilter taskFilter, boolean empty) {
        super.updateItem(taskFilter, empty);

        setText(null);
        if (empty || taskFilter == null) {
            setGraphic(null);
        } else {
            setGraphic(new DeletableChip(taskFilter.toDisplayString(), () ->
                 getListView().getItems().remove(taskFilter)).getRoot());
        }
    }
}
