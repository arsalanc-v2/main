package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ClinicIoParser;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.Model;
import seedu.address.model.analytics.Analytics;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.person.Person;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final Analytics analytics;
    private final ClinicIoParser clinicIoParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        analytics = new Analytics();
        clinicIoParser = new ClinicIoParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = clinicIoParser.parseCommand(commandText);
            return command.execute(model, history, analytics);
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Doctor> getFilteredDoctorList() {
        return model.getFilteredDoctorList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
