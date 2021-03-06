package seedu.clinicio.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.clinicio.model.staff.Role.DOCTOR;
import static seedu.clinicio.model.staff.Role.RECEPTIONIST;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.clinicio.commons.core.index.Index;

import seedu.clinicio.commons.util.StringUtil;

import seedu.clinicio.logic.parser.exceptions.ParseException;

import seedu.clinicio.model.appointment.Date;
import seedu.clinicio.model.appointment.Time;
import seedu.clinicio.model.person.Address;
import seedu.clinicio.model.person.Email;
import seedu.clinicio.model.person.Name;
import seedu.clinicio.model.person.Phone;
import seedu.clinicio.model.staff.Password;
import seedu.clinicio.model.staff.Role;
import seedu.clinicio.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String clinicio} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code clinicio} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses a {@code String password} into a {@code Password}.
     *
     * @throws ParseException if the given {@code password} is invalid.
     */
    public static Password parsePassword(String password) throws ParseException {
        requireNonNull(password);
        if (!Password.isValidPassword(password)) {
            throw new ParseException(Password.MESSAGE_PASSWORD_CONSTRAINTS);
        }
        return new Password(password, false);
    }

    /**
     * Parse {@code role} into either doctor or receptionist
     * @param role The type of user (doctor, receptionist)
     * @return A valid person with its role object.
     */
    public static Role parseRole(String role) throws ParseException {
        requireNonNull(role);
        if (role.equals("doctor")) {
            return DOCTOR;
        } else if (role.equals("receptionist")) {
            return RECEPTIONIST;
        } else {
            throw new ParseException("You have entered invalid role. Please try with either staff or receptionist.");
        }
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String date} into an {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        return Date.newDate(trimmedDate);
    }

    /**
     * Parses a {@code String time} into an {@code Time}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code time} is invalid.
     */
    public static Time parseTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!Time.isValidTime(trimmedTime)) {
            throw new ParseException(Time.MESSAGE_TIME_CONSTRAINTS);
        }
        return Time.newTime(trimmedTime);
    }

    /**
     * Parses a {@code String type} into an int.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code type} is invalid.
     */
    public static int parseType(String type) throws ParseException {
        requireNonNull(type);
        String trimmedType = type.trim();
        if (trimmedType.matches("followup")) {
            return 1;
        }
        return 0;
    }
}
