package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import jdk.jfr.Timestamp;
import seedu.address.model.task.Task;

import java.util.Optional;

public class TaskCard extends UiPart<Region> {
    private static final String FXML = "TaskCard.fxml";

    @FXML
    private Label id;

    @FXML
    private Label name;

    @FXML
    private Label timestamp;

    @FXML
    private Label description;

    @FXML
    private CheckBox isCompleted;

    private final Task task;

    public TaskCard(Task task, int oneIndex) {
        super(FXML);

        this.task = task;

        id.setText(oneIndex + ". ");
        name.setText(task.getTitle());
        description.setText(task.getDescription());
        timestamp.setText(Optional.ofNullable(task.getTimestamp()).map(Object::toString).orElse(""));
        isCompleted.setText("");
        isCompleted.setSelected(false);
    }
}
