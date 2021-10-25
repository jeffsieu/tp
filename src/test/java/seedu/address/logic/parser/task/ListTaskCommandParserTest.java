package seedu.address.logic.parser.task;

import static seedu.address.logic.commands.task.ListTaskCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNDONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.task.ListTaskCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.filters.TaskFilter;
import seedu.address.model.task.filters.TaskFilters;

public class ListTaskCommandParserTest {
    private final ListTaskCommandParser parser = new ListTaskCommandParser();
    @Test
    void parse_emptyArguments_noTaskFilters() {
        assertParseSuccess(parser, "", new ListTaskCommand(new ArrayList<>()));
    }

    @Test
    void parse_showDone_showDoneTasks() {
        assertParseSuccess(parser, " " + PREFIX_DONE, new ListTaskCommand(List.of(TaskFilters.FILTER_DONE)));
    }

    @Test
    void parse_showUndone_showUndoneTasks() {
        assertParseSuccess(parser, " " + PREFIX_UNDONE, new ListTaskCommand(List.of(TaskFilters.FILTER_DONE.invert())));
    }

    @Test
    void parse_showTag_showTaggedTasks() {
        String tagName = "important";
        Tag tag = new Tag(tagName);
        List<TaskFilter> taskFilters = List.of(TaskFilters.FILTER_TAG.apply(tag));
        assertParseSuccess(parser, " " + PREFIX_TAG + tagName,
                new ListTaskCommand(taskFilters));
    }

    @Test
    void parse_withPreamble_failure() {
        assertParseFailure(parser, " preamble", MESSAGE_USAGE);
    }
}
