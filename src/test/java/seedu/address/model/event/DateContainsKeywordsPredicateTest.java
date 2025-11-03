package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalEvents.HACKATHON;
import static seedu.address.testutil.TypicalEvents.MEETING;
import static seedu.address.testutil.TypicalEvents.WORKSHOP;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

/**
 * Extensive tests for DateContainsKeywordsPredicate.
 *
 * The predicate checks whether any of the supplied keywords is a prefix of the Event's Date.value
 * (i.e., {@code event.getDate().value.startsWith(keyword)}).
 */
public class DateContainsKeywordsPredicateTest {

    // ---------------------- test() behaviour ----------------------

    @Test
    public void singleFullDateTime_matchesExactDateTime_returnsTrue() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.singletonList("2025-12-15T14:00"));
        assertTrue(predicate.test(HACKATHON));
    }

    @Test
    public void singlePartialYear_matchesYearPrefix_returnsTrue() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.singletonList("2025"));
        assertTrue(predicate.test(HACKATHON)); // "2025-12-15T14:00" starts with "2025"
    }

    @Test
    public void singlePartialYearMonth_matchesYearMonthPrefix_returnsTrue() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.singletonList("2025-12"));
        assertTrue(predicate.test(HACKATHON)); // matches "2025-12-..."
    }

    @Test
    public void singlePartialDate_matchesDatePrefix_returnsTrue() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.singletonList("2025-12-15"));
        assertTrue(predicate.test(HACKATHON)); // matches "2025-12-15T14:00"
    }

    @Test
    public void partialDateTimePrefix_withoutSeconds_returnsTrue() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.singletonList("2025-12-15T14"));
        assertTrue(predicate.test(HACKATHON)); // "2025-12-15T14:00" startsWith "2025-12-15T14"
    }

    @Test
    public void keywordLongerThanValue_noMatch_returnsFalse() {
        // keyword longer than stored value -> cannot be prefix
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.singletonList("2025-12-15T14:00:00"));
        assertFalse(predicate.test(HACKATHON));
    }

    @Test
    public void differentDate_noMatch_returnsFalse() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.singletonList("2026-01-05T14:00"));
        assertFalse(predicate.test(HACKATHON));
    }

    @Test
    public void multipleKeywords_oneMatches_returnsTrue() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Arrays.asList("2026", "2025-12"));
        assertTrue(predicate.test(HACKATHON)); // second keyword matches
    }

    @Test
    public void multipleKeywords_noneMatch_returnsFalse() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Arrays.asList("2024", "2026-03"));
        assertFalse(predicate.test(HACKATHON));
    }

    @Test
    public void emptyKeywordsList_returnsFalse() {
        // matches the behavior in your earlier Location test: empty list -> no keywords to match
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(HACKATHON));
    }

    @Test
    public void keywordEmptyString_isPrefixOfEverything_returnsTrue() {
        // startsWith("") is true for any string in Java, so predicate should return true
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.singletonList(""));
        assertTrue(predicate.test(HACKATHON));
        assertTrue(predicate.test(MEETING));
    }

    @Test
    public void keywordWithLeadingWhitespace_noMatch_returnsFalse() {
        // keyword has leading space, value does not start with space -> no match
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.singletonList(" 2025"));
        assertFalse(predicate.test(HACKATHON));
    }

    @Test
    public void differentEvents_differentDates_behaviourChecked() {
        // WORKSHOP: "2025-11-10T13:00"
        DateContainsKeywordsPredicate predicateWorkshopYear =
                new DateContainsKeywordsPredicate(Collections.singletonList("2025-11"));
        assertTrue(predicateWorkshopYear.test(WORKSHOP));
        assertFalse(predicateWorkshopYear.test(HACKATHON));
    }

    @Test
    public void multipleKeywords_someEmptyStrings_returnsTrue() {
        // mixture containing empty string (matches all) and a valid prefix
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Arrays.asList("", "2024"));
        assertTrue(predicate.test(HACKATHON)); // because "" matches
    }

    @Test
    public void test_nullEvent_throwsNullPointerException() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.singletonList("2025"));
        assertThrows(NullPointerException.class, () -> predicate.test(null));
    }

    // ---------------------- equals() method ----------------------

    @Test
    public void equals_sameObject_returnsTrue() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.singletonList("2025"));
        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equals_sameKeywords_returnsTrue() {
        DateContainsKeywordsPredicate predicate1 =
                new DateContainsKeywordsPredicate(Collections.singletonList("2025"));
        DateContainsKeywordsPredicate predicate2 =
                new DateContainsKeywordsPredicate(Collections.singletonList("2025"));
        assertTrue(predicate1.equals(predicate2));
    }

    @Test
    public void equals_sameKeywordsDifferentOrder_returnsFalse() {
        DateContainsKeywordsPredicate predicate1 =
                new DateContainsKeywordsPredicate(Arrays.asList("2025", "2025-12"));
        DateContainsKeywordsPredicate predicate2 =
                new DateContainsKeywordsPredicate(Arrays.asList("2025-12", "2025"));
        // List.equals is order-sensitive, so these should be considered different
        assertFalse(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentKeywords_returnsFalse() {
        DateContainsKeywordsPredicate predicate1 =
                new DateContainsKeywordsPredicate(Collections.singletonList("2025"));
        DateContainsKeywordsPredicate predicate2 =
                new DateContainsKeywordsPredicate(Collections.singletonList("2026"));
        assertFalse(predicate1.equals(predicate2));
    }

    @Test
    public void equals_null_returnsFalse() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.singletonList("2025"));
        assertFalse(predicate.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Collections.singletonList("2025"));
        assertFalse(predicate.equals("not a predicate"));
    }

    // ---------------------- toString() method ----------------------

    @Test
    public void toString_containsKeywords() {
        DateContainsKeywordsPredicate predicate =
                new DateContainsKeywordsPredicate(Arrays.asList("2025", "2025-12"));
        String str = predicate.toString();
        assertTrue(str.contains("2025"));
        assertTrue(str.contains("2025-12"));
    }
}
