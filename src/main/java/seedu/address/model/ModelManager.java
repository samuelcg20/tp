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
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.aliasBook = new AliasBook();
        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredEvents = new FilteredList<>(this.addressBook.getEventList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
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
        List<Person> persons = new ArrayList<>();
        this.addressBook.setPersons(persons);
    }

    @Override
    public void resetEvents() {
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
     * Removes the person's name from all event attendance lists.
     */
    private void cleanupPersonAttendance(Person person) {
        String personName = person.getName().fullName;
        // Get all events and remove this person from their attendance lists
        List<Event> allEvents = addressBook.getEventList();
        for (Event event : allEvents) {
            String attendanceList = event.getAttendanceList();
            if (!attendanceList.isEmpty()) {
                String[] attendees = attendanceList.split(", ");
                for (String attendee : attendees) {
                    if (attendee.equals(personName)) {
                        Event updatedEvent = event.removeFromAttendanceList(personName);
                        addressBook.setEvent(event, updatedEvent);
                        break;
                    }
                }
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

        // If the person's name changed, update attendance records
        if (!target.getName().equals(editedPerson.getName())) {
            updatePersonNameInAttendance(target.getName().fullName, editedPerson.getName().fullName);
        }

        addressBook.setPerson(target, editedPerson);
    }

    /**
     * Updates person name in all event attendance lists when a person's name is edited.
     */
    private void updatePersonNameInAttendance(String oldName, String newName) {
        List<Event> allEvents = addressBook.getEventList();
        for (Event event : allEvents) {
            String attendanceList = event.getAttendanceList();
            if (!attendanceList.isEmpty()) {
                String[] attendees = attendanceList.split(", ");
                for (String attendee : attendees) {
                    if (attendee.equals(oldName)) {
                        // Remove old name and add new name
                        Event eventWithoutOldName = event.removeFromAttendanceList(oldName);
                        Event eventWithNewName = eventWithoutOldName.addToAttendanceList(newName);
                        addressBook.setEvent(event, eventWithNewName);
                        break; // Move to next event
                    }
                }
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
        // Clean up attendance records before deleting the event
        cleanupEventAttendance(target);
        addressBook.removeEvent(target);
    }

    /**
     * Cleans up attendance records when an event is deleted.
     * Decreases attendance count for all members who were marked for this event.
     */
    private void cleanupEventAttendance(Event event) {
        String attendanceList = event.getAttendanceList();
        if (attendanceList.isEmpty()) {
            return;
        }
        String[] memberNames = attendanceList.split(", ");
        // Get all persons and decrease their attendance count
        List<Person> allPersons = addressBook.getPersonList();
        for (Person person : allPersons) {
            String personName = person.getName().fullName;
            // Check if this person was marked for attendance at this event
            for (String memberName : memberNames) {
                if (memberName.equals(personName)) {
                    // Decrease attendance count (ensure it doesn't go below 0)
                    int newAttendanceCount = Math.max(0, person.getAttendanceCount() - 1);
                    Person updatedPerson = person.withAttendanceCount(newAttendanceCount);
                    addressBook.setPerson(person, updatedPerson);
                    break;
                }
            }
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
    public boolean hasAlias(String aliasWord) {
        return aliasBook.isAliasPresent(aliasWord);
    }

    @Override
    public boolean hasCommand(String commandWord) {
        return aliasBook.isCommandPresent(commandWord);
    }

    @Override
    public void removeExistingAlias(String commandWord) {
        String key = aliasBook.getAliasForCommandWord(commandWord);
        assert key != null : "Key should not be null";
        aliasBook.removeAlias(key);
    }

    @Override
    public AliasBook getAliasBook() {
        return aliasBook;
    }

    @Override
    public String actualCommand(String commandText) {
        return aliasBook.getCommandWordForAlias(commandText);
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
