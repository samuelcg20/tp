package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Utility methods shared by attendance-related command parsers.
 */
final class AttendanceParserUtil {

    private AttendanceParserUtil() {
        // Utility class
    }

    static AttendanceIndexes parseIndexes(String args, String usageMessage) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MEMBER, PREFIX_EVENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_MEMBER, PREFIX_EVENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, usageMessage));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_MEMBER, PREFIX_EVENT);

        Index memberIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_MEMBER).get());
        Index eventIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_EVENT).get());

        return new AttendanceIndexes(memberIndex, eventIndex);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    static final class AttendanceIndexes {
        private final Index memberIndex;
        private final Index eventIndex;

        private AttendanceIndexes(Index memberIndex, Index eventIndex) {
            this.memberIndex = memberIndex;
            this.eventIndex = eventIndex;
        }

        Index getMemberIndex() {
            return memberIndex;
        }

        Index getEventIndex() {
            return eventIndex;
        }
    }
}
