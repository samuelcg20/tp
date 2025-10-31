package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.role.Role;


/**
 * Represents a command that edits the details of an existing person in the address book.
 * The person is identified by their index, and specified fields are updated with new values.
 */
public abstract class EditCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of a member or an event "
            + "identified by the index number in the displayed list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters for editing members:\n"
            + "  " + COMMAND_WORD + " member MEMBER_INDEX "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_YEAR + "YEAR(1-4)] "
            + "[" + PREFIX_ROLE + "ROLE]...\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@u.nus.edu\n"
            + "Parameters for editing events:\n"
            + "  " + COMMAND_WORD + " event EVENT_INDEX "
            + "[" + PREFIX_NAME + "EVENT NAME] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_LOCATION + "LOCATION]\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " event 2 "
            + PREFIX_NAME + "CS Lecture "
            + PREFIX_LOCATION + "COM1-0203";
    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */

    public abstract static class EditDescriptor {
        private Set<Role> roles;

        public EditDescriptor() {}

        /**
         * Returns true if at least one field is edited.
         */
        public abstract boolean isAnyFieldEdited();

        /**
         * Sets {@code roles} to this object's {@code roles}.
         * A defensive copy of {@code roles} is used internally.
         */
        public void setRoles(Set<Role> roles) {
            this.roles = (roles != null) ? new HashSet<>(roles) : null;
        }

        /**
         * Returns an unmodifiable role set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code roles} is null.
         */
        public Optional<Set<Role>> getTags() {
            return (roles != null) ? Optional.of(Collections.unmodifiableSet(roles)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditDescriptor)) {
                return false;
            }

            EditDescriptor otherEditDescriptor = (EditDescriptor) other;
            return Objects.equals(roles, otherEditDescriptor.roles);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("roles", roles)
                    .toString();
        }
    }
}
