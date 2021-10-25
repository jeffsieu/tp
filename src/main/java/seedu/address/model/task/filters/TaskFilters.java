package seedu.address.model.task.filters;

import java.util.function.Function;

import seedu.address.model.tag.Tag;
import seedu.address.model.task.TaskContainsKeywordsPredicate;

public class TaskFilters {
    public static final TaskFilter FILTER_DONE = new DoneTaskFilter();
    public static final TaskFilter FILTER_UNDONE = new DoneTaskFilter().invert();
    public static final Function<Tag, TaskFilter> FILTER_TAG = TagTaskFilter::new;
    public static final Function<TaskContainsKeywordsPredicate, TaskFilter> FILTER_KEYWORDS = KeywordTaskFilter::new;
}
