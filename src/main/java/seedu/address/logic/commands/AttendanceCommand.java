package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * Base class for attendance-related commands that operate on a member and an event.
    */
public abstract class AttendanceCommand extends Command {

    public static final String MESSAGE_INVALID_MEMBER_AND_EVENT_INDEX =
            "Both member and event indices are invalid.";

    protected final Index memberIndex;
    protected final Index eventIndex;

    protected AttendanceCommand(Index memberIndex, Index eventIndex) {
        this.memberIndex = requireNonNull(memberIndex);
        this.eventIndex = requireNonNull(eventIndex);
    }

    /**
     * Resolves the member and event targets referenced by the stored indexes.
     *
     * @throws CommandException if either index is out of bounds.
     */
    protected AttendanceContext resolveAttendanceContext(Model model,
                                                         String invalidMemberMessage,
                                                         String invalidEventMessage) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Event> lastShownEventList = model.getFilteredEventList();

        int memberZeroBased = memberIndex.getZeroBased();
        int eventZeroBased = eventIndex.getZeroBased();

        boolean invalidMember = memberZeroBased < 0 || memberZeroBased >= lastShownPersonList.size();
        boolean invalidEvent = eventZeroBased < 0 || eventZeroBased >= lastShownEventList.size();

        if (invalidMember && invalidEvent) {
            throw new CommandException(MESSAGE_INVALID_MEMBER_AND_EVENT_INDEX);
        }
        if (invalidMember) {
            throw new CommandException(invalidMemberMessage);
        }
        if (invalidEvent) {
            throw new CommandException(invalidEventMessage);
        }

        Person member = lastShownPersonList.get(memberZeroBased);
        Event event = lastShownEventList.get(eventZeroBased);

        return new AttendanceContext(member, event);
    }

    /**
     * Lightweight container for the member and event targeted by an attendance command.
     */
    protected static class AttendanceContext {
        private final Person member;
        private final Event event;

        private AttendanceContext(Person member, Event event) {
            this.member = member;
            this.event = event;
        }

        public Person getMember() {
            return member;
        }

        public Event getEvent() {
            return event;
        }
    }
}
