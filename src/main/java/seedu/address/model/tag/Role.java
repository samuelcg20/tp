package seedu.address.model.role;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Role in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Role {

    public static final String MESSAGE_CONSTRAINTS = "Roles should be alphanumeric"
            + " and the input must be at most 35 characters long.";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String roleName;

    /**
     * Constructs a {@code Role}.
     *
     * @param roleName A valid role name.
     */
    public Role(String roleName) {
        requireNonNull(roleName);
        checkArgument(isValidTagName(roleName), MESSAGE_CONSTRAINTS);
        this.roleName = roleName;
    }

    /**
     * Returns true if a given string is a valid role name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX) && test.length() <= 35;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Role)) {
            return false;
        }

        Role otherRole = (Role) other;
        return roleName.equals(otherRole.roleName);
    }

    @Override
    public int hashCode() {
        return roleName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + roleName + ']';
    }

}
