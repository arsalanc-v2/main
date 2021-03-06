package seedu.clinicio.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.clinicio.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.logic.commands.exceptions.CommandException;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.analytics.Analytics;

/**
 * Reverts the {@code model}'s ClinicIO to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history, Analytics analytics) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoClinicIo()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoClinicIo();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
