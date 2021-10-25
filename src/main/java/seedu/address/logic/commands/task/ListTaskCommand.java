package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNDONE;

import java.util.List;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.TaskCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.filters.TaskFilter;

/**
 * Completes an existing task in the task list.
 */
public class ListTaskCommand extends TaskCommand {
    public static final String COMMAND_WORD = "list";
    public static final String FULL_COMMAND_WORD = TaskCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Task list updated";
    public static final String MESSAGE_USAGE = FULL_COMMAND_WORD
            + ": Lists tasks matching the given search conditions.\n"
            + "Parameters: "
            + "[" + PREFIX_DONE + "] "
            + "[" + PREFIX_UNDONE + "] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + FULL_COMMAND_WORD + " " + PREFIX_UNDONE + " " + PREFIX_TAG + "important";

    private final List<TaskFilter> taskFilters;

    private List<TaskFilter> previousFilters;

    public ListTaskCommand(List<TaskFilter> taskFilters) {
        this.taskFilters = taskFilters;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        super.canExecute();
        this.previousFilters = model.getOldTaskFilters();
        model.setTaskFilters(taskFilters);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object o) {
        return this == o
                || (o instanceof ListTaskCommand
                && taskFilters.equals(((ListTaskCommand) o).taskFilters));
    }

    @Override
    public int hashCode() {
        return taskFilters.hashCode();
    }

    @Override
    public CommandResult undo(Model model) throws CommandException {
        super.canUndo();
        model.setTaskFilters(this.previousFilters);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
