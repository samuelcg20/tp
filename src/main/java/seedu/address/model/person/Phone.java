package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable
 */
public class Phone {

    public static final String MESSAGE_CONSTRAINTS_NUMBER = "Phone number should only contain digits";
    public static final String MESSAGE_CONSTRAINTS_LENGTH = "Phone number must be exactly 8 digits.";
    public static final String MESSAGE_CONSTRAINTS_START = "Phone number must start with 8 or 9.";
    public static final String MESSAGE_CONSTRAINTS_SPACES = "Phone number must not contain spaces between the digits.";
    private static final String DIGITS_ONLY_REGEX = "\\d+";
    private static final String INTERNAL_WHITESPACE_REGEX = ".*\\s+.*";
    private static final int REQUIRED_LENGTH = 8;

    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        checkArgument(!hasInternalWhitespace(phone), MESSAGE_CONSTRAINTS_SPACES);
        checkArgument(isDigitsOnly(phone), MESSAGE_CONSTRAINTS_NUMBER);
        checkArgument(isValidLength(phone), MESSAGE_CONSTRAINTS_LENGTH);
        checkArgument(isValidStart(phone), MESSAGE_CONSTRAINTS_START);
        value = phone;
    }

    // 1
    /**
     * Returns true if a given string has no internal whitespace.
     */
    public static boolean hasInternalWhitespace(String test) {
        return test.matches(INTERNAL_WHITESPACE_REGEX);
    }

    // 2
    /**
     * Returns true if a given string contains only digits.
     */
    public static boolean isDigitsOnly(String test) {
        return test.matches(DIGITS_ONLY_REGEX);
    }

    // 3
    /**
     * Returns true if a given string has the required length of 8 digits.
     */
    public static boolean isValidLength(String test) {
        return test.length() == REQUIRED_LENGTH;
    }

    // 4
    /**
     * Returns true if a given string starts with 8 or 9.
     */
    public static boolean isValidStart(String test) {
        return test.startsWith("8") || test.startsWith("9");
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

        // instanceof handles nulls
        if (!(other instanceof Phone)) {
            return false;
        }

        Phone otherPhone = (Phone) other;
        return value.equals(otherPhone.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
