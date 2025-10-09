package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an Event date (YYYY-MM-DD).
 * Immutable and validated.
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS = "Dates should be in ISO format YYYY-MM-DD";

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public final String value;

    /**
     * Creates a Date from an ISO string (YYYY-MM-DD).
     */
    public Date(String dateString) {
        requireNonNull(dateString);
        checkArgument(isValidDate(dateString), MESSAGE_CONSTRAINTS);
        this.value = dateString;
    }

    /**
     * Returns true if the given string is a valid ISO date.
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDate.parse(test, FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Date)) {
            return false;
        }
        Date otherDate = (Date) other;
        return value.equals(otherDate.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}