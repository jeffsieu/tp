package seedu.address.logic;

import java.nio.file.Path;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.guiactions.GuiAction;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyTaskList;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;
import seedu.address.model.task.filters.TaskFilter;
import seedu.address.ui.exceptions.GuiException;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Executes the GUI action.
     * @param action The GUI action to execute
     */
    void executeGuiAction(GuiAction action) throws GuiException;

    /**
     * Adds a task filter to the list of selected filters.
     * @param taskFilter The task filter to add
     */
    void addTaskFilter(TaskFilter taskFilter);

    /**
     * Removes a task filter from the list of selected filters.
     * @param taskFilter The task filter to remove
     */
    void removeTaskFilter(TaskFilter taskFilter);

    /**
     * Sets the list of selected task filters.
     * @param taskFilters The list of task filters to filter by
     */
    void setTaskFilters(List<TaskFilter> taskFilters);

    /**
     * Returns a list of available task filters.
     * @return The list of available task filters
     */
    ObservableList<TaskFilter> getAvailableTaskFilters();

    /**
     * Returns the list of selected filters to filter tasks by.
     * @return The list of selected filters to filter tasks by
     */
    ObservableList<TaskFilter> getSelectedTaskFilters();

    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns the task list.
     */
    ReadOnlyTaskList getTaskList();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of tasks */
    ObservableList<Task> getFilteredTaskList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Executes an undo command.
     * @return The command result of the command being executed.
     */
    CommandResult undoCommand();
}
