package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.MEETING;
import static seedu.address.testutil.TypicalEvents.WORKSHOP;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.testutil.EventBuilder;

public class UniqueEventListTest {

    private final UniqueEventList uniqueEventList = new UniqueEventList();

    @Test
    public void contains_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.contains(null));
    }

    @Test
    public void contains_eventNotInList_returnsFalse() {
        assertFalse(uniqueEventList.contains(MEETING));
    }

    @Test
    public void contains_eventInList_returnsTrue() {
        uniqueEventList.add(MEETING);
        assertTrue(uniqueEventList.contains(MEETING));
    }

    @Test
    public void add_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.add(null));
    }

    @Test
    public void add_duplicateEvent_throwsDuplicateEventException() {
        uniqueEventList.add(MEETING);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.add(MEETING));
    }

    @Test
    public void setEvent_nullTargetEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvent(null, MEETING));
    }

    @Test
    public void setEvent_nullEditedEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvent(MEETING, null));
    }

    @Test
    public void setEvent_targetEventNotInList_throwsEventNotFoundException() {
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.setEvent(MEETING, MEETING));
    }

    @Test
    public void setEvent_editedEventIsSameEvent_success() {
        uniqueEventList.add(MEETING);
        uniqueEventList.setEvent(MEETING, MEETING);
        UniqueEventList expectedList = new UniqueEventList();
        expectedList.add(MEETING);
        assertEquals(expectedList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasSameIdentity_success() {
        uniqueEventList.add(MEETING);
        Event editedEvent = new EventBuilder(MEETING)
                .withLocation("New Hall")
                .build();
        uniqueEventList.setEvent(MEETING, editedEvent);
        UniqueEventList expectedList = new UniqueEventList();
        expectedList.add(editedEvent);
        assertEquals(expectedList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasDifferentIdentity_success() {
        uniqueEventList.add(MEETING);
        uniqueEventList.setEvent(MEETING, WORKSHOP);
        UniqueEventList expectedList = new UniqueEventList();
        expectedList.add(WORKSHOP);
        assertEquals(expectedList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasNonUniqueIdentity_throwsDuplicateEventException() {
        uniqueEventList.add(MEETING);
        uniqueEventList.add(WORKSHOP);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.setEvent(MEETING, WORKSHOP));
    }

    @Test
    public void remove_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.remove(null));
    }

    @Test
    public void remove_eventDoesNotExist_throwsEventNotFoundException() {
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.remove(MEETING));
    }

    @Test
    public void remove_existingEvent_removesEvent() {
        uniqueEventList.add(MEETING);
        uniqueEventList.remove(MEETING);
        UniqueEventList expectedList = new UniqueEventList();
        assertEquals(expectedList, uniqueEventList);
    }

    @Test
    public void setEvents_nullUniqueEventList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvents((UniqueEventList) null));
    }

    @Test
    public void setEvents_uniqueEventList_replacesOwnListWithProvidedUniqueEventList() {
        uniqueEventList.add(MEETING);
        UniqueEventList expectedList = new UniqueEventList();
        expectedList.add(WORKSHOP);
        uniqueEventList.setEvents(expectedList);
        assertEquals(expectedList, uniqueEventList);
    }

    @Test
    public void setEvents_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvents((List<Event>) null));
    }

    @Test
    public void setEvents_list_replacesOwnListWithProvidedList() {
        uniqueEventList.add(MEETING);
        List<Event> eventList = Collections.singletonList(WORKSHOP);
        uniqueEventList.setEvents(eventList);
        UniqueEventList expectedList = new UniqueEventList();
        expectedList.add(WORKSHOP);
        assertEquals(expectedList, uniqueEventList);
    }

    @Test
    public void setEvents_listWithDuplicateEvents_throwsDuplicateEventException() {
        List<Event> listWithDuplicates = Arrays.asList(MEETING, MEETING);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.setEvents(listWithDuplicates));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueEventList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toString_returnsUnmodifiableListString() {
        assertEquals(uniqueEventList.asUnmodifiableObservableList().toString(), uniqueEventList.toString());
    }
}
