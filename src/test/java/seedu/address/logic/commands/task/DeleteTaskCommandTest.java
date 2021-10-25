package seedu.address.logic.commands.task;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.task.filters.TaskFilters;

public class DeleteTaskCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalTaskList(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index targetIndex = Index.fromOneBased(1);
        Task taskToDelete = model.getFilteredTaskList().get(targetIndex.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(targetIndex);
        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_SUCCESS, taskToDelete);
        ModelManager expectedModel = ModelManager.from(model);
        expectedModel.deleteTask(taskToDelete);
        assertCommandSuccess(deleteTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(outOfBoundIndex);
        assertCommandFailure(deleteTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        model.addTaskFilter(TaskFilters.FILTER_TAG.apply(new Tag("homework")));
        Index targetIndex = Index.fromOneBased(1);
        Task taskToDelete = model.getFilteredTaskList().get(targetIndex.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(targetIndex);
        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_SUCCESS, taskToDelete);
        ModelManager expectedModel = ModelManager.from(model);
        expectedModel.deleteTask(taskToDelete);
        assertCommandSuccess(deleteTaskCommand, model, expectedMessage, expectedModel);

    }
    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        model.addTaskFilter(TaskFilters.FILTER_TAG.apply(new Tag("homework")));
        Index targetIndex = Index.fromOneBased(2);
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(targetIndex);
        assertCommandFailure(deleteTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

    }

}
