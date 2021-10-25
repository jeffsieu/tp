package seedu.address.logic.parser.task;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import seedu.address.logic.commands.task.FindTaskCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.TaskContainsKeywordsPredicate;

public class FindTaskCommandParser implements Parser<FindTaskCommand> {
    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param userInput the input entered by the user
     * @throws ParseException if {@code userInput} does not conform to the expected format
     */
    @Override
    public FindTaskCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        String trimmedArgs = userInput.trim();

        if (trimmedArgs.isEmpty()) {
            return new FindTaskCommand(TaskContainsKeywordsPredicate.SHOW_ALL_TASKS_PREDICATE);
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindTaskCommand(new TaskContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
