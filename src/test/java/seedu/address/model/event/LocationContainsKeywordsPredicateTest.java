package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalEvents.HACKATHON;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

public class LocationContainsKeywordsPredicateTest {

    // ---------------------- test() method ----------------------

    @Test
    public void singleKeyword_matchesVenue_returnsTrue() {
        LocationContainsKeywordsPredicate predicate =
                new LocationContainsKeywordsPredicate(Collections.singletonList("UTown"));
        assertTrue(predicate.test(HACKATHON));
    }

    @Test
    public void singleKeyword_doesNotMatchVenue_returnsFalse() {
        LocationContainsKeywordsPredicate predicate =
                new LocationContainsKeywordsPredicate(Collections.singletonList("Zoom"));
        assertFalse(predicate.test(HACKATHON));
    }

    @Test
    public void multipleKeywords_oneMatches_returnsTrue() {
        LocationContainsKeywordsPredicate predicate =
                new LocationContainsKeywordsPredicate(Arrays.asList("Zoom", "UTown"));
        assertTrue(predicate.test(HACKATHON));
    }

    @Test
    public void multipleKeywords_noneMatch_returnsFalse() {
        LocationContainsKeywordsPredicate predicate =
                new LocationContainsKeywordsPredicate(Arrays.asList("Zoom", "COM2-0205"));
        assertFalse(predicate.test(HACKATHON));
    }

    @Test
    public void keywordDifferentCase_matchesIgnoreCase_returnsTrue() {
        LocationContainsKeywordsPredicate predicate =
                new LocationContainsKeywordsPredicate(Collections.singletonList("uTOWN"));
        assertTrue(predicate.test(HACKATHON));
    }

    @Test
    public void partialWord_noMatch_returnsFalse() {
        LocationContainsKeywordsPredicate predicate =
                new LocationContainsKeywordsPredicate(Collections.singletonList("UT"));
        assertFalse(predicate.test(HACKATHON));
    }

    @Test
    public void emptyKeywords_returnsFalse() {
        LocationContainsKeywordsPredicate predicate =
                new LocationContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(HACKATHON));
    }

    // ---------------------- equals() method ----------------------

    @Test
    public void equals_sameObject_returnsTrue() {
        LocationContainsKeywordsPredicate predicate =
                new LocationContainsKeywordsPredicate(Collections.singletonList("UTown"));
        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equals_sameKeywords_returnsTrue() {
        LocationContainsKeywordsPredicate predicate1 =
                new LocationContainsKeywordsPredicate(Collections.singletonList("UTown"));
        LocationContainsKeywordsPredicate predicate2 =
                new LocationContainsKeywordsPredicate(Collections.singletonList("UTown"));
        assertTrue(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentKeywords_returnsFalse() {
        LocationContainsKeywordsPredicate predicate1 =
                new LocationContainsKeywordsPredicate(Collections.singletonList("UTown"));
        LocationContainsKeywordsPredicate predicate2 =
                new LocationContainsKeywordsPredicate(Collections.singletonList("Zoom"));
        assertFalse(predicate1.equals(predicate2));
    }

    @Test
    public void equals_null_returnsFalse() {
        LocationContainsKeywordsPredicate predicate =
                new LocationContainsKeywordsPredicate(Collections.singletonList("UTown"));
        assertFalse(predicate.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        LocationContainsKeywordsPredicate predicate =
                new LocationContainsKeywordsPredicate(Collections.singletonList("UTown"));
        assertFalse(predicate.equals("not a predicate"));
    }

    // ---------------------- toString() method ----------------------

    @Test
    public void toString_containsKeywords() {
        LocationContainsKeywordsPredicate predicate =
                new LocationContainsKeywordsPredicate(Arrays.asList("UTown", "Zoom"));
        String str = predicate.toString();
        assertTrue(str.contains("UTown"));
        assertTrue(str.contains("Zoom"));
    }
}
