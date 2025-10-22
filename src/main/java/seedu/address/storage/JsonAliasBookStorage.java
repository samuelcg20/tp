package seedu.address.storage;

import static java.util.Objects.requireNonNull;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.alias.AliasBook;

public class JsonAliasBookStorage implements AliasBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAliasBookStorage.class);

    private Path filePath;

    public JsonAliasBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAliasBookFilePath() {
        return this.filePath;
    }

    @Override
    public Optional<AliasBook> aliasBook() throws DataLoadingException {
        return aliasBook(filePath);
    }

    public Optional<AliasBook> aliasBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableAddressBook> jsonAliasBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableAddressBook.class);
        if (!jsonAliasBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAliasBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveAliasBook(AliasBook aliasBook) throws IOException {
        saveAddressBook(aliasBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyAddressBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAddressBook(AliasBook aliasBook, Path filePath) throws IOException {
        requireNonNull(aliasBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAddressBook(AliasBook), filePath);
    }

}
