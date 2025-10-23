//package seedu.address.logic.parser;
//
//import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
//import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
//
//import java.util.Arrays;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.logic.commands.member.FindMemberNameCommand;
//import seedu.address.model.person.NameContainsKeywordsPredicate;
//
//public class FindMemberNameCommandParserTest {
//
//    private FindCommandParser parser = new FindCommandParser();
//
//    @Test
//    public void parse_emptyArg_throwsParseException() {
//        assertParseFailure(parser,
//                "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMemberNameCommand.MESSAGE_USAGE));
//    }
//
//    @Test
//    public void parse_validArgs_returnsFindCommand() {
//        // no leading and trailing whitespaces
//        FindMemberNameCommand expectedFindMemberNameCommand =
//                new FindMemberNameCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
//        assertParseSuccess(parser, "member Alice Bob", expectedFindMemberNameCommand);
//
//        // multiple whitespaces between keywords
//        assertParseSuccess(parser, " member \n Alice \n \t Bob  \t", expectedFindMemberNameCommand);
//    }
//
//}
