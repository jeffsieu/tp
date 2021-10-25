package seedu.address.model.task.filters;

import seedu.address.model.tag.Tag;

public class TagTaskFilter extends TaskFilter {
    private final Tag tag;

    TagTaskFilter(Tag tag) {
        this(tag, false);
    }

    private TagTaskFilter(Tag tag, boolean isInverted) {
        super(task -> task.getTags().contains(tag), isInverted);
        this.tag = tag;
    }

    @Override
    public String toDisplayString() {
        return (isInverted ? "Not tagged " : "Tagged ") + tag;
    }

    @Override
    public TagTaskFilter invert() {
        return new TagTaskFilter(tag, !isInverted);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        return (o instanceof TagTaskFilter)
                && isInverted == ((TagTaskFilter) o).isInverted
                && tag.equals(((TagTaskFilter) o).tag);
    }

    @Override
    public int hashCode() {
        return tag.hashCode();
    }
}
