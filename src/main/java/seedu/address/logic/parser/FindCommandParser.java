package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_NAME;
import static seedu.address.logic.Messages.MESSAGE_INVALID_ROLE;
import static seedu.address.logic.Messages.MESSAGE_INVALID_TYPE;
import static seedu.address.logic.Messages.MESSAGE_MULTIPLE_PREFIXES_EVENT;
import static seedu.address.logic.Messages.MESSAGE_MULTIPLE_PREFIXES_MEMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.event.FindEventDateCommand;
import seedu.address.logic.commands.event.FindEventNameCommand;
import seedu.address.logic.commands.member.FindMemberNameCommand;
import seedu.address.logic.commands.member.FindMemberRoleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Date;
import seedu.address.model.event.DateContainsKeywordsPredicate;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Role;
import seedu.address.model.person.RoleContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        // Split the current args input into at most 2 parts: [type, index]
        String[] argsParts = args.trim().split("\\s+", 2);

        if (argsParts.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String type = argsParts[0];
        String keywords = argsParts[1];
        if (ParserUtil.isMember(type)) {
            return checkFindMemberType(keywords);
        } else if (ParserUtil.isEvent(type)) {
            return checkFindEventType(keywords);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_TYPE, FindCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses and returns either FindEventNameCommand or FindEventLocationCommand.
     * Expects a prefix n/ or y/.
     */
    private FindCommand checkFindMemberType(String remainingArgs) throws ParseException {
        String args = remainingArgs.trim();
        if (args.startsWith(PREFIX_NAME.getPrefix())) {
            return getFindMemberNameCommand(args);
        } else if (args.startsWith(PREFIX_ROLE.getPrefix())) {
            return getFindMemberRoleCommand(args);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Creates a {@code FindMemberNameCommand} by parsing keywords after the "n/" prefix.
     *
     * @param args user input after "find member"
     * @return command to find members by name
     * @throws ParseException if no keywords are provided or format is invalid
     */
    private static FindMemberNameCommand getFindMemberNameCommand(String args) throws ParseException {
        String keywordsPart = args.substring(2).trim(); // remove n/
        if (keywordsPart.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        boolean keywordsPartContainsOtherPrefixes = CliSyntax.containsAnyPrefixForMember(keywordsPart);

        if (keywordsPartContainsOtherPrefixes) {
            throw new ParseException(
                    String.format(MESSAGE_MULTIPLE_PREFIXES_MEMBER, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = keywordsPart.split("\\s+");
        for (String word : nameKeywords) {
            if (!Name.isValidName(word)) {
                throw new ParseException(String.format(MESSAGE_INVALID_NAME, word));
            }
        }
        return new FindMemberNameCommand(
                new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

    /**
     * Creates a {@code FindMemberRoleCommand} by parsing keywords after the "r/" prefix.
     *
     * @param args user input after "find member"
     * @return command to find members role in CCA.
     * @throws ParseException if no keywords are provided or format is invalid
     */
    private static FindMemberRoleCommand getFindMemberRoleCommand(String args) throws ParseException {
        String keywordsPart = args.substring(2).trim(); // remove n/
        if (keywordsPart.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        boolean keywordsPartContainsOtherPrefixes = CliSyntax.containsAnyPrefixForMember(keywordsPart);

        if (keywordsPartContainsOtherPrefixes) {
            throw new ParseException(
                    String.format(MESSAGE_MULTIPLE_PREFIXES_MEMBER, FindCommand.MESSAGE_USAGE));
        }
        String[] nameKeywords = keywordsPart.split("\\s+");
        for (String word : nameKeywords) {
            if (!Role.isValidRoleName(word)) {
                throw new ParseException(String.format(MESSAGE_INVALID_ROLE, word));
            }
        }
        return new FindMemberRoleCommand(
                new RoleContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

    /**
     * Parses and returns either FindEventNameCommand or FindEventLocationCommand.
     * Expects a prefix n/ or l/.
     */
    private FindCommand checkFindEventType(String remainingArgs) throws ParseException {
        remainingArgs = remainingArgs.trim();
        if (remainingArgs.startsWith(PREFIX_NAME.getPrefix())) {
            return getFindEventNameCommand(remainingArgs);
        } else if (remainingArgs.startsWith(PREFIX_DATE.getPrefix())) {
            return getFindEventDateCommand(remainingArgs);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Creates a {@code FindEventLocationCommand} by parsing keywords after the "v/" prefix.
     *
     * @param remainingArgs user input after "find event"
     * @return command to find events by location
     * @throws ParseException if no keywords are provided or format is invalid
     */
    private static FindEventDateCommand getFindEventDateCommand(String remainingArgs) throws ParseException {
        String keywordsPart = remainingArgs.substring(2).trim(); // remove l/
        if (keywordsPart.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        boolean keywordsPartContainsOtherPrefixes = CliSyntax.containsAnyPrefixForEvent(keywordsPart);

        if (keywordsPartContainsOtherPrefixes) {
            throw new ParseException(
                    String.format(MESSAGE_MULTIPLE_PREFIXES_EVENT, FindCommand.MESSAGE_USAGE));
        }
        String[] dateKeywords = keywordsPart.split("\\s+");
        for (String name : dateKeywords) {
            if (!Date.isValidPartialDate(name)) {
                throw new ParseException(
                        "Please enter a valid partial date.(e.g. 14:60 or 2025-02-30 are not allowed).");
            }
        }
        return new FindEventDateCommand(new DateContainsKeywordsPredicate(Arrays.asList(dateKeywords)));
    }

    /**
     * Creates a {@code FindEventNameCommand} by parsing keywords after the "n/" prefix.
     *
     * @param remainingArgs user input after "find event"
     * @return command to find events by name
     * @throws ParseException if no keywords are provided or format is invalid
     */
    private static FindEventNameCommand getFindEventNameCommand(String remainingArgs) throws ParseException {
        String keywordsPart = remainingArgs.substring(2).trim(); // remove n/
        if (keywordsPart.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        boolean keywordsPartContainsOtherPrefixes = CliSyntax.containsAnyPrefixForEvent(keywordsPart);

        if (keywordsPartContainsOtherPrefixes) {
            throw new ParseException(
                    String.format(MESSAGE_MULTIPLE_PREFIXES_EVENT, FindCommand.MESSAGE_USAGE));
        }
        String[] nameKeywords = keywordsPart.split("\\s+");
        return new FindEventNameCommand(
                new EventNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
