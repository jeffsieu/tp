package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.task.EditTaskCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.task.Timestamp;

public class EditTaskDescriptorBuilder {

    private EditTaskCommand.EditTaskDescriptor descriptor;

    public EditTaskDescriptorBuilder() {
        descriptor = new EditTaskCommand.EditTaskDescriptor();
    }

    public EditTaskDescriptorBuilder(EditTaskCommand.EditTaskDescriptor descriptor) {
        this.descriptor = new EditTaskCommand.EditTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTaskDescriptor} with fields containing {@code task}'s details
     */
    public EditTaskDescriptorBuilder(Task task) {
        descriptor = new EditTaskCommand.EditTaskDescriptor();
        descriptor.setTitle(task.getTitle());
        descriptor.setDescription(task.getDescription().orElse(null));
        descriptor.setTimestamp(task.getTimestamp().orElse(null));
        descriptor.setTags(task.getTags());
    }

    /**
     * Sets the {@code title} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withTitle(String title) {
        descriptor.setTitle(title);
        return this;
    }

    /**
     * Sets the {@code description} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(description);
        return this;
    }

    /**
     * Sets the {@code Timestamp} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withTimestamp(Timestamp timestamp) {
        descriptor.setTimestamp(timestamp);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditTaskDescriptor}
     * that we are building.
     */
    public EditTaskDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditTaskCommand.EditTaskDescriptor build() {
        return descriptor;
    }
}
