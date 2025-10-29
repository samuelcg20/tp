package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_WELCOME_TEA;
import static seedu.address.logic.commands.CommandTestUtil.DESC_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_WELCOME_TEA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_WELCOME_TEA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_VENUE_WELCOME_TEA;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showEventAtIndex;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.event.EditEventCommand.EditEventDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.alias.AliasBook;
import seedu.address.model.event.Event;
import seedu.address.testutil.EditEventDescriptorBuilder;
import seedu.address.testutil.EventBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditEventCommand.
 */
public class EditEventCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new AliasBook(), new UserPrefs());
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Event editedEvent = new EventBuilder().build();
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder(editedEvent).build();
        EditEventCommand editEventCommand = new EditEventCommand(INDEX_FIRST_EVENT, descriptor);

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS,
                Messages.format(editedEvent));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new AliasBook(), new UserPrefs());
        expectedModel.setEvent(model.getFilteredEventList().get(0), editedEvent);
        expectedModel.updateFilteredEventList(Model.PREDICATE_SHOW_ALL_EVENTS);

        assertCommandSuccess(editEventCommand, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastEvent = Index.fromOneBased(model.getFilteredEventList().size());
        Event lastEvent = model.getFilteredEventList().get(indexLastEvent.getZeroBased());

        EventBuilder eventInList = new EventBuilder(lastEvent);
        Event editedEvent = eventInList.withName(VALID_EVENT_NAME_WELCOME_TEA)
                .withLocation(VALID_EVENT_VENUE_WELCOME_TEA).build();

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withName(VALID_EVENT_NAME_WELCOME_TEA)
                .withVenue(VALID_EVENT_VENUE_WELCOME_TEA)
                .build();
        EditEventCommand editEventCommand = new EditEventCommand(indexLastEvent, descriptor);

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS,
                Messages.format(editedEvent));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new AliasBook(), new UserPrefs());
        expectedModel.setEvent(lastEvent, editedEvent);
        expectedModel.updateFilteredEventList(Model.PREDICATE_SHOW_ALL_EVENTS);

        assertCommandSuccess(editEventCommand, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditEventCommand editEventCommand = new EditEventCommand(INDEX_FIRST_EVENT, new EditEventDescriptor());
        Event editedEvent = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS,
                Messages.format(editedEvent));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new AliasBook(), new UserPrefs());
        expectedModel.updateFilteredEventList(Model.PREDICATE_SHOW_ALL_EVENTS);

        assertCommandSuccess(editEventCommand, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showEventAtIndex(model, INDEX_FIRST_EVENT);

        Event eventInFilteredList = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        Event editedEvent = new EventBuilder(eventInFilteredList)
                .withName(VALID_EVENT_NAME_WELCOME_TEA)
                .build();

        EditEventCommand editEventCommand = new EditEventCommand(INDEX_FIRST_EVENT,
                new EditEventDescriptorBuilder().withName(VALID_EVENT_NAME_WELCOME_TEA).build());

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS,
                Messages.format(editedEvent));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new AliasBook(), new UserPrefs());
        expectedModel.setEvent(model.getFilteredEventList().get(0), editedEvent);
        expectedModel.updateFilteredEventList(Model.PREDICATE_SHOW_ALL_EVENTS);

        assertCommandSuccess(editEventCommand, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
    }

    @Test
    public void execute_duplicateEventUnfilteredList_failure() {
        Event firstEvent = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder(firstEvent).build();
        EditEventCommand editEventCommand = new EditEventCommand(INDEX_SECOND_EVENT, descriptor);

        assertCommandFailure(editEventCommand, model, EditEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void execute_duplicateEventFilteredList_failure() {
        showEventAtIndex(model, INDEX_FIRST_EVENT);

        // edit event in filtered list into a duplicate in the address book
        Event eventInList = model.getAddressBook().getEventList().get(INDEX_SECOND_EVENT.getZeroBased());
        EditEventCommand editEventCommand = new EditEventCommand(INDEX_FIRST_EVENT,
                new EditEventDescriptorBuilder(eventInList).build());

        assertCommandFailure(editEventCommand, model, EditEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void execute_invalidEventIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withName(VALID_EVENT_NAME_WELCOME_TEA).build();
        EditEventCommand editEventCommand = new EditEventCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidEventIndexFilteredList_failure() {
        showEventAtIndex(model, INDEX_FIRST_EVENT);
        Index outOfBoundIndex = INDEX_SECOND_EVENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEventList().size());

        EditEventCommand editEventCommand = new EditEventCommand(outOfBoundIndex,
                new EditEventDescriptorBuilder().withName(VALID_EVENT_NAME_WELCOME_TEA).build());

        assertCommandFailure(editEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_allFieldsSpecified_success() {
        Event editedEvent = new EventBuilder().withName("Updated Event")
                .withDate("2024-12-25T13:00")
                .withLocation("LT15")
                .build();
        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder(editedEvent).build();
        EditEventCommand command = new EditEventCommand(INDEX_FIRST_EVENT, descriptor);

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS,
                Messages.format(editedEvent));

        Model expectedModel = new ModelManager(model.getAddressBook(), new AliasBook(), new UserPrefs());
        expectedModel.setEvent(model.getFilteredEventList().get(0), editedEvent);
        assertCommandSuccess(command, model, expectedMessage, CommandResult.showEvents(expectedMessage),
                expectedModel);
    }

    @Test
    public void execute_duplicateEvent_failure() {
        Event firstEvent = model.getFilteredEventList().get(0);
        Event secondEvent = model.getFilteredEventList().get(1);

        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder(secondEvent).build();
        EditEventCommand command = new EditEventCommand(INDEX_FIRST_EVENT, descriptor);

        assertCommandFailure(command, model, EditEventCommand.MESSAGE_DUPLICATE_EVENT);
    }


    @Test
    public void equals() {
        final EditEventDescriptor standardDescriptor = new EditEventDescriptor(DESC_WORKSHOP);
        final EditEventCommand standardCommand = new EditEventCommand(INDEX_FIRST_EVENT, standardDescriptor);

        // same values -> returns true
        EditEventDescriptor copyDescriptor = new EditEventDescriptor(DESC_WORKSHOP);
        EditEventCommand commandWithSameValues = new EditEventCommand(INDEX_FIRST_EVENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new Object()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditEventCommand(INDEX_SECOND_EVENT, DESC_WORKSHOP)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditEventCommand(INDEX_FIRST_EVENT, DESC_WELCOME_TEA)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withName(VALID_EVENT_NAME_WELCOME_TEA)
                .withDate(VALID_EVENT_DATE_WELCOME_TEA)
                .withVenue(VALID_EVENT_VENUE_WELCOME_TEA)
                .build();
        EditEventCommand editEventCommand = new EditEventCommand(index, descriptor);
        String expected = EditEventCommand.class.getCanonicalName()
                + "{index=" + index
                + ", editMemberDescriptor=" + descriptor + "}";
        assertTrue(editEventCommand.toString().contains(expected));
    }
}
