package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.alias.AliasCreateCommand;
import seedu.address.logic.commands.alias.AliasListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.alias.Alias;

/**
 * Parses input arguments and creates a new AliasCommandParser object
 */
public class AliasCommandParser implements Parser<AliasCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AliasCommand
     * and returns an AliasCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    @Override
    public AliasCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            return new AliasListCommand();
        }

        String[] trimmedArgsParts = trimmedArgs.split("\\s+");

        if (trimmedArgsParts.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
        }

        String commandWord = trimmedArgsParts[0];
        String aliasWord = trimmedArgsParts[1];
        Alias alias = ParserUtil.parseAlias(commandWord, aliasWord);
        return new AliasCreateCommand(alias);

    }

}
