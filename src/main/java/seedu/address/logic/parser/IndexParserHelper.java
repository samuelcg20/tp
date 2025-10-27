package seedu.address.logic.parser;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Utilities for parsing one-based positive indices from user input.
 */
public final class IndexParserHelper {

    private IndexParserHelper() {}

    /**
     * Parses a single index string into a positive integer.
     * @param raw the raw index string
     * @param indexType a label used in error messages
     * @return the parsed positive integer
     * @throws ParseException if the index is non-numeric or not positive
     */
    public static int parsePositiveIndex(String raw, String indexType) throws ParseException {
        try {
            int index = Integer.parseInt(raw.trim());
            if (index <= 0) {
                throw new ParseException(String.format(
                        "Invalid %s index: %d. Index must be positive.", indexType, index));
            }
            return index;
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(
                    "Invalid %s index: %s. Index must be a number.", indexType, raw));
        }
    }
}


