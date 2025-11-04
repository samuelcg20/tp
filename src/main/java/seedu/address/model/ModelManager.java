package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.AliasBook;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AliasBook aliasBook;
    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Event> filteredEvents;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, AliasBook aliasBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, aliasBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.aliasBook = aliasBook;
        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredEvents = new FilteredList<>(this.addressBook.getEventList());
    }

    public ModelManager() {
        this(new AddressBook(), new AliasBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public void resetMembers() {
        // Clean up attendance in all events for every existing person before clearing
        List<Person> existingPersons = new ArrayList<>(this.addressBook.getPersonList());
        for (Person person : existingPersons) {
            cleanupPersonAttendance(person);
        }

        List<Person> persons = new ArrayList<>();
        this.addressBook.setPersons(persons);
    }

    @Override
    public void resetEvents() {
        // Clean up attendance counts in all persons for every existing event before clearing
        List<Event> existingEvents = new ArrayList<>(this.addressBook.getEventList());
        for (Event event : existingEvents) {
            if (!event.getDate().isPastCurrDate()) {
                cleanupEventAttendance(event);
            }
        }

        List<Event> events = new ArrayList<>();
        this.addressBook.setEvents(events);
    }

    //=========== Person methods ================================================================================

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        // Clean up attendance records before deleting the person
        cleanupPersonAttendance(target);
        addressBook.removePerson(target);
    }

    /**
     * Cleans up attendance records when a person is deleted.
     * Removes the person's attendance entry from all event attendance lists.
     */
    private void cleanupPersonAttendance(Person person) {
        String attendanceEntry = person.getAttendanceKey();
        // Get all events and remove this person from their attendance lists
        List<Event> allEvents = addressBook.getEventList();
        for (Event event : allEvents) {
            if (event.hasAttendee(attendanceEntry)) {
                Event updatedEvent = event.removeFromAttendanceList(attendanceEntry);
                addressBook.setEvent(event, updatedEvent);
            }
        }
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        updatePersonAttendanceEntry(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    /**
     * Updates a person's identifier in all event attendance lists when they are edited.
     */
    private void updatePersonAttendanceEntry(Person original, Person updated) {
        String oldEntry = original.getAttendanceKey();
        String newEntry = updated.getAttendanceKey();

        List<Event> allEvents = addressBook.getEventList();
        for (Event event : allEvents) {
            if (!oldEntry.equals(newEntry) && event.hasAttendee(oldEntry)) {
                Event updatedEvent = event.replaceAttendeeEntry(oldEntry, newEntry);
                addressBook.setEvent(event, updatedEvent);
            }
        }
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Event methods ================================================================================

    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return addressBook.hasEvent(event);
    }

    @Override
    public void deleteEvent(Event target) {
        if (!target.getDate().isPastCurrDate()) {
            cleanupEventAttendance(target);
        }
        addressBook.removeEvent(target);
    }

    /**
     * Cleans up attendance records when an event is deleted.
     * Decreases attendance count for all members who were marked for this event.
     */
    private void cleanupEventAttendance(Event event) {
        if (event.getAttendees().isEmpty()) {
            return;
        }
        List<String> memberEntries = event.getAttendees();
        // Get all persons and decrease their attendance count
        List<Person> allPersons = addressBook.getPersonList();
        for (Person person : allPersons) {
            String personEntry = person.getAttendanceKey();
            // Check if this person was marked for attendance at this event
            if (!memberEntries.contains(personEntry)) {
                continue;
            }
            int newAttendanceCount = Math.max(0, person.getAttendanceCount() - 1);
            Person updatedPerson = person.withAttendanceCount(newAttendanceCount);
            addressBook.setPerson(person, updatedPerson);
        }
    }

    @Override
    public void addEvent(Event event) {
        addressBook.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
    }

    @Override
    public void setEvent(Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);

        addressBook.setEvent(target, editedEvent);
    }

    //=========== Alias methods ================================================================================

    @Override
    public void addAlias(Alias alias) {
        aliasBook.addAlias(alias);
    }

    @Override
    public boolean hasAlias(Alias alias) {
        return aliasBook.isAliasPresent(alias.getAliasWord());
    }

    @Override
    public boolean hasCommand(String commandWord) {
        return aliasBook.isCommandPresent(commandWord);
    }

    @Override
    public boolean hasCommand(Alias alias) {
        return aliasBook.isCommandPresent(alias.getCommandWord());
    }

    @Override
    public void removeAlias(String commandWord) {
        String key = aliasBook.getAliasForCommandWord(commandWord);
        assert key != null : "Key should not be null";
        aliasBook.removeAlias(key);
    }

    @Override
    public void removeAlias(Alias alias) {
        removeAlias(alias.getCommandWord());
    }

    @Override
    public void replaceAlias(Alias alias) {
        removeAlias(alias);
        addAlias(alias);
    }

    @Override
    public AliasBook getAliasBook() {
        return aliasBook;
    }

    @Override
    public List<Alias> getAliasList() {
        return aliasBook.getAliasList();
    }

    @Override
    public void clearAllAliases() {
        aliasBook.clear();
    }

    @Override
    public boolean isAliasBookEmpty() {
        return aliasBook.isEmpty();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Event} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Event> getFilteredEventList() {
        return filteredEvents;
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredEvents.equals(otherModelManager.filteredEvents);
    }

}
