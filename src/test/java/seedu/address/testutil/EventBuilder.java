package seedu.address.testutil;


import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Date;
import seedu.address.model.event.Venue;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_NAME = "CS2103T Tutorial";
    public static final String DEFAULT_DATE = "2025-10-28T12:00";
    public static final String DEFAULT_LOCATION = "COM2-0205";

    private EventName name;
    private Date date;
    private Venue location;

    /**
     * Creates a {@code EventBuilder} with the default details.
     */
    public EventBuilder() {
        name = new EventName(DEFAULT_NAME);
        date = new Date(DEFAULT_DATE);
        location = new Venue(DEFAULT_LOCATION);
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getName();
        date = eventToCopy.getDate();
        location = eventToCopy.getVenue();
    }

    /**
     * Sets the {@code EventName} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = new EventName(name.trim()); // trim spaces to replicate parser
        return this;
    }

    /**
     * Sets the {@code EventDate} of the {@code Event} that we are building.
     */
    public EventBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code EventLocation} of the {@code Event} that we are building.
     */
    public EventBuilder withLocation(String location) {
        this.location = new Venue(location);
        return this;
    }

    public Event build() {
        return new Event(name, date, location);
    }
}
