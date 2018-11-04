package seedu.clinicio.model.analytics;

import static java.lang.Math.toIntExact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.clinicio.model.analytics.data.Tuple;
import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.appointment.Date;
import seedu.clinicio.model.consultation.Consultation;

//@@author arsalanc-v2

/**
 * Responsible for all statistics related to appointments.
 * Assumes there are a total of 24 appointment slots in any given day.
 */
public class AppointmentStatistics extends Statistics {

    private static final String SUMMARY_TITLE = "Number of appointments";
    private static final int NUM_APPOINTMENTS_DAY = 24;

    private List<Appointment> appointments;
    private List<Consultation> consultations;

    public AppointmentStatistics() {
        this.appointments = new ArrayList<>();
        initializeSummaryValues(SUMMARY_TITLE, defaultSummaryTexts);
    }

    /**
     * Enables the latest list of appointments to be kept.
     * @param appointments An updated list of appointments.
     */
    public void setAppointments(ObservableList<Appointment> appointments) {
        this.appointments = appointments;
    }

    /**
     *
     * @param consultations
     */
    public void setConsultations(ObservableList<Consultation> consultations) {
        this.consultations = consultations;
    }

    /**
     * @return the total number of appointments.
    */
    private int getNumberOfAppointments() {
        return appointments.size();
    }

    /**
     * @return the total number of cancelled appointments.
     */
    private int getNumberOfCancelledAppointments() {
        long cancelledCount = appointments.stream()
            .filter(appt -> appt.isCancelled())
            .count();

        return toIntExact(cancelledCount);
    }

    /**
     * @return the total number of approved appointments.
     * Depends on the fact that there are only two appointment statuses.
     */
    private int getNumberOfApprovedAppointments() {
        return getNumberOfAppointments() - getNumberOfCancelledAppointments();
    }

    /**
     * @return the total number of follow up appointments.
    */
    private int getNumberOfFollowUpAppointments() {
        long followUpCount = appointments.stream()
            .filter(appt -> appt.getAppointmentType() == 1)
            .count();

        return toIntExact(followUpCount);
    }

    /**
     * Computes the number of appointments for each day of the present week.
    */
    private List<Tuple<String, Integer>> getNumberOfCurrentWeekAppointments() {
        List<Date> datesOfAppointments = appointments.stream()
                .map(appt -> appt.getAppointmentDate())
                .collect(Collectors.toList());

        return DateTimeUtil.eachDateOfCurrentWeekCount(datesOfAppointments).entrySet().stream()
            .map(entry -> new Tuple<String, Integer>(DateTimeUtil.getDayFromDate(entry.getKey()).name(),
                entry.getValue()))
            .collect(Collectors.toList());
    }

    /**
     * Computes data to plot the supply of appointments against their demand.
     */
    public void plotAppointmentSupplyDemand() {
        // get the subset of appointments that are scheduled for next week.
        List<Date> scheduledSlotsDates = appointments.stream()
            .map(appt -> appt.getAppointmentDate())
            .filter(date -> DateTimeUtil.isNextWeek(date))
            .collect(Collectors.toList());

        // get a list of the dates for next week
        List<Date> nextWeekDates = DateTimeUtil.getNextWeekDates();

        List<Date> availableSlotsDates = new ArrayList<>();
        // for each day of next week, find the number of slots scheduled
        for (Date nextWeekDate : nextWeekDates) {
            long scheduledSlots = scheduledSlotsDates.stream()
                .filter(scheduledDate -> scheduledDate.equals(nextWeekDate))
                .count();

            // find the number of available slots for a particular day next week
            int availableSlots = NUM_APPOINTMENTS_DAY - toIntExact(scheduledSlots);
            availableSlotsDates.addAll(Collections.nCopies(availableSlots, nextWeekDate));
        }

        List<List<Tuple<String, Integer>>> appointmentGroupsData = overNextWeekData(Arrays.asList
            (scheduledSlotsDates, availableSlotsDates));
        List<String> appointmentGroupsLabels = Arrays.asList("scheduled", "available");

        // get a list of all days next week
        List<String> nextWeekDays = DateTimeUtil.getDaysOfWeek().stream()
            .map(dayOfWeek -> dayOfWeek.name())
            .collect(Collectors.toList());

        statData.addCategoricalVisualization("apptSupplyDemand", ChartType.STACKED_BAR,
            "Appointments Next Week", "Day of week", "Number of appointments", nextWeekDays,
            appointmentGroupsData, appointmentGroupsLabels);
    }

    /**
     * Updates {@code statData} with the latest summary values.
     */
    @Override
    public void computeSummaryData() {
        List<Date> appointmentDates = appointments.stream()
            .map(appt -> appt.getAppointmentDate())
            .collect(Collectors.toList());

        // calculate appointment numbers - TO ABSTRACT
        int appointmentsToday = DateTimeUtil.today(appointmentDates);
        int appointmentsWeek = DateTimeUtil.currentWeek(appointmentDates);
        int appointmentsMonth = DateTimeUtil.currentMonth(appointmentDates);
        int appointmentsYear = DateTimeUtil.currentYear(appointmentDates);

        List<Integer> values = Arrays.asList(appointmentsToday, appointmentsWeek, appointmentsWeek, appointmentsMonth);

        // update data with calculated values
        statData.updateSummary(SUMMARY_TITLE, defaultSummaryTexts, values);
    }

    @Override
    public void computeVisualizationData() {
        plotAppointmentSupplyDemand();
    }
}
