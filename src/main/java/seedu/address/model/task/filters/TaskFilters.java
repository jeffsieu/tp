package seedu.address.model.task.filters;

import java.util.function.Function;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;

public class TaskFilters {
    public static final TaskFilter FILTER_DONE = new TaskFilter("Done", Task::getDone,
            isInverted -> isInverted ? "Undone" : "Done");
    public static final Function<Tag, TaskFilter> FILTER_TAG = tag -> new TaskFilter("Tag",
            task -> task.getTags().contains(tag), isInverted -> (isInverted ? "Not Tagged " : "Tagged ") + tag);


    public static class TaskFilter {
        private String name;
        private Predicate<Task> predicate;
        private boolean isInverted;
        private Function<Boolean, String> toString;

        private TaskFilter(String name, Predicate<Task> predicate, Function<Boolean, String> toString) {
            this(name, predicate, toString, false);
        }

        private TaskFilter(String name, Predicate<Task> predicate, Function<Boolean, String> toString,
                           boolean isInverted) {
            this.name = name;
            this.predicate = predicate;
            this.toString = toString;
            this.isInverted = isInverted;
        }

        public Predicate<Task> getPredicate() {
            return this.predicate;
        }

        public TaskFilter invert() {
            return new TaskFilter(this.name, predicate.negate(), this.toString, !isInverted);
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return toString.apply(this.isInverted);
        }
    }
}
