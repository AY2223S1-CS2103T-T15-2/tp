package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CLASS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.CLASS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CLASS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PARENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PARENT_NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PARENT_NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalStudents.AMY;
import static seedu.address.testutil.TypicalStudents.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddStudCommand;
import seedu.address.model.student.Class;
import seedu.address.model.student.Email;
import seedu.address.model.student.Id;
import seedu.address.model.student.Name;
import seedu.address.model.student.ParentName;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.StudentBuilder;

public class AddStudCommandParserTest {
    private AddStudCommandParser parser = new AddStudCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Student expectedPerson = new StudentBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + STUDENT_NAME_DESC_BOB + ID_DESC_BOB + CLASS_DESC_BOB
                + PARENT_NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_FRIEND, new AddStudCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, STUDENT_NAME_DESC_AMY + STUDENT_NAME_DESC_BOB + ID_DESC_BOB + CLASS_DESC_BOB
                + PARENT_NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_FRIEND, new AddStudCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, STUDENT_NAME_DESC_BOB + ID_DESC_BOB + CLASS_DESC_BOB
                + PARENT_NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + TAG_DESC_FRIEND, new AddStudCommand(expectedPerson));

        // multiple ids - last id accepted
        assertParseSuccess(parser, STUDENT_NAME_DESC_BOB + ID_DESC_AMY + ID_DESC_BOB + CLASS_DESC_BOB
                + PARENT_NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_FRIEND, new AddStudCommand(expectedPerson));

        // multiple class - last class accepted
        assertParseSuccess(parser, STUDENT_NAME_DESC_BOB + ID_DESC_BOB + CLASS_DESC_AMY + CLASS_DESC_BOB
                + PARENT_NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_FRIEND, new AddStudCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, STUDENT_NAME_DESC_BOB + ID_DESC_BOB + CLASS_DESC_BOB
                + PARENT_NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_AMY + EMAIL_DESC_BOB + TAG_DESC_FRIEND, new AddStudCommand(expectedPerson));

        // multiple tags - all accepted
        Student expectedPersonMultipleTags = new StudentBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, STUDENT_NAME_DESC_BOB + ID_DESC_BOB + CLASS_DESC_BOB
                        + PARENT_NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, new AddStudCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Student expectedPerson = new StudentBuilder(AMY).withTags().build();
        assertParseSuccess(parser, STUDENT_NAME_DESC_AMY + ID_DESC_AMY + CLASS_DESC_AMY
                + PARENT_NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY, new AddStudCommand(expectedPerson));
        // missing parent name
        Student expectedPersonWithoutParentName = new StudentBuilder(AMY).withParentName("").build();
        assertParseSuccess(parser, STUDENT_NAME_DESC_AMY + ID_DESC_AMY + CLASS_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY, new AddStudCommand(expectedPersonWithoutParentName));
        // missing phone
        Student expectedPersonWithoutPhone = new StudentBuilder(AMY).withPhone("").build();
        assertParseSuccess(parser, STUDENT_NAME_DESC_AMY + ID_DESC_AMY + CLASS_DESC_AMY
                + PARENT_NAME_DESC_AMY + EMAIL_DESC_AMY, new AddStudCommand(expectedPersonWithoutPhone));
        // missing email
        Student expectedPersonWithoutEmail = new StudentBuilder(AMY).withEmail("").build();
        assertParseSuccess(parser, STUDENT_NAME_DESC_AMY + ID_DESC_AMY + CLASS_DESC_AMY
                + PARENT_NAME_DESC_AMY + PHONE_DESC_AMY, new AddStudCommand(expectedPersonWithoutEmail));
        // missing parent name, phone, email and zero tags
        Student expectedPersonWithoutOptional = new StudentBuilder(AMY)
                .withParentName("")
                .withEmail("")
                .withPhone("")
                .withTags().build();
        assertParseSuccess(parser, STUDENT_NAME_DESC_AMY + ID_DESC_AMY + CLASS_DESC_AMY,
                new AddStudCommand(expectedPersonWithoutOptional));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStudCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_STUDENT_NAME_BOB + CLASS_DESC_BOB + PHONE_DESC_BOB
                        + ID_DESC_BOB + EMAIL_DESC_BOB, expectedMessage);

        // missing id prefix
        assertParseFailure(parser, STUDENT_NAME_DESC_BOB + CLASS_DESC_BOB + PHONE_DESC_BOB
                        + VALID_ID_BOB + EMAIL_DESC_BOB, expectedMessage);

        // missing class prefix
        assertParseFailure(parser, STUDENT_NAME_DESC_BOB + VALID_CLASS_BOB + PHONE_DESC_BOB
                + ID_DESC_BOB + EMAIL_DESC_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_STUDENT_NAME_BOB + CLASS_DESC_BOB + VALID_PHONE_BOB
                        + VALID_ID_BOB + VALID_EMAIL_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_STUDENT_NAME_DESC + ID_DESC_BOB + CLASS_DESC_BOB
                + PARENT_NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid class
        assertParseFailure(parser, STUDENT_NAME_DESC_BOB + ID_DESC_BOB + INVALID_CLASS_DESC
                + PARENT_NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_FRIEND, Class.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, STUDENT_NAME_DESC_BOB + ID_DESC_BOB + CLASS_DESC_BOB + PARENT_NAME_DESC_BOB
                + INVALID_PHONE_DESC + EMAIL_DESC_BOB + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid parent name
        assertParseFailure(parser, STUDENT_NAME_DESC_BOB + ID_DESC_BOB + CLASS_DESC_BOB + INVALID_PARENT_NAME_DESC
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_FRIEND, ParentName.MESSAGE_CONSTRAINTS);

        // invalid id
        assertParseFailure(parser, STUDENT_NAME_DESC_BOB + INVALID_ID_DESC + CLASS_DESC_BOB
                + PARENT_NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_FRIEND, Id.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, STUDENT_NAME_DESC_BOB + ID_DESC_BOB + CLASS_DESC_BOB + PARENT_NAME_DESC_BOB
                + PHONE_DESC_BOB + INVALID_EMAIL_DESC + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, STUDENT_NAME_DESC_BOB + ID_DESC_BOB + CLASS_DESC_BOB + PARENT_NAME_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_TAG_DESC + TAG_DESC_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_STUDENT_NAME_DESC + ID_DESC_BOB + CLASS_DESC_BOB
                        + PARENT_NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + TAG_DESC_FRIEND,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + STUDENT_NAME_DESC_BOB + ID_DESC_BOB + CLASS_DESC_BOB
                        + PARENT_NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStudCommand.MESSAGE_USAGE));

    }
}
