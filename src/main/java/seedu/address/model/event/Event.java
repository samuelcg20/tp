package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents an Event in the application.
 * Guarantees: details are present and not null; field values are validated; immutable.
 */
public class Event {

    public static final String ATTENDANCE_DELIMITER = ", ";

    private final EventName name;
    private final Date date;
    private final Venue venue;
    private final String attendanceList;

    /**
     * Creates an Event. All fields must be non-null and valid.
     */
    public Event(EventName name, Date date, Venue venue) {
        this(name, date, venue, "");
    }

    /**
     * Creates an Event with attendance list. All fields must be non-null and valid.
     */
    public Event(EventName name, Date date, Venue venue, String attendanceList) {
        requireAllNonNull(name, date, venue);
        this.name = name;
        this.date = date;
        this.venue = venue;
        this.attendanceList = attendanceList != null ? attendanceList : "";
    }

    public EventName getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public Venue getVenue() {
        return venue;
    }

    public String getAttendanceList() {
        return attendanceList;
    }

    /**
     * Returns the attendees as an immutable list. Returns an empty list if there are no attendees.
     */
    public List<String> getAttendees() {
        if (attendanceList.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(attendanceList.split(ATTENDANCE_DELIMITER));
    }

    /**
     * Returns true if the given name is present in the attendance list.
     */
    public boolean hasAttendee(String entry) {
        for (String attendee : getAttendees()) {
            if (attendee.equals(entry)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a new Event with updated attendance list.
     */
    public Event withAttendanceList(String newAttendanceList) {
        return new Event(name, date, venue, newAttendanceList);
    }

    /**
     * Adds an attendee entry to the attendance list.
     */
    public Event addToAttendanceList(String attendeeEntry) {
        if (attendanceList.isEmpty()) {
            return withAttendanceList(attendeeEntry);
        } else {
            return withAttendanceList(attendanceList + ATTENDANCE_DELIMITER + attendeeEntry);
        }
    }

    /**
     * Removes an attendee entry from the attendance list.
     */
    public Event removeFromAttendanceList(String attendeeEntry) {
        if (attendanceList.isEmpty()) {
            return this;
        }

        List<String> filtered = new ArrayList<>();
        for (String member : getAttendees()) {
            if (!member.equals(attendeeEntry)) {
                filtered.add(member);
            }
        }

        return withAttendanceList(String.join(ATTENDANCE_DELIMITER, filtered));
    }

    /**
     * Replaces an attendee entry with a new entry in the attendance list.
     */
    public Event replaceAttendeeEntry(String oldEntry, String newEntry) {
        if (attendanceList.isEmpty() || !hasAttendee(oldEntry)) {
            return this;
        }
        List<String> updated = new ArrayList<>();
        for (String attendee : getAttendees()) {
            updated.add(attendee.equals(oldEntry) ? newEntry : attendee);
        }
        return withAttendanceList(String.join(ATTENDANCE_DELIMITER, updated));
    }

    /**
     * Returns true if both events have the same identity (date and venue).
     */
    public boolean isSameEvent(Event otherEvent) {
        return otherEvent.equals(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return date.equals(otherEvent.date)
                && venue.equals(otherEvent.venue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, venue, attendanceList);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("date", date)
                .add("venue", venue)
                .add("attendanceList", attendanceList)
                .toString();
    }
}
