package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.address.logic.commands.member.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new HelpCommand.
 * The help command does not accept any arguments.
 */
public class HelpCommandParser implements Parser<HelpCommand> {

    /**
     * Parses the given arguments for the help command.
     *
     * @param args the arguments following the command word
     * @return a HelpCommand for execution
     * @throws ParseException if arguments are provided (help command takes no arguments)
     */
    @Override
    public HelpCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
        return new HelpCommand();
    }
}
