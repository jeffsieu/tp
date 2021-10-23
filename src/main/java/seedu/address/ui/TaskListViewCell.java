package seedu.address.ui;

import java.util.Optional;

import javafx.scene.control.ListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import seedu.address.model.task.Task;
import seedu.address.ui.exceptions.GuiException;

public class TaskListViewCell extends ListCell<Task> {

    private final TaskListPanel.TaskEditor taskEditor;

    public TaskListViewCell(TaskListPanel.TaskEditor taskEditor) {
        this.taskEditor = taskEditor;
    }

    @Override
    protected void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);

        if (empty || task == null) {
            setGraphic(null);
            setText(null);
        } else {
            setGraphic(new TaskCard(task, getIndex() + 1, taskEditor).getRoot());
            addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    event.consume();
                    showEditDialog(task);
                }
            });
            setOnMouseClicked((event) -> {
                // Double click
                if (event.getClickCount() == 2) {
                    event.consume();
                    showEditDialog(task);
                }
            });
        }
    }

    private void showEditDialog(Task task) {
        EditTaskDialog editTaskDialog = new EditTaskDialog(task);
        Optional<Task> editedTask = editTaskDialog.getDialog().showAndWait();
        if (editedTask.isPresent()) {
            try {
                taskEditor.updateTask(task, editedTask.get());
            } catch (GuiException e) {
                e.printStackTrace();
            }
        }
    }
}
