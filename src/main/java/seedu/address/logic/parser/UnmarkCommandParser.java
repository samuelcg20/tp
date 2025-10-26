package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;

import java.util.stream.Stream;

import seedu.address.logic.commands.UnmarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnmarkCommand object.
 */
public class UnmarkCommandParser implements Parser<UnmarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnmarkCommand
     * and returns an UnmarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnmarkCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MEMBER, PREFIX_EVENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_MEMBER, PREFIX_EVENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_MEMBER, PREFIX_EVENT);

        int memberIndex = parseIndex(argMultimap.getValue(PREFIX_MEMBER).get(), "member");
        int eventIndex = parseIndex(argMultimap.getValue(PREFIX_EVENT).get(), "event");

        return new UnmarkCommand(memberIndex, eventIndex);
    }

    /**
     * Parses a single index string into an integer.
     * @param indexString The index string
     * @param type Type of index (for error messages)
     * @return Parsed integer
     * @throws ParseException if the index is invalid
     */
    private int parseIndex(String indexString, String type) throws ParseException {
        try {
            int index = Integer.parseInt(indexString.trim());
            if (index <= 0) {
                throw new ParseException(String.format("Invalid %s index: %d. Index must be positive.", type, index));
            }
            return index;
        } catch (NumberFormatException e) {
            throw new ParseException(String.format("Invalid %s index: %s. Index must be a number.", type, indexString));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
