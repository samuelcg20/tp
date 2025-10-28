package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_WELCOME_TEA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_WELCOME_TEA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_VENUE_WELCOME_TEA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_VENUE_WORKSHOP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Event MEETING = new EventBuilder()
            .withName("Team Meeting")
            .withDate("2025-11-01T12:00")
            .withLocation("Zoom")
            .build();

    public static final Event WORKSHOP = new EventBuilder()
            .withName("AI Workshop")
            .withDate("2025-11-10T13:00")
            .withLocation("NUS i3 Auditorium")
            .build();

    public static final Event HACKATHON = new EventBuilder()
            .withName("HackNUS 2025")
            .withDate("2025-12-15T14:00")
            .withLocation("UTown")
            .build();

    public static final Event SEMINAR = new EventBuilder()
            .withName("Tech Seminar")
            .withDate("2026-01-05T14:00")
            .build();

    // Manually added
    public static final Event CONFERENCE = new EventBuilder()
            .withName("Developers Conference")
            .withDate("2026-02-20T16:00")
            .withLocation("Suntec City")
            .build();

    public static final Event TRAINING = new EventBuilder()
            .withName("Leadership Training")
            .withDate("2026-03-10T17:00")
            .withLocation("COM2-0205")
            .build();

    // Manually added - Event's details found in {@code CommandTestUtil}
    public static final Event VALID_WORKSHOP_EVENT = new EventBuilder()
            .withName(VALID_EVENT_NAME_WORKSHOP)
            .withDate(VALID_EVENT_DATE_WORKSHOP)
            .withLocation(VALID_EVENT_VENUE_WORKSHOP)
            .build();

    public static final Event VALID_WELCOME_TEA_EVENT = new EventBuilder()
            .withName(VALID_EVENT_NAME_WELCOME_TEA)
            .withDate(VALID_EVENT_DATE_WELCOME_TEA)
            .withLocation(VALID_EVENT_VENUE_WELCOME_TEA)
            .build();

    public static final String KEYWORD_MATCHING_TECH = "Tech"; // A keyword that matches SEMINAR

    private TypicalEvents() {} // prevents instantiation

    /**
     * Returns an {@code EventBook} with all the typical events.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Event event : getTypicalEvents()) {
            ab.addEvent(event);
        }
        return ab;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(MEETING, WORKSHOP, HACKATHON, SEMINAR));
    }
}
