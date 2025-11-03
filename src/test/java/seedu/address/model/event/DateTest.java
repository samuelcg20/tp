package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


public class DateTest {

    // ---------------------- Constructor tests ----------------------

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidFormat_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Date("2025-10-35T12:00"));
    }

    @Test
    public void constructor_validDate_success() {
        String validDate = "2025-10-29T15:30";
        Date date = new Date(validDate);
        assertTrue(date.value.equals(validDate));
    }

    // ---------------------- isValidDate tests ----------------------

    @Test
    public void isValidDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));
    }

    @Test
    public void isValidDate_emptyString_returnsFalse() {
        assertFalse(Date.isValidDate(""));
    }

    @Test
    public void isValidDate_invalidFormat_returnsFalse() {
        assertFalse(Date.isValidDate("2025/10/29 15:30"));
        assertFalse(Date.isValidDate("2025-13-01T10:00")); // invalid month
        assertFalse(Date.isValidDate("2025-00-10T10:00")); // invalid month
        assertFalse(Date.isValidDate("2025-02-30T12:00")); // invalid day
        assertFalse(Date.isValidDate("15:00")); // just time
        assertFalse(Date.isValidDate("2025-10-29")); // missing time
    }

    @Test
    public void isValidDate_validFormat_returnsTrue() {
        assertTrue(Date.isValidDate("2025-10-29T15:30"));
        assertTrue(Date.isValidDate("2025-01-01T00:00"));
        assertTrue(Date.isValidDate("2025-12-31T23:59"));
    }

    // ---------------------- equals tests ----------------------

    @Test
    public void equals_sameObject_returnsTrue() {
        Date date = new Date("2025-10-29T12:00");
        assertTrue(date.equals(date));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Date date1 = new Date("2025-10-29T12:00");
        Date date2 = new Date("2025-10-29T12:00");
        assertTrue(date1.equals(date2));
    }

    @Test
    public void equals_null_returnsFalse() {
        Date date = new Date("2025-10-29T12:00");
        assertFalse(date.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Date date = new Date("2025-10-29T12:00");
        assertFalse(date.equals("2025-10-29T12:00"));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Date date1 = new Date("2025-10-29T12:00");
        Date date2 = new Date("2025-10-30T12:00");
        assertFalse(date1.equals(date2));
    }

    // ---------------------- hashCode tests ----------------------

    @Test
    public void hashCode_sameValues_returnsSameHash() {
        Date date1 = new Date("2025-10-29T12:00");
        Date date2 = new Date("2025-10-29T12:00");
        assertTrue(date1.hashCode() == date2.hashCode());
    }

    @Test
    public void hashCode_differentValues_returnsDifferentHash() {
        Date date1 = new Date("2025-10-29T12:00");
        Date date2 = new Date("2025-10-30T12:00");
        assertFalse(date1.hashCode() == date2.hashCode());
    }

    // ---------------------- toString test ----------------------

    @Test
    public void toString_returnsValue() {
        Date date = new Date("2025-10-29T12:00");
        assertTrue(date.toString().equals("2025-10-29T12:00"));
    }

    // ---------------------- Edge case tests ----------------------

    @Test
    public void isValidDate_leapYear_returnsTrue() {
        assertTrue(Date.isValidDate("2024-02-29T12:00")); // valid leap day
    }

    @Test
    public void isValidDate_nonLeapYear_returnsFalse() {
        assertFalse(Date.isValidDate("2023-02-29T12:00")); // invalid leap day
    }

    @Test
    public void isValidDate_timeEdgeCases_returnsTrue() {
        assertTrue(Date.isValidDate("2025-10-29T00:00")); // midnight
        assertTrue(Date.isValidDate("2025-10-29T23:59")); // last minute
    }

    @Test
    public void isPastCurrDate_pastDate_returnsTrue() {
        Date pastDate = new Date("2000-01-01T00:00");
        assertTrue(pastDate.isPastCurrDate());
    }

    @Test
    public void isPastCurrDate_futureDate_returnsFalse() {
        Date futureDate = new Date("2999-01-01T00:00");
        assertFalse(futureDate.isPastCurrDate());
    }
}
