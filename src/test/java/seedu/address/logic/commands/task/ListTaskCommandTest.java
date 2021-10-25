package seedu.address.logic.commands.task;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.task.ListTaskCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskList;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.Command;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.filters.TaskFilter;
import seedu.address.model.task.filters.TaskFilters;

public class ListTaskCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalTaskList(), new UserPrefs());

    @Test
    void execute_noFilter_showsEntireList() {
        Command command = new ListTaskCommand(new ArrayList<>());
        Model expectedModel = ModelManager.from(model);
        expectedModel.setTaskFilters(new ArrayList<>());

        assertCommandSuccess(command, model, MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    void execute_tagsFilter_showsTasksWithTag() {
        Tag tag = new Tag("important");
        List<TaskFilter> taskFilters = List.of(TaskFilters.FILTER_TAG.apply(tag));
        Command command = new ListTaskCommand(taskFilters);
        Model expectedModel = ModelManager.from(model);
        expectedModel.setTaskFilters(taskFilters);

        assertCommandSuccess(command, model, MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    void execute_multipleFilters_showsCorrectTasks() {
        Tag tag = new Tag("important");
        List<TaskFilter> taskFilters = List.of(
                TaskFilters.FILTER_TAG.apply(tag),
                TaskFilters.FILTER_DONE
        );
        Command command = new ListTaskCommand(taskFilters);
        Model expectedModel = ModelManager.from(model);
        expectedModel.setTaskFilters(taskFilters);

        assertCommandSuccess(command, model, MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    void execute_filterThenClear_showsAllTasks() {
        Tag tag = new Tag("important");
        List<TaskFilter> taskFilters = List.of(
                TaskFilters.FILTER_TAG.apply(tag),
                TaskFilters.FILTER_DONE
        );
        Command command = new ListTaskCommand(taskFilters);
        Model expectedModel = ModelManager.from(model);

        expectedModel.setTaskFilters(taskFilters);
        assertCommandSuccess(command, model, MESSAGE_SUCCESS, expectedModel);

        Command clearCommand = new ListTaskCommand(List.of());
        expectedModel.setTaskFilters(List.of());
        assertCommandSuccess(clearCommand, model, MESSAGE_SUCCESS, expectedModel);
    }
}
