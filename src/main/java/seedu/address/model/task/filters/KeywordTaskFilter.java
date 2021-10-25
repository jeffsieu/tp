package seedu.address.model.task.filters;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskContainsKeywordsPredicate;

public class KeywordTaskFilter extends TaskFilter {
    private static final int MAX_KEYWORDS_LENGTH = 35;
    private final String keywords;

    KeywordTaskFilter(TaskContainsKeywordsPredicate predicate) {
        this(predicate, predicate.getKeywords(), false);
    }

    private KeywordTaskFilter(
        Predicate<Task> predicate,
        String keywords,
        boolean isInverted
    ) {
        super(predicate, isInverted);
        this.keywords = keywords;
    }

    @Override
    public TaskFilter invert() {
        return new KeywordTaskFilter(predicate.negate(), this.keywords, !isInverted);
    }

    @Override
    public String toDisplayString() {
        return (this.isInverted ? "Without " : "Contains ")
                + StringUtil.limitString(keywords, "...", KeywordTaskFilter.MAX_KEYWORDS_LENGTH);
    }
}
