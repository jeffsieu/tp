package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.guiactions.GuiAction;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.task.filters.TaskFilters;
import seedu.address.model.task.filters.TaskFilters.TaskFilter;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final TaskList taskList;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Task> filteredTasks;
    private final ObservableList<TaskFilter> availableTaskFilters;
    private final ObservableList<TaskFilter> selectedTaskFilters;

    /**
     * Initializes a ModelManager with the given addressBook, taskList and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyTaskList taskList, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, taskList, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.taskList = new TaskList(taskList);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredTasks = new FilteredList<>(this.taskList.getTasks());
        availableTaskFilters = FXCollections.observableArrayList();
        selectedTaskFilters = FXCollections.observableArrayList();
    }

    public ModelManager() {
        this(new AddressBook(), new TaskList(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void deleteTask(Task deletedTask) {
        taskList.removeTask(deletedTask);
    }

    //=========== TaskMaster2103 ============================================================================

    @Override
    public ReadOnlyTaskList getTaskList() {
        return taskList;
    }

    @Override
    public void addTask(Task task) {
        taskList.addTask(task);
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return filteredTasks;
    }

    @Override
    public ObservableList<TaskFilter> getAvailableTaskFilters() {
        Set<Tag> allTaskTags = taskList.getTasks().stream()
                .map(Task::getTags)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        List<TaskFilter> tagFilters = allTaskTags.stream().map(TaskFilters.FILTER_TAG).collect(Collectors.toList());

        availableTaskFilters.clear();
        availableTaskFilters.add(TaskFilters.FILTER_DONE);
        availableTaskFilters.add(TaskFilters.FILTER_DONE.invert());
        availableTaskFilters.addAll(tagFilters);
        return availableTaskFilters;
    }

    @Override
    public ObservableList<TaskFilter> getSelectedTaskFilters() {
        return selectedTaskFilters;
    }

    @Override
    public void addTaskFilter(TaskFilter taskFilter) {
        selectedTaskFilters.add(taskFilter);
        recalculateFilteredTaskList();
    }

    @Override
    public void removeTaskFilter(TaskFilter taskFilter) {
        selectedTaskFilters.remove(taskFilter);
        recalculateFilteredTaskList();
    }

    @Override
    public void setTaskFilters(List<TaskFilter> taskFilters) {
        selectedTaskFilters.clear();
        selectedTaskFilters.addAll(taskFilters);
        recalculateFilteredTaskList();
    }

    private void recalculateFilteredTaskList() {
        Predicate<Task> identity = task -> true;
        Predicate<Task> effectivePredicate = selectedTaskFilters.stream().map(TaskFilter::getPredicate)
                .reduce(identity, Predicate::and);
        updateFilteredTaskList(effectivePredicate);
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }

    @Override
    public Task getTaskAtIndex(int index) throws IndexOutOfBoundsException {
        return filteredTasks.get(index);
    }

    @Override
    public void executeGuiAction(GuiAction action) {
        action.executeWith(addressBook, taskList);
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);
        taskList.setTask(target, editedTask);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && taskList.equals(other.taskList)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

}
