package seedu.address.logic.parser.task;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNDONE;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.task.ListTaskCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.filters.TaskFilter;
import seedu.address.model.task.filters.TaskFilters;

public class ListTaskCommandParser implements Parser<ListTaskCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditTaskCommand
     * and returns an EditTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DONE, PREFIX_UNDONE, PREFIX_TAG);

        Set<Tag> tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        List<TaskFilter> taskFilters = tags.stream().map(TaskFilters.FILTER_TAG).collect(Collectors.toList());

        if (argMultimap.getValue(PREFIX_DONE).isPresent()) {
            taskFilters.add(TaskFilters.FILTER_DONE);
        }

        if (argMultimap.getValue(PREFIX_UNDONE).isPresent()) {
            taskFilters.add(TaskFilters.FILTER_UNDONE);
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(ListTaskCommand.MESSAGE_USAGE);
        }

        return new ListTaskCommand(taskFilters);
    }
}
