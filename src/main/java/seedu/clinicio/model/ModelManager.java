package seedu.clinicio.model;

import static java.util.Objects.requireNonNull;
import static seedu.clinicio.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.clinicio.commons.core.ComponentManager;
import seedu.clinicio.commons.core.LogsCenter;

import seedu.clinicio.commons.events.model.ClinicIoChangedEvent;

import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.consultation.Consultation;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.patientqueue.MainQueue;
import seedu.clinicio.model.patientqueue.PreferenceQueue;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.staff.Staff;

/**
 * Represents the in-memory model of the ClinicIO data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedClinicIo versionedClinicIo;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Staff> filteredStaffs;
    private final FilteredList<Appointment> filteredAppointments;
    private final FilteredList<Consultation> filteredConsultations;
    private final MainQueue mainQueue;
    private final PreferenceQueue preferenceQueue;

    /**
     * Initializes a ModelManager with the given ClinicIO and userPrefs.
     */
    public ModelManager(ReadOnlyClinicIo clinicIo, UserPrefs userPrefs) {
        super();
        requireAllNonNull(clinicIo, userPrefs);

        logger.fine("Initializing with ClinicIO: " + clinicIo + " and user prefs " + userPrefs);

        versionedClinicIo = new VersionedClinicIo(clinicIo);
        //@@author jjlee050
        filteredPersons = new FilteredList<>(versionedClinicIo.getPersonList());
        filteredStaffs = new FilteredList<>(versionedClinicIo.getStaffList());
        filteredAppointments = new FilteredList<>(versionedClinicIo.getAppointmentList());
        filteredConsultations = new FilteredList<>(versionedClinicIo.getConsultationList());
        //@@author iamjackslayer
        mainQueue = new MainQueue();
        preferenceQueue = new PreferenceQueue();
    }

    public ModelManager() {
        this(new ClinicIo(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyClinicIo newData) {
        versionedClinicIo.resetData(newData);
        indicateClinicIoChanged();
    }

    @Override
    public ReadOnlyClinicIo getClinicIo() {
        return versionedClinicIo;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateClinicIoChanged() {
        raise(new ClinicIoChangedEvent(versionedClinicIo));
    }

    //========== Boolean check ===============================================================================

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedClinicIo.hasPerson(person);
    }

    //@@author jjlee050
    @Override
    public boolean hasStaff(Staff staff) {
        requireNonNull(staff);
        return versionedClinicIo.hasStaff(staff);
    }

    @Override
    public boolean hasPatientInMainQueue() {
        return mainQueue.hasPatient();
    }

    @Override
    public boolean hasPatientInPreferenceQueue() {
        return preferenceQueue.hasPatient();
    }

    @Override
    public boolean hasPatientInPatientQueue() {
        boolean hasPatient = hasPatientInPreferenceQueue() || hasPatientInMainQueue();
        return hasPatient;
    }

    //@@author gingivitiss
    @Override
    public boolean hasAppointment(Appointment appt) {
        requireNonNull(appt);
        return versionedClinicIo.hasAppointment(appt);
    }

    @Override
    public boolean hasAppointmentClash(Appointment appt) {
        requireNonNull(appt);
        return versionedClinicIo.hasAppointmentClash(appt);
    }

    //@@author arsalanc-v2
    @Override
    public boolean hasConsultation(Consultation consultation) {
        requireNonNull(consultation);
        return versionedClinicIo.hasConsultation(consultation);
    }
    //========== Delete ======================================================================================

    @Override
    public void deletePerson(Person target) {
        versionedClinicIo.removePerson(target);
        indicateClinicIoChanged();
    }

    //@@author gingivitiss
    @Override
    public void deleteAppointment(Appointment target) {
        versionedClinicIo.removeAppointment(target);
        indicateClinicIoChanged();
    }

    @Override
    public void cancelAppointment(Appointment target) {
        versionedClinicIo.cancelAppointment(target);
        indicateClinicIoChanged();
    }

    //@@author arsalanc-v2
    @Override
    public void deleteConsultation(Consultation target) {
        versionedClinicIo.removeConsultation(target);
    }
    //========== Add =========================================================================================

    @Override
    public void addPerson(Person person) {
        versionedClinicIo.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateClinicIoChanged();
    }

    //@@author jjlee050
    @Override
    public void addStaff(Staff staff) {
        versionedClinicIo.addStaff(staff);
        updateFilteredStaffList(PREDICATE_SHOW_ALL_STAFFS);
        indicateClinicIoChanged();
    }

    //@@author gingivitiss
    @Override
    public void addAppointment(Appointment appt) {
        versionedClinicIo.addAppointment(appt);
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        indicateClinicIoChanged();
    }

    //@author arsalanc-v2
    @Override
    public void addConsultation(Consultation consultation) {
        versionedClinicIo.add(consultation);
        updateFilteredConsultationList(PREDICATE_SHOW_ALL_CONSULTATIONS);
    }
    //========== Update ======================================================================================

    @Override
    public void enqueue(Person patient) {
        mainQueue.add(patient);
    }

    /**
     * Enqueues patient who is consulting a particular staff into the 'special' queue.
     * @param patient
     */
    @Override
    public void enqueueIntoPreferenceQueue(Person patient) {
        preferenceQueue.add(patient);
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        versionedClinicIo.updatePerson(target, editedPerson);
        indicateClinicIoChanged();
    }

    //@@author gingivitiss
    @Override
    public void updateAppointment(Appointment target, Appointment editedAppt) {
        requireAllNonNull(target, editedAppt);
        versionedClinicIo.updateAppointment(target, editedAppt);
        indicateClinicIoChanged();
    }

    //@@author arsalanc-v2
    @Override
    public void updateConsultation(Consultation target, Consultation editedConsultation) {
        requireAllNonNull(target, editedConsultation);
        versionedClinicIo.updateConsultation(target, editedConsultation);
    }
    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedClinicIo}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }
    //=========== Filtered Staff List Accessors =============================================================

    //@@author jjlee050
    /**
     * Returns an unmodifiable view of the list of {@code Staff} backed by the internal list of
     * {@code versionedClinicIo}
     */
    @Override
    public ObservableList<Staff> getFilteredStaffList() {
        return FXCollections.unmodifiableObservableList(filteredStaffs);
    }

    //@@author jjlee050
    @Override
    public void updateFilteredStaffList(Predicate<Staff> predicate) {
        requireNonNull(predicate);
        filteredStaffs.setPredicate(predicate);
    }

    //@@author jjlee050
    @Override
    public boolean checkStaffCredentials(Staff staff) {
        requireNonNull(staff);
        return versionedClinicIo.checkStaffCredentials(staff);
    }

    //=========== Undo/Redo =================================================================================
    //=========== Filtered Appointment List Accessors ========================================================

    //@@author gingivitiss
    /**
     * Returns an unmodifiable view of the list of {@code Appointment} backed by the internal list of
     * {@code versionedClinicIo}
     */
    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        return FXCollections.unmodifiableObservableList(filteredAppointments);
    }

    //@@author gingivitiss
    @Override
    public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
        requireNonNull(predicate);
        filteredAppointments.setPredicate(predicate);
    }

    //=========== Filtered Consultation List Accessors ========================================================
    //@@author arsalanc-v2
    /**
     * Returns an unmodifiable view of the list of {@code Consultation} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Consultation> getFilteredConsultationList() {
        return FXCollections.unmodifiableObservableList(filteredConsultations);
    }

    //@@author arsalanc-v2
    @Override
    public void updateFilteredConsultationList(Predicate<Consultation> predicate) {
        requireNonNull(predicate);
        filteredConsultations.setPredicate(predicate);
    }

    //=========== Export ==================================================================================
    //@@author arsalanc-v2

    // TO CHANGE TO USE versionedClinicIo.getPatientsList()

    /**
     * Exports all patients' personal information.
     * @return A String. The feedback message for the user.
     */
    @Override
    public String exportPatients() {
        ObservableList<Patient> patients = FXCollections.observableArrayList();
        return ExportPatientsData.exportPatients(patients);
    }

    /**
     * Exports all patients' appointments records.
     * @return A String. The feedback message for the user.
     */
    @Override
    public String exportPatientsAppointments() {
        ObservableList<Patient> patients = FXCollections.observableArrayList();
        return ExportPatientsData.exportAppointments(patients);
    }

    /**
     * Exports all patients' consultation records.
     * @return A String. The feedback message for the user.
     */
    @Override
    public String exportPatientsConsultations() {
        ObservableList<Patient> patients = FXCollections.observableArrayList();
        return ExportPatientsData.exportConsultations(patients);
    }
    //=========== Undo/Redo ==================================================================================

    @Override
    public boolean canUndoClinicIo() {
        return versionedClinicIo.canUndo();
    }

    @Override
    public boolean canRedoClinicIo() {
        return versionedClinicIo.canRedo();
    }

    @Override
    public void undoClinicIo() {
        versionedClinicIo.undo();
        indicateClinicIoChanged();
    }

    @Override
    public void redoClinicIo() {
        versionedClinicIo.redo();
        indicateClinicIoChanged();
    }

    @Override
    public void commitClinicIo() {
        versionedClinicIo.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        //@@author jjlee050
        return versionedClinicIo.equals(other.versionedClinicIo)
                && filteredPersons.equals(other.filteredPersons)
                && filteredStaffs.equals(other.filteredStaffs)
                && filteredAppointments.equals(other.filteredAppointments);
    }
}
