package seedu.address.model.task;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Task in the task list.
 * Guarantees: title is present and not null, field values are validated, immutable.
 */
public class Task {
    private final String title;

    // Optional description fields
    private final String description;
    private final Timestamp timestamp;
    private final Set<Tag> tags = new HashSet<>();
    private final boolean isDone;

    /**
     * Creates a task with a given title, and optionally a description, timestamp and a set of tags.
     * @param title The title of the task
     * @param description The optional description of the task
     * @param timestamp The optional timestamp of the task
     * @param tags The tags of the task
     */
    public Task(String title, String description, Timestamp timestamp, Set<Tag> tags) {
        this(title, description, timestamp, tags, false);
    }

    /**
     * Creates a task with a given title, and optionally a description, timestamp and a set of tags.
     * Additionally, it also takes in the completion status of the task.
     * @param title The title of the task
     * @param description The optional description of the task
     * @param timestamp The optional timestamp of the task
     * @param tags The tags of the task
     * @param isDone The completion status of the task
     */
    public Task(String title, String description, Timestamp timestamp, Set<Tag> tags, boolean isDone) {
        requireAllNonNull(title, tags);
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.tags.addAll(tags);
        this.isDone = isDone;
    }


    public String getTitle() {
        return title;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<Timestamp> getTimestamp() {
        return Optional.ofNullable(timestamp);
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public boolean getIsDone() {
        return isDone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        Task task = (Task) o;

        return isDone == task.isDone
                && Objects.equals(title, task.title)
                && Objects.equals(description, task.description)
                && Objects.equals(timestamp, task.timestamp)
                && Objects.equals(tags, task.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, timestamp, tags, isDone);
    }

    @Override
    public String toString() {
        return title;
    }
}
