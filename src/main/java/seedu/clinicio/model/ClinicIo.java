package seedu.clinicio.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;

import seedu.clinicio.model.analytics.Analytics;
import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.appointment.UniqueAppointmentList;
import seedu.clinicio.model.consultation.Consultation;
import seedu.clinicio.model.consultation.UniqueConsultationList;
import seedu.clinicio.model.doctor.Doctor;
import seedu.clinicio.model.doctor.UniqueDoctorList;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.person.UniquePersonList;

/**
 * Wraps all data at the ClinicIO level
 * Duplicates are not allowed (by .isSamePerson and .isSameDoctor comparison)
 */
public class ClinicIo implements ReadOnlyClinicIo {

    //@@author arsalanc-v2
    private final Analytics analytics;
    private final UniquePersonList persons;
    //@@author jjlee050
    private final UniqueDoctorList doctors;
    //@@author gingivitiss
    private final UniqueAppointmentList appointments;
    private final UniqueConsultationList consultations;


    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        //@@author arsalanc-v2
        analytics = new Analytics();
        persons = new UniquePersonList();
        //@@author jjlee050
        doctors = new UniqueDoctorList();
        //@@author gingivitiss
        appointments = new UniqueAppointmentList(analytics);
        consultations = new UniqueConsultationList();
    }

    public ClinicIo() {}

    /**
     * Creates an ClinicIO using the Persons in the {@code toBeCopied}
     */
    public ClinicIo(ReadOnlyClinicIo toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //========== List overwrite operations ===================================================================

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    //@@author jjlee050
    /**
     * Replaces the contents of the doctor list with {@code doctors}.
     * {@code doctors} must not contain duplicate doctors.
     */
    public void setDoctors(List<Doctor> doctors) {
        this.doctors.setDoctors(doctors);
    }

    /**
     * Resets the existing data of this {@code ClinicIo} with {@code newData}.
     */
    public void resetData(ReadOnlyClinicIo newData) {
        requireNonNull(newData);
        setPersons(newData.getPersonList());
        setDoctors(newData.getDoctorList());
    }

    //========== Person-level operations =====================================================================

    /**
     * Returns true if a person with the same identity as {@code person} exists in the ClinicIO.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    //@@author jjlee050
    /**
     * Returns true if a doctor with the same identity as {@code doctor} exists in the ClinicIO.
     */
    public boolean hasDoctor(Doctor doctor) {
        requireNonNull(doctor);
        return doctors.contains(doctor);
    }

    //@@author gingivitiss
    /**
     * Returns true if an appointment with the same identity as {@code appt} exists in the ClinicIO.
     */
    public boolean hasAppointment(Appointment appt) {
        requireNonNull(appt);
        return appointments.contains(appt);
    }

    /**
     * Returns true if an appointment has the same slot as {@code appt} in the ClinicIO.
     */
    public boolean hasAppointmentClash(Appointment appt) {
        requireNonNull(appt);
        return appointments.clashes(appt);
    }

    //@@author arsalanc-v2
    /**
     * Returns true if a consultation with the same identity as {@code consultation} exists in the address book.
     */
    public boolean hasConsultation(Consultation consultation) {
        requireNonNull(consultation);
        return consultations.contains(consultation);
    }

    /**
     * Adds a person to the ClinicIO.
     * The person must not already exist in the ClinicIO.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    //author jjlee050
    /**
     * Adds a doctor to the ClinicIO.
     * The doctor must not already exist in the ClinicIO.
     */
    public void addDoctor(Doctor d) {
        doctors.add(d);
    }

    //@@author gingivitiss
    /**
     * Adds an appointment to the ClinicIO.
     * The appointment must not already exist in the ClinicIO.
     */
    public void addAppointment(Appointment appt) {
        appointments.add(appt);
    }

    //@@author arsalanc-v2
    /**
     * Adds a consultation to the address book.
     * The consultation must not already exist in the address book.
     */
    public void add(Consultation consultation) {
        consultations.add(consultation);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the ClinicIO.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the ClinicIO.
     */
    public void updatePerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);
        persons.setPerson(target, editedPerson);
    }

    //@@author jjlee050
    /**
     * Replaces the given doctor {@code target} in the list with {@code editedDoctor}.
     * {@code target} must exist in the ClinicIO.
     * The doctor identity of {@code editedDoctor} must not be the same as another existing doctor in the ClinicIO.
     */
    public void updateDoctor(Doctor target, Doctor editedDoctor) {
        requireNonNull(editedDoctor);
        doctors.setDoctor(target, editedDoctor);
    }

    //@@author gingivitiss
    /**
     * Replaces the given appointment {@code target} in the list with {@code editedAppt}.
     * {@code target} must exist in the ClinicIO.
     * The appointment identity of {@code editedAppt} must not be the same as another existing appointment
     * in the ClinicIO.
     */
    public void updateAppointment(Appointment target, Appointment editedAppt) {
        requireNonNull(editedAppt);
        appointments.setAppointment(target, editedAppt);
    }

    //@@author arsalanc-v2
    /**
     * Replaces the given appointment {@code target} in the list with {@code editedConsultation}.
     * {@code target} must exist in the address book.
     * The consultation identity of {@code editedConsultation} must not be the same as another existing consultation
     * in the address book.
     */
    public void updateConsultation(Consultation target, Consultation editedConsultation) {
        requireNonNull(editedConsultation);
        consultations.setConsultation(target, editedConsultation);
    }

    /**
     * Removes {@code key} from this {@code ClinicIo}.
     * {@code key} must exist in the ClinicIO.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //@@author jjlee050
    /**
     * Removes {@code key} from this {@code ClinicIo}.
     * {@code key} must exist in the ClinicIO.
     */
    public void removeDoctor(Doctor key) {
        doctors.remove(key);
    }

    //@@author gingivitiss
    /**
     * Removes {@code key} from this {@code ClinicIo}. Not to be used to cancel appointments.
     * {@code key} must exist in the ClinicIO.
     */
    public void removeAppointment(Appointment key) {
        appointments.remove(key);
    }

    /**
     * Cancels {@code key} by updating appointment with cancel status.
     * Calls {@code updateAppointment} to replace {@code key} with cancelled one.
     * {@code key} must exist in the ClinicIO.
     */
    public void cancelAppointment(Appointment key) {
        requireNonNull(key);
        appointments.cancelAppointment(key);
    }

    //@@author arsalanc-v2
    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeConsultation(Consultation key) {
        consultations.remove(key);
    }
    //========== Util methods ================================================================================

    @Override
    public String toString() {
        //@@author jjlee050
        return persons.asUnmodifiableObservableList().size() + " persons & "
                + doctors.asUnmodifiableObservableList().size() + " doctors &"
                + appointments.asUnmodifiableObservableList().size() + "appointments ";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    //@@author jjlee050
    @Override
    public ObservableList<Doctor> getDoctorList() {
        return doctors.asUnmodifiableObservableList();
    }

    //@@author gingivitiss
    @Override
    public ObservableList<Appointment> getAppointmentList() {
        return appointments.asUnmodifiableObservableList();
    }

    //@@author arsalanc-v2
    public ObservableList<Consultation> getConsultationList() {
        return consultations.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        //@@author jjlee050
        return other == this // short circuit if same object
                || (other instanceof ClinicIo // instanceof handles nulls
                && persons.equals(((ClinicIo) other).persons)
                && doctors.equals(((ClinicIo) other).doctors)
                && appointments.equals(((ClinicIo) other).appointments));
    }

    @Override
    public int hashCode() {
        //@@author jjlee050
        return Objects.hash(persons.hashCode(), doctors.hashCode(), appointments.hashCode());
    }
}
