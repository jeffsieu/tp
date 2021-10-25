package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.TaskCommand;
import seedu.address.logic.commands.task.AddTaskCommand;
import seedu.address.logic.commands.task.DeleteTaskCommand;
import seedu.address.logic.commands.task.DoneTaskCommand;
import seedu.address.logic.commands.task.EditTaskCommand;
import seedu.address.logic.commands.task.FindTaskCommand;
import seedu.address.logic.commands.task.ListTaskCommand;
import seedu.address.logic.commands.task.PurgeTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.task.AddTaskCommandParser;
import seedu.address.logic.parser.task.DeleteTaskCommandParser;
import seedu.address.logic.parser.task.DoneTaskCommandParser;
import seedu.address.logic.parser.task.EditTaskCommandParser;
import seedu.address.logic.parser.task.FindTaskCommandParser;
import seedu.address.logic.parser.task.ListTaskCommandParser;

/**
 * Parses all task-related commands (those starting with "task") and returns a TaskCommand.
 */
public class TaskCommandParser implements Parser<TaskCommand> {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    @Override
    public TaskCommand parse(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        case AddTaskCommand.COMMAND_WORD:
            return new AddTaskCommandParser().parse(arguments);

        case DeleteTaskCommand.COMMAND_WORD:
            return new DeleteTaskCommandParser().parse(arguments);

        case DoneTaskCommand.COMMAND_WORD:
            return new DoneTaskCommandParser().parse(arguments);

        case EditTaskCommand.COMMAND_WORD:
            return new EditTaskCommandParser().parse(arguments);

        case ListTaskCommand.COMMAND_WORD:
            return new ListTaskCommandParser().parse(arguments);

        case PurgeTaskCommand.COMMAND_WORD:
            return new PurgeTaskCommand();

        case FindTaskCommand.COMMAND_WORD:
            return new FindTaskCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
