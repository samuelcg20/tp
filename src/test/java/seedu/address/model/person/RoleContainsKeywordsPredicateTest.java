package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class RoleContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstKeywordList = Collections.singletonList("Member");
        List<String> secondKeywordList = Arrays.asList("Member", "Leader");

        RoleContainsKeywordsPredicate firstPredicate = new RoleContainsKeywordsPredicate(firstKeywordList);
        RoleContainsKeywordsPredicate secondPredicate = new RoleContainsKeywordsPredicate(secondKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RoleContainsKeywordsPredicate firstPredicateCopy = new RoleContainsKeywordsPredicate(firstKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_roleContainsKeywords_returnsTrue() {
        // One keyword (exact match)
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(Collections.singletonList("mem"));
        assertTrue(predicate.test(new PersonBuilder().withRole("mem").build()));

        // Multiple keywords (one matches)
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("member", "Lember"));
        assertTrue(predicate.test(new PersonBuilder().withRole("member").build()));

    }


    @Test
    public void test_roleDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withRole("member").build()));

        // Non-matching keyword
        predicate = new RoleContainsKeywordsPredicate(Collections.singletonList("lember"));
        assertFalse(predicate.test(new PersonBuilder().withRole("member").build()));

        // Keywords match name/phone/email but not year
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("Alice", "91234567", "alice@u.nus.edu"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("91234567")
                .withEmail("alice@u.nus.edu")
                .withRole("phew")
                .build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("member", "2");
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(keywords);
        String expected = "seedu.address.model.person.RoleContainsKeywordsPredicate{keywords=[member, 2]}";
        assertTrue(predicate.toString().contains("keywords=[member, 2]"));
    }
}
