package seedu.address.model.task;

public class Timestamp {
    private final String timestamp;

    public Timestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return this.timestamp;
    }
}
