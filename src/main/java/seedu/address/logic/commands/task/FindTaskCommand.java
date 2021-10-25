package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.TaskCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.TaskContainsKeywordsPredicate;
import seedu.address.model.task.filters.KeywordTaskFilter;
import seedu.address.model.task.filters.TaskFilters;


public class FindTaskCommand extends TaskCommand {

    public static final String COMMAND_WORD = "find";
    public static final String FULL_COMMAND_WORD = TaskCommand.COMMAND_WORD + " " + COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names or description "
            + "contain any of the specified keywords (case-insensitive) and displays them as a list with "
            + "index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + FULL_COMMAND_WORD + " CS2103 CS2106 PC3130";

    private final TaskContainsKeywordsPredicate predicate;

    public FindTaskCommand(TaskContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.getSelectedTaskFilters().stream().filter(filter -> filter instanceof KeywordTaskFilter)
                .findFirst().ifPresent(model::removeTaskFilter);

        if (!predicate.equals(TaskContainsKeywordsPredicate.SHOW_ALL_TASKS_PREDICATE)) {
            model.addTaskFilter(TaskFilters.FILTER_KEYWORDS.apply(predicate));
        }

        return new CommandResult(String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW,
                model.getFilteredTaskList().size()));

    }
}
