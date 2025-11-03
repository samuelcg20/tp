package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.testutil.TypicalEvents;
import seedu.address.testutil.TypicalPersons;

/**
 * Tests for {@link JsonSerializableAddressBook}.
 *
 * This test file constructs JsonSerializableAddressBook instances programmatically
 * (rather than reading JSON files) so tests are deterministic and independent of
 * on-disk fixtures.
 */
public class JsonSerializableAddressBookTest {

    @Test
    public void toModelType_typicalAddressBook_success() throws Exception {
        // Build an AddressBook containing the typical persons and events
        AddressBook source = new AddressBook();
        for (seedu.address.model.person.Person p : TypicalPersons.getTypicalPersons()) {
            source.addPerson(p);
        }
        for (seedu.address.model.event.Event e : TypicalEvents.getTypicalEvents()) {
            source.addEvent(e);
        }

        // Convert to JsonSerializableAddressBook via the ReadOnlyAddressBook constructor
        JsonSerializableAddressBook jsonAb = new JsonSerializableAddressBook((ReadOnlyAddressBook) source);

        // toModelType should produce an AddressBook equal to the source
        AddressBook converted = jsonAb.toModelType();
        assertEquals(source, converted);
    }

    @Test
    public void toModelType_emptyAddressBook_success() throws Exception {
        // When constructed from an empty AddressBook, toModelType should return an empty AddressBook
        AddressBook empty = new AddressBook();
        JsonSerializableAddressBook jsonAb = new JsonSerializableAddressBook((ReadOnlyAddressBook) empty);
        AddressBook converted = jsonAb.toModelType();
        assertEquals(empty, converted);
    }

    @Test
    public void constructor_nullLists_producesEmptyCollections() throws Exception {
        // The JSON ctor should accept null lists and initialize to empty lists internally,
        // so toModelType should return an empty AddressBook.
        JsonSerializableAddressBook jsonAb = new JsonSerializableAddressBook(null, null);
        AddressBook converted = jsonAb.toModelType();
        assertEquals(new AddressBook(), converted);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() {
        // Create two JsonAdaptedPerson instances representing the same person
        JsonAdaptedPerson p1 = new JsonAdaptedPerson(TypicalPersons.ALICE);
        JsonAdaptedPerson p2 = new JsonAdaptedPerson(TypicalPersons.ALICE); // duplicate

        List<JsonAdaptedPerson> personList = new ArrayList<>(Arrays.asList(p1, p2));
        // no events in this test
        List<JsonAdaptedEvent> eventList = Collections.emptyList();

        JsonSerializableAddressBook jsonAb = new JsonSerializableAddressBook(personList, eventList);

        IllegalValueException thrown = assertThrows(IllegalValueException.class, jsonAb::toModelType);
        assertEquals(JsonSerializableAddressBook.MESSAGE_DUPLICATE_PERSON, thrown.getMessage());
    }

    @Test
    public void toModelType_duplicateEvents_throwsIllegalValueException() {
        // Create two JsonAdaptedEvent instances representing the same event
        JsonAdaptedEvent e1 = new JsonAdaptedEvent(TypicalEvents.MEETING);
        JsonAdaptedEvent e2 = new JsonAdaptedEvent(TypicalEvents.MEETING); // duplicate

        List<JsonAdaptedEvent> eventList = new ArrayList<>(Arrays.asList(e1, e2));
        // no persons in this test
        List<JsonAdaptedPerson> personList = Collections.emptyList();

        JsonSerializableAddressBook jsonAb = new JsonSerializableAddressBook(personList, eventList);

        IllegalValueException thrown = assertThrows(IllegalValueException.class, jsonAb::toModelType);
        assertEquals(JsonSerializableAddressBook.MESSAGE_DUPLICATE_EVENT, thrown.getMessage());
    }
}
