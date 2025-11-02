package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.alias.AliasBook;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Unit tests for {@link UnmarkCommand}.
 */
public class UnmarkCommandTest {

    @Test
    public void execute_memberMarked_success() throws Exception {
        Person member = new PersonBuilder().withName("Alice Tan").build().withAttendanceCount(1);
        Event event = new EventBuilder().withName("Team Sync").build()
                .addToAttendanceList(member.getName().fullName);
        Model model = prepareModel(member, event);

        UnmarkCommand command = new UnmarkCommand(Index.fromOneBased(1), Index.fromOneBased(1));
        CommandResult result = command.execute(model);

        assertEquals(new CommandResult(UnmarkCommand.MESSAGE_SUCCESS), result);
        Person updatedMember = model.getFilteredPersonList().get(0);
        Event updatedEvent = model.getFilteredEventList().get(0);
        assertEquals(0, updatedMember.getAttendanceCount());
        assertFalse(updatedEvent.hasAttendee(member.getName().fullName));
    }

    @Test
    public void execute_memberNotMarked_throwsCommandException() {
        Person member = new PersonBuilder().withName("Brandon Lee").build();
        Event event = new EventBuilder().withName("Weekly Sync").build();
        Model model = prepareModel(member, event);

        UnmarkCommand command = new UnmarkCommand(Index.fromOneBased(1), Index.fromOneBased(1));
        assertCommandFailure(command, model, UnmarkCommand.MESSAGE_NO_ATTENDANCE_TO_UNMARK);
    }

    @Test
    public void execute_invalidMemberIndex_throwsCommandException() {
        Person member = new PersonBuilder().withName("Cheryl Ng").build().withAttendanceCount(1);
        Event event = new EventBuilder().withName("Orientation Briefing").build()
                .addToAttendanceList(member.getName().fullName);
        Model model = prepareModel(member, event);

        Index outOfBoundsMemberIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnmarkCommand command = new UnmarkCommand(outOfBoundsMemberIndex, Index.fromOneBased(1));

        assertCommandFailure(command, model, UnmarkCommand.MESSAGE_INVALID_MEMBER_INDEX);
    }

    @Test
    public void execute_invalidEventIndex_throwsCommandException() {
        Person member = new PersonBuilder().withName("Daryl Ong").build().withAttendanceCount(1);
        Event event = new EventBuilder().withName("Career Talk").build()
                .addToAttendanceList(member.getName().fullName);
        Model model = prepareModel(member, event);

        Index outOfBoundsEventIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        UnmarkCommand command = new UnmarkCommand(Index.fromOneBased(1), outOfBoundsEventIndex);

        assertCommandFailure(command, model, UnmarkCommand.MESSAGE_INVALID_EVENT_INDEX);
    }

    @Test
    public void execute_bothIndexesInvalid_throwsCommandException() {
        Person member = new PersonBuilder().withName("Farah Lee").build().withAttendanceCount(1);
        Event event = new EventBuilder().withName("Design Review").build()
                .addToAttendanceList(member.getName().fullName);
        Model model = prepareModel(member, event);

        Index outOfBoundsMemberIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Index outOfBoundsEventIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        UnmarkCommand command = new UnmarkCommand(outOfBoundsMemberIndex, outOfBoundsEventIndex);

        assertCommandFailure(command, model, AttendanceCommand.MESSAGE_INVALID_MEMBER_AND_EVENT_INDEX);
    }

    @Test
    public void execute_attendanceDoesNotGoBelowZero_success() throws Exception {
        Person member = new PersonBuilder().withName("Evelyn Koh").build(); // attendance count 0
        Event event = new EventBuilder().withName("Code Sprint").build()
                .addToAttendanceList(member.getName().fullName);
        Model model = prepareModel(member, event);

        UnmarkCommand command = new UnmarkCommand(Index.fromOneBased(1), Index.fromOneBased(1));
        command.execute(model);

        Person updatedMember = model.getFilteredPersonList().get(0);
        assertEquals(0, updatedMember.getAttendanceCount());
    }

    @Test
    public void equals() {
        UnmarkCommand unmarkFirst = new UnmarkCommand(Index.fromOneBased(1), Index.fromOneBased(1));
        UnmarkCommand unmarkSecond = new UnmarkCommand(Index.fromOneBased(2), Index.fromOneBased(1));

        // same object
        assertTrue(unmarkFirst.equals(unmarkFirst));

        // same values
        UnmarkCommand unmarkFirstCopy = new UnmarkCommand(Index.fromOneBased(1), Index.fromOneBased(1));
        assertTrue(unmarkFirst.equals(unmarkFirstCopy));

        // different member index
        assertFalse(unmarkFirst.equals(unmarkSecond));

        // null
        assertFalse(unmarkFirst.equals(null));

        // different type
        assertFalse(unmarkFirst.equals(5));
    }

    private Model prepareModel(Person member, Event event) {
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(member);
        addressBook.addEvent(event);
        return new ModelManager(addressBook, new AliasBook(), new UserPrefs());
    }
}
