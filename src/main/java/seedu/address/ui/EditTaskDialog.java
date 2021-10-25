package seedu.address.ui;

import static seedu.address.commons.util.AppUtil.getImage;
import static seedu.address.ui.UiManager.ICON_APPLICATION;

import java.util.Optional;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.task.Timestamp;

public class EditTaskDialog extends UiPart<Region> {
    private static final String FXML = "EditTaskDialog.fxml";

    @FXML
    private TextField title;

    @FXML
    private TextArea description;

    @FXML
    private TextField timestamp;

    @FXML
    private CheckBox isDone;

    @FXML
    private TextField tagInput;

    @FXML
    private FlowPane tagPane;

    @FXML
    private Label tagErrorLabel;

    @FXML
    private Label titleErrorLabel;

    private final ObservableSet<Tag> tags;
    private final Dialog<Task> dialog;

    /**
     * Creates a dialog to edit the given task. If the given task is null, creates a new task.
     * @param t The task to edit
     */
    public EditTaskDialog(Task t) {
        super(FXML);

        // Initialize final fields
        Optional<Task> task = Optional.ofNullable(t);
        tags = task.map(Task::getTags)
                .map(tags -> tags.toArray(new Tag[]{}))
                .map(FXCollections::observableSet)
                .orElse(FXCollections.observableSet());
        dialog = new Dialog<>();

        // Initialize fields from task
        initializeFields(t);
        initializeDialog(t != null);

        //Set the application icon.
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(getImage(ICON_APPLICATION));
    }

    private void initializeFields(Task t) {
        Optional<Task> task = Optional.ofNullable(t);
        title.setText(task.map(Task::getTitle).orElse(""));
        description.setText(task.flatMap(Task::getDescription).orElse(""));
        timestamp.setText(task.flatMap(Task::getTimestamp).map(Timestamp::toString).orElse(""));
        isDone.setSelected(task.map(Task::getIsDone).orElse(false));
        tagErrorLabel.setText("");
        titleErrorLabel.setText("Title cannot be empty");
        titleErrorLabel.getStyleClass().add("error");
        titleErrorLabel.setVisible(false);
        titleErrorLabel.setManaged(false);

        description.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.TAB) {
                event.consume();

                if (event.isShiftDown()) {
                    // Focus on previous
                    timestamp.requestFocus();
                } else {
                    // Focus on next
                    tagInput.requestFocus();
                }
            }
        });

        // Add chip for each tag
        tags.stream()
                .map(tag -> new DeletableChip(tag.tagName, () -> tags.remove(tag)).getRoot())
                .forEach(tagPane.getChildren()::add);

        // Add listener for enter input
        tagInput.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            tagErrorLabel.setText("");
            if (event.getCode() == KeyCode.ENTER) {
                event.consume();
                try {
                    String text = tagInput.getText();
                    tagInput.clear();
                    Tag tag = new Tag(text);
                    tags.add(tag);
                } catch (IllegalArgumentException e) {
                    tagErrorLabel.setText(e.getMessage());
                }
            }
        });

        // Update FlowPane when tag set changes
        tags.addListener((Observable observable) -> {
            tagPane.getChildren().clear();
            tags.stream()
                    .map(tag -> new DeletableChip(tag.tagName, () -> tags.remove(tag)).getRoot())
                    .forEach(tagPane.getChildren()::add);
        });
    }

    private void initializeDialog(boolean isEditingTask) {
        dialog.setTitle(isEditingTask ? "Edit Task" : "Add Task");
        dialog.getDialogPane().setPrefWidth(600);
        dialog.setResizable(true);
        dialog.getDialogPane().setContent(getRoot());
        dialog.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        dialog.getDialogPane().getStyleClass().add("stack-pane");
        dialog.setResultConverter((buttonType) -> {
            if (buttonType.getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) {
                return getTask();
            } else {
                return null;
            }
        });
        ButtonType buttonTypeOk = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, buttonTypeCancel);
        dialog.getDialogPane().lookupButton(buttonTypeOk).addEventFilter(ActionEvent.ACTION, event -> {
            if (!areFieldsValid()) {
                event.consume();
            }
        });
    }

    private boolean areFieldsValid() {
        if (title.getText().isBlank()) {
            titleErrorLabel.setVisible(true);
            titleErrorLabel.setManaged(true);
            return false;
        } else {
            titleErrorLabel.setVisible(false);
            titleErrorLabel.setManaged(false);
            return true;
        }
    }

    public Dialog<Task> getDialog() {
        return dialog;
    }

    private Task getTask() {
        return new Task(
                title.getText().trim(),
                Optional.of(description.getText().trim()).filter(text -> !text.isBlank()).orElse(null),
                Optional.of(timestamp.getText().trim()).filter(text -> !text.isBlank()).map(Timestamp::new).orElse(null),
                tags,
                isDone.isSelected()
        );
    }
}
