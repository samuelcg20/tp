package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalEvents.HACKATHON;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class EventNameContainsKeywordsPredicateTest {

    // ---------------------- test() method ----------------------

    @Test
    public void singleKeyword_matchesEventName() {
        EventNameContainsKeywordsPredicate predicate =
                new EventNameContainsKeywordsPredicate(Collections.singletonList("HackNUS"));
        assertTrue(predicate.test(HACKATHON));
    }

    @Test
    public void singleKeyword_doesNotMatchEventName_returnsFalse() {
        EventNameContainsKeywordsPredicate predicate =
                new EventNameContainsKeywordsPredicate(Collections.singletonList("Meeting"));
        assertFalse(predicate.test(HACKATHON));
    }

    @Test
    public void multipleKeywords_oneMatches() {
        EventNameContainsKeywordsPredicate predicate =
                new EventNameContainsKeywordsPredicate(Arrays.asList("Workshop", "HackNUS"));
        assertTrue(predicate.test(HACKATHON)); // Hackathon matches
    }

    @Test
    public void multipleKeywords_noneMatch_returnsFalse() {
        EventNameContainsKeywordsPredicate predicate =
                new EventNameContainsKeywordsPredicate(Arrays.asList("Workshop", "Meeting"));
        assertFalse(predicate.test(HACKATHON));
    }

    @Test
    public void keywordDifferentCase_matchesIgnoreCase() {
        EventNameContainsKeywordsPredicate predicate =
                new EventNameContainsKeywordsPredicate(Collections.singletonList("hAcKnUs"));
        assertTrue(predicate.test(HACKATHON));
    }

    @Test
    public void partialWord_noMatch_returnsFalse() {
        EventNameContainsKeywordsPredicate predicate =
                new EventNameContainsKeywordsPredicate(Collections.singletonList("Hack"));
        assertFalse(predicate.test(HACKATHON));
    }

    @Test
    public void emptyKeywords_returnsFalse() {
        EventNameContainsKeywordsPredicate predicate =
                new EventNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(HACKATHON));
    }

    // ---------------------- equals() method ----------------------

    @Test
    public void equals_sameObject_returnsTrue() {
        EventNameContainsKeywordsPredicate predicate =
                new EventNameContainsKeywordsPredicate(Collections.singletonList("HackNUS"));
        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equals_sameKeywords_returnsTrue() {
        List<String> keywords = Collections.singletonList("HackNUS");
        EventNameContainsKeywordsPredicate predicate1 = new EventNameContainsKeywordsPredicate(keywords);
        EventNameContainsKeywordsPredicate predicate2 = new EventNameContainsKeywordsPredicate(keywords);
        assertTrue(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentKeywords_returnsFalse() {
        EventNameContainsKeywordsPredicate predicate1 =
                new EventNameContainsKeywordsPredicate(Collections.singletonList("HackNUS"));
        EventNameContainsKeywordsPredicate predicate2 =
                new EventNameContainsKeywordsPredicate(Collections.singletonList("Workshop"));
        assertFalse(predicate1.equals(predicate2));
    }

    @Test
    public void equals_null_returnsFalse() {
        EventNameContainsKeywordsPredicate predicate =
                new EventNameContainsKeywordsPredicate(Collections.singletonList("HackNUS"));
        assertFalse(predicate.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        EventNameContainsKeywordsPredicate predicate =
                new EventNameContainsKeywordsPredicate(Collections.singletonList("HackNUS"));
        assertFalse(predicate.equals("not a predicate"));
    }

    // ---------------------- toString() method ----------------------

    @Test
    public void toString_containsKeywords() {
        EventNameContainsKeywordsPredicate predicate =
                new EventNameContainsKeywordsPredicate(Arrays.asList("HackNUS", "Workshop"));
        String str = predicate.toString();
        assertTrue(str.contains("HackNUS"));
        assertTrue(str.contains("Workshop"));
    }
}
