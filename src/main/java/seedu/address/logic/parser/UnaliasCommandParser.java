package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.UnaliasCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.alias.Alias;

/**
 * Parses input arguments and creates a new UnaliasCommand object.
 */
public class UnaliasCommandParser implements Parser<UnaliasCommand> {

    @Override
    public UnaliasCommand parse(String args) throws ParseException {

        if (args.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnaliasCommand.MESSAGE_USAGE));
        }

        String trimmedArgs = args.trim();
        String[] trimmedArgsParts = trimmedArgs.split("\\s+");

        if (trimmedArgsParts.length != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnaliasCommand.MESSAGE_USAGE));
        }

        String commandWordToRemove = trimmedArgsParts[0].trim();
        boolean isInvalidCommandWord = !Alias.SET_OF_COMMANDS.contains(commandWordToRemove)
                && !commandWordToRemove.equalsIgnoreCase("all");

        if (isInvalidCommandWord) {
            throw new ParseException(String.format(MESSAGE_UNKNOWN_COMMAND, AliasCommand.MESSAGE_USAGE));
        }

        return new UnaliasCommand(commandWordToRemove);
    }
}
