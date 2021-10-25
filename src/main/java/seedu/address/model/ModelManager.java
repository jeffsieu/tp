package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
import seedu.address.model.task.filters.TaskFilter;
import seedu.address.model.task.filters.TaskFilters;
import seedu.address.storage.CommandHistory;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final TaskList taskList;
    private final UserPrefs userPrefs;
    private final CommandHistory commandHistory;
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
        this.commandHistory = new CommandHistory(15);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredTasks = new FilteredList<>(this.taskList.getTasks());
        availableTaskFilters = FXCollections.observableArrayList();
        selectedTaskFilters = FXCollections.observableArrayList();

    }

    public ModelManager() {
        this(new AddressBook(), new TaskList(), new UserPrefs());
    }

    /**
     * Returns a new ModelManager initialize from the given {@link Model}.
     * @param model The model to initialize the {@link ModelManager} from
     * @return The initialized ModelManager
     */
    public static ModelManager from(Model model) {
        return new ModelManager(
                new AddressBook(model.getAddressBook()), new TaskList(model.getTaskList()) , new UserPrefs());
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
    public void updateFilteredPersonList(Predicate<? super Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public Predicate<? super Person> getFilteredPersonPredicate() {
        return Optional.ofNullable(filteredPersons.getPredicate()).orElse(unused -> true);
    }

    //=========== TaskMaster2103 ============================================================================

    @Override
    public ReadOnlyTaskList getTaskList() {
        return taskList;
    }

    @Override
    public void addTask(Task task) {
        taskList.addTask(task);
        recomputeAvailableTaskFilters();
    }

    @Override
    public void insertTask(Task task, int index) {
        taskList.addTaskAtIndex(task, index);
        recomputeAvailableTaskFilters();
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return filteredTasks;
    }

    private void recomputeAvailableTaskFilters() {
        Set<Tag> allTaskTags = taskList.getTasks().stream()
                .map(Task::getTags)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        List<TaskFilter> tagFilters = allTaskTags.stream()
                .map(TaskFilters.FILTER_TAG)
                .sorted(Comparator.comparing(TaskFilter::toString))
                .collect(Collectors.toList());

        availableTaskFilters.clear();
        availableTaskFilters.add(TaskFilters.FILTER_DONE);
        availableTaskFilters.add(TaskFilters.FILTER_UNDONE);
        availableTaskFilters.addAll(tagFilters);
    }

    @Override
    public ObservableList<TaskFilter> getAvailableTaskFilters() {
        recomputeAvailableTaskFilters();
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

    @Override
    public List<TaskFilter> getOldTaskFilters() {
        return this.selectedTaskFilters;
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
    public Predicate<? super Task> getFilteredTaskPredicate() {
        return Optional.ofNullable(filteredTasks.getPredicate()).orElse(unused -> true);
    }

    @Override
    public void executeGuiAction(GuiAction action) {
        action.executeWith(addressBook, taskList);
    }


    @Override
    public void deleteTask(Task deletedTask) {
        taskList.removeTask(deletedTask);
        updateTaskFilters();
    }

    /**
     * Deletes a list of given tasks.
     * This method does not {@code updateTaskFilters} so as to show distinct changes to the task list, if any.
     * Instead, it removes all currently selected task filters from {@code availableTaskFilters}
     * @param tasksToDelete List of tasks in the filtered list to delete.
     */
    @Override
    public void deleteAllInFilteredTaskList(Task... tasksToDelete) {
        for (Task task : tasksToDelete) {
            taskList.removeTask(task);
        }

        availableTaskFilters.removeAll(selectedTaskFilters);
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);
        taskList.setTask(target, editedTask);
        updateTaskFilters();

    }

    /**
     * Update filters for the task list after any changes to it (CLI or GUI)
     */
    private void updateTaskFilters() {
        recomputeAvailableTaskFilters();

        // If removing or editing the task removed a tag, remove all filters associated with that tag
        if (selectedTaskFilters.removeIf(filter -> !availableTaskFilters.contains(filter))) {
            recalculateFilteredTaskList();
        }
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


    //=========== Undo Stack ============================================================================

    public CommandHistory getCommandHistory() {
        return commandHistory;
    }
}
