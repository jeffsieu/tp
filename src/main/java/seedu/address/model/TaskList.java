package seedu.address.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.Task;

public class TaskList implements ReadOnlyTaskList {
    private final ObservableList<Task> tasks = FXCollections.observableArrayList();

    public TaskList() {

    }

    public TaskList(ReadOnlyTaskList toBeCopied) {
        tasks.addAll(toBeCopied.getTasks());
    }

    @Override
    public ObservableList<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }
}
