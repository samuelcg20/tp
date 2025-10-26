package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * Removes a member's attendance from a specified event.
 */
public class UnmarkCommand extends Command {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unmarks a member from an event.\n"
            + "Parameters: m/MEMBER_INDEX e/EVENT_INDEX\n"
            + "Example: " + COMMAND_WORD + " m/1 e/1";

    public static final String MESSAGE_SUCCESS = "Unmarked member from event";
    public static final String MESSAGE_INVALID_MEMBER_INDEX = "Member index is invalid";
    public static final String MESSAGE_INVALID_EVENT_INDEX = "Event index is invalid";
    public static final String MESSAGE_NO_ATTENDANCE_TO_UNMARK = "Member is not marked at this event";

    private final int memberIndex;
    private final int eventIndex;

    /**
     * Creates an UnmarkCommand to remove a member's attendance from a specified event.
     */
    public UnmarkCommand(int memberIndex, int eventIndex) {
        this.memberIndex = memberIndex;
        this.eventIndex = eventIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Event> lastShownEventList = model.getFilteredEventList();

        // Validate member index
        if (memberIndex < 1 || memberIndex > lastShownPersonList.size()) {
            throw new CommandException(MESSAGE_INVALID_MEMBER_INDEX);
        }

        // Validate event index
        if (eventIndex < 1 || eventIndex > lastShownEventList.size()) {
            throw new CommandException(MESSAGE_INVALID_EVENT_INDEX);
        }

        // Get the actual Person and Event objects
        Person memberToUnmark = lastShownPersonList.get(memberIndex - 1);
        Event eventToUnmark = lastShownEventList.get(eventIndex - 1);

        // Check if member is actually marked for attendance
        String attendanceList = eventToUnmark.getAttendanceList();
        boolean found = false;
        if (!attendanceList.isEmpty()) {
            String[] attendees = attendanceList.split(", ");
            for (String attendee : attendees) {
                if (attendee.equals(memberToUnmark.getName().fullName)) {
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            throw new CommandException(MESSAGE_NO_ATTENDANCE_TO_UNMARK);
        }

        // Unmark attendance
        // Update member's attendance count (ensure it doesn't go below 0)
        int newAttendanceCount = Math.max(0, memberToUnmark.getAttendanceCount() - 1);
        Person updatedMember = memberToUnmark.withAttendanceCount(newAttendanceCount);
        model.setPerson(memberToUnmark, updatedMember);

        // Update event's attendance list
        Event updatedEvent = eventToUnmark.removeFromAttendanceList(memberToUnmark.getName().fullName);
        model.setEvent(eventToUnmark, updatedEvent);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnmarkCommand)) {
            return false;
        }

        UnmarkCommand otherUnmarkCommand = (UnmarkCommand) other;
        return memberIndex == otherUnmarkCommand.memberIndex
                && eventIndex == otherUnmarkCommand.eventIndex;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("memberIndex", memberIndex)
                .add("eventIndex", eventIndex)
                .toString();
    }
}
