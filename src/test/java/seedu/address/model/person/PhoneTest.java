 package seedu.address.model.person;

 import static org.junit.jupiter.api.Assertions.assertFalse;
 import static org.junit.jupiter.api.Assertions.assertTrue;
 import static seedu.address.testutil.Assert.assertThrows;

 import org.junit.jupiter.api.Test;

 public class PhoneTest {

     @Test
     public void constructor_null_throwsNullPointerException() {
         assertThrows(NullPointerException.class, () -> new Phone(null));
     }

     @Test
     public void constructor_invalidPhone_throwsIllegalArgumentException() {
         // empty string
         assertThrows(IllegalArgumentException.class, () -> new Phone(""));
         // spaces between digits
         assertThrows(IllegalArgumentException.class, Phone.MESSAGE_CONSTRAINTS_SPACES, () -> new Phone("91 234567"));
         // non-numeric
         assertThrows(IllegalArgumentException.class, Phone.MESSAGE_CONSTRAINTS_NUMBER, () -> new Phone("9a234567"));
         // wrong length
         assertThrows(IllegalArgumentException.class, Phone.MESSAGE_CONSTRAINTS_LENGTH, () -> new Phone("9123456"));
         // wrong start digit
         assertThrows(IllegalArgumentException.class, Phone.MESSAGE_CONSTRAINTS_START, () -> new Phone("71234567"));
     }

     @Test
     public void helpers_validateIndividually() {
         // hasInternalWhitespace
         assertTrue(Phone.hasInternalWhitespace("91 234567"));
         assertFalse(Phone.hasInternalWhitespace("91234567"));

         // isDigitsOnly
         assertTrue(Phone.isDigitsOnly("91234567"));
         assertFalse(Phone.isDigitsOnly("9a234567"));
         assertFalse(Phone.isDigitsOnly("91 234567"));

         // isValidLength
         assertTrue(Phone.isValidLength("91234567"));
         assertFalse(Phone.isValidLength("9123456"));
         assertFalse(Phone.isValidLength("912345678"));

         // isValidStart
         assertTrue(Phone.isValidStart("91234567"));
         assertTrue(Phone.isValidStart("81234567"));
         assertFalse(Phone.isValidStart("71234567"));
     }

     @Test
     public void equals() {
         Phone phone = new Phone("91234567");

         // same values -> returns true
         assertTrue(phone.equals(new Phone("91234567")));

         // same object -> returns true
         assertTrue(phone.equals(phone));

         // null -> returns false
         assertFalse(phone.equals(null));

         // different types -> returns false
         assertFalse(phone.equals(5.0f));

         // different values -> returns false
         assertFalse(phone.equals(new Phone("81234567")));
     }
 }
