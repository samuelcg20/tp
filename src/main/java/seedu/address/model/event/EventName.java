package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Event's name.
 * Guarantees: immutable; is valid as declared in {@link #isValidEventName(String)}
 */
public class EventName {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and\n"
                    + "spaces, should not be blank\n" + "and must be at most 35 characters long.";

    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Creates a Name after validating constraints.
     */
    public EventName(String name) {
        requireNonNull(name);
        checkArgument(isValidEventName(name), MESSAGE_CONSTRAINTS);
        this.fullName = name;
    }

    /**
     * Returns a lowercase, space-normalized version of {@code fullName} for identity comparison.
     */
    public String canonicalForIdentity() {
        String collapsed = normalizeWhitespace(fullName);
        return collapsed.toLowerCase();
    }

    /**
     * Returns the input with leading/trailing spaces removed and multiple internal
     * spaces collapsed to a single space. Preserves original letter casing.
     */
    public static String normalizeWhitespace(String input) {
        requireNonNull(input);
        String trimmed = input.trim();
        return trimmed.replaceAll(" +", " ");
    }

    public static boolean isValidEventName(String test) {
        return test.matches(VALIDATION_REGEX) && test.length() <= 35;
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof EventName)) {
            return false;
        }
        EventName otherName = (EventName) other;
        return fullName.equalsIgnoreCase(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.toLowerCase().hashCode();
    }
}
