package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.TaskCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;
import seedu.address.model.task.filters.TaskFilter;

/**
 * Purges all tasks currently in the task list
 */
public class PurgeTaskCommand extends TaskCommand {
    public static final String COMMAND_WORD = "purge";
    public static final String FULL_COMMAND_WORD = TaskCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Tasks purged!";
    public static final String MESSAGE_NO_TASK_TO_PURGE = "There are no tasks to purge.";
    public static final String MESSAGE_USAGE = FULL_COMMAND_WORD
            + ": Purges all tasks in the displayed task list.\n"
            + "Example: " + FULL_COMMAND_WORD;

    private TreeMap<Integer, Task> deletedTasks;
    private ArrayList<TaskFilter> deletedFilters;

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> taskList = model.getFilteredTaskList();

        if (taskList.size() == 0) {
            throw new CommandException(MESSAGE_NO_TASK_TO_PURGE);
        }

        // Create a copy of the list for undo with their respective indexes
        deletedTasks = new TreeMap<>();
        for (Task deletedTask : taskList) {
            int index = model.indexOf(deletedTask);
            deletedTasks.put(index, deletedTask);
        }

        deletedFilters = new ArrayList<>();
        deletedFilters.addAll(model.getSelectedTaskFilters());

        super.canExecute();

        model.deleteAllInFilteredTaskList();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        return other instanceof PurgeTaskCommand;
    }

    @Override
    public CommandResult undo(Model model) throws CommandException {
        super.canUndo();

        model.setTaskFilters(deletedFilters);
        for (int index : deletedTasks.keySet()) {
            Task task = deletedTasks.get(index);
            model.insertTask(task, index);
        }

        this.canExecute = true;
        return new CommandResult(MESSAGE_SUCCESS);

    }
}
