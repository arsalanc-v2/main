package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;
import seedu.address.model.analytics.Analytics;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.password.Password;
import seedu.address.model.person.Person;
import seedu.address.model.receptionist.Receptionist;

//@@author jjlee050

/**
 * Authenticate user and provide them access to ClinicIO based on the role.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Authenticate user to provide "
            + "user access to ClinicIO based on the roles.\n"
            + "Parameters: " + COMMAND_WORD
            + "[" + PREFIX_ROLE + "ROLE]"
            + "[" + PREFIX_NAME + "NAME]"
            + "[" + PREFIX_PASSWORD + "PASSWORD]\n"
            + "Example: login r/doctor n/Adam Bell pass/doctor1";

    public static final String MESSAGE_FAILURE = "Login failed. Please try again.";
    public static final String MESSAGE_NO_DOCTOR_FOUND = "No doctor found.";
    public static final String MESSAGE_NO_RECEPTIONIST_FOUND = "No receptionist found.";
    public static final String MESSAGE_SUCCESS = "Login successful.";

    private final Person toAuthenticate;

    /**
     * Creates an LoginCommand to add the specified {@code Person}.
     * This {@code Person} could possibly be a doctor or receptionist.
     */
    public LoginCommand(Person person) {
        requireNonNull(person);
        toAuthenticate = person;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, Analytics analytics) throws CommandException {
        requireNonNull(model);

        if (toAuthenticate instanceof Doctor) {
            Doctor authenticatedDoctor = (Doctor) toAuthenticate;
            if (!model.hasDoctor(authenticatedDoctor)) {
                throw new CommandException(MESSAGE_NO_DOCTOR_FOUND);
            }

            Doctor retrievedDoctor = model.getDoctor(authenticatedDoctor);

            boolean isCorrectPassword = Password.checkPassword(
                    authenticatedDoctor.getPassword().toString(),
                    retrievedDoctor.getPassword().toString());

            if (isCorrectPassword) {
                return new CommandResult(MESSAGE_SUCCESS);
            }
        } else if (toAuthenticate instanceof Receptionist) {
            Receptionist authenticatedReceptionist = (Receptionist) toAuthenticate;
            if (!model.hasReceptionist(authenticatedReceptionist)) {
                throw new CommandException(MESSAGE_NO_RECEPTIONIST_FOUND);
            }

            Receptionist retrievedReceptionist = model.getReceptionist(authenticatedReceptionist);

            boolean isCorrectPassword = Password.checkPassword(
                    authenticatedReceptionist.getPassword().toString(),
                    retrievedReceptionist.getPassword().toString());

            if (isCorrectPassword) {
                return new CommandResult(MESSAGE_SUCCESS);
            }
        }
        return new CommandResult(MESSAGE_FAILURE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && toAuthenticate.equals(((LoginCommand) other).toAuthenticate)); // state check
    }

}
