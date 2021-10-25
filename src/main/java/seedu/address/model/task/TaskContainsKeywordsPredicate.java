package seedu.address.model.task;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Task}'s {@code title} or {@code description} matches any of the keywords given.
 */
public class TaskContainsKeywordsPredicate implements Predicate<Task> {

    public static final ShowAllTasksPredicate SHOW_ALL_TASKS_PREDICATE =
            new ShowAllTasksPredicate(new ArrayList<String>());

    private static class ShowAllTasksPredicate extends TaskContainsKeywordsPredicate {
        public ShowAllTasksPredicate(List<String> keywords) {
            super(keywords);
        }
        @Override
        public boolean test(Task task) {
            return true;
        }
    }
    private final List<String> keywords;

    public TaskContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getKeywords() {
        return String.join(", ", keywords);
    }

    @Override
    public boolean test(Task task) {
        String taskDescription = task.getDescription().orElse("");
        return keywords.stream()
                .anyMatch(keyword -> (StringUtil.containsWordIgnoreCase(task.getTitle(), keyword)
                || StringUtil.containsWordIgnoreCase(taskDescription, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TaskContainsKeywordsPredicate) other).keywords)); // state check
    }
}
