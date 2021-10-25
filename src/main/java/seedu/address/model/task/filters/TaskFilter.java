package seedu.address.model.task.filters;

import java.util.function.Function;
import java.util.function.Predicate;

import seedu.address.model.task.Task;

public abstract class TaskFilter {
    protected Predicate<Task> predicate;
    protected boolean isInverted;
    protected Function<Boolean, String> toString;

    TaskFilter(Predicate<Task> predicate, boolean isInverted) {
        this.predicate = predicate;
        this.isInverted = isInverted;
    }

    /**
     * Returns the predicate used to filter tasks.
     *
     * @return The predicate used to filter tasks
     */
    public Predicate<Task> getPredicate() {
        return this.predicate;
    }

    /**
     * Returns a new task filter that accepts tasks not accepted by this task filter, and vice-versa.
     *
     * @return A new task filter with an inverted predicate
     */
    public abstract TaskFilter invert();

    /**
     * Returns the string used to describe the filter in the UI.
     * @return the string used to describe the filter in the UI
     */
    public abstract String toDisplayString();
}
