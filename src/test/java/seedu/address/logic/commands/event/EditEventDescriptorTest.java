package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.event.Date;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Venue;

public class EditEventDescriptorTest {

    private static final EventName VALID_EVENT_NAME = new EventName("Project Demo");
    private static final Date VALID_DATE = new Date("2025-11-01T15:00");
    private static final Venue VALID_VENUE = new Venue("UTown");

    @Test
    public void equals_sameValues_returnsTrue() {
        EditEventCommand.EditEventDescriptor descriptor1 = new EditEventCommand.EditEventDescriptor();
        descriptor1.setEventName(VALID_EVENT_NAME);
        descriptor1.setDate(VALID_DATE);
        descriptor1.setVenue(VALID_VENUE);

        EditEventCommand.EditEventDescriptor descriptor2 =
                new EditEventCommand.EditEventDescriptor(descriptor1);

        assertTrue(descriptor1.equals(descriptor2));
        assertTrue(descriptor2.equals(descriptor1));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        EditEventCommand.EditEventDescriptor descriptor = new EditEventCommand.EditEventDescriptor();
        assertTrue(descriptor.equals(descriptor));
    }

    @Test
    public void equals_nullOrDifferentType_returnsFalse() {
        EditEventCommand.EditEventDescriptor descriptor = new EditEventCommand.EditEventDescriptor();
        assertFalse(descriptor.equals(null));
        assertFalse(descriptor.equals(5));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        EditEventCommand.EditEventDescriptor descriptor1 = new EditEventCommand.EditEventDescriptor();
        descriptor1.setEventName(VALID_EVENT_NAME);

        EditEventCommand.EditEventDescriptor descriptor2 = new EditEventCommand.EditEventDescriptor();
        descriptor2.setEventName(new EventName("Other Event"));

        assertFalse(descriptor1.equals(descriptor2));
    }

    @Test
    public void copyConstructor_createsEqualDescriptor() {
        EditEventCommand.EditEventDescriptor original = new EditEventCommand.EditEventDescriptor();
        original.setDate(VALID_DATE);
        original.setVenue(VALID_VENUE);

        EditEventCommand.EditEventDescriptor copy =
                new EditEventCommand.EditEventDescriptor(original);

        assertEquals(original, copy);
        assertNotEquals(original, new EditEventCommand.EditEventDescriptor());
    }

    @Test
    public void isAnyFieldEdited_allFieldsNull_returnsFalse() {
        EditEventCommand.EditEventDescriptor descriptor = new EditEventCommand.EditEventDescriptor();
        assertFalse(descriptor.isAnyFieldEdited());
    }

    @Test
    public void isAnyFieldEdited_someFieldNonNull_returnsTrue() {
        EditEventCommand.EditEventDescriptor descriptor = new EditEventCommand.EditEventDescriptor();
        descriptor.setVenue(VALID_VENUE);
        assertTrue(descriptor.isAnyFieldEdited());
    }

    @Test
    public void getters_returnCorrectOptionals() {
        EditEventCommand.EditEventDescriptor descriptor = new EditEventCommand.EditEventDescriptor();
        descriptor.setEventName(VALID_EVENT_NAME);
        descriptor.setDate(VALID_DATE);
        descriptor.setVenue(VALID_VENUE);

        assertTrue(descriptor.getEventName().isPresent());
        assertTrue(descriptor.getDate().isPresent());
        assertTrue(descriptor.getVenue().isPresent());

        assertEquals(VALID_EVENT_NAME, descriptor.getEventName().get());
        assertEquals(VALID_DATE, descriptor.getDate().get());
        assertEquals(VALID_VENUE, descriptor.getVenue().get());
    }

    @Test
    public void toString_containsAllFieldNames() {
        EditEventCommand.EditEventDescriptor descriptor = new EditEventCommand.EditEventDescriptor();
        descriptor.setEventName(VALID_EVENT_NAME);
        descriptor.setVenue(VALID_VENUE);
        String output = descriptor.toString();

        assertTrue(output.contains("event name"));
        assertTrue(output.contains("venue"));
        assertTrue(output.contains(VALID_EVENT_NAME.toString()));
        assertTrue(output.contains(VALID_VENUE.toString()));
    }
}
