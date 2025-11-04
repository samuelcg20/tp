package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_PRESIDENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different year -> returns false
        editedAlice = new PersonBuilder(ALICE).withYear(VALID_YEAR_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different role -> returns false
        editedAlice = new PersonBuilder(ALICE).withRole(VALID_ROLE_PRESIDENT).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same phone or same email -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(ALICE.getEmail().value)
                .build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        editedAlice = new PersonBuilder(ALICE).withPhone(ALICE.getPhone().value).withEmail(VALID_EMAIL_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different phone and email -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void withAttendanceCount_updatesCountCorrectly() {
        Person person = new PersonBuilder(ALICE).build();
        Person updatedPerson = person.withAttendanceCount(10);
        assertEquals(10, updatedPerson.getAttendanceCount());
        // other fields remain unchanged
        assertEquals(person.getName(), updatedPerson.getName());
        assertEquals(person.getPhone(), updatedPerson.getPhone());
        assertEquals(person.getEmail(), updatedPerson.getEmail());
        assertEquals(person.getYear(), updatedPerson.getYear());
        assertEquals(person.getRole(), updatedPerson.getRole());
    }

    @Test
    public void toStringMethod() {
        Person person = new PersonBuilder(ALICE).build();
        String expected = Person.class.getCanonicalName()
                + "{name=" + person.getName()
                + ", phone=" + person.getPhone()
                + ", email=" + person.getEmail()
                + ", year=" + person.getYear()
                + ", tags=" + person.getRole()
                + ", attendanceCount=" + person.getAttendanceCount() + "}";
        assertEquals(expected, person.toString());
    }
}
