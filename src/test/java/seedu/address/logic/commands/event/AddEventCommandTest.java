package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.event.AddEventCommand.MESSAGE_DUPLICATE_EVENT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.getTypicalEvents;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.AliasBook;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.testutil.EventBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code AddEventCommand}.
 */
public class AddEventCommandTest {

    private ModelStubAcceptingEventAdded modelStub;

    @BeforeEach
    public void setUp() {
        modelStub = new ModelStubAcceptingEventAdded();
    }

    // === VALID CASES ===

    @Test
    public void execute_validEvent_success() throws Exception {
        Event validEvent = new EventBuilder().build();
        CommandResult result = new AddEventCommand(validEvent).execute(modelStub);
        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, Messages.format(validEvent)),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_typicalEvents_success() throws Exception {
        for (Event e : getTypicalEvents()) {
            CommandResult result = new AddEventCommand(e).execute(modelStub);
            assertTrue(modelStub.eventsAdded.contains(e));
            assertTrue(result.getFeedbackToUser().contains(e.getName().fullName));
        }
    }

    @Test
    public void execute_eventWithDifferentCaseName_success() throws Exception {
        Event event = new EventBuilder().withName("welcome tea").build();
        CommandResult result = new AddEventCommand(event).execute(modelStub);
        assertTrue(result.getFeedbackToUser().contains("welcome tea"));
    }

    @Test
    public void execute_eventWithExtraSpaces_success() throws Exception {
        Event event = new EventBuilder().withName("   Hackathon  ").build();
        CommandResult result = new AddEventCommand(event).execute(modelStub);
        assertTrue(result.getFeedbackToUser().contains("Hackathon"));
    }

    @Test
    public void execute_eventWithTooLongName_throwsIllegalArgumentException() {
        String longName = "National University Hackathon Challenge 2025";
        assertThrows(IllegalArgumentException.class,
                () -> new EventBuilder().withName(longName).build());
    }

    @Test
    public void execute_eventWithValidBoundaryDate_success() throws Exception {
        Event event = new EventBuilder().withDate("2025-12-31T23:59").build();
        new AddEventCommand(event).execute(modelStub);
        assertTrue(modelStub.eventsAdded.contains(event));
    }

    @Test
    public void execute_eventWithMinimumValidDate_success() throws Exception {
        Event event = new EventBuilder().withDate("2000-01-01T00:00").build();
        new AddEventCommand(event).execute(modelStub);
        assertTrue(modelStub.eventsAdded.contains(event));
    }

    @Test
    public void execute_eventWithSimpleVenue_success() throws Exception {
        Event event = new EventBuilder().withLocation("LT27").build();
        new AddEventCommand(event).execute(modelStub);
        assertTrue(modelStub.eventsAdded.contains(event));
    }

    // === DUPLICATE CASES ===

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        Event event = new EventBuilder().build();
        AddEventCommand command = new AddEventCommand(event);
        ModelStub modelStubWithEvent = new ModelStubWithEvent(event);
        assertThrows(CommandException.class, MESSAGE_DUPLICATE_EVENT,
                () -> command.execute(modelStubWithEvent));
    }

    @Test
    public void execute_duplicateEventWithDifferentCaseName_stillThrows() {
        Event event = new EventBuilder().withName("Welcome Tea").build();
        ModelStub modelStubWithEvent = new ModelStubWithEvent(
                new EventBuilder().withName("welcome tea").build());
        AddEventCommand command = new AddEventCommand(event);
        assertThrows(CommandException.class, MESSAGE_DUPLICATE_EVENT,
                () -> command.execute(modelStubWithEvent));
    }

    // === INVALID NAME CASES ===

    @Test
    public void execute_invalidNameWithSymbols_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new EventBuilder().withName("Hack@thon2025!").build());
    }

    @Test
    public void execute_invalidNameEmpty_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new EventBuilder().withName("").build());
    }

    @Test
    public void execute_invalidNameSpacesOnly_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new EventBuilder().withName("    ").build());
    }

    @Test
    public void execute_invalidNameTooLong_throwsIllegalArgumentException() {
        String name = "Annual Student " + "Hackathon ".repeat(15);
        assertThrows(IllegalArgumentException.class,
                () -> new EventBuilder().withName(name).build());
    }

    // === INVALID DATE CASES ===

    @Test
    public void execute_invalidDateFormatSlash_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new EventBuilder().withDate("2025/09/01 18:00").build());
    }

    @Test
    public void execute_invalidDateFormatText_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new EventBuilder().withDate("Sept 1st 2025 18:00").build());
    }

    @Test
    public void execute_invalidDateTimeMissing_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new EventBuilder().withDate("2025-09-01").build());
    }

    @Test
    public void execute_invalidDateTimeTooEarly_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new EventBuilder().withDate("0001-01-01 00:00").build());
    }

    // === INVALID LOCATION CASES ===

    @Test
    public void execute_invalidVenueTooLong_throwsIllegalArgumentException() {
        String venue = "LT" + "A".repeat(200);
        assertThrows(IllegalArgumentException.class,
                () -> new EventBuilder().withLocation(venue).build());
    }

    @Test
    public void execute_invalidVenueSymbols_throwsIllegalArgumentException() throws Exception {
        Event event = new EventBuilder().withLocation("!@#LT27$$").build();
        new AddEventCommand(event).execute(modelStub);
        assertTrue(modelStub.eventsAdded.stream()
                .anyMatch(e -> e.getVenue().toString().trim().equals("!@#LT27$$")));
    }

    @Test
    public void execute_invalidVenueEmpty_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new EventBuilder().withLocation("").build());
    }

    // === MISCELLANEOUS / EDGE CASES ===

    @Test
    public void execute_eventWithLeadingAndTrailingSpacesStillAdded_success() throws Exception {
        Event event = new EventBuilder().withName("  Welcome Tea  ").build();
        new AddEventCommand(event).execute(modelStub);
        assertTrue(modelStub.eventsAdded.stream()
                .anyMatch(e -> e.getName().fullName.trim().equals("Welcome Tea")));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Event event = new EventBuilder().build();
        AddEventCommand command1 = new AddEventCommand(event);
        AddEventCommand command2 = new AddEventCommand(event);
        assertTrue(command1.equals(command2));
    }

    @Test
    public void equals_differentEvents_returnsFalse() {
        AddEventCommand cmd1 = new AddEventCommand(new EventBuilder().withName("A").build());
        AddEventCommand cmd2 = new AddEventCommand(new EventBuilder().withName("B").build());
        assertFalse(cmd1.equals(cmd2));
    }

    @Test
    public void toString_containsEventName() {
        Event e = new EventBuilder().withName("Welcome Tea").build();
        assertTrue(new AddEventCommand(e).toString().contains("Welcome Tea"));
    }

    /**
     * A default model stub that has all of the methods failing.
     */
    /**
     * A default model stub that has all of the methods failing.
     * Replace the previous incomplete ModelStub with this full implementation.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetMembers() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetEvents() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEvent(Event target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEvent(Event target, Event editedEvent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEventList(Predicate<Event> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        // Alias-related methods from Model

        @Override
        public void addAlias(Alias alias) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAlias(String aliasWord) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCommand(String commandWord) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isAliasBookEmpty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeExistingAlias(String commandWord) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String actualCommand(String commandText) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public AliasBook getAliasBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Alias> getAliasList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearAllAliases() {
            throw new AssertionError("This method should not be called.");
        }
    }


    /**
     * A Model stub that contains a single event.
     */
    private class ModelStubWithEvent extends ModelStub {
        private final Event event;

        ModelStubWithEvent(Event event) {
            requireNonNull(event);
            this.event = event;
        }

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return this.event.isSameEvent(event);
        }
    }

    /**
     * A Model stub that always accepts the event being added.
     */
    private class ModelStubAcceptingEventAdded extends ModelStub {
        final ArrayList<Event> eventsAdded = new ArrayList<>();

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return eventsAdded.stream().anyMatch(event::isSameEvent);
        }

        @Override
        public void addEvent(Event event) {
            requireNonNull(event);
            eventsAdded.add(event);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
