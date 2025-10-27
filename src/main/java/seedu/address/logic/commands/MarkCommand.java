package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * Marks a member's attendance at a specified event.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks attendance for a member at an event.\n"
            + "Parameters: m/MEMBER_INDEX e/EVENT_INDEX\n"
            + "Example: " + COMMAND_WORD + " m/1 e/1";

    public static final String MESSAGE_SUCCESS = "Marked attendance for member at event";
    public static final String MESSAGE_INVALID_MEMBER_INDEX = "Member index is invalid";
    public static final String MESSAGE_INVALID_EVENT_INDEX = "Event index is invalid";
    public static final String MESSAGE_DUPLICATE_ATTENDANCE = "Member is already marked for attendance at this event";

    private static final int MIN_VALID_INDEX = 1;

    private final int memberIndex;
    private final int eventIndex;

    /**
     * Creates a MarkCommand to mark a member's attendance at a specified event.
     */
    public MarkCommand(int memberIndex, int eventIndex) {
        this.memberIndex = memberIndex;
        this.eventIndex = eventIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Event> lastShownEventList = model.getFilteredEventList();

        // Validate member index
        if (memberIndex < MIN_VALID_INDEX || memberIndex > lastShownPersonList.size()) {
            throw new CommandException(MESSAGE_INVALID_MEMBER_INDEX);
        }

        // Validate event index
        if (eventIndex < MIN_VALID_INDEX || eventIndex > lastShownEventList.size()) {
            throw new CommandException(MESSAGE_INVALID_EVENT_INDEX);
        }

        // Get the actual Person and Event objects
        Person memberToMark = lastShownPersonList.get(memberIndex - 1);
        Event eventToMark = lastShownEventList.get(eventIndex - 1);

        // Guard against duplicate attendance
        if (eventToMark.hasAttendee(memberToMark.getName().fullName)) {
            throw new CommandException(MESSAGE_DUPLICATE_ATTENDANCE);
        }

        // Mark attendance
        // Update member's attendance count
        Person updatedMember = memberToMark.withAttendanceCount(memberToMark.getAttendanceCount() + 1);
        model.setPerson(memberToMark, updatedMember);

        // Update event's attendance list
        Event updatedEvent = eventToMark.addToAttendanceList(memberToMark.getName().fullName);
        model.setEvent(eventToMark, updatedEvent);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkCommand)) {
            return false;
        }

        MarkCommand otherMarkCommand = (MarkCommand) other;
        return memberIndex == otherMarkCommand.memberIndex
                && eventIndex == otherMarkCommand.eventIndex;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("memberIndex", memberIndex)
                .add("eventIndex", eventIndex)
                .toString();
    }
}
