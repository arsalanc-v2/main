package seedu.clinicio.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.clinicio.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.clinicio.model.Model.PREDICATE_SHOW_ALL_STAFFS;

import static seedu.clinicio.testutil.TypicalPersons.ADAM;
import static seedu.clinicio.testutil.TypicalPersons.ALICE;
import static seedu.clinicio.testutil.TypicalPersons.ALICE_AS_PATIENT;
import static seedu.clinicio.testutil.TypicalPersons.BEN;
import static seedu.clinicio.testutil.TypicalPersons.BENSON;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.appointment.Date;
import seedu.clinicio.model.appointment.Time;
import seedu.clinicio.model.person.NameContainsKeywordsPredicate;
import seedu.clinicio.testutil.ClinicIoBuilder;

public class ModelManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasPerson(null);
    }

    //@@author jjlee050
    @Test
    public void hasStaff_nullStaff_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasStaff(null);
    }

    //@@author gingivitiss
    @Test
    public void hasAppointment_nullAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasAppointment(null);
    }

    @Test
    public void hasPerson_personNotInClinicIo_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    //@@author jjlee050
    @Test
    public void hasStaff_staffNotInClinicIo_returnsFalse() {
        assertFalse(modelManager.hasStaff(ADAM));
    }

    //@@author gingivitiss
    @Test
    public void hasAppointment_appointmentNotInClinicIo_returnsFalse() {
        Date date = new Date(1, 1, 2018);
        Time time = new Time(5, 30);
        Appointment appt = new Appointment(date, time, ALICE_AS_PATIENT, 0);
        assertFalse(modelManager.hasAppointment(appt));
    }

    @Test
    public void hasPerson_personInClinicIo_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    //@@author jjlee050
    @Test
    public void hasStaff_staffInClinicIo_returnsTrue() {
        modelManager.addStaff(ADAM);
        assertTrue(modelManager.hasStaff(ADAM));
    }

    //@@author gingivitiss
    @Test
    public void hasAppointment_appointmentInClinicIo_returnsTrue() {
        Date date = new Date(1, 1, 2018);
        Time time = new Time(5, 30);
        Appointment appt = new Appointment(date, time, ALICE_AS_PATIENT, 0);
        modelManager.addAppointment(appt);
        assertTrue(modelManager.hasAppointment(appt));
    }

    @Test
    public void checkStaffCredentials_nullStaff_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.checkStaffCredentials(null);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }

    //@@author jjlee050
    @Test
    public void getFilteredStaffList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredStaffList().remove(0);
    }

    //@@author gingivitiss
    @Test
    public void getFilteredAppointmentList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredAppointmentList().remove(0);
    }

    @Test
    public void equals() {
        ClinicIo clinicIo = new ClinicIoBuilder().withPerson(ALICE).withPerson(BENSON)
                .withStaff(ADAM).withStaff(BEN).build();
        ClinicIo differentClinicIo = new ClinicIo();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(clinicIo, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(clinicIo, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different clinicIo -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentClinicIo, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(clinicIo, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        //@@author jjlee050
        modelManager.updateFilteredStaffList(PREDICATE_SHOW_ALL_STAFFS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setClinicIoFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(clinicIo, differentUserPrefs)));
    }
}
