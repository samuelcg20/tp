package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class EventTest {

    private final EventName name = new EventName("Hackathon 2025");
    private final Date date = new Date("2025-10-29T15:30");
    private final Venue venue = new Venue("Block 123, Tech Hall");
    private final String attendance = "Alice, Bob";

    // ---------------------- Constructor tests ----------------------

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Event(null, date, venue));
    }

    @Test
    public void constructor_nullDate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Event(name, null, venue));
    }

    @Test
    public void constructor_nullVenue_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Event(name, date, null));
    }

    @Test
    public void constructor_validEvent_success() {
        Event event = new Event(name, date, venue);
        assertEquals(name, event.getName());
        assertEquals(date, event.getDate());
        assertEquals(venue, event.getVenue());
        assertEquals("", event.getAttendanceList());
    }

    @Test
    public void constructor_withAttendanceList_success() {
        Event event = new Event(name, date, venue, attendance);
        assertEquals(attendance, event.getAttendanceList());
        assertEquals(Arrays.asList("Alice", "Bob"), event.getAttendees());
    }

    // ---------------------- Attendance list tests ----------------------

    @Test
    public void getAttendees_emptyAttendanceList_returnsEmptyList() {
        Event event = new Event(name, date, venue);
        List<String> attendees = event.getAttendees();
        assertEquals(Collections.emptyList(), attendees);
    }

    @Test
    public void getAttendees_nonEmptyAttendanceList_returnsList() {
        Event event = new Event(name, date, venue, attendance);
        List<String> attendees = event.getAttendees();
        assertEquals(Arrays.asList("Alice", "Bob"), attendees);
    }

    @Test
    public void hasAttendee_present_returnsTrue() {
        Event event = new Event(name, date, venue, attendance);
        assertTrue(event.hasAttendee("Alice"));
    }

    @Test
    public void hasAttendee_notPresent_returnsFalse() {
        Event event = new Event(name, date, venue, attendance);
        assertFalse(event.hasAttendee("Charlie"));
    }

    @Test
    public void addToAttendanceList_emptyList_addsMember() {
        Event event = new Event(name, date, venue);
        Event updated = event.addToAttendanceList("Alice");
        assertEquals("Alice", updated.getAttendanceList());
        assertEquals(Arrays.asList("Alice"), updated.getAttendees());
    }

    @Test
    public void addToAttendanceList_nonEmptyList_addsMember() {
        Event event = new Event(name, date, venue, "Alice");
        Event updated = event.addToAttendanceList("Bob");
        assertEquals("Alice, Bob", updated.getAttendanceList());
        assertEquals(Arrays.asList("Alice", "Bob"), updated.getAttendees());
    }

    @Test
    public void removeFromAttendanceList_emptyList_returnsSameEvent() {
        Event event = new Event(name, date, venue);
        Event updated = event.removeFromAttendanceList("Alice");
        assertEquals(event, updated);
    }

    @Test
    public void removeFromAttendanceList_existingMember_removesMember() {
        Event event = new Event(name, date, venue, "Alice, Bob");
        Event updated = event.removeFromAttendanceList("Alice");
        assertEquals("Bob", updated.getAttendanceList());
        assertEquals(Arrays.asList("Bob"), updated.getAttendees());
    }

    @Test
    public void removeFromAttendanceList_nonExistingMember_returnsSameEvent() {
        Event event = new Event(name, date, venue, "Alice, Bob");
        Event updated = event.removeFromAttendanceList("Charlie");
        assertEquals(event, updated);
    }

    @Test
    public void replaceAttendeeName_existingMember_replacesName() {
        Event event = new Event(name, date, venue, "Alice, Bob");
        Event updated = event.replaceAttendeeName("Alice", "Charlie");
        assertEquals("Charlie, Bob", updated.getAttendanceList());
        assertEquals(Arrays.asList("Charlie", "Bob"), updated.getAttendees());
    }

    @Test
    public void replaceAttendeeName_nonExistingMember_returnsSameEvent() {
        Event event = new Event(name, date, venue, "Alice, Bob");
        Event updated = event.replaceAttendeeName("David", "Charlie");
        assertEquals(event, updated);
    }

    // ---------------------- isSameEvent tests ----------------------

    @Test
    public void isSameEvent_sameObject_returnsTrue() {
        Event event = new Event(name, date, venue);
        assertTrue(event.isSameEvent(event));
    }

    @Test
    public void isSameEvent_sameNameAndDate_returnsTrue() {
        Event event1 = new Event(name, date, venue);
        Event event2 = new Event(new EventName("Hackathon 2025"), date, new Venue("Other Venue"));
        assertTrue(event1.isSameEvent(event2));
    }

    @Test
    public void isSameEvent_differentNameOrDate_returnsFalse() {
        Event event1 = new Event(name, date, venue);
        Event event2 = new Event(new EventName("Hackathon X"), date, venue);
        Event event3 = new Event(name, new Date("2025-10-30T15:30"), venue);
        assertFalse(event1.isSameEvent(event2));
        assertFalse(event1.isSameEvent(event3));
    }

    // ---------------------- equals tests ----------------------

    @Test
    public void equals_sameObject_returnsTrue() {
        Event event = new Event(name, date, venue, attendance);
        assertTrue(event.equals(event));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Event event1 = new Event(name, date, venue, attendance);
        Event event2 = new Event(new EventName("Hackathon 2025"), new Date("2025-10-29T15:30"),
                new Venue("Block 123, Tech Hall"), "Alice, Bob");
        assertTrue(event1.equals(event2));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Event event1 = new Event(name, date, venue, attendance);
        Event event2 = new Event(new EventName("Hackathon X"), date, venue, attendance);
        assertFalse(event1.equals(event2));
    }

    @Test
    public void equals_null_returnsFalse() {
        Event event = new Event(name, date, venue, attendance);
        assertFalse(event.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Event event = new Event(name, date, venue, attendance);
        assertFalse(event.equals("not an event"));
    }

    // ---------------------- toString test ----------------------

    @Test
    public void toString_containsAllFields() {
        Event event = new Event(name, date, venue, attendance);
        String str = event.toString();
        assertTrue(str.contains(name.toString()));
        assertTrue(str.contains(date.toString()));
        assertTrue(str.contains(venue.toString()));
        assertTrue(str.contains(attendance));
    }
}
