package seedu.address.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.AliasBook;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;

public class ModelManagerTest {

    private ModelManager modelManager;

    @BeforeEach
    public void setUp() {
        modelManager = new ModelManager();
    }

    @Test
    public void constructor_default_initializesEmptyModels() {
        assertNotNull(modelManager.getAddressBook());
        assertNotNull(modelManager.getAliasBook());
        assertNotNull(modelManager.getUserPrefs());
        assertTrue(modelManager.getAddressBook().getPersonList().isEmpty());
    }

    // ====================== UserPrefs Tests ======================

    @Test
    public void setUserPrefs_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_valid_copiesValues() {
        UserPrefs prefs = new UserPrefs();
        prefs.setAddressBookFilePath(Paths.get("data/addressbook.json"));
        prefs.setGuiSettings(new GuiSettings(1200, 800, 100, 100));

        modelManager.setUserPrefs(prefs);
        assertEquals(prefs, modelManager.getUserPrefs());

        // Changing original prefs should not affect modelManager's prefs
        UserPrefs copy = new UserPrefs(prefs);
        prefs.setAddressBookFilePath(Paths.get("different.json"));
        assertEquals(copy, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_valid_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1024, 768, 10, 10);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_valid_setsPath() {
        Path path = Paths.get("address/book/path.json");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    // ====================== Person Tests ======================

    @Test
    public void hasPerson_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_notInAddressBook_returnsFalse() {
        Person alice = new PersonBuilder().withName("Alice Pauline").build();
        assertFalse(modelManager.hasPerson(alice));
    }

    @Test
    public void hasPerson_inAddressBook_returnsTrue() {
        Person alice = new PersonBuilder().withName("Alice Pauline").build();
        modelManager.addPerson(alice);
        assertTrue(modelManager.hasPerson(alice));
    }

    @Test
    public void getFilteredPersonList_modify_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    // ====================== Event Tests ======================

    @Test
    public void addAliasAndHasAliasWorkCorrectly() {
        Event meeting = new EventBuilder().withName("Team Meeting").withDate("2025-11-01T10:00").build();
        modelManager.addEvent(meeting);
        assertTrue(modelManager.hasEvent(meeting));
    }

    @Test
    public void deleteEvent_removesEventSuccessfully() {
        Event meeting = new EventBuilder().withName("Team Meeting").withDate("2025-11-01T10:00").build();
        modelManager.addEvent(meeting);
        assertTrue(modelManager.hasEvent(meeting));
        modelManager.deleteEvent(meeting);
        assertFalse(modelManager.hasEvent(meeting));
    }

    @Test
    public void getFilteredEventList_modify_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredEventList().remove(0));
    }

    @Test
    public void updateFilteredEventList_validPredicate_filtersList() {
        // Use unique names AND dates to ensure no duplicates
        Event event1 = new EventBuilder().withName("Hackathon").withDate("2025-11-01T10:00").build();
        Event event2 = new EventBuilder().withName("Ideate").withDate("2025-11-02T14:00").build();

        modelManager.addEvent(event1);
        modelManager.addEvent(event2);

        modelManager.updateFilteredEventList(e -> e.getName().fullName.contains("Ideate"));
        ObservableList<Event> filtered = modelManager.getFilteredEventList();
        assertEquals(1, filtered.size());
        assertEquals(event2, filtered.get(0));
    }

    // ====================== Alias Tests ======================

    @Test
    public void addAliasWorksCorrectly() {
        Alias alias = new Alias("ls", "list");
        modelManager.addAlias(alias);
        assertTrue(modelManager.hasAlias(alias));
        assertTrue(modelManager.hasCommand(alias));
    }

    @Test
    public void removeAlias_removesSuccessfully() {
        Alias alias = new Alias("ls", "list");
        modelManager.addAlias(alias);
        modelManager.removeAlias(alias);
        assertFalse(modelManager.hasAlias(alias));
    }

    @Test
    public void clearAllAliases_clearsSuccessfully() {
        Alias alias = new Alias("ls", "list");
        modelManager.addAlias(alias);
        assertFalse(modelManager.isAliasBookEmpty());
        modelManager.clearAllAliases();
        assertTrue(modelManager.isAliasBookEmpty());
    }

    // ====================== Equals Tests ======================

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder()
                .withPerson(new PersonBuilder().withName("Alice Cooper").build())
                .build();
        AliasBook aliasBook = new AliasBook();
        UserPrefs prefs = new UserPrefs();

        ModelManager modelManager1 = new ModelManager(addressBook, aliasBook, prefs);
        ModelManager modelManager2 = new ModelManager(addressBook, aliasBook, prefs);

        // same values → true
        assertTrue(modelManager1.equals(modelManager2));

        // same object → true
        assertTrue(modelManager1.equals(modelManager1));

        // null → false
        assertFalse(modelManager1.equals(null));

        // different type → false
        assertFalse(modelManager1.equals("string"));

        // different address book → false
        AddressBook differentBook = new AddressBookBuilder().build();
        assertFalse(modelManager1.equals(new ModelManager(differentBook, aliasBook, prefs)));

        // different user prefs → false
        UserPrefs diffPrefs = new UserPrefs();
        diffPrefs.setAddressBookFilePath(Paths.get("different"));
        assertFalse(modelManager1.equals(new ModelManager(addressBook, aliasBook, diffPrefs)));
    }
}
