package seedu.clinicio.logic.commands;

import seedu.clinicio.commons.core.EventsCenter;
import seedu.clinicio.commons.events.ui.ExitAppRequestEvent;
import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.analytics.Analytics;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting ClinicIO as requested ...";

    @Override
    public CommandResult execute(Model model, CommandHistory history, Analytics analytics) {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}