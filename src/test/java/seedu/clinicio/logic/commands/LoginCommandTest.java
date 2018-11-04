package seedu.clinicio.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PASSWORD_ADAM;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PASSWORD_BEN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PASSWORD_CAT;
import static seedu.clinicio.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.clinicio.logic.commands.CommandTestUtil.assertCommandSuccess;

import static seedu.clinicio.model.staff.Role.DOCTOR;
import static seedu.clinicio.testutil.TypicalPersons.ADAM;
import static seedu.clinicio.testutil.TypicalPersons.BEN;
import static seedu.clinicio.testutil.TypicalPersons.CAT;
import static seedu.clinicio.testutil.TypicalPersons.getTypicalClinicIo;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.clinicio.logic.CommandHistory;

import seedu.clinicio.model.Model;
import seedu.clinicio.model.ModelManager;
import seedu.clinicio.model.UserPrefs;
import seedu.clinicio.model.analytics.Analytics;
import seedu.clinicio.model.staff.Password;
import seedu.clinicio.model.staff.Staff;

//@@author jjlee050
public class LoginCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalClinicIo(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalClinicIo(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private Analytics analytics = new Analytics();

    @Test
    public void execute_validCredentials_returnTrue() {
        LoginCommand command = new LoginCommand(
                new Staff(DOCTOR, ADAM.getName(), new Password("doctor1", false)));
        String expectedMessage = LoginCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel, analytics);

        // TODO: Receptionist
    }

    @Test
    public void execute_invalidCredentials_returnFalse() {
        String expectedMessage = LoginCommand.MESSAGE_FAILURE;

        // different name
        LoginCommand command = new LoginCommand(
                new Staff(DOCTOR, BEN.getName(), new Password(VALID_PASSWORD_ADAM, false)));
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel, analytics);

        // different password
        command = new LoginCommand(
                new Staff(DOCTOR, ADAM.getName(), new Password(VALID_PASSWORD_BEN, false)));
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel, analytics);

        // TODO: Receptionist
    }

    @Test
    public void execute_staffNotInClinicIO_throwsCommandException() {
        String expectedMessage = LoginCommand.MESSAGE_NO_RECORD_FOUND;

        // Staff not inside ClinicIO
        LoginCommand command = new LoginCommand(
                new Staff(DOCTOR, CAT.getName(), new Password(VALID_PASSWORD_CAT, false)));
        assertCommandFailure(command, model, commandHistory, analytics, expectedMessage);

        // TODO: Receptionist
    }

    @Test
    public void equals() {
        LoginCommand loginFirstCommand = new LoginCommand(
                new Staff(DOCTOR, ADAM.getName(), ADAM.getPassword()));
        LoginCommand loginSecondCommand = new LoginCommand(
                new Staff(DOCTOR, BEN.getName(), BEN.getPassword()));

        // same object -> returns true
        assertTrue(loginFirstCommand.equals(loginFirstCommand));

        // same values -> returns true
        LoginCommand loginFirstCommandCopy = new LoginCommand(
                new Staff(DOCTOR, ADAM.getName(), ADAM.getPassword()));
        assertTrue(loginFirstCommand.equals(loginFirstCommandCopy));

        // different types -> returns false
        assertFalse(loginFirstCommand.equals(1));

        // null -> returns false
        assertFalse(loginFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(loginFirstCommand.equals(loginSecondCommand));
    }
}
