package seedu.address.ui;

import java.util.Comparator;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.task.Task;
import seedu.address.ui.exceptions.GuiException;

public class TaskCard extends UiPart<Region> {
    private static final String FXML = "TaskCard.fxml";

    @FXML
    private Label name;

    @FXML
    private Label timestamp;

    @FXML
    private Label description;

    @FXML
    private FlowPane tags;

    @FXML
    private CheckBox isCompleted;

    private final Task task;

    /**
     * Creates a card representing a task. Used in a task list to display a task.
     * @param task The task to represent
     * @param oneIndex The position of the task in the list in one-based indexing
     */
    public TaskCard(Task task, int oneIndex, TaskListPanel.TaskEditor taskEditor) {
        super(FXML);

        this.task = task;

        tags.prefWrapLengthProperty().bind(getRoot().widthProperty().divide(1.5));

        name.setText(oneIndex + ".  " + task.getTitle());
        description.setText(task.getDescription());
        if (task.getDescription() == null) {
            description.setVisible(false);
            description.setManaged(false);
        }
        if (task.getTags().isEmpty()) {
            tags.setVisible(false);
            tags.setManaged(false);
        } else {
            task.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .map(tag -> new Label(tag.tagName))
                    .forEach(tags.getChildren()::add);
        }
        if (task.getTimestamp() == null) {
            timestamp.setVisible(false);
            timestamp.setManaged(false);
        } else {
            timestamp.setText(
                    Optional.ofNullable(task.getTimestamp()).map(ts -> "\uD83D\uDD52 " + ts.toString()).orElse(""));
        }
        isCompleted.setText("");
        isCompleted.setSelected(task.getIsDone());
        isCompleted.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            Task newTask = new Task(
                task.getTitle(),
                task.getDescription(),
                task.getTimestamp(),
                task.getTags(),
                newValue
            );
            try {
                taskEditor.updateTask(task, newTask);
            } catch (GuiException e) {
                e.printStackTrace();
            }
        });
    }
}
