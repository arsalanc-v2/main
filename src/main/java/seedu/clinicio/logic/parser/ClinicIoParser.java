package seedu.clinicio.logic.parser;

import static seedu.clinicio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinicio.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.clinicio.logic.commands.AddApptCommand;
import seedu.clinicio.logic.commands.AddCommand;
import seedu.clinicio.logic.commands.AveragesCommand;
import seedu.clinicio.logic.commands.ClearCommand;
import seedu.clinicio.logic.commands.Command;
import seedu.clinicio.logic.commands.DeleteCommand;
import seedu.clinicio.logic.commands.EditCommand;
import seedu.clinicio.logic.commands.ExitCommand;
import seedu.clinicio.logic.commands.ExportPatientsAppointmentsCommand;
import seedu.clinicio.logic.commands.ExportPatientsCommand;
import seedu.clinicio.logic.commands.ExportPatientsConsultationsCommand;
import seedu.clinicio.logic.commands.FindCommand;
import seedu.clinicio.logic.commands.HelpCommand;
import seedu.clinicio.logic.commands.HistoryCommand;
import seedu.clinicio.logic.commands.ListCommand;
import seedu.clinicio.logic.commands.LoginCommand;
import seedu.clinicio.logic.commands.RedoCommand;
import seedu.clinicio.logic.commands.SelectCommand;
import seedu.clinicio.logic.commands.TotalsCommand;
import seedu.clinicio.logic.commands.UndoCommand;
import seedu.clinicio.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class ClinicIoParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddApptCommand.COMMAND_WORD:
            return new AddApptCommandParser().parse(arguments);

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ExportPatientsCommand.COMMAND_WORD:
            return new ExportPatientsCommand();

        case ExportPatientsAppointmentsCommand.COMMAND_WORD:
            return new ExportPatientsAppointmentsCommand();

        case ExportPatientsConsultationsCommand.COMMAND_WORD:
            return new ExportPatientsConsultationsCommand();

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case AveragesCommand.COMMAND_WORD:
            return new AveragesCommand();

        case TotalsCommand.COMMAND_WORD:
            return new TotalsCommand();

        case LoginCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
