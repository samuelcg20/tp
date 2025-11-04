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
 * Unit tests for {@link MarkCommand}.
 */
public class MarkCommandTest {

    @Test
    public void execute_validIndexes_success() throws Exception {
        Person member = new PersonBuilder().withName("Alice Tan").build();
        Event event = new EventBuilder().withName("Team Sync").build();
        Model model = prepareModel(member, event);

        MarkCommand command = new MarkCommand(Index.fromOneBased(1), Index.fromOneBased(1));
        CommandResult result = command.execute(model);

        assertEquals(new CommandResult(MarkCommand.MESSAGE_SUCCESS), result);
        Person updatedMember = model.getFilteredPersonList().get(0);
        Event updatedEvent = model.getFilteredEventList().get(0);
        assertEquals(1, updatedMember.getAttendanceCount());
        assertTrue(updatedEvent.hasAttendee(member.getAttendanceKey()));
    }

    @Test
    public void execute_duplicateAttendance_throwsCommandException() throws Exception {
        Person member = new PersonBuilder().withName("Brandon Lee").build();
        Event event = new EventBuilder().withName("Weekly Sync").build();
        Model model = prepareModel(member, event);

        MarkCommand initialCommand = new MarkCommand(Index.fromOneBased(1), Index.fromOneBased(1));
        initialCommand.execute(model);

        MarkCommand duplicateCommand = new MarkCommand(Index.fromOneBased(1), Index.fromOneBased(1));
        assertCommandFailure(duplicateCommand, model, MarkCommand.MESSAGE_DUPLICATE_ATTENDANCE);

        Person updatedMember = model.getFilteredPersonList().get(0);
        assertEquals(1, updatedMember.getAttendanceCount());
    }

    @Test
    public void execute_invalidMemberIndex_throwsCommandException() {
        Person member = new PersonBuilder().withName("Cheryl Ng").build();
        Event event = new EventBuilder().withName("Orientation Briefing").build();
        Model model = prepareModel(member, event);

        Index outOfBoundsMemberIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MarkCommand command = new MarkCommand(outOfBoundsMemberIndex, Index.fromOneBased(1));

        assertCommandFailure(command, model, MarkCommand.MESSAGE_INVALID_MEMBER_INDEX);
    }

    @Test
    public void execute_invalidEventIndex_throwsCommandException() {
        Person member = new PersonBuilder().withName("Daryl Ong").build();
        Event event = new EventBuilder().withName("Career Talk").build();
        Model model = prepareModel(member, event);

        Index outOfBoundsEventIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        MarkCommand command = new MarkCommand(Index.fromOneBased(1), outOfBoundsEventIndex);

        assertCommandFailure(command, model, MarkCommand.MESSAGE_INVALID_EVENT_INDEX);
    }

    @Test
    public void execute_bothIndexesInvalid_throwsCommandException() {
        Person member = new PersonBuilder().withName("Ethan Lim").build();
        Event event = new EventBuilder().withName("Tech Talk").build();
        Model model = prepareModel(member, event);

        Index outOfBoundsMemberIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Index outOfBoundsEventIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        MarkCommand command = new MarkCommand(outOfBoundsMemberIndex, outOfBoundsEventIndex);

        assertCommandFailure(command, model, AttendanceCommand.MESSAGE_INVALID_MEMBER_AND_EVENT_INDEX);
    }

    @Test
    public void equals() {
        MarkCommand markFirst = new MarkCommand(Index.fromOneBased(1), Index.fromOneBased(1));
        MarkCommand markSecond = new MarkCommand(Index.fromOneBased(2), Index.fromOneBased(1));

        // same object
        assertTrue(markFirst.equals(markFirst));

        // same values
        MarkCommand markFirstCopy = new MarkCommand(Index.fromOneBased(1), Index.fromOneBased(1));
        assertTrue(markFirst.equals(markFirstCopy));

        // different member index
        assertFalse(markFirst.equals(markSecond));

        // null
        assertFalse(markFirst.equals(null));

        // different type
        assertFalse(markFirst.equals(5));
    }

    private Model prepareModel(Person member, Event event) {
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(member);
        addressBook.addEvent(event);
        return new ModelManager(addressBook, new AliasBook(), new UserPrefs());
    }
}
