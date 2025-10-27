package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.UnaliasCommand;
import seedu.address.logic.commands.member.ExitCommand;
import seedu.address.logic.commands.member.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class UnaliasCommandParser implements Parser<UnaliasCommand> {

    public static final Set<String> SET_OF_COMMANDS = new HashSet<>(Arrays.asList(
            AddCommand.COMMAND_WORD,
            EditCommand.COMMAND_WORD,
            DeleteCommand.COMMAND_WORD,
            ClearCommand.COMMAND_WORD,
            FindCommand.COMMAND_WORD,
            ListCommand.COMMAND_WORD,
            ExitCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD,
            AliasCommand.COMMAND_WORD,
            "all"
    ));

    @Override
    public UnaliasCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] trimmedArgsParts = trimmedArgs.split("\\s+");

        if (trimmedArgsParts.length != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnaliasCommand.MESSAGE_USAGE));
        }

        String commandWordToRemove = trimmedArgsParts[0].trim();

        if (!SET_OF_COMMANDS.contains(commandWordToRemove)) {
            throw new ParseException(String.format(MESSAGE_UNKNOWN_COMMAND, AliasCommand.MESSAGE_USAGE));
        }

        return new UnaliasCommand(commandWordToRemove);
    }
}
