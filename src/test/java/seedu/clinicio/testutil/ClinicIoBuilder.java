package seedu.clinicio.testutil;

import seedu.clinicio.model.ClinicIo;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.staff.Staff;

/**
 * A utility class to help with building ClinicIO objects.
 * Example usage: <br>
 *     {@code ClinicIo clinicIo = new ClinicIoBuilder().withPerson("John", "Doe").build();}
 */
public class ClinicIoBuilder {

    private ClinicIo clinicIo;

    public ClinicIoBuilder() {
        clinicIo = new ClinicIo();
    }

    public ClinicIoBuilder(ClinicIo clinicIo) {
        this.clinicIo = clinicIo;
    }

    /**
     * Adds a new {@code Person} to the {@code ClinicIo} that we are building.
     */
    public ClinicIoBuilder withPerson(Person person) {
        clinicIo.addPerson(person);
        return this;
    }

    /**
     * Adds a new {@code Staff} to the {@code ClinicIo} that we are building.
     */
    public ClinicIoBuilder withStaff(Staff staff) {
        clinicIo.addStaff(staff);
        return this;
    }

    public ClinicIo build() {
        return clinicIo;
    }
}
