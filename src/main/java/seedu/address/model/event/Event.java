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

    /**
     * Creates an Event. All fields must be non-null and valid.
     */
    public Event(EventName name, Date date, Venue venue) {
        requireAllNonNull(name, date, venue);
        this.name = name;
        this.date = date;
        this.venue = venue;
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
                && venue.equals(otherEvent.venue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, venue);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("date", date)
                .add("venue", venue)
                .toString();
    }
}
