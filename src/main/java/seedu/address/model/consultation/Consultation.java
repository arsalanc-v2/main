package seedu.address.model.consultation;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Optional;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;
import seedu.address.model.doctor.Doctor;

//@@author arsalanc-v2

/**
 * Represents a medical consultation involving a patient and doctor.
 */
public class Consultation {

    // information fields
    private final Doctor doctor;
    private Optional<Appointment> appointment;
    private String description;

    // datetime fields
    private final Date date;
    private final Time arrivalTime;
    private Time consultationTime;
    private Time endTime;

    // fields to be changed
    private String prescription;
    private final String patient;

    /**
     * Initializes a {@code Consultation} object with an {@code Appointment}.
     * This consultation is the result of a requisite appointment.
     * All parameters are required.
     * @param doctor The doctor assigned.
     * @param patient The patient to be examined.
     * @param date The date.
     * @param arrivalTime The arrival time of the patient at the clinic.
     * @param appointment The appointment tied to this {@code Consultation} .
     */
    public Consultation(Doctor doctor, String patient, Date date, Time arrivalTime, Appointment appointment) {
        requireAllNonNull(doctor, patient, date, arrivalTime, appointment);
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.arrivalTime = arrivalTime;
        this.appointment = Optional.of(appointment);
    }

    /**
     * Initializes a {@code Consultation} object without an {@code Appointment}.
     * This consultation is a walk-in.
     * All parameters are required.
     * @param doctor The doctor assigned.
     * @param patient The patient to be examined.
     * @param date The date.
     * @param arrivalTime The arrival time of the patient at the clinic.
     */
    public Consultation(Doctor doctor, String patient, Date date, Time arrivalTime) {
        requireAllNonNull(doctor, patient, date, arrivalTime);
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.arrivalTime = arrivalTime;
        this.appointment = Optional.empty();
    }

    public void updateConsultationTime(Time time) {
        consultationTime = time;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updatePrescription(String prescription) {
        this.prescription = prescription;
    }

    public void updateEndTime(Time time) {
        endTime = time;
    }

    /**
     * Denotes a weaker notion of equality between two {@code Consultation} objects.
     * @return {@code true} if {@code patient}, {@code doctor}, {@code consultationDate} and {@code consultationTime}
     * fields are
     * the same.
     * {@code false} otherwise
     */
    public boolean isSameConsultation(Consultation toCheck) {
        return toCheck.getPatient().equals(getPatient())
            && toCheck.getDoctor().equals(getDoctor())
            && toCheck.getConsultationDate().equals(getConsultationDate())
            && toCheck.getConsultationTime().equals(getConsultationTime());
    }

    /**
     * Denotes a stronger notion of equality between two {@code Consultation} objects.
     * @return {@code true} if all {@code Consultation} fields are the same. {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Consultation)) {
            return false;
        }

        Consultation otherConsultation = (Consultation) other;
        return otherConsultation.getConsultationDate().equals(getConsultationDate())
            && otherConsultation.getConsultationTime().equals(getConsultationTime())
            && otherConsultation.getPatient().equals(getPatient())
            && otherConsultation.getAppointment().equals(getAppointment())
            && otherConsultation.getDoctor().equals(getDoctor())
            && otherConsultation.getArrivalTime().equals(getArrivalTime())
            && otherConsultation.getEndTime().equals(getEndTime())
            && otherConsultation.getDescription().equals(getDescription())
            && otherConsultation.getPrescription().equals(getPrescription());
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getPatient() {
        return patient;
    }

    public Date getConsultationDate() {
        return date;
    }

    public Time getConsultationTime() {
        return consultationTime;
    }

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public String getDescription() {
        return description;
    }

    public String getPrescription() {
        return prescription;
    }

    public Optional<Appointment> getAppointment() {
        return appointment;
    }
}