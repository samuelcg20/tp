package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Venue;
import seedu.address.model.person.Person;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Tests for {@link DeleteEventCommand}.
 */
public class DeleteEventCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        // Use default constructor; change if your ModelManager signature differs
        model = new ModelManager();

        // Add three events to the model for test determinism
        model.addEvent(new EventBuilder().withName("Hackathon").withLocation("Location 1").build());
        model.addEvent(new EventBuilder().withName("Workshop").withLocation("Location 2").build());
        model.addEvent(new EventBuilder().withName("Seminar").withLocation("Location 3").build());
    }

    @Test
    public void execute_validIndex_success() throws Exception {
        List<Event> before = List.copyOf(model.getFilteredEventList());
        int initialSize = before.size();
        assertTrue(initialSize >= 1, "Test setup should add at least one event");

        Event eventToDelete = before.get(0);
        DeleteEventCommand cmd = new DeleteEventCommand(Index.fromOneBased(1));

        CommandResult result = cmd.execute(model);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS,
                Messages.format(eventToDelete));
        assertEquals(expectedMessage, result.getFeedbackToUser());

        // model should have one fewer event and should not contain the deleted event
        List<Event> after = model.getFilteredEventList();
        assertEquals(initialSize - 1, after.size(),
                "Model size should decrease by 1 after deletion");
        assertFalse(after.contains(eventToDelete), "Deleted event should no longer be present");
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        int outOfBoundsOneBased = model.getFilteredEventList().size() + 1;
        DeleteEventCommand cmd = new DeleteEventCommand(Index.fromOneBased(outOfBoundsOneBased));

        CommandException thrown = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX, thrown.getMessage());
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        DeleteEventCommand cmd = new DeleteEventCommand(Index.fromOneBased(1));
        assertThrows(NullPointerException.class, () -> cmd.execute(null));
    }

    @Test
    public void execute_deleteLastIndex_success() throws Exception {
        int lastIndexOneBased = model.getFilteredEventList().size();
        Event eventToDelete = model.getFilteredEventList().get(lastIndexOneBased - 1);

        DeleteEventCommand cmd = new DeleteEventCommand(Index.fromOneBased(lastIndexOneBased));
        CommandResult result = cmd.execute(model);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS,
                Messages.format(eventToDelete));
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertFalse(model.getFilteredEventList().contains(eventToDelete));
    }

    @Test
    public void execute_futureEvent_attendanceDecremented() throws Exception {
        Model freshModel = new ModelManager();
        Person attendee = new PersonBuilder().withName("Future Member")
                .withPhone("90000001").withEmail("future@u.nus.edu").build().withAttendanceCount(1);
        freshModel.addPerson(attendee);

        Event futureEvent = new Event(new EventName("Future Event"),
                new Date("2999-01-01T00:00"), new Venue("COM1-01"),
                attendee.getAttendanceKey());
        freshModel.addEvent(futureEvent);

        DeleteEventCommand command = new DeleteEventCommand(Index.fromOneBased(1));
        command.execute(freshModel);

        Person updatedAttendee = freshModel.getAddressBook().getPersonList().get(0);
        assertEquals(0, updatedAttendee.getAttendanceCount());
        assertTrue(freshModel.getAddressBook().getEventList().isEmpty());
    }

    @Test
    public void execute_pastEvent_attendanceUnaffected() throws Exception {
        Model freshModel = new ModelManager();
        Person attendee = new PersonBuilder().withName("Past Member")
                .withPhone("90000002").withEmail("past@u.nus.edu").build().withAttendanceCount(2);
        freshModel.addPerson(attendee);

        Event pastEvent = new Event(new EventName("Past Event"),
                new Date("2000-01-01T00:00"), new Venue("COM1-02"),
                attendee.getAttendanceKey());
        freshModel.addEvent(pastEvent);

        DeleteEventCommand command = new DeleteEventCommand(Index.fromOneBased(1));
        command.execute(freshModel);

        Person updatedAttendee = freshModel.getAddressBook().getPersonList().get(0);
        assertEquals(2, updatedAttendee.getAttendanceCount());
        assertTrue(freshModel.getAddressBook().getEventList().isEmpty());
    }

    @Test
    public void equals_andToString_tests() {
        DeleteEventCommand cmd1 = new DeleteEventCommand(Index.fromOneBased(1));
        DeleteEventCommand cmd1Copy = new DeleteEventCommand(Index.fromOneBased(1));
        DeleteEventCommand cmd2 = new DeleteEventCommand(Index.fromOneBased(2));

        // equals
        assertEquals(cmd1, cmd1Copy);
        assertNotEquals(cmd1, cmd2);
        assertNotEquals(cmd1, null);
        assertNotEquals(cmd1, new Object());

        // toString - ensure format matches your command's toString()
        String toStringValue = cmd1.toString();
        assertTrue(toStringValue.contains("targetIndex"));
    }
}


