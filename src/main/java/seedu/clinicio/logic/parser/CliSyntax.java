package seedu.clinicio.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_TIME = new Prefix("tm/");
    public static final Prefix PREFIX_TYPE = new Prefix("tp/");

    /* Login Prefix definitions */
    public static final Prefix PREFIX_ROLE = new Prefix("r/");
    public static final Prefix PREFIX_PASSWORD = new Prefix("pass/");
}
