package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("-n");
    public static final Prefix PREFIX_PHONE = new Prefix("-p");
    public static final Prefix PREFIX_EMAIL = new Prefix("-e");
    public static final Prefix PREFIX_ADDRESS = new Prefix("-a");
    public static final Prefix PREFIX_TAG = new Prefix("-t");

    /* Task-related prefix definitions */
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("-d");
    public static final Prefix PREFIX_TIMESTAMP = new Prefix("-ts");
    public static final Prefix PREFIX_TITLE = new Prefix("-ti");
    public static final Prefix PREFIX_DONE = new Prefix("--done");
    public static final Prefix PREFIX_UNDONE = new Prefix("--undone");
}
