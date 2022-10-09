package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARENT_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARENT_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.StudentRecord;
import seedu.address.model.student.Student;

/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalStudents {

    public static final Student ALICE = new StudentBuilder()
            .withStudentName("Alice Pauline")
            .withId("123A")
            .withParentName("May Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Student BENSON = new StudentBuilder()
            .withStudentName("Benson Meier")
            .withId("345B")
            .withParentName("Johnson Meier")
            .withPhone("98765432")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withTags("owesMoney", "friends").build();
    public static final Student CARL = new StudentBuilder()
            .withStudentName("Carl Kurz")
            .withId("567F")
            .withParentName("Kenn Kurz")
            .withPhone("95352563")
            .withAddress("wall street").build();
    public static final Student DANIEL = new StudentBuilder()
            .withStudentName("Daniel Meier")
            .withId("678D")
            .withParentName("Benn Meier")
            .withPhone("87652533")
            .withAddress("10th street")
            .withTags("friends").build();
    public static final Student ELLE = new StudentBuilder()
            .withStudentName("Elle Meyer")
            .withId("890A")
            .withId("James Meyer")
            .withPhone("9482224")
            .withAddress("michegan ave").build();
    public static final Student FIONA = new StudentBuilder()
            .withStudentName("Fiona Kunz")
            .withId("789C")
            .withParentName("Gordon Kunz")
            .withPhone("9482427")
            .withAddress("little tokyo").build();
    public static final Student GEORGE = new StudentBuilder()
            .withStudentName("George Best")
            .withId("912B")
            .withParentName("Henry Best")
            .withPhone("9482442")
            .withAddress("4th street").build();

    // Manually added
    public static final Student HOON = new StudentBuilder()
            .withStudentName("Hoon Meier")
            .withId("562B")
            .withParentName("Denise Meier")
            .withPhone("8482424")
            .withAddress("little india").build();
    public static final Student IDA = new StudentBuilder()
            .withStudentName("Ida Mueller")
            .withId("784C")
            .withParentName("Nathan Mueller")
            .withPhone("8482131")
            .withAddress("chicago ave").build();

    // Manually added - Student's details found in {@code CommandTestUtil}
    public static final Student AMY = new StudentBuilder()
            .withStudentName(VALID_STUDENT_NAME_AMY)
            .withId(VALID_ID_AMY)
            .withParentName(VALID_PARENT_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withAddress(VALID_ADDRESS_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Student BOB = new StudentBuilder()
            .withStudentName(VALID_STUDENT_NAME_BOB)
            .withId(VALID_ID_BOB)
            .withParentName(VALID_PARENT_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalStudents() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static StudentRecord getTypicalStudentRecord() {
        StudentRecord sr = new StudentRecord();
        for (Student person : getTypicalStudent()) {
            sr.addStudent(person);
        }
        return sr;
    }

    public static List<Student> getTypicalStudent() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
