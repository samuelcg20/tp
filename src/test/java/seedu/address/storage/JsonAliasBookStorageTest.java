package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.AliasBook;

public class JsonAliasBookStorageTest {

    @TempDir
    public Path tempDir;

    private Path testFile;
    private JsonAliasBookStorage storage;

    @BeforeEach
    public void setUp() {
        testFile = tempDir.resolve("testAliasBook.json");
        storage = new JsonAliasBookStorage(testFile);
    }

    @Test
    public void getAliasBookFilePath_returnsCorrectPath() {
        assertEquals(testFile, storage.getAliasBookFilePath());
    }

    @Test
    public void saveAndReadAliasBook_success() throws Exception {
        AliasBook book = new AliasBook();
        book.addAlias(new Alias("add", "a"));
        book.addAlias(new Alias("delete", "d"));

        storage.saveAliasBook(book);
        Optional<AliasBook> read = storage.aliasBook();

        assertTrue(read.isPresent());
        assertEquals(book, read.get());
    }

    @Test
    public void read_missingFile_returnsEmptyOptional() throws Exception {
        Path missingFile = tempDir.resolve("missing.json");
        JsonAliasBookStorage missingStorage = new JsonAliasBookStorage(missingFile);
        Optional<AliasBook> result = missingStorage.aliasBook();
        assertTrue(result.isEmpty());
    }

    @Test
    public void read_invalidJson_throwsDataLoadingException() throws Exception {
        Path invalidFile = tempDir.resolve("invalid.json");
        Files.writeString(invalidFile, "{invalid json}");
        JsonAliasBookStorage invalidStorage = new JsonAliasBookStorage(invalidFile);

        assertThrows(DataLoadingException.class, invalidStorage::aliasBook);
    }

    @Test
    public void saveAliasBook_nullFilePath_throwsNullPointerException() {
        AliasBook book = new AliasBook();
        assertThrows(NullPointerException.class, () -> storage.saveAliasBook(book, null));
    }

    @Test
    public void saveAliasBook_nullAliasBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> storage.saveAliasBook(null, testFile));
    }

    @Test
    public void saveAliasBook_createsFileIfMissing() throws IOException {
        Path newFile = tempDir.resolve("newAliasBook.json");
        AliasBook book = new AliasBook();
        book.addAlias(new Alias("edit", "e"));

        storage.saveAliasBook(book, newFile);
        assertTrue(Files.exists(newFile));
    }
}

