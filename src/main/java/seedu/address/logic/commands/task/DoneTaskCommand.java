package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.TaskCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Completes an existing task in the task list.
 */
public class DoneTaskCommand extends TaskCommand {
    public static final String COMMAND_WORD = "done";
    public static final String FULL_COMMAND_WORD = TaskCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Task completed: %1$s";
    public static final String MESSAGE_ALREADY_DONE = "Task has already been completed: %1$s";
    public static final String MESSAGE_USAGE =
            FULL_COMMAND_WORD + ": Completes an existing task in the task list.\n"
                    + "Parameters: INDEX (must be a positive integer) " + "Example: " + FULL_COMMAND_WORD + " 1";

    private final Index index;
    private Task completedTask;

    public DoneTaskCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> taskList = model.getFilteredTaskList();

        if (index.getZeroBased() >= taskList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        super.canExecute();

        Task task = taskList.get(index.getZeroBased());
        Task completedTask = new Task(task.getTitle(),
                task.getDescription().orElse(null),
                task.getTimestamp().orElse(null),
                task.getTags(),
                !task.getIsDone());
        this.completedTask = completedTask;
        model.setTask(task,
                completedTask);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                completedTask));
    }

    @Override
    public boolean equals(Object o) {
        return this == o || (o instanceof DoneTaskCommand && index.equals(((DoneTaskCommand) o).index));
    }

    @Override
    public CommandResult undo(Model model) throws CommandException {
        super.canUndo();
        this.execute(model);
        this.canExecute = true;
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                this.completedTask));
    }
}
