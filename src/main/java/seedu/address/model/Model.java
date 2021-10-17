package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.guiactions.GuiAction;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;
import seedu.address.model.task.filters.TaskFilters.TaskFilter;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);


    /**
     * Deletes the given task. The task must exist in the task list.
     * @param deletedTask The task to delete
     */
    void deleteTask(Task deletedTask);

    /**
     * Adds the given task to the task list.
     * @param task The task to be added.
     */
    void addTask(Task task);

    /** Returns an unmodifiable view of the task list */
    ObservableList<Task> getFilteredTaskList();

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

    void addTaskFilter(TaskFilter taskFilter);
    void removeTaskFilter(TaskFilter taskFilter);
    void setTaskFilters(List<TaskFilter> taskFilter);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @param predicate The new predicate to filter by
     */
    void updateFilteredTaskList(Predicate<Task> predicate);

    ReadOnlyTaskList getTaskList();

    /**
     * Replaces the given task {@code target} with {@code editedTask}.
     * {@code target} must exist in the task list.
     * setTask uses targetIndex rather than target Person; This is because tasks may not be unique, unlike persons
     */
    void setTask(Task target, Task editedTask);

    /**
     * Retrieve task at the specified index.
     * @param index Index of task displayed in the GUI.
     * @return Task corresponding to the index provided.
     */
    Task getTaskAtIndex(int index) throws IndexOutOfBoundsException;

    /**
     * Executes the given GUI action with the model context.
     * @param action The GUI action to execute
     */
    void executeGuiAction(GuiAction action);
}
