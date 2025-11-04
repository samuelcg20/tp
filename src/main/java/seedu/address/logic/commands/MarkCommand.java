package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * Marks a member's attendance at a specified event.
 */
public class MarkCommand extends AttendanceCommand {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks attendance for a member at an event.\n"
            + "Parameters: m/MEMBER_INDEX e/EVENT_INDEX\n"
            + "Example: " + COMMAND_WORD + " m/1 e/1";

    public static final String MESSAGE_SUCCESS = "Marked attendance for member at event";
    public static final String MESSAGE_INVALID_MEMBER_INDEX = "Member index is invalid";
    public static final String MESSAGE_INVALID_EVENT_INDEX = "Event index is invalid";
    public static final String MESSAGE_DUPLICATE_ATTENDANCE = "Member is already marked for attendance at this event";

    /**
     * Creates a MarkCommand to mark a member's attendance at a specified event.
     */
    public MarkCommand(Index memberIndex, Index eventIndex) {
        super(memberIndex, eventIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        AttendanceContext context =
                resolveAttendanceContext(model, MESSAGE_INVALID_MEMBER_INDEX, MESSAGE_INVALID_EVENT_INDEX);
        Person memberToMark = context.getMember();
        Event eventToMark = context.getEvent();

        String attendanceEntry = memberToMark.getAttendanceKey();

        // Guard against duplicate attendance
        if (eventToMark.hasAttendee(attendanceEntry)) {
            throw new CommandException(MESSAGE_DUPLICATE_ATTENDANCE);
        }

        // Mark attendance
        // Update member's attendance count
        Person updatedMember = memberToMark.withAttendanceCount(memberToMark.getAttendanceCount() + 1);
        model.setPerson(memberToMark, updatedMember);

        // Update event's attendance list
        Event updatedEvent = eventToMark.addToAttendanceList(attendanceEntry);
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
        return memberIndex.equals(otherMarkCommand.memberIndex)
                && eventIndex.equals(otherMarkCommand.eventIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("memberIndex", memberIndex)
                .add("eventIndex", eventIndex)
                .toString();
    }
}
