package seedu.clinicio.model.analytics;

//@@author arsalanc-v2
/**
 * Represents statistics that are averages.
 */
public class AverageStatistics extends Statistics {

    public static final StatisticType STATISTIC_TYPE = StatisticType.AVERAGE;
    public static final int NUMBER_STATISTICS = 4;
    private static final String AVERAGE_TYPE_DAY = "per day";
    private static final String AVERAGE_TYPE_PATIENT = "per patient";

    // Different statistics fields.
    private static final String NUMBER_PATIENTS_DAY = STATISTIC_TYPE
            + " number of patients "
            + AVERAGE_TYPE_DAY;
    private static final String TIME_WAITING_PATIENT = STATISTIC_TYPE
            + " waiting time "
            + AVERAGE_TYPE_PATIENT;
    private static final String NUMBER_MEDICINES_DAY = STATISTIC_TYPE
            + " number of medicines prescribed "
            + AVERAGE_TYPE_DAY;
    private static final String NUMBER_MEDICINES_PATIENT = STATISTIC_TYPE
            + " number of medicines prescribed "
            + AVERAGE_TYPE_PATIENT;

    // Sets statistics to 0 and stores them in a HashMap.
    public AverageStatistics() {
        super();
        this.statistics.put(NUMBER_PATIENTS_DAY, 0);
        this.statistics.put(TIME_WAITING_PATIENT, 0);
        this.statistics.put(NUMBER_MEDICINES_DAY, 0);
        this.statistics.put(NUMBER_MEDICINES_PATIENT, 0);
    }

    @Override
    public void compute() {
        // placeholder
    }
}
