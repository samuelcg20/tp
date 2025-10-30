package seedu.address.model.role;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Role(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null role name
        assertThrows(NullPointerException.class, () -> Role.isValidTagName(null));
    }

}
