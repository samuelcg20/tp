package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents an Event in the application.
 * Guarantees: details are present and not null; field values are validated; immutable.
 */
public class Event {

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
     * Returns a new Event with updated attendance list.
     */
    public Event withAttendanceList(String newAttendanceList) {
        return new Event(name, date, venue, newAttendanceList);
    }

    /**
     * Adds a member name to the attendance list.
     */
    public Event addToAttendanceList(String memberName) {
        if (attendanceList.isEmpty()) {
            return withAttendanceList(memberName);
        } else {
            return withAttendanceList(attendanceList + ", " + memberName);
        }
    }

    /**
     * Removes a member name from the attendance list.
     */
    public Event removeFromAttendanceList(String memberName) {
        if (attendanceList.isEmpty()) {
            return this;
        }

        String[] members = attendanceList.split(", ");
        StringBuilder newList = new StringBuilder();

        for (String member : members) {
            if (!member.equals(memberName)) {
                if (newList.length() > 0) {
                    newList.append(", ");
                }
                newList.append(member);
            }
        }

        return withAttendanceList(newList.toString());
    }

    /**
     * Returns true if both events have the same identity (name and date).
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getName().equals(getName())
                && otherEvent.getDate().equals(getDate());
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
        return name.equals(otherEvent.name)
                && date.equals(otherEvent.date)
                && venue.equals(otherEvent.venue)
                && attendanceList.equals(otherEvent.attendanceList);
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
